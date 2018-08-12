package com.future.transaction.writer;

import com.future.transaction.exception.TransactionServiceException;
import com.future.transaction.model.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class SummaryReportWriter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    BufferedWriter bw;
    FileWriter fw;
    StringBuilder content;
    @Value("${future.transaction.output.summary.file.path}")
    private String filePath;

    public void initiate() throws TransactionServiceException {

        try {

            log.info("Generating csv file in path {} ", filePath);

            File file = new File(filePath);
            file.createNewFile();
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);

            populateHeader();

        } catch (IOException e) {
            throw new TransactionServiceException(e.getMessage());
        }
    }

    private void populateHeader() throws TransactionServiceException {

        log.info("Writing csv header");

        content = new StringBuilder();
        String header = "Client_Information,Product_Information,Total_Transaction_Amount";
        content.append(header + "\n");
        writeToCsv();
    }

    public void write(List<Summary> summaryList) throws TransactionServiceException {

        formContent(summaryList);
        writeToCsv();
    }

    private void formContent(List<Summary> summaryList) {

        content = new StringBuilder();

        for (Summary summary : summaryList) {
            content.append(summary.getClientInformation() + ",");
            content.append(summary.getProductInformation() + ",");
            content.append(summary.getTotalTransactionAmt());
            content.append('\n');
        }

    }

    private void writeToCsv() throws TransactionServiceException {

        try {
            bw.write(content.toString());
            bw.flush();
        } catch (IOException e) {
            throw new TransactionServiceException(e.getMessage());
        }
    }

    public void closeStream(){
        try {
            bw.close();
            fw.close();

            log.info("Output stream closed");

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
