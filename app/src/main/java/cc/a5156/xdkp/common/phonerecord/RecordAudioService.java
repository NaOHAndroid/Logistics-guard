package cc.a5156.xdkp.common.phonerecord;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import cc.a5156.xdkp.common.util.AudioRecoderUtils;

/**
 * Created by Administrator on 2017/6/30.
 */

public class RecordAudioService extends Service {
    private AudioRecoderUtils recoder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        recoder = new AudioRecoderUtils();
        recoder.startRecord();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recoder.stopRecord();
        recoder = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
