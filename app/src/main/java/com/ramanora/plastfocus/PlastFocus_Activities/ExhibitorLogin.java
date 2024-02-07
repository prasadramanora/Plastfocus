package com.ramanora.plastfocus.PlastFocus_Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;

import com.ramanora.plastfocus.PlastFocus_ApiList.Plastfocus_App_Api_List;
import com.ramanora.plastfocus.PlastFocus_Utils.AppStatus;
import com.ramanora.plastfocus.PlastFocus_Utils.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ExhibitorLogin extends AppCompatActivity {
    Button btn_submit;
    TextView tv_registration, tv_visitor;
    String str_Name = "", str_Lname = "";
    String str_country = "";
    String str_city = "";
    String str_state = "";
    String zipcode = "";
    String str_Designation = "";
    String str_Company = "";
    String Str_addtress = "";
    String str_Email = "";
    String str_FullPhone = "";
    SharedPreferences sharedpreferences;
    String str_QrCode = "";

    EditText edt_email, edt_mobilepassword;
    public static String firebasetoken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitorlogin);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_submit = findViewById(R.id.btn_submit);

        FirebaseMessaging messaging = FirebaseMessaging.getInstance();
        messaging.getToken().addOnSuccessListener(s -> {
            Log.e("Tkencheck55", s);

            firebasetoken = s;


        });


        tv_visitor = findViewById(R.id.tv_visitor);
        tv_registration = findViewById(R.id.tv_registration);
        edt_email = findViewById(R.id.edt_email);
        edt_mobilepassword = findViewById(R.id.edt_mobilepassword);
        tv_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegistrationScreen.class);
                startActivity(i);
                finish();
            }
        });
        tv_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), VIsitorLoginScreen.class);
                startActivity(i);
                finish();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_email.getText().toString().length() == 0) {
                    edt_email.setError("Enter Email");
                    edt_email.requestFocus();
                } else if (edt_mobilepassword.getText().toString().length() == 0) {
                    edt_mobilepassword.setError("Enter Mobile");
                    edt_mobilepassword.requestFocus();
                } else {

                    if (AppStatus.getInstance(ExhibitorLogin.this).isOnline()) {
                        sharedpreferences = getSharedPreferences(ActivityMainHomePage.MyPREFERENCES,
                                Context.MODE_PRIVATE);
                        String registertoken = sharedpreferences.getString("registertoken", "");
                        LoginApi(edt_email.getText().toString(), edt_mobilepassword.getText().toString(), firebasetoken);
                    } else {
                        Toast.makeText(ExhibitorLogin.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void LoginApi(String emaill, String phonenumber, String token) {


        ProgressDialog pd = new ProgressDialog(ExhibitorLogin.this, R.style.StyledDialog);
        pd.setMessage("Exhibitor Login Processing..");
        pd.show();

        DataBaseHandler dataBaseHandler = new DataBaseHandler(ExhibitorLogin.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", emaill);
            jsonObject.put("password", phonenumber);
            jsonObject.put("apns_token", token);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Create a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Plastfocus_App_Api_List.Api_ExhibitorLogin;
        System.out.println("ExhibitorLogi66: " + url);
        System.out.println("ExhibitorLogin66: " + jsonObject.toString());
        // Create a JsonObjectRequest with POST method, URL, headers, and parameters
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Login: " + response.toString());
                        SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();
                        try {
                            JSONObject jsonObject2 = new JSONObject(response.toString());
                            JSONObject jsonObject = jsonObject2.getJSONObject("data");
                            String Userid = jsonObject.getString("id");
                            // SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();
                            str_Name = jsonObject.getString("coordinator_first_name");
                            str_Lname = jsonObject.getString("coordinator_last_name");
                            str_country = jsonObject.getString("country");
                            str_city = jsonObject.getString("city");
                            str_state = jsonObject.getString("state");
                            zipcode = jsonObject.getString("pincode");
                            str_Designation = jsonObject.getString("coordinator_designation");
                            str_Company = jsonObject.getString("company_name");
                            Str_addtress = jsonObject.getString("address") + " , " + jsonObject.getString("city") + " , " + jsonObject.getString("state") + " , " + jsonObject.getString("pincode");
                            str_Email = jsonObject.getString("email");
                            str_FullPhone = jsonObject.getString("mobile_number");

                            str_QrCode = "PlatFocus" + "\t" + str_Name + "\t" + str_Designation + "\t" + str_Company + "\t" + Str_addtress + "\t" + str_Email + "\t" + str_FullPhone + "\t" + "0";
                            editor.putString("imagePreferance", str_QrCode);
                            editor.putString("Name", str_Name);
                            editor.putString("Address", Str_addtress);
                            editor.putString("Designation", str_Designation);
                            editor.putString("Company", str_Company);
                            editor.putString("Emailid", str_Email);
                            editor.putString("loginTest1", "true");
                            editor.putString("UserId", Userid);
                            editor.putString("CheckLogin", "Exhibitor");

                            editor.commit();
                            Intent i = new Intent(getApplicationContext(), ActivityMainHomePage.class);
                            startActivity(i);
                            finish();
                            //getExhibitorData();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        //getExhibitorData();

                        //  Toast.makeText(LoginScreen.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(RegistrationScreen.this, "Network error", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        // Handle the error
                        String body = null;
                        //get status code here
                        String statusCode = String.valueOf(error.networkResponse.statusCode);
                        //get response body and parse with appropriate encoding
                        if (error.networkResponse.data != null) {
                            try {
                                body = new String(error.networkResponse.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            JSONObject jsonObject1 = new JSONObject(body.toString());
                            String message = jsonObject1.getString("message");
                            VolleyErrorHelper.errorcoderesponce(message, ExhibitorLogin.this);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        //  Toast.makeText(ExhibitorLogin.this, body.toString(), Toast.LENGTH_SHORT).show();
                        // System.out.println("AlohaCAllRecording" +body.toString());
                    }
                }) {

        };

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), LoginMenuActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), LoginMenuActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }


}
