package com.ramanora.plastfocus.PlastFocus_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramanora.plastfocus.PlastFocus_Activities.ActivityExhibitiorlistTab;
import com.ramanora.plastfocus.PlastFocus_Fragments.ProductCompanyNameFragment;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.NotificationModel;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;
import com.ramanora.plastfocus.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NoticationAdapter extends RecyclerView.Adapter<NoticationAdapter.DataHolder>  {

    private Context mContext;
    private List<NotificationModel> mArrayListAZ;
    private List<NotificationModel> mFilteredList = new ArrayList<>();
    public static String produtname;

    public NoticationAdapter(Context mContext, List<NotificationModel> mArrayListAZ) {
        this.mContext = mContext;
        this.mArrayListAZ = mArrayListAZ;
        this.mFilteredList = mArrayListAZ;

    }

    @Override
    public NoticationAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.notificationitems, parent, false);
        NoticationAdapter.DataHolder dataHolder = new NoticationAdapter.DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(final NoticationAdapter.DataHolder holder, @SuppressLint("RecyclerView") int position) {


        NotificationModel azExhibitorListPojo = mFilteredList.get(position);


        holder.tv_createdat.setText(azExhibitorListPojo.getCreatedat());
        holder.tv_title.setText(azExhibitorListPojo.getTitle());
        holder.tv_message.setText(azExhibitorListPojo.getMessage());


    }


    @Override
    public int getItemCount() {
        Log.d("test_exhibitor_list", "getItemCount: " + mFilteredList.size());
        return mFilteredList.size();
    }


    public class DataHolder extends RecyclerView.ViewHolder {

        TextView tv_createdat,tv_title, tv_message;

        public DataHolder(View itemView) {
            super(itemView);
            tv_createdat = (TextView) itemView.findViewById(R.id.tv_createdat);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);

        }
    }



}

