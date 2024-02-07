package com.ramanora.plastfocus.PlastFocus_Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Activities.ExhibitorList;
import com.ramanora.plastfocus.PlastFocus_Activities.GetExhibitorScanDataDetails;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.ExhibitorModel;

import java.util.ArrayList;

public class Adapter_ExibitorScanned_List extends RecyclerView.Adapter<Adapter_ExibitorScanned_List.ViewHolder> {
    Context mContext;
    ExhibitorModel qrcode;
    private ArrayList<ExhibitorModel> mArrayListScannedInfo;


    public Adapter_ExibitorScanned_List(Context mContext, ArrayList<ExhibitorModel> mArrayListScannedInfo) {
        this.mContext = mContext;
        this.mArrayListScannedInfo = mArrayListScannedInfo;
    }

    @Override
    public Adapter_ExibitorScanned_List.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.visitorscanadapter, parent, false);
        Adapter_ExibitorScanned_List.ViewHolder dataHolder = new Adapter_ExibitorScanned_List.ViewHolder(view);
        return dataHolder;

    }

    @Override
    public void onBindViewHolder(Adapter_ExibitorScanned_List.ViewHolder holder, int position) {
        qrcode = mArrayListScannedInfo.get(position);


        holder.tv_companyname.setText(
                qrcode.getFull_name()

        );
        Log.d("Country", "Country " + qrcode.getCountry());

        holder.tv_email.setText(qrcode.getEmail() + " | " + qrcode.getPhone_number());
        holder.tv_phone.setText(qrcode.getCompany());
        holder.tv_designation.setText(qrcode.getDesignation());
        holder.tv_address.setText(qrcode.getAddress());
        String producs = qrcode.getProducts();
        producs = producs.replace(",", "\n");
        producs = producs.replace("[", "");
        producs = producs.replace("]", "");
        producs = producs.replaceAll("\"", "");
        String[] separated = producs.split("\"");


        Log.d("Country", "Country " + qrcode.getCountry());




     /*   if (qrcode.getName().equalsIgnoreCase("null") || qrcode.getName() == null || qrcode.getName().equalsIgnoreCase(""))
        {

            //System.out.println(qrcode.getName());
        }
        else {
            holder.mtxtPersonName.setVisibility(View.VISIBLE);
            holder.mtxtPersonName.setText("Name : " + qrcode.getName());
        }

        if (qrcode.getEmail().equalsIgnoreCase("null") || qrcode.getEmail() == null || qrcode.getEmail().equalsIgnoreCase(""))
        {
          //  System.out.println(qrcode.getEmail());
        }
        else {
            holder.mtxtPersonEmail.setVisibility(View.VISIBLE);
            holder.mtxtPersonEmail.setText("Email : " + qrcode.getEmail());
        }

        if (qrcode.getCompany().equalsIgnoreCase("null") || qrcode.getCompany() == null || qrcode.getCompany().equalsIgnoreCase(""))
        {
            //nothing dispay
        }
        else {
            holder.mtxtPersonComapny.setVisibility(View.VISIBLE);
            holder.mtxtPersonComapny.setText("Company : " + qrcode.getCompany());
        }

        if (qrcode.getPhone().equalsIgnoreCase("null") || qrcode.getPhone() == null || qrcode.getPhone().equalsIgnoreCase(""))
        {
            //nothing dispay
        }
        else {
            holder.mtxtPersonPhone.setVisibility(View.VISIBLE);
            holder.mtxtPersonPhone.setText("Phone : " + qrcode.getPhone());
        }

        if (qrcode.getDesignation().equalsIgnoreCase("null") || qrcode.getDesignation() == null || qrcode.getDesignation().equalsIgnoreCase(""))
        {
            //nothing dispay
        }
        else {
            holder.mtxtPersondesignation.setVisibility(View.VISIBLE);
            holder.mtxtPersondesignation.setText("Designation :" + qrcode.getDesignation().trim());
        }

        if (qrcode.getCountry().equalsIgnoreCase("null") || qrcode.getCountry() == null || qrcode.getCountry().equalsIgnoreCase(""))
        {
            //nothing dispay
        }
        else {
            holder.mtxtPersoncountry.setVisibility(View.VISIBLE);
            holder.mtxtPersoncountry.setText("Country :" + qrcode.getCountry().trim());
        }

        if (qrcode.getCity().equalsIgnoreCase("null") || qrcode.getCity() == null || qrcode.getCity().equalsIgnoreCase(""))
        {
            //nothing dispay
        }
        else {
            holder.mtxtPersoncity.setVisibility(View.VISIBLE);
            holder.mtxtPersoncity.setText("City :" + qrcode.getCity().trim());
        }

        if (qrcode.getTypeofvisitor().equalsIgnoreCase("null") || qrcode.getTypeofvisitor() == null || qrcode.getTypeofvisitor().equalsIgnoreCase(""))
        {
            //nothing dispay
        }
        else {

            holder.mtxtPersontypeofvisitor.setVisibility(View.VISIBLE);
            holder.mtxtPersontypeofvisitor.setText("Type of Visitor : " + qrcode.getTypeofvisitor().trim());
        }


        if (qrcode.getOther().equalsIgnoreCase("null") || qrcode.getOther() == null || qrcode.getOther().equalsIgnoreCase(""))
        {
            //nothing dispay
        }
        else {
            holder.mtxtPersonother.setVisibility(View.VISIBLE);
            holder.mtxtPersonother.setText("Other : " + qrcode.getOther().trim());
        }*/

        try {
            if (!ExhibitorList.checkvisitorfound.equals("")) {
                Log.e("CheckValue", ExhibitorList.checkvisitorfound);
                if (position == 0) {
                    holder.ll_layoutitem.setBackgroundResource(R.drawable.cardviewborder);
                }

            } else {
                Log.e("CheckValue", "No");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mArrayListScannedInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_companyname, tv_email, tv_phone, tv_designation, tv_address;
        LinearLayout ll_layoutitem;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_layoutitem = (LinearLayout) itemView.findViewById(R.id.ll_layoutitem);
            tv_companyname = (TextView) itemView.findViewById(R.id.tv_companyname);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_designation = (TextView) itemView.findViewById(R.id.tv_designation);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, GetExhibitorScanDataDetails.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("Name", qrcode.getFull_name());
                    i.putExtra("Company", qrcode.getCompany());
                    i.putExtra("Designation", qrcode.getDesignation());
                    i.putExtra("Address", qrcode.getAddress());
                    i.putExtra("Mobile", qrcode.getPhone_number());
                    i.putExtra("Email", qrcode.getEmail());
                    i.putExtra("Products", qrcode.getProducts());
                    i.putExtra("city", qrcode.getCity());
                    i.putExtra("state", qrcode.getState());
                    i.putExtra("country", qrcode.getCountry());
                    i.putExtra("zipcode", qrcode.getZip_code());


                    mContext.startActivity(i);
                }
            });

        }
    }


}

