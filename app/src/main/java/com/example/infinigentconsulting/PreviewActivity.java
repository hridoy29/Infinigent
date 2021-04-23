package com.example.infinigentconsulting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.parceler.Parcels;

import java.util.ArrayList;


public class PreviewActivity extends AppCompatActivity {

    private TextView _outlate_type_id, _distributor_name_text_view, _aic_name_text_view, _asm_name_text_view,_outlate_name, _retailer_gcc_code, _retail_seller_name, _retailer_mobile_no, _outlate_address,_is_known_about_scheme,_is_facilitated_by_scheme,_is_written_record_available,_does_expired_product_available,_does_satisfied_with_sales_officer,_does_satisfied_with_product_order_And_service,
            _does_got_latest_discount_offer,_will_get_any_discount_offer_from_distributor,_does_cocacola_label_available,_is_gcc_code_available,_scheme_date, _latest_chalan_date,_present_scheme_details, _chalan_amount,_comments_details,_scheme_media_type,_sales_officer_visiting_day,_does_got_any_chalan, _comments_type_text_view, _comments_text_view ;
    private boolean isShopeImageOneAvailable , isShopeImageTwoAvailable , isShopeImageThreeAvailable , isSignatureAvailable ;
    private SchemeAuditParent schemeAuditParent;
    private SchemeAuditShopDetails schemeAuditShopDetails;
    ImageButton previous_btn;
    private ArrayList<byte[]> images;
    private ImageView _signature_preview,_shop_image_one_preview,_shop_image_two_preview,_shop_image_three_preview;
    private String aic,asm,commentType,comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

       

