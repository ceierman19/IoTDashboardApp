package com.example.iotdashboardapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View loginButton) {
                EditText usernameField = view.findViewById(R.id.editTextUsername);
                EditText passwordField = view.findViewById(R.id.editTextPassword);
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {
                    // Should we hardcode like this? //if (username.equals("admin") && password.equals("changeme"))
                    // Or should we just check if the fields are empty?

                    // Not sure if this userInfo bundle is needed
                    Bundle userInfoBundle = new Bundle();
                    userInfoBundle.putString("username", username);
                    userInfoBundle.putString("password", password);
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment, userInfoBundle);
                    //Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                }
                else {
                    Toast toast = Toast.makeText(getActivity(),"Please enter a valid username and password.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        return view;
    }
}