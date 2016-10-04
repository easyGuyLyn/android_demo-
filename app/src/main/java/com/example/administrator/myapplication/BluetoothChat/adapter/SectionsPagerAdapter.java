package com.example.administrator.myapplication.BluetoothChat.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.myapplication.BluetoothChat.fragment.OtherFragment;
import com.example.administrator.myapplication.BluetoothChat.fragment.SingleChatFragment;

/**
 * Created by Administrator on 2016/10/4.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new SingleChatFragment();

            }
            case 1: {
                return new OtherFragment();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "IM";
            case 1:
                return "开发中...";
        }
        return null;
    }
}
