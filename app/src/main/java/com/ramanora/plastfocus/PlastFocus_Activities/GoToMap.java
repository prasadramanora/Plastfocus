package com.ramanora.plastfocus.PlastFocus_Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ramanora.plastfocus.Platsfocus_ImageMapping.ImageSource;
import com.ramanora.plastfocus.Platsfocus_ImageMapping.PinView;
import com.ramanora.plastfocus.R;

public class GoToMap extends AppCompatActivity {
    Button btn_source, btn_desination, btn_viewmap;
    String s1="", d1="", s2="", d2="";
    PinView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gotomap);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_source = findViewById(R.id.btn_source);
        imageView = (PinView) findViewById(R.id.imageView);
        try {

            imageView.setImage(ImageSource.asset("plastfocusmap.png"));
            imageView.setTag(R.drawable.destination);

            // Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_top);
          /*  imageView.setDestination(new PointF(914


                    , 1260
            ));*/
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        btn_desination = findViewById(R.id.btn_desination);
        btn_viewmap = findViewById(R.id.btn_viewmap);
        btn_viewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_source.getText().toString().equals("Select Source"))
                {
                    Toast.makeText(GoToMap.this, "Select Source Exhibitor Name", Toast.LENGTH_SHORT).show();

                }else if(btn_desination.getText().toString().equals("Select Destination"))
                {
                    Toast.makeText(GoToMap.this, "Select Destination Exhibitor Name", Toast.LENGTH_SHORT).show();
                }else {
                    Intent i = new Intent(getApplicationContext(), FindSoueceAndDestinationMap.class);
                    i.putExtra("s1", s1);
                    i.putExtra("d1", d1);
                    i.putExtra("s2", s2);
                    i.putExtra("d2", d2);
                    startActivity(i);
                }

            }
        });
        btn_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MapListWithDesination.class);
                i.putExtra("ActionaBarName","MyLocation");
                startActivityForResult(i, 101);
            }
        });
        btn_desination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MapListWithDesination.class);
                i.putExtra("ActionaBarName","MyDestination");
                startActivityForResult(i, 201);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                // get String data from Intent
                s1 = data.getStringExtra("X_cordinate");
                d1 = data.getStringExtra("Y_cordinate");
                String ExhibitorName = data.getStringExtra("ExhibitorName");

                if (s1.contains("null") || s1.equals("")) {
                    Toast.makeText(this, "Cordinate Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    imageView.setPin(new PointF(Float.parseFloat(s1)


                            , Float.parseFloat(d1)
                    ));
                }
               // btn_source.setText(ExhibitorName);

                Log.e("CheckName", ExhibitorName);
                Log.e("CheckName", s1);
                Log.e("CheckName", d1);
            }
        }
        if (requestCode == 201) {
            // Activity.RESULT_OK
            if (resultCode == RESULT_OK) {
                // get String data from Intent
                s2 = data.getStringExtra("X_cordinate");
                d2 = data.getStringExtra("Y_cordinate");
                String ExhibitorName = data.getStringExtra("ExhibitorName");

                if (s2.contains("null") || s2.equals("")) {
                    Toast.makeText(this, "Cordinate Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    imageView.setDestination(new PointF(Float.parseFloat(s2)


                            , Float.parseFloat(d2)
                    ));
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
        finish();
       /* Intent i = new Intent(getApplicationContext(), ActivityMainHomePage.class);
        startActivity(i);
        finish();*/
    }
}
