package com.example.infinigentconsulting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NumberClass {
    @SerializedName("Id")
    @Expose
    public int Id;
    @SerializedName("Number")
    @Expose
    public String Number;

    public NumberClass(int id, String number) {
        this.Id=id;
        this.Number=number;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        this.Number = number;
    }


}
