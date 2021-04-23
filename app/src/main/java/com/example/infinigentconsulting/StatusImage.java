package com.example.infinigentconsulting;

public class StatusImage {

    private String serialNumberStatus;
    private int imageCountStatus;

    public StatusImage() {

    }

    public String getSerialNumberStatus() {
        return serialNumberStatus;
    }

    public void setSerialNumberStatus(String serialNumberStatus) {
        this.serialNumberStatus = serialNumberStatus;
    }

    public int getImageCountStatus() {
        return imageCountStatus;
    }

    public void setImageCountStatus(int imageCountStatus) {
        this.imageCountStatus = imageCountStatus;
    }

    public StatusImage(String serialNumberStatus, int imageCountStatus) {
        this.serialNumberStatus = serialNumberStatus;
        this.imageCountStatus = imageCountStatus;
    }
}
