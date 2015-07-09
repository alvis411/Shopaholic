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

import java.lang.ref.WeakReference;

/**
 * Created by Alvis on 7/5/2015.
 */
public class ImageSlideShowAdapter extends PagerAdapter {

    private int[] mImageResources = {R.drawable.landing_page, R.drawable.landing_page3, R.drawable.landing_page4};
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
//        DecodeBitmapTask decodeTask = new DecodeBitmapTask(position);
//        decodeTask.execute();
        BitMapWorkerTask bitMapWorkerTask = new BitMapWorkerTask(imageView);
        bitMapWorkerTask.execute(mImageResources[position]);
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

    class BitMapWorkerTask extends AsyncTask<Integer,Void,Bitmap>{
        private final WeakReference<ImageView> imageViewWeakReference;

        public BitMapWorkerTask(ImageView imageView){
            imageViewWeakReference =new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            int resourceId = params[0];
            Bitmap decodedBitmap = BitmapDecode.decodeBitmapFromResource(mContext.getResources(), resourceId,
                    Shopaholic.getShopaholic().getDisplayMetric().widthPixels,
                    Shopaholic.getShopaholic().getDisplayMetric().heightPixels);
            return decodedBitmap;
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
