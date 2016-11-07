package com.example.user.sekety;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TabView extends FragmentActivity implements ActionBar.TabListener {
    ActionBar actionbar;
    ViewPager viewpager;
    FragmentPagerAdapter ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view);
        viewpager=(ViewPager)findViewById(R.id.pager);
        ft=new FragmentPageAdapter(getSupportFragmentManager());
        actionbar=getActionBar();
        viewpager.setAdapter(ft);
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionbar.addTab(actionbar.newTab().setText("Timeline"));
        actionbar.addTab(actionbar.newTab().setText("Profile"));
        actionbar.addTab(actionbar.newTab().setText("Search Places"));
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}