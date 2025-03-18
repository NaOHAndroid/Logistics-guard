package cc.a5156.xdkp.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cc.a5156.xdkp.R;
import cc.a5156.xdkp.common.base.BaseActivity;
import cc.a5156.xdkp.common.base.StartActivity;


/**
 * Created by Administrator on 2017/6/6.
 */
public class LoginActivity extends BaseActivity {
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btLogin=findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String userID = etAccount.getText().toString();
//                SQLiteUtil.save(SQLiteKey.USER_ID, userID);
//                SQLiteUtil.save(SQLiteKey.PASSWORD, etPassword.getText().toString());
//                if ("1".equals(SQLiteUtil.get(SQLiteKey.ECSDK_INIT))) {
//                    IMUtil.ECLogin(userID);
//                } else {
//                    ToastUtil.showToast(that, "ECSDK初始化失败");
//                    //再次初始化
//                    IMUtil.initECSDK();
//                }
                StartActivity.getInstance().startHomeActivity();
                finish();
            }
        });
    }
}
