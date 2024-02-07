package com.ramanora.plastfocus.PlastFocus_Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramanora.plastfocus.Platsfocus_ImageMapping.ActivityImageMappingSingle;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Adapter.ExhibitorMainAdapter;
import com.ramanora.plastfocus.PlastFocus_Utils.AppStatus;

import org.w3c.dom.Text;

public class GetExhibitorDeatilsClass extends AppCompatActivity {
    /*    private JustifiedTextView myMsg;*/

    ImageView iv_emailsend, btntxtmsg, iv_calluser, btn_bookmeeting, iv_mapexhibitor;
    TextView tv_website, tv_productgroup, tv_stall, tv_cname, tv_email1, tv_mobile1, tv_companyprofile, tv_viewmap, tv_address, tv_state, tv_designation, tv_pincode, tv_Name, tv_email, tv_city, tv_country, tv_mobile, tv_hall;
    Button btn_view;
    public static String MyPREFERENCES = "myprefe";
    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        sh = getSharedPreferences(MyPREFERENCES, 0);
        editor = sh.edit();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String CheckLogin = sh.getString("CheckLogin", null);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (CheckLogin.equals("Visitor")) {
            setContentView(R.layout.getdeatils);
        } else {
            setContentView(R.layout.getdeatilsexhibitors);
        }

