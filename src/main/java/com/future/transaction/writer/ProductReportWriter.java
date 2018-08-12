package com.future.transaction.writer;

import com.future.transaction.exception.TransactionServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Component
public class ProductReportWriter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    BufferedWriter bw;
    FileWriter fw;
    StringBuilder content;
    @Value("${future.transaction.output.product.file.path}")
    private String filePath;

    public void initiate() throws TransactionServiceException {

        try {

            log.info("Generating txt file in path {} ", filePath);

            File file = new File(filePath);
            file.createNewFile();
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);


        } catch (IOException e) {
            throw new TransactionServiceException(e.getMessage());
        }
    }


    public void write(Map productTransaction) throws TransactionServiceException {

        formContent(productTransaction);
        writeToFile();
    }

    private void formContent(Map<String, Integer> productTransaction) {

        content = new StringBuilder();

        for (Map.Entry<String, Integer> entry : productTransaction.entrySet()) {
            content.append("Product  = " + entry.getKey() + ", Transaction Amount = " + entry.getValue());
            content.append('\n');
        }

    }

    private void writeToFile() throws TransactionServiceException {

        try {
            bw.write(content.toString());
            bw.flush();
        } catch (IOException e) {
            throw new TransactionServiceException(e.getMessage());
        }
    }

    public void closeStream() {
        try {
            bw.close();
            fw.close();

            log.info("Output stream closed");

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
