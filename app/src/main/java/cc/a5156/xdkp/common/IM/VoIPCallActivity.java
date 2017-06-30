//package cc.a5156.xdkp.common.IM;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.yuntongxun.ecsdk.ECDevice;
//import com.yuntongxun.ecsdk.ECVoIPCallManager;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import cc.a5156.xdkp.R;
//import cc.a5156.xdkp.common.base.BaseActivity;
//import cc.a5156.xdkp.common.sqlite.SQLiteKey;
//import cc.a5156.xdkp.common.util.SQLiteUtil;
//
///**
// * Created by Administrator on 2017/6/10.
// */
//
//public class VoIPCallActivity extends BaseActivity implements View.OnClickListener {
//    private String mCallNumber;
//    private String mCallId;
//    private ECVoIPCallManager.CallType mCallType;
//    private boolean mIncomingCall;
//
//    private final String EXTRA_OUTGOING_CALL = "EXTRA_OUTGOING_CALL";
//
//    @BindView(R.id.btAccept)
//    Button btAccept;
//    @BindView(R.id.btRefuse)
//    Button btRefuse;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_call);
//        ButterKnife.bind(this);
//        Bundle extras = getIntent().getExtras();
//        if (extras == null) {
//            finish();
//            return;
//        }
//
////获取是否是呼入还是呼出
//
//        mIncomingCall = !(getIntent().getBooleanExtra(EXTRA_OUTGOING_CALL, false));
//
////获取是否是音频还是视频
//
//        mCallType = (ECVoIPCallManager.CallType)
//
//                getIntent().getSerializableExtra(ECDevice.CALLTYPE);
//
////获取当前的callid
//
//        mCallId = getIntent().getStringExtra(ECDevice.CALLID);
//
////获取对方的号码
//
//        mCallNumber = getIntent().getStringExtra(ECDevice.CALLER);
//
//        setListener();
//
//    }
//
//    private void setListener() {
//        btAccept.setOnClickListener(this);
//        btRefuse.setOnClickListener(this);
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btAccept:
//                accept();
//                break;
//            case R.id.btRefuse:
//                refuse();
//                break;
//        }
//
//    }
//
//    private void refuse() {
//        ECDevice.getECVoIPCallManager().rejectCall(mCallId, 1);
//        ECDevice.getECVoIPCallManager().releaseCall(mCallId);
//    }
//
//    private void accept() {
//        ECDevice.getECVoIPCallManager().acceptCall(mCallId);
//    }
//}
//
