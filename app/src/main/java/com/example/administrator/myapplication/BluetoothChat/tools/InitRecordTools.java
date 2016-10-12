package com.example.administrator.myapplication.BluetoothChat.tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.BluetoothChat.BluetoothChatActivity;
import com.example.administrator.myapplication.R;

import utils.TLogUtils;
import utils.ToastUtils;

/**
 * Created by Administrator on 2016/10/11.
 */

public class InitRecordTools {

    public static void initRecord(RelativeLayout btn_press_to_speak, final Context context, final RelativeLayout recordingContainer, final TextView recordingHint, final VoiceRecorder voiceRecorder) {
        final PowerManager.WakeLock wakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "Bluchat");
        btn_press_to_speak.setOnTouchListener(new View.OnTouchListener() {
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
                            recordingHint.setText(context.getString(R.string.msg_msg_voice_do_cancel_send_1));
                            recordingHint.setBackgroundColor(Color.TRANSPARENT);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (context.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                                    ((Activity) context).requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                                            BluetoothChatActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                                } else {
                                    Log.d("Home", "Already granted access");
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
                            BluetoothChatActivity.notShowTip = true;
                            recordingHint
                                    .setText(context.getString(R.string.msg_msg_voice_do_cancel_send_2));
                            recordingHint
                                    .setBackgroundResource(R.drawable.recording_text_hint_bg);
                        } else {
                            BluetoothChatActivity.notShowTip = false;
                            if (VoiceRecorder.MAX_DURATION
                                    - voiceRecorder.voice_duration > VoiceRecorder.TIME_TO_COUNT_DOWN) {
                                recordingHint
                                        .setText(context.getString(R.string.msg_msg_voice_do_cancel_send_1));
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
                                // discard the recorded audio.
                                voiceRecorder.discardRecording();
                            } else {
                                // stop recording and send voice file
                                try {
                                    int length = voiceRecorder.stopRecoding();
                                    if (length > 0) {
                                        //// TODO: 2016/10/12  
                                        TLogUtils.d("lyn",voiceRecorder.getVoiceFilePath(length) + ",时长:" + length);
                                        ToastUtils.showMsg(voiceRecorder.getVoiceFilePath(length) + ",时长:" + length);
                                    } else {
                                        ToastUtils.showMsg("录音时间太短");
                                    }
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                    ToastUtils.showMsg("语音文件格式错误，目前支持mp3、amr格式");
                                } catch (Exception e) {
                                    // handle exception
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
        });
    }
}