package com.example.infinigentconsulting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class AuditedSchemeDetails extends AppCompatActivity {


    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    try {
                        Intent i = new Intent(AuditedSchemeDetails.this, MainActivity.class);
                        startActivity(i);
                    } catch (Exception ex) {
                        Toast.makeText(AuditedSchemeDetails.this, ex.getMessage(), Toast.LENGTH_LONG).show();
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
                        Intent i = new Intent(AuditedSchemeDetails.this, LoginActivity.class);
                        startActivity(i);
                    } catch (Exception ex) {
                        Toast.makeText(AuditedSchemeDetails.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return true;
            }
            return false;
        }
    };
    private StatusListAdapter statusListAdapter;
    private ArrayList<StatusUser> UserStatusList;
    private Cursor userStatusCursor;
    private DatabaseHelper myDb;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audited_scheme_details);

        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            toolbar.setTitleTextColor(Color.WHITE);

        } catch (Exception ex) {
            Toast.makeText(AuditedSchemeDetails.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        //ImageView of Cover Photo
        try {
            Glide.with(this).load(R.drawable.banner).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        myDb = new DatabaseHelper(this);

        UserStatusList = new ArrayList<StatusUser>();

        listview = findViewById(R.id.audited_scheme_list);

        try {

            userStatusCursor = myDb.getUserStatusList();
        } catch (Exception ex) {
            Toast.makeText(AuditedSchemeDetails.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            getUserStatusAsArrayList(UserStatusList, userStatusCursor);
            myDb.close();
        } catch (Exception ex) {
            Toast.makeText(AuditedSchemeDetails.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        statusListAdapter = new StatusListAdapter(this, R.layout.scheme_status_list_item, UserStatusList);
        listview.setAdapter(statusListAdapter);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private ArrayList<StatusUser> getUserStatusAsArrayList(ArrayList<StatusUser> userStatusArrayList, Cursor cursor) {

        Cursor res = cursor;
        if (res.getCount() == 0) {
            // show message
            // showMessage("Error","Nothing found");
            Toast.makeText(AuditedSchemeDetails.this, "Not Found", Toast.LENGTH_LONG).show();
//            return DistributordetailsList;
        }
        while (res.moveToNext()) {
            StatusUser _user = new StatusUser();
            _user.setNumberStatus(res.getString(0));
            _user.setOutletNameStatus(res.getString(1));
            _user.setMobileNumberStatus(res.getString(2));
            userStatusArrayList.add(_user);
        }
        return userStatusArrayList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(AuditedSchemeDetails.this, StatusActivity.class);
                startActivity(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AuditedSchemeDetails.this, StatusActivity.class);
        startActivity(i);
    }

}