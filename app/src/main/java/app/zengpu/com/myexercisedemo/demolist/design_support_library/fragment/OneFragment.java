package app.zengpu.com.myexercisedemo.demolist.design_support_library.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.zengpu.com.myexercisedemo.R;
import app.zengpu.com.myexercisedemo.demolist.cardlistview.CardListViewActivity;
import app.zengpu.com.myexercisedemo.demolist.design_support_library.adapter.OneRecyclerViewAdapter;
import app.zengpu.com.myexercisedemo.demolist.glide_with_progress.ProgressLoadingActivity;
import app.zengpu.com.myexercisedemo.demolist.imageview_crop.CropImageViewActivity;
import app.zengpu.com.myexercisedemo.demolist.matrix.MatrixActivity;
import app.zengpu.com.myexercisedemo.demolist.multi_drawer.MultiDrawerActivity;
import app.zengpu.com.myexercisedemo.demolist.photoloop0.PhotoLoopActivity;
import app.zengpu.com.myexercisedemo.demolist.photoloop1.ImageLoopActivity;
import app.zengpu.com.myexercisedemo.demolist.pull_to_refresh.RefreshAndLoadActivity;
import app.zengpu.com.myexercisedemo.demolist.recyclerViewPager.RecyclerViewPagerActivity;
import app.zengpu.com.myexercisedemo.demolist.recyclerViewPager.snap.SnapHelperActivity;
import app.zengpu.com.myexercisedemo.demolist.recyclerViewPager.ThreeDViewPagerActivity;
import app.zengpu.com.myexercisedemo.demolist.rich_textview.RichTextViewActivity;
import app.zengpu.com.myexercisedemo.demolist.selected_textview.SelectedTextViewActivity;
import app.zengpu.com.myexercisedemo.demolist.sensor.SensorActivity;
import app.zengpu.com.myexercisedemo.demolist.snakeview.SnakeViewActivity;
import app.zengpu.com.myexercisedemo.demolist.svg_path_anim.SvgAnimActivity;
import app.zengpu.com.myexercisedemo.demolist.vertical_textview.VerticalTextViewActivity;
import app.zengpu.com.myexercisedemo.demolist.videoRecord.VideoAppendActivity;
import app.zengpu.com.myexercisedemo.demolist.imageview_scale.ScaleImageViewActivity;
import app.zengpu.com.myexercisedemo.demolist.wheel_picker.WheelPickerActivity;


/**
 * Created by linhonghong on 2015/8/11.
 */
public class OneFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private OneRecyclerViewAdapter mAdapter;
    private List<String[]> list = new ArrayList<>();

    public static OneFragment instance() {
        OneFragment view = new OneFragment();
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_one, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initView();
    }

    private void initView() {

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new OneRecyclerViewAdapter(getContext(), list);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OneRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    Class<?> activityClazz = Class.forName(list.get(position)[1]);
                    Intent intent = new Intent(getContext(), activityClazz);
                    startActivity(intent);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initData() {
        list.add(new String[]{"muti drawer ", MultiDrawerActivity.class.getName()});
        list.add(new String[]{"image loop:ViewPager+Handler", PhotoLoopActivity.class.getName()});
        list.add(new String[]{"image loop:ViewPager+timer", ImageLoopActivity.class.getName()});
        list.add(new String[]{"refresh and load ", RefreshAndLoadActivity.class.getName()});
        list.add(new String[]{"video record", VideoAppendActivity.class.getName()});
        list.add(new String[]{"RecyclerViewPager", RecyclerViewPagerActivity.class.getName()});
        list.add(new String[]{"ThreeDViewPagerActivity", ThreeDViewPagerActivity.class.getName()});
        list.add(new String[]{"SnapHelperActivity", SnapHelperActivity.class.getName()});
        list.add(new String[]{"SelectedTextView", SelectedTextViewActivity.class.getName()});
        list.add(new String[]{"SvgPathAnimView", SvgAnimActivity.class.getName()});
        list.add(new String[]{"VerticalTextView", VerticalTextViewActivity.class.getName()});
        list.add(new String[]{"RichTextView", RichTextViewActivity.class.getName()});
        list.add(new String[]{"ProgressLoading", ProgressLoadingActivity.class.getName()});
        list.add(new String[]{"cardlistview", CardListViewActivity.class.getName()});
        list.add(new String[]{"SnakeView", SnakeViewActivity.class.getName()});
        list.add(new String[]{"ScaleImageView", ScaleImageViewActivity.class.getName()});
        list.add(new String[]{"CropImageView", CropImageViewActivity.class.getName()});
        list.add(new String[]{"WheelPicker", WheelPickerActivity.class.getName()});
        list.add(new String[]{"Matrix", MatrixActivity.class.getName()});
        list.add(new String[]{"sensor", SensorActivity.class.getName()});
    }
}
