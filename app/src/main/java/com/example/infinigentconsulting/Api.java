package com.example.infinigentconsulting;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Api {


        @POST("Test")
        Call<Test> Test(
                @Body Test Test
        );
        @POST("SchemeAuditShopDetails")
        Call<SchemeAuditShopDetails> SchemeAuditShopDetails(
                @Body SchemeAuditShopDetails SchemeAuditShopDetails
        );
        @POST("SchemeAuditParent")
        Call<SchemeAuditParent> SchemeAuditParent(
                @Body SchemeAuditParent SchemeAuditParent
        );
        @POST("SchemeAuditChild")
        Call<SchemeAuditChild> SchemeAuditChild(
                @Body SchemeAuditChild SchemeAuditChild
        );

        @POST("Number")
        Call<NumberClass> Number(
                @Body NumberClass Number
        );
        @GET("User")
        Call<ArrayList<UserClass>> getUserList();
        //@FormUrlEncoded
      /*  @GET("MikroTik")
        Call<ResponseBody> MikroTik(

                @Query(value="CustomerMobileNumber", encoded=true) String CustomerMobileNumber,
                @Query(value="TotalBillAmount", encoded=true) int TotalBillAmount,
                @Query(value="BillPaymentType", encoded=true) int BillPaymentType
                //@Query(value="CustomerBillInfoList", encoded=true) List<CustomerBillInfoCls> CustomerBillInfoList
                //@Body List<CustomerBillInfoCls> CustomerBillInfoList
        );
        *//*@FormUrlEncoded
        @POST("MikroTik")
        Call<ResponseBody> MikroTik(
                @Query(value="CustomerMobileNumber", encoded=true) String CustomerMobileNumber,
                @Query(value="TotalBillAmount", encoded=true) int TotalBillAmount,
                @Query(value="BillPaymentType", encoded=true) int BillPaymentType,
                @Field("CustomerBillInfoList")   List<CustomerBillInfoCls> CustomerBillInfoList

               *//**//* @Field("BillingId") int BillingId,
                @Field("BillngDate") String BillngDate,
                @Field("Billable") String Billable*//**//*


        );*//*
        //How to pass String to the api BY GET methord
        @GET("CustomerInfo")
        Call<ResponseBody> CustomerInfo(

                @Query(value="CustomerMobileNumber", encoded=true) String CustomerMobileNumber,
                @Query(value="CustomerEmail", encoded=true) String CustomerEmail
        );

        //Get Customer Bill info

        @GET("CustomerBillInfo")
        Call<ResponseBody> CustomerBillInfo(

                @Query(value="CustomerMobileNumber", encoded=true) String CustomerMobileNumber
               *//* @Field("id") int id,
                @Field("Details") String Details,
                @Field("Amount") String Amount*//*
        );

        @FormUrlEncoded
        @POST("CustomerPayments")
        Call<ResponseBody> CustomerPayments(

                @Field("TotalPayment") int TotalPayment
        );

        @FormUrlEncoded
        @POST("RegisterUser")
        Call<ResponseBody> createRegisterUser(
                @Field("mobileNo") String mobileNo,
                @Field("password") String password,
                @Field("name") String name,
                @Field("userType") int userType,

                @Field("address") String address,
                @Field("division_code") int division_code,
                @Field("zila_code") int zila_code,
                @Field("upazila_code") int upazila_code,
                @Field("paurasava_code") int paurasava_code,
                @Field("union_code") int union_code

        );

        @FormUrlEncoded
        @POST("SoilTestResult")
        Call<ResponseBody> createSoilTestResult(
                @Field("mobile_no") String mobile_no,
                @Field("ph") String ph,
                @Field("carbon") String carbon,
                @Field("magnesium") String magnesium,
                @Field("calcium") String calcium,
                @Field("sulphur") String sulphur,
                @Field("phosphorus") String phosphorus,
                @Field("nitrogen") String nitrogen,
                @Field("is_viewed") int is_viewed


        );
*/
        /*@FormUrlEncoded
        @GET("Division")
        Call<List<Division>> getDivision();*/

        /*@PUT("update/{id}")
        Call<User> updateUser(@Path("id") int id, @Body User user);

        @DELETE("delete/{id}")
        Call<User> deleteUser(@Path("id") int id);*/

}
