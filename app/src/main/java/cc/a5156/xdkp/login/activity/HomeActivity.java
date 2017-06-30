package cc.a5156.xdkp.login.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.a5156.xdkp.R;
import cc.a5156.xdkp.common.base.BaseFragment;
import cc.a5156.xdkp.common.util.ToastUtil;
import cc.a5156.xdkp.diliver.fragment.DiliverFragment;
import cc.a5156.xdkp.me.fragment.MeFragment;
import cc.a5156.xdkp.message.fragment.MessageFragment;
import cc.a5156.xdkp.office.fragment.OfficeFragment;
import cc.a5156.xdkp.receive.fragment.ReceiveFragment;

/**
 * Created by Administrator on 2017/6/6.
 */

public class HomeActivity extends FragmentActivity {
    private MessageFragment messageFragment;
    private ReceiveFragment receiveFragment;
    private DiliverFragment diliverFragment;
//    private OfficeFragment officeFragment;
    private MeFragment meFragment;
    private List<BaseFragment> fragments = new ArrayList<>();
    private CheckBox btMessage;
    private Button btReceive;
    private Button btDiliver;
    private Button btOffice;
    private Button btMe;
    private BaseFragment lastShowFragment;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initFragments();
//        intBottom();


        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.getTabAt(0).setText("消息");
        tabLayout.getTabAt(1).setText("通信");
        tabLayout.getTabAt(2).setText("实名");
//        tabLayout.getTabAt(3).setText("云办公");
        tabLayout.getTabAt(3).setText("我的");
    }


//    private void intBottom() {
//        findViews();
//        setListener();
//    }

//    private void setListener() {
//        btMessage.setOnClickListener(this);
//        btReceive.setOnClickListener(this);
//        btDiliver.setOnClickListener(this);
//        btOffice.setOnClickListener(this);
//        btMe.setOnClickListener(this);
//    }

//    private void findViews() {
//        btMessage = (CheckBox) findViewById(R.id.btMessage);
//        btReceive = (Button) findViewById(R.id.btReceive);
//        btDiliver = (Button) findViewById(R.id.btDiliver);
//        btOffice = (Button) findViewById(R.id.btOffice);
//        btMe = (Button) findViewById(R.id.btMe);
//    }

    private void initFragments() {
        messageFragment = new MessageFragment();
        receiveFragment = new ReceiveFragment();
        diliverFragment = new DiliverFragment();
//        officeFragment = new OfficeFragment();
        meFragment = new MeFragment();
        fragments.add(messageFragment);
        fragments.add(receiveFragment);
        fragments.add(diliverFragment);
//        fragments.add(officeFragment);
        fragments.add(meFragment);

//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.containner, messageFragment);
//        fragmentTransaction.add(R.id.containner, receiveFragment);
//        fragmentTransaction.add(R.id.containner, diliverFragment);
//        fragmentTransaction.add(R.id.containner, officeFragment);
//        fragmentTransaction.add(R.id.containner, meFragment);
//
//        fragmentTransaction.show(messageFragment);
//        lastShowFragment = messageFragment;
//        fragmentTransaction.commit();
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btMessage:
//                showFragment(messageFragment);
//                break;
//            case R.id.btReceive:
//                showFragment(receiveFragment);
//                break;
//            case R.id.btDiliver:
//                showFragment(diliverFragment);
//                break;
//            case R.id.btOffice:
//                showFragment(officeFragment);
//                break;
//            case R.id.btMe:
//                showFragment(meFragment);
//                break;
//        }
//    }

    private void showFragment(BaseFragment showFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (BaseFragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }
//        fragmentTransaction.hide(lastShowFragment);
        fragmentTransaction.show(showFragment);
        lastShowFragment = showFragment;
        fragmentTransaction.commit();
    }


    private long lastTime = 0;

    @Override
    public void onBackPressed() {
        long currTime = System.currentTimeMillis();
        if (currTime - lastTime < 2000) {
            finish();
            System.exit(0);
        } else {
            ToastUtil.showToast(this, "再按一次退出");
            lastTime = currTime;
        }
    }
}
