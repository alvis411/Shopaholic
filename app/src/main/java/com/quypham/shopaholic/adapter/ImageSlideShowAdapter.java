package com.quypham.shopaholic.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.quypham.shopaholic.R;

/**
 * Created by Alvis on 7/5/2015.
 */
public class ImageSlideShowAdapter extends PagerAdapter {

    private int[] mImageResources={R.drawable.landing_page,R.drawable.landing_page3,R.drawable.landing_page4};
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ImageSlideShowAdapter(Context context){
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.landing_pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageLandingPage);
        imageView.setImageResource(mImageResources[position]);

        container.addView(itemView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return mImageResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }
}
