package com.ramanora.plastfocus.PlastFocus_Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ramanora.plastfocus.Platsfocus_ImageMapping.ImageSource;
import com.ramanora.plastfocus.Platsfocus_ImageMapping.PinView;
import com.ramanora.plastfocus.R;

public class FindSoueceAndDestinationMap extends AppCompatActivity {
    Button btn_source, btn_desination, btn_viewmap;
    PinView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findsourceanddestinationmap);
        imageView = (PinView) findViewById(R.id.imageView);
        Intent intent = getIntent();
      String  s1 = intent.getStringExtra("s1");
        String d1 = intent.getStringExtra("d1");
        String  s2 = intent.getStringExtra("s2");
        String d2 = intent.getStringExtra("d2");
        try {

            imageView.setImage(ImageSource.asset("plastfocusmap.png"));
            imageView.setTag(R.drawable.destination);
            if(s1.equals("null") || s1.equals(""))
            {
                Toast.makeText(this, "X and Y coordinate not found Source", Toast.LENGTH_SHORT).show();
            }else {
                imageView.setPin(new PointF(Float.parseFloat(s1), Float.parseFloat(d1)));

            }

            if(s2.equals("null") || s2.equals(""))
            {
                Toast.makeText(this, "X and Y coordinate not found Desination", Toast.LENGTH_SHORT).show();
            }else {
                imageView.setDestination(new PointF(Float.parseFloat(s2)


                        , Float.parseFloat(d2)
                ));

            }
            // Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_top);

        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}