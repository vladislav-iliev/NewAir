package com.example.newair.data.sensors.history_data;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.newair.activity.MainActivity;
import com.example.newair.data.sensors.SensorDataManager;
import com.example.newair.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Accessor to Urban Observatory's history data
 *
 * @author Liam Stannard, Vladislav Iliev
 */
public class HistoryJSONLoader extends AsyncTask<Void, Void, double[][]> {
    /**
     * Listener for the end of the fetching process.
     * Is called in onPostExecute.
     */
    public interface OnHistorySensorsDownloadListener {
        void onHistorySensorsDownloaded(double[][] measures);
    }

    private final OnHistorySensorsDownloadListener delegate;

    private DateFormat HYPHENATED_FORMAT;
    private DateFormat CONDENSED_FORMAT;
    // Keywords to match the APIs JSON
    private String OBSERVATORY_URL;
    private String API_KEY;
    private String VARIABLE_QUERY_FORMAT;
    private String THREE_VARIABLE_FORMAT;
    private String START_END_TIME_FORMAT;
    private String DATA_KEY;
    private String PM25_VARIABLE;
    private String PM10_VARIABLE;
    private String O3_VARIABLE;
    // No measurements over a limit
    private int MEASUREMENTS_LIMIT_UPPER;
    private int ARRAYS_LENGTH;
    private int MEASUREMENTS_POSITION;
    private int MEASUREMENTS_NUMBER_POSITION;
    private int INVALID_INTEGER;
    private int APPROXIMATES_ARRAY_SIZE;
    private int APPROXIMATES_PM25_POSITION;
    private int APPROXIMATES_PM10_POSITION;
    private int APPROXIMATES_O3_POSITION;
    // Pollutant arrays are split for each day of the week,
    // each day having two elements - the sum of all pollution readings
    // for the day, and the number of readings for the day. Dividing the first
    // by the second will give the approximate for each day
    private List<Date> dates;
    private double[][] pm25;
    private double[][] pm10;
    private double[][] o3;
    private boolean successfulLoad;

    public HistoryJSONLoader(final MainActivity activity,
                             final OnHistorySensorsDownloadListener delegate,
                             final Date startDate,
                             final Date endDate) {
        initializeStrings(activity);
        initializeDatesList(startDate, endDate);
        initializeLists();
        this.delegate = delegate;
        successfulLoad = true;
    }

    /**
     * Initialize all URL strings
     *
     * @param activity the activity to access resources from
     */
    private void initializeStrings(Activity activity) {
        HYPHENATED_FORMAT = new SimpleDateFormat(activity.getString(R.string.date_hyphenated_format), Locale.UK);
        CONDENSED_FORMAT = new SimpleDateFormat(activity.getString(R.string.date_condensed_format), Locale.UK);

        OBSERVATORY_URL = activity.getString(R.string.history_address);
        API_KEY = activity.getString(R.string.api_key);
        VARIABLE_QUERY_FORMAT = activity.getString(R.string.variable_format);
        THREE_VARIABLE_FORMAT = activity.getString(R.string.three_variables_format);
        START_END_TIME_FORMAT = activity.getString(R.string.start_end_time_format);

        DATA_KEY = activity.getString(R.string.json_data_key);

        PM25_VARIABLE = activity.getString(R.string.pm25_variable);
        PM10_VARIABLE = activity.getString(R.string.pm10_variable);
        O3_VARIABLE = activity.getString(R.string.o3_variable);
        MEASUREMENTS_LIMIT_UPPER = activity.getResources().getInteger(R.integer.measurement_limit_upper);

        ARRAYS_LENGTH = activity.getResources().getInteger(R.integer.json_history_array_elements);
        MEASUREMENTS_POSITION = activity.getResources().getInteger(R.integer.json_history_measurements_position);
        MEASUREMENTS_NUMBER_POSITION = activity.getResources().getInteger(R.integer.json_history_measurements_number_position);
        INVALID_INTEGER = activity.getResources().getInteger(R.integer.invalid_integer);

        APPROXIMATES_ARRAY_SIZE = activity.getResources().getInteger(R.integer.json_history_approximates_array_size);
        APPROXIMATES_PM25_POSITION = activity.getResources().getInteger(R.integer.json_history_approximates_pm25_position);
        APPROXIMATES_PM10_POSITION = activity.getResources().getInteger(R.integer.json_history_approximates_pm10_position);
        APPROXIMATES_O3_POSITION = activity.getResources().getInteger(R.integer.json_history_approximates_o3_position);
    }

