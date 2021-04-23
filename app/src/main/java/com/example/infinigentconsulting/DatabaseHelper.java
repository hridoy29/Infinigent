package com.example.infinigentconsulting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "qt_infinigentdb.db";


    /*--Table Name TRN_Number --*/
    public static final String TABLE_NAME_TRN_Number = "TRN_Number";
    public static final String TRN_Number_COL_1 = "Id";
    public static final String TRN_Number_COL_2 = "Number";

    /*--Table Name LU_AIC --*/
    public static final String TABLE_NAME_LU_AIC = "LU_AIC";
    public static final String LU_AIC_COL_1 = "Id";
    public static final String LU_AIC_COL_2 = "Name";
    public static final String LU_AIC_COL_3 = "IsActive";

    /*--Table Name LU_ASM --*/
    public static final String TABLE_NAME_LU_ASM = "LU_ASM";
    public static final String LU_ASM_COL_1 = "Id";
    public static final String LU_ASM_COL_2 = "Name";
    public static final String LU_ASM_COL_3 = "IsActive";


    /*--Table Name LU_ChallanType--*/
    public static final String TABLE_NAME_LU_ChallanType = "LU_ChallanType";
    public static final String LU_ChallanType_COL_1 = "Id";
    public static final String LU_ChallanType_COL_2 = "Name";

    /*--Table Name LU_CommentsType --*/
    public static final String TABLE_NAME_LU_CommentsType = "LU_CommentsType";
    public static final String LU_CommentsType_COL_1 = "Id";
    public static final String LU_CommentsType_COL_2 = "CommentsType";

    /*--Table Name LU_Comments --*/
    public static final String TABLE_NAME_LU_Commnets = "LU_Commnets";
    public static final String LU_Commnets_COL_1 = "Id";
    public static final String LU_Commnets_COL_2 = "CommentsTypeId";
    public static final String LU_Commnets_COL_3 = "Comments";

    /*--Table Name LU_DeviceInfo --*/
    public static final String TABLE_NAME_LU_DeviceInfo = "LU_DeviceInfo";
    public static final String LU_DeviceInfo_COL_1 = "Id";
    public static final String LU_DeviceInfo_COL_2 = "Name";
    public static final String LU_DeviceInfo_COL_3 = "UniqueNumber";

    /*--Table Name LU_DistributorDetails --*/
    public static final String TABLE_NAME_LU_DistributorDetails = "LU_DistributorDetails";
    public static final String LU_DistributorDetails_COL_1 = "Id";
    public static final String LU_DistributorDetails_COL_2 = "Name";
    public static final String LU_DistributorDetails_COL_3 = "IsActive";

    /*--Table Name LU_Employee --*/
    public static final String TABLE_NAME_LU_Employee = "LU_Employee";
    public static final String LU_Employee_COL_1 = "Id";
    public static final String LU_Employee_COL_2 = "Name";
    public static final String LU_Employee_COL_3 = "EmployeeTypeId";

    /*--Table Name LU_OutletType --*/
    public static final String TABLE_NAME_LU_OutletType = "LU_OutletType";
    public static final String LU_OutletType_COL_1 = "Id";
    public static final String LU_OutletType_COL_2 = "Name";

    /*--Table Name LU_SchemeMediaType --*/
    public static final String TABLE_NAME_LU_SchemeMediaType = "LU_SchemeMediaType";
    public static final String LU_SchemeMediaType_COL_1 = "Id";
    public static final String LU_SchemeMediaType_COL_2 = "Name";

    /*--Table Name LU_SchemeName --*/
    public static final String TABLE_NAME_LU_SchemeName = "LU_SchemeName";
    public static final String LU_SchemeNameType_COL_1 = "Id";
    public static final String LU_SchemeNameType_COL_2 = "Name";
    public static final String LU_SchemeNameType_COL_3 = "IsActive";

    /*--Table Name LU_User--*/
    public static final String TABLE_NAME_LU_User = "LU_User";
    public static final String LU_User_COL_1 = "Id";
    public static final String LU_User_COL_2 = "Name";
    public static final String LU_User_COL_3 = "Email";
    public static final String LU_User_COL_4 = "MobileNo";
    public static final String LU_User_COL_5 = "Password";
    public static final String LU_User_COL_6 = "IsActive";

    /*--Table Name TRN_SchemeAuditChild--*/
    public static final String TABLE_NAME_TRN_SchemeAuditChild = "TRN_SchemeAuditChild";
    public static final String TRN_SchemeAuditChild_COL_1 = "Id";
    public static final String TRN_SchemeAuditChild_COL_2 = "Number";
    public static final String TRN_SchemeAuditChild_COL_3 = "ImageLocation";
    public static final String TRN_SchemeAuditChild_COL_4 = "IsSignature";

    /*--Table Name TRN_SchemeAuditParent--*/
    public static final String TABLE_NAME_TRN_SchemeAuditParent = "TRN_SchemeAuditParent";
    public static final String TRN_SchemeAuditParent_COL_1 = "Id";
    public static final String TRN_SchemeAuditParent_COL_2 = "Number";
    public static final String TRN_SchemeAuditParent_COL_3 = "UserId";
    public static final String TRN_SchemeAuditParent_COL_4 = "OutlateName";
    public static final String TRN_SchemeAuditParent_COL_5 = "Date";
    public static final String TRN_SchemeAuditParent_COL_6 = "IsKnowenAboutScheme";
    public static final String TRN_SchemeAuditParent_COL_7 = "SchemeDetails";
    public static final String TRN_SchemeAuditParent_COL_8 = "SchemeMediaTypeId";
    public static final String TRN_SchemeAuditParent_COL_9 = "IsFacilitatedByScheme";
    public static final String TRN_SchemeAuditParent_COL_10 = "DateOfScheme";
    public static final String TRN_SchemeAuditParent_COL_11 = "IsWrittenRecordAvailable";
    public static final String TRN_SchemeAuditParent_COL_12 = "LatestChallanDate";
    public static final String TRN_SchemeAuditParent_COL_13 = "ChallanAmount";
    public static final String TRN_SchemeAuditParent_COL_14 = "DoesGotAnyChallan";
    public static final String TRN_SchemeAuditParent_COL_15 = "ChallanTypeId";
    public static final String TRN_SchemeAuditParent_COL_16 = "DoesExpiredProductAvailable";
    public static final String TRN_SchemeAuditParent_COL_17 = "DoesSatisfiedWithSallesOfficer";
    public static final String TRN_SchemeAuditParent_COL_18 = "DoesSatisfiedWithProductOrderAndService";
    public static final String TRN_SchemeAuditParent_COL_19 = "SallesOfficerVisitingDay";
    public static final String TRN_SchemeAuditParent_COL_20 = "DoesGotLatestDiscountOffer";
    public static final String TRN_SchemeAuditParent_COL_21 = "WillGetAnyDiscountOfferFromDistributor";
    public static final String TRN_SchemeAuditParent_COL_22 = "DoesCocaColaLabelAvailable";
    public static final String TRN_SchemeAuditParent_COL_23 = "IsGccCodeAvailable";
    public static final String TRN_SchemeAuditParent_COL_24 = "CommentsType";
    public static final String TRN_SchemeAuditParent_COL_25 = "Comments";
    public static final String TRN_SchemeAuditParent_COL_26 = "CommentDetails";
    public static final String TRN_SchemeAuditParent_COL_27 = "CreatorId";
    public static final String TRN_SchemeAuditParent_COL_28 = "CreationDate";
    public static final String TRN_SchemeAuditParent_COL_29 = "ModifierId";
    public static final String TRN_SchemeAuditParent_COL_30 = "ModificationDate";


    /*--Table Name TRN_SchemeAuditShopDetails--*/
    public static final String TABLE_NAME_TRN_SchemeAuditShopDetails = "TRN_SchemeAuditShopDetails";
    public static final String TRN_SchemeAuditShopDetails_COL_1 = "Id";
    public static final String TRN_SchemeAuditShopDetails_COL_2 = "Number";
    public static final String TRN_SchemeAuditShopDetails_COL_3 = "OutlateName";
    public static final String TRN_SchemeAuditShopDetails_COL_4 = "GccCode";
    public static final String TRN_SchemeAuditShopDetails_COL_5 = "RetailSellerName";
    public static final String TRN_SchemeAuditShopDetails_COL_6 = "MobileNumber";
    public static final String TRN_SchemeAuditShopDetails_COL_7 = "OutlateTypeId";
    public static final String TRN_SchemeAuditShopDetails_COL_8 = "VisitedDate";
    public static final String TRN_SchemeAuditShopDetails_COL_9 = "DistributorName";
    public static final String TRN_SchemeAuditShopDetails_COL_10 = "AsmId";
    public static final String TRN_SchemeAuditShopDetails_COL_11 = "AicId";
    public static final String TRN_SchemeAuditShopDetails_COL_12 = "OutlateAddress";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table " + TABLE_NAME_TRN_Number + " (Id INTEGER PRIMARY KEY AUTOINCREMENT,Number TEXT)");
            db.execSQL("create table " + TABLE_NAME_LU_User + " (Id INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Email TEXT,MobileNo TEXT,Password TEXT,IsActive NUMERIC)");
            db.execSQL("CREATE TABLE " + TABLE_NAME_TRN_SchemeAuditChild + " (Id INTEGER PRIMARY KEY AUTOINCREMENT,Number TEXT, ImageLocation  blob,IsSignature NUMERIC)");
            db.execSQL("CREATE TABLE " + TABLE_NAME_LU_DistributorDetails + " (Id INTEGER ,Name TEXT, IsActive NUMERIC)");
            db.execSQL("CREATE TABLE " + TABLE_NAME_LU_SchemeName + " (Id INTEGER ,Name TEXT, IsActive NUMERIC)");
            db.execSQL("CREATE TABLE " + TABLE_NAME_LU_AIC + " (Id INTEGER ,Name TEXT, IsActive NUMERIC)");
            db.execSQL("CREATE TABLE " + TABLE_NAME_LU_ASM + " (Id INTEGER ,Name TEXT, IsActive NUMERIC)");
            db.execSQL("CREATE TABLE " + TABLE_NAME_LU_CommentsType + " (Id INTEGER ,CommentsType TEXT)");
            db.execSQL("CREATE TABLE " + TABLE_NAME_LU_Commnets + " (Id INTEGER ,CommentsTypeId TEXT, Comments TEXT)");
            db.execSQL("CREATE TABLE " + "TRN_SchemeAuditShopDetails" + " (Id INTEGER PRIMARY KEY AUTOINCREMENT,Number TEXT, OutlateName  TEXT,GccCode INT," +
                    "RetailSellerName TEXT,MobileNumber TEXT,OutlateTypeId INT," +
                    "VisitedDate NUMERIC,DistributorName TEXT,AicId INT,AsmId INT,OutlateAddress TEXT)");
            db.execSQL("CREATE TABLE " + "TRN_SchemeAuditParent" + " (Id INTEGER PRIMARY KEY AUTOINCREMENT,Number TEXT" +
                    ",UserId INT,OutlateName TEXT,Date NUMERIC,IsKnowenAboutScheme INT,SchemeDetails TEXT,SchemeMediaTypeId INT," +
                    "IsFacilitatedByScheme INT,DateOfScheme NUMERIC,IsWrittenRecordAvailable INT,LatestChallanDate NUMERIC," +
                    "ChallanAmount TEXT,DoesGotAnyChallan INT,ChallanTypeId INT,DoesExpiredProductAvailable INT," +
                    "DoesSatisfiedWithSallesOfficer INT,DoesSatisfiedWithProductOrderAndService INT,SallesOfficerVisitingDay INT," +
                    "DoesGotLatestDiscountOffer INT,WillGetAnyDiscountOfferFromDistributor INT,DoesCocaColaLabelAvailable INT,IsGccCodeAvailable INT," +
                    "CommentsType INT,Comments INT,CommentDetails TEXT,CreatorId INT,CreationDate NUMERIC,ModifierId INT,ModificationDate NUMERIC)");
        } catch (Exception exception) {
            throw exception;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRN_Number);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LU_User);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRN_SchemeAuditChild);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LU_DistributorDetails);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LU_SchemeName);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LU_AIC);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LU_ASM);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LU_CommentsType);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LU_Commnets);
            db.execSQL("DROP TABLE IF EXISTS " + "TRN_SchemeAuditShopDetails");
            db.execSQL("DROP TABLE IF EXISTS " + "TRN_SchemeAuditParent");
        } catch (SQLiteException exception) {
            String str = exception.getMessage();
        }
        onCreate(db);
    }


    public boolean addData(String Number, byte[] img, boolean isSignature) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRN_SchemeAuditChild_COL_2, Number);
        contentValues.put(TRN_SchemeAuditChild_COL_3, img);
        contentValues.put(TRN_SchemeAuditChild_COL_4, isSignature);
        boolean Isresult = false;
        try {
            db.beginTransaction();
            long result = db.insert(TABLE_NAME_TRN_SchemeAuditChild, null, contentValues);
            db.setTransactionSuccessful();
            Isresult = result != -1;
        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }
        return Isresult;

    }

    // INSERT Number LIST IN SQLITE DATABASE
    public boolean insertNumberList(Integer Id, String Number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRN_Number_COL_1, Id);
        contentValues.put(TRN_Number_COL_2, Number);

        boolean Isresult = false;
        try {
            db.beginTransaction();
            long result = db.insert(TABLE_NAME_TRN_Number, null, contentValues);
            db.setTransactionSuccessful();
            Isresult = result != -1;
        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }
        return Isresult;
    }

    // INSERT DISTRIBUTOR LIST IN SQLITE DATABASE
    public boolean insertDistributorList(Integer Id, String Name, boolean IsActive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LU_DistributorDetails_COL_1, Id);
        contentValues.put(LU_DistributorDetails_COL_2, Name);
        contentValues.put(LU_DistributorDetails_COL_3, IsActive);
        boolean Isresult = false;
        try {
            db.beginTransaction();
            long result = db.insert(TABLE_NAME_LU_DistributorDetails, null, contentValues);
            db.setTransactionSuccessful();
            Isresult = result != -1;
        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }
        return Isresult;
    }

    // INSERT SCHEME NAME LIST IN SQLITE DATABASE
    public boolean insertSchemeNameList(Integer Id, String Name, boolean IsActive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LU_SchemeNameType_COL_1, Id);
        contentValues.put(LU_SchemeNameType_COL_2, Name);
        contentValues.put(LU_SchemeNameType_COL_3, IsActive);
        boolean Isresult = false;
        try {
            db.beginTransaction();
            long result = db.insert(TABLE_NAME_LU_SchemeName, null, contentValues);
            db.setTransactionSuccessful();
            Isresult = result != -1;
        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }
        return Isresult;
    }

    // INSERT AIC LIST IN SQLITE DATABASE
    public boolean insertAICList(Integer Id, String Name, boolean IsActive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LU_AIC_COL_1, Id);
        contentValues.put(LU_AIC_COL_2, Name);
        contentValues.put(LU_AIC_COL_3, IsActive);
        boolean Isresult = false;
        try {
            db.beginTransaction();
            long result = db.insert(TABLE_NAME_LU_AIC, null, contentValues);
            db.setTransactionSuccessful();
            Isresult = result != -1;
        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }
        return Isresult;
    }

    // INSERT ASM LIST IN SQLITE DATABASE
    public boolean insertASMList(Integer Id, String Name, boolean IsActive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LU_ASM_COL_1, Id);
        contentValues.put(LU_ASM_COL_2, Name);
        contentValues.put(LU_ASM_COL_3, IsActive);
        boolean Isresult = false;
        try {
            db.beginTransaction();
            long result = db.insert(TABLE_NAME_LU_ASM, null, contentValues);
            db.setTransactionSuccessful();
            Isresult = result != -1;
        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }
        return Isresult;
    }

    // INSERT CommentsType LIST IN SQLITE DATABASE
    public boolean insertCommentsTypeList(Integer Id, String CommentsType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LU_CommentsType_COL_1, Id);
        contentValues.put(LU_CommentsType_COL_2, CommentsType);
        boolean Isresult = false;
        try {
            db.beginTransaction();
            long result = db.insert(TABLE_NAME_LU_CommentsType, null, contentValues);
            db.setTransactionSuccessful();
            Isresult = result != -1;
        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }
        return Isresult;
    }

    // INSERT Comments LIST IN SQLITE DATABASE
    public boolean insertCommentsList(Integer Id, Integer CommentsTypeId, String Comments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LU_Commnets_COL_1, Id);
        contentValues.put(LU_Commnets_COL_2, CommentsTypeId);
        contentValues.put(LU_Commnets_COL_3, Comments);
        boolean Isresult = false;
        try {
            db.beginTransaction();
            long result = db.insert(TABLE_NAME_LU_Commnets, null, contentValues);
            db.setTransactionSuccessful();
            Isresult = result != -1;
        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }
        return Isresult;
    }

    // INSERT USER DATE IN SQLITE DATABASE
    public boolean insertUserList(Integer Id,String Name, String Email, String MobileNo, String Password, boolean IsActive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LU_User_COL_1, Id);
        contentValues.put(LU_User_COL_2, Name);
        contentValues.put(LU_User_COL_3, Email);
        contentValues.put(LU_User_COL_4, MobileNo);
        contentValues.put(LU_User_COL_5, Password);
        contentValues.put(LU_User_COL_6, IsActive);
        boolean Isresult = false;
        try {
            db.beginTransaction();
            long result = db.insert(TABLE_NAME_LU_User, null, contentValues);
            db.setTransactionSuccessful();
            Isresult = result != -1;
        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }
        return Isresult;
    }
    public Cursor getUserId(String Email, String Password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String user = "select " + LU_User_COL_1 + " from " + TABLE_NAME_LU_User + " Where " + LU_User_COL_3 + " = " + "'" + Email + "' and " + LU_User_COL_5 + " = '" + Password + "'";
        Cursor res = db.rawQuery(user, null);
        return res ;

    }
    // INSERT Scheme Audit Shop Details DATE IN SQLITE DATABASE
    public boolean insertSchemeAuditShopDetails(SchemeAuditShopDetails SchemeAuditShopDetails, String Number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TRN_SchemeAuditShopDetails_COL_2, Number);
        contentValues.put(TRN_SchemeAuditShopDetails_COL_3, SchemeAuditShopDetails.OutlateName);
        contentValues.put(TRN_SchemeAuditShopDetails_COL_4, SchemeAuditShopDetails.GccCode);
        contentValues.put(TRN_SchemeAuditShopDetails_COL_5, SchemeAuditShopDetails.RetailSellerName);
        contentValues.put(TRN_SchemeAuditShopDetails_COL_6, SchemeAuditShopDetails.MobileNumber);
        contentValues.put(TRN_SchemeAuditShopDetails_COL_7, SchemeAuditShopDetails.OutlateTypeId);
        contentValues.put(TRN_SchemeAuditShopDetails_COL_8, String.valueOf(SchemeAuditShopDetails.VisitedDate));
        contentValues.put(TRN_SchemeAuditShopDetails_COL_9, SchemeAuditShopDetails.DistributorName);
        contentValues.put(TRN_SchemeAuditShopDetails_COL_10, SchemeAuditShopDetails.AsmId);
        contentValues.put(TRN_SchemeAuditShopDetails_COL_11, SchemeAuditShopDetails.AicId);
        contentValues.put(TRN_SchemeAuditShopDetails_COL_12, SchemeAuditShopDetails.OutlateAddress);

        boolean Isresult = false;
        try {
            db.beginTransaction();
            long result = db.insert("TRN_SchemeAuditShopDetails", null, contentValues);
            db.setTransactionSuccessful();
            Isresult = result != -1;
        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }


        return Isresult;
    }

    // INSERT Scheme Audit Parent DATE IN SQLITE DATABASE
    public boolean insertSchemeAuditParent(SchemeAuditParent SchemeAuditParent, String Number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRN_SchemeAuditParent_COL_2, Number);
        contentValues.put(TRN_SchemeAuditParent_COL_3, SchemeAuditParent.UserId);
        contentValues.put(TRN_SchemeAuditParent_COL_4, SchemeAuditParent.OutlateName);
        contentValues.put(TRN_SchemeAuditParent_COL_5, String.valueOf(SchemeAuditParent.Date));
        contentValues.put(TRN_SchemeAuditParent_COL_6, SchemeAuditParent.IsKnowenAboutScheme);
        contentValues.put(TRN_SchemeAuditParent_COL_7, SchemeAuditParent.SchemeDetails);
        contentValues.put(TRN_SchemeAuditParent_COL_8, SchemeAuditParent.SchemeMediaTypeId);
        contentValues.put(TRN_SchemeAuditParent_COL_9, SchemeAuditParent.IsFacilitatedByScheme);
        contentValues.put(TRN_SchemeAuditParent_COL_10, String.valueOf(SchemeAuditParent.DateOfScheme));
        contentValues.put(TRN_SchemeAuditParent_COL_11, SchemeAuditParent.IsWrittenRecordAvailable);
        contentValues.put(TRN_SchemeAuditParent_COL_12, String.valueOf(SchemeAuditParent.LatestChallanDate));
        contentValues.put(TRN_SchemeAuditParent_COL_13, SchemeAuditParent.ChallanAmount);
        contentValues.put(TRN_SchemeAuditParent_COL_14, SchemeAuditParent.DoesGotAnyChallan);
        contentValues.put(TRN_SchemeAuditParent_COL_15, SchemeAuditParent.ChallanTypeId);
        contentValues.put(TRN_SchemeAuditParent_COL_16, SchemeAuditParent.DoesExpiredProductAvailable);
        contentValues.put(TRN_SchemeAuditParent_COL_17, SchemeAuditParent.DoesSatisfiedWithSallesOfficer);
        contentValues.put(TRN_SchemeAuditParent_COL_18, SchemeAuditParent.DoesSatisfiedWithProductOrderAndService);
        contentValues.put(TRN_SchemeAuditParent_COL_19, SchemeAuditParent.SallesOfficerVisitingDay);
        contentValues.put(TRN_SchemeAuditParent_COL_20, SchemeAuditParent.DoesGotLatestDiscountOffer);
        contentValues.put(TRN_SchemeAuditParent_COL_21, SchemeAuditParent.WillGetAnyDiscountOfferFromDistributor);
        contentValues.put(TRN_SchemeAuditParent_COL_22, SchemeAuditParent.DoesCocaColaLabelAvailable);
        contentValues.put(TRN_SchemeAuditParent_COL_23, SchemeAuditParent.IsGccCodeAvailable);
        contentValues.put(TRN_SchemeAuditParent_COL_24, SchemeAuditParent.CommentsType);
        contentValues.put(TRN_SchemeAuditParent_COL_25, SchemeAuditParent.Comments);
        contentValues.put(TRN_SchemeAuditParent_COL_26, SchemeAuditParent.CommentDetails);
        contentValues.put(TRN_SchemeAuditParent_COL_27, SchemeAuditParent.CreatorId);
        contentValues.put(TRN_SchemeAuditParent_COL_28, String.valueOf(SchemeAuditParent.CreationDate));
        contentValues.put(TRN_SchemeAuditParent_COL_29, SchemeAuditParent.ModifierId);
        contentValues.put(TRN_SchemeAuditParent_COL_30, String.valueOf(SchemeAuditParent.ModificationDate));

        boolean Isresult = false;
        try {
            db.beginTransaction();
            long result = db.insert("TRN_SchemeAuditParent", null, contentValues);
            db.setTransactionSuccessful();
            Isresult = result != -1;
        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }

        String userList = "select * from " + "TRN_SchemeAuditParent";
        Cursor res = db.rawQuery(userList, null);
        int ctn = res.getCount();
        return Isresult;
    }

    public boolean getUserValidation(String Email, String Password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String user = "select " + LU_User_COL_3 + " from " + TABLE_NAME_LU_User + " Where " + LU_User_COL_3 + " = " + "'" + Email + "' and " + LU_User_COL_5 + " = '" + Password + "'";
        //String user="select "+ LU_User_COL_3 +" from " + TABLE_NAME_LU_User + " Where "+ LU_User_COL_3 + " = " +""+ Email+" and "+ LU_User_COL_5 + " = "+ Password+"" ;

        Cursor res = db.rawQuery(user, null);
        int cnt = res.getCount();
        return res.getCount() != 0;

    }

    public Cursor getUserList() {
        SQLiteDatabase db = this.getWritableDatabase();
        String userList = "select * from " + TABLE_NAME_LU_User + " Where " + LU_User_COL_6 + " = '" + 1 + "'";
        Cursor res = db.rawQuery(userList, null);
        return res;
    }

    public Cursor getDistributorList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + LU_DistributorDetails_COL_2 + " from " + TABLE_NAME_LU_DistributorDetails, null);
        return res;
    }

