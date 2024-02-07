package com.ramanora.plastfocus.PlastFocus_Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.Nullable;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Adapter.BannerPagerAdapter;
import com.ramanora.plastfocus.PlastFocus_Adapter.HomePageAdapter;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.BannerModelClass;

import com.ramanora.plastfocus.PlastFocus_ModelClasess.QrcodeModel;
import com.ramanora.plastfocus.PlastFocus_FirebaseServices.FirebaseBroadcastReceiver;

import com.ramanora.plastfocus.PlastFocus_ApiList.Plastfocus_App_Api_List;
import com.ramanora.plastfocus.PlastFocus_Utils.AppStatus;
import com.ramanora.plastfocus.PlastFocus_Utils.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 9/12/2017.
 */

public class ActivityMainHomePage extends Activity {
    private final int PERMISSION_REQUEST_CODE = 1;
    ProgressDialog pd;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    RequestQueue rq;
    List<BannerModelClass> sliderImg;
    BannerPagerAdapter viewPagerAdapter;

    View deleteDialogView;

    static int mNotifCount = 0;
    AlertDialog deleteDialog;

    public static String str_login_test, UserId = "", CheckLogin = "";
    String firebasetoken, isUploaded;
    public static String MyPREFERENCES = "myprefe";
    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;


    private BroadcastReceiver mRegistrationBroadcastReceiver;
    DataBaseHandler dataBaseHandler;
    Button btn_Logistics, btn_Complaint;
    String android_id, fcmtoken;
    private RequestQueue mRequestQueue;
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

    ArrayList<String> productnamelist = new ArrayList<>();

    private final int[] imageId = {
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu4,

            R.drawable.menu6,
            // R.drawable.b2b, nnhhhhhhhhhhhhhhhhhhhhhhhhhhhh   hhhhh  hh  hhhhhhh h j
            R.drawable.menu7,
            R.drawable.menu8,
            R.drawable.menu9,
            R.drawable.menu10,


    };
    ArrayList<QrcodeModel> mArrayListQrCodeofflinesync;
    private final int[] imageIdExhibitors = {
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu4,

            R.drawable.menu6,
            // R.drawable.b2b, nnhhhhhhhhhhhhhhhhhhhhhhhhhhhh   hhhhh  hh  hhhhhhh h j
            R.drawable.menu7,
            R.drawable.exivisited,
            R.drawable.menu9,
            R.drawable.menu10,


    };
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    private final String[] menunamelistVisitor = {


            "About PlastFocus",
            "Exhibitor List",

            "News & Update",
            "Map",
            "My Meetings",
            "iVisited",
            "My Profile",
            "Notifications"
    };
    private final String[] menunamelistExhibitor = {


            "About Us",
            "Exhibitor List",

            "News & Update",
            "Map",
            "My Meetings",
            "myVisited",
            "My Profile",
            "Notifications"
    };
    RecyclerView recyclerView;

