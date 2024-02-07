package com.ramanora.plastfocus.PlastFocus_Activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.ramanora.plastfocus.Platsfocus_ImageMapping.ImageSource;
import com.ramanora.plastfocus.Platsfocus_ImageMapping.PinView;
import com.ramanora.plastfocus.R;

import java.util.ArrayList;

public class ActivityImageMapping extends AppCompatActivity {


    ArrayList<String> arrayList;
    Button btn_FindLocation;
    EditText edt_MyPlotNo;
    EditText edt_DestinationPlotNo;
    PinView imageView;

    String Y_cordinate = "", X_cordinate = "", hall1, hall2, stallno1, stallno2;
    // TextView txt_CompanyName;
Button btn_location,btn_destination;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extension_pin_fragment);
        btn_location = (Button) findViewById(R.id.btn_location);
        btn_destination = (Button) findViewById(R.id.btn_destination);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  btn_FindLocation=(Button) findViewById(R.id.btn_FindLocation);
        //  edt_MyPlotNo=(EditText) findViewById(R.id.edt_MyPlotNo);
        //  edt_DestinationPlotNo=(EditText) findViewById(R.id.edt_DestinationPlotNo);
/*        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { ((ExtensionActivity) ExtensionPinFragment.this.getActivity()).next(); }
        });*/
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i=new Intent(getApplicationContext(), MapList.class);
                startActivity(i);*/

                Intent i = new Intent(getApplicationContext(), MapList.class);
                i.putExtra("ActionaBarName","MyLocation");
                startActivityForResult(i, 101);
            }
        });
        btn_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MapList.class);
                i.putExtra("ActionaBarName","MyDestination");
                startActivityForResult(i, 201);
               /* Intent i=new Intent(getApplicationContext(), MapList.class);
                startActivity(i);*/
            }
        });
        Intent intent = getIntent();

        X_cordinate = intent.getStringExtra("X_cordinate");
        Y_cordinate = intent.getStringExtra("Y_cordinate");


        imageView = (PinView) findViewById(R.id.imageView);

        imageView.setImage(ImageSource.asset("plastfocusmap.png"));
        imageView.setTag(R.drawable.pushpin_blue);
        imageView.setTag(R.drawable.destination);
        if (X_cordinate.contains("null") || X_cordinate.equals("")) {
            Toast.makeText(this, "Cordinate Not Found", Toast.LENGTH_SHORT).show();
        } else {
            imageView.setPin(new PointF(Float.parseFloat(X_cordinate), Float.parseFloat(Y_cordinate)));
        }
        //bitmpa = BitmapFactory.decodeResource(this.getResources(), R.drawable.destination);
        // imageView.setDestination(new PointF(x, y));
        // imageView.setPin(new PointF(x, y));
        //imageView.setDestination(new PointF(914, 1260));
        Log.e("X_cordinate",X_cordinate.length()+"");



      /*  if (mArrayListCompany1.size() > 0) {
            if (mArrayListCompany1.get(0).getX().equalsIgnoreCase("") && mArrayListCompany1.get(0).getY().equalsIgnoreCase("")) {
                Log.d("test", "onCreate: null");
            } else {
                float x = Float.parseFloat(mArrayListCompany1.get(0).getX() + "f");
                float y = Float.parseFloat(mArrayListCompany1.get(0).getY() + "f");
                String FromCompanyName = mArrayListCompany1.get(0).getY();
                String ToCompanyName = mArrayListCompany1.get(0).getY();
                imageView.setDestination(new PointF(x, y));

                //2960,1800


                // imageView.setPin(new PointF(x, y));

            }
        } else {

        }
        if (mArrayListCompany2.size() > 0) {
            if (mArrayListCompany2.get(0).getX().equalsIgnoreCase("") && mArrayListCompany2.get(0).getY().equalsIgnoreCase("")) {
                Log.d("test", "onCreate: null");
            } else {
                float x = Float.parseFloat(mArrayListCompany2.get(0).getX() + "f");
                float y = Float.parseFloat(mArrayListCompany2.get(0).getY() + "f");
                imageView.setPin(new PointF(x, y));
            }
        } else {

        }*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                // get String data from Intent
               String s1 = data.getStringExtra("X_cordinate");
                String  d1 = data.getStringExtra("Y_cordinate");
                String ExhibitorName = data.getStringExtra("ExhibitorName");

                if (s1.contains("null") || s1.equals("")) {
                    Toast.makeText(this, "Cordinate Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    imageView.setPin(new PointF(Float.parseFloat(s1), Float.parseFloat(d1)));
                }
              //  btn_source.setText(ExhibitorName);
                Log.e("CheckName", ExhibitorName);
                Log.e("CheckName", s1);
                Log.e("CheckName", d1);
            }
        }
        if (requestCode == 201) {
            // Activity.RESULT_OK
            if (resultCode == RESULT_OK) {
                // get String data from Intent
              String  s2 = data.getStringExtra("X_cordinate");
              String  d2 = data.getStringExtra("Y_cordinate");
                String ExhibitorName = data.getStringExtra("ExhibitorName");
                if (s2.contains("null") || s2.equals("")) {
                    Toast.makeText(this, "Cordinate Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        imageView.setDestination(new PointF(Float.parseFloat(s2), Float.parseFloat(d2)));
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
               // btn_desination.setText(ExhibitorName);
                Log.e("CheckName1", ExhibitorName);
                Log.e("CheckName1", s2);
                Log.e("CheckName1", d2);
            }
            // set text view with string


        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
              /*  Intent i = new Intent(getApplicationContext(), ActivityMainHomePage.class);
                startActivity(i);
                finish();*/
                break;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* Intent intent=new Intent(ActivityImageMapping.this, ActivityMainHomePage.class);
        startActivity(intent);
        finish();*/
    }
}
