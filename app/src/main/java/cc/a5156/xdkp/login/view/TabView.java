package cc.a5156.xdkp.login.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import cc.a5156.xdkp.R;
import cc.a5156.xdkp.common.base.BaseFragment;

/**
 * Created by Administrator on 2017/6/8.
 */

public class TabView extends LinearLayout implements CompoundButton.OnCheckedChangeListener {
    CheckBox cbMessage;
    CheckBox cbReceive;
    CheckBox cbDiliver;
    CheckBox cbOffice;
    CheckBox cbMe;


    private FragmentActivity context;
    private List<BaseFragment> fragments;

    public TabView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = (FragmentActivity) context;
        View.inflate(context, R.layout.tabview, this);
        cbMessage=findViewById(R.id.cbMessage);
        cbReceive=findViewById(R.id.cbReceive);
        cbDiliver=findViewById(R.id.cbDiliver);
        cbOffice=findViewById(R.id.cbOffice);
        cbMe=findViewById(R.id.cbMe);
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
        if (buttonView.getId() == R.id.cbMessage) {
            handleFragment(fragments.get(0), isChecked);
        } else if (buttonView.getId() == R.id.cbReceive) {
            handleFragment(fragments.get(1), isChecked);
        } else if (buttonView.getId() == R.id.cbDiliver) {
            handleFragment(fragments.get(2), isChecked);
        } else if (buttonView.getId() == R.id.cbOffice) {
            handleFragment(fragments.get(3), isChecked);
        } else if (buttonView.getId() == R.id.cbMe) {
            handleFragment(fragments.get(4), isChecked);
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
