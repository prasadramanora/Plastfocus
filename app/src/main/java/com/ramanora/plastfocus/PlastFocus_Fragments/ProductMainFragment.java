package com.ramanora.plastfocus.PlastFocus_Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Adapter.ProductAdapter;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;


import java.util.ArrayList;

public class ProductMainFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    SearchView editTextsearch;
    ProductAdapter ProductNameAdapter;

    public static ArrayList<PlastFocusModelClass> productList;

    RelativeLayout relativeLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.productfragment, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_exhibitor_az);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        editTextsearch = (SearchView) view.findViewById(R.id.editTextsearch);
        relativeLayout = view.findViewById(R.id.fragmentaz);
        editTextsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextsearch.setIconified(false);
            }
        });

        editTextsearch.setQueryHint("Search by ProductName");

        editTextsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (ProductNameAdapter != null)
                    ProductNameAdapter.getFilter().filter(newText);
                return true;
            }
        });
        // mExhibitorList = new ArrayList<>();
        getDatafromDB();
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getDatafromDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Background task
                DataBaseHandler dataBaseHandler = new DataBaseHandler(getActivity());
                productList = new ArrayList<>();
                productList = dataBaseHandler.ProductNamelIst();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (productList.size() > 0) {
                            recyclerView.setHasFixedSize(true);
                            manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(manager);
                            ProductNameAdapter = new ProductAdapter(getActivity(), productList);
                            recyclerView.setAdapter(ProductNameAdapter);
                            ProductNameAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getActivity(), "Data Not Found", Toast.LENGTH_SHORT).show();
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
