package com.jike.uikit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.jike.corekit.utils.DisplayUtils;
import com.jike.uikit.R;

public class AppProgressView extends BaseView {

    private int progress;

    private Paint backPaint,fillPaint;

    public AppProgressView(Context context) {
        super(context);
    }

    public AppProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(AttributeSet attrs) {
        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.AppProgressView, 0, 0);
        progress = array.getInt(R.styleable.AppProgressView_appprogressview_progress, 40);
        array.recycle();

        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(backColor);

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(foreColor);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(isForeGradient) {
            Shader mShader = new LinearGradient(0, 0, width, height,
                    new int[]{foreStartColor, foreEndColor},
                    null, Shader.TileMode.MIRROR);
            fillPaint.setShader(mShader);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int value = width*progress/100;

        if(value<height){
            value=height;
        }

        canvas.drawRoundRect(0,0,width,height,height/2,height/2,backPaint);
        canvas.drawRoundRect(0,0,value,height,height/2,height/2,fillPaint);
    }


    @Override
    public int getWrapContentHeight() {
        return DisplayUtils.dp2px(5, getContext());
    }

    @Override
    public int getWrapContentWidth() {
        return DisplayUtils.dp2px(36, getContext());
    }
}