    /**
     * Calculate the dates between a starting and ending date. Used
     * in {@link #buildURL()} to set up the URL dates.
     *
     * @param startDate the starting date
     * @param endDate   the ending date
     */
    private void initializeDatesList(Date startDate, Date endDate) {
        dates = new ArrayList<>();
        dates.add(startDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        calendar.add(Calendar.DAY_OF_YEAR, -1);
        while (!calendar.getTime().before(endDate)) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
    }

    /**
     * Initialize all buffer pollution lists
     */
    private void initializeLists() {
        pm25 = new double[dates.size()][ARRAYS_LENGTH];
        pm10 = new double[dates.size()][ARRAYS_LENGTH];
        o3 = new double[dates.size()][ARRAYS_LENGTH];

        // Fill the arrays with random a invalid integer
        for (int i = 0; i < pm25.length; i++) {
            for (int j = 0; j < pm25[0].length; j++) {
                pm25[i][j] = INVALID_INTEGER;
                pm10[i][j] = INVALID_INTEGER;
                o3[i][j] = INVALID_INTEGER;
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected double[][] doInBackground(Void... voids) {
        getSensors(readHTML(buildURL()));
        return getApproximates();
    }

    @Override
    protected void onPostExecute(double[][] doubles) {
        delegate.onHistorySensorsDownloaded(getApproximates());
        super.onPostExecute(doubles);
    }

    /**
     * Set up the JSON url
     *
     * @return the JSON url
     */
    private URL buildURL() {
        URL url = null;
        try {
            final String urlString = OBSERVATORY_URL
                    + API_KEY
                    + String.format(
                    VARIABLE_QUERY_FORMAT,
                    String.format(
                            THREE_VARIABLE_FORMAT,
                            PM25_VARIABLE,
                            PM10_VARIABLE,
                            O3_VARIABLE))
                    + String.format(
                    START_END_TIME_FORMAT,
                    CONDENSED_FORMAT.format(
                            dates.get(dates.size() - 1)),
                    CONDENSED_FORMAT.format(
                            dates.get(0)));
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Return the URL contents as String
     *
     * @param url the URL
     * @return the contents as String
     */
    private String readHTML(final URL url) {
        String urlContents = "";
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            final StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            urlContents = stringBuilder.toString();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlContents;
    }

    /**
     * Fill the pollutant arrays with data
     *
     * @param jsonString the HTML JSON string
     */
    private void getSensors(String jsonString) {
        try {
            // Try to parse the whole HTML contents into an array
            final JSONArray sensors = new JSONArray(jsonString);
            JSONObject sensor;
            JSONObject data;

            // Will split pollutants from each sensor
            Iterator<String> typesIterator;
            // Will split days from each sensor
            Iterator<String> datesIterator;
            String type;
            String date;

            double measure;
            // According to its date, every measure is added to a
            // specific position in the array
            double[][] arrayToAddTo;
            int arrayPosition;

            for (int i = 0; i < sensors.length(); i++) {
                sensor = sensors.getJSONObject(i);
                typesIterator = sensor.getJSONObject(DATA_KEY).keys();

                while (typesIterator.hasNext()) {
                    type = (String) typesIterator.next();
                    data = sensor.getJSONObject(DATA_KEY)
                            .getJSONObject(type).getJSONObject(DATA_KEY);

                    datesIterator = data.keys();
                    while (datesIterator.hasNext()) {
                        try {
                            date = (String) datesIterator.next();
                            measure = data.getDouble(date);

                            // Don't count weird readings
                            if (measure < 0 || measure > MEASUREMENTS_LIMIT_UPPER) {
                                continue;
                            }

                            arrayToAddTo = getArrayToAddTo(type);
                            arrayPosition = getArrayPosition(date);

                            arrayToAddTo[arrayPosition][MEASUREMENTS_POSITION] += measure;
                            arrayToAddTo[arrayPosition][MEASUREMENTS_NUMBER_POSITION] += 1;
                        } catch (IllegalArgumentException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // Because the arrays were at first filled with a negative value,
            // add the difference below zero to compensate
            int compensateInitialValues = -INVALID_INTEGER;
            for (int i = 0; i < pm25.length; i++) {
                for (int j = 0; j < pm25[0].length; j++) {
                    pm25[i][j] += compensateInitialValues;
                    pm10[i][j] += compensateInitialValues;
                    o3[i][j] += compensateInitialValues;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            successfulLoad = false;
        }
    }

    /**
     * Get a pollutant's position in its array, according to the parsed reading date.
     */
    private int getArrayPosition(final String jsonDate) throws ParseException {
        return dates.indexOf(HistoryData.dateNullifyTime(HYPHENATED_FORMAT.parse(jsonDate)));
    }

    /**
     * Get a pollutant array, according to a parsed pollution type.
     */
    private double[][] getArrayToAddTo(final String jsonType) throws IllegalArgumentException {
        final String type = jsonType.toLowerCase();
        final double[][] arrayToAddTo;

        if (type.equals(PM25_VARIABLE.toLowerCase())) {
            arrayToAddTo = pm25;
        } else if (type.equals(PM10_VARIABLE.toLowerCase())) {
            arrayToAddTo = pm10;
        } else if (type.equals(O3_VARIABLE.toLowerCase())) {
            arrayToAddTo = o3;
        } else {
            throw new IllegalArgumentException();
        }

        return arrayToAddTo;
    }

    /**
     * Get the average readings for each pollutant for each day.
     *
     * @return Array with size equal to dates,
     * with each day having size equal to number of pollutants.
     */
    private double[][] getApproximates() {
        final double[][] approximates = new double[dates.size()][APPROXIMATES_ARRAY_SIZE];

        // If the load was not successful, fill the array with an invalid integer
        if (!successfulLoad) {
            for (final double[] approximate : approximates) Arrays.fill(approximate, INVALID_INTEGER);
            return approximates;
        }
        for (int i = 0; i < dates.size(); i++) {
            approximates[i][APPROXIMATES_PM25_POSITION] = SensorDataManager.roundToFirstDigit(pm25[i][MEASUREMENTS_POSITION] / pm25[i][MEASUREMENTS_NUMBER_POSITION]);
            approximates[i][APPROXIMATES_PM10_POSITION] = SensorDataManager.roundToFirstDigit(pm10[i][MEASUREMENTS_POSITION] / pm10[i][MEASUREMENTS_NUMBER_POSITION]);
            approximates[i][APPROXIMATES_O3_POSITION] = SensorDataManager.roundToFirstDigit(o3[i][MEASUREMENTS_POSITION] / o3[i][MEASUREMENTS_NUMBER_POSITION]);
        }
        return approximates;
    }
}