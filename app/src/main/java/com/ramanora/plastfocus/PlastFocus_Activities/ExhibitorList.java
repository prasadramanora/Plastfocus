package com.ramanora.plastfocus.PlastFocus_Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
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
import com.ramanora.plastfocus.PlastFocus_Adapter.Adapter_ExibitorScanned_List;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.ExhibitorModel;

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
import java.util.Collections;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class ExhibitorList extends Activity {

    private final int PermissionStorage = 11;
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
    String firebasetoken, isUploaded;
    public static String MyPREFERENCES = "myprefe";
    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;
    DataBaseHandler dataBaseHandler;
    ArrayList<ExhibitorModel> mArrayListQrCode;
    RecyclerView recyclerView;
    ImageView iv_sync;
    LinearLayoutManager manager;
    String next_page_id = "1";
    Adapter_ExibitorScanned_List adapter;
    Button btn_loadmore;
    ImageView iv_downloadcsv;
    public static String checkvisitorfound = "";

    //  DataBaseHandler dataBaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibitiorlist);

        btn_loadmore = findViewById(R.id.btn_loadmore);
        iv_downloadcsv = findViewById(R.id.iv_downloadcsv);
        iv_downloadcsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Log.d("test", "onCreate: SDK_INT  if " + Build.VERSION.SDK_INT);
                    if (hasPermissions_13(ExhibitorList.this, permissions_13)) {

                        exportDB();

                    } else {
                        //  //Log.d("test", "has no Permissions: ");
                        ActivityCompat.requestPermissions((ExhibitorList.this), permissions_13, PermissionStorage);

                    }


                } else {
                    Log.d("test", "onCreate: else" + Build.VERSION.SDK_INT);
                    if (hasPermissions(ExhibitorList.this, permissions)) {


                        exportDB();
                    } else {
                        //  //Log.d("test", "has no Permissions: ");
                        ActivityCompat.requestPermissions((ExhibitorList.this), permissions, PermissionStorage);

                    }

                }


            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        dataBaseHandler = new DataBaseHandler(ExhibitorList.this);
        mArrayListQrCode = new ArrayList<>();

        iv_sync = findViewById(R.id.iv_sync);
        iv_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exhibitorlist();
            }
        });
        if (AppStatus.getInstance(ExhibitorList.this).isOnline()) {

            exhibitorlist();


        } else {
            mArrayListQrCode = (ArrayList<ExhibitorModel>) dataBaseHandler.getExisitorData();
            if (mArrayListQrCode.size() > 0) {
                getExhibitorData();

            } else {
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        }

        Intent intent = getIntent();

        if (intent.hasExtra("checkvisitorfound")) {
            checkvisitorfound = intent.getStringExtra("checkvisitorfound");
            exhibitorlist();
            Log.e("checkvisitorfound", checkvisitorfound);
        } else {
            checkvisitorfound = intent.getStringExtra("checkvisitorfound");
            Log.e("Checkmeeting", "NotFound");
        }

    }

    private void exhibitorlist() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler(ExhibitorList.this);
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.delete("Exhibitordata", null, null);
        db.close();
        sh = getSharedPreferences(MyPREFERENCES, 0);
        editor = sh.edit();

        String UserId = sh.getString("UserId", null);
        ProgressDialog pd = new ProgressDialog(ExhibitorList.this, R.style.StyledDialog);
        pd.setMessage("Refresh Exhibitor Data Wait ..");
        pd.show();
        String url = Plastfocus_App_Api_List.Api_ExhibitorList + UserId + "/visitors?page=1&per_page=100";

        Log.e("ExhibitorListData", url);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ExhibitorList.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d("UI thread", "I am the UI thread");
                        JSONArray js = null;
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.e("ExhibitorListData", response.toString());
                            js = jsonObject.getJSONArray("data");
                            if (js.length() > 0) {
                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject jsonObject1 = js.getJSONObject(i);
                                    ExhibitorModel exhibitorModel = new ExhibitorModel();
                                    String id = jsonObject1.getString("id");
                                    exhibitorModel.setId(id);
                                    String full_name = jsonObject1.getString("full_name");
                                    exhibitorModel.setFull_name(full_name);
                                    String email = jsonObject1.getString("email");
                                    exhibitorModel.setEmail(email);
                                    String phone_number = jsonObject1.getString("phone_number");
                                    exhibitorModel.setPhone_number(phone_number);
                                    String company = jsonObject1.getString("company");
                                    exhibitorModel.setCompany(company);
                                    String designation = jsonObject1.getString("designation");
                                    exhibitorModel.setDesignation(designation);
                                    String address = jsonObject1.getString("address");
                                    exhibitorModel.setAddress(address);
                                    String city = jsonObject1.getString("city");
                                    exhibitorModel.setCity(city);
                                    String state = jsonObject1.getString("state");
                                    exhibitorModel.setState(state);
                                    String country = jsonObject1.getString("country");
                                    exhibitorModel.setCountry(country);
                                    String zip_code = jsonObject1.getString("zip_code");
                                    // next_page_id=jsonObject1.getString("next_page_id");

                                    exhibitorModel.setZip_code(zip_code);
                                    JSONArray products = jsonObject1.getJSONArray("products");
                                    exhibitorModel.setProducts(products.toString());

                                    dataBaseHandler.InsertExhibitorData(exhibitorModel);
                                    ;
                                    pd.dismiss();
                                    // mArrayListQrCode.add(qrcodeModel);
                                }

                            } else {
                                Toast.makeText(ExhibitorList.this, " Data Not Found", Toast.LENGTH_SHORT).show();
                            }
                            pd.dismiss();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(ExhibitorList.this, js.length()+" Exhibitor Found", Toast.LENGTH_SHORT).show();
                        getExhibitorData();


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
                    VolleyErrorHelper.errorcoderesponce(message, ExhibitorList.this);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    private void exportDB() {
        ProgressDialog pd = new ProgressDialog(ExhibitorList.this, R.style.StyledDialog);
        pd.setMessage("Csv Genrated Wait");
        mArrayListQrCode = (ArrayList<ExhibitorModel>) dataBaseHandler.getExisitorData();

        if (mArrayListQrCode.size() > 0) {
            pd.show();


            try {

                String fileName = "VisitorlistCsv.csv";
                File directorynew = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(directorynew, fileName);

                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                SQLiteDatabase db = dataBaseHandler.getReadableDatabase();
                Cursor curCSV = db.rawQuery("SELECT * FROM Exhibitordata", null);
                csvWrite.writeNext(curCSV.getColumnNames());
                while (curCSV.moveToNext()) {
                    //Which column you want to exprort
                    String arrStr[] = {curCSV.getString(0), curCSV.getString(1),
                            curCSV.getString(2),
                            curCSV.getString(3),
                            curCSV.getString(4),
                            curCSV.getString(5),
                            curCSV.getString(6),
                            curCSV.getString(7),
                            curCSV.getString(8),
                            curCSV.getString(9),
                            curCSV.getString(10),
                            curCSV.getString(11),

                            curCSV.getString(12)};
                    csvWrite.writeNext(arrStr);
                }
                csvWrite.close();
                curCSV.close();
                Toast.makeText(this, " Csv Genrated Sucessfully", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                Uri pdfUri;
                // Uri uri = FileProvider.getUriForFile(ExhibitorList.this, BuildConfig.APPLICATION_ID + ".provider", root);

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

    private void getExhibitorData() {
        mArrayListQrCode = (ArrayList<ExhibitorModel>) dataBaseHandler.getExisitorData();
        if (mArrayListQrCode.size() > 0) {

            Collections.reverse(mArrayListQrCode);
            adapter = new Adapter_ExibitorScanned_List(getApplicationContext(), mArrayListQrCode);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
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

       Intent intent=new Intent(getApplicationContext(), ActivityMainHomePage.class);
        startActivity(intent);
        finish();
    }
}
