package com.hackru.buswatcher;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

import com.hackru.buswatcher.com.hackru.buswatcher.data.BusCollection;
import com.hackru.buswatcher.com.hackru.buswatcher.data.StopCollection;

import org.w3c.dom.Text;


public class WatcherActivity extends Activity {

    private static int watchingBus;
    private static int watchingStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watcher);
        Intent intent=getIntent();
        watchingBus=intent.getIntExtra(ChooseBusActivity.EXTRA_BUS_INDEX,-1);
        watchingStop=intent.getIntExtra(ChooseStopActivity.EXTRA_STOP_INDEX,-1);

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
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_watcher, container, false);

            Button setWatcherButton=(Button)rootView.findViewById(R.id.button_toggle_watcher);
            setWatcherButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),ChooseBusActivity.class);
                    startActivityForResult(intent,ChooseBusActivity.REQUEST_CODE);
                }
            });
            busName=(TextView)rootView.findViewById(R.id.text_bus_code);
            stopLeft=(TextView)rootView.findViewById(R.id.text_stop_left);
           initializeWatch(watchingBus, watchingStop);
            return rootView;
        }

        /**
         * use this method to initialize the watch
         */

        private void initializeWatch(int busIndex, int stopIndex){

            //user already selected
            if (watchingBus>=0 && watchingStop>=0) {
                busName.setText(BusCollection.getInstance(getActivity()).getData().get(busIndex).getNameString());
                stopLeft.setText("5 stop from"+ StopCollection.getInstance(getActivity(),busIndex).getData().get(stopIndex).getStreet());
            }
            else{
                //user haven't selected
                busName.setText("no bus");
                stopLeft.setText("click start watcher to watch a bus");
            }

        }
    }
}
