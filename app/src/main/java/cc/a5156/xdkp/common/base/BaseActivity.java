package cc.a5156.xdkp.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/6.
 */

public class BaseActivity extends Activity {
    protected Context that;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        that = this;
    }
}
