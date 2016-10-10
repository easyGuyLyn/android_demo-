package com.example.administrator.myapplication.BluetoothChat;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.BluetoothChat.adapter.ChatAdapter;
import com.example.administrator.myapplication.BluetoothChat.blu.BluetoothChatService;
import com.example.administrator.myapplication.BluetoothChat.config.MyChatEditText;
import com.example.administrator.myapplication.BluetoothChat.config.TextChatMessage;
import com.example.administrator.myapplication.BluetoothChat.config.WaitDialog;
import com.example.administrator.myapplication.BluetoothChat.model.BluChatMsg;
import com.example.administrator.myapplication.BluetoothChat.model.BluChatMsgText;
import com.example.administrator.myapplication.BluetoothChat.tools.InitEmoViewTools;
import com.example.administrator.myapplication.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.ToastUtils;

public class BluetoothChatActivity extends AppCompatActivity {
    // Debugging
    private static final String TAG = "BluetoothChat";
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST_CONNECT_FAIL = 5;
    public static final int MESSAGE_TOAST_CONNECT_LOST = 6;
    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String DEVICE_ADDRESS = "device_adress";
    public static final String TOAST = "toast";
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothChatService mChatService = null;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    //内容
    @Bind(R.id.rv_speech)
    RecyclerView rv_speech; //聊天列表
    @Bind(R.id.rl_chat_control)
    LinearLayout rl_chat_control;
    @Bind(R.id.btn_send)
    TextView btn_send; //发送按钮
    @Bind(R.id.btn_more)
    ImageView btn_more; //更多按钮
    //文字输入
    @Bind(R.id.edittext_layout)
    RelativeLayout edittext_layout;
    @Bind(R.id.et_sendmessage)
    MyChatEditText et_sendmessage;
    @Bind(R.id.btn_set_mode_keyboard)
    ImageView btn_set_mode_keyboard; //切换文字输入按钮
    //语音输入
    @Bind(R.id.btn_press_to_speak)
    RelativeLayout btn_press_to_speak;
    @Bind(R.id.btn_set_mode_voice)
    ImageView btn_set_mode_voice; //切换语音按钮
    //表情
    @Bind(R.id.iv_emoticons)
    ImageView iv_emoticons; //呼唤表情面板按钮
    @Bind(R.id.cpi_footer_chat_activity_emo_indicator)
    CirclePageIndicator cip;  //ViewPager中的界面圆形界面指示器（第三方类库）
    @Bind(R.id.vp_footer_chat_activity_pager_emo)
    ViewPager pager_emo;  //layoutEmo中管理加载表情的界面的ViewPager
    private List<TextChatMessage> emos;//装载表情图片的列表

