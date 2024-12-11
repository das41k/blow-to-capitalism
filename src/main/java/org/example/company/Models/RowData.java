package org.example.company.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RowData {
    private final StringProperty operation;
    private final StringProperty contractInfo;
    private final DoubleProperty amount;

    public RowData(String operation, String contractInfo, double amount) {
        this.operation = new SimpleStringProperty(operation);
        this.contractInfo = new SimpleStringProperty(contractInfo);
        this.amount = new SimpleDoubleProperty(amount);
    }

    public String getOperation() {
        return operation.get();
    }

    public StringProperty operationProperty() {
        return operation;
    }

    public String getContractInfo() {
        return contractInfo.get();
    }

    public StringProperty contractInfoProperty() {
        return contractInfo;
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }
}
