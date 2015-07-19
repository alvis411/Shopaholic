package com.quypham.shopaholic.runnable;

import android.os.Handler;
import android.support.v4.view.ViewPager;

/**
 * Created by Alvis on 7/8/2015.
 */
public class ImageSlideShowRunnable implements Runnable {

    private ViewPager mViewPager;
    private final int IMAGE_DELAY = 5000;
    private Handler mHandler = new Handler();

    public ImageSlideShowRunnable(ViewPager pager){
        mViewPager = pager;
    }

    @Override
    public void run() {
        if(mViewPager.getCurrentItem() >= mViewPager.getAdapter().getCount()-1){
            mViewPager.setCurrentItem(0);
        }else{
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
        }
        mHandler.postDelayed(this,IMAGE_DELAY);

    }
}
