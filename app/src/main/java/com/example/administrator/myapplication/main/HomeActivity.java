package com.example.administrator.myapplication.main;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.TestXUtils.inteface.MyCallBack;
import com.example.administrator.myapplication.TestXUtils.util.XUtil;

import utils.JsonUtil;
import utils.MToastUtils;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "开启Demo之门", Snackbar.LENGTH_LONG)
                        .setAction("开始", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(HomeActivity.this, DemoActivity.class);
                                startActivity(intent);
                            }
                        }).show();
            }
        });

        XUtil.Get("http://218.205.115.244:18088/yiqi/api/collect/list/137", null, new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                //可以根据公司的需求进行统一的请求成功的逻辑处理
                GsonFormatTest bean = JsonUtil.Json2T(result, GsonFormatTest.class);
                MToastUtils.showMsg(bean.getModel().getList().size() + "", HomeActivity.this);
                Log.e("lyn", bean.getModel().getList().size() + "");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //可以根据公司的需求进行统一的请求网络失败的逻辑处理
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
