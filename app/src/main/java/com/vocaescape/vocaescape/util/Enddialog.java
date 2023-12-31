package com.vocaescape.vocaescape.util;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.vocaescape.vocaescape.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Enddialog extends Dialog {
    private Activity mContext;
    private UnifiedNativeAd nativeAd;
    //private UnifiedNativeAdView adView;
    private ActivityManager am = ActivityManager.getInstance();
    public @BindView(R.id.end_linear_adplace)    FrameLayout end_linear_adplace;
    public @BindView(R.id.close_btY)    FrameLayout close_btY;
    public @BindView(R.id.close_btN)    FrameLayout close_btN;
    public Enddialog(Activity context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_exit);
        ButterKnife.bind(this);
       /* if(adView!=null && end_linear_adplace.getChildCount()>0) {
            end_linear_adplace.removeAllViews();
            end_linear_adplace.addView(adView);
        }*/
        MobileAds.initialize(mContext);
        AdLoader.Builder builder = new AdLoader.Builder(mContext, mContext.getString(R.string.nativead_test));

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                // You must call destroy on old ads when you are done with them,
                // otherwise you will have a memory leak.
                if (nativeAd != null) {
                    nativeAd.destroy();
                }


                nativeAd = unifiedNativeAd;
                //UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                   //     .inflate(R.layout.ad_unified_dialog, null);
             /*   findViewById(R.id.close_btN).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,"No", Toast.LENGTH_LONG).show();
                        dismiss();
                    }
                });
                findViewById(R.id.close_btY).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,"Yes", Toast.LENGTH_LONG).show();
                        dismiss();
                        Intent i = new Intent(mContext, SplashEndActivity.class);
                        mContext.startActivity(i);
                        mContext.overridePendingTransition(R.anim.slide_inleft,R.anim.slide_outright);
                        mContext.finish();
                        am.finishAllActivity();
                    }
                });*/
//                populateUnifiedNativeAdView(unifiedNativeAd,adView);
//                end_linear_adplace.removeAllViews();
//                end_linear_adplace.addView(adView);

            }
        });

//        AdLoader adLoader = builder.withAdListener(new AdListener() {
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                Toast.makeText(getContext(), "Failed to load native ad: "
//                        + errorCode, Toast.LENGTH_SHORT).show();
//            }
//        }).build();
//
//        //adLoader.loadAd(new AdRequest.Builder().addTestDevice("F225B75A37119EE77E3DEAB3DC23EB31").build());
//        adLoader.loadAd(new AdRequest.Builder().build());


    }
/*
    public void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {

        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.
        MediaView mediaView = adView.findViewById(R.id.end_ad_media);
        adView.setMediaView(mediaView);
        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.end_ad_headline));
        adView.setBodyView(adView.findViewById(R.id.end_ad_body));
        //adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.end_ad_app_icon));
        //adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.end_ad_stars));
        //adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.end_ad_advertiser));
        adView.setCallToActionView(findViewById(R.id.end_ad_call_to_action));
        // The headline is guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd);
      //  this.adView = adView;
    }*/
}
