package cc.a5156.xdkp.common.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import cc.a5156.xdkp.login.activity.HomeActivity;
import cc.a5156.xdkp.login.activity.LoginActivity;
import cc.a5156.xdkp.message.activity.TestActivity;
import cc.a5156.xdkp.message.activity.TestActivity2;
import cc.a5156.xdkp.message.activity.TestActivity3;
import cc.a5156.xdkp.message.temp.CallHistoryActivity;


/**
 * Created by Administrator on 2017/6/6.
 */

public class StartActivity {
    private static StartActivity startActivity;
    private Context context = App.getContext();
    private Intent intent;

    {
        intent = new Intent();
        //使用Context的startActivity方法，必须添，否则异常：
        //android.util.AndroidRuntimeException: Calling startActivity() from outside of an Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static StartActivity getInstance() {
        if (startActivity == null) {
            startActivity = new StartActivity();
        }
        return startActivity;
    }

    public void startLoginActivity() {
        context.startActivity(intent.setClass(context, LoginActivity.class));
    }

    public void startHomeActivity() {
        context.startActivity(intent.setClass(context, HomeActivity.class));
    }

    public void startTestActivity() {
//        context.startActivity(intent.setClass(context, TestActivity.class));
//        context.startActivity(intent.setClass(context, TestActivity2.class));
        context.startActivity(intent.setClass(context, TestActivity3.class));
    }

    public void startCallHistoryActivity() {
        context.startActivity(intent.setClass(context, CallHistoryActivity.class));
    }
}
