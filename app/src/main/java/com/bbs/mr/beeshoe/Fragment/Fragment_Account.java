package com.bbs.mr.beeshoe.Fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bbs.mr.beeshoe.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_Account extends Fragment {

    TextView name, email, date, address;
    SharedPreferences pref;
    CombinedChart mChart;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.account, container, false);

        pref = getActivity().getSharedPreferences("USER", MODE_PRIVATE);
        name = view.findViewById(R.id.tvAccName);
        email = view.findViewById(R.id.tvAccEmail);
        date = view.findViewById(R.id.tvAccDate);
        address = view.findViewById(R.id.tvAccAddress);
        name.setText(pref.getString("NAME", null));
        email.setText(pref.getString("EMAIL", null));
        date.setText(pref.getString("DATE", null));
        address.setText(pref.getString("ADDRESS", null));
        mChart = view.findViewById(R.id.mChart);
        SetChart();

        return view;
    }

    private void SetChart() {
        mChart.getDescription().setEnabled(false);
        //mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        final List<String> xLabel = new ArrayList<>();
        xLabel.add("Jan");
        xLabel.add("Feb");
        xLabel.add("Mar");
        xLabel.add("Apr");
        xLabel.add("May");
        xLabel.add("Jun");
        xLabel.add("Jul");
        xLabel.add("Aug");
        xLabel.add("Sep");
        xLabel.add("Oct");
        xLabel.add("Nov");
        xLabel.add("Dec");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.YELLOW);

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        };
        xAxis.setValueFormatter(formatter);

        CombinedData data = new CombinedData();
        LineData d = new LineData();
        int[] mdata =  new int[]{120000,50000,100000,45000,50000,65000,200000,500000,150000,30000,100000,30000};
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int index = 0; index < 12; index++) {
            entries.add(new Entry(index, mdata[index]));
        }
        LineDataSet set = new LineDataSet(entries, "Thống kê chi tiêu trên cửa hàng");
        set.setColor(Color.BLACK);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.BLACK);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.YELLOW);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet((ILineDataSet)set);
        data.setData(d);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();

    }
    public int[] getThang(){
        /*list = new int[13];
        for (int i = 0; i <13 ; i++){
            list[i] =  db.getDoanhThuThang(String.valueOf(i));
        }
        return list;*/
        return null;
    }
}

