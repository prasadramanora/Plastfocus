package com.ramanora.plastfocus.PlastFocus_Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramanora.plastfocus.PlastFocus_Adapter.ExhibitorMapAdapterWithDesitnation;
import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;
import com.ramanora.plastfocus.R;

import java.util.ArrayList;
import java.util.List;

public class SelectExhibitionMap extends AppCompatActivity implements ExhibitorMapAdapterWithDesitnation.OnItemClickListener {
    LinearLayoutManager manager;
    public static String filterkey = "";

    List<PlastFocusModelClass> mArrayCallRList;
    ArrayList<String> productnamelist = new ArrayList<>();
    SearchView editTextsearch;
    ExhibitorMapAdapterWithDesitnation azExhibitorlistAdapter;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosexhibitorname);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d355a")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextsearch = (SearchView) findViewById(R.id.editTextsearch);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_exhibitor_az);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String ActionaBarName = extras.getString("ActionaBarName");
            if (ActionaBarName.equals("MyLocation")) {
                getSupportActionBar().setTitle("My Location");
            } else {
                getSupportActionBar().setTitle("My Destination");
            }

        }

        getDatafromDB();

    }

    @Override
    public void onItemClick(View view, PlastFocusModelClass plastFocusModelClass, int position) {
        RelativeLayout rl_mainlayout = view.findViewById(R.id.rl_mainlayout);
        TextView mTvAzExhibitorlist = view.findViewById(R.id.tvazexhibitorlist);
        TextView tv_x_coordinate = view.findViewById(R.id.tv_x_coordinate);
        TextView tv_y_coordinate = view.findViewById(R.id.tv_y_coordinate);
        //  Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MapListWithDesination.this, "click2", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), GoToExhibiionMap.class);
                i.putExtra("X_cordinate", tv_x_coordinate.getText().toString());
                i.putExtra("Y_cordinate", tv_y_coordinate.getText().toString());
                i.putExtra("ExhibitorName", mTvAzExhibitorlist.getText().toString());

                setResult(RESULT_OK, i);
                finish();
            }
        });

    }


    public class LoadeExhibitordata extends AsyncTask<Void, Void, String> {

        // This method runs on the background thread
        @Override
        protected String doInBackground(Void... voids) {

            // hideKeyboard(getActivity());
            DataBaseHandler dataBaseHandler = new DataBaseHandler(SelectExhibitionMap.this);
            mArrayCallRList = dataBaseHandler.getExhibitorData();
            // Return the result
            return "Background task completed";
        }

        // This method runs on the UI thread after doInBackground completes
        @Override
        protected void onPostExecute(String result) {
            if (mArrayCallRList.size() > 0) {
                azExhibitorlistAdapter = new ExhibitorMapAdapterWithDesitnation(SelectExhibitionMap.this, mArrayCallRList, SelectExhibitionMap.this);
                recyclerView.setAdapter(azExhibitorlistAdapter);
                azExhibitorlistAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(SelectExhibitionMap.this, "No Data Found", Toast.LENGTH_SHORT).show();
            }


            editTextsearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    editTextsearch.setIconified(false);
                    editTextsearch.setQueryHint("Search by Comapny,City,Hall,Country");
                }
            });

            editTextsearch.setQueryHint("Search by Company,City,State,Hall,Country,Designation");

            editTextsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterkey = newText;
                    if (newText.length() == 0) {
                        filterkey = "";
                        editTextsearch.setQueryHint("");
                    }
                    if (azExhibitorlistAdapter != null)
                        azExhibitorlistAdapter.getFilter().filter(filterkey);
                    return true;
                }
            });
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

    private void getDatafromDB() {


        new LoadeExhibitordata().execute();

    }
}