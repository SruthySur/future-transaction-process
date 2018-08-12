package com.future.transaction.model;

public class Summary {

    private final String clientInformation;
    private final String productInformation;
    private final int totalTransactionAmt;

    public Summary(String clientInformation, String productInformation, int totalTransactionAmt) {
        this.clientInformation = clientInformation;
        this.productInformation = productInformation;
        this.totalTransactionAmt = totalTransactionAmt;
    }

    public String getClientInformation() {
        return clientInformation;
    }

    public String getProductInformation() {
        return productInformation;
    }

    public int getTotalTransactionAmt() {
        return totalTransactionAmt;
    }
}
