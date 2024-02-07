package com.ramanora.plastfocus.PlastFocus_Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ramanora.plastfocus.R;

public class LoginMenuActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_visitorregistartion, btn_visitorlogin, btn_exibitorlogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginmenu);
        initializeview();
    }

    private void initializeview() {
        btn_visitorlogin = findViewById(R.id.btn_visitorlogin);
        btn_visitorregistartion = findViewById(R.id.btn_visitorregistartion);
        btn_exibitorlogin = findViewById(R.id.btn_exibitorlogin);
        btn_visitorlogin.setOnClickListener(this);
        btn_visitorregistartion.setOnClickListener(this);
        btn_exibitorlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_exibitorlogin) {
            Intent i = new Intent(getApplicationContext(), ExhibitorLogin.class);
            startActivity(i);
        }
        if (v == btn_visitorregistartion) {
            Intent i = new Intent(getApplicationContext(), RegistrationScreen.class);
            startActivity(i);
        }
        if (v == btn_visitorlogin) {
            Intent i = new Intent(getApplicationContext(), VIsitorLoginScreen.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(R.drawable.plast).setTitle("Exit")
                .setMessage("Do you want to close this PlastfocusApp ?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                    }
                }).setNegativeButton("No", null).show();
    }
}
