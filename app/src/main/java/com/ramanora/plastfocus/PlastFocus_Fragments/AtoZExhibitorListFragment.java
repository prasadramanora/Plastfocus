package com.ramanora.plastfocus.PlastFocus_Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.ramanora.plastfocus.PlastFocus_Database.DataBaseHandler;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Activities.ActivityExhibitiorlistTab;
import com.ramanora.plastfocus.PlastFocus_Adapter.ExhibitorMainAdapter;
import com.ramanora.plastfocus.PlastFocus_ModelClasess.PlastFocusModelClass;

import com.ramanora.plastfocus.PlastFocus_Utils.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AtoZExhibitorListFragment extends Fragment {
    Button btn_refresh;
    List<PlastFocusModelClass> mArrayCallRList;
    ArrayList<String> productnamelist = new ArrayList<>();
    String id = "", company_name = "",
            salutation = "",
            coordinator_name = "",
            coordinator_last_name = "",
            coordinator_designation = "",
            address = "",

    country = "",
            state = "",
            city = "",
            pincode = "",
            mobile_number = "",
            alternate_number = "",
            fax = "",
            email = "",
            alternate_email = "",
            website = "",
            product_group = "",
            company_profile = "",
            hall = "",
            stall_number = "",
            cord_x = "",
            cord_y = "";
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    SearchView editTextsearch;
    ExhibitorMainAdapter azExhibitorlistAdapter;

    RelativeLayout relativeLayout;
    FrameLayout fl_fram;
    public static String filterkey = "";

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exhibitorlist_lay_az, null);


        relativeLayout = view.findViewById(R.id.fragmentaz);

        btn_refresh = view.findViewById(R.id.btn_refresh);
        fl_fram = view.findViewById(R.id.fl_fram);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExhibitorData();
            }
        });

        hideKeyboard(getActivity());
        editTextsearch = (SearchView) view.findViewById(R.id.editTextsearch);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_exhibitor_az);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);


        getDatafromDB();


        return view;
    }


    public void getExhibitorData() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler(getActivity());
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.delete("ExhibitorList", null, null);
        db.close();

        // rl_mainhomepage.setVisibility(View.GONE);

        ProgressDialog pd = new ProgressDialog(getActivity(), R.style.StyledDialog);
        pd.setMessage("Exhibitor Data Loaded wait ..");

        pd.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        pd.setCanceledOnTouchOutside(false);
        pd.show();

        String url = "https://ramanora.com/plastfocus/public/api/exhibitors";
        Log.d("ExhibitorData", url);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d("UI thread", "I am the UI thread");
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    //Log.d("ExhibitorData", response.toString());
                                    JSONArray js = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < js.length(); i++) {
                                        JSONObject jsonObject1 = js.getJSONObject(i);
                                        PlastFocusModelClass pojoHomePojo = new PlastFocusModelClass();
                                        id = jsonObject1.getString("id");
                                        pojoHomePojo.setId(id);
                                        company_name = jsonObject1.getString("company_name");
                                        pojoHomePojo.setCompany_name(company_name);
                                        salutation = jsonObject1.getString("salutation");
                                        pojoHomePojo.setSalutation(salutation);
                                        coordinator_name = jsonObject1.getString("coordinator_first_name");
                                        pojoHomePojo.setCoordinator_name(coordinator_name);
                                        coordinator_last_name = jsonObject1.getString("coordinator_last_name");
                                        pojoHomePojo.setCoordinator_last_name(coordinator_last_name);
                                        coordinator_designation = jsonObject1.getString("coordinator_designation");
                                        pojoHomePojo.setCoordinator_designation(coordinator_designation);
                                        address = jsonObject1.getString("address");
                                        address = address.replace("null", "-");
                                        pojoHomePojo.setAddress(address);

                                        country = jsonObject1.getString("country");
                                        country = country.replace("null", "-");
                                        pojoHomePojo.setCountry(country);
                                        state = jsonObject1.getString("state");
                                        state = state.replace("null", "-");
                                        pojoHomePojo.setState(state);

                                        city = jsonObject1.getString("city");
                                        city = city.replace("null", "-");
                                        pojoHomePojo.setCity(city);
                                        pincode = jsonObject1.getString("pincode");
                                        pincode = pincode.replace("null", "-");
                                        pojoHomePojo.setPincode(pincode);
                                        mobile_number = jsonObject1.getString("mobile_number");
                                        pojoHomePojo.setMobile_number(mobile_number);
                                        alternate_number = jsonObject1.getString("alternate_number");
                                        alternate_number = alternate_number.replace("null", "-");
                                        pojoHomePojo.setAlternate_number(alternate_number);
                                        fax = jsonObject1.getString("fax");
                                        fax = fax.replace("null", "-");
                                        pojoHomePojo.setFax(fax);
                                        email = jsonObject1.getString("email");
                                        email = email.replace("null", "-");

                                        pojoHomePojo.setEmail(email);
                                        alternate_email = jsonObject1.getString("alternate_email");
                                        alternate_email = alternate_email.replace("null", "-");
                                        pojoHomePojo.setAlternate_email(alternate_email);
                                        website = jsonObject1.getString("website");
                                        website = website.replace("null", "-");
                                        pojoHomePojo.setWebsite(website);
                                        JSONArray roduct_group = jsonObject1.getJSONArray("product_group");

                                        for (int t = 0; t < roduct_group.length(); t++) {
                                            PlastFocusModelClass pojo = new PlastFocusModelClass();

                                            if (!productnamelist.contains(roduct_group.getString(t))) {
                                                productnamelist.add(roduct_group.getString(t));
                                                pojo.setProductnames(roduct_group.getString(t));
                                                dataBaseHandler.AddProductNames(pojo);
                                            }

                                        }

                                        pojoHomePojo.setProduct_group(roduct_group.toString());
                                        company_profile = jsonObject1.getString("company_profile");
                                        company_profile.replace("null", "-");
                                        pojoHomePojo.setCompany_profile(company_profile);
                                        hall = jsonObject1.getString("hall");
                                        hall = hall.replace("null", "-");
                                        pojoHomePojo.setHall(hall);
                                        stall_number = jsonObject1.getString("stall_number");
                                        stall_number = stall_number.replace("null", "-");
                                        pojoHomePojo.setStall_number(stall_number);

                                        PlastFocusModelClass productdata = new PlastFocusModelClass();

                                        productdata.setCompany_name(company_name);
                                        productdata.setCity(city);
                                        productdata.setProduct_group(roduct_group.toString());
                                        productdata.setState(state);
                                        productdata.setCountry(country);
                                        productdata.setHall(hall);
                                        productdata.setStall_number(stall_number);
                                        dataBaseHandler.AddProductData(productdata);
                                        cord_x = jsonObject1.getString("cord_x");
                                        pojoHomePojo.setCord_x(cord_x);
                                        cord_y = jsonObject1.getString("cord_y");
                                        pojoHomePojo.setCord_y(cord_y);

                                        dataBaseHandler.InserExhibitorData(pojoHomePojo);
                                    }
                                    pd.dismiss();
                                    editTextsearch.clearFocus();
                                    editTextsearch.setQuery("", false);
                                    azExhibitorlistAdapter.getFilter().filter("");


                                    getDatafromDB();
                                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                    Log.d("ExhibitorData", "Done");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        t.start();


                    }
                });


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();

                String body = null;

                if (error.networkResponse.data != null) {
                    try {
                        body = new String(error.networkResponse.data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    JSONObject jsonObject1 = new JSONObject(body.toString());
                    String message = jsonObject1.getString("message");
                    VolleyErrorHelper.errorcoderesponce(message, getActivity());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.d("test", "onErrorResponse: Error " + error.getMessage());


            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    @SuppressLint("NotifyDataSetChanged")
    private void getDatafromDB() {


        new LoadeExhibitordata().execute();

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

    @Override
    public void onStart() {
        super.onStart();

        editTextsearch.setQueryHint(filterkey);
        if (filterkey.length() == 0) {
            filterkey = "";
        }
    }

    public class LoadeExhibitordata extends AsyncTask<Void, Void, String> {

        // This method runs on the background thread
        @Override
        protected String doInBackground(Void... voids) {

            hideKeyboard(getActivity());
            DataBaseHandler dataBaseHandler = new DataBaseHandler(getActivity());
            mArrayCallRList = dataBaseHandler.getExhibitorData();
            // Return the result
            return "Background task completed";
        }

        // This method runs on the UI thread after doInBackground completes
        @Override
        protected void onPostExecute(String result) {
            if (mArrayCallRList.size() > 0) {
                azExhibitorlistAdapter = new ExhibitorMainAdapter(getActivity(), mArrayCallRList);
                recyclerView.setAdapter(azExhibitorlistAdapter);
                azExhibitorlistAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
            }


            editTextsearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    editTextsearch.setIconified(false);
                    editTextsearch.setQueryHint("Search by Comapny,City,Hall,Country");
                }
            });

            editTextsearch.setQueryHint("Search by Comapny,City,Hall,Country");

            editTextsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterkey = newText;
                    if (newText.length() == 0) {
                        filterkey = "";
                        editTextsearch.setQueryHint("");
                    }
                    if (azExhibitorlistAdapter != null)
                        azExhibitorlistAdapter.getFilter().filter(filterkey);
                    return true;
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isRemoving()) {
            getActivity().finish();
            // onBackPressed()
        }
    }
}






