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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ramanora.plastfocus.R;

import com.ramanora.plastfocus.PlastFocus_Activities.ActivityExhibitiorlistTab;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;
import com.ramanora.plastfocus.PlastFocus_Fragments.ProductCompanyNameFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.DataHolder> implements Filterable, Serializable {

    private Context mContext;
    private List<PlastFocusModelClass> mArrayListAZ;
    private List<PlastFocusModelClass> mFilteredList = new ArrayList<>();
    public static String produtname;

    public ProductAdapter(Context mContext, List<PlastFocusModelClass> mArrayListAZ) {
        this.mContext = mContext;
        this.mArrayListAZ = mArrayListAZ;
        this.mFilteredList = mArrayListAZ;

    }

    @Override
    public ProductAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.productitms, parent, false);
        ProductAdapter.DataHolder dataHolder = new ProductAdapter.DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(final ProductAdapter.DataHolder holder, @SuppressLint("RecyclerView") int position) {


        PlastFocusModelClass azExhibitorListPojo = mFilteredList.get(position);


        holder.tv_productname.setText(azExhibitorListPojo.getProductnames());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlastFocusModelClass azExhibitorListPojo = mFilteredList.get(position);
                produtname = holder.tv_productname.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("key", holder.tv_productname.getText().toString());
                ProductCompanyNameFragment companyNameFragment = new ProductCompanyNameFragment();
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
        Log.d("test_exhibitor_list", "getItemCount: " + mFilteredList.size());
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

                        if (azExhibitorListPojo.getProductnames().toLowerCase().contains(charString)
                                || azExhibitorListPojo.getProductnames().toUpperCase().contains(charString)) {

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

        TextView tv_productname, tv_companyname;

        public DataHolder(View itemView) {
            super(itemView);
            tv_companyname = (TextView) itemView.findViewById(R.id.tv_companyname);
            tv_productname = (TextView) itemView.findViewById(R.id.tv_productname);

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

