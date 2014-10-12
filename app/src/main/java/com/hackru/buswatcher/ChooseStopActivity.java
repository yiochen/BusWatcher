package com.hackru.buswatcher;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hackru.buswatcher.com.hackru.buswatcher.data.Stop;
import com.hackru.buswatcher.com.hackru.buswatcher.data.StopCollection;

import java.util.ArrayList;

import static com.hackru.buswatcher.ChooseBusActivity.EXTRA_BUS_INDEX;


public class ChooseStopActivity extends Activity {
    static private int busCode;
    private StopCollection stopCollection;
    public static final String EXTRA_STOP_INDEX="extra stop index";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        busCode=intent.getIntExtra(EXTRA_BUS_INDEX,0);
        stopCollection.getInstance(this,busCode);
        setContentView(R.layout.activity_choose_stop);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_stop, menu);
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
    public static class PlaceholderFragment extends ListFragment {

        public PlaceholderFragment() {
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Intent intent=new Intent(getActivity(),WatcherActivity.class);
            intent.putExtra(ChooseBusActivity.EXTRA_BUS_INDEX,busCode);
            intent.putExtra(ChooseStopActivity.EXTRA_STOP_INDEX,position);
            startActivity(intent);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_choose_stop, container, false);
            ListView stopList;
            stopList=(ListView)rootView.findViewById(android.R.id.list);
            stopList.setAdapter(new StopAdapter(StopCollection.getInstance(getActivity(),busCode).getData()));
            return rootView;
        }
        private class StopAdapter extends ArrayAdapter<Stop> {
            public StopAdapter(ArrayList<Stop> stops){
                super(getActivity(),0,stops);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    convertView=getActivity().getLayoutInflater().inflate(R.layout.list_item_stop,null);

                }
                Stop stop=getItem(position);
                TextView stopName=(TextView) convertView.findViewById(R.id.list_item_stop_name);
                stopName.setText(stop.getStreet());
                return convertView;
            }
        }


    }
}
