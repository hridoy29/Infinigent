package com.example.infinigentconsulting;

public class StatusUser {
    public String getNumberStatus() {
        return numberStatus;
    }

    public void setNumberStatus(String numberStatus) {
        this.numberStatus = numberStatus;
    }

    private String numberStatus;
    private String outletNameStatus;
    private String mobileNumberStatus;

    public StatusUser() {

    }

    public String getOutletNameStatus() {
        return outletNameStatus;
    }

    public StatusUser(String outletNameStatus, String mobileNumberStatus) {
        this.outletNameStatus = outletNameStatus;
        this.mobileNumberStatus = mobileNumberStatus;
    }

    public void setOutletNameStatus(String outletNameStatus) {
        this.outletNameStatus = outletNameStatus;
    }

    public String getMobileNumberStatus() {
        return mobileNumberStatus;
    }

    public void setMobileNumberStatus(String mobileNumberStatus) {
        this.mobileNumberStatus = mobileNumberStatus;
    }
}
