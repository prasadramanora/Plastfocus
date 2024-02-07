package com.ramanora.plastfocus.PlastFocus_Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Adapter.CountryMainAdapter;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryMainFragment extends Fragment {

    SearchView editTextsearch;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    CountryMainAdapter countryExhbitorlistAdapter;

    public static ArrayList<PlastFocusModelClass> countryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.country_exhibitorlist_lay_country_exhibitor, null);

        editTextsearch = (SearchView) view.findViewById(R.id.editTextsearch);



        editTextsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextsearch.setIconified(false);
                editTextsearch.setQueryHint("Search by Country");
            }
        });

        editTextsearch.setQueryHint("Search by Country");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_exhibitor_country);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        getDatafromDB();


        editTextsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (countryExhbitorlistAdapter != null)
                    countryExhbitorlistAdapter.getFilter().filter(newText);
                return true;
            }
        });



        return view;
    }

    private void getDatafromDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataBaseHandler dataBaseHandler = new DataBaseHandler(getActivity());
                countryList = dataBaseHandler.getCountrylist();
                // Background task
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (countryList.size() > 0) {
                            countryExhbitorlistAdapter = new CountryMainAdapter(getActivity(), countryList);
                            recyclerView.setAdapter(countryExhbitorlistAdapter);
                            countryExhbitorlistAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                        // Update UI
                    }
                });
            }
        }).start();




    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(isRemoving()){
            getActivity().finish();
            // onBackPressed()
        }
    }

}
