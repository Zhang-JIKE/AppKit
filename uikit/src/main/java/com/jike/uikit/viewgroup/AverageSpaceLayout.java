package com.jike.uikit.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class AverageSpaceLayout extends ViewGroup {

    public AverageSpaceLayout(Context context) {
        super(context);
    }

    public AverageSpaceLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childNum = getChildCount();
        int layoutWidth = getWidth() - getPaddingLeft() - getPaddingRight();

        int childWidSum = 0;

        for(int i = 0; i < childNum; i++){
            View view = getChildAt(i);
            childWidSum += view.getMeasuredWidth();
        }

        int spaceWidth = 0;
        if(childNum > 1) {
            spaceWidth = (layoutWidth - childWidSum) / (childNum - 1);
        }
        
        int nowX = getPaddingLeft();
        for(int i = 0; i < childNum; i++){
            View view = getChildAt(i);
            
            view.layout(
                    nowX,
                    (getMeasuredHeight() - view.getMeasuredHeight()) / 2,
                    nowX + view.getMeasuredWidth(),
                    (getMeasuredHeight() + view.getMeasuredHeight()) / 2
            );

            nowX += view.getMeasuredWidth() + spaceWidth;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;
        for(int i = 0; i < getChildCount();i++) {
            View view = getChildAt(i);

            int viewHeight = view.getMeasuredHeight();
            int viewWidth = view.getMeasuredWidth();

            width += viewWidth;
            if(viewHeight > height){
                height = viewHeight;
            }
        }

        setMeasuredDimension(
                widthMode == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                heightMode == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );

    }

    public void animShowChildView(int delay){
        for(int i = 0; i < getChildCount();i++) {
            View view = getChildAt(i);
            view.animate()
                    .translationYBy(-view.getMeasuredHeight())
                    .translationY(0)
                    .alphaBy(0)
                    .alpha(1)
                    .setInterpolator(new LinearOutSlowInInterpolator())
                    .setDuration(500).setStartDelay(delay * i).start();
        }
    }

    public void animHideChildView(int delay){
        for(int i = 0; i < getChildCount();i++) {
            View view = getChildAt(i);
            view.animate()
                    .translationYBy(0f)
                    .translationY(-view.getMeasuredHeight())
                    .alphaBy(1)
                    .alpha(0)
                    .setInterpolator(new FastOutLinearInInterpolator())
                    .setDuration(500).setStartDelay(delay * (getChildCount() - i)).start();
        }
    }
}
