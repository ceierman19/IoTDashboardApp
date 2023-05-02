package com.example.iotdashboardapp;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
public class FavSensorFragment extends Fragment implements MyFavSensorsRecyclerViewAdapter.RecyclerViewDelegate{
    private View view;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    List<FavSensor> favSensors;

    // Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon screen orientation changes)
    public FavSensorFragment() {
    }

    public static FavSensorFragment newInstance(int columnCount) {
        FavSensorFragment fragment = new FavSensorFragment();
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
        view = inflater.inflate(R.layout.fragment_fav_sensor_list, container, false);
        ServiceClient client = ServiceClient.sharedServiceClient();
        favSensors = new ArrayList<>();
        MyFavSensorsRecyclerViewAdapter adapter = new MyFavSensorsRecyclerViewAdapter(favSensors);
        adapter.delegate = this;

        JsonObjectRequest request = new AuthRequest(Request.Method.GET, "https://mopsdev.bw.edu/~ceierman19/csc330/architecture_template/www/rest.php/favSensors", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Type favSensorList = new TypeToken<ArrayList<Sensor>>() {
                }.getType();
                Gson gson = new Gson();
                try {
                    List<FavSensor> updatedFavSensors = gson.fromJson(response.get("data").toString(), favSensorList);
                    favSensors.clear();
                    favSensors.addAll(updatedFavSensors);
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

    @Override
    public void didSelect(int index) {
        FavSensor s = favSensors.get(index);
//        Bundle sensorBundle = new Bundle();
//        sensorBundle.putString("readingType", s.reading_type);
//        Navigation.findNavController(view).navigate(R.id.action_sensorFragment_to_sensorReadingsFragment, sensorBundle);
    }
}
