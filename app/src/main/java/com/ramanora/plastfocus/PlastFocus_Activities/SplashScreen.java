package com.ramanora.plastfocus.PlastFocus_Activities;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;


import com.ramanora.plastfocus.R;


public class SplashScreen extends Activity {
    public static String MyPREFERENCES = "myprefe";

    SharedPreferences sharedpreferences;
    public static String str_login_test;
    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                // on below line we are
                // creating a new intent
                sh = getSharedPreferences(MyPREFERENCES, 0);
                //editor = sh.edit();

                str_login_test = sh.getString("loginTest1", null);
                Intent intent;
                if (str_login_test != null
                        && !str_login_test.toString().trim().equals("")) {

                    intent = new Intent(SplashScreen.this, ActivityMainHomePage.class);

                } else {

                    intent = new Intent(SplashScreen.this, LoginMenuActivity.class);

                }

                startActivity(intent);
                finish();

            }
        }, 2000);
    }
}
