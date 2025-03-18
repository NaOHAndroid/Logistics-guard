package cc.a5156.xdkp.common.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;

import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

import cc.a5156.xdkp.common.base.App;

/**
 * Created by Administrator on 2017/6/15.
 */

public class PublicUtil {
    /**
     * 利用系统CallLog获取通话历史记录
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCallHistoryList(Context context, ContentResolver cr) {


        if (ActivityCompat.checkSelfPermission(App.getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        Cursor cs = cr.query(CallLog.Calls.CONTENT_URI, //系统方式获取通讯录存储地址
                new String[]{
                        CallLog.Calls.CACHED_NAME,  //姓名
                        CallLog.Calls.NUMBER,    //号码
                        CallLog.Calls.TYPE,  //呼入/呼出(2)/未接
                        CallLog.Calls.DATE,  //拨打时间
                        CallLog.Calls.DURATION   //通话时长
                }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        String callHistoryListStr = "";
        int i = 0;
        if (cs != null && cs.getCount() > 0) {
            for (cs.moveToFirst(); !cs.isAfterLast() & i < 50; cs.moveToNext()) {
                String callName = cs.getString(0);
                String callNumber = cs.getString(1);
                //通话类型
                int callType = Integer.parseInt(cs.getString(2));
                String callTypeStr = "";
                switch (callType) {
                    case CallLog.Calls.INCOMING_TYPE:
                        callTypeStr = "呼入";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        callTypeStr = "呼出";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callTypeStr = "未接";
                        break;
                }
                //拨打时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date callDate = new Date(Long.parseLong(cs.getString(3)));
                String callDateStr = sdf.format(callDate);
                //通话时长
                int callDuration = Integer.parseInt(cs.getString(4));
                int min = callDuration / 60;
                int sec = callDuration % 60;
                String callDurationStr = min + "分" + sec + "秒";
                String callOne = "类型：" + callTypeStr + ", 称呼：" + callName + ", 号码："
                        + callNumber + ", 通话时长：" + callDurationStr + ", 时间:" + callDateStr
                        + "\n---------------------\n";

                callHistoryListStr += callOne;
                i++;
            }
        }
        return callHistoryListStr;
    }

    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
