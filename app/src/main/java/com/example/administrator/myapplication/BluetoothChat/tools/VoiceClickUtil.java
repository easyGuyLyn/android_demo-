package com.example.administrator.myapplication.BluetoothChat.tools;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.R;

import utils.ToastUtils;

/**
 * Created by Administrator on 2016/10/26.
 */

public class VoiceClickUtil {

    public static void doPlay(final Boolean isSend, final VoiceRecorder mVoiceRecorder, RelativeLayout rl_voice_play, final ImageView iv_audio, final String finalVoicePath) {

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
}
