/*
 * Copyright  2017  zengp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.zengpu.com.myexercisedemo.demolist.wheel_picker;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.zengpu.com.myexercisedemo.R;
import app.zengpu.com.myexercisedemo.demolist.wheel_picker.bean.Data;
import app.zengpu.com.myexercisedemo.demolist.wheel_picker.parser.DataParser;
import app.zengpu.com.myexercisedemo.demolist.wheel_picker.widget.RecyclerWheelPicker;
import app.zengpu.com.myexercisedemo.demolist.wheel_picker.dialog.WheelPicker;

/**
 * Created by zengp on 2017/11/26.
 */

@SuppressLint("ValidFragment")
public class TripleWheelPicker extends WheelPicker {

    private TextView tv_cancel, tv_ok;
    protected RecyclerWheelPicker rv_picker1, rv_picker2, rv_picker3;
    protected String pickData1 = "", pickData2 = "", pickData3 = "";
    protected String unit1 = "", unit2 = "", unit3 = "";
    protected List<Data> datas = new ArrayList<>();

    protected TripleWheelPicker(Builder builder) {
        super(builder);
    }

    public static Builder instance() {
        return new Builder<TripleWheelPicker>(TripleWheelPicker.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (builder.gravity == Gravity.BOTTOM) window.setGravity(Gravity.BOTTOM);
        View contentView = inflater.inflate(R.layout.dialog_wheel_picker_triple, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tv_ok = (TextView) getView().findViewById(R.id.tv_ok);
        tv_cancel = (TextView) getView().findViewById(R.id.tv_cancel);
        tv_ok.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

        rv_picker1 = (RecyclerWheelPicker) getView().findViewById(R.id.rv_picker1);
        rv_picker2 = (RecyclerWheelPicker) getView().findViewById(R.id.rv_picker2);
        rv_picker3 = (RecyclerWheelPicker) getView().findViewById(R.id.rv_picker3);
        rv_picker1.setOnWheelScrollListener(this);
        rv_picker2.setOnWheelScrollListener(this);
        rv_picker3.setOnWheelScrollListener(this);

        parseData();
        inflateData();
    }

    @Override
    protected void parseData() {
        // parse data
        datas = DataParser.parserData(getContext(), builder.resInt, builder.isAll);
    }

    @Override
    protected void inflateData() {
        // setview
        List<Data> datas2 = new ArrayList<>(), datas3 = new ArrayList<>();
        // units
        String[] units = builder.units;
        if (null != units) {
            if (units.length > 0) unit1 = units[0];
            if (units.length > 1) unit2 = units[1];
            if (units.length > 2) unit3 = units[2];
        }
        // default position. find by defPosition firstly, then defValues
        int defP1 = 0, defP2 = 0, defP3 = 0;
        if (datas.size() > 0) {
            int[] defPosition = builder.defPosition;
            if (null != defPosition) {
                if (defPosition.length > 0) defP1 = defPosition[0];
                if (defPosition.length > 1) defP2 = defPosition[1];
                if (defPosition.length > 2) defP3 = defPosition[2];
            }
            defP1 = Math.min(Math.max(0, defP1), datas.size() - 1);
            pickData1 = datas.get(defP1).data;
            datas2 = datas.get(defP1).items;
            if (null != datas2 && datas2.size() > 0) {
                defP2 = Math.min(Math.max(0, defP2), datas2.size() - 1);
                pickData2 = datas2.get(defP2).data;
                datas3 = datas2.get(defP2).items;
                if (null != datas3 && datas3.size() > 0) {
                    defP3 = Math.min(Math.max(0, defP3), datas3.size() - 1);
                    pickData3 = datas3.get(defP3).data;
                }
            }
        }
        String[] defValues = builder.defValues;
        if (datas.size() > 0 && null != defValues) {
            if (defValues.length > 0) {
                for (int i = 0; i < datas.size(); i++) {
                    if (defValues[0].equals(datas.get(i).data)) {
                        defP1 = i;
                        pickData1 = datas.get(defP1).data;
                        break;
                    }
                }
            }
            datas2 = datas.get(defP1).items;
            if (null != datas2 && datas2.size() > 0 && defValues.length > 1) {
                for (int i = 0; i < datas2.size(); i++) {
                    if (defValues[1].equals(datas2.get(i).data)) {
                        defP2 = i;
                        pickData2 = datas2.get(defP2).data;
                        break;
                    }
                }
                datas3 = datas2.get(defP2).items;
                if (null != datas3 && datas3.size() > 0 && defValues.length > 2) {
                    for (int i = 0; i < datas3.size(); i++) {
                        if (defValues[1].equals(datas3.get(i).data)) {
                            defP3 = i;
                            pickData3 = datas3.get(defP3).data;
                            break;
                        }
                    }
                }
            }
        }
        rv_picker1.setUnit(datas.get(defP1).id == 0 ? "" : unit1);
        rv_picker2.setUnit(datas.get(defP1).id == 0 ? "" : unit2);
        rv_picker3.setUnit(datas.get(defP1).id == 0 ? "" : unit3);
        rv_picker3.setData(datas3);
        rv_picker3.scrollTargetPositionToCenter(defP3);
        rv_picker2.setData(datas2);
        rv_picker2.scrollTargetPositionToCenter(defP2);
        rv_picker1.setData(datas);
        rv_picker1.scrollTargetPositionToCenter(defP1);
    }

    @Override
    public void onWheelScrollChanged(RecyclerWheelPicker wheelPicker, boolean isScrolling, int position, Data data) {
        super.onWheelScrollChanged(wheelPicker, isScrolling, position, data);
        if (!rv_picker1.isInitFinish()
                || !rv_picker2.isInitFinish()
                || !rv_picker3.isInitFinish())
            return;

        if (wheelPicker == rv_picker1) {
            if (!isScrolling && null != data) {
                pickData1 = data.data;
                rv_picker1.setUnit(data.id == 0 ? "" : unit1);
                rv_picker2.setUnit(data.id == 0 ? "" : unit2);
                rv_picker3.setUnit(data.id == 0 ? "" : unit3);
                rv_picker2.setData(data.items);
            } else {
                pickData1 = "";
            }
        } else if (wheelPicker == rv_picker2) {
            if (!isScrolling && null != data) {
                pickData2 = data.data;
                rv_picker3.setData(data.items);
            } else {
                pickData2 = "";
            }
        } else if (wheelPicker == rv_picker3) {
            if (!isScrolling && null != data)
                pickData3 = data.data;
            else pickData3 = "";
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_ok) {
            if (!rv_picker1.isScrolling()
                    && !rv_picker2.isScrolling()
                    && !rv_picker3.isScrolling()
                    && null != builder.pickerListener) {
                builder.pickerListener.onPickResult(pickData1, pickData2, pickData3);
                dismiss();
            }
        } else {
            dismiss();
        }
    }

    @Override
    protected void pickerClose() {
        super.pickerClose();
        rv_picker1.release();
        rv_picker2.release();
        rv_picker3.release();
    }
}