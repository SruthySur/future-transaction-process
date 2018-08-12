package com.future.transaction.summary;

import com.future.transaction.exception.TransactionServiceException;
import com.future.transaction.mapper.RecordMapper;
import com.future.transaction.model.mapping.RecordMap;
import com.future.transaction.model.record.TransactionRecord;
import com.future.transaction.parser.InputParser;
import com.future.transaction.writer.ProductReportWriter;
import com.future.transaction.writer.SummaryReportWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReportGenerator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${future.transaction.input.file.path}")
    private String inputfilePath;


    private RecordMapper recordMapper;
    private ProcessTransactionRecord processTransactionRecord;
    private InputParser inputParser;
    private SummaryReportWriter summaryWriter;
    private ProductReportWriter productReportWriter;
    private List<RecordMap> recordMappingList;

    @Autowired
    public ReportGenerator(RecordMapper recordMapper,
                           ProcessTransactionRecord processTransactionRecord, InputParser inputParser,
                           SummaryReportWriter summaryWriter, ProductReportWriter productReportWriter) {

        this.recordMapper = recordMapper;
        this.processTransactionRecord = processTransactionRecord;
        this.inputParser = inputParser;
        this.summaryWriter = summaryWriter;
        this.productReportWriter = productReportWriter;

    }

    public void generate() {

        List<TransactionRecord> transactionRecordList = new ArrayList<>();


        try {

            summaryWriter.initiate();
            productReportWriter.initiate();

            recordMappingList = inputParser.loadRecordMapping();

            //Read and process transaction records
            Files.lines(Paths.get(inputfilePath)).
                    forEach(line -> {
                        try {
                            processRecord(transactionRecordList, line);
                        } catch (TransactionServiceException e) {
                            log.error(e.getMessage());
                        }
                    });


            //Writing any remaining records
            write(transactionRecordList);

            //Display unique product summary
            displayUniqueProductSummary();

        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (TransactionServiceException e) {
            log.error(e.getMessage());
        } finally {
            summaryWriter.closeStream();
            productReportWriter.closeStream();
        }

    }

    private void write(List<TransactionRecord> transactionRecordList) throws TransactionServiceException {
        summaryWriter.write(processTransactionRecord.populateSummaryBean(transactionRecordList));
        transactionRecordList.clear();
    }

    private void displayUniqueProductSummary() throws TransactionServiceException {
        productReportWriter.write(processTransactionRecord.getUniqueProductTransaction());
    }

    private void processRecord(List<TransactionRecord> transactionRecordList, String input) throws TransactionServiceException {
        String attributeName;
        Integer attributeSize;
        String attributeData;

        TransactionRecord transactionRecord = new TransactionRecord();

        for (RecordMap recordMap : recordMappingList) {
            attributeName = recordMap.getAttributeName();
            attributeSize = recordMap.getAttributeSize();
            attributeData = input.substring(0, attributeSize).trim();
            log.debug("Reading attribute {} with value {}", attributeName, attributeData);

            recordMapper.populateBean(transactionRecord, attributeName, attributeData);
            input = input.substring(attributeSize);
        }

        transactionRecordList.add(transactionRecord);

        //Writing to csv as group of 10 records
        if (transactionRecordList.size() == 10) {
            write(transactionRecordList);
        }
    }


}

