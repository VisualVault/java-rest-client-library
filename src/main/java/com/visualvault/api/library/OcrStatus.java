package com.visualvault.api.library;

public enum OcrStatus {
    OcrNotNeeded (0),
    OcrNeeded (1),
    OcrCompleted (2),
    OcrDisabledForDatabase (3),
    DocumentNotLatestRevision(4),
    DocumentNotFound (5),
    DocumentOcrCheckResult(6),
    OcrFailedNoRetry(7),
    IsOcrDocument(8),
    DocumentNotInOcrFolder(9),
    OcrNotNeededForFileType(10);

    private int status;

    OcrStatus(int statusCode){
        this.status = statusCode;
    }

    public int getStatusCode(){
        return this.status;
    }

    public String toString(){
        return Integer.toString(status);
    }

    public String toDescriptionString(){
        switch(status) {
            case 0:
                return "OcrNotNeeded";
            case 1:
                return "OcrNeeded";
            case 2:
                return "OcrCompleted";
            case 3:
                return "OcrDisabledForDatabase";
            case 4:
                return "DocumentNotLatestRevision";
            case 5:
                return "DocumentNotFound";
            case 6:
                return "DocumentOcrCheckResult";
            case 7:
                return "OcrFailedNoRetry";
            case 8:
                return "IsOcrDocument";
            case 9:
                return "DocumentNotInOcrFolder";
            case 10:
                return "OcrNotNeededForFileType";
        }
        return "";
    }

    public static OcrStatus fromInteger(int x) {
        switch(x) {
            case 0:
                return OcrNotNeeded;
            case 1:
                return OcrNeeded;
            case 2:
                return OcrCompleted;
            case 3:
                return OcrDisabledForDatabase;
            case 4:
                return DocumentNotLatestRevision;
            case 5:
                return DocumentNotFound;
            case 6:
                return DocumentOcrCheckResult;
            case 7:
                return OcrFailedNoRetry;
            case 8:
                return IsOcrDocument;
            case 9:
                return DocumentNotInOcrFolder;
            case 10:
                return OcrNotNeededForFileType;
        }
        return null;
    }

    public static OcrStatus fromString(String x) {
        switch(x) {
            case "0":
                return OcrNotNeeded;
            case "1":
                return OcrNeeded;
            case "2":
                return OcrCompleted;
            case "3":
                return OcrDisabledForDatabase;
            case "4":
                return DocumentNotLatestRevision;
            case "5":
                return DocumentNotFound;
            case "6":
                return DocumentOcrCheckResult;
            case "7":
                return OcrFailedNoRetry;
            case "8":
                return IsOcrDocument;
            case "9":
                return DocumentNotInOcrFolder;
            case "10":
                return OcrNotNeededForFileType;
        }
        return null;
    }
}
