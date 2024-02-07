package com.ramanora.plastfocus.PlastFocus_Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.WriterException;


import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;

import com.ramanora.plastfocus.PlastFocus_ModelClasess.QrcodeModel;
import com.ramanora.plastfocus.PlastFocus_ApiList.Plastfocus_App_Api_List;
import com.ramanora.plastfocus.PlastFocus_Utils.AppStatus;
import com.ramanora.plastfocus.PlastFocus_Utils.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


/**
 * Created by admin on 9/13/2017.
 */

public class MyProfile extends AppCompatActivity {
    String array[];
    private String checklogoutotordeleteaccount = "No";
    String imageS = "";

    String Registrationno = "", name = "", deignation = "", company = "", country = "", email = "", phone = "",
            city = "", typeofvisitor = "", other = "";

    /*implements View.OnClickListener*/
    private QRGEncoder qrgEncoder;
    ImageView imageView;

    Thread thread;
    public final static int QRcodeWidth = 500;
    Bitmap bitmap;
    String str_qrCode;

    private Button scanBtn, BtnSave;

    Intent intent;
    // IntentResult result;
    String resulT;
    TextView TxtName, tv_name, tv_designation, tv_company, tv_address, tv_email;
    //  private ShowcaseView showcaseView;
    private int counter = 0;
    SharedPreferences sharedpreferences;
    String isFirst = "", CheckLogin = "";
    String Name = "";
    String Designation = "";
    String Company = "";
    String Address = "";
    String Emailid = "";
    String UserId = "";
    String txt1 = "<br/>Print your entry badges using QR code ";
    String txt2 = "Exchange your information with exhibitiors";
    String txt3 = "<br/>Scan QR code on machines, catalogue etc and get information";
    String txt = "<br/><br/><br/>&#8226;" + txt1 + "<br/>&#8226;" + txt2 + "<br/>&#8226;" + txt3;
    String txt4 = "<br/>View information of the scanned QR codes";
    Button btn_logout;

    TextView tv_delete;
    DataBaseHandler dataBaseHandler;
    ArrayList<QrcodeModel> mArrayListQrCodeofflinesync;

    //  private ZXingScannerView scannerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile);
        dataBaseHandler = new DataBaseHandler(MyProfile.this);
        mArrayListQrCodeofflinesync = (ArrayList<QrcodeModel>) dataBaseHandler.getVisitorDataissync("NO");
        // Log.e("OfflineQrcode:", mArrayListQrCodeofflinesync.size() + "");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = (ImageView) findViewById(R.id.imageView);
        scanBtn = (Button) findViewById(R.id.scan_button);
        BtnSave = (Button) findViewById(R.id.save1);
        TxtName = (TextView) findViewById(R.id.qrname);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        tv_delete = (TextView) findViewById(R.id.tv_delete);


        Intent intentt = getIntent();
        String ScanQrcode = intentt.getStringExtra("ScanQrcode");

