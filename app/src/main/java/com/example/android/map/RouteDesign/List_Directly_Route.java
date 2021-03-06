package com.example.android.map.RouteDesign;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.map.Buttons.ClickButtonsToCallBus;
import com.example.android.map.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.map.MapsActivity.UserInput;
import static com.example.android.map.MapsActivity.mcurrentLatitude;
import static com.example.android.map.MapsActivity.mcurrentLongitude;

/**
 * Created by Howard on 2017/7/22.
 */

public class List_Directly_Route extends Fragment {

    private SearchResult searchResult;
    private SearchParameter data_of_search_parameter;
//    private SearchParameter data = new SearchParameter(null, null);

    private String URL_SEARCH = "http://192.168.0.110:5000/search";
    private double longitude;
    private double latitude;
    private String answerYouEnter;
//    MapsActivity mapsActivity = new MapsActivity();
    private ViewPager viewPager;
    private TabLayout myTabLayout;
    private final String LOG_TAG = List_Directly_Route.class.getSimpleName();

    private View view;
    private SearchPatameter_directly_Adapter mAdapter;
    private ListView callTheBusDirectlyListView;
    private static List<SearchParameter_directly> result = new ArrayList<SearchParameter_directly>();

    private static String busNumber = new String();
    private static String BusStopLocationId = new String();
    private static String RouteId = new String();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        if (view == null){
            view = inflater.inflate(R.layout.fragment_directfragment, container, false);
        }
        else{
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
//        mAdapter = new SearchPatameter_directly_Adapter(getActivity(), new ArrayList<SearchParameter_directly>());
        answerYouEnter = UserInput;
        callTheBusDirectlyListView = (ListView) view.findViewById(R.id.list_directly);
        mAdapter = new SearchPatameter_directly_Adapter(getActivity(), new ArrayList<SearchParameter_directly>());
        callTheBusDirectlyListView.setAdapter(mAdapter);
        callTheBusDirectlyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

//                RouteId = result.get((int)id).getmRouteId();
                busNumber = result.get((int)id).getmNumber();
                BusStopLocationId = result.get((int)id).getmStartStopLocationId();
                Intent intent = new Intent(getActivity(), ClickButtonsToCallBus.class);
                Bundle bundle = new Bundle();
                bundle.putString("RouteId", RouteId);
                bundle.putString("BusStopLocationId",BusStopLocationId);
                bundle.putString("busNumber",busNumber);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        getCallTheBusDirectlyListView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void getCallTheBusDirectlyListView(){
        BusAsyncTask task04 = new BusAsyncTask();
        task04.execute(URL_SEARCH);
    }


    private class BusAsyncTask extends AsyncTask<String, Void, List<SearchParameter_directly>> {

        @Override
        protected List<SearchParameter_directly> doInBackground (String... urls){
            if (urls.length < 1 || urls[0] == null ){
                return null;
            }
            latitude = mcurrentLatitude;
            longitude = mcurrentLongitude;
            urls[0] = urls[0] +'/' + latitude + '/' + longitude + '/' + answerYouEnter;
            Log.i(LOG_TAG, urls[0]);
            result =  QueryUtils_ResearchResult.fetchBusInfo(urls[0]);
            return result;
        }

        @Override
        protected  void onPostExecute(List<SearchParameter_directly> data ){
//            data = new SearchParameter(null, null);
//            data = result;
//                mAdapter = new SearchPatameter_directly_Adapter(getActivity(), new ArrayList<SearchParameter_directly>());
//                mAdapter.clear();
//            if ( data != null ){
//                List<SearchParameter_Google>data_of_google_route = new ArrayList<SearchParameter_Google>();
//                data_of_google_route = data.getmSearchParameter_google();
//                if (data_of_google_route != null || !data_of_google_route.isEmpty()){
                //data_of_google_route = result.getmSearchParameter_google();
//                mAdapter.addAll(data);
//
//                callTheBusDirectlyListView = (ListView) getActivity().findViewById(R.id.list_directly);
                mAdapter = new SearchPatameter_directly_Adapter(getActivity(), result);
//            mAdapter.notifyDataSetChanged();
//            mAdapter.notifyDataSetInvalidated();
                callTheBusDirectlyListView.setAdapter(mAdapter);
                Log.i(LOG_TAG, String.valueOf(result));

                Toast.makeText(getActivity(), "Asynctask done!", Toast.LENGTH_SHORT).show();
                LayoutInflater.from(getActivity());

        }
    }
}