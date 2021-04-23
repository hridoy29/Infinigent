package com.example.infinigentconsulting;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class SignaturePadActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    SignaturePad _signature_pad;
    Button _clearBtn, _saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_pad);
        try {
            verifyStoragePermissions(this);
        }catch (Exception ex) {
            Toast.makeText(SignaturePadActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            _signature_pad = findViewById(R.id.signature_pad);
            _clearBtn = findViewById(R.id.clear_button);
            _saveBtn = findViewById(R.id.save_button);
        }catch (Exception ex) {
            Toast.makeText(SignaturePadActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        _clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    _signature_pad.clear();
                }catch (Exception ex) {
                    Toast.makeText(SignaturePadActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                   Bitmap signatureBitmap = _signature_pad.getSignatureBitmap();
                   if (addJpgSignatureToGallery(signatureBitmap)) {
                       Toast.makeText(SignaturePadActivity.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                   } else {
                       Toast.makeText(SignaturePadActivity.this, "Unable to store the signature", Toast.LENGTH_SHORT).show();
                   }
                   if (addSvgSignatureToGallery(_signature_pad.getSignatureSvg())) {
                       Toast.makeText(SignaturePadActivity.this, "SVG Signature saved into the Gallery", Toast.LENGTH_SHORT).show();

                   } else {
                       Toast.makeText(SignaturePadActivity.this, "Unable to store the SVG signature", Toast.LENGTH_SHORT).show();
                   }

                   //Convert to byte array
                   ByteArrayOutputStream stream = new ByteArrayOutputStream();
                   signatureBitmap.compress(Bitmap.CompressFormat.PNG, 75, stream);
                   byte[] byteArray = stream.toByteArray();

                   Intent intent = getIntent();
                   intent.putExtra("bitmap", byteArray);
                   setResult(RESULT_OK, intent);

                   finish();
               }catch (Exception ex) {
                   Toast.makeText(SignaturePadActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
               }
            }
        });

        _signature_pad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Toast.makeText(SignaturePadActivity.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                try {
                    _clearBtn.setEnabled(true);
                    _saveBtn.setEnabled(true);
                }catch (Exception ex) {
                    Toast.makeText(SignaturePadActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onClear() {
                try {
                    _clearBtn.setEnabled(false);
                    _saveBtn.setEnabled(false);
                }
                catch (Exception ex) {
                    Toast.makeText(SignaturePadActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            String fileName =  String.format("Signature_%d.jpg", System.currentTimeMillis());
            Log.d("aaaafileName", fileName); //save this fileName to sharedPreference.
            File photo = new File(getAlbumStorageDir("SignaturePad"),fileName);
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        SignaturePadActivity.this.sendBroadcast(mediaScanIntent);
    }

    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {
            File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SignaturePadActivity.this, "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}