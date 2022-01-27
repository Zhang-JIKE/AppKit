package com.jike.corekit;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jike.corekit.context.AppContextKit;
import com.jike.corekit.annotation.viewbinder.OnClickAnnotation;
import com.jike.corekit.annotation.viewbinder.ViewBinderAnnotation;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppContextKit.setContext(this);
        AppContextKit.setActivity(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(contentView());


        ViewBinderAnnotation.deploy(this);

        OnClickAnnotation.deploy(this);


        initView();


    }

    protected abstract int contentView();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppContextKit.release();
    }
}
