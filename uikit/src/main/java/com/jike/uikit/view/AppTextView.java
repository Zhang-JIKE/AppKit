package com.jike.uikit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.core.content.res.ResourcesCompat;

import com.jike.corekit.utils.DisplayUtils;
import com.jike.uikit.R;

public class AppTextView extends BaseView{

    private String text;
    private int textSize;
    private int fontId;

    private float boldSize;
    private boolean fakeBold;

    private float space;

    protected Paint textPaint;
    private Paint.FontMetricsInt fontMetrics;
    private Rect rect=new Rect();

    public AppTextView(Context context) {
        super(context);
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(AttributeSet attrs) {
        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.AppTextView, 0, 0);
        text = array.getString(R.styleable.AppTextView_apptextview_text);
        textSize = (int) array.getDimension(R.styleable.AppTextView_apptextview_textSize, DisplayUtils.sp2px(14,getContext()));
        fontId = array.getResourceId(R.styleable.AppTextView_apptextview_font, 0);
        boldSize = array.getFloat(R.styleable.AppTextView_apptextview_boldSize,2);
        fakeBold = array.getBoolean(R.styleable.AppTextView_apptextview_fakeBold,true);

        space = array.getFloat(R.styleable.AppTextView_apptextview_space, 0.02f);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(boldSize);

        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setFakeBoldText(fakeBold);

        textPaint.setLetterSpacing(space);

        if(fontId != 0) {
            textPaint.setTypeface(ResourcesCompat.getFont(getContext(), fontId));
        }
        fontMetrics = textPaint.getFontMetricsInt();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(isTextGradient) {
            Shader mShader = new LinearGradient(0, 0, width, height,
                    new int[]{textStartColor, textEndColor},
                    null, Shader.TileMode.MIRROR);
            textPaint.setShader(mShader);
        }
        rect.set(0, 0, width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int baseline = rect.top + (rect.bottom - rect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(text,width/2f,baseline,textPaint);
    }

    @Override
    public int getWrapContentHeight() {
        return fontMetrics.descent-fontMetrics.ascent;
    }

    @Override
    public int getWrapContentWidth() {
        return (int) (textPaint.measureText(text));
    }
}
