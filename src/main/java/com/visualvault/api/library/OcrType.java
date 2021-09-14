package com.visualvault.api.library;

public enum OcrType {
    None (0),
    OcrOnly (1),
    OcrCheckInNewRev (2),
    OcrCheckInReplace (3);

    private int ocrType;

    OcrType(int ocrType){
        this.ocrType = ocrType;
    }

    public int getOcrType(){
        return this.ocrType;
    }

    public String toString(){
        return Integer.toString(ocrType);
    }

    public String toDescriptionString(){
        switch(ocrType) {
            case 0:
                return "None";
            case 1:
                return "OcrOnly";
            case 2:
                return "OcrCheckInNewRev";
            case 3:
                return "OcrCheckInReplace";
        }
        return "";
    }

    public static OcrType fromInteger(int x) {
        switch(x) {
            case 0:
                return None;
            case 1:
                return OcrOnly;
            case 2:
                return OcrCheckInNewRev;
            case 3:
                return OcrCheckInReplace;
        }
        return null;
    }

    public static OcrType fromString(String x) {
        switch(x) {
            case "0":
                return None;
            case "1":
                return OcrOnly;
            case "2":
                return OcrCheckInNewRev;
            case "3":
                return OcrCheckInReplace;
        }
        return null;
    }

}

