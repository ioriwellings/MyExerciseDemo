package app.zengpu.com.myexercisedemo.demolist.pull_to_refresh.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Date;

import app.zengpu.com.myexercisedemo.R;
import app.zengpu.com.myexercisedemo.demolist.pull_to_refresh.view.RefreshAndLoadTextView;
import app.zengpu.com.myexercisedemo.demolist.pull_to_refresh.view.core.RefreshAndLoadViewBase;

/**
 * Created by zengpu on 2016/7/21.
 */
public class TextViewActivity extends AppCompatActivity implements
        RefreshAndLoadViewBase.OnRefreshListener, RefreshAndLoadViewBase.OnLoadListener {

    private RefreshAndLoadTextView refreshAndLoadTextView;
    private TextView textView;
    private String text = "<<<<<<<测试>>>>>";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_textview);

        refreshAndLoadTextView = (RefreshAndLoadTextView) findViewById(R.id.rllv_list);
        refreshAndLoadTextView.setOnRefreshListener(this);
        refreshAndLoadTextView.setOnLoadListener(this);
//        refreshAndLoadTextView.setCanLoad(false);

        textView = (TextView) findViewById(R.id.tv_list);
        textView.setText(text);
    }


    int textFlag = 0;

    @Override
    public void onRefresh() {

        refreshAndLoadTextView.postDelayed(new Runnable() {
            @Override
            public void run() {


                // 加载失败
                if (textFlag == 0) {
                    refreshAndLoadTextView.refreshAndLoadFailure();
                    text = text + "\n" + "下拉刷新：加载失败 "  + new Date().toLocaleString();
                    textFlag = 1;
                    return;
                }
                // 加载成功
                if (textFlag == 1) {
                    // 更新数据
                    text = text + "\n" + "下拉刷新：刷新成功 " + new Date().toLocaleString();
                    textView.setText(text);
                    // 更新完后调用该方法结束刷新
                    refreshAndLoadTextView.refreshComplete();
                    textFlag = 2;
                    return;
                }
                // 没有更多数据
                if (textFlag == 2) {
                    refreshAndLoadTextView.refreshAndLoadNoMore();
                    text = text + "\n" + "下拉刷新：数据最新 "  + new Date().toLocaleString();
                    textFlag = 0;
                    return;
                }
            }
        }, 1500);

    }

    @Override
    public void onLoad() {
        refreshAndLoadTextView.postDelayed(new Runnable() {
            @Override
            public void run() {

                // 加载失败
                if (textFlag == 0) {
                    refreshAndLoadTextView.refreshAndLoadFailure();
                    text = text + "\n" + "上拉加载：加载失败 "  + new Date().toLocaleString();
                    textFlag = 1;
                    return;
                }
                // 加载成功
                if (textFlag == 1) {
                    // 更新数据
                    text = text + "\n" + "上拉加载：加载成功 " + new Date().toLocaleString();
                    textView.setText(text);
                    // 更新完后调用该方法结束刷新
                    refreshAndLoadTextView.loadCompelte();
                    textFlag = 2;
                    return;
                }
                // 没有更多数据
                if (textFlag == 2) {
                    refreshAndLoadTextView.refreshAndLoadNoMore();
                    text = text + "\n" + "上拉加载：没有更多 "  + new Date().toLocaleString();
                    textFlag = 0;
                    return;
                }

            }
        }, 1500);

    }
}
