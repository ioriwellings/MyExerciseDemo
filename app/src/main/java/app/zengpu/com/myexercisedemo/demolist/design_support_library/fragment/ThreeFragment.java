package app.zengpu.com.myexercisedemo.demolist.design_support_library.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.zengpu.com.myexercisedemo.R;


/**
 * Created by linhonghong on 2015/8/11.
 */
public class ThreeFragment extends Fragment {

    public static ThreeFragment instance() {
        ThreeFragment view = new ThreeFragment();
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, null);
        return view;
    }

}