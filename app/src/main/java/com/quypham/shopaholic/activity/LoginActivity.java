package com.quypham.shopaholic.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.quypham.shopaholic.R;
import com.quypham.shopaholic.Shopaholic;
import com.quypham.shopaholic.adapter.ImageSlideShowAdapter;
import com.quypham.shopaholic.runnable.ImageSlideShowRunnable;

import java.util.Arrays;


public class LoginActivity extends BaseActivity implements FacebookCallback<LoginResult>, View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    public enum LoginMethod {
        FACEBOOK, GMAIL, EMAIL;
    }
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;
    private ImageSlideShowAdapter mSlideShowAdapter;
    private Button facebookLogin,googleLogin;
    private ViewPager mViewPager;
    private Handler mHandler = new Handler();
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private AccessTokenTracker tokenTracker;
    private GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;
    /* A flag indicating that a PendingIntent is in progress and prevents
       * us from starting further intents.
       */
    private boolean mIntentInProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        FacebookSdk.sdkInitialize(this);
        //init Facebook component for login
        initFacebookComponent();
        initGoogleComponent();
        initSlideShow();
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
    protected void onDestroy() {
        super.onDestroy();
        //stop tracking profile info and access token of facebook
        profileTracker.stopTracking();
        tokenTracker.stopTracking();
        //
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //send result to facebook manager
        callbackManager.onActivityResult(requestCode, resultCode, data);
        //
        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    private void initSlideShow() {
        mSlideShowAdapter = new ImageSlideShowAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.landingPager);
        mViewPager.setAdapter(mSlideShowAdapter);
        mHandler.postDelayed(new ImageSlideShowRunnable(mViewPager), 5000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.facebookLoginButton:
                AccessToken token = AccessToken.getCurrentAccessToken();
                if (token == null || token.isExpired()) {
                    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
                } else {
                    if (Shopaholic.getShopaholic().getFacebookProfile() == null) {
                        Shopaholic.getShopaholic().setFacebookProfile(Profile.getCurrentProfile());
                    }
                    startMainActivity(LoginMethod.FACEBOOK);
                }
                break;
            case R.id.googleLoginButton:
                if (!mGoogleApiClient.isConnecting()) {
                    mGoogleApiClient.connect();
                }
        }
    }

    public void startMainActivity(LoginMethod method) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.putExtra("login_method", method);
        startActivity(mainIntent);
    }

    public void initGoogleComponent() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        googleLogin = (Button) findViewById(R.id.googleLoginButton);
        googleLogin.setOnClickListener(this);
    }
    /**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mIntentInProgress && connectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(connectionResult.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    public void initFacebookComponent() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, this);
        tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken1) {

            }
        };
        tokenTracker.startTracking();
        facebookLogin = (Button) findViewById(R.id.facebookLoginButton);
        if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired()) {
            facebookLogin.setText(R.string.continue_facebook);
        } else {
            facebookLogin.setText(R.string.login_facebook);
        }
        facebookLogin.setOnClickListener(this);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null) {
                    Shopaholic.getShopaholic().setFacebookProfile(currentProfile);
                }
            }
        };
        startMainActivity(LoginMethod.FACEBOOK);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException e) {

    }

}
