package com.ramanora.plastfocus.PlastFocus_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;
import com.ramanora.plastfocus.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExhibitorMapAdapterWithDesitnation extends RecyclerView.Adapter<ExhibitorMapAdapterWithDesitnation.DataHolder> implements Filterable, Serializable {

    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private List<PlastFocusModelClass> ExhibitorAtoZList;
    public static List<PlastFocusModelClass> exhibitorListDeatils = new ArrayList<>();
    FragmentManager mFragment;
    public static String x_cordinate;
    public static String y_cordinate;
    public static int positionindex = 0;
    PlastFocusModelClass azExhibitorListPojo;
    public ExhibitorMapAdapterWithDesitnation(Context mContext, List<PlastFocusModelClass> ExhibitorAtoZList,OnItemClickListener listener) {
        this.mContext = mContext;
        this.ExhibitorAtoZList = ExhibitorAtoZList;
        this.exhibitorListDeatils = ExhibitorAtoZList;
        this.onItemClickListener = listener;

    }

    @Override
    public ExhibitorMapAdapterWithDesitnation.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.exhibitor_az_adapter, parent, false);
        ExhibitorMapAdapterWithDesitnation.DataHolder dataHolder = new ExhibitorMapAdapterWithDesitnation.DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(final ExhibitorMapAdapterWithDesitnation.DataHolder holder, @SuppressLint("RecyclerView") int position) {


         azExhibitorListPojo = exhibitorListDeatils.get(position);
        holder.mTvAzExhibitorlist.setText(azExhibitorListPojo.getCompany_name());
        holder.tv_ownername.setText(azExhibitorListPojo.getCity() + " | " + azExhibitorListPojo.getState() + " | " + azExhibitorListPojo.getCountry());
        holder.tv_y_coordinate.setText(azExhibitorListPojo.getCord_y());
        holder.tv_x_coordinate.setText(azExhibitorListPojo.getCord_x());
        holder.tv_stall.setText("Stall : " + azExhibitorListPojo.getStall_number());

        holder.mhallno.setText("Hall : " + azExhibitorListPojo.getHall());
        // Log.e("Checkleagth", holder.mTvAzExhibitorlist.getText().toString().length() + "");


        String hallNo = azExhibitorListPojo.getHall();
//        Log.d("hallNo","Hall No AZ "+azExhibitorListPojo.getStallno().length());

//        Log.e("getY()",azExhibitorListPojo.getY());


        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  positionindex = position;
                Intent i = new Intent(mContext, GoToMap.class);
                i.putExtra("X_cordinate", azExhibitorListPojo.getCord_x());
                i.putExtra("Y_cordinate", azExhibitorListPojo.getCord_y());
                i.putExtra("ExhibitorName", azExhibitorListPojo.getCompany_name());
               // i.set(RESULT_OK, i);
                mContext.startActivity(i);


            }
        });*/
    }

    @Override
    public int getItemCount() {
        Log.d("test_exhibitor_list", "getItemCount: " + exhibitorListDeatils.size());
        return exhibitorListDeatils.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    exhibitorListDeatils = ExhibitorAtoZList;
                } else {

                    ArrayList<PlastFocusModelClass> filteredList = new ArrayList<>();

                    for (PlastFocusModelClass azExhibitorListPojo : ExhibitorAtoZList) {

                        if (azExhibitorListPojo.getCompany_name().toLowerCase().contains(charString)
                                || azExhibitorListPojo.getCompany_name().toLowerCase().contains(charString) ||
                                azExhibitorListPojo.getCountry().toLowerCase().contains(charString) ||
                                azExhibitorListPojo.getHall().toLowerCase().contains(charString) ||
                                azExhibitorListPojo.getStall_number().toLowerCase().contains(charString) ||
                                azExhibitorListPojo.getCity().toLowerCase().contains(charString) ||
                                azExhibitorListPojo.getState().toLowerCase().contains(charString) ||
                                azExhibitorListPojo.getCountry().toLowerCase().contains(charString) ||
                                azExhibitorListPojo.getHall().toLowerCase().contains(charString) ||
                                azExhibitorListPojo.getStall_number().toUpperCase().contains(charString) ||

                                azExhibitorListPojo.getCompany_name().toUpperCase().contains(charString)
                                || azExhibitorListPojo.getCompany_name().toUpperCase().contains(charString) ||
                                azExhibitorListPojo.getCountry().toUpperCase().contains(charString) ||
                                azExhibitorListPojo.getHall().toUpperCase().contains(charString) ||
                                azExhibitorListPojo.getStall_number().toUpperCase().contains(charString) ||
                                azExhibitorListPojo.getCity().toUpperCase().contains(charString) ||
                                azExhibitorListPojo.getState().toUpperCase().contains(charString) ||
                                azExhibitorListPojo.getCountry().toUpperCase().contains(charString) ||
                                azExhibitorListPojo.getHall().toUpperCase().contains(charString) ||
                                azExhibitorListPojo.getStall_number().toUpperCase().contains(charString) ||


                                azExhibitorListPojo.getPincode().toLowerCase().contains(charString)) {

                            filteredList.add(azExhibitorListPojo);
                        }
                    }

                    exhibitorListDeatils = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = exhibitorListDeatils;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                exhibitorListDeatils = (ArrayList<PlastFocusModelClass>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public class DataHolder extends RecyclerView.ViewHolder {

        TextView mTvAzExhibitorlist, mhallno, tv_x_coordinate, tv_y_coordinate, tv_ownername, tv_stall;
        RelativeLayout rl_mainlayout;

        public DataHolder(View itemView) {
            super(itemView);

            onItemClickListener.onItemClick(itemView,azExhibitorListPojo,getAdapterPosition());


            tv_stall = (TextView) itemView.findViewById(R.id.tv_stall);
            tv_ownername = (TextView) itemView.findViewById(R.id.tv_ownername);
            rl_mainlayout = (RelativeLayout) itemView.findViewById(R.id.rl_mainlayout);
            mTvAzExhibitorlist = (TextView) itemView.findViewById(R.id.tvazexhibitorlist);
            mhallno = (TextView) itemView.findViewById(R.id.tvhallno);
            // tvproductname = (TextView) itemView.findViewById(R.id.tvproductname);
            tv_x_coordinate = (TextView) itemView.findViewById(R.id.tv_x_coordinate);

            tv_y_coordinate = (TextView) itemView.findViewById(R.id.tv_y_coordinate);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view,PlastFocusModelClass plastFocusModelClass,int position);
    }
}
