package com.visualvault.api.library;

public enum OcrErrorCodeType {
    None (0),
    ErrorThrown (1),
    OcrProcessingError (2),
    OcrOutputSaveError (3),
    CheckInError  (4);

    private int ocrErrorCodeType;

    OcrErrorCodeType(int ocrErrorCodeType){

        this.ocrErrorCodeType = ocrErrorCodeType;
    }

    public int getDocumentOcrErrorCodeType(){

        return this.ocrErrorCodeType;
    }

    public String toString(){
        return Integer.toString(ocrErrorCodeType);
    }

    public String toDescriptionString(){
        switch(ocrErrorCodeType) {
            case 0:
                return "None";
            case 1:
                return "ErrorThrown";
            case 2:
                return "OcrProcessingError";
            case 3:
                return "OcrOutputSaveError";
            case 4:
                return "CheckInError";
        }
        return "";
    }

    public static OcrErrorCodeType fromInteger(int x) {
        switch(x) {
            case 0:
                return None;
            case 1:
                return ErrorThrown;
            case 2:
                return OcrProcessingError;
            case 3:
                return OcrOutputSaveError;
            case 4:
                return CheckInError;
        }
        return null;
    }

    public static OcrErrorCodeType fromString(String x) {
        switch(x) {
            case "0":
                return None;
            case "1":
                return ErrorThrown;
            case "2":
                return OcrProcessingError;
            case "3":
                return OcrOutputSaveError;
            case "4":
                return CheckInError;
        }
        return null;
    }

}
