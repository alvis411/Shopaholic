package com.quypham.shopaholic.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Alvis on 7/25/2015.
 */
public class MemoryCache {
    private LruCache<String,Bitmap> mCache;
    private static MemoryCache instance;
    private static final int MAX_MEMORY = (int) (Runtime.getRuntime().maxMemory()/1024);
    private static final int MAX_CACHE_SIZE = MAX_MEMORY / 4;

    private MemoryCache(){
        if(mCache == null){
            mCache = new LruCache<String,Bitmap>(MAX_CACHE_SIZE){
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // The cache size will be measured in kilobytes rather than
                    // number of items.
                    return bitmap.getByteCount() / 1024;
                }
            };
        }
    };

    public static synchronized MemoryCache getInstance(){
        if(instance == null){
            instance = new MemoryCache();
        }

        return instance;
    }

    public boolean addBitmapToCache(String key,Bitmap bitmap){
        if(mCache.get(key) == null){
            mCache.put(key,bitmap);
            return true;
        }else{
            return false;
        }
    }

    public Bitmap getBitmapFromCache(String key){
        if(key == null){
            return null;
        }
        return mCache.get(key);
    }
}
