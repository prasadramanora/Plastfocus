package com.ramanora.plastfocus.PlastFocus_Fragments;

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
import com.ramanora.plastfocus.PlastFocus_Adapter.HallMainAdapter;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HallMainFragment extends Fragment {


    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    LinearLayoutManager manager;
    HallMainAdapter hallExhbitorlistAdapter;
    ArrayList<PlastFocusModelClass> hallList;

    SearchView editTextsearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.hall_exhibitorlist_lay_hall_exhibitor, null);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_exhibitor_hall);
        relativeLayout = view.findViewById(R.id.hallfragment);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        editTextsearch = (SearchView) view.findViewById(R.id.editTextsearch);

        editTextsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextsearch.setQueryHint("Search by Hall");
                editTextsearch.setIconified(false);
            }
        });

        editTextsearch.setQueryHint("Search by Hall");


        getDatafromDB();

        editTextsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (hallExhbitorlistAdapter != null)
                    hallExhbitorlistAdapter.getFilter().filter(newText);
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
                // mExhibitorList1 = databaseAccess.getExhibitorlist();
                hallList = dataBaseHandler.getHalllist();
                // Background task
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (hallList.size() > 0) {
                            hallExhbitorlistAdapter = new HallMainAdapter(getActivity(), hallList);
                            recyclerView.setAdapter(hallExhbitorlistAdapter);
                            hallExhbitorlistAdapter.notifyDataSetChanged();
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
