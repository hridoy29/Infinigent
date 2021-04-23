package com.example.infinigentconsulting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;
    private static final String TAG = "LoginActivity";
    Button _loginButton;
    EditText _passwordText;
    EditText _useridText;
    EditText _numberText;
    DatabaseHelper mDatabaseHelper;
    String prevStarted = "yes";
    private boolean IsInternetAvaiable = false;
    private boolean result = false;
    CheckBox _remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabaseHelper = new DatabaseHelper(this);
        this._useridText = (EditText) findViewById(R.id.input_userid);
        this._passwordText = (EditText) findViewById(R.id.input_password);
        this._numberText = (EditText) findViewById(R.id.input_number);
        this._loginButton = (Button) findViewById(R.id.btn_login);
        this._remember = (CheckBox) findViewById(R.id.remember);
        final String MY_PREFS_NAME = "DeviceNumber";

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
      try {

          if (checkbox.equals("true")){
              Intent intent = new Intent(LoginActivity.this, MainActivity.class);
              startActivity(intent);
          }else{
            String username = preferences.getString("username","");
            String password = preferences.getString("password","");
            String number = preferences.getString("number","");
            this._useridText.setText(username);
            this._passwordText.setText(password);
            this._numberText.setText(number);
          }
      }catch (Exception ex) {
          Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
      }



        this._loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putInt("Id", Integer.parseInt(_numberText.getText().toString()));
                    editor.apply();
                    login();
                } catch (Exception ex) {
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        this._remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("remember","true");
                    edit.putString("username",_useridText.getText().toString());
                    edit.putString("password",_passwordText.getText().toString());
                    edit.putString("number",_numberText.getText().toString());
                    edit.apply();
                }else if(!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("remember","false");
                    edit.apply();
                }
            }
        });


    }

    public void login() {
        if (validate()) {
            String user_id = this._useridText.getText().toString();
            String password = this._passwordText.getText().toString();
            ArrayList<String> passing = new ArrayList();
            passing.add(user_id);
            passing.add(password);
            // new UserAuthCheck().execute(passing);
            onLoginSuccess();
            return;
        }
        onLoginFailed();
    }

    public boolean validate() {
        boolean valid = false;
        String user_id = this._useridText.getText().toString();
        String password = this._passwordText.getText().toString();
        // mDatabaseHelper.getUserValidation(user_id,password);
        if (user_id.isEmpty()) {
            this._useridText.setError("enter a user id");
            valid = false;
        } else {
            this._useridText.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            this._passwordText.setError("between 4 and 10 alphanumeric characters");
            return false;
        }
       if(mDatabaseHelper.getUserValidation(user_id,password))
        {
           // this._passwordText.setError("Invalid User ID or Password");
           valid = true;
        }else{
          getUsers(user_id,password);
       }
        this._passwordText.setError(null);
        return valid;
    }

    public void onLoginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        int _userid=0;
      /*  Cursor cursor=mDatabaseHelper.getUserId(this._useridText.getText().toString(),this._passwordText.getText().toString());
        while (cursor.moveToNext()) {
            _userid= cursor.getInt(0);
        }*/
        SharedPreferences.Editor editor = getSharedPreferences("UserId", MODE_PRIVATE).edit();
        editor.putString("Id", this._useridText.getText().toString());
        editor.putString("Password", this._passwordText.getText().toString());
        editor.apply();

        bundle.putInt("userid", _userid);
        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onLoginFailed() {
        this._loginButton.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }

    public class UserAuthCheck extends AsyncTask<ArrayList<String>, Void, Integer> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            if (i == 1) {
                onLoginSuccess();

            } else {
                onLoginFailed();
            }
        }

        protected Integer doInBackground(ArrayList<String>... params) {
            String response = "";
            URL url = null;
            try {
                url = new URL("http://192.168.99.15:8080/AuthService.svc/GetAuth");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                ArrayList<String> passed = params[0];
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
                JSONStringer userJson = new JSONStringer().object().key("user").object().key("userid").value(passed.get(0)).key("password").value(((String) passed.get(1))).endObject().endObject();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
                outputStreamWriter.write(userJson.toString());
                outputStreamWriter.close();
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

    private void getUsers(String userName, String password) {

        Call<ArrayList<UserClass>> call = RetrofitClient.getInstance().getApi().getUserList();
        call.enqueue(new Callback<ArrayList<UserClass>>() {

            @Override
            public void onResponse(Call<ArrayList<UserClass>> call, Response<ArrayList<UserClass>> response) {
                ArrayList<UserClass> userList = response.body();
                
                if (userList.size() > 1) {
                    for (int i = 0; i <userList.size();i++){
                        if(userList.get(i).getEmail().equals(userName) && userList.get(i).getPassword().equals(password)){
                            onLoginSuccess();

                        }
                    }
                } else {
                    // onLoginFailed();
                    Toast.makeText(LoginActivity.this, "Please keep your internet connected", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<UserClass>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//   private void AddUserList(ArrayList<UserClass> UserList) {
//
//        for (UserClass item : UserList) {
//            boolean isInserted = mDatabaseHelper.insertUserList(item.Name, item.Email, item.MobileNo, item.Password, item.IsActive);
//        }
//        Cursor res = mDatabaseHelper.getUserList();
//        if (res.getCount() != 0) {
//            Toast.makeText(LoginActivity.this, "User List Inserted", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(LoginActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
//        }
//
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
//        if (!sharedpreferences.getBoolean(prevStarted, false)) {
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putBoolean(prevStarted, Boolean.TRUE);
//            editor.apply();
//
//            if (internetIsConnected() == true) {
//                try {
//                    getUsers();
//                } catch (Exception ex) {
//                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//            else {
//                Toast.makeText(LoginActivity.this, "Internet Is Not Available,Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    private boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            IsInternetAvaiable = (Runtime.getRuntime().exec(command).waitFor() == 0);
            return IsInternetAvaiable;
        } catch (Exception e) {
            return IsInternetAvaiable;
        }
    }

}