package com.ramanora.plastfocus.PlastFocus_Activities;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ramanora.plastfocus.R;

public class ActivitySeminar extends Activity {
    TextView tv_seminar4, tv_seminar3, tv_seminar2, tv_seminar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seminar);
        tv_seminar1 = findViewById(R.id.tv_seminar1);
        tv_seminar2 = findViewById(R.id.tv_seminar2);
        tv_seminar3 = findViewById(R.id.tv_seminar3);
        tv_seminar4 = findViewById(R.id.tv_seminar4);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        tv_seminar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeminarClass1.class);
                i.putExtra("Seminar", "seminar1");
                startActivity(i);


            }
        });
        tv_seminar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeminarClass1.class);
                i.putExtra("Seminar", "seminar2");
                startActivity(i);


            }
        });
        tv_seminar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeminarClass1.class);
                i.putExtra("Seminar", "seminar3");
                startActivity(i);

            }
        });
        tv_seminar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeminarClass1.class);
                i.putExtra("Seminar", "seminar4");
                startActivity(i);

            }
        });


    }
}
