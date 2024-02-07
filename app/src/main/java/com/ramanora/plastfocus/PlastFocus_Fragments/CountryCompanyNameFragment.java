package com.ramanora.plastfocus.PlastFocus_Fragments;


import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Adapter.ExhibitorMainAdapter;
import com.ramanora.plastfocus.PlastFocus_Adapter.CountryCompanyAdapter;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryCompanyNameFragment extends Fragment {


    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    private ArrayList<PlastFocusModelClass> mArrayListCompany;
    CountryCompanyAdapter companyAdapter;
    RequestQueue mRequestQueue;
    SearchView editTextsearch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.country_fragment_company_name,null);

        mRecyclerView= (RecyclerView) view.findViewById(R.id.recycler_exhibitor_company);
        mLinearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayListCompany = new ArrayList<>();

        Bundle bundle=getArguments();

        String country = bundle.getString("key");

        System.out.println(" country name :"+country);


        DataBaseHandler dataBaseHandler = new DataBaseHandler(getActivity());
//hall comp
        mArrayListCompany= (ArrayList<PlastFocusModelClass>) dataBaseHandler.getFilterExhibitorDatabycountry(country);




        System.out.println("mArrayListCompany country :"+mArrayListCompany.size());


//c
       /* companyAdapter=new CountryCompanyAdapter(getContext(),mArrayListCompany);
        mRecyclerView.setAdapter(companyAdapter);*/

        ExhibitorMainAdapter azExhibitorlistAdapter = new ExhibitorMainAdapter(getActivity(), mArrayListCompany);
        mRecyclerView.setAdapter(azExhibitorlistAdapter);
        azExhibitorlistAdapter.notifyDataSetChanged();

        mRequestQueue= Volley.newRequestQueue(getContext());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editTextsearch = (SearchView) view.findViewById(R.id.editTextsearch);
        editTextsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextsearch.setIconified(false);
            }
        });

        editTextsearch.setQueryHint("Search...");

        editTextsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText){
                if (azExhibitorlistAdapter != null) azExhibitorlistAdapter.getFilter().filter(newText);
                return true;
            }});



        return view;


    }

}
