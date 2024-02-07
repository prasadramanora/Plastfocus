package com.ramanora.plastfocus.PlastFocus_Utils;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ramanora.plastfocus.R;

import java.util.HashMap;
import java.util.Map;



public class VolleyErrorHelper {
    /**
     * Returns appropriate message which is to be displayed to the user
     * against the specified error object.
     *
     * @param error
     * @param context
     * @return
     */

    public static AlertDialog.Builder builder;

    public static String getMessage(Object error, Context context) {
        if (error instanceof TimeoutError) {
            return context.getResources().getString(R.string.generic_server_down);
        } else if (isServerProblem(error)) {
            return handleServerError(error, context);
        } else if (isNetworkProblem(error)) {
            return context.getResources().getString(R.string.no_internet);
        }
        return context.getResources().getString(R.string.generic_error);
    }

    public static void errorcoderesponce(String tv_message, Context context) {



        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogerrormessage);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView text = (TextView) dialog.findViewById(R.id.tv_message);
        text.setText(tv_message);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_cancel);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    /**
     * Determines whether the error is related to network
     *
     * @param error
     * @return
     */
    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    /**
     * Determines whether the error is related to server
     *
     * @param error
     * @return
     */
    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }

    /**
     * Handles the server error, tries to determine whether to show a stock message or to
     * show a message retrieved from the server.
     *
     * @param err
     * @param context
     * @return
     */


    private static String handleServerError(Object err, Context context) {
        VolleyError error = (VolleyError) err;

        NetworkResponse response = error.networkResponse;
        Log.e("ResponceCode",response.statusCode+"");
        switch (response.statusCode) {
            case 200:
                Log.e("ResponceCode",response.statusCode+"");
               // Toast.makeText(context, "200", Toast.LENGTH_SHORT).show();
                errorcoderesponce("yes",context);
                return context.getResources().getString(R.string.user_exists);

            case 404:
               // Toast.makeText(context, response.statusCode+"", Toast.LENGTH_SHORT).show();

               // return context.getResources().getString(R.string.user_exists);
            case 400:
              //  Toast.makeText(context, response.statusCode+"", Toast.LENGTH_SHORT).show();
                break;

            case 405:
               // Toast.makeText(context, response.statusCode+"", Toast.LENGTH_SHORT).show();
                break;
            case 401:
              //  Toast.makeText(context, response.statusCode+"", Toast.LENGTH_SHORT).show();
                try {
                    // server might return error like this { "error": "Some error occured" }
                    // Use "Gson" to parse the result
                    HashMap<String, String> result = new Gson().fromJson(new String(response.data),
                            new TypeToken<Map<String, String>>() {
                            }.getType());

                    if (result != null && result.containsKey("error")) {
                        return result.get("error");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // invalid request
                return error.getMessage() != null ? error.getMessage() : context.getResources().getString(R.string.generic_error);

            default:
               // Toast.makeText(context, response.statusCode+"", Toast.LENGTH_SHORT).show();
                return context.getResources().getString(R.string.generic_server_down);
        }
        return context.getResources().getString(R.string.generic_error);
    }
}