    String message = "";
    public static String emailid;
    String MessageBody = "";
    ImageView iv_loading;
    RelativeLayout rl_mainhomepage;
    LinearLayout ll_menubuttun;
    public static String webviewlink = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepagemenuitems);
        // this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        rl_mainhomepage = findViewById(R.id.rl_mainhomepage);

        sliderImg = new ArrayList<>();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ll_menubuttun = (LinearLayout) findViewById(R.id.ll_menubuttun);

        Intent intent = getIntent();
        try {

            // Log.e("Mychecknotification",FirebaseBroadcastReceiver.checknotification);
            if (FirebaseBroadcastReceiver.checknotification.equals("Meeting Request")) {
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.notificationdialog);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
                TextView tv_message = dialog.findViewById(R.id.tv_message);
                tv_message.setText(FirebaseBroadcastReceiver.NotificationMessage);

                Button btn_view = dialog.findViewById(R.id.btn_view);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (FirebaseBroadcastReceiver.checknotification.equals("Meeting Request")) {
                            dialog.dismiss();
                            FirebaseBroadcastReceiver.checknotification = "";
                            Intent i = new Intent(getApplicationContext(), ExhibitorListMeeting.class);
                            i.putExtra("Checkmeeting", "Yes");
                            startActivity(i);

                            // finish();
                        }


                    }
                });

            } else {
                if (FirebaseBroadcastReceiver.checknotification.equals("New Visitor")) {
                    final Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.notificationdialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCancelable(false);
                    dialog.show();
                    TextView tv_message = dialog.findViewById(R.id.tv_message);
                    TextView tv_messagetitle = dialog.findViewById(R.id.tv_messagetitle);
                    tv_message.setText(FirebaseBroadcastReceiver.NotificationMessage);
                    tv_messagetitle.setText("New Visitor");
                    Button btn_view = dialog.findViewById(R.id.btn_view);
                    Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btn_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            if (FirebaseBroadcastReceiver.checknotification.equals("New Visitor")) {
                                dialog.dismiss();
                                FirebaseBroadcastReceiver.checknotification = "";
                                Intent i = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                                    i = new Intent(getApplicationContext(), ExhibitorList.class);
                                }
                                i.putExtra("checkvisitorfound", "Yes");
                                startActivity(i);

                                // finish();
                            }


                        }
                    });

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        if (AppStatus.getInstance(ActivityMainHomePage.this).isOnline()) {
            LoadbannerApi();
        }

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == sliderImg.size() - 1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
        dataBaseHandler = new DataBaseHandler(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);

        mRequestQueue = Volley.newRequestQueue(getBaseContext());

        sh = getSharedPreferences(MyPREFERENCES, 0);
        editor = sh.edit();
        str_login_test = sh.getString("loginTest1", null);
        UserId = sh.getString("UserId", null);
        CheckLogin = sh.getString("CheckLogin", null);
        isUploaded = sh.getString("isUploaded", null);
        emailid = sh.getString("email", null);
        Log.d("test", "Register isUploaded : " + str_login_test);

        firebasetoken = sh.getString("fcmtoken", null);
        if (CheckLogin.equals("Visitor")) {
            ll_menubuttun.setVisibility(View.GONE);
        } else {
            ll_menubuttun.setVisibility(View.VISIBLE);
        }
        btn_Logistics = findViewById(R.id.btn_Logistics);
        btn_Complaint = findViewById(R.id.btn_Complaint);
        btn_Complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webviewlink = "https://complaints.plastfocus.org/";
                Intent i = new Intent(getApplicationContext(), ExhibitoRlOginwebView.class);
                startActivity(i);
            }
        });
        btn_Logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webviewlink = "https://logistics.plastfocus.org/login/Login.aspx";
                Intent i = new Intent(getApplicationContext(), ExhibitoRlOginwebView.class);
                startActivity(i);
            }
        });
        Log.d("Token", "onCreate: " + firebasetoken);

        android_id = Settings.Secure.getString(ActivityMainHomePage.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);


        SharedPreferences sharedpreferences = getSharedPreferences(ActivityMainHomePage.MyPREFERENCES,
                Context.MODE_PRIVATE);

        String isUploadedToken = sh.getString("isUploadedToken", "");
        Log.d("Token", "onCreate: isUploadedToken " + isUploadedToken);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }

        LayoutInflater factory = LayoutInflater.from(this);
        deleteDialogView = factory.inflate(R.layout.permisson, null);
        deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
        checkPermission();

        dataBaseHandler = new DataBaseHandler(ActivityMainHomePage.this);
        mArrayListQrCodeofflinesync = (ArrayList<QrcodeModel>) dataBaseHandler.getVisitorDataissync("NO");
        // mNotifCount = databaseHelper.getNotificationCount();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager
                = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<PlastFocusModelClass> mArrayList = prepareData();
        HomePageAdapter adapter = new HomePageAdapter(mArrayList, getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //postDataToken();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            changeTextStatus(true);
        } else {
            changeTextStatus(false);
        }

        mNotifCount = dataBaseHandler.getNotificationCount();

        recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
        recyclerView.setHasFixedSize(true);
        layoutManager
                = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        mArrayList = prepareData();
        adapter = new HomePageAdapter(mArrayList, getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Plastfocus_App_Api_List.PUSH_NOTIFICATION));
    }

    private void SendQrCodeScanDataOfflineSync(String company_name, String coordinator_designation, String address, String mobile_number, String coordinator_name, String email) {


        DataBaseHandler dataBaseHandler = new DataBaseHandler(ActivityMainHomePage.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("company_name", company_name);
            jsonObject.put("coordinator_name", coordinator_name);
            jsonObject.put("coordinator_designation", coordinator_designation);
            jsonObject.put("coordinator_name", coordinator_name);
            jsonObject.put("address", address);
            jsonObject.put("city", "-");
            jsonObject.put("state", "-");
            jsonObject.put("pincode", "-");
            jsonObject.put("mobile_number", mobile_number);
            jsonObject.put("email", email);
            jsonObject.put("website", "-");
            jsonObject.put("exhibitor_ref_id", "0");
            jsonObject.put("visitor_ref_id", UserId);
            jsonObject.put("country", "-");

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Create a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Plastfocus_App_Api_List.Api_QrcodeDatUploadServer;
        System.out.println("OfflineQrcode: " + url);
        System.out.println("OfflineQrcode: " + jsonObject.toString());
        // Create a JsonObjectRequest with POST method, URL, headers, and parameters
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("OfflineQrcode: " + response.toString());
                        //  SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();

                        DataBaseHandler dataBaseHandler = new DataBaseHandler(ActivityMainHomePage.this);

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            //  System.out.println("Qrcode: " + js.toString());


                            String company_name = jsonObject1.getString("company_name");
                            QrcodeModel qrcodeModel = new QrcodeModel();
                            qrcodeModel.setCompany_name(company_name);
                            String coordinator_name = jsonObject1.getString("coordinator_name");
                            qrcodeModel.setCoordinator_name(coordinator_name);
                            String coordinator_designation = jsonObject1.getString("coordinator_designation");
                            qrcodeModel.setCoordinator_designation(coordinator_designation);
                            String address = jsonObject1.getString("address");
                            qrcodeModel.setAddress(address);
                            String city = jsonObject1.getString("city");
                            qrcodeModel.setCity(city);
                            String state = jsonObject1.getString("state");
                            qrcodeModel.setState(state);
                            String pincode = jsonObject1.getString("pincode");
                            qrcodeModel.setPincode(pincode);
                            String mobile_number = jsonObject1.getString("mobile_number");
                            qrcodeModel.setMobile_number(mobile_number);
                            String email = jsonObject1.getString("email");
                            qrcodeModel.setEmail(email);
                            String website = jsonObject1.getString("website");
                            qrcodeModel.setWebsite(website);
                            String exhibitor_ref_id = jsonObject1.getString("exhibitor_ref_id");
                            qrcodeModel.setExhibitor_ref_id(exhibitor_ref_id);
                            String visitor_ref_id = jsonObject1.getString("visitor_ref_id");
                            qrcodeModel.setVisitor_ref_id(visitor_ref_id);

                            String country = jsonObject1.getString("country");
                            qrcodeModel.setCountry(country);
                            String updated_at = jsonObject1.getString("updated_at");
                            qrcodeModel.setUpdated_at(updated_at);
                            String created_at = jsonObject1.getString("created_at");
                            qrcodeModel.setCreated_at(created_at);
                            String id = jsonObject1.getString("id");
                            qrcodeModel.setQrid(id);

                            qrcodeModel.setIssync("YES");
                            dataBaseHandler.InserVisitorList(qrcodeModel);
                            // dataBaseHandler.InserQECodeTable(qrcodeModel);
                            ContentValues values = new ContentValues();

                            values.put("email", email);
                            values.put("issync", "YES");
                            //values.put("website", web); // CameraPojo Phone

                            dataBaseHandler.updateQrcodeData(values, email);
                            mArrayListQrCodeofflinesync.remove(0);
                            if (mArrayListQrCodeofflinesync.size() > 0) {
                                checkofflinesync();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(RegistrationScreen.this, "Network error", Toast.LENGTH_SHORT).show();
                        // pd.dismiss();
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
                            VolleyErrorHelper.errorcoderesponce(message, ActivityMainHomePage.this);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        Log.e("errormessge", body.toString());
                    }
                }) {

        };

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);

    }

    private void checkofflinesync() {
        if (AppStatus.getInstance(ActivityMainHomePage.this).isOnline()) {

            if (mArrayListQrCodeofflinesync.size() > 0) {
                SendQrCodeScanDataOfflineSync(mArrayListQrCodeofflinesync.get(0).getCompany_name(), mArrayListQrCodeofflinesync.get(0).getCoordinator_designation(), mArrayListQrCodeofflinesync.get(0).getAddress(), mArrayListQrCodeofflinesync.get(0).getMobile_number(), mArrayListQrCodeofflinesync.get(0).getCoordinator_name(), mArrayListQrCodeofflinesync.get(0).getEmail());
            }
            //

        } else {
            //Toast.makeText(this, "QrCode Format Not Match", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeTextStatus(boolean isConnected) {

        // Change status according to boolean value
        if (isConnected) {
            checkofflinesync();
            //Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }
    }

    private void showpermissiondilaog() {

        Button btn_permisson = deleteDialogView.findViewById(R.id.btn_permisson);
        btn_permisson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {

                    int cameraPermissionfine = checkSelfPermission(Manifest.permission.CAMERA);
                    int callPermissionfine = checkSelfPermission(Manifest.permission.CALL_PHONE);
                    int locationPermissionfine = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
                    int storagePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int readPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    int locationPermissioncoarse = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);


                    List<String> listPermissionsNeeded = new ArrayList<>();

                    if (locationPermissionfine != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                    if (locationPermissioncoarse != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                    }
                    if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                    if (readPermission != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }

                    if (cameraPermissionfine != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(Manifest.permission.CAMERA);
                    }

                    if (callPermissionfine != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
                    }


                    if (!listPermissionsNeeded.isEmpty()) {
                        // showpermissiondilaog();
                        ActivityCompat.requestPermissions(ActivityMainHomePage.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_REQUEST_CODE);
                        // return false;
                    } else {
                        deleteDialog.dismiss();
                    }
                }
            }
        });

        deleteDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private ArrayList<PlastFocusModelClass> prepareData() {

        ArrayList<PlastFocusModelClass> mArrayList = new ArrayList<>();
        for (int i = 0; i < imageId.length; i++) {
            PlastFocusModelClass pojo = new PlastFocusModelClass();
            if (CheckLogin.equals("Visitor")) {
                pojo.setmImg(imageId[i]);
            } else {
                pojo.setmImg(imageIdExhibitors[i]);
            }


            if (CheckLogin.equals("Visitor")) {
                pojo.setTv_itemname(menunamelistVisitor[i]);
            } else {
                pojo.setTv_itemname(menunamelistExhibitor[i]);
            }


            mArrayList.add(pojo);
        }
        return mArrayList;

    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(R.drawable.plast).setTitle("Exit")
                .setMessage("Do you want to close this Plastfocus ?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                    }
                }).setNegativeButton("No", null).show();
    }


    @Override
    public void onStart() {
        super.onStart();

        List<PlastFocusModelClass> mArrayCallRList = dataBaseHandler.getExhibitorData();
        if (mArrayCallRList.size() > 0) {

        } else {

            getExhibitorData();
        }
    }


    public boolean checkPermission() {

        if (Build.VERSION.SDK_INT >= 23) {

            int cameraPermissionfine = checkSelfPermission(Manifest.permission.CAMERA);
            int callPermissionfine = checkSelfPermission(Manifest.permission.CALL_PHONE);
            int locationPermissionfine = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int storagePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int locationPermissioncoarse = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);


            List<String> listPermissionsNeeded = new ArrayList<>();

            if (locationPermissionfine != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (locationPermissioncoarse != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (readPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (cameraPermissionfine != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
                deleteDialog.dismiss();
            } else {

            }

            if (callPermissionfine != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
                deleteDialog.dismiss();
            } else {

            }


            if (!listPermissionsNeeded.isEmpty()) {
                deleteDialog.dismiss();
                // ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_REQUEST_CODE);
                return false;
            } else {
                showpermissiondilaog();

            }
            return true;
        }
        return true;

    }


    private void LoadbannerApi() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler(ActivityMainHomePage.this);


        String url = Plastfocus_App_Api_List.Api_BannersImageApi;
        RequestQueue requestQueue = Volley.newRequestQueue(ActivityMainHomePage.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d("UI thread", "I am the UI thread");

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());

                            JSONArray js = jsonObject.getJSONArray("data");
                            for (int i = 0; i < js.length(); i++) {
                                BannerModelClass sliderUtils = new BannerModelClass();
                                String bannerlogos = Plastfocus_App_Api_List.BAseURlBAnnerLOgo + js.get(i);
                                sliderUtils.setSliderImageUrl(bannerlogos);
                                sliderImg.add(sliderUtils);
                                Log.e("CheckBannerLogos", bannerlogos);
                            }

                            viewPagerAdapter = new BannerPagerAdapter(sliderImg, ActivityMainHomePage.this);

                            viewPager.setAdapter(viewPagerAdapter);

                            dotscount = viewPagerAdapter.getCount();
                            dots = new ImageView[dotscount];

                            for (int i = 0; i < dotscount; i++) {

                                dots[i] = new ImageView(ActivityMainHomePage.this);
                                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.activedot));

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                params.setMargins(8, 0, 8, 0);

                                sliderDotspanel.addView(dots[i], params);

                            }

                            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactivedot));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pd.dismiss();
                //  Toast.makeText(Activity_Login.this, Utils.VOLLEY_ERROR_MSG, Toast.LENGTH_SHORT).show();
                error.getMessage();
                try {
                    String responseBody = null;
                    try {
                        responseBody = new String(error.networkResponse.data, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    JSONObject responseJson = new JSONObject(responseBody);
                    String errorMessage = responseJson.getString("message");
                    //  Log.d("test", "TestApiErrorMeesage" + errorMessage);
                    if (errorMessage != null) {
                        //    VolleyErrorHelper.errorcoderesponce(errorMessage, getApplicationContext());
                    }
                    // Handle custom error message from the server
                    // errorMessage contains the custom error message
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("test", "onErrorResponse: Error " + error.getMessage());


            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    public void getExhibitorData() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.delete("ExhibitorList", null, null);
        db.close();

        // rl_mainhomepage.setVisibility(View.GONE);

        pd = new ProgressDialog(ActivityMainHomePage.this, R.style.StyledDialog);
        pd.setMessage("Exhibitor Data Loaded wait ..");

        pd.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        pd.setCanceledOnTouchOutside(false);
        pd.show();

        String url = Plastfocus_App_Api_List.Api_ExhibitorList;
        Log.d("ExhibitorData", url);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d("UI thread", "I am the UI thread");
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    Log.d("ExhibitorData", response.toString());
                                    JSONArray js = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < js.length(); i++) {
                                        JSONObject jsonObject1 = js.getJSONObject(i);
                                        PlastFocusModelClass pojoHomePojo = new PlastFocusModelClass();
                                        id = jsonObject1.getString("id");
                                        pojoHomePojo.setId(id);
                                        company_name = jsonObject1.getString("company_name");
                                        pojoHomePojo.setCompany_name(company_name);
                                        salutation = jsonObject1.getString("salutation");
                                        pojoHomePojo.setSalutation(salutation);
                                        coordinator_name = jsonObject1.getString("coordinator_first_name");
                                        pojoHomePojo.setCoordinator_name(coordinator_name);
                                        coordinator_last_name = jsonObject1.getString("coordinator_last_name");
                                        pojoHomePojo.setCoordinator_last_name(coordinator_last_name);
                                        coordinator_designation = jsonObject1.getString("coordinator_designation");
                                        pojoHomePojo.setCoordinator_designation(coordinator_designation);
                                        address = jsonObject1.getString("address");
                                        address = address.replace("null", "-");
                                        pojoHomePojo.setAddress(address);

                                        country = jsonObject1.getString("country");
                                        country = country.replace("null", "-");
                                        pojoHomePojo.setCountry(country);
                                        state = jsonObject1.getString("state");
                                        state = state.replace("null", "-");
                                        pojoHomePojo.setState(state);

                                        city = jsonObject1.getString("city");
                                        city = city.replace("null", "-");
                                        pojoHomePojo.setCity(city);
                                        pincode = jsonObject1.getString("pincode");
                                        pincode = pincode.replace("null", "-");
                                        pojoHomePojo.setPincode(pincode);
                                        mobile_number = jsonObject1.getString("mobile_number");
                                        pojoHomePojo.setMobile_number(mobile_number);
                                        alternate_number = jsonObject1.getString("alternate_number");
                                        alternate_number = alternate_number.replace("null", "-");
                                        pojoHomePojo.setAlternate_number(alternate_number);
                                        fax = jsonObject1.getString("fax");
                                        fax = fax.replace("null", "-");
                                        pojoHomePojo.setFax(fax);
                                        email = jsonObject1.getString("email");
                                        email = email.replace("null", "-");

                                        pojoHomePojo.setEmail(email);
                                        alternate_email = jsonObject1.getString("alternate_email");
                                        alternate_email = alternate_email.replace("null", "-");
                                        pojoHomePojo.setAlternate_email(alternate_email);
                                        website = jsonObject1.getString("website");
                                        website = website.replace("null", "-");
                                        pojoHomePojo.setWebsite(website);
                                        JSONArray roduct_group = jsonObject1.getJSONArray("product_group");

                                        for (int t = 0; t < roduct_group.length(); t++) {
                                            PlastFocusModelClass pojo = new PlastFocusModelClass();

                                            if (!productnamelist.contains(roduct_group.getString(t))) {
                                                productnamelist.add(roduct_group.getString(t));
                                                pojo.setProductnames(roduct_group.getString(t));
                                                dataBaseHandler.AddProductNames(pojo);
                                            }

                                        }

                                        pojoHomePojo.setProduct_group(roduct_group.toString());
                                        company_profile = jsonObject1.getString("company_profile");
                                        company_profile.replace("null", "-");
                                        pojoHomePojo.setCompany_profile(company_profile);
                                        hall = jsonObject1.getString("hall");
                                        hall = hall.replace("null", "-");
                                        pojoHomePojo.setHall(hall);
                                        stall_number = jsonObject1.getString("stall_number");
                                        stall_number = stall_number.replace("null", "-");
                                        pojoHomePojo.setStall_number(stall_number);

                                        PlastFocusModelClass productdata = new PlastFocusModelClass();

                                        productdata.setCompany_name(company_name);
                                        productdata.setCity(city);
                                        productdata.setProduct_group(roduct_group.toString());
                                        productdata.setState(state);
                                        productdata.setCountry(country);
                                        productdata.setHall(hall);
                                        productdata.setStall_number(stall_number);
                                        dataBaseHandler.AddProductData(productdata);
                                        cord_x = jsonObject1.getString("cord_x");
                                        pojoHomePojo.setCord_x(cord_x);
                                        cord_y = jsonObject1.getString("cord_y");
                                        pojoHomePojo.setCord_y(cord_y);
                                        Log.e("company_name", company_name);
                                        Log.e("mobile_number", mobile_number);
                                        dataBaseHandler.InserExhibitorData(pojoHomePojo);
                                    }
                                    pd.dismiss();
                                    Log.d("ExhibitorData", "Done");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        t.start();

                    }
                });
                // rl_mainhomepage.setVisibility(View.VISIBLE);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();

                String body = null;

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
                    VolleyErrorHelper.errorcoderesponce(message, ActivityMainHomePage.this);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.d("test", "onErrorResponse: Error " + error.getMessage());


            }
        });

        requestQueue.add(jsonObjectRequest);

    }


}