//    public Cursor getAICNameList() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + TABLE_NAME_LU_AIC, null);
//        return res;
//    }

    public Cursor getAICNameList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + LU_AIC_COL_2 + " ||'('||" + LU_AIC_COL_1 + " ||')' from " + TABLE_NAME_LU_AIC, null);


        return res;
    }

//    public Cursor getASMNameList() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + TABLE_NAME_LU_ASM, null);
//        return res;
//    }

    public Cursor getASMNameList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + LU_ASM_COL_2 + " ||'('||" + LU_ASM_COL_1 + " ||')' from " + TABLE_NAME_LU_ASM, null);

        return res;
    }

    public Cursor getNumberList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_TRN_Number, null);

        return res;
    }

    public void updateNumberList(int Id, String Number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRN_Number_COL_2, Number);
        int i = db.update(TABLE_NAME_TRN_Number, contentValues, "Id = ?", new String[]{String.valueOf(Id)});
        int f = i;
    }

    //    public Cursor getCommentsTypeList() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + TABLE_NAME_LU_CommentsType, null);
//        return res;
//    }
    public Cursor getCommentsTypeList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + LU_CommentsType_COL_2 + " ||'('||" + LU_CommentsType_COL_1 + " ||')' from " + TABLE_NAME_LU_CommentsType, null);

        //Cursor res = db.rawQuery("select * from " + TABLE_NAME_LU_CommentsType, null);
        return res;
    }

    public Cursor getCommentsList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + LU_Commnets_COL_3 + " ||'(' ||" + LU_Commnets_COL_1 + " ||')' from " + TABLE_NAME_LU_Commnets, null);
        return res;
    }

    public Cursor getCommentListById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // String strrr="select "+LU_Commnets_COL_3 +" ||'('||"+ LU_Commnets_COL_1+" ||')' from "+ TABLE_NAME_LU_Commnets + " where "+ LU_Commnets_COL_2 + " = " + id ;
        Cursor res = db.rawQuery("SELECT Comments ||'('|| Id ||')' FROM LU_Commnets Where CommentsTypeId='" + id + "'", null);
        return res;
    }

    public Cursor getLatestNumberById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Number   FROM TRN_Number Where Id='" + id + "'", null);
        return res;
    }

    public Integer deleteData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Integer count = 0;
        try {
            db.beginTransaction();
            db.delete(TABLE_NAME_TRN_Number, null, null);
            count++;
            db.delete(TABLE_NAME_LU_User, null, null);
            count++;
            db.delete(TABLE_NAME_LU_AIC, null, null);
            count++;
            db.delete(TABLE_NAME_LU_ASM, null, null);
            count++;

            db.delete(TABLE_NAME_LU_CommentsType, null, null);
            count++;
            db.delete(TABLE_NAME_LU_Commnets, null, null);
            count++;
            db.delete(TABLE_NAME_LU_DistributorDetails, null, null);
            count++;
            db.delete(TABLE_NAME_LU_SchemeName, null, null);
            count++;
            db.setTransactionSuccessful();

        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }
        if (count == 8) {
            return count;
        } else {
            return 0;
        }
    }

    public Integer deleteTRNData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Integer count = 0;
        try {
            db.beginTransaction();
            // db.delete(TABLE_NAME_TRN_SchemeAuditShopDetails, "1= ?", new String[]{"1"});
            db.delete(TABLE_NAME_TRN_SchemeAuditShopDetails, null, null);
            count++;
            db.delete(TABLE_NAME_TRN_SchemeAuditParent, null, null);
            count++;
            db.delete(TABLE_NAME_TRN_SchemeAuditChild, null, null);
            count++;
            db.setTransactionSuccessful();

        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }
        if (count == 3) {
            return count;
        } else {
            return 0;
        }
    }

    public boolean isSchemeDataAvailableAtLocalDB() {
        boolean _isSchemeDataAvailableAtLocalDB = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ImageData, AuditShopDetailsData, AuditShopParentData;
        ImageData = getImageData();
        AuditShopDetailsData = getAuditShopDetailsData();
        AuditShopParentData = getAuditShopParentData();
        if (ImageData.getCount() != 0) {
            _isSchemeDataAvailableAtLocalDB = true;
        }
        if (AuditShopDetailsData.getCount() != 0) {
            _isSchemeDataAvailableAtLocalDB = true;
        }
        if (AuditShopParentData.getCount() != 0) {
            _isSchemeDataAvailableAtLocalDB = true;
        }
        return _isSchemeDataAvailableAtLocalDB;

    }

    public Cursor getImageData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_TRN_SchemeAuditChild, null);
        return res;
    }

    public Cursor getAuditShopDetailsData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + "TRN_SchemeAuditShopDetails", null);
        return res;
    }

    public Cursor getAuditShopParentData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + "TRN_SchemeAuditParent", null);
        return res;
    }

    public long getAuditShopDetailsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME_TRN_SchemeAuditShopDetails);
        db.close();
        return count;
    }

    public Cursor getUserStatusList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + TRN_SchemeAuditShopDetails_COL_2 + "," + TRN_SchemeAuditShopDetails_COL_3 + "," + TRN_SchemeAuditShopDetails_COL_6 + "  from " + "TRN_SchemeAuditShopDetails", null);
        return res;
    }
    public Cursor getImageStatusList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + TRN_SchemeAuditChild_COL_2 + ", COUNT(" + TRN_SchemeAuditChild_COL_3 + ")  from " + "TRN_SchemeAuditChild GROUP BY "+TRN_SchemeAuditChild_COL_2, null);
        return res;
    }


    public void deleteSchemeAuditChildRowData(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete(TABLE_NAME_TRN_SchemeAuditChild, "Id=?", new String[]{Integer.toString(id)});
            db.setTransactionSuccessful();

        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }

    }

    public void deleteSchemeAuditParentRowData(String num) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete(TABLE_NAME_TRN_SchemeAuditParent, "Number=?", new String[]{num});

            db.setTransactionSuccessful();

        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }

    }

    public void deleteSchemeAuditShopRowData(String num) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete(TABLE_NAME_TRN_SchemeAuditShopDetails, "Number=?", new String[]{num});

            db.setTransactionSuccessful();

        } catch (Exception exception) {

            db.endTransaction();
        } finally {
            db.endTransaction();
        }

    }

    public Cursor getSchemeNameList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + LU_SchemeNameType_COL_2 + " from " + TABLE_NAME_LU_SchemeName, null);
        return res;
    }
}