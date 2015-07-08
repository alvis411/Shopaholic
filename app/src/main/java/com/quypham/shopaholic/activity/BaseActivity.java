package com.quypham.shopaholic.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.quypham.shopaholic.Shopaholic;

/**
 * Created by Alvis on 7/8/2015.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Shopaholic.getShopaholic().getDisplayMetric() == null){
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            Shopaholic.getShopaholic().setDisplayMetric(metrics);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
