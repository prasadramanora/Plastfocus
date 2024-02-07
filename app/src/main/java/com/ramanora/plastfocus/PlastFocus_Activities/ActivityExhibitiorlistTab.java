package com.ramanora.plastfocus.PlastFocus_Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;


import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;

import com.ramanora.plastfocus.PlastFocus_Fragments.AtoZExhibitorListFragment;
import com.ramanora.plastfocus.PlastFocus_Fragments.CountryMainFragment;
import com.ramanora.plastfocus.PlastFocus_Fragments.HallMainFragment;
import com.ramanora.plastfocus.PlastFocus_Fragments.ProductMainFragment;
import com.ramanora.plastfocus.PlastFocus_Utils.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ActivityExhibitiorlistTab extends AppCompatActivity {
    private TabLayout ExhibitorTab;
    private ViewPager BannerViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibitiorlist_activity);
        BannerViewPager = (ViewPager) findViewById(R.id.viewpagerexhibitorlist);
        setupViewPager(BannerViewPager);
        ExhibitorTab = (TabLayout) findViewById(R.id.tabs);
        ExhibitorTab.setSelectedTabIndicatorColor(Color.parseColor("#2d355a"));
        ExhibitorTab.setSelectedTabIndicatorHeight((int) (4 * getResources().getDisplayMetrics().density));
        ExhibitorTab.setTabTextColors(Color.parseColor("#3b4148"), Color.parseColor("#3b4148"));
        ExhibitorTab.setupWithViewPager(BannerViewPager);
        ExhibitorTab.setOnTabSelectedListener(onTabSelectedListener(BannerViewPager));
        getNotificationPermission();

    }

    public void getNotificationPermission() {
        try {
            if (Build.VERSION.SDK_INT > 32) {


                if (ActivityCompat.checkSelfPermission(ActivityExhibitiorlistTab.this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    Log.e("grantpermission", "Yes");
                } else {
                    ActivityCompat.requestPermissions(ActivityExhibitiorlistTab.this, new
                            String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 201);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menurefresh, menu);
        return true;
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


    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager BannerViewPager) {

        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                BannerViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // finish();
    }

    private void setupViewPager(ViewPager BannerViewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AtoZExhibitorListFragment(), "A to Z");
        adapter.addFragment(new HallMainFragment(), "Hall");
        adapter.addFragment(new CountryMainFragment(), "Country");
        adapter.addFragment(new ProductMainFragment(), "Products");
        BannerViewPager.setAdapter(adapter);
        BannerViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(ExhibitorTab));
        onTabSelectedListener(BannerViewPager);


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
