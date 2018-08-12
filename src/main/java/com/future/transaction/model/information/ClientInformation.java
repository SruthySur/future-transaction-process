package com.future.transaction.model.information;

public class ClientInformation implements Information {

    private final String clientType;
    private final String clientNumber;
    private final String accNumber;
    private final String subAccNumber;

    public ClientInformation(String clientType, String clientNumber, String accNumber, String subAccNumber) {
        this.clientType = clientType;
        this.clientNumber = clientNumber;
        this.accNumber = accNumber;
        this.subAccNumber = subAccNumber;
    }

    public String getInfo() {
        return clientType + UNDERSCORE + clientNumber + UNDERSCORE + accNumber + UNDERSCORE + subAccNumber;
    }
}
