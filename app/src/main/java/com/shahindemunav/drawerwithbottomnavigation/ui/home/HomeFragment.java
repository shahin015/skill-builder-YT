package com.shahindemunav.drawerwithbottomnavigation.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.shahindemunav.drawerwithbottomnavigation.ui.dashboard.DashboardViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ///Image Slider
    private ImageSlider imageSlider;
    private int totatalslider = 0;
    private ArrayList<SlideModel> list;
    private DatabaseReference databaseReference, ref, Count;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /////firebase called
        databaseReference = FirebaseDatabase.getInstance().getReference("slider");
        Count = FirebaseDatabase.getInstance().getReference("Count");
        ref = FirebaseDatabase.getInstance().getReference();
        imageSlider = root.findViewById(R.id.image_slider);
        /////////////////////image class
        Call_All_Slider_Image();

        Count.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totatalslider = Integer.parseInt(String.valueOf(snapshot.child("count").getValue()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;

    }

    private void Call_All_Slider_Image() {


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<SlideModel> imageList = new ArrayList<>();
                list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {


                    // Toast.makeText(getContext(), ""+snapshot1.getKey(), Toast.LENGTH_SHORT).show();
                    ref = FirebaseDatabase.getInstance().getReference("slider/" + snapshot1.getKey());
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            /// SliderData data=snapshot.getValue(SliderData.class);
                            String img = (String) snapshot.child("imageUrl").getValue();
                            String tit = (String) snapshot.child("title").getValue();
                            String pb = (String) snapshot.child("pub").getValue();
                            //  Toast.makeText(getContext(), ""+img, Toast.LENGTH_SHORT).show();

                            if (!pb.equals("Privet")) {
                                imageList.add(0, new SlideModel(img, tit, null));
                                imageSlider.setImageList(imageList, ScaleTypes.FIT);
                            }


                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }


                ////  imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/skill-buider-yt.appspot.com/o/slider%2F%5BB%407f400e4.jpg?alt=media&token=ff451b97-f797-40d7-8eaf-c838a6564378", "photoTitile1", null));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}



