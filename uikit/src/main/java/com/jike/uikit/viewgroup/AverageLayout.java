package com.jike.uikit.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.jike.uikit.viewgroup.base.BaseLayout;

public class AverageLayout extends BaseLayout {

    public AverageLayout(Context context) {
        super(context);
    }

    public AverageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childNum = getChildCount();
        int layoutWidth = width - getPaddingLeft() - getPaddingRight();

        int childWidth = layoutWidth / getChildCount();
        int nowX = getPaddingLeft();

        for(int i = 0; i < childNum; i++){
            View view = getChildAt(i);
            
            view.layout(
                    nowX,
                    0,
                    nowX + childWidth,
                    height);

            nowX += childWidth;
        }
    }

    @Override
    public void init(AttributeSet attrs) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public int getWrapContentWidth() {
        int wrapWidth = 0;
        for(int i = 0; i < getChildCount();i++) {
            View view = getChildAt(i);

            int viewWidth = view.getMeasuredWidth();

            wrapWidth+=viewWidth;
        }
        return wrapWidth;
    }

    @Override
    public int getWrapContentHeight() {
        int wrapHeight = 0;
        for(int i = 0; i < getChildCount();i++) {
            View view = getChildAt(i);

            int viewHeight = view.getMeasuredHeight();

            if(viewHeight > height){
                wrapHeight = viewHeight;
            }
        }
        return wrapHeight + getPaddingTop() + getPaddingBottom();
    }

}
