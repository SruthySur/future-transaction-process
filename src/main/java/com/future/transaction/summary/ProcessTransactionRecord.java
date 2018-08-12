package com.future.transaction.summary;

import com.future.transaction.model.Summary;
import com.future.transaction.model.information.ClientInformation;
import com.future.transaction.model.information.ProductInformation;
import com.future.transaction.model.record.TransactionRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProcessTransactionRecord {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Map<String, Integer> productTransaction = new HashMap<>();

    public List<Summary> populateSummaryBean(List<TransactionRecord> transactionRecordList) {

        ClientInformation clientInformation;
        ProductInformation productInformation;
        Summary summary;
        List<Summary> summaryList = new ArrayList<>();
        int clientProductTransactionAmt;

        for (TransactionRecord transactionRecord : transactionRecordList) {
            clientInformation = new ClientInformation(transactionRecord.getAttribute("client_type"),
                    transactionRecord.getAttribute("client_number"),
                    transactionRecord.getAttribute("account_number"),
                    transactionRecord.getAttribute("sub_account_number"));
            productInformation = new ProductInformation(transactionRecord.getAttribute("exchange_code"),
                    transactionRecord.getAttribute("product_group_code"),
                    transactionRecord.getAttribute("symbol"),
                    transactionRecord.getAttribute("expiration_date"));

            log.debug("Populating summary record with clientInformation {} productInformation {} ", clientInformation, productInformation);

            clientProductTransactionAmt = getClientProductTransactionAmt(transactionRecord);

            captureProductTransactions(productInformation.getInfo(), clientProductTransactionAmt);

            summary = new Summary(clientInformation.getInfo(), productInformation.getInfo(), clientProductTransactionAmt);
            summaryList.add(summary);
        }
        return summaryList;
    }

    private void captureProductTransactions(String productInfo, int clientProductTransactionAmt) {
        int uniqueProductTransactionAmt = clientProductTransactionAmt;

        if (productTransaction.get(productInfo) != null) {
            uniqueProductTransactionAmt = clientProductTransactionAmt + productTransaction.get(productInfo);
        }
        productTransaction.put(productInfo, uniqueProductTransactionAmt);
    }

    private int getClientProductTransactionAmt(TransactionRecord transactionRecord) {
        String quantityLong = transactionRecord.getAttribute("quantity_long");
        String quantityShort = transactionRecord.getAttribute("quantity_short");

        return (quantityLong != null ? Integer.parseInt(quantityLong) : 0) -
                (quantityShort != null ? Integer.parseInt(quantityShort) : 0);

    }


    public Map getUniqueProductTransaction() {
        return productTransaction;
    }
}
