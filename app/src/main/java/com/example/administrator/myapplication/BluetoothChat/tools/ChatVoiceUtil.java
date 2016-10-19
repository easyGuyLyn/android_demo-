package com.example.administrator.myapplication.BluetoothChat.tools;

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

    public static void initVoice(final Boolean isSend, final VoiceRecorder mVoiceRecorder, Handler handler, final Context mContext, final BluChatMsgBean message, final LinearLayout ll_voice_info, final ProgressBar pb_outgoing, RelativeLayout rl_voice_play, final ImageView iv_audio) {
        if (handler == null) {
            synchronized (Handler.class) {
                if (handler == null) {
                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            if (msg.what == 1) {
                                ll_voice_info.setVisibility(View.VISIBLE);
                                pb_outgoing.setVisibility(View.GONE);
                            }
                        }
                    };
                }
            }
        }
        //setVoiceWidth(rl_voice_play, message);//设置语音点击区域的长度
        final String[] voicePath = {null}; //语音播放文件地址
        if (new File(message.getFilePath()).exists()) {
            ll_voice_info.setVisibility(View.VISIBLE);
            pb_outgoing.setVisibility(View.GONE);
            voicePath[0] = message.getFilePath();
        } else {
            ll_voice_info.setVisibility(View.GONE);
            pb_outgoing.setVisibility(View.VISIBLE);
            final Handler finalHandler = handler;
            ThreadUtils.newThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String savepath = getVoiceFilePath(mContext);
                        Base64Utils.decoderBase64File(message.getContent(), savepath);
                        message.setFilePath(savepath);
                        voicePath[0] = savepath;
                        Message msg = new Message();
                        msg.what = 1;
                        finalHandler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        final String finalVoicePath = voicePath[0];
        rl_voice_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalVoicePath == null) {
                    ToastUtils.showMsg("文件还在缓冲..");
                    return;
                }
                mVoiceRecorder.playVoice(finalVoicePath, new VoiceRecorder.MediaPlayerCallback() {

                    @Override
                    public void onStart() {
                        if (isSend) {
                            iv_audio.setImageResource(R.drawable.anim_voice_playing_right);
                        } else {
                            iv_audio.setImageResource(R.drawable.anim_voice_playing_left);
                        }
                        AnimationDrawable ad = (AnimationDrawable) iv_audio.getDrawable();
                        ad.start();
                    }

                    @Override
                    public void onStop() {
                        Drawable drawable = iv_audio.getDrawable();
                        if (drawable instanceof AnimationDrawable) {
                            ((AnimationDrawable) drawable).stop();
                        }
                        if (isSend) {
                            iv_audio.setImageResource(R.drawable.chatto_voice_playing_f3);
                        } else {
                            iv_audio.setImageResource(R.drawable.chatfrom_voice_playing_f3);
                        }
                    }
                });
            }
        });
    }

    public static void setVoiceWidth(RelativeLayout rl_voice_play, BluChatMsgBean message) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
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
