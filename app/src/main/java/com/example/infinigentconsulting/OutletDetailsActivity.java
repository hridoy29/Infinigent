package com.example.infinigentconsulting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class OutletDetailsActivity extends AppCompatActivity {
    private final String[] OutletTypeIdArray = {"Grocery", "Convenience", "E & D", "Travel", "Wholesale", "N/A"};
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    try {
                        Intent i = new Intent(OutletDetailsActivity.this, MainActivity.class);
                        startActivity(i);
                    } catch (Exception ex) {
                        Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
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
                        Intent i = new Intent(OutletDetailsActivity.this, LoginActivity.class);
                        startActivity(i);
                    } catch (Exception ex) {
                        Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return true;
            }
            return false;
        }
    };
    LinearLayout parent_linear;
    private ImageButton _outlate_next_button;
    private EditText _outlate_name, _retailer_gcc_code, _retail_seller_name, _retailer_mobile_no, _outlate_address;
    private AutoCompleteTextView _outlate_type_id, _distributor_name_text_view, _aic_name_text_view, _asm_name_text_view;
    private DatabaseHelper myDb;
    private Cursor distributor_db_cursor, aic_db_cursor, asm_db_cursor;
    private int aic_id, asm_id;
    private SchemeAuditParent schemeAuditParent;
    private Date _visited_date;
    private ArrayList<byte[]> images;
    private ArrayList<String> DistributorNameList, AICNameList, ASMNameList;
    private String commentType, comments, activity;
    private boolean isShopeImageOneAvailable = false, isShopeImageTwoAvailable = false, isShopeImageThreeAvailable = false, isSignatureAvailable = false;
    private byte[] sign = null;
    private byte[] img1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_details);


        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            parent_linear = findViewById(R.id.outlet_details_cl);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

            initCollapsingToolbar();
          

        } catch (Exception ex) {
            Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        //ImageView of Cover Photo
        try {
            Glide.with(this).load(R.drawable.banner).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


        images = new ArrayList<>();

        try {

            sign = drawablToByte(R.drawable.sign_brows);
            img1 = drawablToByte(R.drawable.image_brows);

            images.add(sign);
            images.add(img1);
            images.add(img1);
            images.add(img1);
        } catch (Exception ex) {
            Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        // Bottom Navigation View
        try {
            BottomNavigationView navigation = findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        } catch (Exception ex) {
            Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Database Helper
        myDb = new DatabaseHelper(this);
        try {

            distributor_db_cursor = myDb.getDistributorList();
            aic_db_cursor = myDb.getAICNameList();
            asm_db_cursor = myDb.getASMNameList();
        } catch (Exception ex) {
            Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        //ArrayList<String> for Dropdown
        try {
            DistributorNameList = new ArrayList<>();
            AICNameList = new ArrayList<String>();
            ASMNameList = new ArrayList<String>();
            //Get Data from local Database to ArrayList <String>
            DistributorNameList = getArrayListOfString(distributor_db_cursor);
            AICNameList = getArrayListOfString(aic_db_cursor);
            ASMNameList = getArrayListOfString(asm_db_cursor);
        } catch (Exception ex) {
            Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        /** START OF FIND VIEW BY ID'S FOR ALL THE VIEWS OF UI **/

        try {
            _outlate_name = findViewById(R.id.outlet_name);
            _retailer_gcc_code = findViewById(R.id.retailer_gcc_code);
            _retail_seller_name = findViewById(R.id.retailer_name);
            _retailer_mobile_no = findViewById(R.id.retailer_mobile);
            _outlate_type_id = findViewById(R.id.outlet_type);
            _distributor_name_text_view = findViewById(R.id.distributor_name);
            _aic_name_text_view = findViewById(R.id.aic_name);
            _asm_name_text_view = findViewById(R.id.asm_name);
            _outlate_address = findViewById(R.id.outlet_address);
            _outlate_next_button = findViewById(R.id.outlate_previous_button);

        } catch (Exception ex) {
            Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        /** END OF FIND VIEW BY ID'S FOR ALL THE VIEWS OF UI **/

        try {
            _visited_date = Calendar.getInstance().getTime();
        } catch (Exception ex) {
            Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        /** START OF EXTRACT THE BUNDLES AND SET THE VIEWS **/


        try {
            activity = this.getIntent().getStringExtra("activity");
        } catch (Exception ex) {
            Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Set a dummy SchemeAuditParent class object

        commentType = "";
        comments = "";
        try {
            schemeAuditParent = new SchemeAuditParent(1, "1", 1, "", _visited_date, -1, "", "", -1, "N/A", -1, "N/A", "0", "", -1, -1, -1, -1, "", -1, -1, -1, -1, -1, -1, "N/A", 1, _visited_date, 1, _visited_date);
        } catch (Exception ex) {
            Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (activity.equals("SchemeDetailsActivity")) {
            try {
                SchemeAuditShopDetails schemeAuditShopDetails = Parcels.unwrap(this.getIntent().getParcelableExtra("schemeAuditShopDetails"));
                schemeAuditParent = Parcels.unwrap(this.getIntent().getParcelableExtra("schemeAuditParent"));
                images = Parcels.unwrap(this.getIntent().getParcelableExtra("images"));
                _outlate_name.setText(schemeAuditShopDetails.getOutlateName());
                String aic = this.getIntent().getStringExtra("aic");
                String asm = this.getIntent().getStringExtra("asm");
                commentType = this.getIntent().getStringExtra("commentType");
                comments = this.getIntent().getStringExtra("comments");
                isSignatureAvailable = this.getIntent().getBooleanExtra("isSignatureAvailable", false);
                isShopeImageOneAvailable = this.getIntent().getBooleanExtra("isShopeImageOneAvailable", false);
                isShopeImageTwoAvailable = this.getIntent().getBooleanExtra("isShopeImageTwoAvailable", false);
                isShopeImageThreeAvailable = this.getIntent().getBooleanExtra("isShopeImageThreeAvailable", false);
                if (schemeAuditShopDetails.getGccCode() == 0) {
                    _retailer_gcc_code.setText("");
                } else {
                    _retailer_gcc_code.setText(String.valueOf(schemeAuditShopDetails.getGccCode()));
                }

                _retail_seller_name.setText(schemeAuditShopDetails.getRetailSellerName());
                _retailer_mobile_no.setText(schemeAuditShopDetails.getMobileNumber());
                _outlate_type_id.setText(schemeAuditShopDetails.getOutlateTypeId());
                _distributor_name_text_view.setText(schemeAuditShopDetails.getDistributorName());
                aic_id = schemeAuditShopDetails.getAicId();
                _aic_name_text_view.setText(aic);

                // int indexOfAIC = getIndexByProperty(AICNameList, aic_id);
                /// _aic_name_text_view.setText(AICNameList.get(indexOfAIC).Name);
                asm_id = schemeAuditShopDetails.getAsmId();
                _asm_name_text_view.setText(asm);
                //  int indexOfASM = getIndexByProperty(ASMNameList, asm_id);
                //  _asm_name_text_view.setText(ASMNameList.get(indexOfASM).Name);
                _outlate_address.setText(schemeAuditShopDetails.getOutlateAddress());
            } catch (Exception ex) {
                Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

        /** END OF EXTRACT THE BUNDLES AND SET THE VIEWS **/

        // change keyboard focus
//        try {
//            hideKeyboard(OutletDetailsActivity.this);
//        } catch (Exception ex) {
//            Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
//        }

//        parent_linear.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                try {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                } catch (Exception ex) {
//                    Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
//                }
//                return true;
//            }
//        });

        //Outlet Type Id ArrayAdapter
        final ArrayAdapter<String> outletTypeIdArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, OutletTypeIdArray);

        _outlate_type_id.setAdapter(outletTypeIdArrayAdapter);


        //Outlet Type Id TEXT VIEW ON CLICK LISTENER

        _outlate_type_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                try {
                    _outlate_type_id.showDropDown();
                } catch (Exception ex) {
                    Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        // handle click event and set Outlet Type Id
        _outlate_type_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    String selected = (String) adapterView.getItemAtPosition(i);
                    _outlate_type_id.setText(selected);
                } catch (Exception ex) {
                    Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        //Distributor Name ArrayAdapter
        String[] distributorNameArray = DistributorNameList.toArray(new String[DistributorNameList.size()]);

        final ArrayAdapter<String> distributorNameArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, distributorNameArray);

        _distributor_name_text_view.setAdapter(distributorNameArrayAdapter);


        //Distributor Name TEXT VIEW ON CLICK LISTENER

        _distributor_name_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                try {
                    _distributor_name_text_view.showDropDown();
                } catch (Exception ex) {
                    Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        // handle click event and set Distributor Name
        _distributor_name_text_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String selected = (String) adapterView.getItemAtPosition(i);
                    _distributor_name_text_view.setText(selected);
                } catch (Exception ex) {
                    Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


        //AIC Dropdown

        //AIC ArrayAdapter
        String[] aicNameArray = AICNameList.toArray(new String[AICNameList.size()]);

        final ArrayAdapter<String> aicNameArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, aicNameArray);

        _aic_name_text_view.setAdapter(aicNameArrayAdapter);


        //AIC Name TEXT VIEW ON CLICK LISTENER

        _aic_name_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                try {
                    _aic_name_text_view.showDropDown();
                } catch (Exception ex) {
                    Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        // handle click event and set AIC Name
        _aic_name_text_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String selected = (String) adapterView.getItemAtPosition(i);
                    _aic_name_text_view.setText(selected);
                    int startIndex = selected.indexOf('(');
                    int endIndex = selected.indexOf(')');
                    String id = selected.substring(startIndex + 1, endIndex);
                    aic_id = Integer.parseInt(id);
                } catch (Exception ex) {
                    Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


        //ASM Dropdown

        //ASM ArrayAdapter
        String[] asmNameArray = ASMNameList.toArray(new String[ASMNameList.size()]);

        final ArrayAdapter<String> asmArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, asmNameArray);

        _asm_name_text_view.setAdapter(asmArrayAdapter);


        //ASM Name TEXT VIEW ON CLICK LISTENER

        _asm_name_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                try {
                    _asm_name_text_view.showDropDown();
                } catch (Exception ex) {
                    Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        // handle click event and set ASM Name
        _asm_name_text_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String selected = (String) adapterView.getItemAtPosition(i);
                    _asm_name_text_view.setText(selected);
                    int startIndex = selected.indexOf('(');
                    int endIndex = selected.indexOf(')');
                    String id = selected.substring(startIndex + 1, endIndex);
                    asm_id = Integer.parseInt(id);
                } catch (Exception ex) {
                    Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        //On Click Listener For Outlet Next Button
        _outlate_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (validate()) {
                        sendAll();
                    } else {
                        _outlate_next_button.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Please fill all the field", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
//        final CollapsingToolbarLayout collapsingToolbar =
//                findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        //   collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this,R.color.transparent));
//        collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
//        collapsingToolbar.setCollapsedTitleTextColor(Color.rgb(1, 1, 1));
//        collapsingToolbar.setTitleEnabled(false);
//        appBarLayout.setExpanded(true);


        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
//                    collapsingToolbar.setTitle(getString(R.string.app_name));
//                    collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
                    isShow = true;
                } else if (isShow) {
                    //collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * GET ARRAYLIST OF STRING FROM DATABASE
     **/
    private ArrayList<String> getArrayListOfString(Cursor cursor) {

        ArrayList<String> arrayList = new ArrayList<String>();

        Cursor res = Objects.requireNonNull(cursor);
        if (res.getCount() == 0) {
            Toast.makeText(OutletDetailsActivity.this, "Not Found", Toast.LENGTH_LONG).show();

        }
        while (res.moveToNext()) {

            arrayList.add(res.getString(0));


        }
        return arrayList;
    }

    /**  GET INDEX BY PROPERTY METHOD **/

//    private int getIndexByProperty(ArrayList<GenericClass> genericClassArrayList, int id) {
//        for (int i = 0; i < genericClassArrayList.size(); i++) {
//            if (genericClassArrayList != null && genericClassArrayList.get(i).Id == id) {
//                return i;
//            }
//        }
//        return -1;// not there is list
//    }

    /**
     * METHOD SEND ALL FOR SENDING DATA TO NEXT INTENT
     **/

    private void sendAll() {
        try {
            String aic = _aic_name_text_view.getText().toString();
            String asm = _asm_name_text_view.getText().toString();
            int gcc_code = 0;
            String gcc_temp = _retailer_gcc_code.getText().toString();
            if (!"".equals(gcc_temp)) {
                gcc_code = Integer.parseInt(gcc_temp);
            }


            SchemeAuditShopDetails schemeAuditShopDetails = new SchemeAuditShopDetails(1, "1", _outlate_name.getText().toString(),
                    gcc_code,
                    _retail_seller_name.getText().toString(),
                    _retailer_mobile_no.getText().toString(),
                    _outlate_type_id.getText().toString(),
                    _visited_date,
                    _distributor_name_text_view.getText().toString(),
                    aic_id, asm_id,
                    _outlate_address.getText().toString());
            Intent i = new Intent(OutletDetailsActivity.this, SchemeDetailsActivity.class);

            i.putExtra("schemeAuditShopDetails", Parcels.wrap(schemeAuditShopDetails));
            i.putExtra("schemeAuditParent", Parcels.wrap(schemeAuditParent));
            i.putExtra("images", Parcels.wrap(images));
            i.putExtra("activity", "OutletDetailsActivity");
            i.putExtra("aic", aic);
            i.putExtra("asm", asm);
            i.putExtra("commentType", commentType);
            i.putExtra("comments", comments);
            i.putExtra("isShopeImageOneAvailable", isShopeImageOneAvailable);
            i.putExtra("isShopeImageTwoAvailable", isShopeImageTwoAvailable);
            i.putExtra("isShopeImageThreeAvailable", isShopeImageThreeAvailable);
            i.putExtra("isSignatureAvailable", isSignatureAvailable);
            startActivity(i);
        } catch (Exception ex) {
            Toast.makeText(OutletDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * VALIDATE METHOD FOR ALL INPUTS
     **/
    public boolean validate() {
        boolean valid = true;
        String outlet_name = this._outlate_name.getText().toString();
        String retailer_gcc_code = this._retailer_gcc_code.getText().toString();
        String retail_seller_name = this._retail_seller_name.getText().toString();
        String retailer_mobile = this._retailer_mobile_no.getText().toString();
        String outlet_type = this._outlate_type_id.getText().toString();
        String distributor_name = this._distributor_name_text_view.getText().toString();
        String aic_name = this._aic_name_text_view.getText().toString();
        String asm_name = this._asm_name_text_view.getText().toString();
        String outlet_address = this._outlate_address.getText().toString();


        if (outlet_name.isEmpty()) {
            this._outlate_name.setError("enter a outlet name");
            valid = false;
        } else {
            this._outlate_name.setError(null);
        }
        if (retailer_gcc_code.length() != 8 && !retailer_gcc_code.isEmpty()) {
            this._retailer_gcc_code.setError("retailer gcc code must be 8 digits");
            valid = false;
        } else {
            this._retailer_gcc_code.setError(null);
        }
        if (retail_seller_name.isEmpty()) {
            this._retail_seller_name.setError("enter retailer name");
            valid = false;
        } else {
            this._retail_seller_name.setError(null);
        }
        if (retailer_mobile.length() != 11 && !retailer_mobile.isEmpty()) {
            this._retailer_mobile_no.setError("retailer mobile no must be 11 digits");
            valid = false;
        } else {
            this._retailer_mobile_no.setError(null);
        }
        if (outlet_type.isEmpty()) {
            this._outlate_type_id.setError("enter outlet type");
            valid = false;
        } else {
            this._outlate_type_id.setError(null);
        }
        if (distributor_name.isEmpty()) {
            this._distributor_name_text_view.setError("enter distributor name");
            valid = false;
        } else {
            this._distributor_name_text_view.setError(null);
        }
        if (aic_name.isEmpty()) {
            this._aic_name_text_view.setError("enter aic name");
            valid = false;
        } else {
            this._aic_name_text_view.setError(null);
        }
        if (asm_name.isEmpty()) {
            this._asm_name_text_view.setError("enter asm name");
            valid = false;
        } else {
            this._asm_name_text_view.setError(null);
        }
        if (outlet_address.isEmpty()) {
            this._outlate_address.setError("enter outlet address");
            valid = false;
        } else {
            this._outlate_address.setError(null);
        }

        return valid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(OutletDetailsActivity.this, MainActivity.class);
                startActivity(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public byte[] drawablToByte(int id) {
        Resources res = getResources();
        Drawable drawable = res.getDrawable(id);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        return stream.toByteArray();
    }

    @Override
    public void onBackPressed() {
    }

    private void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        /*
         * If no view is focused, an NPE will be thrown
         *
         * Maxim Dmitriev
         */
        if (focusedView == null) {
            focusedView = new View(activity);
        }

        inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                2);

    }
}



