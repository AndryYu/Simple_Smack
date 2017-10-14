package com.andryyu.smack.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andryyu.smack.R;
import com.andryyu.smack.ui.fragment.ContactsFragment;
import com.andryyu.smack.ui.fragment.DiscoverFragment;
import com.andryyu.smack.ui.fragment.MeFragment;
import com.andryyu.smack.ui.fragment.RecentMsgFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private int image[] = {R.drawable.tab_message, R.drawable.tab_contacts, R.drawable.tab_dicovery, R.drawable.tab_me};
    private String text[] = {"微信", "通讯录", "发现", "我"};
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initViews();
    }

    /**
     * <p>initData</p>
     */
    private void initData(){
        fragmentList = new ArrayList<>();
        fragmentList.add(new RecentMsgFragment());
        fragmentList.add(new ContactsFragment());
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new MeFragment());
    }

    /**
     * <p>initViews</p>
     */
    private void initViews() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        vpMain.setOffscreenPageLimit(image.length);
        vpMain.setAdapter(adapter);
        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.setupWithViewPager(vpMain);
        for (int i = 0; i < image.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            View view = getLayoutInflater().inflate(R.layout.tab, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.tab_iv);
            TextView textView = (TextView) view.findViewById(R.id.tab_tv);
            imageView.setImageResource(image[i]);
            textView.setText(text[i]);
            //设置自定义的tab布局
            tab.setCustomView(view);
        }
        tabLayout.setOnTabSelectedListener(this);
        onTabSelected(tabLayout.getTabAt(0));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        vpMain.setCurrentItem(position, false);
        TextView tv = (TextView) tab.getCustomView().findViewById(R.id.tab_tv);
        tv.setTextColor(Color.argb(255, 69, 192, 26));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        TextView tv = (TextView) tab.getCustomView().findViewById(R.id.tab_tv);
        tv.setTextColor(Color.argb(255, 153, 153, 153));
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }
}