        try {
            schemeAuditShopDetails = Parcels.unwrap(getIntent().getParcelableExtra("schemeAuditShopDetails"));
            schemeAuditParent = Parcels.unwrap(getIntent().getParcelableExtra("schemeAuditParent"));
            images = (ArrayList<byte[]>) Parcels.unwrap(this.getIntent().getParcelableExtra("images"));

             aic =this.getIntent().getStringExtra("aic");
             asm =this.getIntent().getStringExtra("asm");
             commentType =this.getIntent().getStringExtra("commentType");
             comments =this.getIntent().getStringExtra("comments");
            isSignatureAvailable = this.getIntent().getBooleanExtra("isSignatureAvailable",false);
            isShopeImageOneAvailable = this.getIntent().getBooleanExtra("isShopeImageOneAvailable",false);
            isShopeImageTwoAvailable = this.getIntent().getBooleanExtra("isShopeImageTwoAvailable",false);
            isShopeImageThreeAvailable = this.getIntent().getBooleanExtra("isShopeImageThreeAvailable",false);
        }catch (Exception ex) {
            Toast.makeText(PreviewActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        /** START OF FIND VIEW BY ID'S FOR ALL THE VIEWS OF UI **/

      try {
          previous_btn =findViewById(R.id.preview_previous_button);

          _outlate_name = findViewById(R.id.outlet_name_preview);
          _retailer_gcc_code = findViewById(R.id.retailer_gcc_code_preview);
          _retail_seller_name = findViewById(R.id.retailer_name_preview);
          _retailer_mobile_no = findViewById(R.id.retailer_mobile_preview);
          _outlate_type_id = findViewById(R.id.outlet_type_preview);
          _distributor_name_text_view = findViewById(R.id.distributor_name_preview);
          _aic_name_text_view = findViewById(R.id.aic_name_preview);
          _asm_name_text_view = findViewById(R.id.asm_name_preview);
          _outlate_address = findViewById(R.id.outlet_address_preview);
          _is_known_about_scheme = findViewById(R.id.is_known_about_scheme_preview);


          _present_scheme_details = findViewById(R.id.present_scheme_details_preview);
          _scheme_media_type = findViewById(R.id.scheme_media_type_preview);

          _is_facilitated_by_scheme = findViewById(R.id.is_facilitated_by_scheme_preview);

          _is_written_record_available =findViewById(R.id.is_written_record_available_preview);

          _chalan_amount =findViewById(R.id.chalan_amount_preview);
          _does_got_any_chalan= findViewById(R.id.does_got_any_chalan_preview);

          _does_expired_product_available =findViewById(R.id.does_expired_product_available_preview);

          _does_satisfied_with_sales_officer = findViewById(R.id.does_satisfied_with_sales_officer_preview);

          _does_satisfied_with_product_order_And_service =findViewById(R.id.does_satisfied_with_product_order_And_service_preview);

          _sales_officer_visiting_day = findViewById(R.id.sales_officer_visiting_day_preview) ;

          _does_got_latest_discount_offer =findViewById(R.id.does_got_latest_discount_offer_preview);

          _will_get_any_discount_offer_from_distributor = findViewById(R.id.will_get_any_discount_offer_from_distributor_preview);
          _does_cocacola_label_available = findViewById(R.id.does_cocacola_label_available_preview);

          _is_gcc_code_available = findViewById(R.id.is_gcc_code_available_preview);


          _comments_type_text_view = findViewById(R.id.comments_type_preview);
          _comments_text_view =findViewById(R.id.comments_preview);
          _comments_details = findViewById(R.id.comment_details_preview);


          _scheme_date = findViewById(R.id.date_of_scheme_preview);
          _latest_chalan_date = findViewById(R.id.latest_chalan_date_preview);
          _signature_preview =findViewById(R.id.signature_preview);
          _shop_image_one_preview =findViewById(R.id.shop_img_one_preview);
          _shop_image_two_preview =findViewById(R.id.shop_img_two_preview);
          _shop_image_three_preview =findViewById(R.id.shop_img_three_preview);
      }catch (Exception ex) {
          Toast.makeText(PreviewActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
      }



    /** SET ALL THE VALUES TO VIEWS  **/

        try {
            _outlate_name.setText(schemeAuditShopDetails.getOutlateName());

            _retailer_gcc_code.setText(String.valueOf(schemeAuditShopDetails.getGccCode()));

            _retail_seller_name.setText(schemeAuditShopDetails.getRetailSellerName());
            _retailer_mobile_no.setText(schemeAuditShopDetails.getMobileNumber());
            _outlate_type_id.setText(schemeAuditShopDetails.getOutlateTypeId());
            _distributor_name_text_view.setText(schemeAuditShopDetails.getDistributorName());
            _aic_name_text_view.setText(aic);
            _asm_name_text_view.setText(asm);
            _comments_type_text_view.setText(commentType);
            _comments_text_view.setText(comments);
            _outlate_address.setText(schemeAuditShopDetails.getOutlateAddress());


            //
            _present_scheme_details.setText(schemeAuditParent.getSchemeDetails());
            _scheme_media_type.setText(schemeAuditParent.getSchemeMediaTypeId());
            _chalan_amount.setText(schemeAuditParent.getChallanAmount());
            _does_got_any_chalan.setText(schemeAuditParent.getDoesGotAnyChallan());
            _sales_officer_visiting_day.setText(schemeAuditParent.getSallesOfficerVisitingDay());

            _comments_details.setText(schemeAuditParent.getCommentDetails());


            _scheme_date.setText(schemeAuditParent.getDateOfScheme());


            _latest_chalan_date.setText(schemeAuditParent.getLatestChallanDate());
            getRadioValue( _is_known_about_scheme,schemeAuditParent.getIsKnowenAboutScheme());
            getRadioValue(_is_facilitated_by_scheme,schemeAuditParent.getIsFacilitatedByScheme());
            getRadioValue(_is_written_record_available,schemeAuditParent.getIsWrittenRecordAvailable());
            getRadioValue(_does_expired_product_available,schemeAuditParent.getDoesExpiredProductAvailable());
            getRadioValue (_does_satisfied_with_sales_officer,schemeAuditParent.getDoesSatisfiedWithSallesOfficer());
            getRadioValue (_does_satisfied_with_product_order_And_service,schemeAuditParent.getDoesSatisfiedWithProductOrderAndService());
            getRadioValue (_does_got_latest_discount_offer,schemeAuditParent.getDoesGotLatestDiscountOffer());
            getRadioValue (_will_get_any_discount_offer_from_distributor,schemeAuditParent.getWillGetAnyDiscountOfferFromDistributor());
            getRadioValue (_does_cocacola_label_available,schemeAuditParent.getDoesCocaColaLabelAvailable());
            getRadioValue (_is_gcc_code_available,schemeAuditParent.getIsGccCodeAvailable());
        }catch (Exception ex) {
            Toast.makeText(PreviewActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            byte[] byteArray =  images.get(0);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            try {
                Glide.with(this).load(bmp).into(_signature_preview);
            } catch (Exception e) {
                e.printStackTrace();
            }
           // _signature_preview.setImageBitmap(bmp);
            _signature_preview.setBackgroundResource(R.drawable.border);
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            Toast.makeText(this, "image not loaded"  , Toast.LENGTH_LONG).show();

        }
        try {
            byte[] byteArray =  images.get(1);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            try {
                Glide.with(this).load(bmp).into(_shop_image_one_preview);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //_shop_image_one_preview.setImageBitmap(bmp);
            _shop_image_one_preview.setBackgroundResource(R.drawable.border);
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            Toast.makeText(this, "image not loaded"  , Toast.LENGTH_LONG).show();

        }
        try {
            byte[] byteArray =  images.get(2);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            try {
                Glide.with(this).load(bmp).into(_shop_image_two_preview);
            } catch (Exception e) {
                e.printStackTrace();
            }
          //  _shop_image_two_preview.setImageBitmap(bmp);
            _shop_image_two_preview.setBackgroundResource(R.drawable.border);
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            Toast.makeText(this, "image not loaded"  , Toast.LENGTH_LONG).show();

        }
        try {
            byte[] byteArray =  images.get(3);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            try {
                Glide.with(this).load(bmp).into(_shop_image_three_preview);
            } catch (Exception e) {
                e.printStackTrace();
            }
         //   _shop_image_three_preview.setImageBitmap(bmp);
            _shop_image_three_preview.setBackgroundResource(R.drawable.border);
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            Toast.makeText(this, "image not loaded"  , Toast.LENGTH_LONG).show();

        }



        previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(PreviewActivity.this, ImageBrowsingActivity.class);
                    i.putExtra("schemeAuditShopDetails", Parcels.wrap(schemeAuditShopDetails));
                    i.putExtra("schemeAuditParent", Parcels.wrap(schemeAuditParent));
                    i.putExtra("images", Parcels.wrap(images));
                    i.putExtra("aic",aic);
                    i.putExtra("asm",asm);
                    i.putExtra("commentType",commentType);
                    i.putExtra("comments",comments);
                    i.putExtra("isShopeImageOneAvailable",isShopeImageOneAvailable);
                    i.putExtra("isShopeImageTwoAvailable",isShopeImageTwoAvailable);
                    i.putExtra("isShopeImageThreeAvailable",isShopeImageThreeAvailable);
                    i.putExtra("isSignatureAvailable",isSignatureAvailable);
                    startActivity(i);
                }
                catch (Exception ex) {
                    Toast.makeText(PreviewActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });




    }


    public void getRadioValue(TextView tv,int value){
        switch (value){
            case 1:
                tv.setText(R.string.yes);
                break;
            case 2:
                tv.setText(R.string.no);
                break;

            case 3:
            case -1:
                tv.setText(R.string.not_required);
                break;

        }
    }

    @Override
    public void onBackPressed() { }



}