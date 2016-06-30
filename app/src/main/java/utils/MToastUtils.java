package utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lyn on 2016/2/23.
 */
public class MToastUtils {
    //toast
    private static Toast mToast;

    public static void showMsg(String msg, Context context) {

        if (mToast != null) {
            mToast.setText(msg);
        } else {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
