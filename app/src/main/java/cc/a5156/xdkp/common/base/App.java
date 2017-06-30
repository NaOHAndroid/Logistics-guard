package cc.a5156.xdkp.common.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cc.a5156.xdkp.common.adapter.ZActivityLifecycleCallbacks;
import cc.a5156.xdkp.login.activity.EntranceActivity;
import cc.a5156.xdkp.login.activity.HomeActivity;

/**
 * Created by Administrator on 2017/6/6.
 */

public class App extends Application {

    private static Context context;

    private static List<Activity> mActivities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //初始化全局异常管理
        CrashHandler.getInstance().init(context);
        registerActivityLifecycleCallbacks();
    }

    private void registerActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(new ZActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                mActivities.add(activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                mActivities.remove(activity);
            }
        });
    }


    public static Context getContext() {
        return context;
    }

    public static void finishEntranceActivity() {
        Activity activity = mActivities.get(0);
        if (activity instanceof EntranceActivity) {
            activity.finish();
        }
    }

    public static boolean hasHomeActivity() {
        for (Activity activity : mActivities) {
            if (activity instanceof HomeActivity)
                return true;
        }
        return false;
    }

    public static void exit() {
        for (Activity activity : mActivities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }
}
