package com.example6767gh.mytestauthentication;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import cn.pedant.SweetAlert.SweetAlertDialog;

//import cn.pedant.SweetAlert.SweetAlertDialog;

public class Utils {

    static  SweetAlertDialog pDialog;

    public static void showLoading(Context context) {
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        Log.i("elec","sadasda");
    }

    public static void hideLoading() {
        if (pDialog != null)
            pDialog.dismiss();
        Log.i("elec","sadasda");
    }

}
