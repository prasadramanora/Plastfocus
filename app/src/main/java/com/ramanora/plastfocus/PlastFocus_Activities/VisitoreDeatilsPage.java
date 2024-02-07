package com.ramanora.plastfocus.PlastFocus_Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Adapter.ExhibitorMeetingAdapter;


public class VisitoreDeatilsPage extends Activity {

    ImageView btnemail, btntxtmsg, btnWhatsapp;
    TextView tv_name, tv_address, tv_address1, tv_meetingtime, tv_city, tv_product, tv_state, tv_mobile, tv_country, tv_email, tv_company, tv_pincode, tv_designation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitormeetinginfo);
        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        btnemail = findViewById(R.id.btnemail);
        btntxtmsg = findViewById(R.id.btntxtmsg);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);
        tv_name = findViewById(R.id.tv_Name);
        tv_meetingtime = findViewById(R.id.tv_meetingtime);
        tv_address = findViewById(R.id.tv_address);
        tv_address1 = findViewById(R.id.tv_address1);
        tv_city = findViewById(R.id.tv_city);
        tv_product = findViewById(R.id.tv_product);
        tv_state = findViewById(R.id.tv_state);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_country = findViewById(R.id.tv_country);
        tv_company = findViewById(R.id.tv_company);
        tv_pincode = findViewById(R.id.tv_pincode);
        tv_designation = findViewById(R.id.tv_designation);
        tv_email = findViewById(R.id.tv_email);
        tv_meetingtime.setText(ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getMeeting_time() + " From " + ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getMeeting_date());
        tv_name.setText(ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getCoordinator_name());
        tv_designation.setText(ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getCoordinator_designation());
        tv_company.setText(ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getCompany_name());
        tv_address.setText(ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getAddress());
        tv_address1.setText(ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getCity() + "," + ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getState() + "," + ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getCountry() + "," + ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getPincode());
        tv_email.setText(ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getEmail());

        tv_mobile.setText(ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getMobile_number());
        String producs = ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getProduct_group();

        producs = producs.replace(",", "\n");
        producs = producs.replace("[", "");
        producs = producs.replace("]", "");
        producs = producs.replaceAll("\"", "");
        tv_product.setText(producs.toString());
        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getMobile_number()));
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
                emailActivity.putExtra(Intent.EXTRA_EMAIL, new String[]{ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getEmail()});
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

                    Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", ExhibitorMeetingAdapter.exhibitormeetinglist.get(ExhibitorMeetingAdapter.positionindex).getMobile_number(), null));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), ExhibitorListMeeting.class);
        startActivity(i);
        finish();
    }
}
