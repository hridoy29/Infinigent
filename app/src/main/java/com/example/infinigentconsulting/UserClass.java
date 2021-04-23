package com.example.infinigentconsulting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserClass {
    @SerializedName("Id")
    @Expose
    public int Id;
    @SerializedName("Name")
    @Expose
    public String Name;
    @SerializedName("Email")
    @Expose
    public String Email;
    @SerializedName("MobileNo")
    @Expose
    public String MobileNo;
    @SerializedName("Password")
    @Expose
    public String Password;
    @SerializedName("IsActive")
    @Expose
    public boolean IsActive;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.MobileNo = mobileNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Boolean getIsActive() {
        return IsActive;
    }

    public void setIsActive(Boolean IsActive) {
        this.IsActive = IsActive;
    }


}
