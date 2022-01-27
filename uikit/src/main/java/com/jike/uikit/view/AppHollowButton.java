package com.jike.uikit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.jike.corekit.utils.DisplayUtils;
import com.jike.uikit.R;

public class AppHollowButton extends BaseView {

    private Paint backPaint,textPaint;

    private Paint.FontMetricsInt fontMetrics;

    private float textSize;

    private String text;

    private float space;

    private Rect rect=new Rect();


    public AppHollowButton(Context context) {
        super(context);
    }

    public AppHollowButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(AttributeSet attrs) {
        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.AppHollowButton, 0, 0);
        text = array.getString(R.styleable.AppHollowButton_apphollowbutton_text);
        textSize = array.getDimension(R.styleable.AppHollowButton_apphollowbutton_textSize, DisplayUtils.px2sp(14, getContext()));

        space = array.getFloat(R.styleable.AppHollowButton_apphollowbutton_space, 0);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setLetterSpacing(space);

        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setFakeBoldText(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        fontMetrics = textPaint.getFontMetricsInt();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(0,0,width,height,radius,radius,backPaint);
        int baseline = rect.top + (rect.bottom - rect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(text,width/2,baseline,textPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(backColor);
        if(isBackGradient){
            Shader mShader = new LinearGradient(0,0,width,height,
                    new int[]{backStartColor,backEndColor},
                    null,Shader.TileMode.MIRROR);
            backPaint.setShader(mShader);
        }

        if(isRound){
            radius = height/2;
        }

        rect.set(0,0,width,height);
    }

    @Override
    public int getWrapContentHeight() {
        return fontMetrics.descent-fontMetrics.ascent+DisplayUtils.dp2px(6, getContext());
    }

    @Override
    public int getWrapContentWidth() {
        return (int) (textPaint.measureText(text)+DisplayUtils.dp2px(16, getContext()));
    }

}
