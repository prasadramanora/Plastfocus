package com.ramanora.plastfocus.PlastFocus_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductFilterAdapter extends RecyclerView.Adapter<ProductFilterAdapter.DataHolder> implements Filterable, Serializable {

    private Context mContext;
    private List<PlastFocusModelClass> mArrayListAZ;
    public static List<PlastFocusModelClass> mFilteredList = new ArrayList<>();
    FragmentManager mFragment;
    public static String x_cordinate;
    public static String y_cordinate;
    public static int positionindex = 0;

    public ProductFilterAdapter(Context mContext, List<PlastFocusModelClass> mArrayListAZ) {
        this.mContext = mContext;
        this.mArrayListAZ = mArrayListAZ;
        this.mFilteredList = mArrayListAZ;

    }

    @Override
    public ProductFilterAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.exhibitor_az_adapter, parent, false);
        ProductFilterAdapter.DataHolder dataHolder = new ProductFilterAdapter.DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(final ProductFilterAdapter.DataHolder holder, @SuppressLint("RecyclerView") int position) {


        PlastFocusModelClass azExhibitorListPojo = mFilteredList.get(position);
        holder.mTvAzExhibitorlist.setText(azExhibitorListPojo.getCompany_name());
        holder.tv_ownername.setText(azExhibitorListPojo.getCity() + " || " + azExhibitorListPojo.getState() + " || " + azExhibitorListPojo.getCountry());
        holder.tv_y_coordinate.setText(azExhibitorListPojo.getCord_y());
        holder.tv_x_coordinate.setText(azExhibitorListPojo.getCord_x());

        String hall = azExhibitorListPojo.getHall();
        String stallno = azExhibitorListPojo.getStall_number();

        if (hall.equals("null") || hall.equals("")) {
            hall = "";
        }
        if (stallno.equals("null") || stallno.equals("")) {
            stallno = "";
        }
        holder.mhallno.setText("Hall : " + hall + " " + "Stall : " + stallno);
        Log.e("Checkleagth", holder.mTvAzExhibitorlist.getText().toString().length() + "");


        String hallNo = azExhibitorListPojo.getHall();
//        Log.d("hallNo","Hall No AZ "+azExhibitorListPojo.getStallno().length());

//        Log.e("getY()",azExhibitorListPojo.getY());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionindex = position;



               /* PojoHomePojo azExhibitorListPojo = mFilteredList.get(position);
                ExhibitorDeatilsFragment exhibitorListGridViewFragment = new ExhibitorDeatilsFragment();
                mFragment = ((ActivityExhibitiorlistTab) mContext).getSupportFragmentManager();
                mFragment.beginTransaction().add(R.id.activityexhibitorlist, exhibitorListGridViewFragment).addToBackStack("").commit();

                Bundle bundle = new Bundle();
                bundle.putSerializable("AZData", azExhibitorListPojo);
                exhibitorListGridViewFragment.setArguments(bundle);*/
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

                        if (azExhibitorListPojo.getCompany_name().toLowerCase().contains(charString)
                                || azExhibitorListPojo.getCompany_name().toUpperCase().contains(charString)) {

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

        TextView mTvAzExhibitorlist, mhallno, tv_x_coordinate, tv_y_coordinate, tv_ownername;
        RelativeLayout rl_mainlayout;

        public DataHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            tv_ownername = (TextView) itemView.findViewById(R.id.tv_ownername);
            rl_mainlayout = (RelativeLayout) itemView.findViewById(R.id.rl_mainlayout);
            mTvAzExhibitorlist = (TextView) itemView.findViewById(R.id.tvazexhibitorlist);
            mhallno = (TextView) itemView.findViewById(R.id.tvhallno);
            // tvproductname = (TextView) itemView.findViewById(R.id.tvproductname);
            tv_x_coordinate = (TextView) itemView.findViewById(R.id.tv_x_coordinate);

            tv_y_coordinate = (TextView) itemView.findViewById(R.id.tv_y_coordinate);
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