    //全局
    private WaitDialog waitDialog;
    private ChatAdapter speechAdapter;
    private List<BluChatMsg> mData = new ArrayList<>();
    private InputMethodManager imm;
    private Boolean isNeedSrollByItself = true;
    private Boolean isDestroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_bluetooth_chat);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "++ ON START ++");
        if (!mBluetoothAdapter.isEnabled()) {
            //提示用户打开；
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            //直接打开；
            //	mBluetoothAdapter.enable();
            //	 setupTask();
            // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupTask();
        }
    }

    private void initView() {
        toolbar.setTitle(getString(R.string.BluTittle));
        toolbar.setSubtitle(getString(R.string.notConnect));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            ToastUtils.showMsg(getString(R.string.Bluetoothisnotavailable));
            finishActivity();
            return;
        }
        InitEmoViewTools.initEmoView(this, emos, pager_emo, cip, et_sendmessage);//初始化表情相关业务
    }

    public void setupTask() {
        waitDialog = new WaitDialog(this);
        mChatService = new BluetoothChatService(this, mHandler);
        rv_speech.setLayoutManager(new GridLayoutManager(this, 1));
        speechAdapter = new ChatAdapter(this, mData);
        rv_speech.setAdapter(speechAdapter);
    }

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE://蓝牙连接状态改变后的回调
                    Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            setStatus(getString(R.string.connecttedTo) + " " + mConnectedDeviceName);
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            setStatus(getString(R.string.connectting));
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            setStatus(getString(R.string.notConnect));
                            break;
                    }
                    break;
                case MESSAGE_WRITE: //成功发送消息后的回调
                    byte[] writeBuf = (byte[]) msg.obj;
                    String writeMessage = new String(writeBuf);
                    addMsg(new BluChatMsgText(BluChatMsg.SEND, "我", writeMessage));
                    hideKeyboard();
                    et_sendmessage.setText("");
                    rv_speech.scrollToPosition(mData.size() - 1);
                    waitDialog.dismiss();
                    break;
                case MESSAGE_READ: //成功读取消息后的回调
                    byte[] readBuf = (byte[]) msg.obj;
                    String deviceName = msg.getData().getString(DEVICE_NAME);
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    addMsg(new BluChatMsgText(BluChatMsg.RECEIVE, deviceName, readMessage));
                    break;
                case MESSAGE_DEVICE_NAME://获得连接设备名后的回调
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    ToastUtils.showMsg(getString(R.string.connecttedTo) + " " + mConnectedDeviceName);
                    break;
                case MESSAGE_TOAST_CONNECT_LOST://掉线的回调
                    ToastUtils.showMsg(msg.getData().getString(TOAST));
                    String adress = msg.getData().getString(DEVICE_ADDRESS);
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(adress);
                    if (!isDestroyed) {
                        // Start the service over to restart listening mode
                        mChatService.start();
                    }
                    break;
                case MESSAGE_TOAST_CONNECT_FAIL://连接失败的回调
                    if (!isDestroyed) {
                        // Start the service over to restart listening mode
                        mChatService.start();
                    }
                    ToastUtils.showMsg(msg.getData().getString(TOAST));
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_blutooth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent serverIntent = null;
        if (id == R.id.secure_connect_scan) {
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
            return true;
        } else if (id == R.id.insecure_connect_scan) {
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
            return true;
        } else if (id == R.id.discoverable) {
            ensureDiscoverable();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ensureDiscoverable() {//重新搜索设备
        Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    public void setStatus(String status) {//设置此时蓝牙的连接状态在副标题上
        if (toolbar != null) {
            toolbar.setSubtitle(status);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    setupTask();
                } else {
                    ToastUtils.showMsg(getString(R.string.Bluetoothisnotavailable));
                    finish();
                }
        }
    }

    private void connectDevice(Intent data, boolean secure) {//连接设备
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        mChatService.connect(device, secure);
    }

    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
        rv_speech.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });
        et_sendmessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    btn_more.setVisibility(View.GONE);
                    btn_send.setVisibility(View.VISIBLE);
                } else {
                    btn_more.setVisibility(View.VISIBLE);
                    btn_send.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rv_speech.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    isNeedSrollByItself = true;
                } else {
                    isNeedSrollByItself = false;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    protected boolean isSlideToBottom(RecyclerView recyclerView) {//recyclerView是否处于底部
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    @OnClick(R.id.btn_set_mode_voice)
    public void setToVoice() {
        ToastUtils.showMsg("暂不支持语言~");
    }

    @OnClick(R.id.btn_more)
    public void setToMore() {
        ToastUtils.showMsg("暂不支持更多功能~");
    }

    @OnClick(R.id.iv_emoticons)
    public void setToEmj() {
        ToastUtils.showMsg("暂不支持表情功能~");
    }

    @OnClick(R.id.btn_send)
    public void setToSend() {
        sendMessage(et_sendmessage.getText().toString());
    }

    private void sendMessage(String message) {//发送一条消息
        //// TODO: 2016/10/8  
        waitDialog.sendMsg();
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            waitDialog.dismiss();
            ToastUtils.showMsg(getString(R.string.notConnect));
            return;
        }
        if (message.length() > 0) {
            byte[] send = message.getBytes();
            mChatService.write(send);
        }
    }

    public void addMsg(BluChatMsg msg) {//增加了一条消息
        mData.add(msg);
        speechAdapter.notifyItemInserted(mData.size() - 1);
        if (isNeedSrollByItself) rv_speech.scrollToPosition(mData.size() - 1);
    }

    private void hideKeyboard() { //隐藏软键盘
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void finishActivity() {
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
        if (mChatService != null) mChatService.stop();
        ButterKnife.unbind(this);
    }
}
