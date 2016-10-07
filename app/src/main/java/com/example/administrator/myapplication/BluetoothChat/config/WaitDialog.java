package com.example.administrator.myapplication.BluetoothChat.config;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;


/**
 * Created by HaughtyEyes on 2016/8/30.
 */
public class WaitDialog extends Dialog {
    private TextView tv_txt;
    private ImageView iv_icon;

    public WaitDialog(Context context) {
        super(context, R.style.WaitDialog);
        View view = View.inflate(getContext(), R.layout.dialog_speech_wait, null);
        tv_txt = (TextView) view.findViewById(R.id.tv_txt);
        //iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        setContentView(view);
        setCancelable(false);
    }

    private void setDialog(String text) {
        //iv_icon.setImageResource(res);
        tv_txt.setText(text);
    }

    public void sendMsg() {
        setDialog("发送中...");
        show();
    }

    public void connectAgain() {
        setDialog("正在尝试重连中...");
        show();
    }
}
