package com.example.iotdashboardapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.iotdashboardapp.model.AuthRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class SensorReadingsFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    List<SensorReadings> sensorReadings;

    // Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon screen orientation changes)
    public SensorReadingsFragment() {
    }

    public static SensorReadingsFragment newInstance(int columnCount) {
        SensorReadingsFragment fragment = new SensorReadingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_readings_list, container, false);
        ServiceClient client = ServiceClient.sharedServiceClient();
        sensorReadings = new ArrayList<>();
        MySensorReadingsRecyclerViewAdapter adapter = new MySensorReadingsRecyclerViewAdapter(sensorReadings);

        Bundle sensorBundle = getArguments();
        String readingType = sensorBundle.getString("readingType");
        int id = -1;
        if (readingType.equals("temp")) {
            id = 0;
        }
        else if (readingType.equals("humd")) {
            id = 1;
        }
        else if (readingType.equals("soiltemp")) {
            id = 2;
        }
        else {
            id = 3;
        }
        id = sensorBundle.getInt("readingTypeNum");
        id -= 1;
        //String url = "https://mopsdev.bw.edu/~ceierman19/csc330/architecture_template/www/rest.php/sensorReadings/" + id;
        String url = "https://mopsdev.bw.edu/~jgersey20/330/www/rest.php/sensorReadings/" + id;
        JsonObjectRequest request = new AuthRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Type sensorReadingsList = new TypeToken<ArrayList<SensorReadings>>() {}.getType();
                Gson gson = new Gson();
                try {
                    List<SensorReadings> updatedSensorReadings = gson.fromJson(response.get("data").toString(), sensorReadingsList);
                    sensorReadings.clear();
                    sensorReadings.addAll(updatedSensorReadings);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        });

        client.addRequest(request);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        // Set the adapter
        if (recyclerView instanceof RecyclerView) {
            Context context = view.getContext();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}