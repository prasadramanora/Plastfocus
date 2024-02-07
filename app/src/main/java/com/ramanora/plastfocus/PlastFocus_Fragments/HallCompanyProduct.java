package com.ramanora.plastfocus.PlastFocus_Fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ramanora.plastfocus.R;


/**
 * Created by Owner on 09/10/2017.
 */

public class HallCompanyProduct extends Fragment {

    TextView mCompanyProduct, mCompanyName;


    public HallCompanyProduct() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, null);

        /*mCompanyProduct = (TextView) view.findViewById(R.id.azcompanyproduct);
        mCompanyName = (TextView) view.findViewById(R.id.azcompanyname);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        Bundle bundle2 = getArguments();

        String companyname = bundle2.getString("companyname");
        // String products = azExhibitorListPojo.getProdcut();

        System.out.println("company name : " + companyname);





        System.out.println("mArrayListCompany company :" + listprod.get(0).getProductname());

        String productname = listprod.get(0).getProductname();

        mCompanyProduct.setText(productname);

        String[] productlist = productname.split(",");
        System.out.println("length" + productlist.length);

        String temp = "";


        *//*for (int i = 0; i < productlist.length; i++) {
            temp = temp + productlist[i].toString() + " " + "\n\n";
            mCompanyProduct.setText(temp);


        }*//*

        for (int i = 0; i < productlist.length; i++) {
            temp = temp + productlist[i].toString() + " " + "\n";
            mCompanyProduct.setText(temp);


        }

     *//*   for (int i = 0; i < productlist.length; i++) {
            temp = temp + productlist[i].toString() + "\n\n" + "<br><font color='#33B5E5'>" +
                    " Catalog : http://www.sany.in/products/index.html" + " </font><br><br>\n\n";

// temp = temp + productlist[i].toString() + " " + "\n\n";
            mCompanyProduct.setText(Html.fromHtml(temp+"\n\n"));


        }
        mCompanyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sany.in/products/index.html")));
            }
        });
*//*
        productname.replaceAll(",", "\n");
*/

        return view;


    }
}
