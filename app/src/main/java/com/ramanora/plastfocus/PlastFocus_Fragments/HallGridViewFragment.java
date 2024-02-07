package com.ramanora.plastfocus.PlastFocus_Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class HallGridViewFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    String companyname;




    String hall, stallno;

    ArrayList<PlastFocusModelClass> arrayListcompany = new ArrayList<PlastFocusModelClass>();

    SliderLayout sliderLayout;
    HashMap<String, Integer> Hash_file_maps;


    public HallGridViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, null);

      //  TextView textView = (TextView) view.findViewById(R.id.companyNameGridview);


        //Data come from AZExhibitorAdapter
        Bundle bundle = getArguments();
        PlastFocusModelClass azExhibitorListPojo = (PlastFocusModelClass) bundle.getSerializable("AZData");
        String CompanyName = azExhibitorListPojo.getCompany_name();
        stallno = azExhibitorListPojo.getStall_number();
        System.out.println("companyname" + CompanyName);
        arrayListcompany.add(azExhibitorListPojo);

        hall = bundle.getString("hallname");
        //     stallno = bundle.getString("stallno");


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //textView.setText(CompanyName);


        @SuppressLint("MissingInflatedId") RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_grid_viewaz);
        recyclerView.setHasFixedSize(true);

       /* RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<GridHallPojo> mArrayListGrid = prepareData1();
        HallGridViewAdapter adapter = new HallGridViewAdapter(mArrayListGrid, arrayListcompany, getActivity(), hall, stallno);
        recyclerView.setAdapter(adapter);*/

        //image slider
        try {
        Hash_file_maps = new HashMap<>();

     //   sliderLayout = (SliderLayout) view.findViewById(R.id.hallgridslider);


  /*  Hash_file_maps.put("STONA 2018", R.drawable.imageslider1);
    Hash_file_maps.put("13TH INTERNATIONAL GRANITES & STONE FAIR", R.drawable.imageslider2);
    Hash_file_maps.put("7TH TO 10TH FEB 2018", R.drawable.imageslider3);
    Hash_file_maps.put(" ", R.drawable.imageslider4);
*/
    for (String name : Hash_file_maps.keySet()) {
        TextSliderView textSliderView = new TextSliderView(getActivity());
        textSliderView
                .description(name)
                .image(Hash_file_maps.get(name))
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .setOnSliderClickListener(this);
        textSliderView.bundle(new Bundle());
        textSliderView.getBundle().putString("extra", name);
        sliderLayout.addSlider(textSliderView);
    }
    sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
    sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
    sliderLayout.setCustomAnimation(new DescriptionAnimation());
    sliderLayout.setDuration(3000);
    sliderLayout.addOnPageChangeListener(this);
}catch (Exception e)
{
    e.printStackTrace();
}

        return view;

    }




    //image slider

    @Override
    public void onStop() {
        try {
            sliderLayout.stopAutoCycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        //     Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


}
