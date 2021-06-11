package com.example.infinigentconsulting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    private RecyclerView recyclerView;
    private CardViewAdapter adapter;
    private List<CardElement> cardElements;
    private boolean IsInternetAvaiable = false;
    private boolean IsNumberFromDBFound=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        myDb = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recycler_view);
        cardElements = new ArrayList<CardElement>();
        adapter = new CardViewAdapter(this, cardElements, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent i;
                switch (position) {
                    case 0:
                        try {
                            i = new Intent(MainActivity.this, OutletDetailsActivity.class);
                            i.putExtra("activity", "MainActivity");
                            startActivity(i);
                        } catch (Exception ex) {
                            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        } finally {
                            break;
                        }
                    case 1:
//                    try {
//                        i = new Intent(MainActivity.this, HygieneAuditActivity.class);
//                        startActivity(i);
//                    } catch (Exception ex) {
//                        Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
//                    } finally {
//                        break;
//                    }
                        break;

                    case 2:
                        try {
                            final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Dialog_Alert)
                                    .setTitle("SYNC")
                                    .setMessage("Do You Want To Sync With Database?")
                                    .setPositiveButton("Yes", null)
                                    .setNegativeButton("No", null)
                                    .show();
                            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            positiveButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    if (internetIsConnected() == true) {
                                        try {
                                            if (myDb.isSchemeDataAvailableAtLocalDB() == true) {
                                                Toast.makeText(MainActivity.this, "Sending  Information To Main DB. Please wait...", Toast.LENGTH_LONG).show();
                                                try {
                                                    sentLatestNumberByApi();
                                                    SentImageByApi();
                                                    AuditShopDetailsDataByApi();
                                                    SchemeAuditParentDataByApi();
                                                    syncDataToLocalDBWithOutNumberTable();
                                                }
                                                catch (Exception ex) {
                                                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                            else
                                            {
                                                try {
                                                    syncDataToLocalDBWithNumberTable();
                                                    syncDataToLocalDBWithOutNumberTable();
                                                }
                                                catch (Exception ex) {
                                                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                                                }

                                            }
                                            Toast.makeText(MainActivity.this, "Collecting Information. Please wait...", Toast.LENGTH_LONG).show();

                                        } catch (Exception ex) {
                                            Toast.makeText(MainActivity.this, "Internet Is Not Available,Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "Internet Is Not Available,Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } catch (Exception ex) {
                            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        } finally {
                            break;
                        }
                    case 3:
                        try {
                            i = new Intent(MainActivity.this, StatusActivity.class);
                            startActivity(i);
                        } catch (Exception ex) {
                            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        } finally {
                            break;
                        }
                    case 4:
                        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Dialog_Alert)
                                .setTitle("DELETE")
                                .setMessage("Do You Want To Delete Schemed Data?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                try {
                                    if (myDb.isSchemeDataAvailableAtLocalDB() == true) {
                                        Toast.makeText(MainActivity.this, " Please wait...", Toast.LENGTH_LONG).show();
                                        try {
                                            myDb.deleteTRNData();
                                        } catch (Exception ex) {
                                            Toast.makeText(MainActivity.this, "Internet Is Not Available,Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (Exception ex) {
                                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        break;
                }
            }
        });

        try {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            prepareAlbums();
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        //ImageView of Cover Photo
        try {
            Glide.with(this).load(R.drawable.banner).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String GetPreviousNumber(int Id) {
        Cursor cursor = myDb.getNumberList();
        String Number = "";
        if (cursor.getCount() > 0) {
            Cursor _cur = myDb.getLatestNumberById(Id);
            while (_cur.moveToNext()) {
                Number = _cur.getString(0);
            }
        }
        return Number;
    }

    private void sentLatestNumberByApi() {
        final String MY_PREFS_NAME = "DeviceNumber";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int Id = prefs.getInt("Id", 0);
        String oldNumber = GetPreviousNumber(Id);

        if (oldNumber.length() == 0) {
            syncDataToLocalDBWithNumberTable();
            return;
        }
        else {
            _TestReplaceOldNumberInSQLdb(Id, oldNumber);
        }

    }


    private void SentImageByApi() {
        Cursor getImageBrowsingActivityDataListFromLocalDB = myDb.getImageData();
        if (getImageBrowsingActivityDataListFromLocalDB.getCount() == 0) {
            return;
        }

        while (getImageBrowsingActivityDataListFromLocalDB.moveToNext()) {
            _TestSchemeAuditChild(
                    getImageBrowsingActivityDataListFromLocalDB.getInt(0),
                    getImageBrowsingActivityDataListFromLocalDB.getString(1)
                    , getImageBrowsingActivityDataListFromLocalDB.getBlob(2)
                    , Boolean.parseBoolean(getImageBrowsingActivityDataListFromLocalDB.getString(3)));
        }

    }

    private void AuditShopDetailsDataByApi() {
        Cursor getAuditShopDetailsDataListFromLocalDB = myDb.getAuditShopDetailsData();
        if (getAuditShopDetailsDataListFromLocalDB.getCount() == 0) {
            return;
        }

        while (getAuditShopDetailsDataListFromLocalDB.moveToNext()) {
            _TestAuditShopDetailsData(getAuditShopDetailsDataListFromLocalDB.getInt(0)
                    , getAuditShopDetailsDataListFromLocalDB.getString(1)
                    , getAuditShopDetailsDataListFromLocalDB.getString(2)
                    , getAuditShopDetailsDataListFromLocalDB.getInt(3)
                    , getAuditShopDetailsDataListFromLocalDB.getString(4)
                    , getAuditShopDetailsDataListFromLocalDB.getString(5)
                    , getAuditShopDetailsDataListFromLocalDB.getString(6)
                    , getAuditShopDetailsDataListFromLocalDB.getString(7)
                    , getAuditShopDetailsDataListFromLocalDB.getString(8)
                    , getAuditShopDetailsDataListFromLocalDB.getInt(9)
                    , getAuditShopDetailsDataListFromLocalDB.getInt(10)
                    , getAuditShopDetailsDataListFromLocalDB.getString(11)
            );
        }

    }

    private void SchemeAuditParentDataByApi() {
        Cursor getAuditShopParentDataListFromLocalDB = myDb.getAuditShopParentData();
        if (getAuditShopParentDataListFromLocalDB.getCount() == 0) {
            return;
        }
        while (getAuditShopParentDataListFromLocalDB.moveToNext()) {
            _TestSchemeAuditParentData(getAuditShopParentDataListFromLocalDB.getInt(0)
                    , getAuditShopParentDataListFromLocalDB.getString(1)
                    , getAuditShopParentDataListFromLocalDB.getInt(2)
                    , getAuditShopParentDataListFromLocalDB.getString(3)
                    , getAuditShopParentDataListFromLocalDB.getString(4)
                    , getAuditShopParentDataListFromLocalDB.getInt(5)
                    , getAuditShopParentDataListFromLocalDB.getString(6)
                    , getAuditShopParentDataListFromLocalDB.getString(7)
                    , getAuditShopParentDataListFromLocalDB.getInt(8)
                    , getAuditShopParentDataListFromLocalDB.getString(9)
                    , getAuditShopParentDataListFromLocalDB.getInt(10)
                    , getAuditShopParentDataListFromLocalDB.getString(11)
                    , getAuditShopParentDataListFromLocalDB.getString(12)
                    , getAuditShopParentDataListFromLocalDB.getString(13)
                    , getAuditShopParentDataListFromLocalDB.getInt(14)
                    , getAuditShopParentDataListFromLocalDB.getInt(15)
                    , getAuditShopParentDataListFromLocalDB.getInt(16)
                    , getAuditShopParentDataListFromLocalDB.getInt(17)
                    , getAuditShopParentDataListFromLocalDB.getString(18)
                    , getAuditShopParentDataListFromLocalDB.getInt(19)
                    , getAuditShopParentDataListFromLocalDB.getInt(20)
                    , getAuditShopParentDataListFromLocalDB.getInt(21)
                    , getAuditShopParentDataListFromLocalDB.getInt(22)
                    , getAuditShopParentDataListFromLocalDB.getInt(23)
                    , getAuditShopParentDataListFromLocalDB.getInt(24)
                    , getAuditShopParentDataListFromLocalDB.getString(25)
                    , getAuditShopParentDataListFromLocalDB.getInt(26)
                    , getAuditShopParentDataListFromLocalDB.getString(27)
                    , getAuditShopParentDataListFromLocalDB.getInt(28)
                    , getAuditShopParentDataListFromLocalDB.getString(29)


            );
        }

    }

    private void _Test(String Number, byte[] Photo, boolean isSignature) {
        String encodedImage = Base64.encodeToString(Photo, Base64.DEFAULT);
        Test Test = new Test(1, Number, encodedImage);
        Call<Test> call = RetrofitClient
                .getInstance()
                .getApi()
                .Test(Test);
        call.enqueue(new Callback<Test>() {
            @Override
            public void onResponse(Call<Test> call, Response<Test> response) {
                Test s = response.body();
                if (s.Id != 0) {
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Test> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void syncDataToLocalDBWithNumberTable(){

        Integer Counter = myDb.deleteDataOnlyNumberTable();
        if (Counter == 1) {
            try {
                new GetNumberList().execute();
            }
            catch (Exception ex) {
                Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(MainActivity.this, "404 Server Error", Toast.LENGTH_LONG).show();
        }
    }
    private void syncDataToLocalDBWithOutNumberTable(){
        Integer Counter = myDb.deleteData();
        if (Counter == 7) {
            try {

                new GetListofUser().execute();
                new GetDistributorList().execute();
                new GetSchemeNameList().execute();
                new GetAICList().execute();
                new GetASMList().execute();
                new GetCommentsTypeList().execute();
                new GetCommentsList().execute();
            }
            catch (Exception ex) {
                Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(MainActivity.this, "404 Server Error", Toast.LENGTH_LONG).show();
        }
    }
    private void _TestReplaceOldNumberInSQLdb(int Id, String Number) {

        NumberClass _number = new NumberClass(Id, Number);
        Call<NumberClass> call = RetrofitClient
                .getInstance()
                .getApi()
                .Number(_number);
        call.enqueue(new Callback<NumberClass>() {
            @Override
            public void onResponse(Call<NumberClass> call, Response<NumberClass> response) {
                try {
                    NumberClass s = response.body();
                    if (s.Id != 0) {
                        syncDataToLocalDBWithNumberTable();
                        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_LONG).show();
                    } else {
                        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Dialog_Alert)
                                .setTitle("INTERNET CONNECTIVITY ERROR!!!")
                                .setMessage("Sync Again Please")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                try {
                                    sentLatestNumberByApi();
                                } catch (Exception ex) {
                                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                } catch (Exception ex) {
                    final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Dialog_Alert)
                            .setTitle("INTERNET CONNECTIVITY ERROR!!!")
                            .setMessage("Sync Again Please")
                            .setPositiveButton("Yes", null)
                            .setNegativeButton("No", null)
                            .show();
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            try {
                                sentLatestNumberByApi();
                            } catch (Exception ex) {
                                Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<NumberClass> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void _TestSchemeAuditChild(int Id, String Number, byte[] ImageLocation, boolean isSignature) {
        String encodedImage = Base64.encodeToString(ImageLocation, Base64.DEFAULT);
        SchemeAuditChild SchemeAuditChild = new SchemeAuditChild(Id, Number, encodedImage);
        Call<SchemeAuditChild> call = RetrofitClient
                .getInstance()
                .getApi()
                .SchemeAuditChild(SchemeAuditChild);
        call.enqueue(new Callback<SchemeAuditChild>() {
            @Override
            public void onResponse(Call<SchemeAuditChild> call, Response<SchemeAuditChild> response) {
                SchemeAuditChild s = response.body();
//                if (SchemeAuditChild.Id != 0) {
//                    Toast.makeText(MainActivity.this, "Image Successful", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Image Invalid", Toast.LENGTH_LONG).show();
//                }
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Image is successfully uploaded", Toast.LENGTH_LONG).show();
                    int id = SchemeAuditChild.Id;
                    try {
                        myDb.deleteSchemeAuditChildRowData(id);
                    } catch (Exception ex) {
                        Toast.makeText(MainActivity.this, "Internet Is Not Available,Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<SchemeAuditChild> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void _TestAuditShopDetailsData(int id, String number, String outlateName, int gccCode, String retailSellerName, String mobileNumber, String outlateTypeId, String visitedDate, String distributorName, int asmId, int aicId, String outlateAddress) {

        SchemeAuditShopDetails SchemeAuditShopDetails = new SchemeAuditShopDetails(id, number, outlateName, gccCode, retailSellerName, mobileNumber, outlateTypeId, stringToDate(visitedDate), distributorName, asmId, aicId, outlateAddress);
        Call<SchemeAuditShopDetails> call = RetrofitClient
                .getInstance()
                .getApi()
                .SchemeAuditShopDetails(SchemeAuditShopDetails);
        call.enqueue(new Callback<SchemeAuditShopDetails>() {
            @Override
            public void onResponse(Call<SchemeAuditShopDetails> call, Response<SchemeAuditShopDetails> response) {
                //  SchemeAuditShopDetails s = response.body();
//                if (schemeAuditShopDetails.Id != 0) {
//                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Invalid", Toast.LENGTH_LONG).show();
//                }


                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "SchemeAuditShopDetails is successfully uploaded", Toast.LENGTH_LONG).show();
                    String num = SchemeAuditShopDetails.Number;
                    try {
                        myDb.deleteSchemeAuditShopRowData(num);

                    } catch (Exception ex) {
                        Toast.makeText(MainActivity.this, "Internet Is Not Available,Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<SchemeAuditShopDetails> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void _TestSchemeAuditParentData(int id, String number, int userId, String outlateName, String Date, int isKnowenAboutScheme, String schemeDetails, String schemeMediaTypeId, int isFacilitatedByScheme, String dateOfScheme, int isWrittenRecordAvailable, String latestChallanDate, String challanAmount, String doesGotAnyChallan, int challanTypeId, int doesExpiredProductAvailable, int doesSatisfiedWithSallesOfficer, int doesSatisfiedWithProductOrderAndService, String sallesOfficerVisitingDay, int doesGotLatestDiscountOffer, int willGetAnyDiscountOfferFromDistributor, int doesCocaColaLabelAvailable, int isGccCodeAvailable, int commentsType, int comments, String commentDetails, int creatorId, String creationDate, int modifierId, String modificationDate) {

        SchemeAuditParent schemeAuditParent = new SchemeAuditParent(id, number, userId, outlateName, stringToDate(Date), isKnowenAboutScheme, schemeDetails, schemeMediaTypeId, isFacilitatedByScheme, dateOfScheme, isWrittenRecordAvailable, latestChallanDate, challanAmount, doesGotAnyChallan, challanTypeId, doesExpiredProductAvailable, doesSatisfiedWithSallesOfficer, doesSatisfiedWithProductOrderAndService, sallesOfficerVisitingDay, doesGotLatestDiscountOffer, willGetAnyDiscountOfferFromDistributor, doesCocaColaLabelAvailable, isGccCodeAvailable, commentsType, comments, commentDetails, creatorId, stringToDate(creationDate), modifierId, stringToDate(modificationDate));
        Call<SchemeAuditParent> call = RetrofitClient
                .getInstance()
                .getApi()
                .SchemeAuditParent(schemeAuditParent);
        call.enqueue(new Callback<SchemeAuditParent>() {
            @Override
            public void onResponse(Call<SchemeAuditParent> call, Response<SchemeAuditParent> response) {
               /* String s = response.body();
                if (response.errorBody().toString()== "Data Added Successfuly") {
                    Toast.makeText(MainActivity.this, "scheme Audit Parent Successful", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid", Toast.LENGTH_LONG).show();
                }*/

                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "SchemeAuditParent is successfully uploaded", Toast.LENGTH_LONG).show();
                    String num = schemeAuditParent.Number ;
                    try {
                        myDb.deleteSchemeAuditParentRowData(num);

                    } catch (Exception ex) {
                        Toast.makeText(MainActivity.this, "Internet Is Not Available,Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(Call<SchemeAuditParent> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            IsInternetAvaiable = (Runtime.getRuntime().exec(command).waitFor() == 0);
            return IsInternetAvaiable;
        } catch (Exception e) {
            return IsInternetAvaiable;
        }
    }

    // method for showing image and text in the cards
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.scheme_auditt,
                R.drawable.hygiene_audit,
                R.drawable.sync,
                R.drawable.status,
                R.drawable.clear_scheme};

        CardElement a = new CardElement("Scheme Audit", covers[0]);
        cardElements.add(a);

        a = new CardElement("Hygiene Audit", covers[1]);
        cardElements.add(a);

        a = new CardElement("Sync", covers[2]);
        cardElements.add(a);

        a = new CardElement("Status", covers[3]);
        cardElements.add(a);
        a = new CardElement("Clear Scheme", covers[4]);
        cardElements.add(a);


        adapter.notifyDataSetChanged();
    }

    /**
     * Converting dp to pixel
     */

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void AddUserList(ArrayList<UserClass> UserList) {

        for (UserClass item : UserList) {
            boolean isInserted = myDb.insertUserList(item.Id,item.Name, item.Email, item.MobileNo, item.Password, item.IsActive);
        }
        Cursor res = myDb.getUserList();
        if (res.getCount() != 0) {
            Toast.makeText(MainActivity.this, "User List Inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
        }

    }

    public void AddDistributorList(ArrayList<GenericClass> DistributorList) {

        for (GenericClass item : DistributorList) {
            boolean isInserted = myDb.insertDistributorList(item.Id, item.Name, item.IsActive);
        }
        Cursor res = myDb.getDistributorList();
        if (res.getCount() != 0) {
            Toast.makeText(MainActivity.this, "Distributor List Inserted", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

    public void AddSchemeNameList(ArrayList<GenericClass> SchemeNameList) {

        for (GenericClass item : SchemeNameList) {
            boolean isInserted = myDb.insertSchemeNameList(item.Id, item.Name, item.IsActive);
        }
        Cursor res = myDb.getSchemeNameList();
        if (res.getCount() != 0) {
            Toast.makeText(MainActivity.this, "Scheme Name List Inserted", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

    public void AddAICList(ArrayList<GenericClass> AICList) {
        for (GenericClass item : AICList) {
            boolean isInserted = myDb.insertAICList(item.Id, item.Name, item.IsActive);
        }
        Cursor res = myDb.getAICNameList();
        if (res.getCount() != 0) {
            Toast.makeText(MainActivity.this, "AIC List Inserted", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

    public void AddASMList(ArrayList<GenericClass> ASMList) {
        for (GenericClass item : ASMList) {
            boolean isInserted = myDb.insertASMList(item.Id, item.Name, item.IsActive);
        }
        Cursor res = myDb.getASMNameList();
        if (res.getCount() != 0) {
            Toast.makeText(MainActivity.this, "ASM List Inserted", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

    public void AddNumberList(ArrayList<NumberClass> NumberList) {
        for (NumberClass item : NumberList) {
            boolean isInserted = myDb.insertNumberList(item.Id, item.Number);
        }
        Cursor res = myDb.getNumberList();
        if (res.getCount() != 0) {
            Toast.makeText(MainActivity.this, "Number List Inserted", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

    public void AddCommentsTypeList(ArrayList<CommentsTypeClass> CommentsTypeList) {

        for (CommentsTypeClass item : CommentsTypeList) {
            boolean isInserted = myDb.insertCommentsTypeList(item.Id, item.CommentsType);
        }
        Cursor res = myDb.getCommentsTypeList();
        if (res.getCount() != 0) {
            Toast.makeText(MainActivity.this, "Comments Type List Inserted", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

    }

    public void AddCommentsList(ArrayList<CommnetsClass> CommentsList) {
        for (CommnetsClass item : CommentsList) {
            boolean isInserted = myDb.insertCommentsList(item.Id, item.CommentsTypeId, item.Comments);
        }
        Cursor res = myDb.getCommentsTypeList();
        if (res.getCount() != 0) {
            Toast.makeText(MainActivity.this, "Comments List Inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(MainActivity.this, "Information Stored Successfully...", Toast.LENGTH_LONG).show();
    }


    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    /* Begin-- Collect ASM List information data from DB with API --Begin*/
    public class GetNumberList extends AsyncTask<ArrayList<String>, Void, ArrayList<NumberClass>> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(ArrayList<NumberClass> NumberList) {

            if (NumberList.size() > 1) {
                AddNumberList(NumberList);
            } else {

            }
        }

        protected ArrayList<NumberClass> doInBackground(ArrayList<String>... params) {
            Integer result;
            JSONObject jObject;
            JSONArray jsonArray = null;
            int i = 0;
            String str = "http://api.infinigentconsulting.com/api/Number";//"http://202.126.122.85:71/api/Division";
            String response = "";
            ArrayList<NumberClass> NumberArrayList = new ArrayList();
            URL url = null;
            try {
                url = new URL(str);
            } catch (MalformedURLException e) {
                response = e.getMessage();
            } catch (Exception ex) {
                response = ex.getMessage();
            }
            HttpURLConnection conn = null;

            JSONObject jsonObject;
            JSONStringer userJson = null;
            OutputStreamWriter outputStreamWriter = null;
            int responseCode;
            BufferedReader br;
            String line;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //starting
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            try {
                responseCode = conn.getResponseCode();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while (true) {
                    line = br.readLine();
                    if (line != null) {
                        response = response + line;
                    } else {
                        break;
                    }

                }
                jObject = null;
                if (!response.isEmpty()) {
                    try {
                        jsonArray = new JSONArray(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {

                    for (i = 0; i < jsonArray.length(); i++) {
                        JSONObject object2 = jsonArray.getJSONObject(i);
                        NumberClass _Number = new NumberClass(object2.getInt("Id"), object2.getString("Number"));
                        _Number.setId(object2.getInt("Id"));
                        _Number.setNumber(object2.getString("Number"));
                        NumberArrayList.add(_Number);

                    }
                } catch (JSONException e322) {
                    response = e322.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return NumberArrayList;
        }
    }

    /*END-- Collect ASM List data from DB with API --END*/

    /* Begin-- Collect User information data from DB with API --Begin*/
    public class GetListofUser extends AsyncTask<ArrayList<String>, Void, ArrayList<UserClass>> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(ArrayList<UserClass> UserList) {
            // super.onPostExecute(i);
            if (UserList.size() > 1) {
                AddUserList(UserList);
            } else {
                // onLoginFailed();
                Log.d("a", "not found");
            }
        }

        protected ArrayList<UserClass> doInBackground(ArrayList<String>... params) {
            Integer result;
            JSONObject jObject;
            JSONArray jsonArray = null;
            int i = 0;
            String str = "http://api.infinigentconsulting.com/api/User";//"http://202.126.122.85:71/api/Division";
            String response = "";
            ArrayList<UserClass> UserArrayList = new ArrayList();
            URL url = null;
            try {
                url = new URL(str);
            } catch (MalformedURLException e) {
                response = e.getMessage();
            } catch (Exception ex) {
                response = ex.getMessage();
            }
            HttpURLConnection conn = null;

            JSONObject jsonObject;
            JSONStringer userJson = null;
            OutputStreamWriter outputStreamWriter = null;
            int responseCode;
            BufferedReader br;
            String line;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //starting
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            try {
                responseCode = conn.getResponseCode();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while (true) {
                    line = br.readLine();
                    if (line != null) {
                        response = response + line;
                    } else {
                        break;
                    }

                }
                jObject = null;
                if (!response.isEmpty()) {
                    try {
                        jsonArray = new JSONArray(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {

                    for (i = 0; i < jsonArray.length(); i++) {
                        JSONObject object2 = jsonArray.getJSONObject(i);
                        UserClass _UserClass = new UserClass();
                        _UserClass.setId(object2.getInt("Id"));
                        _UserClass.setName(object2.getString("Name"));
                        _UserClass.setEmail(object2.getString("Email"));
                        _UserClass.setMobileNo(object2.getString("MobileNo"));
                        _UserClass.setPassword(object2.getString("Password"));
                        _UserClass.setIsActive(object2.getBoolean("IsActive"));

                        UserArrayList.add(_UserClass);

                    }
                } catch (JSONException e322) {
                    response = e322.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return UserArrayList;
        }
    }

    /*END-- Collect User information data from DB with API --END*/
    /* Begin-- Collect Distributor List information data from DB with API --Begin*/
    public class GetDistributorList extends AsyncTask<ArrayList<String>, Void, ArrayList<GenericClass>> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(ArrayList<GenericClass> DistributorList) {
            // super.onPostExecute(i);
            if (DistributorList.size() > 1) {
                AddDistributorList(DistributorList);


            } else {
                // onLoginFailed();
            }
        }

        protected ArrayList<GenericClass> doInBackground(ArrayList<String>... params) {
            Integer result;
            JSONObject jObject;
            JSONArray jsonArray = null;
            int i = 0;
            String str = "http://api.infinigentconsulting.com/api/DistributorDetails";//"http://202.126.122.85:71/api/Division";
            String response = "";
            ArrayList<GenericClass> DistributorArrayList = new ArrayList();
            URL url = null;
            try {
                url = new URL(str);
            } catch (MalformedURLException e) {
                response = e.getMessage();
            } catch (Exception ex) {
                response = ex.getMessage();
            }
            HttpURLConnection conn = null;

            JSONObject jsonObject;
            JSONStringer userJson = null;
            OutputStreamWriter outputStreamWriter = null;
            int responseCode;
            BufferedReader br;
            String line;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //starting
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            try {
                responseCode = conn.getResponseCode();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while (true) {
                    line = br.readLine();
                    if (line != null) {
                        response = response + line;
                    } else {
                        break;
                    }

                }
                jObject = null;
                if (!response.isEmpty()) {
                    try {
                        jsonArray = new JSONArray(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {

                    for (i = 0; i < jsonArray.length(); i++) {
                        JSONObject object2 = jsonArray.getJSONObject(i);
                        GenericClass _Distributor = new GenericClass();
                        _Distributor.setId(object2.getInt("Id"));
                        _Distributor.setName(object2.getString("Name"));
                        _Distributor.setIsActive(object2.getBoolean("IsActive"));


                        DistributorArrayList.add(_Distributor);

                    }
                } catch (JSONException e322) {
                    response = e322.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return DistributorArrayList;
        }
    }

    /*END-- Collect Distributor List data from DB with API --END*/
    /** Begin-- Collect Scheme Name List information data from DB with API --Begin **/
    public class GetSchemeNameList extends AsyncTask<ArrayList<String>, Void, ArrayList<GenericClass>> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(ArrayList<GenericClass> SchemeNameList) {
            // super.onPostExecute(i);
            if (SchemeNameList.size() > 1) {
                AddSchemeNameList(SchemeNameList);


            } else {
                // onLoginFailed();
            }
        }

        protected ArrayList<GenericClass> doInBackground(ArrayList<String>... params) {
            Integer result;
            JSONObject jObject;
            JSONArray jsonArray = null;
            int i = 0;
            String str = "http://api.infinigentconsulting.com/api/SchemeName";            //"http://202.126.122.85:71/api/Division";
            String response = "";
            ArrayList<GenericClass> SchemeNameArrayList = new ArrayList();
            URL url = null;
            try {
                url = new URL(str);
            } catch (MalformedURLException e) {
                response = e.getMessage();
            } catch (Exception ex) {
                response = ex.getMessage();
            }
            HttpURLConnection conn = null;

            JSONObject jsonObject;
            JSONStringer userJson = null;
            OutputStreamWriter outputStreamWriter = null;
            int responseCode;
            BufferedReader br;
            String line;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //starting
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            try {
                responseCode = conn.getResponseCode();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while (true) {
                    line = br.readLine();
                    if (line != null) {
                        response = response + line;
                    } else {
                        break;
                    }

                }
                jObject = null;
                if (!response.isEmpty()) {
                    try {
                        jsonArray = new JSONArray(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {

                    for (i = 0; i < jsonArray.length(); i++) {
                        JSONObject object2 = jsonArray.getJSONObject(i);
                        GenericClass _SchemeName = new GenericClass();
                        _SchemeName.setId(object2.getInt("Id"));
                        _SchemeName.setName(object2.getString("Name"));
                        _SchemeName.setIsActive(object2.getBoolean("IsActive"));


                        SchemeNameArrayList.add(_SchemeName);

                    }
                } catch (JSONException e322) {
                    response = e322.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return SchemeNameArrayList;
        }
    }
    /*END-- Collect Scheme Name List data from DB with API --END*/
    /* Begin-- Collect AIC List information data from DB with API --Begin*/
    public class GetAICList extends AsyncTask<ArrayList<String>, Void, ArrayList<GenericClass>> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(ArrayList<GenericClass> AICList) {
            // super.onPostExecute(i);
            if (AICList.size() > 1) {
                AddAICList(AICList);


            } else {
                // onLoginFailed();
            }
        }

        protected ArrayList<GenericClass> doInBackground(ArrayList<String>... params) {
            Integer result;
            JSONObject jObject;
            JSONArray jsonArray = null;
            int i = 0;
            String str = "http://api.infinigentconsulting.com/api/AIC";//"http://202.126.122.85:71/api/Division";
            String response = "";
            ArrayList<GenericClass> AICArrayList = new ArrayList();
            URL url = null;
            try {
                url = new URL(str);
            } catch (MalformedURLException e) {
                response = e.getMessage();
            } catch (Exception ex) {
                response = ex.getMessage();
            }
            HttpURLConnection conn = null;

            JSONObject jsonObject;
            JSONStringer userJson = null;
            OutputStreamWriter outputStreamWriter = null;
            int responseCode;
            BufferedReader br;
            String line;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //starting
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            try {
                responseCode = conn.getResponseCode();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while (true) {
                    line = br.readLine();
                    if (line != null) {
                        response = response + line;
                    } else {
                        break;
                    }

                }
                jObject = null;
                if (!response.isEmpty()) {
                    try {
                        jsonArray = new JSONArray(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {

                    for (i = 0; i < jsonArray.length(); i++) {
                        JSONObject object2 = jsonArray.getJSONObject(i);
                        GenericClass _AIC = new GenericClass();
                        _AIC.setId(object2.getInt("Id"));
                        _AIC.setName(object2.getString("Name"));
                        _AIC.setIsActive(object2.getBoolean("IsActive"));


                        AICArrayList.add(_AIC);

                    }
                } catch (JSONException e322) {
                    response = e322.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return AICArrayList;
        }
    }

    /*END-- Collect AIC List data from DB with API --END*/
    /* Begin-- Collect ASM List information data from DB with API --Begin*/
    public class GetASMList extends AsyncTask<ArrayList<String>, Void, ArrayList<GenericClass>> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(ArrayList<GenericClass> ASMList) {
            // super.onPostExecute(i);
            if (ASMList.size() > 1) {
                AddASMList(ASMList);


            } else {
                // onLoginFailed();
            }
        }

        protected ArrayList<GenericClass> doInBackground(ArrayList<String>... params) {
            Integer result;
            JSONObject jObject;
            JSONArray jsonArray = null;
            int i = 0;
            String str = "http://api.infinigentconsulting.com/api/ASM";//"http://202.126.122.85:71/api/Division";
            String response = "";
            ArrayList<GenericClass> ASMArrayList = new ArrayList();
            URL url = null;
            try {
                url = new URL(str);
            } catch (MalformedURLException e) {
                response = e.getMessage();
            } catch (Exception ex) {
                response = ex.getMessage();
            }
            HttpURLConnection conn = null;

            JSONObject jsonObject;
            JSONStringer userJson = null;
            OutputStreamWriter outputStreamWriter = null;
            int responseCode;
            BufferedReader br;
            String line;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //starting
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            try {
                responseCode = conn.getResponseCode();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while (true) {
                    line = br.readLine();
                    if (line != null) {
                        response = response + line;
                    } else {
                        break;
                    }

                }
                jObject = null;
                if (!response.isEmpty()) {
                    try {
                        jsonArray = new JSONArray(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {

                    for (i = 0; i < jsonArray.length(); i++) {
                        JSONObject object2 = jsonArray.getJSONObject(i);
                        GenericClass _ASM = new GenericClass();
                        _ASM.setId(object2.getInt("Id"));
                        _ASM.setName(object2.getString("Name"));
                        _ASM.setIsActive(object2.getBoolean("IsActive"));


                        ASMArrayList.add(_ASM);

                    }
                } catch (JSONException e322) {
                    response = e322.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return ASMArrayList;
        }
    }

    /*END-- Collect ASM List data from DB with API --END*/
    /* Begin-- Collect CommentsType List information data from DB with API --Begin*/
    public class GetCommentsTypeList extends AsyncTask<ArrayList<String>, Void, ArrayList<CommentsTypeClass>> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(ArrayList<CommentsTypeClass> CommentsTypeList) {
            // super.onPostExecute(i);
            if (CommentsTypeList.size() > 1) {
                AddCommentsTypeList(CommentsTypeList);


            } else {
                // onLoginFailed();
            }
        }

        protected ArrayList<CommentsTypeClass> doInBackground(ArrayList<String>... params) {
            Integer result;
            JSONObject jObject;
            JSONArray jsonArray = null;
            int i = 0;
            String str = "http://api.infinigentconsulting.com/api/CommentsType";//"http://202.126.122.85:71/api/Division";
            String response = "";
            ArrayList<CommentsTypeClass> CommentsTypeArrayList = new ArrayList();
            URL url = null;
            try {
                url = new URL(str);
            } catch (MalformedURLException e) {
                response = e.getMessage();
            } catch (Exception ex) {
                response = ex.getMessage();
            }
            HttpURLConnection conn = null;

            JSONObject jsonObject;
            JSONStringer userJson = null;
            OutputStreamWriter outputStreamWriter = null;
            int responseCode;
            BufferedReader br;
            String line;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //starting
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            try {
                responseCode = conn.getResponseCode();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while (true) {
                    line = br.readLine();
                    if (line != null) {
                        response = response + line;
                    } else {
                        break;
                    }

                }
                jObject = null;
                if (!response.isEmpty()) {
                    try {
                        jsonArray = new JSONArray(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {

                    for (i = 0; i < jsonArray.length(); i++) {
                        JSONObject object2 = jsonArray.getJSONObject(i);
                        CommentsTypeClass _CommentsType = new CommentsTypeClass();
                        _CommentsType.setId(object2.getInt("Id"));
                        _CommentsType.setCommentsType(object2.getString("CommentsType"));


                        CommentsTypeArrayList.add(_CommentsType);

                    }
                } catch (JSONException e322) {
                    response = e322.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return CommentsTypeArrayList;
        }
    }

    /*END-- Collect CommentsType List data from DB with API --END*/
    /* Begin-- Collect Comments List information data from DB with API --Begin*/
    public class GetCommentsList extends AsyncTask<ArrayList<String>, Void, ArrayList<CommnetsClass>> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(ArrayList<CommnetsClass> CommentsList) {
            // super.onPostExecute(i);
            if (CommentsList.size() > 1) {
                AddCommentsList(CommentsList);


            } else {
                // onLoginFailed();
            }
        }

        protected ArrayList<CommnetsClass> doInBackground(ArrayList<String>... params) {
            Integer result;
            JSONObject jObject;
            JSONArray jsonArray = null;
            int i = 0;
            String str = "http://api.infinigentconsulting.com/api/Commnets";//"http://202.126.122.85:71/api/Division";
            String response = "";
            ArrayList<CommnetsClass> CommentsArrayList = new ArrayList();
            URL url = null;
            try {
                url = new URL(str);
            } catch (MalformedURLException e) {
                response = e.getMessage();
            } catch (Exception ex) {
                response = ex.getMessage();
            }
            HttpURLConnection conn = null;

            JSONObject jsonObject;
            JSONStringer userJson = null;
            OutputStreamWriter outputStreamWriter = null;
            int responseCode;
            BufferedReader br;
            String line;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //starting
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            try {
                responseCode = conn.getResponseCode();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while (true) {
                    line = br.readLine();
                    if (line != null) {
                        response = response + line;
                    } else {
                        break;
                    }

                }
                jObject = null;
                if (!response.isEmpty()) {
                    try {
                        jsonArray = new JSONArray(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {

                    for (i = 0; i < jsonArray.length(); i++) {
                        JSONObject object2 = jsonArray.getJSONObject(i);
                        CommnetsClass _Comments = new CommnetsClass();
                        _Comments.setId(object2.getInt("Id"));
                        _Comments.setCommentsTypeId(object2.getInt("CommentsTypeId"));
                        _Comments.setComments(object2.getString("Comments"));


                        CommentsArrayList.add(_Comments);

                    }
                } catch (JSONException e322) {
                    response = e322.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return CommentsArrayList;
        }
    }
    /*END-- Collect Comments List data from DB with API --END*/

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private final int spanCount;
        private final int spacing;
        private final boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }

    }

    public Date stringToDate(String dateString) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        try {
            date = format.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    try {
                        Intent  i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                    } catch (Exception ex) {
                        Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return true;
                case R.id.navigation_settings:
                    return true;

                case R.id.navigation_log_out:
                    try {
                        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("remember","false");
                        edit.apply();
                        Intent  i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                    } catch (Exception ex) {
                        Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }

}