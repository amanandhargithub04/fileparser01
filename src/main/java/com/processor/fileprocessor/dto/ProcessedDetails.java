package com.processor.fileprocessor.dto;

import java.util.List;

public class ProcessedDetails {
    private Object importedData;
    private Object violations;
    private String tansactionStatus;

    public ProcessedDetails() {
    }

    public Object getImportedData() {
        return importedData;
    }

    public void setImportedData(Object importedData) {
        this.importedData = importedData;
    }

    public Object getViolations() {
        return violations;
    }

    public void setViolations(Object violations) {
        this.violations = violations;
    }

    public String getTansactionStatus() {
        return tansactionStatus;
    }

    public void setTansactionStatus(String tansactionStatus) {
        this.tansactionStatus = tansactionStatus;
    }
}
