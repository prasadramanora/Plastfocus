package com.ramanora.plastfocus.PlastFocus_Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Adapter.Adapter_Visitor_Scanned;

public class GetVisitorScanDeatilsPage extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    TextView tv_name, tv_address, tv_designation, tv_compny, tv_citytext, tv_mobile, tv_email;
    ImageView btnemail, btntxtmsg, btnWhatsapp;
    String city = "";
    String state = "";
    String zipcode = "";

    String country = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitorscandetails);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_name = findViewById(R.id.tv_name);
        tv_address = findViewById(R.id.tv_address);
        btnemail = findViewById(R.id.btnemail);
        btntxtmsg = findViewById(R.id.btntxtmsg);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);
        tv_designation = findViewById(R.id.tv_designation);
        tv_compny = findViewById(R.id.tv_comapny);
        tv_citytext = findViewById(R.id.tv_citytext);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_email = findViewById(R.id.tv_email);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String Name = extras.getString("Name");
            String Company = extras.getString("Company");
            String Designation = extras.getString("Designation");
            String Address = extras.getString("Address");
            String Mobile = extras.getString("Mobile");
            String Email = extras.getString("Email");
            if (extras.getString("city") == null || extras.getString("city").isEmpty()) {
                city = "-";
            } else {
                city = extras.getString("city");
            }
            if (extras.getString("state") == null || extras.getString("state").isEmpty()) {
                state = "-";
            } else {
                state = extras.getString("state");
            }
            if (extras.getString("country") == null || extras.getString("country").isEmpty()) {
                country = "-";
            } else {
                country = extras.getString("country");
            }
            if (extras.getString("zipcode") == null || extras.getString("zipcode").isEmpty()) {
                zipcode = "-";
            } else {
                zipcode = extras.getString("zipcode");
            }
            tv_name.setText(Adapter_Visitor_Scanned.OfflineSyncListQrcodeData.get(Adapter_Visitor_Scanned.positionindex).getCoordinator_name());
            tv_designation.setText(Adapter_Visitor_Scanned.OfflineSyncListQrcodeData.get(Adapter_Visitor_Scanned.positionindex).getCoordinator_designation());
            tv_compny.setText(Adapter_Visitor_Scanned.OfflineSyncListQrcodeData.get(Adapter_Visitor_Scanned.positionindex).getCompany_name());
            tv_address.setText(Adapter_Visitor_Scanned.OfflineSyncListQrcodeData.get(Adapter_Visitor_Scanned.positionindex).getAddress());
            tv_mobile.setText(Adapter_Visitor_Scanned.OfflineSyncListQrcodeData.get(Adapter_Visitor_Scanned.positionindex).getMobile_number());
            tv_email.setText(Adapter_Visitor_Scanned.OfflineSyncListQrcodeData.get(Adapter_Visitor_Scanned.positionindex).getEmail());
            // tv_name.setText(Adapter_Scanned_List.OfflineSyncListQrcodeData.get(Adapter_Scanned_List.positionindex).getCoordinator_name());
            tv_address.setText(Adapter_Visitor_Scanned.OfflineSyncListQrcodeData.get(Adapter_Visitor_Scanned.positionindex).getAddress() + "\n");

            // String NAme=data.spli
            //The key argument here must match that used in the other activity
        }


        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tv_mobile.getText().toString()));
                startActivity(dialIntent);


            }
        });
        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailActivity = new Intent(Intent.ACTION_SEND);
                emailActivity.setData(Uri.parse("mailto:"));
                emailActivity.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                emailActivity.setPackage("com.google.android.gm");
                emailActivity.setType("text/html");
                emailActivity.putExtra(Intent.EXTRA_EMAIL, new String[]{tv_email.getText().toString()});
                emailActivity.putExtra(Intent.EXTRA_SUBJECT, "enter your subject");
                emailActivity.putExtra(Intent.EXTRA_TEXT, "enter your email");
                startActivity(Intent.createChooser(emailActivity, "Select your Email Provider :"));
            }
        });
        btntxtmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    String phoneNo = "";//The phone number you want to text
                    String sms = "";//The message you want to text to the phone

                    Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", tv_mobile.getText().toString(), null));
                    smsIntent.putExtra("sms_body", sms);
                    startActivity(smsIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), VisitorList.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), VisitorList.class);
        startActivity(i);
        finish();
    }
}
