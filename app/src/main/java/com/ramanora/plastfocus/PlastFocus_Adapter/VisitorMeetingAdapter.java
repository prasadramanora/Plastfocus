package com.ramanora.plastfocus.PlastFocus_Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Activities.ExhibitorMeetingDeatils;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;

import java.util.ArrayList;

public class VisitorMeetingAdapter extends RecyclerView.Adapter<VisitorMeetingAdapter.ViewHolder> {
    Context mContext;
    PlastFocusModelClass meetingdetailsmodel;
    public static  ArrayList<PlastFocusModelClass> exhibitormeetinglist;

    public  static  int positionindex = 0;
    public VisitorMeetingAdapter(Context mContext, ArrayList<PlastFocusModelClass> mArrayListScannedInfo) {
        this.mContext = mContext;
        this.exhibitormeetinglist = mArrayListScannedInfo;
    }

    @Override
    public VisitorMeetingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adpter_listview_scanned_info, parent, false);
        VisitorMeetingAdapter.ViewHolder dataHolder = new VisitorMeetingAdapter.ViewHolder(view);
        return dataHolder;

    }

    @Override
    public void onBindViewHolder(VisitorMeetingAdapter.ViewHolder holder, int position) {
        meetingdetailsmodel = exhibitormeetinglist.get(position);


        holder.tv_meetingtimedate.setText(meetingdetailsmodel.getMeeting_date() + " From " + meetingdetailsmodel.getMeeting_time());
        holder.tv_companyname.setText(meetingdetailsmodel.getCompany_name());
        holder.tv_hall.setText("Hall - " + meetingdetailsmodel.getHall() + " " + "Stall - " + meetingdetailsmodel.getStall_number());
        holder.tv_email.setText(meetingdetailsmodel.getEmail() + " | " + meetingdetailsmodel.getMobile_number());
        holder.tv_nane.setText(meetingdetailsmodel.getCoordinator_name() + " " + meetingdetailsmodel.getCoordinator_last_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                positionindex = position;
                Intent i=new Intent(mContext,ExhibitorMeetingDeatils.class);
                i.putExtra("AZData", meetingdetailsmodel);
                mContext.startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return exhibitormeetinglist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_companyname, tv_email, tv_hall, tv_nane, tv_meetingtimedate;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_companyname = (TextView) itemView.findViewById(R.id.tv_companyname);
            tv_meetingtimedate = (TextView) itemView.findViewById(R.id.tv_meetingtimedate);
            tv_hall = (TextView) itemView.findViewById(R.id.tv_hall);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
            tv_nane = (TextView) itemView.findViewById(R.id.tv_name);

        }
    }


}