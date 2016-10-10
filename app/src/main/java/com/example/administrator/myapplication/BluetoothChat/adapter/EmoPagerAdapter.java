package com.example.administrator.myapplication.BluetoothChat.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 配合ChatActivity界面中，当按下界面底部的表情按钮后会弹出加载表情的布局。
 * 布局中使用一个ViewPager管理两个呈现表情的界面
 * 该适配器就是配合ViewPager，加载两个界面的
 */
public class EmoPagerAdapter extends PagerAdapter {
    List<View> views;

    public EmoPagerAdapter(List<View> views) {
        super();
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

}
