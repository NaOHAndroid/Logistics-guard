//package cc.a5156.xdkp.common.service;
//
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.media.MediaRecorder;
//import android.os.Environment;
//import android.os.IBinder;
//import android.telephony.PhoneStateListener;
//import android.telephony.TelephonyManager;
//
//import java.io.File;
//import java.io.FileWriter;
//
//import cc.a5156.xdkp.common.util.PublicUtil;
//import cc.a5156.xdkp.common.util.ToastUtil;
//
///**
// * Created by Administrator on 2017/6/15.
// */
//public class PhoneService extends Service {
//
//    private StringBuilder sb = new StringBuilder();
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        telephonyManager.listen(new PhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
//        ToastUtil.showToast(getApplicationContext(), "录音服务已启动");
//    }
//
//    private final class PhoneListener extends PhoneStateListener {
//        private String incomingNumber;
//        private File file;
//        private MediaRecorder mediaRecorder;
//
//
//        @Override
//        public void onCallStateChanged(int state, String incomingNumber) {
//            try {
//                switch (state) {
//                    case TelephonyManager.CALL_STATE_RINGING://来电
//                        this.incomingNumber = incomingNumber;
//                        sb.append(PublicUtil.getCurrentTime() + " CALL_STATE_RINGING\r\n");
//                        break;
//
//                    case TelephonyManager.CALL_STATE_OFFHOOK://接通电话
//                        sb.append(PublicUtil.getCurrentTime() + " CALL_STATE_OFFHOOK\r\n");
//
//                        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "ZZZZZZZZRecord");
//                        if (!dir.exists()) {
//                            dir.mkdirs();
//                        }
//                        file = new File(dir, incomingNumber + System.currentTimeMillis() + ".amr");
//                        mediaRecorder = new MediaRecorder();
//                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
//                        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//                        mediaRecorder.setOutputFile(file.getAbsolutePath());
//                        mediaRecorder.prepare();
//                        mediaRecorder.start();//开始录音
//                        break;
//
//                    case TelephonyManager.CALL_STATE_IDLE://挂断电话后回归到空闲状态
//                        sb.append(PublicUtil.getCurrentTime() + " CALL_STATE_IDLE\r\n");
//
//                        if (mediaRecorder != null) {
//                            mediaRecorder.stop();
//                            mediaRecorder.release();
//                            mediaRecorder = null;
//                        }
//                        break;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        ToastUtil.showToast(getApplicationContext(), "录音服务已终止");
//        try {
//            FileWriter fw = new FileWriter(new File(Environment.getExternalStorageDirectory() + File.separator + "ZLog.txt"));
//            fw.write(sb.toString());
//            fw.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
