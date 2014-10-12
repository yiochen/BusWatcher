package com.hackru.buswatcher;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

import com.hackru.buswatcher.com.hackru.buswatcher.data.BusCollection;
import com.hackru.buswatcher.com.hackru.buswatcher.data.Stop;
import com.hackru.buswatcher.com.hackru.buswatcher.data.StopCollection;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class WatcherActivity extends Activity {

    private static int watchingBus;
    private static int watchingStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watcher);
        Intent intent = getIntent();
        watchingBus = intent.getIntExtra(ChooseBusActivity.EXTRA_BUS_INDEX, -1);
        watchingStop = intent.getIntExtra(ChooseStopActivity.EXTRA_STOP_INDEX, -1);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.watcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private TextView busName;
        private TextView stopLeft;
        private Timer timer;

        private BroadcastReceiver receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("MyActivity", "receiving data");
                int length = intent.getIntExtra(GetDataService.LENField, 0);
                int[] stop_ids = intent.getIntArrayExtra(GetDataService.STOP_IDS);
                double[] latitudes = intent.getDoubleArrayExtra(GetDataService.LATITUDES);
                double[] longitudes = intent.getDoubleArrayExtra(GetDataService.LONGITUDES);
                int[] direction_ids = intent.getIntArrayExtra(GetDataService.DIRECTION_IDS);


                int size = StopCollection.getInstance(getActivity(), watchingStop).getData().size();
                ArrayList<Stop> allStops = StopCollection.getInstance(getActivity(), watchingStop).getData();
                int[] currentStopIndex = new int[length];
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < length; j++) {
                        if (Integer.parseInt(allStops.get(i).getCode()) == stop_ids[j])
                            currentStopIndex[j] = i;
                    }
                }

                int minStops = 100;
                Log.i("WatchingActivity", "min stops:" + minStops);
                for (int i : currentStopIndex) {
                    if (Math.abs(watchingStop - i) < minStops)
                        minStops = Math.abs(watchingStop - i);
                }

                stopLeft.setText(minStops + " stop from" + StopCollection.getInstance(getActivity(), watchingBus).getData().get(watchingStop).getStreet());
            }
        };

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_watcher, container, false);

            Button setWatcherButton = (Button) rootView.findViewById(R.id.button_toggle_watcher);
            setWatcherButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ChooseBusActivity.class);
                    startActivityForResult(intent, ChooseBusActivity.REQUEST_CODE);
                }
            });
            busName = (TextView) rootView.findViewById(R.id.text_bus_code);
            stopLeft = (TextView) rootView.findViewById(R.id.text_stop_left);

            initializeWatch(watchingBus, watchingStop);
            return rootView;
        }

        /**
         * use this method to initialize the watch
         */

        private void initializeWatch(int busIndex, int stopIndex) {

            //user already selected
            if (watchingBus >= 0 && watchingStop >= 0) {
                timer = new Timer();
                timer.scheduleAtFixedRate(new FetchDataTask(), 0, 80000);
                busName.setText(BusCollection.getInstance(getActivity()).getData().get(busIndex).getNameString());
                stopLeft.setText("5 stop from" + StopCollection.getInstance(getActivity(), stopIndex).getData().get(stopIndex).getStreet());
            } else {
                //user haven't selected
                busName.setText("no bus");
                stopLeft.setText("click start watcher to watch a bus");
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            getActivity().registerReceiver(receiver, new IntentFilter(GetDataService.NOTIFICATION));

        }

        @Override
        public void onPause() {
            super.onPause();
            getActivity().unregisterReceiver(receiver);
           if (timer!=null) timer.cancel();
        }

        class FetchDataTask extends TimerTask {

            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), GetDataService.class);
                // add infos for the service which file to download and where to store
                intent.putExtra(GetDataService.BUS_CODE, GetDataService.Q18);
                getActivity().startService(intent);
            }
        }
    }
}
