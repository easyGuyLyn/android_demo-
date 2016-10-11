package com.example.administrator.myapplication.BluetoothChat.config;


import utils.DateUtil;

/**
 * Created by lyn on 2016/8/30.
 */
public class TimeShowUtil {

    public static String getTimeShow(long time) {
        long nowTime = System.currentTimeMillis();
        if (DateUtil.getDay(nowTime).equals(DateUtil.getDay(time))) {
            return DateUtil.getTimeDT(time);
        } else {
            return DateUtil.getTimeTT(time);
        }
    }
}
