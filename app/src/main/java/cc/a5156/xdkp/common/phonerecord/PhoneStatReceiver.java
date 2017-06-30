package cc.a5156.xdkp.common.phonerecord;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

/**
 * Created by Administrator on 2017/6/30.
 */

public class PhoneStatReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        switch (tm.getCallState()) {
            case TelephonyManager.CALL_STATE_RINGING:
                context.startService(new Intent(context, RecordAudioService.class));
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:

                break;
            case TelephonyManager.CALL_STATE_IDLE:
                context.stopService(new Intent(context, RecordAudioService.class));
                break;
        }
    }
}
