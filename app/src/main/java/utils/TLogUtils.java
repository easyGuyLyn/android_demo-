package utils;

import android.util.Log;


/**
 * Created by lyn on 2016/2/24.
 */
public class TLogUtils {

    /**
     * debug开关.
     */
    public static boolean D = true;


    /**
     * 普通log
     *
     * @param tag
     * @param message
     */
    public static void d(String tag, String message) {
        if (D) {
            Log.e(tag, message);
        }
    }

}
