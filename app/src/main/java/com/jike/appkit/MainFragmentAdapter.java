package com.jike.appkit;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.jike.corekit.FragmentAdapter;

public class MainFragmentAdapter extends FragmentAdapter {


    public MainFragmentAdapter(Context context, View container) {
        super(context, container);
    }

    @Override
    protected Fragment onCreateFragment(int index) {
        if(index == 0){
            return new HomeFragment();
        }else if(index == 1){
            return new SearchFragment();
        }else if(index == 2){
            return new LikeFragment();
        }else if(index == 3){
            return new MineFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getFragmentsCount() {
        return 4;
    }
}