        btn_bookmeeting = findViewById(R.id.btn_bookmeeting);
        btn_bookmeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(GetExhibitorDeatilsClass.this).isOnline()) {
                    Intent i = new Intent(getApplicationContext(), BookMeeting.class);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(GetExhibitorDeatilsClass.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_view = findViewById(R.id.btn_view);
        tv_website = findViewById(R.id.tv_website);
        tv_productgroup = findViewById(R.id.tv_productgroup);
        tv_email1 = findViewById(R.id.tv_email1);
        tv_stall = findViewById(R.id.tv_stall);
        tv_companyprofile = findViewById(R.id.tv_companyprofile);
        iv_emailsend = findViewById(R.id.iv_emailsend);
        btntxtmsg = findViewById(R.id.btntxtmsg);
        iv_calluser = findViewById(R.id.iv_calluser);
        tv_mobile1 = findViewById(R.id.tv_mobile1);
        iv_mapexhibitor = findViewById(R.id.iv_mapexhibitor);
        tv_hall = findViewById(R.id.tv_hall);
        tv_cname = findViewById(R.id.tv_cname);
        tv_email = findViewById(R.id.tv_email);
        tv_Name = findViewById(R.id.tv_Name);
        tv_address = findViewById(R.id.tv_address);
        tv_state = findViewById(R.id.tv_state);
        tv_designation = findViewById(R.id.tv_designation);
        tv_pincode = findViewById(R.id.tv_pincode);
        tv_city = findViewById(R.id.tv_city);
        tv_viewmap = findViewById(R.id.tv_viewmap);
        tv_country = findViewById(R.id.tv_country);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_cname.setText(ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getCompany_name());
        tv_address.setText("Company Address:" + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getAddress().replace("null", "-"));
        tv_state.setText(" State : " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getState().replace("null", "-"));
        tv_designation.setText("Designation : " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getCoordinator_designation().replace("null", "-"));
        tv_pincode.setText("Pincode : " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getPincode().replace("null", "-"));
        tv_Name.setText("Full Name : " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getSalutation().replace("null", "-") + " " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getCoordinator_name().replace("null", "-") + " " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getCoordinator_last_name().replace("null", "-"));
        tv_email.setText("Email Id : " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getEmail().replace("null", "-"));
        tv_city.setText("City : " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getCity().replace("null", "-"));
        tv_mobile.setText("Mobile Number No: " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getMobile_number().replace("null", "-"));
        tv_country.setText("Country : " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getCountry().replace("null", "-"));
        iv_mapexhibitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityImageMapping.class);
                i.putExtra("X_cordinate", ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getCord_x());
                i.putExtra("Y_cordinate", ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getCord_y());

                startActivity(i);

            }
        });
        tv_viewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getAddress().replace("null", "-") + "," + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getCity()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        tv_hall.setText("Hall : " + " " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getHall());


        tv_companyprofile.setText("Company Profile : " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getCompany_profile().replace("null", "-"));

        tv_email1.setText("Alternate Email  : " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getAlternate_email().replace("null", "-"));
        tv_mobile1.setText("Alternate Mobile No  : " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getAlternate_number().replace("null", "-"));
        tv_stall.setText("Stall  No  : " + " " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getStall_number().replace("null", "-"));

        tv_website.setText("Website : " + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getWebsite().replace("null", "-"));
        if (ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getWebsite().equals("")) {
            btn_view.setVisibility(View.GONE);
        } else {
            btn_view.setVisibility(View.VISIBLE);
        }
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("http://" + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getWebsite()));
                startActivity(browserIntent);

            }
        });
        String producs = ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getProduct_group();
        producs = producs.replace(",", "\n");
        producs = producs.replace("[", "");
        producs = producs.replace("]", "");
        producs = producs.replaceAll("\"", "");
        tv_productgroup.setText("Product Group : " + producs);
        //azExhibitorListPojo.getSalutation()+" "+azExhibitorListPojo.getCoordinator_name()+"\n"+azExhibitorListPojo.getCoordinator_last_name()
        ;
        iv_calluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(GetExhibitorDeatilsClass.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.choosemobilenumber);
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView tv_mobile1 = dialog.findViewById(R.id.tv_mobile1);
                TextView tv_mobile2 = dialog.findViewById(R.id.tv_mobile2);

                View view2 = dialog.findViewById(R.id.view2);
                View view1 = dialog.findViewById(R.id.view1);


                tv_mobile1.setText(ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getMobile_number());
                tv_mobile2.setText(ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getAlternate_number());
                if (tv_mobile2.length() == 1) {
                    tv_mobile2.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                }

                tv_mobile1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getMobile_number()));
                            startActivity(dialIntent);


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                tv_mobile2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (tv_mobile2.length() == 1) {
                                tv_mobile2.setVisibility(View.GONE);
                                Toast.makeText(GetExhibitorDeatilsClass.this, "Mobile Number Not Valid", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getAlternate_number()));
                                startActivity(dialIntent);
                            }


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        iv_emailsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(GetExhibitorDeatilsClass.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.chooseemaildialog);
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView tv_email1 = dialog.findViewById(R.id.tv_email1);
                TextView tv_email2 = dialog.findViewById(R.id.tv_email2);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel);

                tv_email1.setText(ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getEmail());

                tv_email2.setText(ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getAlternate_email());

                View view2 = dialog.findViewById(R.id.view2);
                View view1 = dialog.findViewById(R.id.view1);
                if (tv_email2.getText().toString().length() == 0) {
                    view2.setVisibility(View.GONE);
                    tv_email2.setVisibility(View.GONE);
                }
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                tv_email1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent emailActivity = new Intent(Intent.ACTION_SEND);

                        emailActivity.setData(Uri.parse("mailto:"));

                        emailActivity.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        emailActivity.setPackage("com.google.android.gm");
                        emailActivity.setType("text/html");

                        emailActivity.putExtra(Intent.EXTRA_EMAIL, new String[]{ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getEmail()});
                        emailActivity.putExtra(Intent.EXTRA_SUBJECT, "enter your subject");
                        emailActivity.putExtra(Intent.EXTRA_TEXT, "enter your email");

                        startActivity(Intent.createChooser(emailActivity, "Select your Email Provider :"));
                    }
                });

                tv_email2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tv_email2.length() == 1) {
                            tv_email2.setVisibility(View.GONE);
                            // Toast.makeText(GetExhibitorDeatilsClass.this, "Email Address Not Valid", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent emailActivity = new Intent(Intent.ACTION_SEND);

                            emailActivity.setData(Uri.parse("mailto:"));

                            emailActivity.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            emailActivity.setPackage("com.google.android.gm");
                            emailActivity.setType("text/html");

                            emailActivity.putExtra(Intent.EXTRA_EMAIL, new String[]{ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getAlternate_email()});
                            emailActivity.putExtra(Intent.EXTRA_SUBJECT, "enter your subject");
                            emailActivity.putExtra(Intent.EXTRA_TEXT, "enter your email");

                            startActivity(Intent.createChooser(emailActivity, "Select your Email Provider :"));
                        }

                    }
                });


            }
        });
        btntxtmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(GetExhibitorDeatilsClass.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.choosemobilenumber);
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView tv_mobile1 = dialog.findViewById(R.id.tv_mobile1);
                TextView tv_mobile2 = dialog.findViewById(R.id.tv_mobile2);
                View view2 = dialog.findViewById(R.id.view2);
                View view1 = dialog.findViewById(R.id.view1);
                tv_mobile1.setText(ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getMobile_number());
                tv_mobile2.setText(ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getAlternate_number());
                if (tv_mobile2.length() == 1) {
                    tv_mobile2.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                }
                tv_mobile1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String phoneNo = "";//The phone number you want to text
                            String sms = "";//The message you want to text to the phone

                            Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getMobile_number(), null));
                            smsIntent.putExtra("sms_body", sms);
                            startActivity(smsIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                tv_mobile2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (tv_mobile2.length() == 1) {
                                tv_mobile2.setVisibility(View.GONE);
                                Toast.makeText(GetExhibitorDeatilsClass.this, "Mobile Number Not Valid", Toast.LENGTH_SHORT).show();
                            } else {
                                String phoneNo = "";//The phone number you want to text
                                String sms = "";//The message you want to text to the phone

                                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", ExhibitorMainAdapter.exhibitorListDeatils.get(ExhibitorMainAdapter.positionindex).getAlternate_number(), null));
                                smsIntent.putExtra("sms_body", sms);
                                startActivity(smsIntent);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });
        // Log.e("CheckMyData",AZExhibitorlistAdapter.exhibitorListDeatils.get(AZExhibitorlistAdapter.positionindex).getAlternate_number());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), ActivityExhibitiorlistTab.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }
}
