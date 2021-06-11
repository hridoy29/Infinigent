package com.example.infinigentconsulting;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.parceler.Parcels;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageBrowsingActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_ONE = 1888, CAMERA_REQUEST_TWO = 1889, CAMERA_REQUEST_THREE = 1890, REQUEST_CODE = 1;
    private static final int MY_CAMERA_PERMISSION_CODE_ONE = 100, MY_CAMERA_PERMISSION_CODE_TWO = 101, MY_CAMERA_PERMISSION_CODE_THREE = 102;
    final int REQUEST_CODE_GALLERY = 999;
    private boolean isShopeImageOneAvailable , isShopeImageTwoAvailable , isShopeImageThreeAvailable , isSignatureAvailable ;
    DatabaseHelper mDatabaseHelper;
    private ImageButton _img_previous_button, _shop_img_one, _shop_img_two, _shop_img_three, _signature_upload;
    private Button _image_submit_button, _submit, _preview_button;
    private SchemeAuditParent schemeAuditParent;
    private SchemeAuditShopDetails schemeAuditShopDetails;
    private String aic, asm, commentType, comments;
    private ArrayList<byte[]> images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browsing);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //ImageView of Cover Photo
        try {
            Glide.with(this).load(R.drawable.banner).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mDatabaseHelper = new DatabaseHelper(this);

        /** Find view by ids **/
        try {
            _img_previous_button = findViewById(R.id.img_previous_button);
            _shop_img_one = findViewById(R.id.shop_img_one);
            _shop_img_two = findViewById(R.id.shop_img_two);
            _shop_img_three = findViewById(R.id.shop_img_three);
            /* _image_submit_button = findViewById(R.id._submit_img);*/
            _signature_upload = findViewById(R.id.signature_upload);
            _submit = findViewById(R.id.submit);
            _preview_button = findViewById(R.id.preview);
        }catch (Exception ex) {
            Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            schemeAuditShopDetails = Parcels.unwrap(getIntent().getParcelableExtra("schemeAuditShopDetails"));
            schemeAuditParent = Parcels.unwrap(getIntent().getParcelableExtra("schemeAuditParent"));
            images = (ArrayList<byte[]>) Parcels.unwrap(this.getIntent().getParcelableExtra("images"));
            aic = this.getIntent().getStringExtra("aic");
            asm = this.getIntent().getStringExtra("asm");
            commentType = this.getIntent().getStringExtra("commentType");
            comments = this.getIntent().getStringExtra("comments");
            isSignatureAvailable = this.getIntent().getBooleanExtra("isSignatureAvailable",false);
            isShopeImageOneAvailable = this.getIntent().getBooleanExtra("isShopeImageOneAvailable",false);
            isShopeImageTwoAvailable = this.getIntent().getBooleanExtra("isShopeImageTwoAvailable",false);
            isShopeImageThreeAvailable = this.getIntent().getBooleanExtra("isShopeImageThreeAvailable",false);
        }catch (Exception ex) {
            Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            byte[] byteArray = images.get(0);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            try {
                Glide.with(this).load(bmp).into(_signature_upload);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //  _signature_upload.setImageBitmap(bmp);
            _signature_upload.setBackgroundResource(R.drawable.border);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            Toast.makeText(this, "image not loaded", Toast.LENGTH_LONG).show();

        }
        try {
            byte[] byteArray = images.get(1);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            try {
                Glide.with(this).load(bmp).into(_shop_img_one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //_shop_img_one.setImageBitmap(bmp);
            _shop_img_one.setBackgroundResource(R.drawable.border);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            Toast.makeText(this, "image not loaded", Toast.LENGTH_LONG).show();

        }
        try {
            byte[] byteArray = images.get(2);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            try {
                Glide.with(this).load(bmp).into(_shop_img_two);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // _shop_img_two.setImageBitmap(bmp);
            _shop_img_two.setBackgroundResource(R.drawable.border);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            Toast.makeText(this, "image not loaded", Toast.LENGTH_LONG).show();

        }
        try {
            byte[] byteArray = images.get(3);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            try {
                Glide.with(this).load(bmp).into(_shop_img_three);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //_shop_img_three.setImageBitmap(bmp);
            _shop_img_three.setBackgroundResource(R.drawable.border);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            Toast.makeText(this, "image not loaded", Toast.LENGTH_LONG).show();

        }
        _preview_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(ImageBrowsingActivity.this, PreviewActivity.class);
                    i.putExtra("schemeAuditShopDetails", Parcels.wrap(schemeAuditShopDetails));
                    i.putExtra("schemeAuditParent", Parcels.wrap(schemeAuditParent));
                    i.putExtra("images", Parcels.wrap(images));
                    i.putExtra("aic", aic);
                    i.putExtra("asm", asm);
                    i.putExtra("commentType", commentType);
                    i.putExtra("comments", comments);
                    i.putExtra("isShopeImageOneAvailable",isShopeImageOneAvailable);
                    i.putExtra("isShopeImageTwoAvailable",isShopeImageTwoAvailable);
                    i.putExtra("isShopeImageThreeAvailable",isShopeImageThreeAvailable);
                    i.putExtra("isSignatureAvailable",isSignatureAvailable);

                    startActivity(i);
                }catch (Exception ex) {
                    Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        _signature_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ImageBrowsingActivity.this, SignaturePadActivity.class));
                try {
                    Intent signatureIntent = new Intent(ImageBrowsingActivity.this, SignaturePadActivity.class);
                    //  startActivity(signatureIntent);
                    startActivityForResult(signatureIntent, REQUEST_CODE);
                }catch (Exception ex) {
                    Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        _img_previous_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(ImageBrowsingActivity.this, SchemeDetailsActivity.class);
                    i.putExtra("schemeAuditShopDetails", Parcels.wrap(schemeAuditShopDetails));
                    i.putExtra("schemeAuditParent", Parcels.wrap(schemeAuditParent));
                    i.putExtra("images", Parcels.wrap(images));
                    i.putExtra("activity", "ImageBrowsingActivity");
                    i.putExtra("aic", aic);
                    i.putExtra("asm", asm);
                    i.putExtra("commentType", commentType);
                    i.putExtra("comments", comments);
                    i.putExtra("isShopeImageOneAvailable",isShopeImageOneAvailable);
                    i.putExtra("isShopeImageTwoAvailable",isShopeImageTwoAvailable);
                    i.putExtra("isShopeImageThreeAvailable",isShopeImageThreeAvailable);
                    i.putExtra("isSignatureAvailable",isSignatureAvailable);
                    startActivity(i);
                }catch (Exception ex) {
                    Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        //Shop Image 1 set onClicklistener
        _shop_img_one.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        try {
                            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE_ONE);
                            } else {
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST_ONE);
                            }
                        }catch (Exception ex) {
                            Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        //Shop Image 2 set onClicklistener
        _shop_img_two.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        try {
                            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE_TWO);
                            } else {
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST_TWO);
                            }
                        }
                        catch (Exception ex) {
                            Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        //Shop Image 3 set onClicklistener
        _shop_img_three.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        try {
                            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE_THREE);
                            } else {
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST_THREE);
                            }
                        }
                        catch (Exception ex) {
                            Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        _submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(ImageBrowsingActivity.this, R.style.Theme_AppCompat_Dialog_Alert)
                        .setTitle("ADD INFORMATION")
                        .setMessage("Do You Want To Add Information In Local Database?")
                        .setPositiveButton("Yes", null)
                        .setNegativeButton("No", null)
                        .show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        final String MY_PREFS_NAME = "DeviceNumber";
                        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                        int Id = prefs.getInt("Id", 0);
                        String oldNumber= GetPreviousNumber(Id);
                        String newNumber=CreateNewNumber(oldNumber);
                        if (isSignatureAvailable == true) {
                            try {
                                //  byte[] NewImage0 = imageViewToByte(_signature_upload);
                                AddImageBrowsingActivityDataInLocalDB(newNumber, images.get(0), true);
                            }
                            catch (Exception ex) {
                                Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            //newImgEntry.add(NewImage0);
                        }
                        if (isShopeImageOneAvailable == true) {
                            try {
                                // byte[] NewImage1 = imageViewToByte(_shop_img_one);
                                AddImageBrowsingActivityDataInLocalDB(newNumber, images.get(1), false);
                            }
                            catch (Exception ex) {
                                Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        if (isShopeImageTwoAvailable == true) {
                            try {
                                //  byte[] NewImage2 = imageViewToByte(_shop_img_two);
                                AddImageBrowsingActivityDataInLocalDB(newNumber,  images.get(2), false);
                            }
                            catch (Exception ex) {
                                Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        if (isShopeImageThreeAvailable == true) {
                            try {
                                // byte[] NewImage3 = imageViewToByte(_shop_img_three);
                                AddImageBrowsingActivityDataInLocalDB(newNumber,  images.get(3), false);
                            }
                            catch (Exception ex) {
                                Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        try {
                            SchemeAuditShopDetailsActivityDataInLocalDB(schemeAuditShopDetails,newNumber);
                            SchemeAuditShopParentActivityDataInLocalDB(schemeAuditParent,newNumber);
                            mDatabaseHelper.updateNumberList(Id,newNumber);
                            Intent i;
                            i = new Intent(ImageBrowsingActivity.this, MainActivity.class);
                            startActivity(i);
                        }catch (Exception ex) {
                            Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });



    }

    private String GetPreviousNumber(int Id) {
        Cursor cursor = mDatabaseHelper.getNumberList();
        String Number="";
        if (cursor.getCount() > 0) {
            Cursor _cur = mDatabaseHelper.getLatestNumberById(Id);
            while (_cur.moveToNext()) {
                Number = _cur.getString(0);
            }

        }
        return Number;
    }
    private String CreateNewNumber(String Number)
    {
        String oldNumber,newNumber,tagNumber;
        int incrementalNumber=0;
        oldNumber=Number.substring(8);
        tagNumber=Number.substring(0,8);
        incrementalNumber= Integer.parseInt(oldNumber)+1;
        newNumber=tagNumber+incrementalNumber;
        return  newNumber;
    }

    private void SchemeAuditShopParentActivityDataInLocalDB(SchemeAuditParent _schemeAuditParent,String Number) {

        SharedPreferences prefs = getSharedPreferences("UserId", MODE_PRIVATE);
        String Id = prefs.getString("Id", "Provat");
        String Password = prefs.getString("Password", "4321");
        int _userid=2;
        Cursor cursor=mDatabaseHelper.getUserId(Id,Password);
        while (cursor.moveToNext()) {
            _userid= cursor.getInt(0);
        }
        _schemeAuditParent.setUserId(_userid);
        boolean insertData = mDatabaseHelper.insertSchemeAuditParent(_schemeAuditParent, Number);
        if (insertData) {
            Toast("Data Inserted To Local DataBase");
        } else {
            Toast("Data Not Inserted To Local DataBase");
        }
    }
    private void SchemeAuditShopDetailsActivityDataInLocalDB(SchemeAuditShopDetails schemeAuditShopDetails,String Number) {
        boolean insertData = mDatabaseHelper.insertSchemeAuditShopDetails(schemeAuditShopDetails,Number);
        if (insertData) {
            Toast("Data Inserted To Local DataBase");
        } else {
            Toast("Data Not Inserted To Local DataBase");
        }
    }

    private void AddImageBrowsingActivityDataInLocalDB(String Number, byte[] newImgEntry, boolean isSignature) {

        boolean insertData = mDatabaseHelper.addData(Number, newImgEntry, isSignature);
        if (insertData) {
            Toast("Image Browsing Data Inserted To Local DataBase");
        } else {
            Toast("Image Browsing Not Data Inserted To Local DataBase");
        }

    }

    private void SentImageByApi() {
        Cursor getImageBrowsingActivityDataListFromLocalDB = mDatabaseHelper.getImageData();
        if (getImageBrowsingActivityDataListFromLocalDB.getCount() == 0) {
            return;
        }
        //byte[] newImgEntry = "Any String you want".getBytes();
        while (getImageBrowsingActivityDataListFromLocalDB.moveToNext()) {
            _Test(getImageBrowsingActivityDataListFromLocalDB.getString(1)
                    , getImageBrowsingActivityDataListFromLocalDB.getBlob(2)
                    , Boolean.parseBoolean(getImageBrowsingActivityDataListFromLocalDB.getString(3)));
        }
        // new SentImage().execute(newImgEntry);
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
                    Toast.makeText(ImageBrowsingActivity.this, "Successfull", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ImageBrowsingActivity.this, "Invalid", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Test> call, Throwable t) {
                Toast.makeText(ImageBrowsingActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public class SentImage extends AsyncTask<byte[], Void, Integer> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            if (i == 1) {


            } else {

            }
        }

        protected Integer doInBackground(byte[]... params) {
            String response = "";
            URL url = null;
            try {
                url = new URL("http://api.infinigentconsulting.com/api/Tests");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                byte[] passed = params[0];
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                assert conn != null;
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                JSONObject jsonObject = new JSONObject();
                JSONStringer userJson = new JSONStringer()
                        .object()
                        .key("test")
                        .object()
                        .key("Number").value("Hridoy")
                        .key("ImageLocation").value(passed)
                        .key("IsSignature").value(false)
                        .endObject()
                        .endObject();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
                outputStreamWriter.write(userJson.toString());
                outputStreamWriter.close();
                int responseCode = conn.getResponseCode();
                if (conn.getResponseCode() == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while (true) {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        response = response + line;
                    }
                } else {
                    response = "";
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            Integer result = 0;
            JSONObject jObject = null;
            if (!response.isEmpty()) {
                try {
                    jObject = new JSONObject(response);
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
                try {
                    result = Integer.parseInt(jObject.getString("GetAuthResult"));
                } catch (JSONException e32) {
                    e32.printStackTrace();
                }
            }
            return result;
        }
    }

    private void Toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public byte[] imageViewToByte(ImageButton image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public byte[] bitmapToByteForPreview(Bitmap bitmap) {
        // Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 75, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE_ONE) {
            try {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_ONE);
                } else {
                    Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception ex) {
                Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
        if (requestCode == MY_CAMERA_PERMISSION_CODE_TWO) {
            try {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_TWO);
                } else {
                    Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception ex) {
                Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == MY_CAMERA_PERMISSION_CODE_THREE) {
            try {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_THREE);
                } else {
                    Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception ex) {
                Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_ONE && resultCode == Activity.RESULT_OK) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");

                byte [] byteImage = bitmapToByteForPreview(photo);
                Bitmap bmp = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
                //ImageView of Cover Photo
                try {
                    Glide.with(this).load(bmp).into(_shop_img_one);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // _shop_img_one.setImageBitmap(photo);
                images.set(1, byteImage);
                isShopeImageOneAvailable = true;
            }catch (Exception ex) {
                Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == CAMERA_REQUEST_TWO && resultCode == Activity.RESULT_OK) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                // _shop_img_two.setImageBitmap(photo);
                byte [] byteImage = bitmapToByteForPreview(photo);
                Bitmap bmp = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
                //ImageView of Cover Photo
                try {
                    Glide.with(this).load(bmp).into(_shop_img_two);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // _shop_img_one.setImageBitmap(photo);
                images.set(2, byteImage);
                isShopeImageTwoAvailable = true;
            }catch (Exception ex) {
                Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == CAMERA_REQUEST_THREE && resultCode == Activity.RESULT_OK) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                //_shop_img_three.setImageBitmap(photo);
                byte [] byteImage = bitmapToByteForPreview(photo);
                Bitmap bmp = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
                //ImageView of Cover Photo
                try {
                    Glide.with(this).load(bmp).into(_shop_img_three);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // _shop_img_one.setImageBitmap(photo);
                images.set(3, byteImage);
                isShopeImageThreeAvailable = true;
            }
            catch (Exception ex) {
                Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            try {
                byte[] byteArray = data.getByteArrayExtra("bitmap");
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                // _signature_upload.setImageBitmap(bmp);
                try {
                    Glide.with(this).load(bmp).into(_signature_upload);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                images.set(0, byteArray);
                _signature_upload.setBackgroundResource(R.drawable.border);
                isSignatureAvailable = true;
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                Toast.makeText(this, "image not loaded", Toast.LENGTH_LONG).show();

            }
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    try {
                        Intent  i = new Intent(ImageBrowsingActivity.this, MainActivity.class);
                        startActivity(i);
                    } catch (Exception ex) {
                        Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
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
                        Intent  i = new Intent(ImageBrowsingActivity.this, LoginActivity.class);
                        startActivity(i);
                    } catch (Exception ex) {
                        Toast.makeText(ImageBrowsingActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
    }
}