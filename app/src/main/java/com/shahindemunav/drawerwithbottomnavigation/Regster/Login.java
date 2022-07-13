package com.shahindemunav.drawerwithbottomnavigation.Regster;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shahindemunav.drawerwithbottomnavigation.R;


public class Login extends Fragment {
    private FrameLayout parentLayout;
    private TextView singUp;




    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);

        parentLayout=getActivity().findViewById(R.id.regsete_framLay0ut);
        singUp=view.findViewById(R.id.singin);

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setFragmnet(new RegsttitionFragment());

            }
        });








        return view;




    }

    private void setFragmnet(Fragment fragment) {

        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentLayout.getId(),fragment);
        fragmentTransaction.commit();
    }






}