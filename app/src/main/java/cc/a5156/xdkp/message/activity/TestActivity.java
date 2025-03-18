package cc.a5156.xdkp.message.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.core.app.ActivityCompat;

import java.io.IOException;

import cc.a5156.xdkp.R;
import cc.a5156.xdkp.common.base.BaseActivity;
import cc.a5156.xdkp.common.http.OkHttpClientManager;
import cc.a5156.xdkp.common.util.PermissionsActivity;
import cc.a5156.xdkp.common.util.PermissionsChecker;
import cc.a5156.xdkp.common.util.SQLiteUtil;
import cc.a5156.xdkp.common.util.ToastUtil;

/**
 * Created by Administrator on 2017/6/7 12.
 */
public class TestActivity extends BaseActivity {
    ImageView iv;
    Button bt;
    EditText etTarget;
    Button btGet;

    private PermissionsChecker mPermissionsChecker;
    private static final int REQUEST_CODE = 0; // 请求码


    private String[] permissions = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.GET_TASKS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        iv = findViewById(R.id.iv);
        bt = findViewById(R.id.bt);
        etTarget = findViewById(R.id.etTarget);
        btGet = findViewById(R.id.btGet);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadImage();
//                chat();
//                call();
                localeCall();
            }
        });
        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = SQLiteUtil.get("username");
                ToastUtil.showToast(that, s);
            }
        });
        mPermissionsChecker = new PermissionsChecker(this);
    }

    private void localeCall() {
        //启动录音服务
//        Intent service = new Intent(this, PhoneService.class);
//        startService(service);

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + etTarget.getText().toString()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面

        if (mPermissionsChecker.lacksPermissions(permissions)) {
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, permissions);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }

//    private void call() {
////        说明：mCurrentCallId如果返回空则代表呼叫失败，可能是参数错误引起。否则返回是一串数字，是当前通话的标识。
//        String mCurrentCallId = ECDevice.getECVoIPCallManager().makeCall(ECVoIPCallManager.CallType.VOICE,
//                etTarget.getText().toString());
//        Log.e("ZZZ", "mCurrentCallId===" + mCurrentCallId);
//    }

    private void loadImage() {
        try {
            OkHttpClientManager.displayImage(iv, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496835031429&di=1658fe29b1f6d79425a87a04227da336&imgtype=0&src=http%3A%2F%2Fimg.car.mianfeiapp.net%2Fupload%2F20160822%2F1471844761907226.jpg", R.mipmap.ic_launcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void chat() {
//        try {
//            // 组建一个待发送的ECMessage
//            ECMessage msg = ECMessage.createECMessage(ECMessage.Type.TXT);
//            msg.setTo("13670299411");
//            ECTextMessageBody msgBody = new ECTextMessageBody("你好，徐总!");
//            msg.setBody(msgBody);
//            // 调用SDK发送接口发送消息到服务器
//            ECChatManager manager = ECDevice.getECChatManager();
//            manager.sendMessage(msg, new ECChatManager.OnSendMessageListener() {
//                @Override
//                public void onSendMessageComplete(ECError error, ECMessage message) {
//                    // 处理消息发送结果
//                    if (message == null) {
//                        return;
//                    }
//                    Log.e("ZZZ", "发送完成");
//                    // 将发送的消息更新到本地数据库并刷新UI
//                }
//
//                @Override
//                public void onProgress(String msgId, int totalByte, int progressByte) {
//                    // 处理文件发送上传进度（尽上传文件、图片时候SDK回调该方法）
//                }
//            });
//        } catch (Exception e) {
//            // 处理发送异常
//            Log.e("ECSDK_Demo", "send message fail , e=" + e.getMessage());
//        }
//    }
}
