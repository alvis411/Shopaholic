package com.quypham.shopaholic.activity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.quypham.shopaholic.R;
import com.quypham.shopaholic.Shopaholic;

/**
 * Created by Alvis on 7/26/2015.
 */
public class MainActivity extends BaseActivity {
    private TextView userProfileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userProfileImage = (TextView) findViewById(R.id.detail);
        if(Shopaholic.getShopaholic().getFacebookProfile()!=null){
//            userProfileImage.setImageBitmap(Shopaholic.getShopaholic().getFacebookProfile().getProfilePictureUri(userProfileImage.getMeasuredWidth(),userProfileImage.getMeasuredHeight()));
            userProfileImage.setText(Shopaholic.getShopaholic().getFacebookProfile().getName());
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
