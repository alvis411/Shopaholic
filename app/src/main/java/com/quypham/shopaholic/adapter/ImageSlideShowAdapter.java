package com.quypham.shopaholic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.quypham.shopaholic.R;
import com.quypham.shopaholic.Shopaholic;
import com.quypham.shopaholic.utils.MemoryCache;
import com.quypham.shopaholic.utils.ScalingUtilities;

import java.lang.ref.WeakReference;

/**
 * Created by Alvis on 7/5/2015.
 */
public class ImageSlideShowAdapter extends PagerAdapter {

    private int[] mImageResources = {R.drawable.landing_page6, R.drawable.landing_page, R.drawable.landing_page3};
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ImageSlideShowAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.landing_pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageLandingPage);
        final Bitmap cacheBitmap = MemoryCache.getInstance().getBitmapFromCache(String.valueOf(mImageResources[position]));
        if(cacheBitmap!=null){
            Log.d("Cache","Get bitmap from cache");
            imageView.setImageBitmap(cacheBitmap);
        }else{
            Log.d("Cache","Get bitmap for first time");
            setFitBitmap(imageView, mImageResources[position]);
        }
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

    private void setFitBitmap(ImageView img,int resourceId){
        BitMapWorkerTask bitMapWorkerTask = new BitMapWorkerTask(img);
        bitMapWorkerTask.execute(resourceId);
    }

    class BitMapWorkerTask extends AsyncTask<Integer,Void,Bitmap>{
        private final WeakReference<ImageView> imageViewWeakReference;

        public BitMapWorkerTask(ImageView imageView){
            imageViewWeakReference =new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            int resourceId = params[0];
            int mDstWidth =  Shopaholic.getShopaholic().getDisplayMetric().widthPixels;
            int mDstHeight = Shopaholic.getShopaholic().getDisplayMetric().heightPixels;
            //Decode image
            Bitmap unscaledBitmap = ScalingUtilities.decodeResource(mContext.getResources(), resourceId,
                    mDstWidth, mDstHeight, ScalingUtilities.ScalingLogic.FIT);

            //Scale image
            Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, mDstWidth,
                    mDstHeight, ScalingUtilities.ScalingLogic.FIT);
            unscaledBitmap.recycle();
            //add bitmap to cache
            MemoryCache.getInstance().addBitmapToCache(String.valueOf(resourceId),scaledBitmap);
            return scaledBitmap;
        }
        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if(imageViewWeakReference!=null && bitmap!=null){
                final ImageView imageView = imageViewWeakReference.get();
                if(imageView!=null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}
