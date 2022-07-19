package com.shahindemunav.drawerwithbottomnavigation.Regster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shahindemunav.drawerwithbottomnavigation.MainActivity;
import com.shahindemunav.drawerwithbottomnavigation.R;


public class Login extends Fragment {
    private FrameLayout parentLayout;
    private TextView singUp;
    private EditText login_email,login_password;
    private Button login;
    private FirebaseAuth mauth;
    private ProgressDialog progressDialog;
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
        login_email=view.findViewById(R.id.login_email);
        login_password=view.findViewById(R.id.login_pass);
        login=view.findViewById(R.id.logoin);
        mauth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(getActivity());





        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setFragmnet(new RegsttitionFragment());

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=login_email.getText().toString();
                String password=login_password.getText().toString();


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    login_email.setError("Enter Valid Email");
                    login_email.requestFocus();
                    return;

                }

                if (password.isEmpty() || password.length() <= 5) {
                    login_password.setError("Enter Valid Passsword");
                    login_password.requestFocus();
                    return;
                }
                progressDialog.setMessage("Loging User");
                progressDialog.show();

                mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent=new Intent(getActivity(),MainActivity.class);
                            intent.putExtra("email",email);
                          // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            getActivity().finish();



                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.e("problem",e.getLocalizedMessage());
                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                    }
                });




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