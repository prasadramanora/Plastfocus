package com.ramanora.plastfocus.PlastFocus_Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Adapter.ExhibitorMainAdapter;
import com.ramanora.plastfocus.PlastFocus_Adapter.MeetingsDateDialogAdapter;
import com.ramanora.plastfocus.PlastFocus_Adapter.MettingTimeDialogAdapter;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;

import com.ramanora.plastfocus.PlastFocus_ApiList.Plastfocus_App_Api_List;
import com.ramanora.plastfocus.PlastFocus_Utils.AppStatus;
import com.ramanora.plastfocus.PlastFocus_Utils.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BookMeeting extends AppCompatActivity {
    TextView tv_comapnyname;
    static TextView tv_selecttimeformat;
    TextView tv_name;
    TextView tv_hall;
    TextView tv_email;
    static TextView tv_selectdate;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    SearchView editTextsearch;
    MeetingsDateDialogAdapter meetingsDateDialogAdapter;
    Button btn_bookmeeting;
    public static ArrayList<PlastFocusModelClass> meetingdateslist = new ArrayList<>();
    public static ArrayList<PlastFocusModelClass> mettingtimelist = new ArrayList<>();
    public static String MyPREFERENCES = "myprefe";
    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;
    String UserId = "";
    Button btn_updaedates;
    DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmeeting);
        sh = getSharedPreferences(MyPREFERENCES, 0);
        editor = sh.edit();

        UserId = sh.getString("UserId", null);
        //getMyDatesANdTimeMeeting
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        tv_selectdate = findViewById(R.id.tv_selectdate);
        btn_updaedates = findViewById(R.id.btn_updaedates);
        btn_updaedates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(BookMeeting.this).isOnline()) {
                    getMeetingDateandTimeApi();

                } else {
                    Toast.makeText(BookMeeting.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_bookmeeting = findViewById(R.id.btn_bookmeeting);
        btn_bookmeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(BookMeeting.this).isOnline()) {
                    if (tv_selectdate.getText().toString().equals("Please Select Date")) {
                        Toast.makeText(BookMeeting.this, "Please Select Meeting Date", Toast.LENGTH_SHORT).show();
                    } else if (tv_selecttimeformat.getText().toString().equals("Please Select Time")) {
                        Toast.makeText(BookMeeting.this, "Please Select Meeting Time", Toast.LENGTH_SHORT).show();

                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(BookMeeting.this).create();
                        alertDialog.setTitle("other");
                        alertDialog.setMessage("Are you sure want to confirm Meeting?");
                        alertDialog.setIcon(R.drawable.applogo);

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (AppStatus.getInstance(BookMeeting.this).isOnline()) {
                                    BookMeeting(tv_selectdate.getText().toString(), tv_selecttimeformat.getText().toString(), UserId, ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getId());

                                } else {
                                    Toast.makeText(BookMeeting.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();


                    }
                } else {
                    Toast.makeText(BookMeeting.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_selecttimeformat = findViewById(R.id.tv_selecttimeformat);

        tv_selecttimeformat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(BookMeeting.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialogmeetingtimes);

                recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_exhibitor_az);
                Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                recyclerView.setHasFixedSize(true);
                manager = new LinearLayoutManager(BookMeeting.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);
                MettingTimeDialogAdapter meetingsDateDialogAdapter = new MettingTimeDialogAdapter(BookMeeting.this, mettingtimelist, dialog);
                recyclerView.setAdapter(meetingsDateDialogAdapter);
                meetingsDateDialogAdapter.notifyDataSetChanged();
                dialog.show();
            }
        });
        tv_selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(BookMeeting.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialogmeetingdates);

                recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_exhibitor_az);
                Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                recyclerView.setHasFixedSize(true);
                manager = new LinearLayoutManager(BookMeeting.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);
                meetingsDateDialogAdapter = new MeetingsDateDialogAdapter(BookMeeting.this, meetingdateslist, dialog);
                recyclerView.setAdapter(meetingsDateDialogAdapter);
                meetingsDateDialogAdapter.notifyDataSetChanged();
                dialog.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataBaseHandler = new DataBaseHandler(this);
        List<PlastFocusModelClass> myarraydatesandtimelist = dataBaseHandler.getMyDatesANdTimeMeeting();
        if (myarraydatesandtimelist.size() > 0) {
            for (int i = 0; i < myarraydatesandtimelist.size(); i++) {

                String jsonObject = myarraydatesandtimelist.get(i).getMeetingdates();
                try {
                    JSONObject obj = new JSONObject(jsonObject);
                    Log.e("CheclMeetingDate22", obj.toString());
                    JSONArray dates = obj.getJSONArray("dates");
                    Log.e("CheclMeetingDate22", dates.toString());
                    JSONArray times = obj.getJSONArray("times");
                    Log.e("CheclMeetingDate22", times.toString());
                    meetingdateslist = new ArrayList<>();
                    mettingtimelist = new ArrayList<>();
                    for (int h = 0; h < dates.length(); h++) {
                        PlastFocusModelClass pojoHomePojo = new PlastFocusModelClass();
                        Log.e("CheclMeetingDate22", dates.get(h).toString());
                        pojoHomePojo.setMeetingdates(dates.get(h).toString());
                        meetingdateslist.add(pojoHomePojo);
                    }
                    for (int h = 0; h < times.length(); h++) {

                        PlastFocusModelClass pojoHomePojo = new PlastFocusModelClass();
                        Log.e("CheclMeetingDate22", times.get(h).toString());
                        pojoHomePojo.setMeetingtimes(times.get(h).toString());
                        mettingtimelist.add(pojoHomePojo);
                        Log.e("CheclMeetingDate22", times.get(i).toString());
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        tv_comapnyname = findViewById(R.id.tv_companyname);
        tv_name = findViewById(R.id.tv_name);
        tv_hall = findViewById(R.id.tv_hall);
        tv_email = findViewById(R.id.tv_email);

        tv_comapnyname.setText(ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getCompany_name());
        tv_name.setText(ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getAddress());
        if (ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getHall().equals("null") || ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getHall().equals("")) {
            tv_hall.setText("Hall : " + "-" + " " + "Stall : " + "-");

        } else {
            tv_hall.setText("Hall - " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getHall() + " " + "Stall - " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getStall_number());

        }

        tv_email.setText(ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getEmail());


    }

    public static void setDate() {
        tv_selectdate.setText(MeetingsDateDialogAdapter.sleecteddatemeeting);

    }

    public static void setTime() {
        tv_selecttimeformat.setText(MettingTimeDialogAdapter.sleectedtimemeeting);

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void getMeetingDateandTimeApi() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        // DataBaseHandler dataBaseHandler = new DataBaseHandler(VisitorList.this);
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.delete("MeetingShedule", null, null);
        ProgressDialog pd = new ProgressDialog(BookMeeting.this, R.style.StyledDialog);
        pd.setMessage("Update Meeting Data");
        pd.show();
        //String UserId = sh.getString("UserId", null);
        String url = Plastfocus_App_Api_List.Api_MeetingDatesAndTime;
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

                            //dataBaseHandler.AddMeetigScheduleInfo(pojoHomePojo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        List<PlastFocusModelClass> myarraydatesandtimelist = dataBaseHandler.getMyDatesANdTimeMeeting();
                        if (myarraydatesandtimelist.size() > 0) {
                            for (int i = 0; i < myarraydatesandtimelist.size(); i++) {

                                String jsonObject = myarraydatesandtimelist.get(i).getMeetingdates();
                                try {
                                    JSONObject obj = new JSONObject(jsonObject);
                                    // Log.e("CheclMeetingDate22", obj.toString());
                                    JSONArray dates = obj.getJSONArray("dates");
                                    //Log.e("CheclMeetingDate22", dates.toString());
                                    JSONArray times = obj.getJSONArray("times");
                                    // Log.e("CheclMeetingDate22", times.toString());
                                    meetingdateslist = new ArrayList<>();
                                    mettingtimelist = new ArrayList<>();
                                    for (int h = 0; h < dates.length(); h++) {
                                        PlastFocusModelClass pojoHomePojo = new PlastFocusModelClass();
                                        //  Log.e("CheclMeetingDate22", dates.get(h).toString());
                                        pojoHomePojo.setMeetingdates(dates.get(h).toString());
                                        meetingdateslist.add(pojoHomePojo);
                                    }
                                    for (int h = 0; h < times.length(); h++) {

                                        PlastFocusModelClass pojoHomePojo = new PlastFocusModelClass();
                                        Log.e("CheclMeetingDate22", times.get(h).toString());
                                        pojoHomePojo.setMeetingtimes(times.get(h).toString());
                                        mettingtimelist.add(pojoHomePojo);
                                        Log.e("CheclMeetingDate22", times.get(i).toString());
                                    }
                                    pd.dismiss();
                                    Toast.makeText(BookMeeting.this, "Meeting Dates and Time Updated Sucessfully.", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
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
                    VolleyErrorHelper.errorcoderesponce(message, BookMeeting.this);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.d("test", "onErrorResponse: Error " + error.getMessage());


            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void BookMeeting(String SelectedDate, String Selectedtime, String visitorid, String Exhibitorid) {
        ProgressDialog pd = new ProgressDialog(BookMeeting.this, R.style.StyledDialog);
        pd.setMessage("Meeting Schedule Wait ");
        pd.show();


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("meeting_date", SelectedDate);
            jsonObject.put("meeting_time", Selectedtime);
            jsonObject.put("visitor_id", visitorid);
            jsonObject.put("exhibitor_id", Exhibitorid);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Create a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Plastfocus_App_Api_List.Api_BookMeeting;
        System.out.println("BookMeeting:: " + url);
        System.out.println("BookMeeting:: " + jsonObject.toString());
        // Create a JsonObjectRequest with POST method, URL, headers, and parameters
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("BookMeeting: " + response.toString());

                        pd.dismiss();
                        Toast.makeText(BookMeeting.this, "Meeting Schedule Sucessfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), GetExhibitorDeatilsClass.class);
                        startActivity(i);
                        finish();

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
                            VolleyErrorHelper.errorcoderesponce(message, BookMeeting.this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), GetExhibitorDeatilsClass.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), GetExhibitorDeatilsClass.class);
        startActivity(i);
        finish();
    }
}
