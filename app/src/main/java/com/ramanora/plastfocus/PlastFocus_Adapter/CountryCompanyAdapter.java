package com.ramanora.plastfocus.PlastFocus_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

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
import com.ramanora.plastfocus.PlastFocus_Activities.GetExhibitorDeatilsClass;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;

import java.util.ArrayList;

/**
 * Created by admin on 10/14/2017.
 */

public class CountryCompanyAdapter extends RecyclerView.Adapter<CountryCompanyAdapter.DataHolder> implements Filterable {

    private Context mContext;
    private ArrayList<PlastFocusModelClass> mArrayListCompany;
    private ArrayList<PlastFocusModelClass> mFilteredList;
    FragmentManager mFragment;
    public  static  int positionindex = 0;
    public CountryCompanyAdapter(Context mContext, ArrayList<PlastFocusModelClass> mArrayListCompany) {
        this.mContext = mContext;
        this.mArrayListCompany = mArrayListCompany;
        this.mFilteredList = mArrayListCompany;

    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.country_company_adapter,parent,false);

        DataHolder dataHolder=new DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(final DataHolder holder, @SuppressLint("RecyclerView") final int position) {

        final PlastFocusModelClass companyPojo = mFilteredList.get(position);

        holder.mTvCompanyName.setText(companyPojo.getCompany_name());
        holder.tvaddress.setText(companyPojo.getState()+" || "+companyPojo.getCountry()+"||"+companyPojo.getAddress());

        holder.mTvCompanyhall.setText("Hall : "+companyPojo.getStall_number().replace("null","")+" "+"Stall : "+companyPojo.getCord_x().replace("null",""));
        String hallNo = companyPojo.getHall();
        Log.d("hallNo","Hall No "+hallNo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExhibitorMainAdapter.positionindex = position;
                ExhibitorMainAdapter.exhibitorListDeatils=mFilteredList;
                Intent i=new Intent((ActivityExhibitiorlistTab) mContext, GetExhibitorDeatilsClass.class);
                i.putExtra("checkfragment", "country");

                mContext.startActivity(i);
              /*  PojoHomePojo companyPojo = mFilteredList.get(position);
                CountryGridViewFragment countryGridViewFragment=new CountryGridViewFragment();
                mFragment=((ActivityExhibitiorlistTab)mContext).getSupportFragmentManager();
                mFragment.beginTransaction().add(R.id.activityexhibitorlist,countryGridViewFragment).addToBackStack("").commit();

                Bundle bundle = new Bundle();
                bundle.putSerializable("AZData",companyPojo);

                countryGridViewFragment.setArguments(bundle);
                hideKeyboard(mContext);*/

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

                        if (hallExhibitorListPojo.getCompany_name().toLowerCase().contains(charString) || hallExhibitorListPojo.getCompany_name().toUpperCase().contains(charString)) {

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

        TextView mTvCompanyName, mTvCompanyhall,tvaddress;

        public DataHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            mTvCompanyName = (TextView) itemView.findViewById(R.id.mTvCompanyName);
            mTvCompanyhall = (TextView) itemView.findViewById(R.id.tvhallname);
            tvaddress= (TextView) itemView.findViewById(R.id.tvaddress);


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