package cc.a5156.xdkp.message.temp;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import cc.a5156.xdkp.R;
import cc.a5156.xdkp.common.util.PublicUtil;

/**
 * Created by Administrator on 2017/6/21.
 */

public class CallHistoryActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callhistory);
        TextView tv_callHistory = (TextView) findViewById(R.id.tvCallHistory);
        tv_callHistory.setText("通讯记录");

        //获取通话记录
        ContentResolver cr = getContentResolver();
        String callHistoryListStr = PublicUtil.getCallHistoryList(null, cr);
        tv_callHistory.setTextSize(12.0f);
        tv_callHistory.setText(callHistoryListStr);
    }
}
