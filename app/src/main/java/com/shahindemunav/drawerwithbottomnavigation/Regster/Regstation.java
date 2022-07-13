package com.shahindemunav.drawerwithbottomnavigation.Regster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.shahindemunav.drawerwithbottomnavigation.R;

public class Regstation extends AppCompatActivity {
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regstation);
        frameLayout=findViewById(R.id.regsete_framLay0ut);

        setFragmnet(new Login());

    }

    private void setFragmnet(Fragment fragment) {

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }


}