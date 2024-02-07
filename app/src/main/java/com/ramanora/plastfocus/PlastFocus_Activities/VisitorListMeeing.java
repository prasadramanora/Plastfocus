package com.ramanora.plastfocus.PlastFocus_Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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
import com.ramanora.plastfocus.PlastFocus_Adapter.VisitorMeetingAdapter;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;

import com.ramanora.plastfocus.PlastFocus_Utils.AppStatus;
import com.ramanora.plastfocus.PlastFocus_Utils.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class VisitorListMeeing extends AppCompatActivity {
    public static String MyPREFERENCES = "myprefe";
    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;
    DataBaseHandler dataBaseHandler;
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
    ArrayList<PlastFocusModelClass> mraayVisitormeetings;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    VisitorMeetingAdapter adapter;
    ImageView iv_sync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitormeeting);
        dataBaseHandler = new DataBaseHandler(this);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iv_sync = findViewById(R.id.iv_sync);
        iv_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(VisitorListMeeing.this).isOnline()) {
                    getvisitormeetingApi();
                }else {
                    Toast.makeText(VisitorListMeeing.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

               // getvisitormeetingApi();
            }
        });
        sh = getSharedPreferences(MyPREFERENCES, 0);
        editor = sh.edit();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        mraayVisitormeetings = new ArrayList<>();

        List<PlastFocusModelClass> mraayVisitormeetings = dataBaseHandler.getVisitorMeetingData();

        Log.e("MyMeeting", mraayVisitormeetings.size() + "");
        if (mraayVisitormeetings.size() > 0) {
            adapter = new VisitorMeetingAdapter((Context) VisitorListMeeing.this, (ArrayList<PlastFocusModelClass>) mraayVisitormeetings);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            if (AppStatus.getInstance(VisitorListMeeing.this).isOnline()) {
                getvisitormeetingApi();
            }else {
                if (mraayVisitormeetings.size() > 0) {
                    getMeetindData();
                }
              //  Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }

        }
        //  getvisitormeetingApi();
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

    public void getvisitormeetingApi() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);

        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.delete("ExhibitorListMeeting", null, null);

        ProgressDialog pd = new ProgressDialog(VisitorListMeeing.this,R.style.StyledDialog);
        pd.setMessage(" Visitors Meetings Loaded ..");
        pd.setCancelable(false);
        pd.show();
        String UserId = sh.getString("UserId", null);
        String url = Plastfocus_App_Api_List.Api_VisitorMeetings + UserId + "/my-meetings";

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
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("exhibitor");
                                Log.e("MyJsonObject1", jsonObject2.toString());

                                id = jsonObject2.getString("id");
                                pojoHomePojo.setId(id);
                                company_name = jsonObject2.getString("company_name");
                                pojoHomePojo.setCompany_name(company_name);
                                salutation = jsonObject2.getString("salutation");
                                pojoHomePojo.setSalutation(salutation);
                                coordinator_name = jsonObject2.getString("coordinator_first_name");
                                pojoHomePojo.setCoordinator_name(coordinator_name);
                                coordinator_last_name = jsonObject2.getString("coordinator_last_name");
                                pojoHomePojo.setCoordinator_last_name(coordinator_last_name);
                                coordinator_designation = jsonObject2.getString("coordinator_designation");
                                pojoHomePojo.setCoordinator_designation(coordinator_designation);
                                address = jsonObject2.getString("address");
                                pojoHomePojo.setAddress(address);
                                country = jsonObject2.getString("country");
                                pojoHomePojo.setCountry(country);
                                state = jsonObject2.getString("state");
                                pojoHomePojo.setState(state);
                                city = jsonObject2.getString("city");
                                pojoHomePojo.setCity(city);
                                pincode = jsonObject2.getString("pincode");
                                pincode=pincode.replace("null","-");
                                pojoHomePojo.setPincode(pincode);
                                mobile_number = jsonObject2.getString("mobile_number");
                                pojoHomePojo.setMobile_number(mobile_number);
                                alternate_number = jsonObject2.getString("alternate_number");
                                alternate_number=alternate_number.replace("null","-");
                                pojoHomePojo.setAlternate_number(alternate_number);
                                fax = jsonObject2.getString("fax");
                                fax=fax.replace("null","");
                                pojoHomePojo.setFax(fax);
                                email = jsonObject2.getString("email");
                                pojoHomePojo.setEmail(email);
                                alternate_email = jsonObject2.getString("alternate_email");
                                alternate_email=alternate_email.replace("null","-");
                                pojoHomePojo.setAlternate_email(alternate_email);
                                website = jsonObject2.getString("website");
                                website=website.replace("null","-");
                                pojoHomePojo.setWebsite(website);
                                JSONArray roduct_group = jsonObject2.getJSONArray("product_group");
                                /*for(int j=0;j<roduct_group.length();j++)
                                {
                                    Log.e("MyJSonData", String.valueOf(roduct_group.get(j)));
                                    pojoHomePojo.setProduct_group(String.valueOf(roduct_group.get(j)));
                                }*/
                                pojoHomePojo.setProduct_group(roduct_group.toString());
                                company_profile = jsonObject2.getString("company_profile");
                                pojoHomePojo.setCompany_profile(company_profile);
                                hall = jsonObject2.getString("hall");
                                hall=hall.replace("null","-");
                                pojoHomePojo.setHall(hall);

                                stall_number = jsonObject2.getString("stall_number");
                                stall_number=stall_number.replace("null","-");
                                pojoHomePojo.setStall_number(stall_number);


                                cord_x = jsonObject2.getString("cord_x");
                                pojoHomePojo.setCord_x(cord_x);
                                cord_y = jsonObject2.getString("cord_y");
                                pojoHomePojo.setCord_y(cord_y);
                                Log.e("company_name", company_name);
                                Log.e("mobile_number", mobile_number);
                                dataBaseHandler.InserExhibitorDataMeeting(pojoHomePojo);

                            }
                            getMeetindData();


                            pd.dismiss();
                            Toast.makeText(VisitorListMeeing.this, js.length() + " " + "Meeting Found", Toast.LENGTH_SHORT).show();


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
                    VolleyErrorHelper.errorcoderesponce(message, VisitorListMeeing.this);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.d("test", "onErrorResponse: Error " + error.getMessage());


            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    private void getMeetingDateandTimeApi() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        // DataBaseHandler dataBaseHandler = new DataBaseHandler(VisitorList.this);
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.delete("MeetingShedule", null, null);
        ProgressDialog pd = new ProgressDialog(VisitorListMeeing.this,R.style.StyledDialog);

        String UserId = sh.getString("UserId", null);
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
                            PlastFocusModelClass pojoHomePojo=new PlastFocusModelClass();
                            pojoHomePojo.setMeetingdates(jsonObject1.toString());
                            pojoHomePojo.setMeetingtitle("datesandtime");
                            dataBaseHandler.AddMeetigDatesScheduleInfo(pojoHomePojo);

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
                    VolleyErrorHelper.errorcoderesponce(message, VisitorListMeeing.this);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.d("test", "onErrorResponse: Error " + error.getMessage());


            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void getMeetindData() {
        List<PlastFocusModelClass> mraayVisitormeetings = dataBaseHandler.getVisitorMeetingData();

        Log.e("MyMeeting", mraayVisitormeetings.size() + "");
        if (mraayVisitormeetings.size() > 0) {
            try {


                for (int i = 0; i < mraayVisitormeetings.size(); i++) {
                    for (int j = i + 1; j < mraayVisitormeetings.size(); j++) {
                        if (mraayVisitormeetings.get(i).getCompany_name().equals(mraayVisitormeetings.get(j).getCompany_name())) {
                            mraayVisitormeetings.remove(j);
                            j--;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            adapter = new VisitorMeetingAdapter((Context) VisitorListMeeing.this, (ArrayList<PlastFocusModelClass>) mraayVisitormeetings);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}
