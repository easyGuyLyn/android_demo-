package com.example.administrator.myapplication.BluetoothChat.tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.BluetoothChat.BluetoothChatActivity;
import com.example.administrator.myapplication.R;

import utils.ToastUtils;

/**
 * Created by Administrator on 2016/10/31.
 */

public class VocieTouchListener implements View.OnTouchListener {

    private Context mContext;
    private Handler handler;
    private PowerManager.WakeLock wakeLock;
    private RelativeLayout recordingContainer;// 一个容器，装载显示录音时声音变化的ivRecord和录音时提示信息的tvVoiceTips
    private TextView recordingHint;
    private VoiceRecorder voiceRecorder; //语音逻辑类

    public VocieTouchListener(VoiceRecorder voiceRecorder, TextView recordingHint, RelativeLayout recordingContainer, Handler handler, Context mContext) {
        this.voiceRecorder = voiceRecorder;
        this.recordingHint = recordingHint;
        this.recordingContainer = recordingContainer;
        this.handler = handler;
        this.mContext = mContext;
        wakeLock = ((PowerManager) mContext.getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "Bluchat");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                try {
                    v.setPressed(true);
                    wakeLock.acquire();
                    if (VoiceRecorder.isPlaying)
                        VoiceRecorder.currentPlayListener.stopPlayVoice();
                    recordingContainer.setVisibility(View.VISIBLE);
                    recordingHint.setText(mContext.getString(R.string.msg_msg_voice_do_cancel_send_1));
                    recordingHint.setBackgroundColor(Color.TRANSPARENT);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (mContext.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                            ((Activity) mContext).requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                                    BluetoothChatActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        } else {
                            voiceRecorder.startRecording();
                        }
                    } else {
                        voiceRecorder.startRecording();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    v.setPressed(false);
                    if (wakeLock.isHeld())
                        wakeLock.release();
                    if (voiceRecorder != null)
                        voiceRecorder.discardRecording();
                    recordingContainer.setVisibility(View.INVISIBLE);
                    ToastUtils.showMsg("录音失败！");
                    return false;
                }
                return true;
            case MotionEvent.ACTION_MOVE: {
                v.setPressed(true);
                if (event.getY() < 0) {
                    // 上滑
                    recordingHint.setText(mContext.getString(R.string.msg_msg_voice_do_cancel_send_2));
                    recordingHint.setBackgroundResource(R.drawable.recording_text_hint_bg);
                } else {
                    if (VoiceRecorder.MAX_DURATION - voiceRecorder.voice_duration > VoiceRecorder.TIME_TO_COUNT_DOWN) {
                        recordingHint.setText(mContext.getString(R.string.msg_msg_voice_do_cancel_send_1));
                        recordingHint.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
                return true;
            }
            case MotionEvent.ACTION_UP:
                v.setPressed(false);
                if (recordingContainer.getVisibility() == View.VISIBLE) {
                    recordingContainer.setVisibility(View.INVISIBLE);
                    if (wakeLock.isHeld())
                        wakeLock.release();
                    if (event.getY() < 0) {
                        voiceRecorder.discardRecording();
                    } else {
                        try {
                            int length = voiceRecorder.stopRecoding();
                            Message msg = new Message();
                            msg.what = BluetoothChatActivity.VOICE_UP;
                            msg.arg1 = length;
                            handler.sendMessage(msg);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            ToastUtils.showMsg("语音文件格式错误，目前支持mp3、amr格式");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                return true;
            default:
                recordingContainer.setVisibility(View.INVISIBLE);
                if (voiceRecorder != null)
                    voiceRecorder.discardRecording();
                return false;
        }
    }
}
