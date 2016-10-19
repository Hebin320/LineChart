package com.hebin.linechart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.rl_linechart)
    RelativeLayout rlLinechart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setView();
    }

    private void setView() {
        List<String> xLineDate = new ArrayList<>();
        List<Integer> yLineDate = new ArrayList<>();
        List<Integer> lineOneDate = new ArrayList<>();
        List<Integer> lineTwoDate = new ArrayList<>();
        int[] oneInt = {24,35,43,16,24,37,56};
        int[] twoInt = {64,15,23,46,54,47,26};
        int[] yInt = {10,20,30,40,50,60,70};
        String[] xString = {"周一","周二","周三","周四","周五","周六","周日"};
        for (int i = 0; i <oneInt.length ; i++) {
            xLineDate.add(xString[i]);
            yLineDate.add(yInt[i]);
            lineOneDate.add(oneInt[i]);
            lineTwoDate.add(twoInt[i]);
        }
        rlLinechart.addView(new LineChartView(this,lineOneDate,lineTwoDate,xLineDate,yLineDate));

    }
}
