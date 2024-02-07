package com.ramanora.plastfocus.PlastFocus_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Activities.GetVisitorScanDeatilsPage;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.QrcodeModel;

import java.util.ArrayList;

/**
 * Created by admin on 11/16/2017.
 */

public class Adapter_Visitor_Scanned extends RecyclerView.Adapter<Adapter_Visitor_Scanned.ViewHolder> {
    Context mContext;
    QrcodeModel qrcode;
    public static ArrayList<QrcodeModel> OfflineSyncListQrcodeData;

    public static int positionindex;

    public Adapter_Visitor_Scanned(Context mContext, ArrayList<QrcodeModel> OfflineSyncListQrcodeData) {
        this.mContext = mContext;
        this.OfflineSyncListQrcodeData = OfflineSyncListQrcodeData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.visitorscanadapter, parent, false);
        ViewHolder dataHolder = new ViewHolder(view);
        return dataHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        qrcode = OfflineSyncListQrcodeData.get(position);

        holder.tv_companyname.setVisibility(View.VISIBLE);
        holder.tv_companyname.setText(
                qrcode.getCoordinator_name()

        );

        holder.tv_email.setText(qrcode.getEmail() + " | " + qrcode.getMobile_number());
        holder.tv_phone.setText(qrcode.getCompany_name());
        holder.tv_designation.setText(qrcode.getCoordinator_designation());
        holder.tv_address.setText(qrcode.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionindex = position;
                Intent i = new Intent(mContext, GetVisitorScanDeatilsPage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("Name", qrcode.getCoordinator_name());
                i.putExtra("Company", qrcode.getCompany_name());
                i.putExtra("Designation", qrcode.getCoordinator_designation());
                i.putExtra("Address", qrcode.getAddress());
                i.putExtra("Mobile", qrcode.getMobile_number());
                i.putExtra("Email", qrcode.getEmail());
                i.putExtra("city", qrcode.getCity());
                i.putExtra("state", qrcode.getState());
                i.putExtra("country", qrcode.getCountry());
                i.putExtra("zipcode", qrcode.getPincode());
                mContext.startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        return OfflineSyncListQrcodeData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_companyname, tv_email, tv_phone, tv_designation, tv_address
                ;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_companyname = (TextView) itemView.findViewById(R.id.tv_companyname);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_designation = (TextView) itemView.findViewById(R.id.tv_designation);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);


        }
    }


}
