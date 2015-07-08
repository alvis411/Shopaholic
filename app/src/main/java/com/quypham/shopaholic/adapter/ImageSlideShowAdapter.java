package com.quypham.shopaholic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.quypham.shopaholic.R;
import com.quypham.shopaholic.Shopaholic;
import com.quypham.shopaholic.utils.BitmapDecode;

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
        DecodeBitmapTask decodeTask = new DecodeBitmapTask(position);
        decodeTask.execute();
        imageView.setImageBitmap(imageBitmap);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImageResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private class DecodeBitmapTask extends AsyncTask<Integer,Void,Bitmap>{
        private Bitmap result;
        private int position;
        public DecodeBitmapTask(int position){
            this.position = position;
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            result   = BitmapDecode.decodeBitmapFromResource(mContext.getResources(),mImageResources[position],
                    Shopaholic.getShopaholic().getDisplayMetric().widthPixels,
                    Shopaholic.getShopaholic().getDisplayMetric().heightPixels);
            return result;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }
}
