package com.example.pooja.bhumi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Set;
import java.util.TreeSet;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.layer.BackgroundLayer;
import gov.nasa.worldwind.layer.BlueMarbleLandsatLayer;

public class Bhumi extends AppCompatActivity {

    @Override
    //comment
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(myReceiver, new IntentFilter(BhumiFirebaseMessagingService.INTENT_FILTER));

        setContentView(R.layout.activity_bhumi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create a World Window (a GLSurfaceView)...
        WorldWindow wwd = new WorldWindow(getApplicationContext());
        // ... and add some map layers
        wwd.getLayers().addLayer(new BackgroundLayer());
        wwd.getLayers().addLayer(new BlueMarbleLandsatLayer());
        // Add the WorldWindow view object to the layout that was reserved for the globe.
        FrameLayout globeLayout = (FrameLayout) findViewById(R.id.globe);
        globeLayout.addView(wwd);


        TextView dispNotification = (TextView) findViewById(R.id.textView);

        Intent intent_o = getIntent();
        dispNotification.setText("Your Current Location is Bangalore\n");
        if (intent_o.getExtras() !=null) {
            TreeSet<String> keys = new TreeSet(intent_o.getExtras().keySet());
            if (keys!=null) {
                for (String key : keys) {
                    dispNotification.append("\n" + key + ":" + intent_o.getStringExtra(key));
                }
            }
        }
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Intent intent_o = getIntent();

            TextView dispNotification = (TextView) findViewById(R.id.textView);
            dispNotification.setText("Your Current Location is Bangalore\n");

            if (intent.getExtras() !=null) {

                TreeSet<String> keys = new TreeSet(intent.getExtras().keySet());
                if (keys!=null) {
                    for (String key : keys) {
                            dispNotification.append("\n" + key + ":" + intent.getStringExtra(key));
                    }
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
   /* @Override
    protected void onResume() {
        super.onResume();
        //setContentView(R.layout.activity_bhumi);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        TextView dispNotification = (TextView) findViewById(R.id.textView);

        Intent intent_o = getIntent();
        String name1 = intent_o.getStringExtra("name1");
        String name2 = intent_o.getStringExtra("name2");
        if (name1!=null && name2!=null)
         dispNotification.append("\n"+name1+":"+name2);

    }
*/

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bhumi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
