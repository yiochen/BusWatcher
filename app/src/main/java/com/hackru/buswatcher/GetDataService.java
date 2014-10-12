package com.hackru.buswatcher;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;


public class GetDataService extends IntentService {

    public static String BUS_CODE = "bus_code";
    public static String LENField = "length";
    public static String STOP_IDS = "stop_ids";
    public static String LATITUDES = "latitudes";
    public static String LONGITUDES = "longitudes";
    public static String DIRECTION_IDS = "direction_ids";

    static String Q18 = "Q18";

    private final String apiKey = "366312c4-6ae0-4186-867f-821f165b2d2b";
    private final String apiHost = "http://api.prod.obanyc.com/api/siri/vehicle-monitoring.json";

    public static String NOTIFICATION = "com.example.chaojiewang.demo.getdataservice.receiver";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GetDataService(String name) {
        super(name);
    }

    public GetDataService() {
        super("getdataservice");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("GetDataService", "inside handler");
        String bus_code = intent.getStringExtra(BUS_CODE);
        JSONArray movingBuses = queryBusInfo(bus_code);
        //packing up data to be sent over
        int size = movingBuses.length();
        int[] stop_ids = new int[size];
        double[] latitudes = new double[size];
        double[] longitudes = new double[size];
        int[] direction_ids = new int[size];

        for (int i = 0; i < movingBuses.length(); i++) {
            try {
                stop_ids[i] = parseStopId(movingBuses.getJSONObject(i)
                        .getJSONObject("MonitoredVehicleJourney")
                        .getJSONObject("MonitoredCall")
                        .getString("StopPointRef"));
                latitudes[i] = movingBuses.getJSONObject(i)
                        .getJSONObject("MonitoredVehicleJourney")
                        .getJSONObject("VehicleLocation")
                        .getDouble("Longitude");
                longitudes[i] = movingBuses.getJSONObject(i)
                        .getJSONObject("MonitoredVehicleJourney")
                        .getJSONObject("VehicleLocation")
                        .getDouble("Latitude");
                direction_ids[i] = movingBuses.getJSONObject(i)
                        .getJSONObject("MonitoredVehicleJourney")
                        .getInt("DirectionRef");
            } catch (JSONException e) {
                Log.e("GetDataService", "reading json error");
                e.printStackTrace();
            }
        }

        //send it
        Intent intent1 = new Intent(NOTIFICATION);
        intent1.putExtra(LENField, size);
        intent1.putExtra(STOP_IDS, stop_ids);
        intent1.putExtra(LATITUDES, latitudes);
        intent1.putExtra(LONGITUDES, longitudes);
        intent1.putExtra(DIRECTION_IDS, direction_ids);
        Log.i("GetDataService", "about to broadcast");
        sendBroadcast(intent1);
    }

    public JSONArray queryBusInfo(String bus_code) {
        //building get request
        String urlLink = String.format("%s?key=%s&LineRef=%s", apiHost,apiKey, bus_code);
        String rawJson = "";
        Log.i("GetDataService", "about to getting internet data");
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(urlLink));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                rawJson = out.toString();
            }
            Log.i("GetDataService", "rawJson: " + rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);
            return jsonObj.getJSONObject("Siri")
                    .getJSONObject("ServiceDelivery")
                    .getJSONArray("VehicleMonitoringDelivery")
                    .getJSONObject(0)
                    .getJSONArray("VehicleActivity");
        } catch (Exception err) {
            Log.i("GetDataService", "err: " + err.toString());
        }

        return null;
    }

    private int parseStopId(String numStr) {
        int starting = 0;
        for ( ; starting < numStr.length(); starting++) {
            if (numStr.charAt(starting) >= '0' && numStr.charAt(starting) <= '9') {
                break;
            }
        }
        return Integer.parseInt(numStr.substring(starting));
    }
}
