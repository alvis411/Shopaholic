package com.quypham.shopaholic.utils;

import android.graphics.Bitmap;
import android.util.LruCache;
/**
 * Created by QuyPP1 on 7/9/2015.
 */
public class MemoryCache {
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final int cacheSize = maxMemory / 8;

    private LruCache<String,Bitmap> memoryCache;

    public MemoryCache(){
        memoryCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public boolean putImage(String key,Bitmap bitmap){
        if(memoryCache!=null && memoryCache.get(key)==null){
            memoryCache.put(key,bitmap);
            return true;
        }
        return false;
    }

    public Bitmap getImage(String key){
        if(memoryCache!=null){
            return memoryCache.get(key);
        }
        return null;
    }

    public boolean clearCache(){
        if(memoryCache!=null){
            return true;
        }
        return false;
    }
}
