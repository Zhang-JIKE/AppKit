package com.jike.uikit.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.jike.corekit.utils.DisplayUtils;
import com.jike.uikit.R;
import com.jike.uikit.viewgroup.base.BaseLayout;

public class WaterFallLayout extends BaseLayout {

    private int colNum;

    private float space;

    private int childWidth;

    private Rect[] rects;

    public WaterFallLayout(Context context) {
        super(context);
    }

    public WaterFallLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void init(AttributeSet attrs) {
        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.WaterFallLayout, 0, 0);
        colNum = array.getInt(R.styleable.WaterFallLayout_waterfalllayout_col, 2);
        space = array.getDimension(R.styleable.WaterFallLayout_waterfalllayout_space, DisplayUtils.dp2px(22, getContext()));
        array.recycle();
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        super.onLayout(b, i, i1, i2, i3);
        for(int ii = 0; ii < getChildCount(); ii++){
            View view = getChildAt(ii);
            view.layout(rects[ii].left, rects[ii].top, rects[ii].right, rects[ii].bottom);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        childWidth = (int)((width - (space * (colNum + 1))) / colNum);
        int widthMeasureSpec_child = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
        measureChildren(widthMeasureSpec_child, heightMeasureSpec);
    }

    @Override
    public int getWrapContentWidth() {
        return 0;
    }

    @Override
    public int getWrapContentHeight() {
        int count = getChildCount();


        rects = new Rect[count];

        int curColmn = 0;
        int curRow = 0;

        int[] colHeights = new int[colNum];

        for(int i = 0; i < count; i++){
            View view = getChildAt(i);

            int childHeight = view.getMeasuredHeight();
            colHeights[curColmn] += (space + childHeight);

            int left = (int) ((curColmn + 1) * space + curColmn * childWidth);
            int top = colHeights[curColmn] - childHeight;
            int right = left + childWidth;
            int bottom = colHeights[curColmn];

            rects[i] = new Rect(left, top, right, bottom);

            //列号+1，如果满列就行号+1，列号置0
            curColmn++;
            if(curColmn >= colNum){
                curColmn = 0;
                curRow++;
            }
        }

        int maxHeight = getMax(colHeights);

        return (int) (maxHeight + space);
    }

    private int getMax(int[] num){
        int max = 0;
        for(int n : num){
            if(n > max){
                max = n;
            }
        }
        return max;
    }
}
