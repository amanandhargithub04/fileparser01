package com.processor.fileprocessor.dto;

public class Violation {
    private String propertyName;
    private int order;
    private String errorMessage;

    public Violation(String propertyName, int order, String errorMessage) {
        this.propertyName = propertyName;
        this.order = order;
        this.errorMessage = errorMessage;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public int getOrder() {
        return order;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
