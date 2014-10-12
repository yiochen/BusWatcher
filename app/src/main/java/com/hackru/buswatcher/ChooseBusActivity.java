package com.hackru.buswatcher;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hackru.buswatcher.com.hackru.buswatcher.data.Bus;
import com.hackru.buswatcher.com.hackru.buswatcher.data.BusCollection;

import java.util.ArrayList;


public class ChooseBusActivity extends Activity {
    public static final int REQUEST_CODE=1;
    public static final String EXTRA_BUS_INDEX="extra bus index";
    public static Bus watchingBus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_buses);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_bus, menu);
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
        private ArrayList<Bus> mBuses;
        private BusAdapter adapter;
        public PlaceholderFragment() {
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            watchingBus=BusCollection.getInstance(getActivity()).getData().get(position);
            Intent intent=new Intent(getActivity(),ChooseStopActivity.class);
            intent.putExtra(EXTRA_BUS_INDEX,position);
            startActivity(intent);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_choose_buses, container, false);
            ListView busList;
            busList = (ListView)rootView.findViewById(android.R.id.list);
            busList.setAdapter(new BusAdapter(BusCollection.getInstance(getActivity()).getData()));
            return rootView;
        }

        private class BusAdapter extends ArrayAdapter<Bus>{
            public BusAdapter(ArrayList<Bus> buses){
                super(getActivity(),0,buses);

            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    convertView=getActivity().getLayoutInflater().inflate(R.layout.list_item_bus,null);
                }
                Bus bus=getItem(position);
                TextView busName=(TextView)convertView.findViewById(R.id.list_item_bus_name);
                busName.setText(bus.getNameString());
                return convertView;

            }
        }
    }
}
