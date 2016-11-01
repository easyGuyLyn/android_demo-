package utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * 双击退出
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TLogUtils.i("actvity_status", "onCreate");
        setContentView();
        getWindow().setBackgroundDrawable(null);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        initData();
        setListener();
    }

    /**
     * 设置布局文件
     */
    public abstract void setContentView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 设置控件的监听
     */
    public abstract void setListener();


    @Override
    protected void onRestart() {
        super.onRestart();
        TLogUtils.i("actvity_status", "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        TLogUtils.i("actvity_status", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        TLogUtils.i("actvity_status", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        TLogUtils.i("actvity_status", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        TLogUtils.i("actvity_status", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TLogUtils.i("actvity_status", "onDestroy");
        ButterKnife.unbind(this);
        AppManager.getAppManager().finishActivity(this);
    }
}
