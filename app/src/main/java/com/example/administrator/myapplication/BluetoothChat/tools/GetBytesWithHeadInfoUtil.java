package com.example.administrator.myapplication.BluetoothChat.tools;

import utils.TLogUtils;

/**
 * Created by Administrator on 2016/10/31.
 */

public class GetBytesWithHeadInfoUtil {

    public static int HEADLENGTH = 10;

    public static byte[] getByteArry(String content) {

        String head = null;
        int contentByteLength = content.getBytes().length;
        head = contentByteLength + "";
        TLogUtils.d("lyn_getBytes", contentByteLength + "");
        if (getNumber(contentByteLength) < HEADLENGTH) {
            int tee = HEADLENGTH - getNumber(contentByteLength);
            for (int i = 0; i < tee; i++) {
                head += " ";//用空格补到10位
            }
        }
        int allByteLength = (head + content).getBytes().length;
        TLogUtils.d("lyn_getBytes", allByteLength + "");
        return (head + content).getBytes();
    }

    public static int getNumber(int n) {
        int count = 0;
        while (Math.abs(n) % 10 > 0 || n / 10 != 0) {
            count++;
            n = n / 10;
        }
        return count;
    }
}
