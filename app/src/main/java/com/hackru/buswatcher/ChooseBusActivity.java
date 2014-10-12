package com.hackru.buswatcher;

import android.app.Activity;
import android.app.Fragment;
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
import com.hackru.buswatcher.com.hackru.buswatcher.data.StopCollection;

import java.util.ArrayList;


public class ChooseBusActivity extends Activity {
    public static final int REQUEST_CHOICE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bus);
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
    public static class PlaceholderFragment extends Fragment {

        private BusCollection busCollection;
        private StopCollection stopCollection;
        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            busCollection=BusCollection.getInstance();

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_choose_bus, container, false);
            //setting up the list view on the left side, which is the list for bus
            ViewGroup busListContainer=(ViewGroup)rootView.findViewById(R.id.bus_list_container);
            ListView busList=(ListView)busListContainer.findViewById(android.R.id.list);
            busList.setAdapter(new BusAdapter(busCollection.getData()));
            //setting up the list view on the right side, which is the list for stop.

            return rootView;
        }
        private class BusAdapter extends ArrayAdapter<Bus>{
            public BusAdapter( ArrayList<Bus> buses ){
                super(getActivity(),0,buses);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    convertView=getActivity().getLayoutInflater().inflate(R.layout.list_item_bus,null);
                }
                Bus bus=(Bus)getItem(position);
                TextView busName=(TextView)convertView.findViewById(R.id.list_item_bus_name);
                busName.setText(bus.getNameString());
                return convertView;
            }
        }
    }

}
