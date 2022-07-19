package com.shahindemunav.drawerwithbottomnavigation.ui.gallery;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.shahindemunav.drawerwithbottomnavigation.R;

public class GalleryFragment extends Fragment {
    private FrameLayout layout;
    private ViewPager viewPager;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        viewPager=root.findViewById(R.id.viewPager);
        VewPagerAdapeter viwepagerAdapter=new VewPagerAdapeter(getChildFragmentManager());
        viewPager.setAdapter(viwepagerAdapter);



        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager=view.findViewById(R.id.viewPager);




    }
}