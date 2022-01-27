package com.jike.leakradar;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.jike.corekit.FragmentAdapter;
import com.jike.leakradar.fragment.AboutFragment;
import com.jike.leakradar.fragment.HeapFragment;
import com.jike.leakradar.fragment.LeakFragment;

public class LeakFragmentAdapter extends FragmentAdapter {

    public LeakFragmentAdapter(Context context, View container) {
        super(context, container);
    }

    @Override
    protected Fragment onCreateFragment(int index) {
        if(index == 0){
            return new LeakFragment();
        }else if(index == 1){
            return new HeapFragment();
        }else if(index == 2){
            return new AboutFragment();
        }
        return new LeakFragment();
    }

    @Override
    public int getFragmentsCount() {
        return 3;
    }
}
