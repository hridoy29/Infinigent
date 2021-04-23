package com.example.infinigentconsulting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SchemeDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private final String[] salesOfficerVisitingDay = {"1", "2", "3", "Daily", "Irregular", "N/A"};
    private final String[] DoesGotChalan = {"Electronic", "Manual", "Both", "Not Found", "N/A"};
    private final String[] SchemeMediaTypeIdArray = {"MDO", "Others", "N/A"};
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    try {
                        Intent i = new Intent(SchemeDetailsActivity.this, MainActivity.class);
                        startActivity(i);
                    } catch (Exception ex) {
                        Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
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
                        Intent i = new Intent(SchemeDetailsActivity.this, LoginActivity.class);
                        startActivity(i);
                    } catch (Exception ex) {
                        Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return true;
            }
            return false;
        }
    };
    String[] commentsArray;
    private RadioGroup _is_known_about_scheme, _is_facilitated_by_scheme, _is_written_record_available, _does_expired_product_available, _does_satisfied_with_sales_officer, _does_satisfied_with_product_order_And_service,
            _does_got_latest_discount_offer, _will_get_any_discount_offer_from_distributor, _does_cocacola_label_available, _is_gcc_code_available;
    private RadioButton _is_known_about_scheme_yes, _is_known_about_scheme_no, _is_known_about_scheme_not_required, _is_facilitated_by_scheme_yes, _is_facilitated_by_scheme_no, _is_facilitated_by_scheme_not_required, _is_written_record_available_yes, _is_written_record_available_no, _is_written_record_available_not_required, _does_expired_product_available_yes,
            _does_expired_product_available_no, _does_expired_product_available_not_required, _does_satisfied_with_sales_officer_yes, _does_satisfied_with_sales_officer_no, _does_satisfied_with_sales_officer_not_required, _does_satisfied_with_product_order_And_service_yes,
            _does_satisfied_with_product_order_And_service_no, _does_satisfied_with_product_order_And_service_not_required, _does_got_latest_discount_offer_yes, _does_got_latest_discount_offer_no, _does_got_latest_discount_offer_not_required, _will_get_any_discount_offer_from_distributor_yes, _will_get_any_discount_offer_from_distributor_no, _will_get_any_discount_offer_from_distributor_not_required,
            _does_cocacola_label_available_yes, _does_cocacola_label_available_no, _does_cocacola_label_available_not_required, _is_gcc_code_available_yes, _is_gcc_code_available_no, _is_gcc_code_available_not_required;
    private TextView _scheme_date, _latest_chalan_date;
    private Button _scheme_date_picker, _latest_chalan_date_picker;
    private EditText  _chalan_amount, _comments_details;
    private int mYear, mMonth, mDay;
    private ImageButton _scheme_details_previous_button, _scheme_details_next_button;
    private AutoCompleteTextView _scheme_media_type,_present_scheme_details, _sales_officer_visiting_day, _does_got_any_chalan, _comments_type_text_view, _comments_text_view;
    private DatabaseHelper myDb;
    private boolean isShopeImageOneAvailable, isShopeImageTwoAvailable, isShopeImageThreeAvailable, isSignatureAvailable;
    private ArrayList<String> CommentsTypeList, CommentsList, SchemeNameList;
    private Cursor comments_type_db_cursor, comments_cursor, comments_by_id_db_cursor, scheme_name_cursor;
    private String _outlate_name;
    private Date _latest_date;
    private int comment_type_id;
    private int comment_id;
    private SchemeAuditShopDetails schemeAuditShopDetails;
    private SchemeAuditParent schemeAuditParent;
    private String aic, asm, commentType, comments;
    private ArrayList<byte[]> images;
    private ArrayAdapter<String> commentsArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_details);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            initCollapsingToolbar();
        } catch (Exception ex) {
            Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        // change keyboard focus
        try {
            hideKeyboard(SchemeDetailsActivity.this);
        } catch (Exception ex) {
            Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


//        findViewById(R.id.scheme_details_cl).setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                try {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                } catch (Exception ex) {
//                    Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
//                }
//                return true;
//            }
//        });

        //ImageView of Cover Photo
        try {
            Glide.with(this).load(R.drawable.banner).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Database Helper
        myDb = new DatabaseHelper(this);
        try {
            comments_type_db_cursor = myDb.getCommentsTypeList();
            comments_cursor = myDb.getCommentsList();
            scheme_name_cursor= myDb.getSchemeNameList();
        } catch (Exception ex) {
            Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        //ArrayList<String> for Dropdown
        CommentsTypeList = new ArrayList<>();
        CommentsList = new ArrayList<>();
        SchemeNameList = new ArrayList<>();

        //Get Data from local Database to ArrayList <String>
        try {
            CommentsTypeList = getArrayListOfString(comments_type_db_cursor);
            SchemeNameList = getArrayListOfString(scheme_name_cursor);
        } catch (Exception ex) {
            Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        /** START OF FIND VIEW BY ID'S FOR ALL THE VIEWS OF UI **/
        try {
            _is_known_about_scheme = findViewById(R.id.is_known_about_scheme);
            _is_known_about_scheme_yes = findViewById(R.id.is_known_about_scheme_yes);
            _is_known_about_scheme_no = findViewById(R.id.is_known_about_scheme_no);
            _is_known_about_scheme_not_required = findViewById(R.id.is_known_about_scheme_not_required);

            _present_scheme_details = findViewById(R.id.present_scheme_details);
            _scheme_media_type = findViewById(R.id.scheme_media_type);

            _is_facilitated_by_scheme = findViewById(R.id.is_facilitated_by_scheme);
            _is_facilitated_by_scheme_yes = findViewById(R.id.is_facilitated_by_scheme_yes);
            _is_facilitated_by_scheme_no = findViewById(R.id.is_facilitated_by_scheme_no);
            _is_facilitated_by_scheme_not_required = findViewById(R.id.is_facilitated_by_scheme_not_required);

            _is_written_record_available = findViewById(R.id.is_written_record_available);
            _is_written_record_available_yes = findViewById(R.id.is_written_record_available_yes);
            _is_written_record_available_no = findViewById(R.id.is_written_record_available_no);
            _is_written_record_available_not_required = findViewById(R.id.is_written_record_available_not_required);

            _chalan_amount = findViewById(R.id.chalan_amount);
            _does_got_any_chalan = findViewById(R.id.does_got_any_chalan);

            _does_expired_product_available = findViewById(R.id.does_expired_product_available);
            _does_expired_product_available_yes = findViewById(R.id.does_expired_product_available_yes);
            _does_expired_product_available_no = findViewById(R.id.does_expired_product_available_no);
            _does_expired_product_available_not_required = findViewById(R.id.does_expired_product_available_not_required);

            _does_satisfied_with_sales_officer = findViewById(R.id.does_satisfied_with_sales_officer);
            _does_satisfied_with_sales_officer_yes = findViewById(R.id.does_satisfied_with_sales_officer_yes);
            _does_satisfied_with_sales_officer_no = findViewById(R.id.does_satisfied_with_sales_officer_no);
            _does_satisfied_with_sales_officer_not_required = findViewById(R.id.does_satisfied_with_sales_officer_not_required);

            _does_satisfied_with_product_order_And_service = findViewById(R.id.does_satisfied_with_product_order_And_service);
            _does_satisfied_with_product_order_And_service_yes = findViewById(R.id.does_satisfied_with_product_order_And_service_yes);
            _does_satisfied_with_product_order_And_service_no = findViewById(R.id.does_satisfied_with_product_order_And_service_no);
            _does_satisfied_with_product_order_And_service_not_required = findViewById(R.id.does_satisfied_with_product_order_And_service_not_required);

            _sales_officer_visiting_day = findViewById(R.id.sales_officer_visiting_day);

            _does_got_latest_discount_offer = findViewById(R.id.does_got_latest_discount_offer);
            _does_got_latest_discount_offer_yes = findViewById(R.id.does_got_latest_discount_offer_yes);
            _does_got_latest_discount_offer_no = findViewById(R.id.does_got_latest_discount_offer_no);
            _does_got_latest_discount_offer_not_required = findViewById(R.id.does_got_latest_discount_offer_not_required);

            _will_get_any_discount_offer_from_distributor = findViewById(R.id.will_get_any_discount_offer_from_distributor);
            _will_get_any_discount_offer_from_distributor_yes = findViewById(R.id.will_get_any_discount_offer_from_distributor_yes);
            _will_get_any_discount_offer_from_distributor_no = findViewById(R.id.will_get_any_discount_offer_from_distributor_no);
            _will_get_any_discount_offer_from_distributor_not_required = findViewById(R.id.will_get_any_discount_offer_from_distributor_not_required);

            _does_cocacola_label_available = findViewById(R.id.does_cocacola_label_available);
            _does_cocacola_label_available_yes = findViewById(R.id.does_cocacola_label_available_yes);
            _does_cocacola_label_available_no = findViewById(R.id.does_cocacola_label_available_no);
            _does_cocacola_label_available_not_required = findViewById(R.id.does_cocacola_label_available_not_required);

            _is_gcc_code_available = findViewById(R.id.is_gcc_code_available);
            _is_gcc_code_available_yes = findViewById(R.id.is_gcc_code_available_yes);
            _is_gcc_code_available_no = findViewById(R.id.is_gcc_code_available_no);
            _is_gcc_code_available_not_required = findViewById(R.id.is_gcc_code_available_not_required);

            _scheme_details_previous_button = findViewById(R.id.scheme_details_previous_button);
            _scheme_details_next_button = findViewById(R.id.scheme_details_next_button);
            _scheme_date_picker = findViewById(R.id.date_of_scheme_date_picker);
            _latest_chalan_date_picker = findViewById(R.id.latest_chalan_date_picker);
            _comments_type_text_view = findViewById(R.id.comments_type);
            _comments_text_view = findViewById(R.id.comments);
            _comments_details = findViewById(R.id.comment_details);


            _scheme_date = findViewById(R.id.date_of_scheme_);
            _latest_chalan_date = findViewById(R.id.latest_chalan_date);
        } catch (Exception ex) {
            Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        /** END OF FIND VIEW BY ID'S FOR ALL THE VIEWS OF UI **/


        /** START OF EXTRACT THE BUNDLES AND SET THE VIEWS **/

        try {
            String activity = this.getIntent().getStringExtra("activity");
        } catch (Exception ex) {
            Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        try {
            aic = this.getIntent().getStringExtra("aic");
            asm = this.getIntent().getStringExtra("asm");
            commentType = this.getIntent().getStringExtra("commentType");
            comments = this.getIntent().getStringExtra("comments");
            isSignatureAvailable = this.getIntent().getBooleanExtra("isSignatureAvailable", false);
            isShopeImageOneAvailable = this.getIntent().getBooleanExtra("isShopeImageOneAvailable", false);
            isShopeImageTwoAvailable = this.getIntent().getBooleanExtra("isShopeImageTwoAvailable", false);
            isShopeImageThreeAvailable = this.getIntent().getBooleanExtra("isShopeImageThreeAvailable", false);

            schemeAuditShopDetails = Parcels.unwrap(this.getIntent().getParcelableExtra("schemeAuditShopDetails"));
            schemeAuditParent = Parcels.unwrap(this.getIntent().getParcelableExtra("schemeAuditParent"));
            images = Parcels.unwrap(this.getIntent().getParcelableExtra("images"));

            _outlate_name = schemeAuditShopDetails.getOutlateName();
            _latest_date = schemeAuditShopDetails.getVisitedDate();
            _scheme_media_type.setText(schemeAuditParent.getSchemeMediaTypeId());
            _chalan_amount.setText(schemeAuditParent.getChallanAmount());
            _does_got_any_chalan.setText(schemeAuditParent.getDoesGotAnyChallan());
            _sales_officer_visiting_day.setText(schemeAuditParent.getSallesOfficerVisitingDay());
            comment_type_id = schemeAuditParent.getCommentsType();
            comment_id = schemeAuditParent.getComments();
            _comments_type_text_view.setText(commentType);
            _comments_text_view.setText(comments);
            _comments_details.setText(schemeAuditParent.getCommentDetails());

            //set all radio buttons
            setRadioButton(_is_known_about_scheme_yes, _is_known_about_scheme_no, _is_known_about_scheme_not_required, schemeAuditParent.getIsKnowenAboutScheme());
            setRadioButton(_is_facilitated_by_scheme_yes, _is_facilitated_by_scheme_no, _is_facilitated_by_scheme_not_required, schemeAuditParent.getIsFacilitatedByScheme());
            setRadioButton(_is_written_record_available_yes, _is_written_record_available_no, _is_written_record_available_not_required, schemeAuditParent.getIsWrittenRecordAvailable());
            setRadioButton(_does_expired_product_available_yes, _does_expired_product_available_no, _does_expired_product_available_not_required, schemeAuditParent.getDoesExpiredProductAvailable());
            setRadioButton(_does_satisfied_with_sales_officer_yes, _does_satisfied_with_sales_officer_no, _does_satisfied_with_sales_officer_not_required, schemeAuditParent.getDoesSatisfiedWithSallesOfficer());
            setRadioButton(_does_satisfied_with_product_order_And_service_yes, _does_satisfied_with_product_order_And_service_no, _does_satisfied_with_product_order_And_service_not_required, schemeAuditParent.getDoesSatisfiedWithProductOrderAndService());
            setRadioButton(_does_got_latest_discount_offer_yes, _does_got_latest_discount_offer_no, _does_got_latest_discount_offer_not_required, schemeAuditParent.getDoesGotLatestDiscountOffer());
            setRadioButton(_will_get_any_discount_offer_from_distributor_yes, _will_get_any_discount_offer_from_distributor_no, _will_get_any_discount_offer_from_distributor_not_required, schemeAuditParent.getWillGetAnyDiscountOfferFromDistributor());
            setRadioButton(_does_cocacola_label_available_yes, _does_cocacola_label_available_no, _does_cocacola_label_available_not_required, schemeAuditParent.getDoesCocaColaLabelAvailable());
            setRadioButton(_is_gcc_code_available_yes, _is_gcc_code_available_no, _is_gcc_code_available_not_required, schemeAuditParent.getIsGccCodeAvailable());
            // SET PRESENT SCHEME DETAILS
            if (schemeAuditParent.getIsKnowenAboutScheme() == 1) {
                _present_scheme_details.setText(schemeAuditParent.getSchemeDetails());
                _present_scheme_details.setEnabled(true);
                _present_scheme_details.setInputType(InputType.TYPE_CLASS_TEXT);
                _present_scheme_details.setFocusableInTouchMode(true);
                _present_scheme_details.setCursorVisible(true);
            } else {
                _present_scheme_details.setText("N/A");
                _present_scheme_details.setFocusable(false);
                _present_scheme_details.setEnabled(false);
                _present_scheme_details.setCursorVisible(false);
                _present_scheme_details.setKeyListener(null);
            }
            // SET SCHEME DATE
            if (schemeAuditParent.getIsFacilitatedByScheme() == 1) {
                _scheme_date.setText(schemeAuditParent.getDateOfScheme());
                _scheme_date_picker.setEnabled(true);
            } else {
                _scheme_date.setText("N/A");
                _scheme_date_picker.setEnabled(false);
            }
            // SET LATEST CHALAN DATE
            _latest_chalan_date.setText(schemeAuditParent.getLatestChallanDate());

            if (schemeAuditShopDetails.getGccCode() == 0) {
                _is_gcc_code_available_yes.setEnabled(false);
                _is_gcc_code_available_no.setEnabled(false);
                _is_gcc_code_available_not_required.setEnabled(false);
            } else {
                _is_gcc_code_available_yes.setEnabled(true);
                _is_gcc_code_available_no.setEnabled(true);
                _is_gcc_code_available_not_required.setEnabled(true);
            }
        } catch (Exception ex) {
            Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        /** END OF EXTRACT THE BUNDLES AND SET THE VIEWS **/


        /**    START OF ALL ON CLICK LISTENER    **/

        _scheme_details_previous_button.setOnClickListener(this);
        _scheme_details_next_button.setOnClickListener(this);

        _scheme_date_picker.setOnClickListener(this);
        _latest_chalan_date_picker.setOnClickListener(this);


        /** ALL RADIO BUTTON LISTENER **/

        //IS KNOWN ABOUT SCHEME RADIO BUTTON CHANGE LISTENER

        _is_known_about_scheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if (checkedId == R.id.is_known_about_scheme_yes) {
                    try {
                        _present_scheme_details.setText("");
                        _present_scheme_details.setEnabled(true);
                        _present_scheme_details.setInputType(InputType.TYPE_CLASS_TEXT);
                        _present_scheme_details.setFocusableInTouchMode(true);
                        _present_scheme_details.setCursorVisible(true);
                    } catch (Exception ex) {
                        Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        _present_scheme_details.setText("N/A");
                        _present_scheme_details.setFocusable(false);
                        _present_scheme_details.setEnabled(false);
                        _present_scheme_details.setCursorVisible(false);
                        _present_scheme_details.setKeyListener(null);
                    } catch (Exception ex) {
                        Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //IS FACILITATED BY SCHEME RADIO BUTTON CHANGE LISTENER

        _is_facilitated_by_scheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if (checkedId == R.id.is_facilitated_by_scheme_yes) {
                    try {
                        _scheme_date.setText("N/A");
                        _scheme_date_picker.setEnabled(true);
                    } catch (Exception ex) {
                        Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        _scheme_date.setText("N/A");
                        _scheme_date_picker.setEnabled(false);
                    } catch (Exception ex) {
                        Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        /** ALL DROPDOWN LISTENER **/
        //Scheme Media Type Id ArrayAdapter

        final ArrayAdapter<String> schemeMediaTypeArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, SchemeMediaTypeIdArray);

        _scheme_media_type.setAdapter(schemeMediaTypeArrayAdapter);

        //Scheme Media Type Id TEXT VIEW ON CLICK LISTENER

        _scheme_media_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                try {
                    _scheme_media_type.showDropDown();
                } catch (Exception ex) {
                    Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        // handle click event and set Scheme Media Type Id
        _scheme_media_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String selected = (String) adapterView.getItemAtPosition(i);
                    int pos = Arrays.asList(SchemeMediaTypeIdArray).indexOf(selected);
                    _scheme_media_type.setText(SchemeMediaTypeIdArray[pos]);
                } catch (Exception ex) {
                    Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        //Scheme Name ArrayAdapter
        String[] schemeNameArray = SchemeNameList.toArray(new String[SchemeNameList.size()]);

        final ArrayAdapter<String> distributorNameArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, schemeNameArray);

        _present_scheme_details.setAdapter(distributorNameArrayAdapter);


        //Scheme Name TEXT VIEW ON CLICK LISTENER

        _present_scheme_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                try {
                    _present_scheme_details.showDropDown();
                } catch (Exception ex) {
                    Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        // handle click event and set Scheme Name
        _present_scheme_details.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String selected = (String) adapterView.getItemAtPosition(i);
                    _present_scheme_details.setText(selected);
                } catch (Exception ex) {
                    Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


        ////COMMENT TYPE
        //Comment Type ArrayAdapter
        String[] commentTypeArray = CommentsTypeList.toArray(new String[CommentsTypeList.size()]);
        final ArrayAdapter<String> commentTypeArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, commentTypeArray);

        _comments_type_text_view.setAdapter(commentTypeArrayAdapter);

        //Comment Type TEXT VIEW ON CLICK LISTENER

        _comments_type_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                try {
                    _comments_type_text_view.showDropDown();
                } catch (Exception ex) {
                    Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        // handle click event and set Comment Type
        _comments_type_text_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String selected = (String) adapterView.getItemAtPosition(i);
                    int startIndex = selected.indexOf('(');
                    int endIndex = selected.indexOf(')');
                    _comments_type_text_view.setText(selected);
                    String id = selected.substring(startIndex + 1, endIndex);
                    comment_type_id = Integer.parseInt(id);
                    CommentsList.clear();
                    comments_by_id_db_cursor = myDb.getCommentListById(comment_type_id);
                    CommentsList = getArrayListOfString(comments_by_id_db_cursor);
                    commentsArray = CommentsList.toArray(new String[CommentsList.size()]);
                    commentsArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, commentsArray);
                    _comments_text_view.setAdapter(commentsArrayAdapter);
                } catch (Exception ex) {
                    Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        });


        //------------------------------------------------------------------------------------//


        ////COMMENTS
        //Comments ArrayAdapter
        commentsArray = CommentsList.toArray(new String[CommentsList.size()]);
        commentsArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, commentsArray);

        _comments_text_view.setAdapter(commentsArrayAdapter);

        //Comment Type TEXT VIEW ON CLICK LISTENER

        _comments_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                try {
                    _comments_text_view.showDropDown();
                } catch (Exception ex) {
                    Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        // handle click event and set Comments
        _comments_text_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String selected = (String) adapterView.getItemAtPosition(i);
                    _comments_text_view.setText(selected);
                    int startIndex = selected.indexOf('(');
                    int endIndex = selected.indexOf(')');
                    String id = selected.substring(startIndex + 1, endIndex);
                    comment_id = Integer.parseInt(id);
                } catch (Exception ex) {
                    Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        });

        // Sales Officer Visiting Day ArrayAdapter

        final ArrayAdapter<String> salesOfficerVisitingDayArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, salesOfficerVisitingDay);

        _sales_officer_visiting_day.setAdapter(salesOfficerVisitingDayArrayAdapter);

        //Sales Officer Visiting Day TEXT VIEW ON CLICK LISTENER

        _sales_officer_visiting_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                try {
                    _sales_officer_visiting_day.showDropDown();
                } catch (Exception ex) {
                    Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        // handle click event and set comment on textview
        _sales_officer_visiting_day.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    String selected = (String) adapterView.getItemAtPosition(i);
                    _sales_officer_visiting_day.setText(selected);
                } catch (Exception ex) {
                    Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


        //Does Got Any Challan ArrayAdapter

        final ArrayAdapter<String> doesGotAnyChallanArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, DoesGotChalan);

        _does_got_any_chalan.setAdapter(doesGotAnyChallanArrayAdapter);

        //Does Got Any ChallanTEXT VIEW ON CLICK LISTENER

        _does_got_any_chalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                try {
                    _does_got_any_chalan.showDropDown();
                } catch (Exception ex) {
                    Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        // handle click event and set Does Got Any Challan
        _does_got_any_chalan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String selected = (String) adapterView.getItemAtPosition(i);
                    _does_got_any_chalan.setText(selected);
                } catch (Exception ex) {
                    Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        /**    END OF ALL ON CLICK LISTENER    **/




    }

    @Override
    public void onClick(View view) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        if (view == _scheme_details_previous_button) {
            try {
                Intent i = new Intent(SchemeDetailsActivity.this, OutletDetailsActivity.class);
                i.putExtra("schemeAuditShopDetails", Parcels.wrap(schemeAuditShopDetails));
                i.putExtra("schemeAuditParent", Parcels.wrap(schemeAuditParent));
                i.putExtra("images", Parcels.wrap(images));
                i.putExtra("aic", aic);
                i.putExtra("asm", asm);
                i.putExtra("commentType", commentType);
                i.putExtra("comments", comments);
                i.putExtra("activity", "SchemeDetailsActivity");
                i.putExtra("isShopeImageOneAvailable", isShopeImageOneAvailable);
                i.putExtra("isShopeImageTwoAvailable", isShopeImageTwoAvailable);
                i.putExtra("isShopeImageThreeAvailable", isShopeImageThreeAvailable);
                i.putExtra("isSignatureAvailable", isSignatureAvailable);
                startActivity(i);
            } catch (Exception ex) {
                Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        if (view == _scheme_details_next_button) {
            try {
                if (validate()) {
                    sendAll();
                } else {
                    _scheme_details_next_button.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Please fill all the field", Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }


        }
        if (view == _scheme_date_picker) {
            try {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                _scheme_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            } catch (Exception ex) {
                Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
//            Date scheme_date = stringToDate(_scheme_date.getText().toString());
        }
        if (view == _latest_chalan_date_picker) {
            try {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                _latest_chalan_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            } catch (Exception ex) {
                Toast.makeText(SchemeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }

        }


    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this,R.color.transparent));
        collapsingToolbar.setCollapsedTitleTextColor(Color.rgb(1, 1, 1));
        appBarLayout.setExpanded(true);
        collapsingToolbar.setTitleEnabled(false);

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
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
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
            // show message
            // showMessage("Error","Nothing found");
            Toast.makeText(SchemeDetailsActivity.this, "Not Found", Toast.LENGTH_LONG).show();

        }
        while (res.moveToNext()) {

            arrayList.add(res.getString(0));


        }
        return arrayList;
    }

    public ArrayList<CommnetsClass> getCommentsAsArrayList(ArrayList<CommnetsClass> commentsArrayList, Cursor cursor) {

        Cursor res = cursor;
        if (res.getCount() == 0) {
            // show message
            // showMessage("Error","Nothing found");
            Toast.makeText(SchemeDetailsActivity.this, "Not Found", Toast.LENGTH_LONG).show();
//            return DistributordetailsList;
        }
        while (res.moveToNext()) {
            CommnetsClass _comments = new CommnetsClass();
            _comments.setId(res.getInt(0));
            _comments.setCommentsTypeId(res.getInt(1));
            _comments.setComments(res.getString(2));
            commentsArrayList.add(_comments);
        }
        return commentsArrayList;
    }

    public int getRadioValue(RadioGroup radioGroup) {
        int radio_id = radioGroup.getCheckedRadioButtonId();
        int value = -1;
        switch (radio_id) {
            case R.id.is_known_about_scheme_yes:
            case R.id.is_facilitated_by_scheme_yes:
            case R.id.is_written_record_available_yes:
            case R.id.does_expired_product_available_yes:
            case R.id.does_satisfied_with_sales_officer_yes:
            case R.id.does_satisfied_with_product_order_And_service_yes:
            case R.id.does_got_latest_discount_offer_yes:
            case R.id.will_get_any_discount_offer_from_distributor_yes:
            case R.id.does_cocacola_label_available_yes:
            case R.id.is_gcc_code_available_yes:
                value = 1;
                break;
            case R.id.is_known_about_scheme_no:
            case R.id.is_facilitated_by_scheme_no:
            case R.id.is_written_record_available_no:
            case R.id.does_expired_product_available_no:
            case R.id.does_satisfied_with_sales_officer_no:
            case R.id.does_satisfied_with_product_order_And_service_no:
            case R.id.does_got_latest_discount_offer_no:
            case R.id.will_get_any_discount_offer_from_distributor_no:
            case R.id.does_cocacola_label_available_no:
            case R.id.is_gcc_code_available_no:
                value = 2;
                break;
            case R.id.is_known_about_scheme_not_required:
            case R.id.is_facilitated_by_scheme_not_required:
            case R.id.is_written_record_available_not_required:
            case R.id.does_expired_product_available_not_required:
            case R.id.does_satisfied_with_sales_officer_not_required:
            case R.id.does_satisfied_with_product_order_And_service_not_required:
            case R.id.does_got_latest_discount_offer_not_required:
            case R.id.will_get_any_discount_offer_from_distributor_not_required:
            case R.id.does_cocacola_label_available_not_required:
            case R.id.is_gcc_code_available_not_required:
                value = 3;
                break;
        }

        return value;
    }

    public void setRadioButton(RadioButton yesRB, RadioButton noRB, RadioButton notRequiredRB, int value) {

        switch (value) {
            case 1:
                yesRB.setChecked(true);
                noRB.setChecked(false);
                notRequiredRB.setChecked(false);
                break;
            case 2:
                yesRB.setChecked(false);
                noRB.setChecked(true);
                notRequiredRB.setChecked(false);
                break;

            case 3:
            case -1:
                yesRB.setChecked(false);
                noRB.setChecked(false);
                notRequiredRB.setChecked(true);
                break;

        }


    }

    public Date stringToDate(String dateString) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = format.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    private int getIndexByProperty(ArrayList<CommentsTypeClass> commentsTypeClasssArrayList, int id) {
        for (int i = 0; i < commentsTypeClasssArrayList.size(); i++) {
            if (commentsTypeClasssArrayList != null && commentsTypeClasssArrayList.get(i).Id == id) {
                return i;
            }
        }
        return -1;// not there is list
    }

    private int getCommentsIndex(ArrayList<CommnetsClass> commentsClassArrayList, int id) {
        for (int i = 0; i < commentsClassArrayList.size(); i++) {
            if (commentsClassArrayList != null && commentsClassArrayList.get(i).Id == id) {
                return i;
            }
        }
        return -1;// not there is list
    }

    private boolean validate() {
        boolean valid = true;
       // String present_scheme_details = this._present_scheme_details.getText().toString();
        String scheme_media_type = this._scheme_media_type.getText().toString();
        String scheme_date = this._scheme_date.getText().toString();
        String chalan_date = this._latest_chalan_date.getText().toString();
        String chalan_amount = this._chalan_amount.getText().toString();
        String does_got_any_chalan = this._does_got_any_chalan.getText().toString();
        String sales_officer_visiting_day = this._sales_officer_visiting_day.getText().toString();
        String comments_type = this._comments_type_text_view.getText().toString();
        String comment = this._comments_text_view.getText().toString();
        String comment_details = this._comments_details.getText().toString();


//        if (present_scheme_details.isEmpty()) {
//            this._present_scheme_details.setError("enter scheme details");
//            valid = false;
//        } else {
//            this._present_scheme_details.setError(null);
//        }
        if (scheme_media_type.isEmpty()) {
            this._scheme_media_type.setError("enter scheme media");
            valid = false;
        } else {
            this._scheme_media_type.setError(null);
        }
        if (scheme_date.isEmpty()) {
            this._scheme_date.setError("enter date of scheme");
            valid = false;
        } else {
            this._scheme_date.setError(null);
        }
        if (chalan_date.isEmpty()) {
            this._latest_chalan_date.setError("enter latest chalan date");
            valid = false;
        } else {
            this._latest_chalan_date.setError(null);
        }
        if (chalan_amount.isEmpty()) {
            this._chalan_amount.setError("enter chalan amount");
            valid = false;
        } else {
            this._chalan_amount.setError(null);
        }
        if (does_got_any_chalan.isEmpty()) {
            this._does_got_any_chalan.setError("enter chalan type");
            valid = false;
        } else {
            this._does_got_any_chalan.setError(null);
        }
        if (sales_officer_visiting_day.isEmpty()) {
            this._sales_officer_visiting_day.setError("enter sales officer visiting day");
            valid = false;
        } else {
            this._sales_officer_visiting_day.setError(null);
        }
        if (comments_type.isEmpty()) {
            this._comments_type_text_view.setError("enter comment type");
            valid = false;
        } else {
            this._comments_type_text_view.setError(null);
        }
        if (comment.isEmpty()) {
            this._comments_text_view.setError("enter comment");
            valid = false;
        } else {
            this._comments_text_view.setError(null);
        }
        if (comment_details.isEmpty()) {
            this._comments_details.setError("enter comment details");
            valid = false;
        } else {
            this._comments_details.setError(null);
        }

        return valid;
    }

    private void sendAll() {
        String present_scheme_details = this._present_scheme_details.getText().toString();
        if(present_scheme_details.isEmpty()){
            present_scheme_details = "N/A";
        }

        int isKnownAboutScheme = getRadioValue(_is_known_about_scheme);
        int isFacilitatedByScheme = getRadioValue(_is_facilitated_by_scheme);
        int isWrittenRecordAvailable = getRadioValue(_is_written_record_available);
        int doesSatisfiedWithSalesOfficer = getRadioValue(_does_satisfied_with_sales_officer);
        int doesExpiredProductAvailable = getRadioValue(_does_expired_product_available);
        int doesSatisfiedWithProductOrderAndService = getRadioValue(_does_satisfied_with_product_order_And_service);
        int doesGotLatestDiscountOffer = getRadioValue(_does_got_latest_discount_offer);
        int willGetAnyDiscountOfferFromDistributor = getRadioValue(_will_get_any_discount_offer_from_distributor);
        int doesCocacolaLabelAvailable = getRadioValue(_does_cocacola_label_available);
        int isGccCodeAvailable = getRadioValue(_is_gcc_code_available);
        commentType = _comments_type_text_view.getText().toString();
        comments = _comments_text_view.getText().toString();

        //   Date scheme_date = stringToDate(_scheme_date.getText().toString());
        // Date latest_chalan_date = stringToDate(_latest_chalan_date.getText().toString());


        SchemeAuditParent schemeAuditParent = new SchemeAuditParent(1,
                "1",
                1,
                _outlate_name,
                _latest_date,
                isKnownAboutScheme,
                present_scheme_details,
                _scheme_media_type.getText().toString(),
                isFacilitatedByScheme,
                _scheme_date.getText().toString(),
                isWrittenRecordAvailable,
                _latest_chalan_date.getText().toString(),
                _chalan_amount.getText().toString(),
                _does_got_any_chalan.getText().toString(),
                1,
                doesExpiredProductAvailable,
                doesSatisfiedWithSalesOfficer,
                doesSatisfiedWithProductOrderAndService,
                _sales_officer_visiting_day.getText().toString(),
                doesGotLatestDiscountOffer,
                willGetAnyDiscountOfferFromDistributor,
                doesCocacolaLabelAvailable,
                isGccCodeAvailable,
                comment_type_id,
                comment_id,
                _comments_details.getText().toString(),
                1,
                _latest_date,
                1,
                _latest_date);

        Intent i = new Intent(SchemeDetailsActivity.this, ImageBrowsingActivity.class);
        i.putExtra("schemeAuditShopDetails", Parcels.wrap(schemeAuditShopDetails));
        i.putExtra("schemeAuditParent", Parcels.wrap(schemeAuditParent));
        i.putExtra("images", Parcels.wrap(images));
        i.putExtra("aic", aic);
        i.putExtra("asm", asm);
        i.putExtra("commentType", commentType);
        i.putExtra("comments", comments);
        i.putExtra("isShopeImageOneAvailable", isShopeImageOneAvailable);
        i.putExtra("isShopeImageTwoAvailable", isShopeImageTwoAvailable);
        i.putExtra("isShopeImageThreeAvailable", isShopeImageThreeAvailable);
        i.putExtra("isSignatureAvailable", isSignatureAvailable);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
    }

    private void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        View focusedView =activity.getCurrentFocus();
        /*
         * If no view is focused, an NPE will be thrown
         *
         * Maxim Dmitriev
         */
        if (focusedView == null) {
            focusedView = new View(activity);
        }

        inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                0);

    }

}