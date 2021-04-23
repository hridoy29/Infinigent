package com.example.infinigentconsulting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class SchemeAuditShopDetails {
    @SerializedName("Id")
    @Expose
    public int Id;
    @SerializedName("Number")
    @Expose
    public String Number;
    @SerializedName("OutlateName")
    @Expose
    public String OutlateName;
    @SerializedName("GccCode")
    @Expose
    public int GccCode;
    @SerializedName("RetailSellerName")
    @Expose
    public String RetailSellerName;
    @SerializedName("MobileNumber")
    @Expose
    public String MobileNumber;
    @SerializedName("OutlateTypeId")
    @Expose
    public String OutlateTypeId;
    @SerializedName("VisitedDate")
    @Expose
    public Date VisitedDate;
    @SerializedName("DistributorName")
    @Expose
    public String DistributorName;
    @SerializedName("AsmId")
    @Expose
    public int AsmId;
    @SerializedName("AicId")
    @Expose
    public int AicId;
    @SerializedName("OutlateAddress")
    @Expose
    public String OutlateAddress;


    public SchemeAuditShopDetails(int id, String number, String outlateName, int gccCode, String retailSellerName, String mobileNumber, String outlateTypeId, Date visitedDate, String distributorName, int asmId, int aicId, String outlateAddress) {
        this.Id = id;
        this.Number = number;
        this.OutlateName = outlateName;
        this.GccCode = gccCode;
        this.RetailSellerName = retailSellerName;
        this.MobileNumber = mobileNumber;
        this.OutlateTypeId = outlateTypeId;
        this.VisitedDate = visitedDate;
        this.DistributorName = distributorName;
        this.AsmId = asmId;
        this.AicId = aicId;
        this.OutlateAddress = outlateAddress;
    }

    public SchemeAuditShopDetails() {
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

    public String getOutlateName() {
        return OutlateName;
    }

    public void setOutlateName(String outlateName) {
        this.OutlateName = outlateName;
    }

    public int getGccCode() {
        return GccCode;
    }

    public void setGccCode(int gccCode) {
        this.GccCode = gccCode;
    }

    public String getRetailSellerName() {
        return RetailSellerName;
    }

    public void setRetailSellerName(String retailSellerName) {
        this.RetailSellerName = retailSellerName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.MobileNumber = mobileNumber;
    }

    public String getOutlateTypeId() {
        return OutlateTypeId;
    }

    public void setOutlateTypeId(String outlateTypeId) {
        this.OutlateTypeId = outlateTypeId;
    }

    public Date getVisitedDate() {
        return VisitedDate;
    }

    public void setVisitedDate(Date visitedDate) {
        this.VisitedDate = visitedDate;
    }

    public String getDistributorName() {
        return DistributorName;
    }

    public void setDistributorName(String distributorName) {
        this.DistributorName = distributorName;
    }

    public int getAsmId() {
        return AsmId;
    }

    public void setAsmId(int asmId) {
        this.AsmId = asmId;
    }

    public int getAicId() {
        return AicId;
    }

    public void setAicId(int aicId) {
        this.AicId = aicId;
    }

    public String getOutlateAddress() {
        return OutlateAddress;
    }

    public void setOutlateAddress(String outlateAddress) {
        this.OutlateAddress = outlateAddress;
    }


}
