package com.ramanora.plastfocus.PlastFocus_Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ramanora.plastfocus.PlastFocus_ApiList.Plastfocus_App_Api_List;
import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Adapter.ExhibitorMeetingAdapter;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;

import com.ramanora.plastfocus.PlastFocus_Utils.AppStatus;
import com.ramanora.plastfocus.PlastFocus_Utils.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExhibitorListMeeting extends Activity {
    public static String MyPREFERENCES = "myprefe";
    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;
    DataBaseHandler dataBaseHandler;
    List<PlastFocusModelClass> mraayVisitormeetings;
    String id = "", company_name = "",
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
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    ImageView iv_sync;
    ExhibitorMeetingAdapter adapter;
   public static  String Checkmeeting = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitormeeting);
        dataBaseHandler = new DataBaseHandler(this);

        sh = getSharedPreferences(MyPREFERENCES, 0);
        editor = sh.edit();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        iv_sync = findViewById(R.id.iv_sync);
        iv_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(ExhibitorListMeeting.this).isOnline()) {
                    getvisitormeetingApi();
                } else {
                    Toast.makeText(ExhibitorListMeeting.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

                }
            }
        });
        if (AppStatus.getInstance(ExhibitorListMeeting.this).isOnline()) {
            getvisitormeetingApi();

        } else {
            List<PlastFocusModelClass> mraayVisitormeetings = dataBaseHandler.getExhibitorMeetingData();

            Log.e("MyMeeting", mraayVisitormeetings.size() + "");
            if (mraayVisitormeetings.size() > 0) {
               getMeetindData();
            } else {
                Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

            }
        }
        Intent intent = getIntent();
       // CheckNewNotificationMeeting = intent.getStringExtra("CheckNewNotificationMeeting");

        if (intent.hasExtra("Checkmeeting")) {
            Checkmeeting = intent.getStringExtra("Checkmeeting");
            mraayVisitormeetings = dataBaseHandler.getExhibitorMeetingData();

            Log.e("MyMeeting", mraayVisitormeetings.size() + "");
            if (mraayVisitormeetings.size() > 0) {

            }else {
                getvisitormeetingApi();
            }

            Log.e("Checkmeeting", Checkmeeting);
        }else {
             Checkmeeting = intent.getStringExtra("Checkmeeting");
            Log.e("Checkmeeting", "NotFound");
        }



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
    public void getvisitormeetingApi() {
       // DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.delete("VisitorListMeeting", null, null);

        ProgressDialog pd = new ProgressDialog(ExhibitorListMeeting.this,R.style.StyledDialog);
        pd.setMessage(" Exhibitors Meetings Loaded ..");
        pd.setCancelable(false);
        pd.show();
        String UserId = sh.getString("UserId", null);
        String url = Plastfocus_App_Api_List.Api_ExhibitorMeetings + UserId + "/my-meetings";

        Log.d("VisitorListApi", url);
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
                            Log.d("VisitorListApi", jsonObject.toString());
                            JSONArray js = jsonObject.getJSONArray("data");
                            for (int i = 0; i < js.length(); i++) {
                                JSONObject jsonObject1 = js.getJSONObject(i);
                                Log.e("MyJsonObject", jsonObject1.toString());
                                PlastFocusModelClass pojoHomePojo = new PlastFocusModelClass();
                                pojoHomePojo.setMid(jsonObject1.getString("id"));
                                pojoHomePojo.setVisitor_id(jsonObject1.getString("visitor_id"));
                                pojoHomePojo.setExhibitor_id(jsonObject1.getString("exhibitor_id"));
                                pojoHomePojo.setMeeting_date(jsonObject1.getString("meeting_date"));
                                pojoHomePojo.setMeeting_time(jsonObject1.getString("meeting_time"));
                                pojoHomePojo.setMeeting_start_date(jsonObject1.getString("meeting_start_date"));
                                pojoHomePojo.setMeeting_status(jsonObject1.getString("meeting_status"));
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("visitor");
                                Log.e("MyJsonObject1", jsonObject2.toString());


                                id = jsonObject2.getString("id");
                                pojoHomePojo.setMid(id);
                                company_name = jsonObject2.getString("company");
                                pojoHomePojo.setCompany_name(company_name);

                                coordinator_name = jsonObject2.getString("full_name");
                                pojoHomePojo.setCoordinator_name(coordinator_name);
                                Log.e("company_name", company_name);
                                Log.e("mobile_number", coordinator_name);

                                coordinator_designation = jsonObject2.getString("designation");
                                pojoHomePojo.setCoordinator_designation(coordinator_designation);
                                address = jsonObject2.getString("address");
                                pojoHomePojo.setAddress(address);
                                country = jsonObject2.getString("country");
                                pojoHomePojo.setCountry(country);
                                state = jsonObject2.getString("state");
                                pojoHomePojo.setState(state);
                                city = jsonObject2.getString("city");
                                pojoHomePojo.setCity(city);
                                pincode = jsonObject2.getString("zip_code");
                                pojoHomePojo.setPincode(pincode);
                                mobile_number = jsonObject2.getString("phone_number");
                                pojoHomePojo.setMobile_number(mobile_number);


                                email = jsonObject2.getString("email");
                                pojoHomePojo.setEmail(email);


                                JSONArray roduct_group = jsonObject2.getJSONArray("products");
                                /*for(int j=0;j<roduct_group.length();j++)
                                {
                                    Log.e("MyJSonData", String.valueOf(roduct_group.get(j)));
                                    pojoHomePojo.setProduct_group(String.valueOf(roduct_group.get(j)));
                                }*/
                                pojoHomePojo.setProduct_group(roduct_group.toString());


                                dataBaseHandler.InserVisitorDataMeeting(pojoHomePojo);

                              //  getMeetingDateandTimeApi();

                            }
                            Toast.makeText(ExhibitorListMeeting.this, js.length() + " " + "Meeting Loaded", Toast.LENGTH_SHORT).show();

                            pd.dismiss();
                            getMeetindData();

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
                    JSONObject jsonObject1=new JSONObject(body.toString());
                    String message=jsonObject1.getString("message");
                    VolleyErrorHelper.errorcoderesponce(message, ExhibitorListMeeting.this);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.d("test", "onErrorResponse: Error " + error.getMessage());


            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), ActivityMainHomePage.class);
        startActivity(i);
        finish();
    }

    private void getMeetindData() {

        List<PlastFocusModelClass> mraayVisitormeetings = dataBaseHandler.getExhibitorMeetingData();
        Collections.reverse(mraayVisitormeetings);
        Log.e("MyMeeting", mraayVisitormeetings.size() + "");
        if (mraayVisitormeetings.size() > 0) {
              adapter = new ExhibitorMeetingAdapter((Context) ExhibitorListMeeting.this, (ArrayList<PlastFocusModelClass>) mraayVisitormeetings);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
