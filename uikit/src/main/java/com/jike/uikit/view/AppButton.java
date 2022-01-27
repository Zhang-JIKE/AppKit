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

public class AppButton extends AppTextView{

    private boolean isSolid;

    private Paint backPaint;

    public AppButton(Context context) {
        super(context);
    }

    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(AttributeSet attrs) {
        super.init(attrs);
        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.AppButton, 0, 0);
        isSolid = array.getBoolean(R.styleable.AppButton_appbutton_isSolid, true);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Shader mShader = new LinearGradient(0,0,width,height,
                new int[]{backStartColor,backEndColor},
                null,Shader.TileMode.MIRROR);

        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(backColor);
        backPaint.setStrokeWidth(borderWidth);

        if(isSolid){
            backPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }else {
            backPaint.setStyle(Paint.Style.STROKE);
        }

        if(isBackGradient) {
            backPaint.setShader(mShader);
        }

        if(isRound){
            radius = height/2.0f;
        }
    }

    @Override
    public int getWrapContentWidth() {
        return super.getWrapContentWidth() + DisplayUtils.dp2px(16, getContext());
    }

    @Override
    public int getWrapContentHeight() {
        return super.getWrapContentHeight() + DisplayUtils.dp2px(8, getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRoundRect(borderWidth,borderWidth,width-borderWidth,height-borderWidth,radius,radius,backPaint);

        super.onDraw(canvas);
    }
}
