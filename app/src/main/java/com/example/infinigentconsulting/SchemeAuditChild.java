package com.example.infinigentconsulting;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchemeAuditChild {
    @SerializedName("Id")
    @Expose
    public int Id;
    @SerializedName("Number")
    @Expose
    public String Number;

    @SerializedName("ImageLocation")
    @Expose
    private String ImageLocation;

    public SchemeAuditChild(int Id,String Number,String ImageLocation){
        this.Id=Id;
        this.Number=Number;
        this.setImageLocation(ImageLocation);

    }
    public void setId(int Id) {
        this.Id = Id;
    }

    public void setNumber(String Number) {
        this.Number = Number;
    }



    public int getId() {
        return Id;
    }

    public String getNumber() {
        return Number;
    }

    @NonNull
    @Override
    public String toString() {
        return Number;
    }

    public String getImageLocation() {
        return ImageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.ImageLocation = imageLocation;
    }



}

