package com.jike.appkit;

import android.widget.FrameLayout;

import com.jike.corekit.BaseActivity;
import com.jike.corekit.annotation.viewbinder.OnClick;
import com.jike.corekit.annotation.viewbinder.ViewBinder;

public class MainActivity extends BaseActivity {

    @ViewBinder(id = R.id.container)
    private FrameLayout mainContainer;

    private MainFragmentAdapter fragmentAdapter;

    @Override
    protected int contentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        fragmentAdapter = new MainFragmentAdapter(MainActivity.this, mainContainer);
        fragmentAdapter.showFragment(0);

        App.activity = this;
    }
    @OnClick(id = R.id.item1)
    private void onClick1(){
        fragmentAdapter.showFragment(0);
    }

    @OnClick(id = R.id.item2)
    private void onClick2(){
        fragmentAdapter.showFragment(1);
    }

    @OnClick(id = R.id.item3)
    private void onClick3(){
        fragmentAdapter.showFragment(2);
    }

    @OnClick(id = R.id.item4)
    private void onClick4(){
        fragmentAdapter.showFragment(3);
    }

}