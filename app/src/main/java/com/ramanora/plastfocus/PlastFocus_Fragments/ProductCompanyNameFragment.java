package com.ramanora.plastfocus.PlastFocus_Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Adapter.ProductAdapter;
import com.ramanora.plastfocus.PlastFocus_Adapter.ExhibitorMainAdapter;
import com.ramanora.plastfocus.PlastFocus_Adapter.CountryCompanyAdapter;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;


import java.util.ArrayList;
import java.util.List;

public class ProductCompanyNameFragment extends Fragment {


    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    private ArrayList<PlastFocusModelClass> mArrayListCompany;
    CountryCompanyAdapter companyAdapter;
    RequestQueue mRequestQueue;
    SearchView editTextsearch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.productfilterfragment,null);

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











       // DataBaseHandler dataBaseHandler = new DataBaseHandler(getActivity());
        List<PlastFocusModelClass> mArrayCallRList = dataBaseHandler.getExhibitorDatafilterbtproduct(ProductAdapter.produtname);
        System.out.println("mArrayListCompany country :"+mArrayCallRList.size());
        ProgressDialog pd = new ProgressDialog(getActivity(), R.style.StyledDialog);
        pd.setMessage(" Data Loaded wait ..");

        pd.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        pd.setCanceledOnTouchOutside(false);
        pd.show();
        if(mArrayCallRList.size()==0)
        {
            pd.dismiss();
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
        System.out.println("mArrayListCompany country :"+ProductAdapter.produtname);
        // List<PojoHomePojo> mArrayCallRList = dataBaseHandler.getFilterProducts(ProductAdapter.produtname);
        Log.e("Checkproduct",ProductAdapter.produtname);
        /*ProductFilterAdapter azExhibitorlistAdapter = new ProductFilterAdapter(getActivity(), mArrayCallRList);
        recyclerView.setAdapter(azExhibitorlistAdapter);
        azExhibitorlistAdapter.notifyDataSetChanged();*/
        Log.e("Checkproduct",mArrayCallRList.size()+"");
        ExhibitorMainAdapter azExhibitorlistAdapter = new ExhibitorMainAdapter(getActivity(), mArrayCallRList);
        mRecyclerView.setAdapter(azExhibitorlistAdapter);
        azExhibitorlistAdapter.notifyDataSetChanged();
        pd.dismiss();
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

        editTextsearch.setQueryHint("Search by Company,Hall,City,State");

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
