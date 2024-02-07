package com.ramanora.plastfocus.PlastFocus_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
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
import com.ramanora.plastfocus.PlastFocus_Fragments.HallCompanyNameFragment;

import java.util.ArrayList;

/**
 * Created by Owner on 09/10/2017.
 */

public class HallMainAdapter extends RecyclerView.Adapter<HallMainAdapter.DataHolder> implements Filterable {

    private Context mContext;
    private ArrayList<PlastFocusModelClass> mArrayListAZ;
    private ArrayList<PlastFocusModelClass> mArrayListAZ1;
    private ArrayList<PlastFocusModelClass> mFilteredList;
    FragmentManager mFragment;

    public HallMainAdapter(Context mContext, ArrayList<PlastFocusModelClass> mArrayListAZ) {
        this.mContext = mContext;
        this.mArrayListAZ = mArrayListAZ;
        this.mFilteredList = mArrayListAZ;


    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.hall_exhibitor_hall_adapter, parent, false);
        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(final DataHolder holder, @SuppressLint("RecyclerView") final int position) {

        PlastFocusModelClass azExhibitorListPojo = mFilteredList.get(position);
        if (azExhibitorListPojo.getHall().equals("null")) {
            holder.mhallno.setVisibility(View.GONE);
        } else {
            holder.mhallno.setVisibility(View.VISIBLE);
            holder.mhallno.setText(azExhibitorListPojo.getHall());
        }


        Log.e("mallNoCheck", holder.mhallno.getText().toString().length() + "");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //AZExhibitorListPojo azExhibitorListPojo = mArrayListAZ1.get(position);

                PlastFocusModelClass azExhibitorListPojo = mFilteredList.get(position);


                Bundle bundle = new Bundle();

                bundle.putString("key", azExhibitorListPojo.getHall());


                HallCompanyNameFragment companyNameFragment = new HallCompanyNameFragment();
                companyNameFragment.setArguments(bundle);
                FragmentManager mFragment;
                mFragment = ((ActivityExhibitiorlistTab) mContext).getSupportFragmentManager();
                mFragment.beginTransaction().add(R.id.activityexhibitorlist, companyNameFragment).addToBackStack("").commit();

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

                    mFilteredList = mArrayListAZ;
                } else {

                    ArrayList<PlastFocusModelClass> filteredList = new ArrayList<>();

                    for (PlastFocusModelClass azExhibitorListPojo : mArrayListAZ) {

                        if (azExhibitorListPojo.getHall().toLowerCase().contains(charString)
                                || azExhibitorListPojo.getHall().toUpperCase().contains(charString)) {

                            filteredList.add(azExhibitorListPojo);
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

        TextView mTvAzExhibitorlist, mhallno;

        public DataHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            // mTvAzExhibitorlist = (TextView) itemView.findViewById(R.id.tvazexhibitorlist);
            mhallno = (TextView) itemView.findViewById(R.id.tvhallno);


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
