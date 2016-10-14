package com.example.administrator.myapplication.BluetoothChat.tools;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;


import com.example.administrator.myapplication.BluetoothChat.BluetoothChatActivity;
import com.example.administrator.myapplication.BluetoothChat.config.CacheConfig;
import com.example.administrator.myapplication.main.MyApplication;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import utils.FileUtil;
import utils.TLogUtils;

public class VoiceRecorder {

    private Context context;
    private Handler handler;

    //录音相关
    MediaRecorder recorder;
    public static final String EXTENSION = ".amr";
    private boolean isRecording = false;
    private long startTime;
    public int voice_duration;
    private String voiceFilePath = null;
    private String fileName = null;
    private File file;
    public static final int MAX_DURATION = 180;// 最大录音时长
    public static final int TIME_TO_COUNT_DOWN = 10;// 倒计时开始
    private static SimpleDateFormat mFormat = new SimpleDateFormat("yyyMMddHHmmssSSS");

    //播放语音相关
    AudioManager mAudioMgr;
    private static MediaPlayer mediaPlayer = null;
    private MediaPlayerCallback mMediaPlayerCallback;
    private static AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = null;
    private static String playSource = null;
    public static boolean isPlaying = false;
    public static VoiceRecorder currentPlayListener = null;

    public VoiceRecorder(Context context, Handler paramHandler) {
        this.handler = paramHandler;
        this.context = context;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR_MR1) {
            mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        //失去焦点之后的操作
                        if (isPlaying) {
                            stopPlayVoice();
                        }
                    }
                }
            };
        }
    }

    public String startRecording() {
        if ((this.file != null) && (this.file.exists()) && (!this.file.isDirectory())) {
            this.file.delete();
        }
        this.file = null;
        try {
            this.recorder = new MediaRecorder();
            this.recorder.setAudioSource(AudioSource.MIC);
            this.recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            this.recorder.setAudioEncoder(1);
            this.recorder.setAudioChannels(1);
            this.recorder.setAudioSamplingRate(8000);
            this.recorder.setAudioEncodingBitRate(64);
            this.voiceFilePath = getVoiceFilePath();
            this.file = new File(this.voiceFilePath);
            this.recorder.setOutputFile(this.file.getAbsolutePath());
            this.recorder.prepare();
            this.isRecording = true;
            this.recorder.start();
        } catch (IOException localIOException) {
            Log.e("lyn", "voice prepare() failed");
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (VoiceRecorder.this.isRecording) {
                        Message localMessage = new Message();
                        double ratio = (double) recorder.getMaxAmplitude();
                        localMessage.arg1 = (int) ((14 * ratio) / 32768);
                        localMessage.what = BluetoothChatActivity.VOICE_REFRESH;
                        VoiceRecorder.this.handler.sendMessage(localMessage);
                        SystemClock.sleep(100L);
                    }
                } catch (Exception localException) {
                    Log.e("lyn", localException.toString());
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRecording) {
                    try {
                        voice_duration++;
                        Log.d("IM", voice_duration + "");
                        if (MAX_DURATION - voice_duration < TIME_TO_COUNT_DOWN) {
                            Message msg = handler.obtainMessage();
                            if (MAX_DURATION - voice_duration < 0) {
                                msg.arg1 = stopRecoding();
                                msg.what = BluetoothChatActivity.VOICE_LONG;
                                voice_duration = 0;
                                handler.sendMessage(msg);
                            } else {
                                msg.arg1 = MAX_DURATION - voice_duration;
                                msg.what = BluetoothChatActivity.VOICE_TIP;
                                handler.sendMessage(msg);
                            }
                        }
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }
                }
            }
        }).start();
        this.startTime = new Date().getTime();
        Log.e("lyn", "voice" + " start voice recording to file:" + this.file.getAbsolutePath());
        return this.file == null ? null : this.file.getAbsolutePath();
    }

    public interface MediaPlayerCallback {
        void onStart();

        void onStop();
    }

    public void discardRecording() {
        if (this.recorder != null) {
            try {
                this.isRecording = false;
                this.voice_duration = 0;
                this.recorder.stop();
                this.recorder.release();
                this.recorder = null;
            } catch (IllegalStateException localIllegalStateException) {
                Log.e("discardRecording", "discardRecording");
            }
        }
        if ((this.file != null) && (this.file.exists()) && (!this.file.isDirectory())) {
            this.file.delete();
        }
    }

    public int stopRecoding() {
        if (this.recorder != null) {
            this.isRecording = false;
            this.voice_duration = 0;
            this.recorder.stop();
            this.recorder.release();
            this.recorder = null;
            int i = (int) (new Date().getTime() - this.startTime) / 1000;
            return i;
        }
        return 0;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.recorder != null) {
            this.recorder.release();
        }
    }

    public int getAudioTime(String audioPath) {
        int duration = 0;
        File f = new File(audioPath);
        String string = f.getName();
        if (string.contains("_")) {
            try {
                duration = Integer.parseInt(string.substring(string.lastIndexOf("_") + 1, string.lastIndexOf(".")));
            } catch (Exception e) {
                e.printStackTrace();
                duration = 0;
            }

        } else {
            duration = Math.round(f.length() / (33 * 1000));

        }
        return duration;
    }

    public String getVoiceFilePath(int length) {
        fileName = file.getAbsolutePath().split(EXTENSION)[0] + "_" + length + EXTENSION;

        file.renameTo(new File(fileName));
        return fileName;
    }

    public boolean isRecording() {
        return this.isRecording;
    }

    public String getVoiceFilePath() {
        fileName = FileUtil.getDiskFileDir(context, CacheConfig.VOICE_BLU) + "/" + mFormat.format(new Date()) + EXTENSION;
        return fileName;
    }

    public void playVoice(String filePath, MediaPlayerCallback callback) {
        if (!(new File(filePath).exists())) {
            Log.d("IM", "not exits");
            return;
        }
        if (isPlaying) {
            stopPlayVoice();
            if (playSource.equals(filePath)) {
                return;
            } else {
                doPlay(filePath, callback);
            }
        } else {
            doPlay(filePath, callback);
        }
    }

    private void doPlay(String filePath, MediaPlayerCallback callback) {
        mMediaPlayerCallback = callback;
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        audioManager.setSpeakerphoneOn(true);
        audioManager.setMode(AudioManager.MODE_NORMAL);

        ((Activity) context).setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
        requestAudioFocus();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mMediaPlayerCallback.onStop();
                    stopPlayVoice(); // stop animation
                }

            });
            isPlaying = true;
            playSource = filePath;
            currentPlayListener = this;
            mediaPlayer.start();
            mMediaPlayerCallback.onStart();
        } catch (Exception e) {
            Log.d("IM", e.toString());
        }
    }

    public void stopPlayVoice() {
        // stop play voice
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            mMediaPlayerCallback.onStop();
        }
        abandonAudioFocus();
        isPlaying = false;
        recoveryAudioManager();
    }


    public static void recoveryAudioManager() {  //不影响其它应用的声音的使用
        AudioManager audioManager = (AudioManager) MyApplication.getInstance().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setSpeakerphoneOn(true);
        audioManager.setMode(AudioManager.MODE_NORMAL);
    }

    private void requestAudioFocus() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1) {
            return;
        }

        if (mAudioMgr == null)
            mAudioMgr = (AudioManager) MyApplication.getInstance()
                    .getSystemService(Context.AUDIO_SERVICE);
        if (mAudioMgr != null) {
            int ret = mAudioMgr.requestAudioFocus(mAudioFocusChangeListener,
                    AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (ret != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                TLogUtils.d("requestAudioFocus", "request audio focus fail. " + ret);
            }
        }

    }

    private void abandonAudioFocus() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1) {
            return;
        }
        if (mAudioMgr != null) {
            mAudioMgr.abandonAudioFocus(mAudioFocusChangeListener);
            mAudioMgr = null;
        }
    }

}
