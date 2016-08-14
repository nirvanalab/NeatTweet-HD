package com.codepath.apps.neattweet.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.codepath.apps.neattweet.Adapter.UserDetailFragmentPagerAdapter;
import com.codepath.apps.neattweet.Manager.TwitterManager;
import com.codepath.apps.neattweet.Models.Banner;
import com.codepath.apps.neattweet.Models.BannerResponseHandler;
import com.codepath.apps.neattweet.Models.User;
import com.codepath.apps.neattweet.R;
import com.codepath.apps.neattweet.Utility.UtilityManager;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class UserDetailActivity extends AppCompatActivity {

    User user;
    @BindView(R.id.ivProfilePic) ImageView ivProfilePic;
    @BindView(R.id.tvFullname) TextView tvFullname;
    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.tvTagline) TextView tvTagline;
    @BindView(R.id.tvLocation) TextView tvLocation;
    @BindView(R.id.tvFollowers) TextView tvFollowers;
    @BindView(R.id.tvFollowing)TextView tvFollowing;
    @BindView(R.id.tabsDetail) PagerSlidingTabStrip tabsDetail;
    @BindView(R.id.viewPagerDetail) ViewPager viewPagerDetail ;
    @BindView(R.id.ivBackdrop)ImageView ivBackdrop;
    @BindView(R.id.main_content)CoordinatorLayout rootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));
        boolean shouldIncludeRetweets = getIntent().getBooleanExtra("shouldIncludeRetweets",false);
         ;
        Glide.with(getApplicationContext()).load(user.getProfileImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(),5,5))
                .into(ivProfilePic);


        String screenName = "@"+user.getScreenName();
        tvUsername.setText(screenName);
        tvFullname.setText(user.getName());
        tvTagline.setText(user.getDescription());
        tvLocation.setText(user.getLocation());
        tvFollowers.setText(user.getFollowersCount());
        tvFollowing.setText(user.getFriendsCount());

        setupFont();


        viewPagerDetail.setAdapter(new UserDetailFragmentPagerAdapter(getSupportFragmentManager(),user.getId(),shouldIncludeRetweets));
        viewPagerDetail.setPageTransformer(true, new RotateUpTransformer());
        tabsDetail.setViewPager(viewPagerDetail);

        //get Banner
        TwitterManager.getSharedInstance().getBanner(user.getId(), new BannerResponseHandler() {
            @Override
            public void bannerResults(boolean isSuccess, Banner banner) {
                if ( isSuccess) {
                    Glide.with(getApplicationContext()).load(banner.getUrl())
                            .into(ivBackdrop);
                }
            }
        });

//        CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle(user.getName());
    }

    private void setupFont(){
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/roboto_bold.ttf");
        Typeface regularFont = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");
        tvFullname.setTypeface(boldFont);
        tvFollowers.setTypeface(boldFont);
        tvFollowing.setTypeface(boldFont);
        tvUsername.setTypeface(regularFont);
        tvLocation.setTypeface(regularFont);
        tvTagline.setTypeface(regularFont);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //check if internet is there
        if (!UtilityManager.getSharedInstance().isNetworkAvailable(this)) {
            Snackbar.make(rootLayout, R.string.no_internet_message, Snackbar.LENGTH_INDEFINITE).show();
        }

    }

    @Override
    protected void onPause() {

        //overridePendingTransition(R.anim.hold, R.anim.rotate_in);
        super.onPause();
    }




}
