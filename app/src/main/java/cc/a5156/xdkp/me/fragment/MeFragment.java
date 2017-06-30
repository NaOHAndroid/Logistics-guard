package cc.a5156.xdkp.me.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cc.a5156.xdkp.R;
import cc.a5156.xdkp.common.base.BaseFragment;


/**
 * Created by Administrator on 2017/6/6.
 */

public class MeFragment extends BaseFragment {
    private Button btExit;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_me;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btExit = (Button) view.findViewById(R.id.btExit);
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

}
