package com.jike.leakradar;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jike.corekit.BaseActivity;
import com.jike.uikit.view.AppIcon;

public class LeakActivity extends BaseActivity {

    private FrameLayout fm01,fm02,fm03,container;

    private LeakFragmentAdapter fragmentAdapter;

    private AppIcon iv01,iv02,iv03;
    private TextView tv01,tv02,tv03;

    @Override
    protected int contentView() {
        return R.layout.activity_leak;
    }

    @Override
    protected void initView() {
        fm01 = findViewById(R.id.fm01);
        fm02 = findViewById(R.id.fm02);
        fm03 = findViewById(R.id.fm03);
        container = findViewById(R.id.container);

        iv01 = findViewById(R.id.iv01);
        iv02 = findViewById(R.id.iv02);
        iv03 = findViewById(R.id.iv03);

        tv01 = findViewById(R.id.tv01);
        tv02 = findViewById(R.id.tv02);
        tv03 = findViewById(R.id.tv03);

        fragmentAdapter = new LeakFragmentAdapter(LeakActivity.this, container);

        setFragment(0);

        fm01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(0);
            }
        });

        fm02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(1);
            }
        });

        fm03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(2);
            }
        });
    }

    private void setFragment(int index){
        fragmentAdapter.showFragment(index);
        /*if(index == 0){
            iv01.setIconColor(getColor(R.color.appBlue));
            iv02.setIconColor(getColor(R.color.appLightBlue));
            iv03.setIconColor(getColor(R.color.appLightBlue));
        }else if(index == 1){
            iv01.setIconColor(getColor(R.color.appLightBlue));
            iv02.setIconColor(getColor(R.color.appBlue));
            iv03.setIconColor(getColor(R.color.appLightBlue));
        }else if(index == 2){
            iv01.setIconColor(getColor(R.color.appLightBlue));
            iv02.setIconColor(getColor(R.color.appLightBlue));
            iv03.setIconColor(getColor(R.color.appBlue));
        }*/

        if(index == 0){
            iv01.setAlpha(1);
            iv02.setAlpha(0.3f);
            iv03.setAlpha(0.3f);
            tv01.setAlpha(1);
            tv02.setAlpha(0.3f);
            tv03.setAlpha(0.3f);
        }else if(index == 1){
            iv01.setAlpha(0.3f);
            iv02.setAlpha(1);
            iv03.setAlpha(0.3f);
            tv01.setAlpha(0.3f);
            tv02.setAlpha(1);
            tv03.setAlpha(0.3f);
        }else if(index == 2){
            iv01.setAlpha(0.3f);
            iv02.setAlpha(0.3f);
            iv03.setAlpha(1);
            tv01.setAlpha(0.3f);
            tv02.setAlpha(0.3f);
            tv03.setAlpha(1);
        }
    }

}
