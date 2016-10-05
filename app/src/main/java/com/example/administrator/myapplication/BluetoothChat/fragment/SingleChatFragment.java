package com.example.administrator.myapplication.BluetoothChat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.BluetoothChat.adapter.ChatAdapter;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.ToastUtils;

/**
 * Created by Administrator on 2016/10/4.
 */

public class SingleChatFragment extends Fragment {
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final String DEVICE_NAME = "device_name";
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final String TOAST = "toast";
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;

    private View mView;
    @Bind(R.id.rv_speech)
    RecyclerView rv_speech;
    @Bind(R.id.rl_chat_control)
    RelativeLayout rl_chat_control;
    @Bind(R.id.btn_send)
    TextView btn_send;
    @Bind(R.id.btn_more)
    ImageView btn_more;
    //文字输入
    @Bind(R.id.edittext_layout)
    RelativeLayout edittext_layout;
    @Bind(R.id.iv_emoticons)
    ImageView iv_emoticons;
    @Bind(R.id.et_sendmessage)
    EditText et_sendmessage;
    @Bind(R.id.btn_set_mode_keyboard)
    ImageView btn_set_mode_keyboard;
    //语音输入
    @Bind(R.id.btn_press_to_speak)
    RelativeLayout btn_press_to_speak;
    @Bind(R.id.btn_set_mode_voice)
    ImageView btn_set_mode_voice;

    private ChatAdapter speechAdapter;
    private List<String> mData = new ArrayList<>();
    private InputMethodManager imm;

    public SingleChatFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_single_chat, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        initAdapter();
        initData();
        initListener();
    }

    public void initAdapter() {
        rv_speech.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        speechAdapter = new ChatAdapter(getActivity(), mData);
        rv_speech.setAdapter(speechAdapter);
    }

    public void initData() {
        for (int i = 0; i < 2; i++) {
            mData.add("test" + i);
        }
        speechAdapter.notifyDataSetChanged();
    }

    public void initListener() {
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
        ToastUtils.showMsg("发送~");
    }


    /**
     * @方法名：hideKeyboard
     * @描述：隐藏软键盘
     * @输出：void
     */
    private void hideKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
