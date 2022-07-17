package com.shahindemunav.drawerwithbottomnavigation.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahindemunav.drawerwithbottomnavigation.MainActivity;
import com.shahindemunav.drawerwithbottomnavigation.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Paymnet extends AppCompatActivity {
    private  TextView typeofid, orderTxtvie, orderPlayerid, total, selecttype, youraccountNo, yourtransgation, payment,paymentaccount;

    private  String DataFromIntent="";
    private  String bank="";
    private  String key="";
    private  String amonts="";
    private String mbkash,mnagad,mroket,mupay;
    private RadioButton R_bkash, R_nagad, R_roket, R_upay;
    private TextView accountHelth,accountitile;
    private LinearLayout mainLayout;
    private DatabaseReference reference2,reference;
    private ProgressDialog progressDialog;
    private EditText account_nummber, transcationId, phone_number,amont;
    private Button palceorder;
    String keys;

    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_paymnet);
        mainLayout=findViewById(R.id.mainLayout);
        accountHelth=findViewById(R.id.accountHelth);
       DataFromIntent=getIntent().getStringExtra("accountStatus");
       key=getIntent().getStringExtra("key");
       accountitile=findViewById(R.id.accountTitiel);
       progressDialog=new ProgressDialog(getApplicationContext());
        R_bkash = findViewById(R.id.bkash);
        R_nagad = findViewById(R.id.nagad);
        R_roket = findViewById(R.id.roket);
        R_upay = findViewById(R.id.upay);
        amont=findViewById(R.id.totolapaymentTaka);
        payment=findViewById(R.id.payment);
        paymentaccount=findViewById(R.id.accountNumber);


        Toast.makeText(this, "Your Referal Code "+key, Toast.LENGTH_SHORT).show();

        typeofid = findViewById(R.id.tpyeofid);

        phone_number=findViewById(R.id.phoneNo);

        selecttype = findViewById(R.id.select_all);
        youraccountNo = findViewById(R.id.youraccountNumber);
        yourtransgation = findViewById(R.id.tranxjetionid);
        payment = findViewById(R.id.payment);
        account_nummber = findViewById(R.id.account_number);
        transcationId = findViewById(R.id.transcationId);
        palceorder = findViewById(R.id.placeorde);
        reference2=FirebaseDatabase.getInstance().getReference("Mobaile");
        transcationId = findViewById(R.id.transcationId);
        progressDialog=new ProgressDialog(this);

        reference=FirebaseDatabase.getInstance().getReference();
        SharedPreferences sh = getSharedPreferences("name", MODE_PRIVATE);
        keys=sh.getString("key","");





       CallDataBaseForMObailNumbae();





       R_bkash.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               selecttype.setText(getString(R.string.biksh));
               R_nagad.setChecked(false);
               R_roket.setChecked(false);
               R_upay.setChecked(false);
               bank = "bkash";
               setEdittext(bank);
               paymentaccount.setText("Account Number is: "+mbkash);
               typeofid.setText("account type :bkash");
           }
       });
       R_nagad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecttype.setText(getString(R.string.nagad));
                R_roket.setChecked(false);
                R_upay.setChecked(false);
                R_bkash.setChecked(false);
                bank = "Nagad";
                paymentaccount.setText("Account Number is: "+mnagad);
                setEdittext(bank);

            }
        });
       R_roket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selecttype.setText(getString(R.string.roket));
                R_nagad.setChecked(false);

                R_upay.setChecked(false);
                R_bkash.setChecked(false);
                bank = "Rocket";
                paymentaccount.setText("Account Number is: "+mroket);
                setEdittext(bank);
                typeofid.setText("account type :"+bank);
            }
        });
       R_upay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecttype.setText(getString(R.string.upay));

                R_nagad.setChecked(false);
                R_roket.setChecked(false);

                R_bkash.setChecked(false);
                bank = "Upay";
                setEdittext(bank);
                paymentaccount.setText("Account Number is: "+mupay);
                typeofid.setText("account type :"+bank);
            }
        });

        palceorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValiditions();
            }
        });




    }

    private void setEdittext(String bank) {

        youraccountNo.setText("Enter Your " + bank + " Account No");
        yourtransgation.setText("Enter Your " + bank + " Transaction ID");


    }

    private void checkValiditions() {

        String account,trasngationid,phone;
        account = account_nummber.getText().toString().trim();
        trasngationid = transcationId.getText().toString().trim();

        phone = phone_number.getText().toString().trim();

        if (account.isEmpty() || trasngationid.isEmpty()  || phone.isEmpty()) {

            Toast.makeText(this, "Fill all The filds", Toast.LENGTH_SHORT).show();
            return;
        }
        if (account.length()==11 || account.length()==14 || account.length()==12) {
            if (bank.contains("bkash")){
                if (trasngationid.length()==10){


                        if (phone.length()==11 ||phone.length()==14){
                            progressDialog.setMessage("Procssing Order");
                            progressDialog.show();
                            DatasendToFirebse(account, trasngationid, phone, bank, key);

                        }else {

                            phone_number.setError("Enter Valid Phone NO");
                            phone_number.requestFocus();

                            return;
                        }








                }
                else {
                    transcationId.setError("Enter Valid ID");
                    transcationId.requestFocus();
                    return;

                }

            }
            else  if (bank.contains("Nagad")){


                if (trasngationid.length()==8){


                        if (phone.length()==11 ||phone.length()==14){
                            progressDialog.setMessage("Procssing Order");
                            progressDialog.show();
                            DatasendToFirebse(account, trasngationid,  phone, bank, key);

                        }else {

                            phone_number.setError("Enter Valid Phone NO");
                            phone_number.requestFocus();

                            return;
                        }




                    }




                }
                else {
                    transcationId.setError("Enter Valid ID");
                    transcationId.requestFocus();
                    return;

                }

            }

            else if (bank.contains("Rocket")){

                if (trasngationid.length()==10){



                        if (phone.length()==11 ||phone.length()==14){
                            progressDialog.setMessage("Procssing Order");
                            progressDialog.show();
                            DatasendToFirebse(account, trasngationid,  phone, bank, key);

                        }else {

                            phone_number.setError("Enter Valid Phone NO");
                            phone_number.requestFocus();

                            return;
                        }








                }
                else {
                    transcationId.setError("Enter Valid ID");
                    transcationId.requestFocus();
                    return;

                }
            }
            else if (bank.contains("Upay")){

                Toast.makeText(this, "Upay Coming Soon", Toast.LENGTH_SHORT).show();
                return;
//                if (full_name.length()>4&&full_name.length()<12){
//
//                    if (phone.length()==11 ||phone.length()==14){
//                        progressDialog.setMessage("Procssing Order");
//                        progressDialog.show();
//                        DatasendToFirebse(account, trasngationid, full_name, phone, bank, playerid);
//
//                    }else {
//
//                        phone_number.setText("Enter Valid Phone NO");
//                        phone_number.requestFocus();
//
//                        return;
//                    }
//
//
//
//
//                }
//                else {
//                    fullname.setError("Enter You Full Name ");
//                    fullname.requestFocus();
//
//                    return;
//                }
            }
        }













    private void CallDataBaseForMObailNumbae() {

        progressDialog.setMessage("Fatching Number");
        progressDialog.setCancelable(false);
        progressDialog.show();
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mbkash=snapshot.child("bKash").getValue().toString();
                mnagad=snapshot.child("Nagad").getValue().toString();
                mroket=snapshot.child("Roket").getValue().toString();
                mupay=snapshot.child("Upay").getValue().toString();
                amonts=snapshot.child("amount").getValue().toString();

                payment.setText("You Have To pay Total: "+amonts);




                    progressDialog.dismiss();







            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Paymnet.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });


    }


    private void checkValidition() {



        Toast.makeText(this, "your key is: "+key, Toast.LENGTH_SHORT).show();
        reference.child("users").child(keys).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              String accountStatus=snapshot.child("accountStatus").getValue().toString();


                if (accountStatus.contains("inactive")){
                    accountitile.setText("Your Account is "+DataFromIntent+" Payment Now For Activition");
                }


                if (accountStatus.contains("Block")){
                    mainLayout.setVisibility(View.GONE);
                    accountHelth.setText(DataFromIntent);
                    accountHelth.setVisibility(View.VISIBLE);
                }else if (accountStatus.contains("Pending")){

                    mainLayout.setVisibility(View.GONE);
                    accountHelth.setText("Your Account is "+DataFromIntent+"\n Your Key Is :"+key);
                    accountHelth.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void DatasendToFirebse(String account, String trasngationid,  String phone, String bank, String key)
    {
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        HashMap<String,String>pay=new HashMap<>();
        pay.put("accountNumber",account);
        pay.put("trasngationid",trasngationid);
        pay.put("refcode",key);
        pay.put("bank",bank);
        pay.put("color","1");
        pay.put("phone",phone);
        pay.put("amount",amonts);
        pay.put("key",key);
        pay.put("status","Pending");



        reference.child("users").child(keys).child("paymentInformetion").setValue(pay).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    reference.child("users").child(key).child("accountStatus").setValue("Pending").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(Paymnet.this, "Contrak Administor ", Toast.LENGTH_SHORT).show();


                            }else {
                                Intent intent=new Intent(Paymnet.this,MainActivity.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Paymnet.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Paymnet.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }

    @Override
    protected void onStart() {


        checkValidition();

        super.onStart();
    }
}