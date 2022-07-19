package com.shahindemunav.drawerwithbottomnavigation.OrderandPayment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.shahindemunav.drawerwithbottomnavigation.R;

public class Alpayment extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpayment);
        Intent in=getIntent();
        String sub=in.getStringExtra("subcribe");
        String like=in.getStringExtra("like");
        String comment=in.getStringExtra("comment");
        String share=in.getStringExtra("share");

        textView=findViewById(R.id.textViews);

        textView.setText(sub+like+comment+share);

    }
}