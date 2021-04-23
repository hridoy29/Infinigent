package com.example.infinigentconsulting;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test {
    @SerializedName("Id")
    @Expose
    public int Id;
    @SerializedName("Number")
    @Expose
    public String Number;

    @SerializedName("Photo")
    @Expose
    private String Photo;

public Test(int Id,String Number,String Photo){
    this.Id=Id;
    this.Number=Number;
    this.setPhoto(Photo);

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

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}