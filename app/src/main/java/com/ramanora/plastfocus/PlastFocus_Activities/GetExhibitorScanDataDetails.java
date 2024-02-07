package com.ramanora.plastfocus.PlastFocus_Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramanora.plastfocus.R;

public class GetExhibitorScanDataDetails extends Activity {
    TextView tv_name, tv_city,tv_designation, tv_compny, tv_address, tv_mobile, tv_email, tv_produts;
    ImageView btnemail, btntxtmsg, btnWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getexhibitordetails);
       // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        tv_name = findViewById(R.id.tv_name);
        tv_city= findViewById(R.id.tv_city);
        btnemail = findViewById(R.id.btnemail);
        btntxtmsg = findViewById(R.id.btntxtmsg);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);
        tv_designation = findViewById(R.id.tv_designation);
        tv_compny = findViewById(R.id.tv_comapny);
        tv_address = findViewById(R.id.tv_address);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_email = findViewById(R.id.tv_email);
        tv_produts = findViewById(R.id.tv_produts);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String Name = extras.getString("Name");
            String Company = extras.getString("Company");
            String Designation = extras.getString("Designation");
            String Address = extras.getString("Address");
            String Mobile = extras.getString("Mobile");
            String Email = extras.getString("Email");
            String city = extras.getString("city");
            String state = extras.getString("state");
            String country = extras.getString("country");
            String zipcode = extras.getString("zipcode");
            String Products = extras.getString("Products");

            tv_name.setText(Name);
            tv_designation.setText(Designation);
            tv_compny.setText(Company);
            if(Address.contains("null"))
            {
                Address="-";
            }
            if(city.contains("null"))
            {
                city="-";
            }
            if(state.contains("null"))
            {
                state="-";
            }
            if(country.contains("null"))
            {
                country="-";
            }
            if(zipcode.contains("null"))
            {
                zipcode="-";
            }
            tv_address.setText(Address);
            tv_mobile.setText(Mobile);
            tv_email.setText(Email);
            tv_name.setText(Name);
            tv_city.setText("City-"+city+"\n"+"State-"+state+"\n"+"Country-"+country+"\n"+"Zip-code-"+zipcode);
            String producs = Products;
            producs = producs.replace(",", "\n");
            producs = producs.replace("[", "");
            producs = producs.replace("]", "");
            producs = producs.replaceAll("\"", "");
            String[] separated = producs.split("\"");
            for (int i = 0; i < separated.length; i++) {
                tv_produts.setText(separated[i]);
            }

            // String NAme=data.spli
            //The key argument here must match that used in the other activity
        }


        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +  tv_mobile.getText().toString()));
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
                emailActivity.putExtra(Intent.EXTRA_EMAIL, new String[]{tv_email.getText().toString()});
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
                    String phoneNo = "";//The phone number you want to text
                    String sms= "";//The message you want to text to the phone

                    Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", tv_mobile.getText().toString(), null));
                    smsIntent.putExtra("sms_body",sms);
                    startActivity(smsIntent);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        // Log.e("CheckMyData",AZExhibitorlistAdapter.mFilteredList.get(AZExhibitorlistAdapter.positionindex).getAlternate_number());
    }

}
