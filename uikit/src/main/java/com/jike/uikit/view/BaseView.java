package com.jike.uikit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jike.corekit.utils.DisplayUtils;
import com.jike.uikit.R;
import com.jike.uikit.uiengine.FlexUiEngine;

public abstract class BaseView extends View {

    protected int width;
    protected int height;

    protected boolean isBackGradient;
    protected boolean isForeGradient;
    protected boolean isTextGradient;
    protected boolean isRound;

    protected float radius,borderWidth;
    protected int backStartColor,backEndColor,backColor;
    protected int textStartColor,textEndColor,textColor;
    protected int foreStartColor,foreEndColor,foreColor;

    protected int pressAnimStyle;

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.BaseView, 0, 0);
        isRound = array.getBoolean(R.styleable.BaseView_uikit_isRound, false);
        radius = array.getDimension(R.styleable.BaseView_uikit_radius, DisplayUtils.dp2px(4, getContext()));
        borderWidth = array.getDimension(R.styleable.BaseView_uikit_borderWidth,DisplayUtils.dp2px(1, getContext()));

        isBackGradient = array.getBoolean(R.styleable.BaseView_uikit_isBackGradient, false);
        backStartColor = array.getColor(R.styleable.BaseView_uikit_backStartColor, Color.parseColor("#0064FE"));
        backEndColor = array.getColor(R.styleable.BaseView_uikit_backEndColor, Color.parseColor("#0064FE"));
        backColor = array.getColor(R.styleable.BaseView_uikit_backColor, Color.parseColor("#0064FE"));

        isTextGradient = array.getBoolean(R.styleable.BaseView_uikit_isTextGradient, false);
        textStartColor = array.getColor(R.styleable.BaseView_uikit_textStartColor, Color.parseColor("#FFFFFF"));
        textEndColor = array.getColor(R.styleable.BaseView_uikit_textEndColor, Color.parseColor("#FFFFFF"));
        textColor = array.getColor(R.styleable.BaseView_uikit_textColor, Color.parseColor("#FFFFFF"));

        isForeGradient = array.getBoolean(R.styleable.BaseView_uikit_isForeGradient, false);
        foreStartColor = array.getColor(R.styleable.BaseView_uikit_foreStartColor, Color.parseColor("#FFFFFF"));
        foreEndColor = array.getColor(R.styleable.BaseView_uikit_foreEndColor, Color.parseColor("#FFFFFF"));
        foreColor = array.getColor(R.styleable.BaseView_uikit_foreColor, Color.parseColor("#FFFFFF"));

        pressAnimStyle = array.getInt(R.styleable.BaseView_uikit_pressAnimStyle, 0);

        init(attrs);
    }

    protected abstract void init(AttributeSet attrs);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();

        width = measureWidth(minimumWidth, widthMeasureSpec);
        height = measureHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = getWrapContentWidth();
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = getWrapContentWidth();
        }
        return defaultWidth;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultHeight = getWrapContentHeight();
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = getWrapContentHeight();
                break;
        }
        return defaultHeight;
    }

    protected abstract int getWrapContentWidth();
    protected abstract int getWrapContentHeight();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(pressAnimStyle == 0){
            return super.onTouchEvent(event);
        }

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(pressAnimStyle == 1){
                animate().scaleX(0.9f).scaleY(0.9f).setDuration(270)
                    .setInterpolator(FlexUiEngine.EaseInterpolator()).start();
            }else if(pressAnimStyle == 2){
                animate().alpha(0.7f).setDuration(270)
                        .setInterpolator(FlexUiEngine.EaseInterpolator()).start();
            }else if(pressAnimStyle == 3){
                animate().scaleX(0.9f).scaleY(0.9f).alpha(0.7f).setDuration(270)
                        .setInterpolator(FlexUiEngine.EaseInterpolator()).start();
            }
        }else {
            if(event.getAction()==MotionEvent.ACTION_UP){
                performClick();
            }

            if(pressAnimStyle == 1) {
                animate().scaleX(1f).scaleY(1).setDuration(270)
                        .setInterpolator(FlexUiEngine.OverInterpolator()).start();
            }else if(pressAnimStyle == 2){
                animate().alpha(1).setDuration(270)
                        .setInterpolator(FlexUiEngine.EaseInterpolator()).start();
            }else if(pressAnimStyle == 3){
                animate().scaleX(1).scaleY(1).alpha(1).setDuration(270)
                        .setInterpolator(FlexUiEngine.EaseInterpolator()).start();
            }
        }
        return true;
    }
}
