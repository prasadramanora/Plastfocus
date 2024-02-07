package com.ramanora.plastfocus.PlastFocus_Activities;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ramanora.plastfocus.PlastFocus_Adapter.MettingTimeDialogAdapter;
import com.ramanora.plastfocus.PlastFocus_Adapter.NoticationAdapter;
import com.ramanora.plastfocus.PlastFocus_ApiList.Plastfocus_App_Api_List;
import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.NotificationModel;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.QrcodeModel;
import com.ramanora.plastfocus.PlastFocus_Utils.AppStatus;
import com.ramanora.plastfocus.PlastFocus_Utils.VolleyErrorHelper;
import com.ramanora.plastfocus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityNotification extends AppCompatActivity {
    LinearLayoutManager manager;
    RecyclerView recyclerView;
    String url;
    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;
    public static String MyPREFERENCES = "myprefe";
    ArrayList<NotificationModel> notificationlist = new ArrayList<>();
    ImageView iv_sync;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iv_sync = findViewById(R.id.iv_sync);
        iv_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(ActivityNotification.this).isOnline()) {
                    notificationlist.clear();
                    NotificationApi();
                } else {
                    Toast.makeText(ActivityNotification.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        if (AppStatus.getInstance(ActivityNotification.this).isOnline()) {
            NotificationApi();
        } else {
            Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }
    @SuppressLint("NewApi")
    public static String formatDate(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    private void NotificationApi() {
        SharedPreferences.Editor sharedEditor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();
        // String str_login_test = ActivityMainHomePage.sh.getString("loginTest1", null);
        sh = getSharedPreferences(MyPREFERENCES, 0);
        String CheckLogin = sh.getString("CheckLogin", null);
        ProgressDialog pd = new ProgressDialog(ActivityNotification.this, R.style.StyledDialog);
        pd.setMessage("Update Notification Data");
        pd.show();
        if (CheckLogin.equals("Visitor")) {
            url = Plastfocus_App_Api_List.Api_VisitorNotification;
            // String url = "https://ramanora.com/plastfocus/public/api/{{visitor-id}}/my-meetings?" + UserId ;
            Log.d("Notification", url);
        } else {
            url = Plastfocus_App_Api_List.Api_ExhibitorNotification;
            // String url = "https://ramanora.com/plastfocus/public/api/{{visitor-id}}/my-meetings?" + UserId ;
            Log.d("Notification", url);
        }
        //String UserId = sh.getString("UserId", null);

        //String url = "https://ramanora.com/plastfocus/public/api/exhibitors";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d("UI thread", "I am the UI thread");

                        Log.e("ResponceNotification", response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray data = jsonObject.getJSONArray("data");


                            for (int i = 0; i < data.length(); i++) {
                                NotificationModel notificationModel = new NotificationModel();

                                JSONObject jsonObject1 = data.getJSONObject(i);
                                String title = jsonObject1.getString("title");
                                String message = jsonObject1.getString("message");
                                String created_at = jsonObject1.getString("created_at");

                              //  @SuppressLint({"NewApi", "LocalSuppress"}) String formattedDateTime = formatDate(LocalDateTime.parse(created_at), "yyyy-MM-dd HH:mm:ss");
                                notificationModel.setTitle(title);
                                notificationModel.setMessage(message);
                                notificationModel.setCreatedat(created_at);
                                notificationlist.add(notificationModel);
                                recyclerView.setHasFixedSize(true);
                                manager = new LinearLayoutManager(ActivityNotification.this, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(manager);
                                NoticationAdapter meetingsDateDialogAdapter = new NoticationAdapter(ActivityNotification.this, notificationlist);
                                recyclerView.setAdapter(meetingsDateDialogAdapter);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        pd.dismiss();
                        // Toast.makeText(ActivityNotification.this, "Meeting Dates and Time Updated Sucessfully.", Toast.LENGTH_SHORT).show();

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
                    VolleyErrorHelper.errorcoderesponce(message, ActivityNotification.this);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.d("test", "onErrorResponse: Error " + error.getMessage());


            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), ActivityMainHomePage.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), ActivityMainHomePage.class);
        startActivity(i);
        finish();
    }
}
