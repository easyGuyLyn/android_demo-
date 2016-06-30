package com.example.administrator.myapplication.topfloat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.ultraPullToRefresh.ultra.PtrClassicFrameLayout;
import com.example.administrator.myapplication.ultraPullToRefresh.ultra.PtrDefaultHandler;
import com.example.administrator.myapplication.ultraPullToRefresh.ultra.PtrFrameLayout;
import com.example.administrator.myapplication.ultraPullToRefresh.ultra.PtrHandler;
import com.example.administrator.myapplication.ultraPullToRefresh.ultra.header.MaterialHeader;

import utils.CommonUtils;
import utils.MToastUtils;


public class TopFloatActivity extends AppCompatActivity implements MyScrollView.OnScrollListener {
    private EditText search_edit;
    private MyScrollView myScrollView;
    private int searchLayoutTop;
    protected PtrClassicFrameLayout mPtrFrameLayout;
    LinearLayout search01, search02;
    RelativeLayout rlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_float);
        //初始化控件
        init();
    }

    private void init() {
        mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.material_style_ptr_frame_fragment_main);
        search_edit = (EditText) findViewById(R.id.search_edit);
        myScrollView = (MyScrollView) findViewById(R.id.myScrollView);
        search01 = (LinearLayout) findViewById(R.id.search01);
        search02 = (LinearLayout) findViewById(R.id.search02);
        rlayout = (RelativeLayout) findViewById(R.id.rlayout);
        myScrollView.setOnScrollListener(this);
        initMaterial();
    }

    public void initMaterial() {
        // header
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, CommonUtils.dip2px(this, 15), 0, CommonUtils.dip2px(this, 10));
        header.setPtrFrameLayout(mPtrFrameLayout);
        mPtrFrameLayout.setDurationToClose(100);
        mPtrFrameLayout.setPinContent(false);
        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setDurationToCloseHeader(1500);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.setLastUpdateTimeRelateObject(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            searchLayoutTop = rlayout.getBottom();//获取searchLayout的顶部位置
        }
    }

    //监听滚动Y值变化，通过addView和removeView来实现悬停效果
    @Override
    public void onScroll(int scrollY) {
        if (scrollY >= searchLayoutTop) {
            if (search_edit.getParent() != search01) {
                search02.removeView(search_edit);
                search01.addView(search_edit);
            }
        } else {
            if (search_edit.getParent() != search02) {
                search01.removeView(search_edit);
                search02.addView(search_edit);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh(true);
            }
        }, 100);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                MToastUtils.showMsg("刷新完毕", TopFloatActivity.this);
                long delay = 0;
                mPtrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrameLayout.refreshComplete();
                    }
                }, delay);
            }
        });
    }
}
