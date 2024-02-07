package com.ramanora.plastfocus.Platsfocus_ImageMapping;

import android.content.Intent;


import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ramanora.plastfocus.PlastFocus_Activities.ActivityMainHomePage;
import com.ramanora.plastfocus.PlastFocus_Activities.GoToMap;
import com.ramanora.plastfocus.PlastFocus_Activities.MapList;
import com.ramanora.plastfocus.R;


import java.util.ArrayList;

/**
 * Created by amolrokade on 17/11/17.
 */

public class ActivityImageMappingSingle extends AppCompatActivity {


    PinView imageView;


    Button btn_location, btn_finddestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleimage_mapping);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = (PinView) findViewById(R.id.imageView);
        btn_location = (Button) findViewById(R.id.btn_location);
        btn_finddestination = (Button) findViewById(R.id.btn_finddestination);

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MapList.class);
                startActivity(i);
            }
        });
        btn_finddestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MapList.class);
                startActivity(i);
            }
        });
        try {
            imageView.setImage(ImageSource.asset("plastfocusmap.png"));
            imageView.setTag(R.drawable.destination);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}

