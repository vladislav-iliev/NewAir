package com.example.newair.data.sensors.live_data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.newair.activity.MainActivity;
import com.example.newair.data.sensors.Sensor;
import com.example.newair.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Accessor to Urban Observatory's live data
 *
 * @author Liam Stannard, Vladislav Iliev
 */
public class LiveJSONLoader extends AsyncTask<Void, Void, List<Sensor>> {

    public interface OnLiveSensorsDownloadListener {
        void onLiveSensorsDownloaded(List<Sensor> sensorList);
    }

    private final OnLiveSensorsDownloadListener delegate;
    // Keywords to match the Observatory's API
    private final String OBSERVATORY_URL;
    private final String API_KEY;
    private final String VARIABLE_QUERY_FORMAT;
    private final String BUFFER_QUERY_FORMAT;
    private final String TWO_VARIABLE_FORMAT;
    private final String IS_ACTIVE_KEY;
    private final String DATA_KEY;
    private final String GEOM_KEY;
    private final String COORDINATES_KEY;
    private final String PM10_VARIABLE;
    private final String TEMP_VARIABLE;
    private final String HUMID_VARIABLE;
    // No measurements over a limit
    private final int MEASUREMENTS_LIMIT_UPPER;
    private final String NO_JSON_OBJECT_FOUND_LOG_TITLE;
    private final String NO_JSON_OBJECT_FOUND_LOG_CONTENTS;
    // Temperature and Humidity sensors require different URL builds, fetch them
    // separately with different URLs
    private final List<Sensor> sensorsPm10;
    private final List<Sensor> sensorsTempHumid;

    public LiveJSONLoader(final MainActivity activity, final OnLiveSensorsDownloadListener onLiveSensorsDownloadListener) {
        OBSERVATORY_URL = activity.getString(R.string.live_address);
        API_KEY = activity.getString(R.string.api_key);
        VARIABLE_QUERY_FORMAT = activity.getString(R.string.variable_format);
        BUFFER_QUERY_FORMAT = activity.getString(R.string.buffer_query);
        TWO_VARIABLE_FORMAT = activity.getString(R.string.two_variables_format);

        IS_ACTIVE_KEY = activity.getString(R.string.json_is_active_key);
        DATA_KEY = activity.getString(R.string.json_data_key);
        GEOM_KEY = activity.getString(R.string.json_geom_key);
        COORDINATES_KEY = activity.getString(R.string.json_coordinates_key);

        PM10_VARIABLE = activity.getString(R.string.pm10_variable);
        TEMP_VARIABLE = activity.getString(R.string.temperature_variable);
        HUMID_VARIABLE = activity.getString(R.string.humidity_variable);
        MEASUREMENTS_LIMIT_UPPER = activity.getResources().getInteger(R.integer.measurement_limit_upper);

        NO_JSON_OBJECT_FOUND_LOG_TITLE = activity.getString(R.string.no_json_object_found_log_title);
        NO_JSON_OBJECT_FOUND_LOG_CONTENTS = activity.getString(R.string.no_json_object_found_log_contents);

        sensorsPm10 = new ArrayList<>();
        sensorsTempHumid = new ArrayList<>();
        delegate = onLiveSensorsDownloadListener;
    }

    @Override
    protected List<Sensor> doInBackground(final Void... voids) {
        getPM10();
        getTemperatureAndHumidity();
        // No need to separate measurements anymore, they will be redistributed
        // at  LiveSensorLists.addSensor()
        sensorsPm10.addAll(sensorsTempHumid);
        return sensorsPm10;
    }

    @Override
    protected void onPostExecute(final List<Sensor> sensorList) {
        delegate.onLiveSensorsDownloaded(sensorList);
        super.onPostExecute(sensorList);
    }

    /**
     * Return the URL contents as String
     *
     * @param url the URL
     * @return the contents as String
     */
    private String readHTML(URL url) {
        String urlContents = "";
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            final StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append("\n");

            urlContents = stringBuilder.toString();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlContents;
    }

