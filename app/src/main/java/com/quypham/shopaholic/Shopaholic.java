package com.quypham.shopaholic;

import android.app.Application;
import android.util.DisplayMetrics;

/**
 * Created by Alvis on 7/8/2015.
 */
public class Shopaholic extends Application{

    private static Shopaholic mInstance;
    private static DisplayMetrics displayMetrics;

    public static synchronized Shopaholic getShopaholic(){
        if(mInstance == null){
            mInstance = new Shopaholic();
        }
        return mInstance;
    }


    public DisplayMetrics getDisplayMetric(){
        return displayMetrics;
    }

    public void setDisplayMetric(DisplayMetrics metrics){
        displayMetrics = metrics;
    }
}
