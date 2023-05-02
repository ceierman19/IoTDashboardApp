package com.example.iotdashboardapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public LoginFragment() { // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ServiceClient client = ServiceClient.sharedServiceClient();

        EditText usernameField = view.findViewById(R.id.editTextUsername);
        EditText passwordField = view.findViewById(R.id.editTextPassword);

        view.findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View registerButton) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity(),"Please enter a valid username and password.", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                JSONObject json = new JSONObject();
                try {
                    json.put("username", username);
                    json.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://mopsdev.bw.edu/~ceierman19/csc330/architecture_template/www/rest.php/users", json, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("status") == 0) {
                                Toast toast = Toast.makeText(getActivity(),"Hooray! Successfully registered!.", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else {
                                Toast toast = Toast.makeText(getActivity(),"Try again.", Toast.LENGTH_LONG);
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
                        //TODO: handle error
                    }
                });

                client.addRequest(request);
            }
        });

        view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View loginButton) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity(),"Please enter a valid username and password.", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                AuthRequest.username = username;
                AuthRequest.password = password;

                JsonObjectRequest request = new AuthRequest(Request.Method.GET, "https://mopsdev.bw.edu/~ceierman19/csc330/architecture_template/www/rest.php/sensors", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("status") == 0) {
                                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                            }
                            else {
                                Toast toast = Toast.makeText(getActivity(),"Try again.", Toast.LENGTH_LONG);
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
                        //TODO: handle error
                    }
                });

                client.addRequest(request);
            }
        });

        return view;
    }
}