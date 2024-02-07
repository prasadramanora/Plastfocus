package com.ramanora.plastfocus.PlastFocus_Activities;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;

import com.ramanora.plastfocus.PlastFocus_FirebaseServices.MyFirebaseMessagingService;
import com.ramanora.plastfocus.PlastFocus_ApiList.Plastfocus_App_Api_List;
import com.ramanora.plastfocus.PlastFocus_Utils.AppStatus;
import com.ramanora.plastfocus.PlastFocus_Utils.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class VIsitorLoginScreen extends AppCompatActivity implements View.OnClickListener {
    EditText edt_email, edt_mobilepassword;
    PlastFocusModelClass productdata;
    String str_Name = "";
    String str_country = "";
    String str_city = "";
    String str_state = "";
    String zipcode = "";
    String str_Designation = "";
    String str_Company = "";
    String Str_addtress = "";
    String str_Email = "";
    String str_FullPhone = "";
    TextView tv_registration, tv_exhibitor;
    Button btn_submit;
    String id = "", company_name = "", str_QrCode = "",
            salutation = "",
            coordinator_name = "",
            coordinator_last_name = "",
            coordinator_designation = "",
            address = "",
            country = "",
            state = "",
            city = "",
            pincode = "",
            mobile_number = "",
            alternate_number = "",
            fax = "",
            email = "",
            alternate_email = "",
            website = "",
            product_group = "",
            company_profile = "",
            hall = "",
            stall_number = "",
            cord_x = "",
            cord_y = "";
    ScrollView scrollview;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt_email = findViewById(R.id.edt_email);
        //  scrollview= findViewById(R.id.scrollview);
        tv_exhibitor = findViewById(R.id.tv_exhibitor);
        getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_PAN);
        tv_exhibitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ExhibitorLogin.class);
                startActivity(i);
                finish();
            }
        });
        edt_mobilepassword = findViewById(R.id.edt_mobilepassword);
        edt_mobilepassword.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        tv_registration = findViewById(R.id.tv_registration);
        tv_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegistrationScreen.class);
                startActivity(i);
                finish();
            }
        });
        getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_PAN);

    }


    private void LoginApi(String email, String phonenumber) {


        ProgressDialog pd = new ProgressDialog(VIsitorLoginScreen.this, R.style.StyledDialog);
        pd.setMessage("Login Visitor Processing..");


        pd.show();


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("phone_number", phonenumber);
            jsonObject.put("apns_token", MyFirebaseMessagingService.firebasetoken);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Create a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // String url = ApiList.Api_Login;
        System.out.println("Login: " + Plastfocus_App_Api_List.Api_Login);
        System.out.println("Login: " + jsonObject.toString());
        // Create a JsonObjectRequest with POST method, URL, headers, and parameters
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Plastfocus_App_Api_List.Api_Login, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Login: " + response.toString());

                        pd.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response.toString());
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                            String Userid = jsonObject2.getString("id");
                            SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();
                            str_Name = jsonObject2.getString("full_name");
                            str_country = jsonObject2.getString("country");
                            str_city = jsonObject2.getString("city");
                            str_state = jsonObject2.getString("state");
                            zipcode = jsonObject2.getString("zip_code");
                            str_Designation = jsonObject2.getString("designation");
                            str_Company = jsonObject2.getString("company");
                            Str_addtress = jsonObject2.getString("address") + " , " + jsonObject2.getString("city") + " , " + jsonObject2.getString("state") + " , " + jsonObject2.getString("zip_code");
                            str_Email = jsonObject2.getString("email");
                            str_FullPhone = jsonObject2.getString("phone_number");
                            // str_QrCode = "PlatFocus" + "\t" + str_Name + "\t" + str_Designation + "\t" +  str_Company + "\t" + str_Country +"\t" + str_Email + "\t" + str_FullPhone;

                            str_QrCode = "PlatFocus Visitor" + "\t" + str_Name + "\t" + str_Designation + "\t" + str_Company + "\t" + Str_addtress + "\t" + str_Email + "\t" + str_FullPhone + "\t" + "0";
                            editor.putString("imagePreferance", str_QrCode);
                            editor.putString("Name", str_Name);
                            editor.putString("Address", Str_addtress);
                            editor.putString("Designation", str_Designation);
                            editor.putString("Company", str_Company);
                            editor.putString("Emailid", str_Email);
                            editor.putString("loginTest1", "true");
                            editor.putString("UserId", Userid);
                            editor.putString("CheckLogin", "Visitor");

                            editor.commit();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        getMeetingDateandTimeApi();

                        // new LoadExhibitorDta().execute("ExhibitorData");
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
                        // String statusCode = String.valueOf(error.networkResponse.statusCode);
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
                            VolleyErrorHelper.errorcoderesponce(message, VIsitorLoginScreen.this);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                        // Toast.makeText(LoginScreen.this, body.toString(), Toast.LENGTH_SHORT).show();
                        // System.out.println("AlohaCAllRecording" +body.toString());
                    }
                }) {

        };

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);

    }




    private void getMeetingDateandTimeApi() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        // DataBaseHandler dataBaseHandler = new DataBaseHandler(VisitorList.this);
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.delete("MeetingShedule", null, null);
        ProgressDialog pd = new ProgressDialog(VIsitorLoginScreen.this, R.style.StyledDialog);

        //String UserId = sh.getString("UserId", null);
        String url = "https://ramanora.com/plastfocus/public/api/schedule";
        // String url = "https://ramanora.com/plastfocus/public/api/{{visitor-id}}/my-meetings?" + UserId ;
        Log.d("VisitorDateANdTime", url);
        //String url = "https://ramanora.com/plastfocus/public/api/exhibitors";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d("UI thread", "I am the UI thread");

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.d("VisitorDateANdTime", jsonObject.toString());
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            Log.d("VisitorDateANdTime", jsonObject1.toString());
                            PlastFocusModelClass pojoHomePojo = new PlastFocusModelClass();
                            pojoHomePojo.setMeetingdates(jsonObject1.toString());
                            pojoHomePojo.setMeetingtitle("datesandtime");
                            dataBaseHandler.AddMeetigDatesScheduleInfo(pojoHomePojo);
                            Intent intent = new Intent(VIsitorLoginScreen.this, ActivityMainHomePage.class);
                            startActivity(intent);
                            finish();
                            //dataBaseHandler.AddMeetigScheduleInfo(pojoHomePojo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                //  Toast.makeText(Activity_Login.this, Utils.VOLLEY_ERROR_MSG, Toast.LENGTH_SHORT).show();
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
                    VolleyErrorHelper.errorcoderesponce(message, VIsitorLoginScreen.this);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.d("test", "onErrorResponse: Error " + error.getMessage());


            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_submit) {
            if (edt_email.getText().toString().length() == 0) {
                edt_email.setError("Enter Email");
                edt_email.requestFocus();
            } else if (edt_mobilepassword.getText().toString().length() == 0) {
                edt_mobilepassword.setError("Enter Mobile");
                edt_mobilepassword.requestFocus();
            } else {
                if (AppStatus.getInstance(VIsitorLoginScreen.this).isOnline()) {

                    LoginApi(edt_email.getText().toString(), edt_mobilepassword.getText().toString());
                } else {
                    Toast.makeText(VIsitorLoginScreen.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
