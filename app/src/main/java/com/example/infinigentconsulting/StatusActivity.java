package com.example.infinigentconsulting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class StatusActivity extends AppCompatActivity {
    private TextView _count;
    private DatabaseHelper db;
    private ImageStatusAdapter imageStatusAdapter;
    private ArrayList<StatusImage> UserStatusList;
    private Cursor userStatusCursor;
    private DatabaseHelper myDb;
    private ListView listview;
    private Button details_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            toolbar.setTitleTextColor(Color.WHITE);

        } catch (Exception ex) {
            Toast.makeText(StatusActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        //ImageView of Cover Photo
        try {
            Glide.with(this).load(R.drawable.banner).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            details_button = findViewById(R.id.audited_scheme_details_btn);
            listview = findViewById(R.id.list);
            _count = findViewById(R.id.count);


            myDb = new DatabaseHelper(this);

            UserStatusList = new ArrayList<StatusImage>();
        }catch (Exception ex) {
            Toast.makeText(StatusActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }



        try {

            userStatusCursor = myDb.getImageStatusList();
        } catch (Exception ex) {
            Toast.makeText(StatusActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            getUserStatusAsArrayList(UserStatusList,userStatusCursor);
            myDb.close();
        } catch (Exception ex) {
            Toast.makeText(StatusActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        //Set On Click Listener For Details Button

        details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent  i = new Intent(StatusActivity.this, AuditedSchemeDetails.class);
                    startActivity(i);
                }catch(Exception ex){
                    Toast.makeText(StatusActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });





       try {
           int counts = (int) myDb.getAuditShopDetailsCount();
           myDb.close();

           _count.setText(""+counts);
       }catch (Exception ex) {
           Toast.makeText(StatusActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
       }

        imageStatusAdapter = new ImageStatusAdapter(this, R.layout.status_list_item, UserStatusList);
       listview.setAdapter(imageStatusAdapter);





        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    private ArrayList<StatusImage> getUserStatusAsArrayList(ArrayList<StatusImage> userStatusArrayList, Cursor cursor) {

        Cursor res = cursor;
        if (res.getCount() == 0) {
            // show message
            // showMessage("Error","Nothing found");
            Toast.makeText(StatusActivity.this, "Not Found", Toast.LENGTH_LONG).show();
//            return DistributordetailsList;
        }
        while (res.moveToNext()) {
            StatusImage _user = new StatusImage();

            _user.setSerialNumberStatus(res.getString(0));
            _user.setImageCountStatus(res.getInt(1));
            userStatusArrayList.add(_user);
        }
        return userStatusArrayList;
    }


    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    try {
                        Intent  i = new Intent(StatusActivity.this, MainActivity.class);
                        startActivity(i);
                    } catch (Exception ex) {
                        Toast.makeText(StatusActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
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
                        Intent  i = new Intent(StatusActivity.this, LoginActivity.class);
                        startActivity(i);
                    } catch (Exception ex) {
                        Toast.makeText(StatusActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(StatusActivity.this, MainActivity.class);
                startActivity(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() { }

}