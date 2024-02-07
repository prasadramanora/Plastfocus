package com.ramanora.plastfocus.PlastFocus_Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import com.ramanora.plastfocus.PlastFocus_Activities.BookMeeting;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MeetingsDateDialogAdapter extends RecyclerView.Adapter<MeetingsDateDialogAdapter.DataHolder> implements Filterable, Serializable {

    private Context mContext;
    private List<PlastFocusModelClass> mArrayListAZ;
    public static List<PlastFocusModelClass> mFilteredList = new ArrayList<>();
    FragmentManager mFragment;
    public static String x_cordinate;
    public static String sleecteddatemeeting;
    public  static  int positionindex = 0;
    Dialog dialog;
    public MeetingsDateDialogAdapter(Context mContext, List<PlastFocusModelClass> mArrayListAZ, Dialog dialog) {
        this.mContext = mContext;
        this.mArrayListAZ = mArrayListAZ;
        this.mFilteredList = mArrayListAZ;
        this.dialog = dialog;


    }

    @Override
    public MeetingsDateDialogAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.meedigdatesdialogitem, parent, false);
        MeetingsDateDialogAdapter.DataHolder dataHolder = new MeetingsDateDialogAdapter.DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(final MeetingsDateDialogAdapter.DataHolder holder, @SuppressLint("RecyclerView") int position) {


        PlastFocusModelClass azExhibitorListPojo = mFilteredList.get(position);
        holder.tv_meetingdates.setText(azExhibitorListPojo.getMeetingdates());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
               // Toast.makeText(mContext, ""+holder.tv_meetingdates.getText().toString(), Toast.LENGTH_SHORT).show();
                sleecteddatemeeting=holder.tv_meetingdates.getText().toString();
                BookMeeting.setDate();
               /* PojoHomePojo azExhibitorListPojo = mFilteredList.get(position);
                ExhibitorDeatilsFragment exhibitorListGridViewFragment = new ExhibitorDeatilsFragment();
                mFragment = ((ActivityExhibitiorlistTab) mContext).getSupportFragmentManager();
                mFragment.beginTransaction().add(R.id.activityexhibitorlist, exhibitorListGridViewFragment).addToBackStack("").commit();

                Bundle bundle = new Bundle();
                bundle.putSerializable("AZData", azExhibitorListPojo);
                exhibitorListGridViewFragment.setArguments(bundle);*/
               // hideKeyboard(mContext);

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
                                || azExhibitorListPojo.getCompany_name().toUpperCase().contains(charString)|| azExhibitorListPojo.getCountry().toUpperCase().contains(charString)|| azExhibitorListPojo.getHall().toUpperCase().contains(charString)|| azExhibitorListPojo.getStall_number().toUpperCase().contains(charString)|| azExhibitorListPojo.getCity().toUpperCase().contains(charString)|| azExhibitorListPojo.getState().toUpperCase().contains(charString)|| azExhibitorListPojo.getCountry().toUpperCase().contains(charString)) {

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

        TextView tv_meetingdates, mhallno, tv_x_coordinate, tv_y_coordinate,tv_ownername;
        RelativeLayout rl_mainlayout;

        public DataHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            tv_meetingdates = (TextView) itemView.findViewById(R.id.tv_meetingdates);

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
