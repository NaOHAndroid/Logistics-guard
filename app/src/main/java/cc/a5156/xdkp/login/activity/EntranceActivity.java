package cc.a5156.xdkp.login.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cc.a5156.xdkp.R;
import cc.a5156.xdkp.common.base.BaseActivity;
import cc.a5156.xdkp.common.base.StartActivity;
import cc.a5156.xdkp.common.util.PermissionsActivity;
import cc.a5156.xdkp.common.util.PermissionsChecker;


/**
 * Created by Administrator on 2017/6/6.
 */

public class EntranceActivity extends BaseActivity {
    Button btInit;
    private PermissionsChecker mPermissionsChecker;
    private static final int REQUEST_CODE = 0; // 请求码

    private String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.NFC,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.CALL_PHONE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        btInit=findViewById(R.id.btInit);
        mPermissionsChecker = new PermissionsChecker(this);
        btInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化联通SDK
//                IMUtil.initECSDK(etAccount.getText().toString());
                StartActivity.getInstance().startHomeActivity();
                finish();

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
//        if (mPermissionsChecker.lacksPermissions(permissions)) {
//            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, permissions);
//        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
//        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
//            finish();
//        }
    }
}
