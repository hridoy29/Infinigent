package com.example.infinigentconsulting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.parceler.Parcels;

public class SchemeAuditActivity extends AppCompatActivity {
    private ImageButton _input_scheme_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_audit);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        _input_scheme_button = findViewById(R.id.input_scheme_audit_btn);
        _input_scheme_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SchemeAuditActivity.this, OutletDetailsActivity.class);
                i.putExtra("activity","SchemeAuditActivity");
                startActivity(i);
            }
        });


        //ImageView of Cover Photo
        try {
            Glide.with(this).load(R.drawable.banner).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(SchemeAuditActivity.this, MainActivity.class);
                startActivity(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(SchemeAuditActivity.this, MainActivity.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_settings:
                    return true;

                case R.id.navigation_log_out:
                    Intent j = new Intent(SchemeAuditActivity.this, LoginActivity.class);
                    startActivity(j);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() { }
}