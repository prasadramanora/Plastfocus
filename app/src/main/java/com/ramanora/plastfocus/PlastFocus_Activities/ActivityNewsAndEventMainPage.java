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


import androidx.appcompat.app.AppCompatActivity;

import com.ramanora.plastfocus.R;


public class ActivityNewsAndEventMainPage extends AppCompatActivity {
    ProgressDialog pd;

    WebView mwebviewvirtualexhibition;

    //private ProgressBar spinner;
    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_news_and_event_mainpage);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pd = new ProgressDialog(ActivityNewsAndEventMainPage.this, R.style.StyledDialog);
        pd.setMessage("Page Loaded Wait ..");

        pd.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        pd.setCanceledOnTouchOutside(false);
        pd.show();
        // spinner = (ProgressBar) findViewById(R.id.progressBar1);
        mwebviewvirtualexhibition = (WebView) findViewById(R.id.webviewleadcon);

        mwebviewvirtualexhibition.setWebViewClient(new WebViewClient() {

        });
        mwebviewvirtualexhibition.loadUrl("https://www.instagram.com/plastfocusexhibition?fbclid=IwAR1E8I920mKfFTvD192DpeM5X9UYPsBpCBVB3wBn4MgjgOFPOvB4_fOmqo4");

        mwebviewvirtualexhibition.getSettings().setLoadWithOverviewMode(true);
        mwebviewvirtualexhibition.getSettings().setUseWideViewPort(true);
        WebSettings webSettings = mwebviewvirtualexhibition.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mwebviewvirtualexhibition.setWebViewClient(new CustomWebViewClient());

    }

    public class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView webview, String url, Bitmap favicon) {
            pd.show();
            webview.setVisibility(webview.INVISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            // spinner.setVisibility(View.GONE);
            pd.dismiss();
            view.setVisibility(mwebviewvirtualexhibition.VISIBLE);
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
