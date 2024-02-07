package com.ramanora.plastfocus.PlastFocus_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Activities.ActivityExhibitiorlistTab;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;
import com.ramanora.plastfocus.PlastFocus_Fragments.HallGridViewFragment;

import java.util.ArrayList;

/**
 * Created by admin on 10/14/2017.
 */

public class HallCompanyAdapter extends RecyclerView.Adapter<HallCompanyAdapter.DataHolder> implements Filterable {

    private Context mContext;
    private ArrayList<PlastFocusModelClass> mArrayListCompany;
    private ArrayList<PlastFocusModelClass> mFilteredList;
    FragmentManager mFragment;
    String hall;
    String stallno;


    public HallCompanyAdapter(Context mContext, ArrayList<PlastFocusModelClass> mArrayListCompany) {
        this.mContext = mContext;
        this.mArrayListCompany = mArrayListCompany;
        this.mFilteredList = mArrayListCompany;
        this.hall = hall;

    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.hall_company_adapter,parent,false);

        DataHolder dataHolder=new DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(final DataHolder holder, @SuppressLint("RecyclerView") final int position) {

        final PlastFocusModelClass companyPojo = mFilteredList.get(position);

        holder.mTvCompanyName.setText(companyPojo.getCompany_name());
        holder.mhallno.setText(companyPojo.getHall()+ " -  Stall No : " + companyPojo.getStall_number());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlastFocusModelClass companyPojo = mFilteredList.get(position);
                HallGridViewFragment exhibitorListGridViewFragment=new HallGridViewFragment();
                mFragment=((ActivityExhibitiorlistTab)mContext).getSupportFragmentManager();
                mFragment.beginTransaction().add(R.id.activityexhibitorlist,
                        exhibitorListGridViewFragment).addToBackStack("").commit();

                Bundle bundle = new Bundle();
                bundle.putSerializable("AZData",companyPojo);

                bundle.putString("hallname",hall);


                exhibitorListGridViewFragment.setArguments(bundle);
                hideKeyboard(mContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayListCompany;
                } else {

                    ArrayList<PlastFocusModelClass> filteredList = new ArrayList<>();

                    for (PlastFocusModelClass hallExhibitorListPojo : mArrayListCompany) {

                        if (hallExhibitorListPojo.getCompany_name().toLowerCase().contains(charString) ||
                                hallExhibitorListPojo.getCompany_name().toUpperCase().contains(charString)) {

                            filteredList.add(hallExhibitorListPojo);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<PlastFocusModelClass>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public class DataHolder extends RecyclerView.ViewHolder {

        private TextView mTvCompanyName, mhallno;

        public DataHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            mTvCompanyName = (TextView) itemView.findViewById(R.id.tvcompanyname);
            mhallno= (TextView) itemView.findViewById(R.id.tvhallname1);


        }
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((ActivityExhibitiorlistTab) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}