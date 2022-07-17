package com.shahindemunav.drawerwithbottomnavigation.Regster;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.shahindemunav.drawerwithbottomnavigation.MainActivity;
import com.shahindemunav.drawerwithbottomnavigation.R;
import com.shahindemunav.drawerwithbottomnavigation.payment.Paymnet;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


public class RegsttitionFragment extends Fragment {
    private Button regstition;
    private String rcode = "1234567891";
    private int REQUEST_CODE = 5;
    private EditText ed_name, ed_mobail_account, ed_password, ed_mpassword, ed_house, refcode, email;
    private FusedLocationProviderClient fusedLocationClient;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private DatabaseReference dbref;
    private ProgressDialog progressDialog;

    private String Coin;

    public RegsttitionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_regsttition, container, false);
        email = view.findViewById(R.id.setEmail);
        ed_name = view.findViewById(R.id.ed_name);
        ed_mobail_account = view.findViewById(R.id.ed_account);
        ed_password = view.findViewById(R.id.passwoed_o);
        ed_mpassword = view.findViewById(R.id.passwoed_m);
        ed_house = view.findViewById(R.id.house);
        refcode = view.findViewById(R.id.refcode);
        regstition = view.findViewById(R.id.regstition);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        dbref=FirebaseDatabase.getInstance().getReference("Count");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        progressDialog = new ProgressDialog(getActivity());


        getDefoutlCoid();
        permistion();

        regstition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validety();

            }
        });

        return view;
    }

    private void permistion() {


        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            //do you stuf here
                        } else {
                            // Toast.makeText(EditEventActivity.this, "Permission Denied! plz enable application permission from app settings!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }

                }).check();


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
    }

    private void getDefoutlCoid() {
       dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Coin = snapshot.child("defoultcoin").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Validety() {

        String name = ed_name.getText().toString();
        String mobail = ed_mobail_account.getText().toString();
        String emails = email.getText().toString();
        String password1 = ed_password.getText().toString();
        String passwprd2 = ed_mpassword.getText().toString();
        String address = ed_house.getText().toString();
        String rcode = refcode.getText().toString();

        if (name.isEmpty()) {
            ed_name.setError("Enter Your Name");
            ed_name.requestFocus();
            return;

        }
        if (mobail.isEmpty()) {
            ed_mobail_account.setError("Enter your Valid Phone(11 Digit)");
            ed_mobail_account.requestFocus();
            return;
        }
        if (mobail.length() != 11) {
            ed_mobail_account.setError("Enter your Valid Phone(11 Digit)");
            ed_mobail_account.requestFocus();
            return;

        }
        if (emails.isEmpty()) {
            email.setError("Your Email Must Here ");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
            email.setError("Enter Valid Email");
            email.requestFocus();
            return;

        }

        if (password1.isEmpty() || password1.length() <= 5) {
            ed_password.setError("Enter Valid Passsword");
            ed_password.requestFocus();
            return;
        }
        if (passwprd2.isEmpty()) {
            ed_mpassword.setError("Enter Match password");
            ed_mpassword.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            ed_house.setError("Enter Address");
            ed_house.requestFocus();
            return;
        }

        if (password1.equals(passwprd2)) {


        } else {
            ed_mpassword.setError("Password not Match!");
            ed_mpassword.requestFocus();
            return;
        }


        LoginIntodatabase(name, mobail, password1, emails, address, rcode);


    }

    private void LoginIntodatabase(String name, String mobail, String password1, String emails, String address, String rcode) {
        permistion();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        progressDialog.setMessage("Creating Account");
                        progressDialog.show();
                        // Got last known location. In some rare situations this can be null.
                        String Latitude = String.valueOf(location.getLatitude());
                        String Longitude = String.valueOf(location.getLongitude());
                        //// Toast.makeText(getActivity(), ""+Latitude+"long"+Longitude, Toast.LENGTH_SHORT).show();
                        auth.createUserWithEmailAndPassword(emails, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    String key = reference.push().getKey();
                                    SharedPreferences sharedPref = getActivity().getSharedPreferences("name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("key", key);
                                    editor.commit();

                                    HashMap<String, String> user = new HashMap<>();
                                    user.put("key", key);
                                    user.put("name", name);
                                    user.put("email", emails);
                                    user.put("password", password1);
                                    user.put("phone", mobail);
                                    user.put("address", address);
                                    user.put("yourRcode", mobail);
                                    user.put("Latitude", Latitude);
                                    user.put("Longitude", Longitude);
                                    user.put("rcode", rcode);
                                    user.put("accountStatus", "inactive");
                                    user.put("Coin", Coin);


                                    reference.child(key).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                Intent intent = new Intent(getActivity(), Paymnet.class);
                                                intent.putExtra("key",key);
                                                startActivity(intent);



                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                                        }
                                    });


                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });


        
        
        
        
        
        
        
        
        
        
    }




}