    /**
     * Fetches all live PM10 pollution data
     */
    private void getPM10() {
        sensorsPm10.addAll(getSensors(readHTML(buildPm10URL())));
    }

    /**
     * Sets up the live PM10 pollution URL
     *
     * @return the live PM10 pollution URL
     */
    private URL buildPm10URL() {
        URL url = null;
        try {
            url = new URL(OBSERVATORY_URL + API_KEY + String.format(VARIABLE_QUERY_FORMAT, PM10_VARIABLE));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Fetches all live Temperature and Humidity data
     */
    private void getTemperatureAndHumidity() {
        // Get Temp and Humid sensors only at PM10 locations, otherwise
        // the connection takes too long to load all
        for (final Sensor pm10sensor : sensorsPm10)
            sensorsTempHumid.addAll(getSensors(readHTML(buildURLTempHumid(pm10sensor.getLatLng()))));
    }

    /**
     * Sets up the live Temperature and Humidity URL
     *
     * @return the live Temperature and Humidity URL
     */
    private URL buildURLTempHumid(LatLng pm10LatLong) {
        URL url = null;
        try {
            url = new URL(OBSERVATORY_URL + API_KEY
                    + String.format(VARIABLE_QUERY_FORMAT, String.format(TWO_VARIABLE_FORMAT, TEMP_VARIABLE, HUMID_VARIABLE))
                    + String.format(BUFFER_QUERY_FORMAT, pm10LatLong.longitude, pm10LatLong.latitude, 5));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Fill the data arrays with live data
     *
     * @param jsonString the HTML JSON string
     */
    private List<Sensor> getSensors(String jsonString) {
        List<Sensor> readSensors = new ArrayList<>();

        JSONArray sensors;
        JSONObject sensor;

        Iterator<String> typesIterator;
        String type;

        JSONArray coordinatesArray;
        double latitude;
        double longitude;

        double measure;

        try {
            sensors = new JSONArray(jsonString);

            for (int i = 0; i < sensors.length(); i++) {
                sensor = sensors.getJSONObject(i);

                if (sensor.getBoolean(IS_ACTIVE_KEY)) {
                    typesIterator = sensor.getJSONObject(DATA_KEY).keys();

                    while (typesIterator.hasNext()) {
                        try {
                            type = (String) typesIterator.next();
                            final JSONObject data = sensor.getJSONObject(DATA_KEY).getJSONObject(type).getJSONObject(DATA_KEY);

                            measure = data.getDouble(data.keys().next());
                            if (measure < 0 || measure > MEASUREMENTS_LIMIT_UPPER) {
                                continue;
                            }

                            coordinatesArray = sensor.getJSONObject(GEOM_KEY).getJSONArray(COORDINATES_KEY);
                            latitude = coordinatesArray.getDouble(1);
                            longitude = coordinatesArray.getDouble(0);

                            readSensors.add(new Sensor(parseType(type), new LatLng(latitude, longitude), measure));
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (JSONException e) {
            Log.e(NO_JSON_OBJECT_FOUND_LOG_TITLE, NO_JSON_OBJECT_FOUND_LOG_CONTENTS);
        }

        return readSensors;
    }

    /**
     * Get the Sensor {@link Sensor.SensorType} from a String
     *
     * @param jsonType the String to parse
     * @return the parsed Enum
     * @throws IllegalArgumentException when cannot parse
     */
    private Sensor.SensorType parseType(final String jsonType) throws IllegalArgumentException {
        final String type = jsonType.toLowerCase();
        final Sensor.SensorType sensorType;

        if (type.equals(PM10_VARIABLE.toLowerCase())) {
            sensorType = Sensor.SensorType.PM10;
        } else if (type.equals(TEMP_VARIABLE.toLowerCase())) {
            sensorType = Sensor.SensorType.TEMP;
        } else if (type.equals(HUMID_VARIABLE.toLowerCase())) {
            sensorType = Sensor.SensorType.HUMID;
        } else {
            throw new IllegalArgumentException();
        }

        return sensorType;
    }
}