package com.ramanora.plastfocus.PlastFocus_Activities;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.ramanora.plastfocus.PlastFocus_FirebaseServices.MyFirebaseMessagingService;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;

import com.ramanora.plastfocus.PlastFocus_ApiList.Plastfocus_App_Api_List;
import com.ramanora.plastfocus.PlastFocus_Utils.AppStatus;
import com.ramanora.plastfocus.PlastFocus_Utils.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class RegistrationScreen extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    JSONArray jsonArray = new JSONArray();


    RelativeLayout rl_categorygroup1;
    LinearLayout rl_categoryitems2, rl_categoryitems, rl_categoryitems3, rl_categoryitems4, rl_categoryitems5, rl_categoryitems6;
    ImageView iv_lowerarraow, iv_upperarraow, iv_lowerarraow2, iv_upperarraow2, iv_lowerarraow3, iv_upperarraow3, iv_upperarraow4, iv_lowerarraow4, iv_lowerarraow5, iv_upperarraow5, iv_upperarraow6, iv_lowerarraow6;
    EditText edt_country,edt_name, edt_zipcode,edit_designation, edt_orgnization, edt_address, edt_mobile, edt_email, edt_city, edt_state;
    public static Bitmap bitmap;
    public static String zipcode="",str_city = "", str_state = "", str_QrCode = "", str_country="",str_Name = "", str_Designation = "", str_Company = "", Str_addtress = "", str_Email = "", str_FullPhone = "";
    public final static int QRcodeWidth = 500;
    TextView tv_login,tv_cat1,tv_cat2,tv_cat3,tv_cat4,tv_cat5,tv_cat6;
    Button btn_submit;

    String[] strAr1 = {"3d Printing", "ecom platforms", "ERP Software Providers", "Material Formulating / Compounding"};
    String[] strAr2 = new String[]{"3D Printing Machines", "Auxiliary & Testing Equipment", "Bag & Sack Making Equipments", "Blow Moulding Machines", "Extruders & Extrusion lines", "Industrial Cooler", "Industrial Equipments", "Injection Moulding Machines", "Material Handling", "Measurement & Testing Equipment", "Packaging Machines", "Parts & Components", "Plastics Welding Equipment", "Post Processing / Converting Machines", "Preprocessing and Recycling Machines", "Printing & Finishing Machines", "Productivity Improvement", "Reactive or Reinforced Resins Machines", "Recycling Machines", "Rotating Moulding Machines", "Rubber Processing Equipment", "Storage Silos", "Vaccum & Thermoforming Machines"};
    String[] strAr3 = {"Mould Creators", "Mould Designers"};

    String[] strAr4 = {"Bio-Plastics & Degradable Plastics", "Packaging Materials", "Plastics Tooling & Engineering Products", "Semi-Finished Products", "White label manufacturers"};

    String[] strAr5 = {"Additives", "Adhesives & Glues", "BioPlastics", "CAD/CAM/CAE Software", "Calcium zinc STABILIZERS", "Coating Compounds", "Colour Pigments", "Composite Material", "Experts & Consultants", "Fillers", "Foams & Intermediates", "Granules", "Lead free stabilizer", "Masterbatches", "Plastic granules", "PVC CHEMICALS", "PVC HEAT STABILIZERS", "Reinforcing Fibres / Materials", "Rubbers & Elastomers", "Semi-finished Products", "Synthetic Fibres", "ThermoPlastics", "Thermosets"};
    // String [] menuitems= ["3D Printing Machines", "Auxiliary & Testing Equipment", "Bag & Sack Making Equipments", "Blow Moulding Machines", "Extruders & Extrusion lines", "Industrial Cooler", "Industrial Equipments", "Injection Moulding Machines", "Material Handling", "Measurement & Testing Equipment", "Packaging Machines", "Parts & Components", "Plastics Welding Equipment", "Post Processing / Converting Machines", "Preprocessing and Recycling Machines", "Printing & Finishing Machines", "Productivity Improvement", "Reactive or Reinforced Resins Machines", "Recycling Machines", "Rotating Moulding Machines", "Rubber Processing Equipment", "Storage Silos", "Vaccum & Thermoforming Machines"];
    String[] strAr6 = {"Consultants", "Machinery", "Software", "Technology"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        findvewbyids();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_Name="";
                str_country="";
                str_city="";
                str_state="";
                zipcode="";
                str_Designation="";
                str_Company="";
                Str_addtress="";
                str_Email="";
                str_FullPhone="";
                str_Name = edt_name.getText().toString();
                str_country=edt_country.getText().toString();
                str_city = edt_city.getText().toString();
                str_state = edt_state.getText().toString();
                zipcode=edt_zipcode.getText().toString();
                str_Designation = edit_designation.getText().toString();
                str_Company = edt_orgnization.getText().toString();
                Str_addtress = edt_address.getText().toString() + " , " + str_city + " , " + str_state+" , "+zipcode;
                str_Email = edt_email.getText().toString();
                str_FullPhone = edt_mobile.getText().toString();

              //  str_QrCode = "101" + "\t" + str_Name + "\t" + str_Designation + "\t" + str_Company + "\t" + Str_addtress + "\t" + str_Email + "\t" + str_FullPhone;
                str_QrCode = "PlatFocus" + "\t" + str_Name + "\t" + str_Designation + "\t" + str_Company + "\t" + Str_addtress + "\t" + str_Email + "\t" + str_FullPhone+"\t" + "0";

                SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();


                editor.putString("imagePreferance", str_QrCode);
                editor.putString("Name", str_Name);
                editor.putString("Address", Str_addtress);
                editor.putString("Designation", str_Designation);
                editor.putString("Company", str_Company);
                editor.putString("Emailid", str_Email);

                editor.commit();
                // Toast.makeText(RegistrationScreen.this, "Test", Toast.LENGTH_SHORT).show();
                if (edt_name.getText().toString().length() == 0) {
                    edt_name.setError("Enter Name");
                    edt_name.requestFocus();
                } else if (edt_orgnization.getText().toString().length() == 0) {
                    edt_orgnization.setError("Enter Orgnization");
                    edt_orgnization.requestFocus();
                } else if (edit_designation.getText().toString().length() == 0) {
                    edit_designation.setError("Enter Designation");
                    edit_designation.requestFocus();
                } else if (edt_address.getText().toString().length() == 0) {
                    edt_address.setError("Enter Address");
                    edt_address.requestFocus();
                } else if (edt_mobile.getText().toString().length() == 0) {
                    edt_mobile.setError("Enter Mobile Number");
                    edt_mobile.requestFocus();
                } else if (edt_email.getText().toString().length() == 0) {
                    edt_email.setError("Enter Email");
                    edt_email.requestFocus();
                }else if (edt_zipcode.getText().toString().length() == 0) {
                    edt_zipcode.setError("Enter Zipcode");
                    edt_zipcode.requestFocus();
                }
                else if (edt_city.getText().toString().length() == 0) {
                    edt_city.setError("Enter City");
                    edt_city.requestFocus();
                }
                else if (edt_state.getText().toString().length() == 0) {
                    edt_state.setError("Enter State");
                    edt_state.requestFocus();
                }
                else if (edt_country.getText().toString().length() == 0) {
                    edt_country.setError("Enter Country");
                    edt_country.requestFocus();
                }
                else {
                    try {

                        JSONObject jsonObject = new JSONObject();
                        //jsonObject=new JSONObject();
                        jsonObject.put("full_name", edt_name.getText().toString());
                        jsonObject.put("country", edt_country.getText().toString());
                        jsonObject.put("phone_number", edt_mobile.getText().toString());
                        jsonObject.put("products", jsonArray);
                        jsonObject.put("email", edt_email.getText().toString());
                        jsonObject.put("state", edt_state.getText().toString());
                        jsonObject.put("company", edt_orgnization.getText().toString());
                        jsonObject.put("city",edt_city.getText().toString());
                        jsonObject.put("zip_code", edt_zipcode.getText().toString());
                        jsonObject.put("address", edt_address.getText().toString());
                        jsonObject.put("apns_token", "");

                        jsonObject.put("designation", edit_designation.getText().toString());
                        if (AppStatus.getInstance(RegistrationScreen.this).isOnline()) {

                            RegistraionApi(jsonObject);
                        } else {
                            Toast.makeText(RegistrationScreen.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException jse) {
                        jse.printStackTrace();
                    }


                }
            }
        });
    }

    private void RegistraionApi(JSONObject jsonObject) {


        ProgressDialog pd = new ProgressDialog(RegistrationScreen.this,R.style.StyledDialog);
        pd.setMessage("Visitor Registrartion Processing..");
        pd.show();

        // Create a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        System.out.println("RegistrationVisitor: " + Plastfocus_App_Api_List.Api_Registration);
        System.out.println("RegistrationVisitor: " + jsonObject.toString());
        // Create a JsonObjectRequest with POST method, URL, headers, and parameters
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Plastfocus_App_Api_List.Api_Registration, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("RegistrationVisitor: " + response.toString());
                            JSONObject jsonObject1=new JSONObject(response.toString());
                            // String Userid=jsonObject1.getString("id");
                            JSONObject jsonObject2=jsonObject1.getJSONObject("data");
                            String Userid=jsonObject2.getString("id");
                            SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();

                            editor.putString("UserId", Userid);

                            editor.commit();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        getMeetingDateandTimeApi();

                        genrateQrcodeimage();
                        Log.d("testApi1", "onResponse:ExhibitorData ==>>" + response);
                        Intent i = new Intent(getApplicationContext(), ActivityMainHomePage.class);
                        startActivity(i);
                        finish();
                        //getExhibitorData();

                    pd.dismiss();
                        //Toast.makeText(RegistrationScreen.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
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
                            VolleyErrorHelper.errorcoderesponce(message, RegistrationScreen.this);
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

    private void genrateQrcodeimage() {
        try {
            // bitmap = TextToImageEncode(str_QrCode);
            Log.e("CheckBitmap", bitmap + "");
            SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();

            editor.putString("loginTest1", "true");
            editor.putString("CheckLogin", "Visitor");
            editor.commit();
            Intent i = new Intent(getApplicationContext(), ActivityMainHomePage.class);
            startActivity(i);
            finish();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void findvewbyids() {
        edt_name = findViewById(R.id.edt_name);
        edt_country= findViewById(R.id.edt_country);
        edt_zipcode= findViewById(R.id.edt_zipcode);
        edit_designation = findViewById(R.id.edt_designation);
        edt_orgnization = findViewById(R.id.edt_orgnization);
        edt_address = findViewById(R.id.edt_address);
        tv_cat1 = findViewById(R.id.tv_cat1);
        tv_cat2 = findViewById(R.id.tv_cat2);
        tv_cat3 = findViewById(R.id.tv_cat3);
        tv_cat4 = findViewById(R.id.tv_cat4);
        tv_cat5 = findViewById(R.id.tv_cat5);
        tv_cat6 = findViewById(R.id.tv_cat6);

        edt_email = findViewById(R.id.edt_email);
        edt_mobile = findViewById(R.id.edt_mobile);
        btn_submit = findViewById(R.id.btn_submit);
        tv_login = findViewById(R.id.tv_login);
        edt_city = findViewById(R.id.edt_city);
        edt_state = findViewById(R.id.edt_state);
        rl_categorygroup1 = findViewById(R.id.rl_categorygroup1);
        rl_categoryitems2 = findViewById(R.id.rl_categoryitems2);
        rl_categoryitems3 = findViewById(R.id.rl_categoryitems3);

        rl_categoryitems4 = findViewById(R.id.rl_categoryitems4);
        rl_categoryitems5 = findViewById(R.id.rl_categoryitems5);
        rl_categoryitems6 = findViewById(R.id.rl_categoryitems6);
        iv_lowerarraow2 = findViewById(R.id.iv_lowerarraow2);
        iv_lowerarraow = findViewById(R.id.iv_lowerarraow);
        iv_upperarraow = findViewById(R.id.iv_upperarraow);

        iv_lowerarraow3 = findViewById(R.id.iv_lowerarraow3);
        iv_upperarraow3 = findViewById(R.id.iv_upperarraow3);

        iv_lowerarraow4 = findViewById(R.id.iv_lowerarraow4);
        iv_upperarraow4 = findViewById(R.id.iv_upperarraow4);


        iv_lowerarraow5 = findViewById(R.id.iv_lowerarraow5);
        iv_upperarraow5 = findViewById(R.id.iv_upperarraow5);

        iv_lowerarraow6 = findViewById(R.id.iv_lowerarraow6);
        iv_upperarraow6 = findViewById(R.id.iv_upperarraow6);

        iv_upperarraow2 = findViewById(R.id.iv_upperarraow2);
        iv_upperarraow6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_upperarraow6.setVisibility(View.GONE);
                iv_lowerarraow6.setVisibility(View.VISIBLE);
                rl_categoryitems6.setVisibility(View.GONE);
            }
        });
        iv_upperarraow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_upperarraow5.setVisibility(View.GONE);
                iv_lowerarraow5.setVisibility(View.VISIBLE);
                rl_categoryitems5.setVisibility(View.GONE);
            }
        });
        iv_upperarraow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_upperarraow4.setVisibility(View.GONE);
                iv_lowerarraow4.setVisibility(View.VISIBLE);
                rl_categoryitems4.setVisibility(View.GONE);
            }
        });
        iv_upperarraow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_upperarraow3.setVisibility(View.GONE);
                iv_lowerarraow3.setVisibility(View.VISIBLE);
                rl_categoryitems3.setVisibility(View.GONE);
            }
        });
        tv_cat4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                rl_categoryitems4.setVisibility(View.VISIBLE);
                rl_categoryitems4.removeAllViews();
                for (int i = 0; i < strAr4.length; i++) {
                    CheckBox ch = new CheckBox(RegistrationScreen.this);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                    ch.setTextColor(R.color.colurprimary);

                    rl_categoryitems4.addView(ch);
                    ch.setText("");
                    ch.setText(strAr4[i]);
                    iv_lowerarraow4.setVisibility(View.GONE);
                    iv_upperarraow4.setVisibility(View.VISIBLE);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                }
            }
        });
        iv_lowerarraow4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                rl_categoryitems4.setVisibility(View.VISIBLE);
                rl_categoryitems4.removeAllViews();
                for (int i = 0; i < strAr4.length; i++) {
                    CheckBox ch = new CheckBox(RegistrationScreen.this);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                    ch.setTextColor(R.color.colurprimary);
                    rl_categoryitems4.addView(ch);
                    ch.setText(strAr4[i]);
                    iv_lowerarraow4.setVisibility(View.GONE);
                    iv_upperarraow4.setVisibility(View.VISIBLE);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                }
            }
        });
        tv_cat6.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                rl_categoryitems6.setVisibility(View.VISIBLE);
                rl_categoryitems6.removeAllViews();
                for (int i = 0; i < strAr6.length; i++) {
                    CheckBox ch = new CheckBox(RegistrationScreen.this);
                    ch.setTextColor(R.color.colurprimary);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                    rl_categoryitems6.addView(ch);
                    ch.setText(strAr6[i]);
                    iv_lowerarraow6.setVisibility(View.GONE);
                    iv_upperarraow6.setVisibility(View.VISIBLE);
                }
            }
        });
        iv_lowerarraow6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_categoryitems6.setVisibility(View.VISIBLE);
                rl_categoryitems6.removeAllViews();
                for (int i = 0; i < strAr6.length; i++) {
                    CheckBox ch = new CheckBox(RegistrationScreen.this);
                    ch.setTextColor(R.color.colurprimary);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                    rl_categoryitems6.addView(ch);
                    ch.setText(strAr6[i]);
                    iv_lowerarraow6.setVisibility(View.GONE);
                    iv_upperarraow6.setVisibility(View.VISIBLE);
                }
            }
        });
        tv_cat5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                rl_categoryitems5.setVisibility(View.VISIBLE);
                rl_categoryitems5.removeAllViews();
                for (int i = 0; i < strAr5.length; i++) {
                    CheckBox ch = new CheckBox(RegistrationScreen.this);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                    ch.setTextColor(R.color.colurprimary);
                    rl_categoryitems5.addView(ch);
                    ch.setText(strAr5[i]);
                    iv_lowerarraow5.setVisibility(View.GONE);
                    iv_upperarraow5.setVisibility(View.VISIBLE);
                }
            }
        });
        iv_lowerarraow5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                rl_categoryitems5.setVisibility(View.VISIBLE);
                rl_categoryitems5.removeAllViews();
                for (int i = 0; i < strAr5.length; i++) {
                    CheckBox ch = new CheckBox(RegistrationScreen.this);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                    ch.setTextColor(R.color.colurprimary);
                    rl_categoryitems5.addView(ch);
                    ch.setText(strAr5[i]);
                    iv_lowerarraow5.setVisibility(View.GONE);
                    iv_upperarraow5.setVisibility(View.VISIBLE);
                }
            }
        });
        tv_cat3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                rl_categoryitems3.setVisibility(View.VISIBLE);
                rl_categoryitems3.removeAllViews();
                for (int i = 0; i < strAr3.length; i++) {
                    CheckBox ch = new CheckBox(RegistrationScreen.this);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                    ch.setTextColor(R.color.colurprimary);
                    rl_categoryitems3.addView(ch);
                    ch.setText(strAr3[i]);
                    iv_lowerarraow3.setVisibility(View.GONE);
                    iv_upperarraow3.setVisibility(View.VISIBLE);
                }
            }
        });
        iv_lowerarraow3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                rl_categoryitems3.setVisibility(View.VISIBLE);
                rl_categoryitems3.removeAllViews();
                for (int i = 0; i < strAr3.length; i++) {
                    CheckBox ch = new CheckBox(RegistrationScreen.this);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                    ch.setTextColor(R.color.colurprimary);
                    rl_categoryitems3.addView(ch);
                    ch.setText(strAr3[i]);
                    iv_lowerarraow3.setVisibility(View.GONE);
                    iv_upperarraow3.setVisibility(View.VISIBLE);
                }
            }
        });
        iv_upperarraow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_upperarraow.setVisibility(View.GONE);
                iv_lowerarraow.setVisibility(View.VISIBLE);
                rl_categoryitems.setVisibility(View.GONE);
            }
        });
        tv_cat1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                rl_categoryitems.setVisibility(View.VISIBLE);
                rl_categoryitems.removeAllViews();
                for (int i = 0; i < strAr1.length; i++) {
                    CheckBox ch = new CheckBox(RegistrationScreen.this);

                    ch.setTextColor(R.color.colurprimary);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                    rl_categoryitems.addView(ch);
                    ch.setText(strAr1[i]);
                    iv_lowerarraow.setVisibility(View.GONE);
                    iv_upperarraow.setVisibility(View.VISIBLE);
                }
            }
        });
        iv_lowerarraow.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                rl_categoryitems.setVisibility(View.VISIBLE);
                rl_categoryitems.removeAllViews();
                for (int i = 0; i < strAr1.length; i++) {
                    CheckBox ch = new CheckBox(RegistrationScreen.this);

                    ch.setTextColor(R.color.colurprimary);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                    rl_categoryitems.addView(ch);
                    ch.setText(strAr1[i]);
                    iv_lowerarraow.setVisibility(View.GONE);
                    iv_upperarraow.setVisibility(View.VISIBLE);
                }
            }
        });
        tv_cat2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                rl_categoryitems2.setVisibility(View.VISIBLE);
                rl_categoryitems2.removeAllViews();
                for (int i = 0; i < strAr2.length; i++) {
                    CheckBox ch = new CheckBox(RegistrationScreen.this);
                    ch.setTextColor(R.color.colurprimary);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                    rl_categoryitems2.addView(ch);
                    ch.setText(strAr2[i]);
                    iv_lowerarraow2.setVisibility(View.GONE);
                    iv_upperarraow2.setVisibility(View.VISIBLE);
                }
            }
        });
        iv_lowerarraow2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                rl_categoryitems2.setVisibility(View.VISIBLE);
                for (int i = 0; i < strAr2.length; i++) {
                    CheckBox ch = new CheckBox(RegistrationScreen.this);
                    ch.setTextColor(R.color.colurprimary);
                    ch.setOnCheckedChangeListener(RegistrationScreen.this);
                    rl_categoryitems2.addView(ch);
                    ch.setText(strAr2[i]);
                    iv_lowerarraow2.setVisibility(View.GONE);
                    iv_upperarraow2.setVisibility(View.VISIBLE);
                }
            }
        });
        iv_upperarraow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_upperarraow2.setVisibility(View.GONE);
                iv_lowerarraow2.setVisibility(View.VISIBLE);
                rl_categoryitems2.setVisibility(View.GONE);
            }
        });

        rl_categoryitems = findViewById(R.id.rl_categoryitems1);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), VIsitorLoginScreen.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), LoginMenuActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == btn_submit) {

        }
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
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String checkedText = buttonView.getText() + "";
        //Toast.makeText(this, "" + checkedText, Toast.LENGTH_SHORT).show();
        if (isChecked) {
            try {

                jsonArray.put(checkedText);

                       // Log.e("CheckJsonObject",jsonObject.toString());


            } catch (Exception jse) {
                jse.printStackTrace();
            }
        } else {

        }
    }

    private void getMeetingDateandTimeApi() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        // DataBaseHandler dataBaseHandler = new DataBaseHandler(VisitorList.this);
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.delete("MeetingShedule", null, null);
        ProgressDialog pd = new ProgressDialog(RegistrationScreen.this,R.style.StyledDialog);

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
                    VolleyErrorHelper.errorcoderesponce(message, RegistrationScreen.this);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.d("test", "onErrorResponse: Error " + error.getMessage());


            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}