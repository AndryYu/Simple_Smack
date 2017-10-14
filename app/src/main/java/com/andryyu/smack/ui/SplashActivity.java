package com.andryyu.smack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.andryyu.smack.R;
import com.andryyu.smack.ui.splash.TranslateFragment;
import com.andryyu.smack.ui.splash.WelcompagerTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.vp_splash)
    ViewPager vpSplash;
    private int[] layouts = {
            R.layout.splash_welcome1,
            R.layout.splash_welcome2,
            R.layout.splash_welcome3
    };
    private WelcompagerTransformer transformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        initViews();
        initData();
    }

    /**
     * <p>initViews</p>
     */
    private void initViews(){

    }

    /**
     * <p>initData</p>
     */
    private void initData(){
        WelcomePagerAdapter adapter = new WelcomePagerAdapter(getSupportFragmentManager());
        System.out.println("offset:"+vpSplash.getOffscreenPageLimit());
        vpSplash.setOffscreenPageLimit(3);
        vpSplash.setAdapter(adapter);

        transformer = new WelcompagerTransformer();
        vpSplash.setPageTransformer(true, transformer);
        vpSplash.setOnPageChangeListener(transformer);
    }


    class WelcomePagerAdapter extends FragmentPagerAdapter {

        public WelcomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new TranslateFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("layoutId", layouts[position]);
            bundle.putInt("pageIndex", position);
            f.setArguments(bundle );
            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
