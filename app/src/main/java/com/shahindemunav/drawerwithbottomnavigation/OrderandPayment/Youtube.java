package com.shahindemunav.drawerwithbottomnavigation.OrderandPayment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahindemunav.drawerwithbottomnavigation.R;

import java.util.ArrayList;

import java.util.List;


public class Youtube extends Fragment {
    private Spinner spinner_subcrive,spinner_like,spinner_commnet,spinner_share;

    private DatabaseReference reference;
    private ProgressDialog progressDialog;
    private Button how_much_pay,go_for_pay;
    private LinearLayout sublayout,like_layout,comment_layout,share_layout;
    private TextView totlas_subcriver,subcriver_amount,total_like,like_amount,totoal_comment,comment_amount,total_share,share_amount;
    private RadioButton radioButton_sub,radioButton_like,radioButton_comment,radioButton_share;
    private  int all_value=0;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_youtube, container, false);
        total_like=view.findViewById(R.id.total_like);
        progressDialog=new ProgressDialog(getActivity());
        go_for_pay=view.findViewById(R.id.go_for_pay);

        like_amount=view.findViewById(R.id.like_amount);
        totoal_comment=view.findViewById(R.id.comment_detels);
        comment_amount=view.findViewById(R.id.comment_amont);
        total_share=view.findViewById(R.id.share_detels);
        share_amount=view.findViewById(R.id.share_amount);
        spinner_subcrive=view.findViewById(R.id.Spinner_subcsrive);
        spinner_like=view.findViewById(R.id.Spinner_like);
        spinner_commnet=view.findViewById(R.id.Spinner_comment);
        spinner_share=view.findViewById(R.id.Spinner_share);
        radioButton_sub=view.findViewById(R.id.subcsrive);
        radioButton_like=view.findViewById(R.id.like);
        radioButton_comment=view.findViewById(R.id.comment);
        radioButton_share=view.findViewById(R.id.share);
        sublayout=view.findViewById(R.id.sub_layout);
        comment_layout=view.findViewById(R.id.commet_layout);
        like_layout=view.findViewById(R.id.like_layout);
        share_layout=view.findViewById(R.id.share_layout);
        totlas_subcriver=view.findViewById(R.id.Subcribe_text);
        subcriver_amount=view.findViewById(R.id.Subcribe_amount);



        reference= FirebaseDatabase.getInstance().getReference("Youtube");
        setSpnnerData(spinner_subcrive,spinner_like,spinner_commnet,spinner_share);

        getDataFormFireBase();
        how_much_pay=view.findViewById(R.id.findTaka);


        radioButton_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag=radioButton_sub.getTag().toString();
                if (tag.contains("s")){
                    go_for_pay.setClickable(false);
                    go_for_pay.setText("Click How Much For Pay");
                    radioButton_sub.setSelected(true);
                    spinner_subcrive.setVisibility(View.VISIBLE);
                    sublayout.setVisibility(View.VISIBLE);



                    radioButton_sub.setTag("f");
                }else if (tag.contains("f")){
                    sublayout.setVisibility(View.GONE);

                    go_for_pay.setClickable(false);
                    go_for_pay.setText("Click How Much For Pay");

                    spinner_subcrive.setVisibility(View.INVISIBLE);
                    radioButton_sub.setSelected(false);
                    radioButton_sub.setChecked(false);
                    radioButton_sub.setTag("s");
                }



            }
        });


        radioButton_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag=radioButton_like.getTag().toString();
                if (tag.contains("s")){
                    go_for_pay.setClickable(false);
                    go_for_pay.setText("Click How Much For Pay");
                    like_layout.setVisibility(View.VISIBLE);
                    radioButton_like.setSelected(true);
                    spinner_like.setVisibility(View.VISIBLE);
                    radioButton_like.setTag("f");
                }else if (tag.contains("f")){
                    go_for_pay.setClickable(false);
                    go_for_pay.setText("Click How Much For Pay");
                    spinner_like.setVisibility(View.INVISIBLE);
                    like_layout.setVisibility(View.GONE);
                    radioButton_like.setSelected(false);
                    radioButton_like.setChecked(false);
                    radioButton_like.setTag("s");
                }



            }
        });


        radioButton_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag=radioButton_comment.getTag().toString();
                if (tag.contains("s")){
                    radioButton_comment.setSelected(true);
                    go_for_pay.setClickable(false);
                    go_for_pay.setText("Click How Much For Pay");
                    spinner_commnet.setVisibility(View.VISIBLE);
                    comment_layout.setVisibility(View.VISIBLE);
                    radioButton_comment.setTag("f");
                }else if (tag.contains("f")){
                    go_for_pay.setClickable(false);
                    go_for_pay.setText("Click How Much For Pay");
                    comment_layout.setVisibility(View.GONE);
                    spinner_commnet.setVisibility(View.GONE);
                    radioButton_comment.setSelected(false);
                    radioButton_comment.setChecked(false);
                    radioButton_comment.setTag("s");
                }



            }
        });



        radioButton_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag=radioButton_share.getTag().toString();
                if (tag.contains("s")){
                    radioButton_share.setSelected(true);
                    spinner_share.setVisibility(View.VISIBLE);
                    share_layout.setVisibility(View.VISIBLE);
                    go_for_pay.setClickable(false);
                    go_for_pay.setText("Click How Much For Pay");




                    radioButton_share.setTag("f");
                }else if (tag.contains("f")){
                    go_for_pay.setClickable(false);
                    go_for_pay.setText("Click How Much For Pay");

                    share_layout.setVisibility(View.GONE);
                    spinner_share.setVisibility(View.GONE);
                    radioButton_share.setSelected(false);
                    radioButton_share.setChecked(false);
                    radioButton_share.setTag("s");
                }



            }
        });



        how_much_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_for_pay.setClickable(true);
                go_for_pay.setText("Go For Pay");
                all_value=0;
                subcriver_amount.setText("0");
                like_amount.setText("0");
                comment_amount.setText("0");
                share_amount.setText("0");
                String subcrive=spinner_subcrive.getSelectedItem().toString();
                String like=spinner_like.getSelectedItem().toString();
                String share=spinner_share.getSelectedItem().toString();
                String comment=spinner_commnet.getSelectedItem().toString();
                boolean r_subcrive=radioButton_sub.isSelected();
                boolean r_like=radioButton_like.isSelected();
                boolean r_coment=radioButton_comment.isSelected();
                boolean r_share=radioButton_share.isSelected();

                if (r_subcrive==true){
                    getData_From_firebase(subcrive,like,share,comment,r_subcrive,r_like,r_coment,r_share);

                }

                if (r_like==true){
                    getData_From_firebase(subcrive,like,share,comment,r_subcrive,r_like,r_coment,r_share);

                }
                if (r_coment==true){
                    getData_From_firebase(subcrive,like,share,comment,r_subcrive,r_like,r_coment,r_share);

                }

                if (r_share==true){
                    getData_From_firebase(subcrive,like,share,comment,r_subcrive,r_like,r_coment,r_share);

                }

                Toast.makeText(getActivity(), r_subcrive+"", Toast.LENGTH_SHORT).show();

