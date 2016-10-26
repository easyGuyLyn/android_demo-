package com.example.administrator.myapplication.BluetoothChat.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.BluetoothChat.config.CacheConfig;
import com.example.administrator.myapplication.BluetoothChat.model.BluChatMsgBean;
import com.example.administrator.myapplication.R;

import java.io.File;
import java.util.Date;

import utils.Base64Utils;
import utils.FileUtil;
import utils.ThreadUtils;
import utils.ToastUtils;

/**
 * Created by Administrator on 2016/10/19.
 */

public class ChatVoiceUtil {

    public static final String EXTENSION = ".amr";

    public static String initVoice(DisplayMetrics displayMetrics, Handler handler, final Context mContext, final BluChatMsgBean message, final LinearLayout ll_voice_info, final ProgressBar pb_outgoing, final RelativeLayout rl_voice_play) {
        if (handler == null) {
            synchronized (Handler.class) {
                if (handler == null) {
                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            if (msg.what == 1) {
                                rl_voice_play.setVisibility(View.VISIBLE);
                                ll_voice_info.setVisibility(View.VISIBLE);
                                pb_outgoing.setVisibility(View.GONE);
                            }
                        }
                    };
                }
            }
        }
        setVoiceWidth(rl_voice_play, message, displayMetrics);//设置语音点击区域的长度
        String savepath = getVoiceFilePath(mContext);//语音播放文件地址
        if (new File(message.getFilePath()).exists()) {
            rl_voice_play.setVisibility(View.VISIBLE);
            ll_voice_info.setVisibility(View.VISIBLE);
            pb_outgoing.setVisibility(View.GONE);
            savepath = message.getFilePath();
        } else {
            rl_voice_play.setVisibility(View.GONE);
            ll_voice_info.setVisibility(View.GONE);
            pb_outgoing.setVisibility(View.VISIBLE);
            final Handler finalHandler = handler;
            final String finalSavepath = savepath;
            ThreadUtils.newThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Base64Utils.decoderBase64File(message.getContent(), finalSavepath);
                        message.setFilePath(finalSavepath);
                        Message msg = new Message();
                        msg.what = 1;
                        finalHandler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return savepath;
    }

    public static void setVoiceWidth(RelativeLayout rl_voice_play, BluChatMsgBean message, DisplayMetrics displayMetrics) {
        int audioTime = Integer.parseInt(message.getVoiceLength());
        ViewGroup.LayoutParams layoutParams = rl_voice_play.getLayoutParams();
        // width :1080
        int rl_width = audioTime * 10 + displayMetrics.widthPixels / 9;
        layoutParams.width = rl_width < displayMetrics.widthPixels / 10 * 6 ? rl_width : displayMetrics.widthPixels / 10 * 6;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public static String getVoiceFilePath(Context context) {
        return FileUtil.getDiskFileDir(context, CacheConfig.VOICE_BLU) + "/" + new Date() + EXTENSION;
    }
}
