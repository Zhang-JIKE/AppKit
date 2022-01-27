package com.jike.corekit;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public abstract class FragmentAdapter {

    private Fragment[] mFragments;

    private FragmentManager mFragmentManager;

    private FragmentTransaction mTransaction;

    private int containerLayoutId;

    public FragmentAdapter(Context context, View container){
        this.containerLayoutId = container.getId();
        mFragments = new Fragment[getFragmentsCount()];
        AppCompatActivity activity = (AppCompatActivity) context;
        mFragmentManager = activity.getSupportFragmentManager();
    }

    public void showFragment(int index){
        if(mFragments != null){
            if(index < 0){
                index = 0;
            }
            if(index >= getFragmentsCount()){
                index = getFragmentsCount() - 1;
            }
            mTransaction = mFragmentManager.beginTransaction();

            hideAllFragments();

            Fragment selectedFragment = mFragments[index];
            if(selectedFragment == null){
                Fragment nFragment = onCreateFragment(index);
                mFragments[index] = nFragment;
                mTransaction.add(containerLayoutId, mFragments[index]);
            }else{
                mTransaction.show(selectedFragment);
            }

            mTransaction.commitAllowingStateLoss();
        }
    }

    private void hideAllFragments(){
        if(mFragments != null){
            for(Fragment fragment : mFragments){
                if(fragment != null) {
                    mTransaction.hide(fragment);
                }
            }
        }
    }

    protected abstract Fragment onCreateFragment(int index);

    public abstract int getFragmentsCount();
}
