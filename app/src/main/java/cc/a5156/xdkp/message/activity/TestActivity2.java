package cc.a5156.xdkp.message.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.a5156.xdkp.R;

/**
 * Created by Administrator on 2017/6/20.
 */

public class TestActivity2 extends Activity {
    @BindView(R.id.lv)
    ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> mData;

    private int number = 20;//每次获取多少条数据
    private int maxpage = 5;//总共有多少页
    private boolean loadfinish = true;
    private View footer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);
        footer = getLayoutInflater().inflate(R.layout.footer, null);
        mData = new ArrayList<>(loadData(0, 20));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                final int loadtotal = totalItemCount;
                int lastItemid = listView.getLastVisiblePosition();//获取当前屏幕最后Item的ID
                if ((lastItemid + 1) == totalItemCount) {//达到数据的最后一条记录
                    if (totalItemCount > 0) {
                        //当前页
                        int currentpage = totalItemCount % number == 0 ? totalItemCount / number : totalItemCount / number + 1;
                        int nextpage = currentpage + 1;//下一页
                        if (nextpage <= maxpage && loadfinish) {
                            loadfinish = false;
                            listView.addFooterView(footer);

                            new Thread(new Runnable() {
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    List<String> result = loadData(loadtotal, number);
                                    handler.sendMessage(handler.obtainMessage(100, result));
                                }
                            }).start();
                        }
                    }

                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = null;
                boolean b = s.equals("sss");
            }
        });
    }

    private List<String> loadData(int offsert, int number) {
        List<String> data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            data.add("大帅哥" + i);
        }
        return data;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            mData.addAll((List<String>) msg.obj);
            adapter.notifyDataSetChanged();//告诉ListView数据已经发生改变，要求ListView更新界面显示
            if (listView.getFooterViewsCount() > 0) listView.removeFooterView(footer);
            loadfinish = true;
        }
    };

}
