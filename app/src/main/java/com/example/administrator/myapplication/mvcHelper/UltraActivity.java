/*
Copyright 2015 shizhefei（LuckyJayce）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.example.administrator.myapplication.mvcHelper;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;


import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mvcHelper.mvc.MVCHelper;
import com.example.administrator.myapplication.mvcHelper.mvc.MVCUltraHelper;
import com.example.administrator.myapplication.ultraPullToRefresh.ultra.PtrClassicFrameLayout;
import com.example.administrator.myapplication.ultraPullToRefresh.ultra.PtrFrameLayout;
import com.example.administrator.myapplication.ultraPullToRefresh.ultra.header.MaterialHeader;

import java.util.List;


/**
 * 测试下拉刷新组件，MVCUltraHelper
 *
 * @author LuckyJayce
 */
public class UltraActivity extends AppCompatActivity {
    private MVCHelper<List<Book>> mvcHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultra);

		/*
         * 配置PtrClassicFrameLayout的刷新样式
		 */
        PtrClassicFrameLayout mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        final MaterialHeader header = new MaterialHeader(getApplicationContext());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, dipToPix(getApplicationContext(), 15), 0, dipToPix(getApplicationContext(), 10));
        header.setPtrFrameLayout(mPtrFrameLayout);
        mPtrFrameLayout.setLoadingMinTime(800);
        mPtrFrameLayout.setDurationToCloseHeader(800);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);

        mvcHelper = new MVCUltraHelper<List<Book>>(mPtrFrameLayout);
        // 设置数据源
        mvcHelper.setDataSource(new BooksDataSource());
        // 设置适配器
        mvcHelper.setAdapter(new BooksAdapter(this));
        // 加载数据
        mvcHelper.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放资源
        mvcHelper.destory();
    }

    public void onClickBack(View view) {
        finish();
    }

    /**
     * 根据dip值转化成px值
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dipToPix(Context context, int dip) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return size;
    }

}
