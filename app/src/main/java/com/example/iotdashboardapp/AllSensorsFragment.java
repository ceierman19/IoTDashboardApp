package com.example.iotdashboardapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.iotdashboardapp.model.AuthRequest;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllSensorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllSensorsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllSensorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllSensorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllSensorsFragment newInstance(String param1, String param2) {
        AllSensorsFragment fragment = new AllSensorsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ServiceClient.sharedServiceClient(getApplicationContext());
        //ServiceClient.sharedServiceClient(null);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_sensors, container, false);

        view.findViewById(R.id.buttonGetAllSensorData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View getAllSensorDataButton) {
                AuthRequest.username = "ceierman19";
                AuthRequest.password = "changeme";
                JsonObjectRequest request = new AuthRequest(Request.Method.GET, "https://mopsdev.bw.edu/~ceierman19/csc330/architecture_template/www/rest.php/orders", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        TextView sensorDataField = view.findViewById(R.id.textViewAllSensorData);
                        sensorDataField.setText(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        //TODO: handle error
                    }
                });
                //ServiceClient client = ServiceClient.sharedServiceClient(getApplicationContext());
                //ServiceClient client = ServiceClient.sharedServiceClient(null);
                //client.addRequest(request);
            }
        });

        return view;
    }
}