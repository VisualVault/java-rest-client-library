package com.visualvault.api.library;

public enum OcrOutcomeType {
    None  (0),
    Success (1),
    SuccessNoTextExtracted (2),
    Failure (3),
    FailureNoRetry (4),
    ResultingDocumentFromSuccess (5);

    private int status;

    OcrOutcomeType(int statusCode){
        this.status = statusCode;
    }

    public int getOcrOutcomeType(){
        return this.status;
    }

    public String toString(){
        return Integer.toString(status);
    }

    public String toDescriptionString(){
        switch(status) {
            case 0:
                return "None";
            case 1:
                return "Success";
            case 2:
                return "SuccessNoTextExtracted";
            case 3:
                return "Failure";
            case 4:
                return "FailureNoRetry";
            case 5:
                return "ResultingDocumentFromSuccess";
        }
        return "";
    }

    public static OcrOutcomeType fromInteger(int x) {
        switch(x) {
            case 0:
                return None;
            case 1:
                return Success;
            case 2:
                return SuccessNoTextExtracted;
            case 3:
                return Failure;
            case 4:
                return FailureNoRetry;
            case 5:
                return ResultingDocumentFromSuccess;
        }
        return null;
    }

    public static OcrOutcomeType fromString(String x) {
        switch(x) {
            case "0":
                return None;
            case "1":
                return Success;
            case "2":
                return SuccessNoTextExtracted;
            case "3":
                return Failure;
            case "4":
                return FailureNoRetry;
            case "5":
                return ResultingDocumentFromSuccess;
        }
        return null;
    }
}
