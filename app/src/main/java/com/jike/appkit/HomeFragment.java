package com.jike.appkit;

import com.jike.corekit.BaseFragment;

public class HomeFragment extends BaseFragment {

    @Override
    protected int contentView() {
        return R.layout.fragment_1home;
    }

    @Override
    protected void initView() {
        //new LThread().start();
    }
    class LThread extends Thread{

        @Override
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
