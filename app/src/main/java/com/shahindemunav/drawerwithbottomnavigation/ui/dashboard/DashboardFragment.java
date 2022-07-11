package com.shahindemunav.drawerwithbottomnavigation.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahindemunav.drawerwithbottomnavigation.R;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
///Image Slider
    private ImageSlider imageSlider;

    private DatabaseReference databaseReference;


    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        /////firebase called
        databaseReference= FirebaseDatabase.getInstance().getReference("Slider");
        imageSlider=root.findViewById(R.id.image_slider);
        /////////////////////image class
        Call_All_Slider_Image();
        return root;

    }

    private void Call_All_Slider_Image() {
        databaseReference.child("SliderList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String photo=snapshot.child("photo1").getValue().toString();
                String photoTitile1=snapshot.child("photoTittle1").getValue().toString();
                String photo2=snapshot.child("photo2").getValue().toString();
                String photoTitile2=snapshot.child("photoTittle2").getValue().toString();
                String photo3=snapshot.child("photo3").getValue().toString();
                String photoTitile3=snapshot.child("photoTittle3").getValue().toString();
                String photo4=snapshot.child("photo4").getValue().toString();
                String photoTitile4=snapshot.child("photoTittle4").getValue().toString();
                String photo5=snapshot.child("photo5").getValue().toString();
                String photoTitile5=snapshot.child("photoTittle5").getValue().toString();
                String photo6=snapshot.child("photo6").getValue().toString();
                String photoTitile6=snapshot.child("photoTittle6").getValue().toString();
                String photo7=snapshot.child("photo7").getValue().toString();
                String photoTitile7=snapshot.child("photoTittle7").getValue().toString();
                String photo8=snapshot.child("photo8").getValue().toString();
                String photoTitile8=snapshot.child("photoTittle8").getValue().toString();
                String photo9=snapshot.child("photo9").getValue().toString();
                String photoTitile9=snapshot.child("photoTittle9").getValue().toString();
                String photo10=snapshot.child("photo10").getValue().toString();
                String photoTitile10=snapshot.child("photo10tittle").getValue().toString();
                ArrayList<SlideModel> imageList = new ArrayList<>();
                imageList.add(new SlideModel(photo, photoTitile1, null));
                imageList.add(new SlideModel(photo2, photoTitile2, null));
                imageList.add(new SlideModel(photo3, photoTitile3, null));
                imageList.add(new SlideModel(photo4, photoTitile4, null));
                imageList.add(new SlideModel(photo5, photoTitile5, null));
                imageList.add(new SlideModel(photo6, photoTitile6, null));
                imageList.add(new SlideModel(photo7, photoTitile7, null));
                imageList.add(new SlideModel(photo8, photoTitile8, null));
                imageList.add(new SlideModel(photo9, photoTitile9, null));
                imageList.add(new SlideModel(photo10, photoTitile10, null));
                imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel("https://media-cdn.tripadvisor.com/media/photo-s/08/e7/2c/fe/inage-ocean-park.jpg", "photoTitile1", null));

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP);

    }
}