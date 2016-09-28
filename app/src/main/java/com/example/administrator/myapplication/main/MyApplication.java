package com.example.administrator.myapplication.main;

import org.xutils.x;

import android.app.Application;

import com.example.administrator.myapplication.mvcHelper.mvc.MVCHelper;

import utils.CrashHandler;
import utils.view.MyLoadViewFactory;

public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化XUtils3
        x.Ext.init(this);
        //设置debug模式
        x.Ext.setDebug(true);
        // 设置LoadView的factory，用于创建使用者自定义的加载失败，加载中，加载更多等布局,写法参照DeFaultLoadViewFactory
        MVCHelper.setLoadViewFractory(new MyLoadViewFactory());
        //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
}
