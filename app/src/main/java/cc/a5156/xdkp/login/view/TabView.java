package cc.a5156.xdkp.login.view;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.a5156.xdkp.R;
import cc.a5156.xdkp.common.base.BaseFragment;

/**
 * Created by Administrator on 2017/6/8.
 */

public class TabView extends LinearLayout implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.cbMessage)
    CheckBox cbMessage;
    @BindView(R.id.cbReceive)
    CheckBox cbReceive;
    @BindView(R.id.cbDiliver)
    CheckBox cbDiliver;
    @BindView(R.id.cbOffice)
    CheckBox cbOffice;
    @BindView(R.id.cbMe)
    CheckBox cbMe;


    private FragmentActivity context;
    private List<BaseFragment> fragments;

    public TabView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = (FragmentActivity) context;
        View.inflate(context, R.layout.tabview, this);
        ButterKnife.bind(this);
        setListener();
    }

    public void setTags(List<BaseFragment> fragments) {
        this.fragments = fragments;
    }

    private void setListener() {
        cbMessage.setOnCheckedChangeListener(this);
        cbReceive.setOnCheckedChangeListener(this);
        cbDiliver.setOnCheckedChangeListener(this);
        cbOffice.setOnCheckedChangeListener(this);
        cbMe.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        FragmentTransaction fragmentTransaction = context.getFragmentManager().beginTransaction();
//        BaseFragment fragment = ((BaseFragment) buttonView.getTag());
//        if (isChecked) {
//            fragmentTransaction.show(fragment);
//        } else {
//            fragmentTransaction.hide(fragment);
//        }
//        fragmentTransaction.commit();
        switch (buttonView.getId()) {
            case R.id.cbMessage:
                handleFragment(fragments.get(0), isChecked);
                break;
            case R.id.cbReceive:
                handleFragment(fragments.get(1), isChecked);
                break;
            case R.id.cbDiliver:
                handleFragment(fragments.get(2), isChecked);
                break;
            case R.id.cbOffice:
                handleFragment(fragments.get(3), isChecked);
                break;
            case R.id.cbMe:
                handleFragment(fragments.get(4), isChecked);
                break;
        }
    }

    private void handleFragment(BaseFragment baseFragment, boolean isChecked) {
        FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
        if (isChecked) {
            fragmentTransaction.show(baseFragment);
        } else {
            fragmentTransaction.hide(baseFragment);
        }
        fragmentTransaction.commit();
    }
}
