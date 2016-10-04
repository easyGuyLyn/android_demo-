package com.example.administrator.myapplication.BluetoothChat;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.myapplication.BluetoothChat.adapter.SectionsPagerAdapter;
import com.example.administrator.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BluetoothChatActivity extends AppCompatActivity {

    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_bluetooth_chat);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        toolbar.setTitle("蓝牙技术");
        toolbar.setSubtitle("未连接设备");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mSectionsPagerAdapter);
        tabs.setupWithViewPager(viewpager);
    }

    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_blutooth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.secure_connect_scan) {

            return true;
        } else if (id == R.id.insecure_connect_scan) {

            return true;
        } else if (id == R.id.discoverable) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置此时蓝牙的连接状态在副标题上
     * @param status
     */
    public void setStatus(String status) {
        toolbar.setSubtitle(status);
    }





    private void finishActivity() {
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
