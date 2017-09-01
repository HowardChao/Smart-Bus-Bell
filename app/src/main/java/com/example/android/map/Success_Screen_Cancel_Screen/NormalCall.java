package com.example.android.map.Success_Screen_Cancel_Screen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.map.Buttons.BusStopOnTheRouteParameter;
import com.example.android.map.CancelBell.Cancel_Bus_Successfully;
import com.example.android.map.R;

import java.util.List;

import static com.example.android.map.Buttons.ClickButtonsToCallBus.BusBell;
import static com.example.android.map.Buttons.ClickButtonsToCallBus.RouteId;
import static com.example.android.map.BusList.CallTheBus_directly.BusStopLocationId;

/**
 * Created by Howard on 2017/7/21.
 */

public class NormalCall extends AppCompatActivity {

    private static final String USGS_REQUEST_URL_BUS_BELL = "http://192.168.0.110:5000/bell/";
    private final String LOG_TAG = NormalCall.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_call);
        TextView succeedCallBus = findViewById(R.id.IDSuccess);
        Button cancelButtonDisable = findViewById(R.id.IDCancelBus_Normal);
        cancelButtonDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusBell = 0;
//                RouteId = 0;
//                BusStopLocationId = "";
                Intent intent = new Intent();
                intent.setClass(NormalCall.this, Cancel_Bus_Successfully.class);
                startActivity(intent);
            }
        });

        BusAsyncTask task03 = new BusAsyncTask();
        task03.execute(USGS_REQUEST_URL_BUS_BELL);
    }

    private class BusAsyncTask extends AsyncTask<String, Void, List<BusStopOnTheRouteParameter>> {

        @Override
        protected List<BusStopOnTheRouteParameter> doInBackground (String... urls){
            if (urls.length < 1 || urls[0] == null ){
                return null;
            }
            String busBell;
            String routeId;
            String stopLocationId;
            busBell = String.valueOf(BusBell);
            routeId = String.valueOf(RouteId);
            stopLocationId = BusStopLocationId;

            urls[0] = urls[0]+ routeId+'/'+stopLocationId + '/' + busBell;
            Log.i(LOG_TAG, urls[0]);
            //Log.i(LOG_TAG, urls[1]);
            QueryUtils_Transmit_BusBell.fetchBusInfo(urls[0]);

            return null;
        }

        @Override
        protected  void onPostExecute(List<BusStopOnTheRouteParameter> data){
            if (data != null && !data.isEmpty()){

            }
        }
    }


}