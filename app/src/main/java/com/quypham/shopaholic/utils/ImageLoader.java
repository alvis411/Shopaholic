package com.quypham.shopaholic.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import com.quypham.shopaholic.Shopaholic;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;


/**
 * Created by QuyPP1 on 7/9/2015.
 */
public class ImageLoader {
    private Context mContext;
    //handler to display images in UI thread
    Handler handler = new Handler();
    private Map<ImageView,String> imageCache = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    public ImageLoader(Context context) {
        this.mContext = context;
    }

    public void displayImage(ImageView imageView){
        if(imageCache.get(imageView)==null){
            // download bitmap, if necessary
            // decode bitmap
        }else{
            //use existence decoded bitmap
        }

    }

    class ImageDecode implements Runnable {
        private int resourceId;
        public ImageDecode(int resourceId) {
            this.resourceId = resourceId;
        }

        @Override
        public void run() {
            Bitmap decodedBitmap = BitmapUtilities.decodeBitmapFromResource(mContext.getResources(), resourceId,
                    Shopaholic.getShopaholic().getDisplayMetric().widthPixels,
                    Shopaholic.getShopaholic().getDisplayMetric().heightPixels);
            handler.post(new ImageDisplay(decodedBitmap));
        }
    }

    class ImageDisplay implements Runnable{
        private Bitmap imageBitmap;
        public ImageDisplay(Bitmap imageBitmap){
            this.imageBitmap = imageBitmap;
        }

        @Override
        public void run() {

        }
    }
}

