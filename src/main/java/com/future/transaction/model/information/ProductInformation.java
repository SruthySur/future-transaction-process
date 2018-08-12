package com.future.transaction.model.information;

public class ProductInformation implements Information {

    private final String exchangeCode;
    private final String productGroupCode;
    private final String symbol;
    private final String expirationDate;

    public ProductInformation(String exchangeCode, String productGroupCode, String symbol, String expirationDate) {
        this.exchangeCode = exchangeCode;
        this.productGroupCode = productGroupCode;
        this.symbol = symbol;
        this.expirationDate = expirationDate;
    }

    public String getInfo() {
        return exchangeCode + UNDERSCORE + productGroupCode + UNDERSCORE + symbol + UNDERSCORE + expirationDate;
    }
}
