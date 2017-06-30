package cc.a5156.xdkp.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by admin on 2017/5/28.
 */
public class ToastUtil {
    private static Toast mToast = null;

    public static void showToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public static void showToast(Context context, int resID) {
        showToast(context, context.getString(resID));
    }
}