//                if (subcrive.isEmpty()||like.isEmpty()||share.isEmpty()||comment.isEmpty()){
//                    Toast.makeText(getActivity(), "Somthing Error", Toast.LENGTH_SHORT).show();
//                    return;
//                }


            }
        });

        go_for_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Loading ");
                progressDialog.setCancelable(false);
                progressDialog.show();
                int subamoun= Integer.parseInt(String.valueOf(subcriver_amount.getText().toString()));
                int likamoun= Integer.parseInt(String.valueOf(like_amount.getText().toString()));
                int commentamoun= Integer.parseInt(String.valueOf(comment_amount.getText().toString()));
                int shareamoun= Integer.parseInt(String.valueOf(share_amount.getText().toString()));
                int all=subamoun+likamoun+commentamoun+shareamoun;


                progressDialog.dismiss();



                AlertDialog builder=new AlertDialog.Builder(getActivity())
                        .setTitle(" Are You Shure for paymnet")
                        .setMessage(all+" Taka You Have to Pay")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (all==0){
                                    Toast.makeText(getContext(), "Selecet Somthing", Toast.LENGTH_SHORT).show();
                                    dialogInterface.dismiss();
                                }
                                Intent intent=new Intent(getActivity(),Alpayment.class);
                                intent.putExtra("subcribe",totlas_subcriver.getText().toString());
                                intent.putExtra("like",total_like.getText().toString());
                                intent.putExtra("comment",totoal_comment.getText().toString());
                                intent.putExtra("share",total_share.getText().toString());
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                getActivity().startActivity(intent);




                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                builder.show();

            }
        });


        return view;

    }


    private void getData_From_firebase(String subcrive, String like, String share, String comment, boolean r_subcrive, boolean r_like, boolean r_coment, boolean r_share) {

        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();


        try {

            if (r_subcrive==true){
                reference.child("Subcribe").child(subcrive).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String sub=snapshot.child("Subcribe").getValue().toString();
                        String sub_amount=snapshot.child("amount").getValue().toString();
                        totlas_subcriver.setText("Subcribe: "+subcrive);

                        if (!sub_amount.isEmpty()){
                            subcriver_amount.setText(sub_amount);



                        }else {
                            subcriver_amount.setText("No data Found");
                        }
                        progressDialog.dismiss();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            if (r_like==true){
                reference.child("Like").child(like).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String sub=snapshot.child("Like").getValue().toString();
                        String sub_amount=snapshot.child("amount").getValue().toString();


                        total_like.setText("Like: "+like);
                        if (!sub_amount.isEmpty()){
                            like_amount.setText(sub_amount);


                        }else {
                            like_amount.setText("No data Found");
                        }

                        progressDialog.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            if (r_coment==true){
                reference.child("Comment").child(comment).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String sub=snapshot.child("Comment").getValue().toString();
                        String sub_amount=snapshot.child("amount").getValue().toString();


                        totoal_comment.setText("Comment: "+comment);
                        if (!sub_amount.isEmpty()){
                            comment_amount.setText(sub_amount);

                        }else {
                            comment_amount.setText("No data Found");
                        }
                        progressDialog.dismiss();




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            if (r_share==true){
                reference.child("Share").child(share).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String sub=snapshot.child("Share").getValue().toString();
                        String sub_amount=snapshot.child("amount").getValue().toString();
                                               total_share.setText("Share: "+sub);
                        if (!sub_amount.isEmpty()){
                            share_amount.setText(sub_amount);

                        }else {
                            share_amount.setText("No data Found");
                        }
                        progressDialog.dismiss();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }


        }catch (Exception e){
            Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void getDataFormFireBase() {
    }


    private void setSpnnerData(Spinner spinner_subcrive, Spinner spinner_like, Spinner spinner_commnet, Spinner spinner_share) {
        final List<String> nomeConsulta = new ArrayList<String>();
        nomeConsulta.add("100");
        nomeConsulta.add("200");
        nomeConsulta.add("300");
        nomeConsulta.add("400");
        nomeConsulta.add("500");
        nomeConsulta.add("600");
        nomeConsulta.add("700");
        nomeConsulta.add("800");
        nomeConsulta.add("900");
        nomeConsulta.add("1000");
        nomeConsulta.add("1100");
        nomeConsulta.add("1200");
        nomeConsulta.add("1300");
        nomeConsulta.add("1400");
        nomeConsulta.add("1500");
        nomeConsulta.add("1600");
        nomeConsulta.add("1700");
        nomeConsulta.add("1800");
        nomeConsulta.add("1900");
        nomeConsulta.add("2000");
        nomeConsulta.add("2100");
        nomeConsulta.add("2200");
        nomeConsulta.add("2300");
        nomeConsulta.add("2400");
        nomeConsulta.add("2500");
        nomeConsulta.add("1000");
        nomeConsulta.add("2600");
        nomeConsulta.add("2700");
        nomeConsulta.add("2800");
        nomeConsulta.add("2900");
        nomeConsulta.add("3000");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, nomeConsulta);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_subcrive.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, nomeConsulta);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_like.setAdapter(arrayAdapter2);

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, nomeConsulta);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_commnet.setAdapter(arrayAdapter3);
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, nomeConsulta);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_share.setAdapter(arrayAdapter4);

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Youtube() {
        // Required empty public constructor
    }


}