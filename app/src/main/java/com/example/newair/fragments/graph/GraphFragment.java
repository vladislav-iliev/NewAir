package com.example.newair.fragments.graph;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.newair.activity.MainActivity;
import com.example.newair.R;
import com.example.newair.data.SensorViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Graph screen
 * @author Ioannis Gylaris, Vladislav Iliev
 */
public class GraphFragment extends Fragment {
    private MainActivity activity;

    private final Date[] dates = createDates();

    private LineChart lineChart;
    private boolean firstLoad;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (MainActivity) this.getActivity();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_graph, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lineChart = view.findViewById(R.id.line_chart);
        firstLoad = true;
        setUpLineChart();
        updateGraph();
    }

    private Date[] createDates() {
        final Date[] res = new Date[7];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        res[0] = dateNullifyTime(calendar.getTime());
        for (int i = 1; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            res[i] = dateNullifyTime(calendar.getTime());
        }
        return res;
    }

    private Date dateNullifyTime(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Initializes the Date entries
     * @return the list of Date entries
     */
    private List<Entry> initializeEntries() {
        final List<Entry> entryList = new ArrayList<>();
        final int invalidInteger = this.activity.getResources().getInteger(R.integer.invalid_integer);
        for (int i = 0; i < this.dates.length; i++) entryList.add(new Entry(i, invalidInteger));
        return entryList;
    }

    /**
     * Initializes the Graph data set
     * @param entryList the entries for the data set
     * @return the data set
     */
    private LineDataSet initializeDataSet(final List<Entry> entryList) {
        final LineDataSet dataSet = new LineDataSet(entryList, getResources().getString(R.string.line_data_set_label));
        dataSet.setColor(ContextCompat.getColor(activity, R.color.navbarTextLight));
        dataSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.white));
        dataSet.setCircleHoleColor(ContextCompat.getColor(requireContext(), R.color.black));
        dataSet.setCircleRadius(getResources().getInteger(R.integer.graph_circle_radius));
        dataSet.setCircleHoleRadius(getResources().getInteger(R.integer.graph_circle_hole_radius));
        dataSet.setLineWidth(getResources().getInteger(R.integer.graph_line_width));
        dataSet.setHighLightColor(ContextCompat.getColor(requireContext(), R.color.brown_transparent));
        return dataSet;
    }

    /**
     * Merges all data sets in a list (only one data set is needed)
     * @param lineDataSet the data set
     * @return the data sets list
     */
    private List<ILineDataSet> initializeDataSetList(final LineDataSet lineDataSet) {
        final List<ILineDataSet> dataSetList = new ArrayList<>();
        dataSetList.add(lineDataSet);
        return dataSetList;
    }

    /**
     * Initializes the line data
     * @param dataSetList the line sets
     * @return the line data
     */
    private LineData initializeLineData(final List<ILineDataSet> dataSetList) {
        final LineData lineData = new LineData(dataSetList);
        lineData.setValueTextSize(activity.getResources().getInteger(R.integer.graph_text_size));
        lineData.setValueTextColor(ContextCompat.getColor(requireContext(), R.color.backgroundRed));
        return lineData;
    }

    /**
     * Sets up the line chart
     */
    private void setUpLineChart() {
        final List<Entry> entryList = initializeEntries();
        final LineDataSet lineDataSet = initializeDataSet(entryList);
        final List<ILineDataSet> lineDataSetList = initializeDataSetList(lineDataSet);
        final LineData lineData = initializeLineData(lineDataSetList);

        lineChart.setData(lineData);
        lineChart.getLegend().setEnabled(false);
        lineChart.getAxisRight().setDrawLabels(false);

        lineChart.setVisibleYRange(0, activity.getResources().getInteger(R.integer.graph_max_limit), lineDataSet.getAxisDependency());
        initializeDescription(this.lineChart);
        initializeXAxis(this.lineChart);
    }

    /**
     * Sets up the chart description
     * @param lineChart the line chart to add description to
     */
    private void initializeDescription(LineChart lineChart) {
        final Description description = new Description();
        description.setTextSize(activity.getResources().getInteger(R.integer.graph_description_text_size));
        description.setTextColor(ContextCompat.getColor(requireContext(), R.color.backgroundRed));
        description.setText(getResources().getString(R.string.graph_description));
        lineChart.setDescription(description);
    }

    private void initializeXAxis(final LineChart lineChart) {
        final XAxis x_axis = lineChart.getXAxis();
        x_axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        x_axis.setDrawGridLines(false);
        x_axis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return formatDateDate(dates[dates.length - (int) value - 1]);
            }
        });
        x_axis.setLabelRotationAngle(activity.getResources().getInteger(R.integer.graph_dates_angle));
    }

    /**
     * Converts a date to a String
     * @param date the date
     * @return the formatted String
     */
    public String formatDateDate(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format(
                Locale.UK,
                getString(R.string.date_format),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.UK));
    }

    /**
     * Updates the Graph
     */
    private void updateGraph() {
        final SensorViewModel viewModel = new ViewModelProvider(requireActivity()).get(SensorViewModel.class);
        final ILineDataSet lineDataSet = this.lineChart.getLineData().getDataSetByIndex(0);
        final List<Double> pm10 = viewModel.getUiState().getValue().getHistoryData();

        if (firstLoad) {
            for (int i = 0; i < lineDataSet.getEntryCount() - 1; i++)
                lineDataSet.getEntryForIndex(i).setY(pm10.get(pm10.size() - 1 - i).floatValue());
            firstLoad = false;
        }

        lineDataSet.getEntryForIndex(lineDataSet.getEntryCount() - 1).setY(pm10.get(0).floatValue());
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }
}