package com.visualvault.api.library;

public enum DocumentCheckInState {
    Unreleased (0),
    Released (1),
    Replace (2),
    ReleasedNoWfServices (3);

    private int checkInState;

    DocumentCheckInState(int checkInState){
        this.checkInState = checkInState;
    }

    public int getCheckInState(){
        return this.checkInState;
    }

    public String toString(){
        return Integer.toString(checkInState);
    }

    public String toDescriptionString(){
        switch(checkInState) {
            case 0:
                return "Unreleased";
            case 1:
                return "Released";
            case 2:
                return "Replace";
            case 3:
                return "ReleasedNoWfServices";
        }
        return "";
    }

    public static DocumentCheckInState fromInteger(int x) {
        switch(x) {
            case 0:
                return Unreleased;
            case 1:
                return Released;
            case 2:
                return Replace;
            case 3:
                return ReleasedNoWfServices;
        }
        return null;
    }

    public static DocumentCheckInState fromString(String x) {
        switch(x) {
            case "0":
                return Unreleased;
            case "1":
                return Released;
            case "2":
                return Replace;
            case "3":
                return ReleasedNoWfServices;
        }
        return null;
    }

}

