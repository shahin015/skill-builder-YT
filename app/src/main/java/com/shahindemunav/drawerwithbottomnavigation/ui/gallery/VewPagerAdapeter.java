package com.shahindemunav.drawerwithbottomnavigation.ui.gallery;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.shahindemunav.drawerwithbottomnavigation.OrderandPayment.Facebook;
import com.shahindemunav.drawerwithbottomnavigation.OrderandPayment.Instragram;
import com.shahindemunav.drawerwithbottomnavigation.OrderandPayment.Tiktok;
import com.shahindemunav.drawerwithbottomnavigation.OrderandPayment.Youtube;


public class VewPagerAdapeter extends FragmentPagerAdapter {



    public VewPagerAdapeter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Youtube();
            case 1:
                return new Facebook();
            case 2:
                return new Tiktok();
            case 3:
                return new Instragram();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
