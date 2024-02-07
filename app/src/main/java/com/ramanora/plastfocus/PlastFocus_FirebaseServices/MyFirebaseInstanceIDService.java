package com.ramanora.plastfocus.PlastFocus_FirebaseServices;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;




/**
 * Created by abc on 12/7/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    static String refreshedToken;
    private static final String TAG = "test";


    @Override
    public void onTokenRefresh() {

        //Getting registration token
        refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat

        //calling the method store token and passing token
        storeToken(refreshedToken);
    }

    private void storeToken(String token) {
        //we will save the token in sharedpreferences later
     //   Log.d(TAG, "Refreshed token: " + token);

     // String as =  AppPreferences.savePreferences(MyFirebaseInstanceIDService.this, AppPreferences.KEY_TOKEN, refreshedToken);
       // Log.d(TAG, "Refreshed token: " + as);

    }
}