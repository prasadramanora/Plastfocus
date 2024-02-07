package com.ramanora.plastfocus.PlastFocus_Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.Nullable;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.opencsv.CSVWriter;

import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Adapter.Adapter_Visitor_Scanned;

import com.ramanora.plastfocus.PlastFocus_ModelClasess.QrcodeModel;
import com.ramanora.plastfocus.PlastFocus_ApiList.Plastfocus_App_Api_List;
import com.ramanora.plastfocus.PlastFocus_Utils.AppStatus;
import com.ramanora.plastfocus.PlastFocus_Utils.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class VisitorList extends AppCompatActivity {
    public static String MyPREFERENCES = "myprefe";
    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;
    Button btn_scan;
    private final int PERMISSIONS_REQUEST_READ_LOCATION = 11;
    public static String[] permissions = new String[]{
            android.Manifest.permission.INTERNET,

            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE

    };

    public static String[] permissions_13 = new String[]{
            android.Manifest.permission.INTERNET,

            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_VIDEO,
            android.Manifest.permission.READ_MEDIA_AUDIO,

            Manifest.permission.POST_NOTIFICATIONS
    };
    ArrayList<QrcodeModel> mArrayListQrCode;
    RecyclerView recyclerView;
    ImageView iv_sync;
    LinearLayoutManager manager;
    Adapter_Visitor_Scanned adapter;
    DataBaseHandler dataBaseHandler;
    ImageView iv_downloadcsv;
    String resulT;
    String array[];
    String imageS = "";
    String UserId = "";
    String Registrationno = "", name = "", deignation = "", company = "", country = "", email = "", phone = "";
    ArrayList<QrcodeModel> mArrayListQrCodeofflinesync;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitorlist);
        iv_downloadcsv = findViewById(R.id.iv_downloadcsv);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sh = getSharedPreferences(MyPREFERENCES, 0);
        UserId = sh.getString("UserId", null);
        iv_downloadcsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Log.d("test", "onCreate: SDK_INT  if " + Build.VERSION.SDK_INT);
                    if (hasPermissions_13(VisitorList.this, permissions_13)) {

                        exportDB();

                    } else {
                        //  //Log.d("test", "has no Permissions: ");
                        ActivityCompat.requestPermissions((VisitorList.this), permissions_13, PERMISSIONS_REQUEST_READ_LOCATION);

                    }


                } else {
                    Log.d("test", "onCreate: else" + Build.VERSION.SDK_INT);
                    if (hasPermissions(VisitorList.this, permissions)) {


                        exportDB();
                    } else {
                        //  //Log.d("test", "has no Permissions: ");
                        ActivityCompat.requestPermissions((VisitorList.this), permissions, PERMISSIONS_REQUEST_READ_LOCATION);

                    }


                }
                // exportDB();
            }
        });
        btn_scan = findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(VisitorList.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(VisitorList.this, ScannerQrCode.class);
                        intent.putExtra("ScanQrcode", "Yes");
                        startActivityForResult(intent, 12);


                    } else {
                        ActivityCompat.requestPermissions(VisitorList.this, new
                                String[]{Manifest.permission.CAMERA}, 201);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //exhibitors/{{exhibitor-id}}/visitors?page=1&per_page=10
        iv_sync = findViewById(R.id.iv_sync);
        iv_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(VisitorList.this).isOnline()) {

                    VisitorList();
                } else {
                    Toast.makeText(VisitorList.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        dataBaseHandler = new DataBaseHandler(VisitorList.this);
        mArrayListQrCodeofflinesync = (ArrayList<QrcodeModel>) dataBaseHandler.getVisitorDataissync("NO");
        //checkEmailsyncArrylist = (ArrayList<QrcodeModel>) dataBaseHandler.getEmailSyncQrcodeTable();

        mArrayListQrCode = new ArrayList<>();

        if (AppStatus.getInstance(VisitorList.this).isOnline()) {

            VisitorList();


        } else {
            //  mArrayListQrCode = (ArrayList<QrcodeModel>) dataBaseHandler.getQrcodeDataOfflineSync();
            mArrayListQrCode = (ArrayList<QrcodeModel>) dataBaseHandler.getVisitorData();
            if (mArrayListQrCode.size() > 0) {
                adapter = new Adapter_Visitor_Scanned(getApplicationContext(), mArrayListQrCode);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            // Toast.makeText(VisitorList.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        // Log.e("MyQrCodeDataScan2leangth",scannedInfo+"");
        if (data == null) {
            // Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            startActivity(new Intent(VisitorList.this, ActivityMainHomePage.class));

        } else if (requestCode == 12) {
            if (data != null) {
                //  result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

                resulT = data.getStringExtra("intentData");
                Log.d("resulT", "resulT " + resulT);


                array = resulT.split("\t");
                // Qrcode qrcode = null;
                for (int i = 0; i < array.length; i++) {
                    Log.d("test", "onActivityResult: " + i + "  " + array[i]);
                    Log.d("test", "onActivityResultArray: " + array.length);
                    // qrcode = new Qrcode();
                    if (array.length == 1) {
                        if (i == 3)
                            company = array[i];
                        Log.d("test", "onActivityResultcompany: " + i + "  " + company);
                        //qrcode.setCompany(company);
                    } else if (i == 0) {
                        Registrationno = array[i];
                        Log.d("test", "onActivityResult: " + i + "  " + Registrationno);
                        // qrcode.setRegistrationno(Registrationno);
                    } else if (i == 1) {
                        name = array[i];
                        //  qrcode.setName(name);
                        Log.d("test", "onActivityResult: " + i + "  " + name);
                    } else if (i == 2) {
                        deignation = array[i];
                        Log.d("test", "onActivityResult: " + i + "  " + deignation);
                        //qrcode.setDesignation(deignation);
                    } else if (i == 3) {
                        company = array[i];
                        Log.d("test", "onActivityResult: " + i + "  " + company);
                        //qrcode.setCompany(company);

                    } else if (i == 4) {
                        country = array[i];
                        Log.d("test", "onActivityResultCountry: " + i + "  " + country);
                        // qrcode.setCountry(country);

                    } else if (i == 5) {
                        email = array[i];
                        Log.d("test", "onActivityResult: " + i + "  " + email);
                        // qrcode.setEmail(email);

                    } else if (i == 6) {
                        phone = array[i];
                        Log.d("test", "onActivityResult: " + i + "  " + phone);
                        // qrcode.setPhone(phone);
                    }




                }

            }

            if (AppStatus.getInstance(VisitorList.this).isOnline()) {
                if (resulT.contains("PlatFocus") && !resulT.contains("PlatFocus Visitor")) {
                    SendQrCodeScanData(company, deignation, country, phone, name, email);

                } else {
                    Toast.makeText(this, "Invalid QRCode", Toast.LENGTH_SHORT).show();
                }

            } else {
                String notfound = "no";
                ArrayList<QrcodeModel> mArrayListQrCodeofflinesync = (ArrayList<QrcodeModel>) dataBaseHandler.getVisitorData();

                for (int i = 0; i < mArrayListQrCodeofflinesync.size(); i++) {
                    if (mArrayListQrCodeofflinesync.get(i).getEmail().contains(email)) {
                        notfound = "Yes";
                    } else {
                        notfound = "No";
                    }
                }
                if (notfound.equals("Yes")) {
                    Toast.makeText(VisitorList.this, "Exhibitor already scanned", Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(VisitorList.this, "Your Record Save Offline ", Toast.LENGTH_SHORT).show();
                    if (resulT.contains("PlatFocus") && !resulT.contains("PlatFocus Visitor")) {
                        QrcodeModel qrcodeModel = new QrcodeModel();
                        qrcodeModel.setAddress(country);
                        qrcodeModel.setCoordinator_name(name);
                        qrcodeModel.setCoordinator_designation(deignation);
                        qrcodeModel.setCompany_name(company);
                        qrcodeModel.setMobile_number(phone);
                        qrcodeModel.setEmail(email);
                        qrcodeModel.setIssync("NO");

                        //dataBaseHandler.InserQECodeTableOfflineSync(qrcodeModel);
                        dataBaseHandler.InserVisitorList(qrcodeModel);
                        Toast.makeText(VisitorList.this, "Your Record Save Offline ", Toast.LENGTH_SHORT).show();
                        //dataBaseHandler.InserQECodeTable(qrcodeModel);
                        getvisitordata();
                    } else {
                        Toast.makeText(this, "Invalid QRCode", Toast.LENGTH_SHORT).show();
                    }

                }

                //exampleDBHelper.addScannedInfo(Registrationno, name, deignation, company, country, email, phone);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void SendQrCodeScanData(String company_name, String coordinator_designation, String address, String mobile_number, String coordinator_name, String email) {


        ProgressDialog pd = new ProgressDialog(VisitorList.this, R.style.StyledDialog);
        pd.setMessage("Wait..");
        pd.show();

        DataBaseHandler dataBaseHandler = new DataBaseHandler(VisitorList.this);
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
        String url = "https://ramanora.com/plastfocus/public/api/store-visited-exhibitor";
        System.out.println("QrcodeVisitorScreen: " + url);
        System.out.println("QrcodeVisitorScreen: " + jsonObject.toString());
        // Create a JsonObjectRequest with POST method, URL, headers, and parameters
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        System.out.println("QrcodeVisitorScreen: " + response.toString());
                        //  SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();

                        DataBaseHandler dataBaseHandler = new DataBaseHandler(VisitorList.this);

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

                            dataBaseHandler.InserQECodeTable(qrcodeModel);
                            VisitorList();

                            Toast.makeText(VisitorList.this, "Exhibitor Scanned Sucessfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                            VolleyErrorHelper.errorcoderesponce(message, VisitorList.this);
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

    private void exportDB() {
        ProgressDialog pd = new ProgressDialog(VisitorList.this, R.style.StyledDialog);
        pd.setMessage("Csv Genrated Wait");

        mArrayListQrCode = (ArrayList<QrcodeModel>) dataBaseHandler.getVisitorData();
        if (mArrayListQrCode.size() > 0) {
            pd.show();

            try {


                String fileName = "Exhibitorlistcsv.csv";
                File directorynew = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(directorynew, fileName);


                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                SQLiteDatabase db = dataBaseHandler.getReadableDatabase();
                Cursor curCSV = db.rawQuery("SELECT * FROM VisitorList", null);
                csvWrite.writeNext(curCSV.getColumnNames());
                while (curCSV.moveToNext()) {
                    //Which column you want to exprort
                    String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2), curCSV.getString(3), curCSV.getString(4), curCSV.getString(5), curCSV.getString(6), curCSV.getString(7), curCSV.getString(8), curCSV.getString(9), curCSV.getString(10), curCSV.getString(11), curCSV.getString(12)};
                    csvWrite.writeNext(arrStr);
                }
                csvWrite.close();
                curCSV.close();
                Toast.makeText(this, "Exhibitor Csv Genrated Sucessfully", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                Uri pdfUri;
                //  Uri uri = FileProvider.getUriForFile(VisitorList.this, BuildConfig.APPLICATION_ID + ".provider", root);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    pdfUri = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", file);
                } else {
                    pdfUri = Uri.fromFile(file);
                }
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/pdf");
                share.putExtra(Intent.EXTRA_STREAM, pdfUri);
                startActivity(Intent.createChooser(share, "Share"));

            } catch (Exception sqlEx) {
                pd.dismiss();
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
            }
        } else {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            return directory.delete();
        }
        return false;
    }

    private void VisitorList() {
        ProgressDialog pd = new ProgressDialog(VisitorList.this, R.style.StyledDialog);
        pd.setMessage("Wait..");
        pd.show();
        sh = getSharedPreferences(MyPREFERENCES, 0);
        String UserId = sh.getString("UserId", null);
        DataBaseHandler dataBaseHandler = new DataBaseHandler(VisitorList.this);
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.delete("VisitorList", null, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("visitor_id", UserId);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Create a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Plastfocus_App_Api_List.Api_VisitorList;
        System.out.println("Visitorlist: " + url);
        System.out.println("Visitorlist: " + jsonObject.toString());
        // Create a JsonObjectRequest with POST method, URL, headers, and parameters
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        System.out.println("Visitorlist: " + response.toString());
                        SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();
                        try {
                            JSONObject jsonObject2 = new JSONObject(response.toString());

                            JSONArray jsonArray = jsonObject2.getJSONArray("data");
                            System.out.println("Visitorlist: " + jsonArray.length());
                            if (jsonArray.length() > 0) {

                                JSONArray jsonArray1;
                                try {
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    jsonArray1 = jsonObject.getJSONArray("data");
                                    //  System.out.println("Qrcode: " + js.toString());

                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
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

                                        ContentValues values = new ContentValues();
                                        values.put("company_name", company_name);
                                        values.put("coordinator_name", coordinator_name); // CameraPojo Phone
                                        values.put("coordinator_designation", coordinator_designation);
                                        values.put("address", address);

                                        values.put("city", city);
                                        values.put("state", state); // CameraPojo Phone
                                        values.put("pincode", pincode);
                                        values.put("mobile_number", mobile_number);

                                        values.put("email", email);
                                        //values.put("website", web); // CameraPojo Phone
                                        values.put("exhibitor_ref_id", exhibitor_ref_id);
                                        values.put("visitor_ref_id", visitor_ref_id);

                                        values.put("country", country);
                                        values.put("updated_at", updated_at); // CameraPojo Phone
                                        values.put("created_at", created_at);
                                        values.put("qrid", id);
                                        dataBaseHandler.InserVisitorList(qrcodeModel);
                                        dataBaseHandler.updateQrcodeData(values, email);

                                        //mArrayListQrCode.add(qrcodeModel);
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else {
                                Toast.makeText(VisitorList.this,   "  Data Not Found", Toast.LENGTH_SHORT).show();
                            }
                           // Toast.makeText(VisitorList.this, jsonArray.length() + " Visitor Found", Toast.LENGTH_SHORT).show();
                            getvisitordata();

                            // String Userid = jsonObject.getString("id");
                            // SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();

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
                            VolleyErrorHelper.errorcoderesponce(message, VisitorList.this);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

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
                Intent i = new Intent(getApplicationContext(), ActivityMainHomePage.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    private void SendQrCodeScanDataOfflineSync(String company_name, String coordinator_designation, String address, String mobile_number, String coordinator_name, String email) {


        DataBaseHandler dataBaseHandler = new DataBaseHandler(VisitorList.this);
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

        System.out.println("OfflineQrcode: " + jsonObject.toString());
        // Create a JsonObjectRequest with POST method, URL, headers, and parameters
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Plastfocus_App_Api_List.Api_QrcodeDatUploadServer, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("OfflineQrcode: " + response.toString());
                        //  SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();

                        DataBaseHandler dataBaseHandler = new DataBaseHandler(VisitorList.this);

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
                            VolleyErrorHelper.errorcoderesponce(message, VisitorList.this);
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

    @Override
    protected void onStart() {
        super.onStart();

        checkofflinesync();
    }

    private void checkofflinesync() {
        if (AppStatus.getInstance(VisitorList.this).isOnline()) {
            if (mArrayListQrCodeofflinesync.size() > 0) {
                SendQrCodeScanDataOfflineSync(mArrayListQrCodeofflinesync.get(0).getCompany_name(), mArrayListQrCodeofflinesync.get(0).getCoordinator_designation(), mArrayListQrCodeofflinesync.get(0).getAddress(), mArrayListQrCodeofflinesync.get(0).getMobile_number(), mArrayListQrCodeofflinesync.get(0).getCoordinator_name(), mArrayListQrCodeofflinesync.get(0).getEmail());
            }
        } else {
            Log.e("OfflineMode", "Yes");
        }
    }

    private void getvisitordata() {
        mArrayListQrCode = (ArrayList<QrcodeModel>) dataBaseHandler.getVisitorData();
        if (mArrayListQrCode.size() > 0) {
            adapter = new Adapter_Visitor_Scanned(getApplicationContext(), mArrayListQrCode);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No Record Found", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean hasPermissions_13(Context context, String... permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }

        }
        return true;
    }

    public boolean hasPermissions(Context context, String... permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
