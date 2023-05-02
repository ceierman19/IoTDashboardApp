package com.example.iotdashboardapp;

import static android.view.View.GONE;

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
import android.widget.Button;
import android.widget.Toast;
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
        String source = sensorBundle.getString("source");
        int id;

        if (source.equals("fav")) {
            String readingType = sensorBundle.getString("readingType");
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
        }
        else {
            Button unfavButton = view.findViewById(R.id.buttonUnfav);
            unfavButton.setVisibility(GONE);

            id = sensorBundle.getInt("readingTypeNum");
            id -= 1;
        }

        String url = "https://mopsdev.bw.edu/~ceierman19/csc330/architecture_template/www/rest.php/readings/" + id;
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

        String readingType = sensorBundle.getString("readingType");
        JSONObject json = new JSONObject();
        try {
            json.put("username", AuthRequest.username);
            json.put("reading_type", readingType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        view.findViewById(R.id.buttonFav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View favButton) {
                JsonObjectRequest request2 = new AuthRequest(Request.Method.POST, "https://mopsdev.bw.edu/~ceierman19/csc330/architecture_template/www/rest.php/favSensors", json, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("status") == 0) {
                                Toast toast = Toast.makeText(getActivity(), "Hooray! Favorited.", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                Toast toast = Toast.makeText(getActivity(), "Try again.", Toast.LENGTH_LONG);
                                toast.show();
                            }
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

                client.addRequest(request2);
            }
        });

        view.findViewById(R.id.buttonUnfav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unfavButton) {
                int favoriteId = sensorBundle.getInt("favoriteId");

                JSONObject json2 = new JSONObject();
                try {
                    json2.put("username", AuthRequest.username);
                    json2.put("favorite_id", favoriteId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = "https://mopsdev.bw.edu/~ceierman19/csc330/architecture_template/www/rest.php/favSensors/" + favoriteId;
                JsonObjectRequest request3 = new AuthRequest(Request.Method.DELETE, url, json2, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("status") == 0) {
                                Toast toast = Toast.makeText(getActivity(), "Boo! Unfavorited.", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                Toast toast = Toast.makeText(getActivity(), "Try again.", Toast.LENGTH_LONG);
                                toast.show();
                            }
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

                client.addRequest(request3);
            }
        });

        return view;
    }
}