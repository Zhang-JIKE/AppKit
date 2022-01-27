package com.jike.uikit.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.jike.corekit.utils.DisplayUtils;
import com.jike.uikit.R;
import com.jike.uikit.uiengine.FlexUiEngine;

public class AppSwitch extends BaseView {

    private boolean isChecked;

    private Paint backPaint, strokePaint, togglePaint, toggleLight, toggleGrey;

    private float toggleRadius;

    private float lb, rb, curX;

    private ValueAnimator animator;

    private float togglePadding;

    public interface OnSwitchChangedListner {
        void onSwitchChanged(boolean isChecked);
    }

    private OnSwitchChangedListner onSwitchChangedListner;

    public void setOnSwitchChangedListner(OnSwitchChangedListner onSwitchChangedListner) {
        this.onSwitchChangedListner = onSwitchChangedListner;
    }

    public AppSwitch(Context context) {
        super(context);
    }

    public AppSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(AttributeSet attrs) {
        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.AppSwitch, 0, 0);
        isChecked = array.getBoolean(R.styleable.AppSwitch_appswitch_checked, false);
        togglePadding = array.getDimension(R.styleable.AppSwitch_appswitch_togglePadding, DisplayUtils.dp2px(1, getContext()));
        array.recycle();

        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setColor(Color.parseColor("#CDD4D9"));
        strokePaint.setStrokeWidth(borderWidth);
        strokePaint.setStyle(Paint.Style.STROKE);

        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(backColor);
        backPaint.setStyle(Paint.Style.FILL);

        togglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        togglePaint.setColor(foreColor);
        togglePaint.setStyle(Paint.Style.FILL);

        toggleGrey = new Paint(Paint.ANTI_ALIAS_FLAG);
        toggleGrey.setColor(getResources().getColor(R.color.appBlack4));
        toggleGrey.setStyle(Paint.Style.FILL);

        toggleLight = new Paint(Paint.ANTI_ALIAS_FLAG);
        toggleLight.setColor(foreColor);
        toggleLight.setAlpha(255);
    }

    @Override
    public int getWrapContentWidth() {
        return DisplayUtils.dp2px(40f, getContext());
    }

    @Override
    public int getWrapContentHeight() {
        return DisplayUtils.dp2px(24f, getContext());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        toggleRadius = (height - 2 * borderWidth)/2 - togglePadding;

        toggleLight.setMaskFilter(new BlurMaskFilter(toggleRadius, BlurMaskFilter.Blur.OUTER));

        if(isForeGradient) {
            Shader mShader = new LinearGradient(0, 0, width, height,
                    new int[]{foreStartColor, foreEndColor},
                    null, Shader.TileMode.MIRROR);
            togglePaint.setShader(mShader);
        }

        radius = height/2f;

        lb = borderWidth + toggleRadius + togglePadding;
        rb = width - toggleRadius - borderWidth - togglePadding;

        if(isChecked){
            curX = rb;
        }else {
            curX = lb;
        }
        float percent = (curX - lb)/(rb - lb);
        toggleLight.setAlpha((int) (percent*255));
        togglePaint.setAlpha((int) (percent*255));

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChecked){
                    animUnCheck();
                }else {
                    animCheck();
                }
                if(onSwitchChangedListner!=null){
                    onSwitchChangedListner.onSwitchChanged(isChecked);
                }
            }
        });
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        if(isChecked){

        }else {

        }
        invalidate();
        if(onSwitchChangedListner!=null){
            onSwitchChangedListner.onSwitchChanged(isChecked);
        }
    }

    public boolean isChecked() {
        return isChecked;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(0, 0, width, height, radius, radius, backPaint);
        canvas.drawRoundRect(borderWidth/2, borderWidth/2, width - borderWidth/2, height - borderWidth/2, radius, radius, strokePaint);
        canvas.drawCircle(curX, height/2, toggleRadius, toggleGrey);
        canvas.drawCircle(curX, height/2, toggleRadius, togglePaint);
    }

    private void animCheck(){
        isChecked = true;
        if(animator != null){
            animator.cancel();
            animator.removeAllUpdateListeners();
            animator.removeAllListeners();
        }

        animator=ValueAnimator.ofFloat(curX, rb);
        animator.setDuration(350);
        animator.setInterpolator(FlexUiEngine.EaseInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                curX = v;
                float percent = (curX - lb)/(rb - lb);
                toggleLight.setAlpha((int) (percent*255));
                togglePaint.setAlpha((int) (percent*255));
                invalidate();
            }
        });
        animator.start();
    }

    private void animUnCheck(){
        isChecked = false;
        if(animator != null){
            animator.cancel();
            animator.removeAllUpdateListeners();
            animator.removeAllListeners();
        }

        animator=ValueAnimator.ofFloat(curX, lb);
        animator.setDuration(350);
        animator.setInterpolator(FlexUiEngine.EaseInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                curX = v;
                float percent = (curX - lb)/(rb - lb);
                toggleLight.setAlpha((int) (percent*255));
                togglePaint.setAlpha((int) (percent*255));
                invalidate();
            }
        });
        animator.start();
    }

}
