package com.ramanora.plastfocus.PlastFocus_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Activities.ExhibitorListMeeting;
import com.ramanora.plastfocus.PlastFocus_Activities.VisitoreDeatilsPage;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;

import java.util.ArrayList;

public class ExhibitorMeetingAdapter extends RecyclerView.Adapter<ExhibitorMeetingAdapter.ViewHolder> {
    Context mContext;
    PlastFocusModelClass meetingdetailsmodel;
    public static ArrayList<PlastFocusModelClass> exhibitormeetinglist;

    public  static  int positionindex = 0;
    public ExhibitorMeetingAdapter(Context mContext, ArrayList<PlastFocusModelClass> mArrayListScannedInfo) {
        this.mContext = mContext;
        this.exhibitormeetinglist = mArrayListScannedInfo;
    }

    @Override
    public ExhibitorMeetingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.exhibitoradpter_listview_scanned_info, parent, false);
        ExhibitorMeetingAdapter.ViewHolder dataHolder = new ExhibitorMeetingAdapter.ViewHolder(view);
        return dataHolder;

    }

    @Override
    public void onBindViewHolder(ExhibitorMeetingAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        meetingdetailsmodel = exhibitormeetinglist.get(position);


        holder.tv_meetingtimedate.setText(meetingdetailsmodel.getMeeting_date() + " From " + meetingdetailsmodel.getMeeting_time());
        holder.tv_username.setText(meetingdetailsmodel.getCoordinator_name()+" "+"("+meetingdetailsmodel.getCoordinator_designation()+")");
        holder.tv_company.setText(meetingdetailsmodel.getCompany_name());
        holder.tv_email.setText(meetingdetailsmodel.getEmail() + " | " + meetingdetailsmodel.getMobile_number());
      //  holder.tv_nane.setText(meetingdetailsmodel.getCoordinator_name() + " " + meetingdetailsmodel.getCoordinator_last_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                positionindex = position;
                Intent i=new Intent(mContext, VisitoreDeatilsPage.class);
                i.putExtra("AZData", meetingdetailsmodel);
                mContext.startActivity(i);



            }
        });
        try {
            if (!ExhibitorListMeeting.Checkmeeting.equals("")) {
                Log.e("CheckValue", ExhibitorListMeeting.Checkmeeting);
                if(position==0)
                {
                    holder.ll_exlist.setBackgroundResource(R.drawable.cardviewborder);
                }

            } else {
                Log.e("CheckValue", "No");

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

       /* if(ExhibitorListMeeting.Checkmeeting.length()>0)
        {
            holder.exhibitorcard.setBackgroundResource(R.drawable.buttunborder);

        }*/
    }

    @Override
    public int getItemCount() {
        return exhibitormeetinglist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_username, tv_email, tv_company, tv_nane, tv_meetingtimedate;
        LinearLayout ll_exlist;
        CardView exhibitorcard;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_exlist= (LinearLayout) itemView.findViewById(R.id.ll_exlist);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_meetingtimedate = (TextView) itemView.findViewById(R.id.tv_meetingtimedate);
            tv_company = (TextView) itemView.findViewById(R.id.tv_company);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
exhibitorcard=itemView.findViewById(R.id.exhibitorcard);

        }
    }


}
