package com.ramanora.plastfocus.PlastFocus_Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Adapter.VisitorMeetingAdapter;

public class ExhibitorMeetingDeatils extends AppCompatActivity {
    ImageView btnemail, btntxtmsg, btnWhatsapp;
    TextView tv_website, tv_productgroup, tv_stall, tv_cname, tv_email1, tv_mobile1, tv_companyprofile, tv_address, tv_state, tv_designation, tv_pincode, tv_Name, tv_email, tv_city, tv_country, tv_mobile, tv_hall;
    Button btn_view;
    ImageView btn_bookmeeting;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibitormeeting);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_view = findViewById(R.id.btn_view);
        tv_website = findViewById(R.id.tv_website);
        btn_bookmeeting = findViewById(R.id.btn_bookmeeting);
        tv_productgroup = findViewById(R.id.tv_productgroup);
        tv_email1 = findViewById(R.id.tv_email1);
        tv_stall = findViewById(R.id.tv_stall);
        tv_companyprofile = findViewById(R.id.tv_companyprofile);
        btnemail = findViewById(R.id.btnemail);
        btntxtmsg = findViewById(R.id.btntxtmsg);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);
        tv_mobile1 = findViewById(R.id.tv_mobile1);
        tv_hall = findViewById(R.id.tv_hall);
        tv_cname = findViewById(R.id.tv_cname);
        tv_email = findViewById(R.id.tv_email);
        tv_Name = findViewById(R.id.tv_Name);
        tv_address = findViewById(R.id.tv_address);
        tv_state = findViewById(R.id.tv_state);
        tv_designation = findViewById(R.id.tv_designation);
        tv_pincode = findViewById(R.id.tv_pincode);
        tv_city = findViewById(R.id.tv_city);
        tv_country = findViewById(R.id.tv_country);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_cname.setText(VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getCompany_name());
        tv_address.setText("Company Address:" + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getAddress().replace("null", "-"));
        tv_state.setText(" State : " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getState().replace("null", "-"));
        tv_designation.setText("Designation : " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getCoordinator_designation().replace("null", "-"));
        tv_pincode.setText("Pincode : " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getPincode().replace("null", "-"));
        tv_Name.setText("Full Name : " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getSalutation().replace("null", "-") + " " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getCoordinator_name().replace("null", "-") + " " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getCoordinator_last_name().replace("null", "-"));
        tv_email.setText("Email Id : " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getEmail().replace("null", "-"));
        tv_city.setText("City : " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getCity().replace("null", "-"));
        tv_mobile.setText("Mobile Number : " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getMobile_number().replace("null", "-"));
        tv_country.setText("Country : " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getCountry().replace("null", "-"));
        tv_hall.setText("Hall No : " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getHall().replace("null", "-"));
        tv_companyprofile.setText("Company Profile: " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getCompany_profile().replace("null", "-"));
        tv_email1.setText("AlterNate Email  : " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getAlternate_email().replace("null", "-"));
        tv_mobile1.setText("AlterNate Phone No  : " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getAlternate_number().replace("null", "-"));
        tv_stall.setText("Stall  No  : " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getStall_number().replace("null", "-"));

        tv_website.setText("Website : " + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getWebsite().replace("null", "-"));
        if (VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getWebsite().equals("")) {
            btn_view.setVisibility(View.GONE);
        } else {
            btn_view.setVisibility(View.VISIBLE);
        }
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("http://" + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getWebsite()));
                startActivity(browserIntent);

            }
        });

        String producs = VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getProduct_group();
        producs = producs.replace(",", "\n");
        producs = producs.replace("[", "");
        producs = producs.replace("]", "");
        producs = producs.replaceAll("\"", "");
        tv_productgroup.setText("Product Group: " + producs);
        //azExhibitorListPojo.getSalutation()+" "+azExhibitorListPojo.getCoordinator_name()+"\n"+azExhibitorListPojo.getCoordinator_last_name()

        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getMobile_number()));
                startActivity(dialIntent);

            }
        });
        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailActivity = new Intent(Intent.ACTION_SEND);
                // emailActivity.setData(Uri.parse("mailto:"));
                emailActivity.setData(Uri.parse("mailto:"));
                //  emailActivity.setType("mailto");
                // emailActivity.addCategory(Intent.CATEGORY_APP_EMAIL);
                emailActivity.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                emailActivity.setPackage("com.google.android.gm");
                emailActivity.setType("text/html");
                // emailActivity.setPackage("com.microsoft.office.outlook");
                emailActivity.putExtra(Intent.EXTRA_EMAIL, new String[]{VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getEmail()});
                emailActivity.putExtra(Intent.EXTRA_SUBJECT, "enter your subject");
                emailActivity.putExtra(Intent.EXTRA_TEXT, "enter your email");
                //  emailActivity.putExtra(Intent.EXTRA_STREAM, uri);
                //emailActivity.putExtra(DocumentsContract.EXTRA_INITIAL_URI, MediaStore.Downloads.EXTERNAL_CONTENT_URI);
                startActivity(Intent.createChooser(emailActivity, "Select your Email Provider :"));
            }
        });
        btntxtmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    String phoneNo = VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getMobile_number();//The phone number you want to text
                    String sms = "";//The message you want to text to the phone

                    Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(phoneNo));
                    smsIntent.putExtra("sms_body", sms);
                    startActivity(smsIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // Log.e("CheckMyData",VisitorMeetingAdapter.exhibitormeetinglist.get(VisitorMeetingAdapter.positionindex).getAlternate_number());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), VisitorListMeeing.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), VisitorListMeeing.class);
        startActivity(i);
        finish();
    }
}