        if (intentt.hasExtra("ScanQrcode")) {
            ScanQrcode = intentt.getStringExtra("ScanQrcode");
            if (ScanQrcode.equals("Yes")) {
                Intent ie = new Intent(MyProfile.this, ScannerQrCode.class);
                startActivityForResult(ie, 12);
            } else {

            }
        }

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyProfile.this);
                dialog.setIcon(R.drawable.applogo);
                dialog.setTitle("PlastFocus App");
                dialog.setMessage("Are you sure want to Delete Account Permanantly?");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (AppStatus.getInstance(MyProfile.this).isOnline()) {
                            mArrayListQrCodeofflinesync = (ArrayList<QrcodeModel>) dataBaseHandler.getVisitorDataissync("NO");

                            if (mArrayListQrCodeofflinesync.size() > 0) {

                                new AlertDialog.Builder(MyProfile.this)
                                        .setTitle("PlastFocus")
                                        .setMessage("Sync Offline Scan QrCode.")

                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                checklogoutotordeleteaccount = "DeleteYES";
                                                SendQrCodeScanDataOfflineSync(mArrayListQrCodeofflinesync.get(0).getCompany_name(), mArrayListQrCodeofflinesync.get(0).getCoordinator_designation(), mArrayListQrCodeofflinesync.get(0).getAddress(), mArrayListQrCodeofflinesync.get(0).getMobile_number(), mArrayListQrCodeofflinesync.get(0).getCoordinator_name(), mArrayListQrCodeofflinesync.get(0).getEmail());

                                                // Continue with delete operation
                                            }
                                        })

                                        // A null listener allows the button to dismiss the dialog and take no further action.
                                        .setNegativeButton(android.R.string.no, null)
                                        .setIcon(R.drawable.applogo)
                                        .show();
                                // SendQrCodeScanDataOfflineSync(mArrayListQrCodeofflinesync.get(0).getCompany_name(), mArrayListQrCodeofflinesync.get(0).getCoordinator_designation(), mArrayListQrCodeofflinesync.get(0).getAddress(), mArrayListQrCodeofflinesync.get(0).getMobile_number(), mArrayListQrCodeofflinesync.get(0).getCoordinator_name(), mArrayListQrCodeofflinesync.get(0).getEmail());
                            } else {
                                SQLiteDatabase db = dataBaseHandler.getWritableDatabase();


                                db.delete("ExhibitorList", null, null);
                                db.delete("ExhibitorListMeeting", null, null);
                                db.delete("VisitorListMeeting", null, null);
                                db.delete("QrcodeTable", null, null);
                               // db.delete("QrcodeTableofflinesync", null, null);
                                db.delete("VisitorList", null, null);
                                db.delete("Exhibitordata", null, null);
                                db.delete("ProductDataTable", null, null);
                                db.delete("MeetingShedule", null, null);
                              //  db.delete("CheckEMailSyncQrcode", null, null);

                                db.close();
                                RemoveAccount(UserId);
                            }

                        } else {
                            Toast.makeText(MyProfile.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                        //Toast.makeText(context,"Adapter",Toast.LENGTH_LONG).show();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        //Toast.makeText(context,"Adapter",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();

            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyProfile.this);
                dialog.setIcon(R.drawable.applogo);
                dialog.setTitle("PlastFocus App");
                dialog.setMessage("Are you sure want to exit App?");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (AppStatus.getInstance(MyProfile.this).isOnline()) {
                            if (mArrayListQrCodeofflinesync.size() > 0) {
                                new AlertDialog.Builder(MyProfile.this)
                                        .setTitle("PlastFocus")
                                        .setMessage("Sync Offline Scan QrCode.")

                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                checklogoutotordeleteaccount = "LogoutYES";
                                                mArrayListQrCodeofflinesync = (ArrayList<QrcodeModel>) dataBaseHandler.getVisitorDataissync("NO");
                                                SendQrCodeScanDataOfflineSync(mArrayListQrCodeofflinesync.get(0).getCompany_name(), mArrayListQrCodeofflinesync.get(0).getCoordinator_designation(), mArrayListQrCodeofflinesync.get(0).getAddress(), mArrayListQrCodeofflinesync.get(0).getMobile_number(), mArrayListQrCodeofflinesync.get(0).getCoordinator_name(), mArrayListQrCodeofflinesync.get(0).getEmail());

                                                // Continue with delete operation
                                            }
                                        })

                                        // A null listener allows the button to dismiss the dialog and take no further action.
                                        .setNegativeButton(android.R.string.no, null)
                                        .setIcon(R.drawable.applogo)
                                        .show();
                            } else {
                                SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
                                db.delete("ExhibitorList", null, null);
                                db.delete("ExhibitorListMeeting", null, null);
                                db.delete("VisitorListMeeting", null, null);
                                db.delete("QrcodeTable", null, null);
                              //  db.delete("QrcodeTableofflinesync", null, null);
                                db.delete("VisitorList", null, null);
                                db.delete("Exhibitordata", null, null);
                                db.delete("ProductDataTable", null, null);
                                db.delete("MeetingShedule", null, null);
                               // db.delete("CheckEMailSyncQrcode", null, null);


                                db.close();
                                Logout(UserId);
                            }


                        } else {
                            Toast.makeText(MyProfile.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                        //Toast.makeText(context,"Adapter",Toast.LENGTH_LONG).show();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        //Toast.makeText(context,"Adapter",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();

            }
        });
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_company = (TextView) findViewById(R.id.tv_company);
        tv_designation = (TextView) findViewById(R.id.tv_designation);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_email = (TextView) findViewById(R.id.tv_email);


        sharedpreferences = getSharedPreferences(ActivityMainHomePage.MyPREFERENCES,
                Context.MODE_PRIVATE);
        //for exhibitor
        //   str_qrCode = ActivitySplash.sh.getString("qrcode", null);
        CheckLogin = sharedpreferences.getString("CheckLogin", null);
        isFirst = sharedpreferences.getString("isFirst", "");
        Name = sharedpreferences.getString("Name", "");
        Address = sharedpreferences.getString("Address", "");
        Designation = sharedpreferences.getString("Designation", "");
        Company = sharedpreferences.getString("Company", "");
        Emailid = sharedpreferences.getString("Emailid", "");
        UserId = sharedpreferences.getString("UserId", null);
        tv_name.setText(Name);
        tv_company.setText(Company);
        tv_designation.setText("(" + Designation + ")");
        tv_address.setText(Address);
        tv_email.setText(Emailid);
        Log.d("test", "onStartCommand: isFirst " + isFirst);




        imageS = ActivityMainHomePage.sh.getString("imagePreferance", null);
        Log.e("MyFormatQrcode", imageS);
        String Name = ActivityMainHomePage.sh.getString("name", null);
        TxtName.setText(Name);
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        qrgEncoder = new QRGEncoder(imageS, null, QRGContents.Type.TEXT, smallerDimension);
        try {
            Bitmap bitmapResult = qrgEncoder.encodeAsBitmap();
            imageView.setImageBitmap(bitmapResult);
            // btnSave.setVisibility(View.VISIBLE);
        } catch (WriterException e) {
            e.printStackTrace();
        }

      /*  Bitmap imageB = decodeToBase64(imageS);
        imageView.setImageBitmap(imageB);*/
        ActivityMainHomePage.editor.putString("loginTest", "true");


        if (CheckLogin.equals("Visitor")) {
            BtnSave.setVisibility(View.VISIBLE);
            scanBtn.setVisibility(View.VISIBLE);
            tv_delete.setVisibility(View.VISIBLE);
        } else {
            BtnSave.setVisibility(View.GONE);
            scanBtn.setVisibility(View.GONE);
            tv_delete.setVisibility(View.GONE);
        }
        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(MyProfile.this, VisitorList.class);

                startActivityForResult(intent, 1);


            }
        });


        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    if (ActivityCompat.checkSelfPermission(MyProfile.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                        Intent intent1 = new Intent(MyProfile.this, ScannerQrCode.class);
                        startActivityForResult(intent1, 12);


                    } else {
                        ActivityCompat.requestPermissions(MyProfile.this, new
                                String[]{Manifest.permission.CAMERA}, 201);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkofflinesync();
    }

    private void checkofflinesync() {
        if (AppStatus.getInstance(MyProfile.this).isOnline()) {

            if (mArrayListQrCodeofflinesync.size() > 0) {
                SendQrCodeScanDataOfflineSync(mArrayListQrCodeofflinesync.get(0).getCompany_name(), mArrayListQrCodeofflinesync.get(0).getCoordinator_designation(), mArrayListQrCodeofflinesync.get(0).getAddress(), mArrayListQrCodeofflinesync.get(0).getMobile_number(), mArrayListQrCodeofflinesync.get(0).getCoordinator_name(), mArrayListQrCodeofflinesync.get(0).getEmail());
            }
            //

        } else {
            //Toast.makeText(this, "QrCode Format Not Match", Toast.LENGTH_SHORT).show();
        }
    }

    private void Logout(String userid) {


        ProgressDialog pd = new ProgressDialog(MyProfile.this, R.style.StyledDialog);
        pd.setMessage("Logout Wait..");
        pd.show();


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", userid);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = "";
        // Create a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        if (CheckLogin.equals("Visitor")) {
            url = Plastfocus_App_Api_List.Api_VIsitorLogout;
        } else {
            url = Plastfocus_App_Api_List.Api_ExhibitorLogout;
        }

        System.out.println("Logout: " + url);
        System.out.println("Logout: " + jsonObject.toString());
        // Create a JsonObjectRequest with POST method, URL, headers, and parameters
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Logout: " + response.toString());
                        SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();

                        editor.putString("loginTest1", "");


                        editor.commit();
                        Intent i = new Intent(getApplicationContext(), LoginMenuActivity.class);
                        startActivity(i);
                        finish();
                        pd.dismiss();

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
                            VolleyErrorHelper.errorcoderesponce(message, MyProfile.this);
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

    private void setAlpha(float alpha, View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            for (View view : views) {
                view.setAlpha(alpha);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        // Log.e("MyQrCodeDataScan2leangth",scannedInfo+"");
        if (data == null) {
            // Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            startActivity(new Intent(MyProfile.this, ActivityMainHomePage.class));

        } else if (requestCode == 12) {
            if (data != null) {


                resulT = data.getStringExtra("intentData");
                Log.d("resulT", "resulT " + resulT);


                array = resulT.split("\t");

                for (int i = 0; i < array.length; i++) {
                    Log.d("test", "onActivityResult: " + i + "  " + array[i]);
                    Log.d("test", "onActivityResultArray: " + array.length);

                    if (array.length == 1) {
                        if (i == 3)
                            company = array[i];
                        Log.d("test", "onActivityResultcompany: " + i + "  " + company);

                    } else if (i == 0) {
                        Registrationno = array[i];
                        Log.d("test", "onActivityResult: " + i + "  " + Registrationno);

                    } else if (i == 1) {
                        name = array[i];

                        Log.d("test", "onActivityResult: " + i + "  " + name);
                    } else if (i == 2) {
                        deignation = array[i];
                        Log.d("test", "onActivityResult: " + i + "  " + deignation);

                    } else if (i == 3) {
                        company = array[i];
                        Log.d("test", "onActivityResult: " + i + "  " + company);


                    } else if (i == 4) {
                        country = array[i];
                        Log.d("test", "onActivityResultCountry: " + i + "  " + country);


                    } else if (i == 5) {
                        email = array[i];
                        Log.d("test", "onActivityResult: " + i + "  " + email);


                    } else if (i == 6) {
                        phone = array[i];
                        Log.d("test", "onActivityResult: " + i + "  " + phone);

                    }




                }

            }

            if (AppStatus.getInstance(MyProfile.this).isOnline()) {
                Log.e("CehckQrcodeformat1", resulT);
                if (resulT.contains("PlatFocus")) {
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
                    Log.e("CehckQrcodeformat", resulT);

                    if (resulT.contains("PlatFocus") && !resulT.contains("PlatFocus Visitor")) {
                        Toast.makeText(MyProfile.this, "Exhibitor already scanned", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MyProfile.this, "Invalid QRCode", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Log.e("CehckQrcodeformat", resulT);
                    if (resulT.contains("PlatFocus") && !resulT.contains("PlatFocus Visitor")) {
                        QrcodeModel qrcodeModel = new QrcodeModel();
                        qrcodeModel.setAddress(country);
                        qrcodeModel.setCoordinator_name(name);
                        qrcodeModel.setCoordinator_designation(deignation);
                        qrcodeModel.setCompany_name(company);
                        qrcodeModel.setMobile_number(phone);
                        qrcodeModel.setEmail(email);
                        qrcodeModel.setIssync("NO");

                        dataBaseHandler.InserVisitorList(qrcodeModel);
                        Toast.makeText(MyProfile.this, "Your Record Save Offline ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Invalid QRCode", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void SendQrCodeScanData(String company_name, String coordinator_designation, String address, String mobile_number, String coordinator_name, String email) {


        ProgressDialog pd = new ProgressDialog(MyProfile.this, R.style.StyledDialog);
        pd.setMessage("Wait..");
        pd.show();

        DataBaseHandler dataBaseHandler = new DataBaseHandler(MyProfile.this);
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
        System.out.println("Qrcode: " + url);
        System.out.println("Qrcode: " + jsonObject.toString());
        // Create a JsonObjectRequest with POST method, URL, headers, and parameters
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        System.out.println("Qrcode: " + response.toString());
                        //  SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();

                        DataBaseHandler dataBaseHandler = new DataBaseHandler(MyProfile.this);

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

                            Toast.makeText(MyProfile.this, "Exhibitor Scanned Sucessfully", Toast.LENGTH_SHORT).show();
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
                            VolleyErrorHelper.errorcoderesponce(message, MyProfile.this);
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

    private void SendQrCodeScanDataOfflineSync(String company_name, String coordinator_designation, String address, String mobile_number, String coordinator_name, String email) {


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
        System.out.println("OfflineQrcode: " + url);
        System.out.println("OfflineQrcode: " + jsonObject.toString());
        // Create a JsonObjectRequest with POST method, URL, headers, and parameters
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("OfflineQrcode: " + response.toString());
                        //  SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();

                        DataBaseHandler dataBaseHandler = new DataBaseHandler(MyProfile.this);

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
                            if (checklogoutotordeleteaccount.equals("LogoutYES")) {
                                SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
                                db.delete("ExhibitorList", null, null);
                                db.delete("ExhibitorListMeeting", null, null);
                                db.delete("VisitorListMeeting", null, null);
                                db.delete("QrcodeTable", null, null);
                                db.delete("QrcodeTableofflinesync", null, null);
                                db.delete("VisitorList", null, null);
                                db.delete("Exhibitordata", null, null);
                                db.delete("ProductDataTable", null, null);
                                db.delete("MeetingShedule", null, null);
                                db.delete("CheckEMailSyncQrcode", null, null);


                                db.close();
                                Log.e("CheckControlSync", "LogoutAccount");
                                Toast.makeText(MyProfile.this, "Logout Sucessfully.", Toast.LENGTH_SHORT).show();

                                Logout(UserId);
                            } else if (checklogoutotordeleteaccount.equals("DeleteYES")) {
                                // Toast.makeText(MyProfile.this, "Upload QRCode Data Sucessfully.", Toast.LENGTH_SHORT).show();
                                SQLiteDatabase db = dataBaseHandler.getWritableDatabase();


                                db.delete("ExhibitorList", null, null);
                                db.delete("ExhibitorListMeeting", null, null);
                                db.delete("VisitorListMeeting", null, null);
                                db.delete("QrcodeTable", null, null);
                                db.delete("QrcodeTableofflinesync", null, null);
                                db.delete("VisitorList", null, null);
                                db.delete("Exhibitordata", null, null);
                                db.delete("ProductDataTable", null, null);
                                db.delete("MeetingShedule", null, null);
                                db.delete("CheckEMailSyncQrcode", null, null);

                                db.close();
                                Log.e("CheckControlSync", "RemiveAccount");
                                //  Toast.makeText(MyProfile.this, "Logout Sucessfully.", Toast.LENGTH_SHORT).show();
                                RemoveAccount(UserId);
                            } else {
                                Log.e("CheckControlSync", "NoSelected");
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
                            VolleyErrorHelper.errorcoderesponce(message, MyProfile.this);
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

    private void RemoveAccount(String userid) {


        ProgressDialog pd = new ProgressDialog(MyProfile.this, R.style.StyledDialog);
        pd.setMessage("Account Close..");
        pd.show();


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", userid);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Create a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = Plastfocus_App_Api_List.Api_RemoveAccount;
        System.out.println("Logout: " + url);
        System.out.println("Logout: " + jsonObject.toString());
        // Create a JsonObjectRequest with POST method, URL, headers, and parameters
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Logout: " + response.toString());
                        SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();

                        editor.putString("loginTest1", "");


                        editor.commit();
                        Intent i = new Intent(getApplicationContext(), LoginMenuActivity.class);
                        startActivity(i);
                        finish();
                        pd.dismiss();

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
                            VolleyErrorHelper.errorcoderesponce(message, MyProfile.this);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        // System.out.println("ErrorMessge" +body.toString());
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
}