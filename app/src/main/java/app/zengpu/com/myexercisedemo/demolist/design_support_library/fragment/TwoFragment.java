package app.zengpu.com.myexercisedemo.demolist.design_support_library.fragment;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.zengpu.com.myexercisedemo.R;
import app.zengpu.com.myexercisedemo.demolist.design_support_library.activity.DSLScrollingActivity;
import app.zengpu.com.myexercisedemo.demolist.design_support_library.adapter.TwoRecyclerViewAdapter;
import app.zengpu.com.myexercisedemo.demolist.design_support_library.listener.RecyclerViewItemTouchCallback;


/**
 * Created by linhonghong on 2015/8/11.
 */
public class TwoFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TwoRecyclerViewAdapter mAdapter;
    private List<String> list = new ArrayList<>();
    private List<Drawable> drawablelist = new ArrayList<>();

    public static TwoFragment instance() {
        TwoFragment view = new TwoFragment();
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                initData();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initView();
                    }
                });
            }
        }).start();
    }

    private void initView() {

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        mAdapter = new TwoRecyclerViewAdapter(getContext(), list, drawablelist);

        mRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new RecyclerViewItemTouchCallback(list, mAdapter)).attachToRecyclerView(mRecyclerView);

        mAdapter.setOnItemClickListener(new TwoRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                switch (view.getId()) {
                    case R.id.iv_icon:
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                            BitmapDrawable bd = (BitmapDrawable) drawablelist.get(position);
                            Bitmap icon = bd.getBitmap();
                            DSLScrollingActivity.actionStart((AppCompatActivity) getContext(), view, icon, list.get(position));
                        } else {
                            DSLScrollingActivity.actionStart(getContext());
                        }
                        break;
                }
            }

        });
    }

    private void initData() {
        List<PackageInfo> packages = getContext().getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < packages.size(); i++) {

            PackageInfo packageInfo = packages.get(i);
            String appName = packageInfo.applicationInfo.loadLabel(getContext().getPackageManager()).toString();
            Drawable appIcon = packageInfo.applicationInfo.loadIcon(getContext().getPackageManager());

            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                list.add(appName);
                drawablelist.add(appIcon);
            }
        }
    }
}
