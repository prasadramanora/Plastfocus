package com.ramanora.plastfocus.PlastFocus_Activities;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.ramanora.plastfocus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityAboutEvent extends AppCompatActivity {
    /*    private JustifiedTextView myMsg;*/


    TextView txtRead;
    ProgressDialog pd;
    WebView mwebviewvirtualexhibition;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutevent_lay);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pd = new ProgressDialog(ActivityAboutEvent.this, R.style.StyledDialog);
        pd.setMessage("Page Loaded Wait ..");

        pd.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        pd.setCanceledOnTouchOutside(false);
        pd.show();

        mwebviewvirtualexhibition = (WebView) findViewById(R.id.webviewleadcon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mwebviewvirtualexhibition.setWebViewClient(new WebViewClient() {

        });
        mwebviewvirtualexhibition.loadUrl("https://www.plastfocus.org/");

        mwebviewvirtualexhibition.getSettings().setLoadWithOverviewMode(true);
        mwebviewvirtualexhibition.getSettings().setUseWideViewPort(true);
        WebSettings webSettings = mwebviewvirtualexhibition.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        mwebviewvirtualexhibition.setWebViewClient(new CustomWebViewClient());

    }

    public class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView webview, String url, Bitmap favicon) {
            webview.setVisibility(webview.INVISIBLE);
            pd.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.setVisibility(mwebviewvirtualexhibition.VISIBLE);
            pd.dismiss();
            super.onPageFinished(view, url);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), ActivityMainHomePage.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mwebviewvirtualexhibition.canGoBack()) {
                        mwebviewvirtualexhibition.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

}

