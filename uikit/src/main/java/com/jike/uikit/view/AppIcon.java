package com.jike.uikit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.jike.corekit.utils.BitmapUtils;
import com.jike.corekit.utils.DisplayUtils;
import com.jike.uikit.R;

public class AppIcon extends BaseView {

    private Bitmap icon;

    private Paint iconPaint;

    private Rect srcRect,dstRect;

    private boolean isRender;

    private float scale;

    public AppIcon(Context context) {
        super(context);
    }

    public AppIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(AttributeSet attrs) {
        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.AppIcon, 0, 0);
        icon = BitmapUtils.getBitmapById(getContext(), array.getResourceId(R.styleable.AppIcon_appicon_src, R.drawable.ic_mark));
        isRender = array.getBoolean(R.styleable.AppIcon_appicon_isRender, false);
        iconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        iconPaint.setColor(foreColor);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(isForeGradient){
            Shader mShader = new LinearGradient(0,0,width,height,
                    new int[]{foreStartColor,foreEndColor},
                    null,Shader.TileMode.MIRROR);
            iconPaint.setShader(mShader);
        }

        srcRect=new Rect(0,0,icon.getWidth(),icon.getHeight());

        scale = DisplayUtils.dp2px(24, getContext())/srcRect.width();

        dstRect=new Rect(getPaddingLeft(),getPaddingTop(),width-getPaddingRight(),height-getPaddingBottom());


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.TRANSPARENT);
        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        int saveCount = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        //???????????????
        canvas.drawBitmap(icon,srcRect,dstRect,iconPaint);

        //??????????????????
        if(isRender){
            iconPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }
        //????????????
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), width - getPaddingRight(), height - getPaddingBottom(), iconPaint);
        //??????????????????
        iconPaint.setXfermode(null);
        //????????????
        canvas.restoreToCount(saveCount);
    }

    @Override
    protected int getWrapContentWidth() {
        return DisplayUtils.dp2px(24, getContext());
    }

    @Override
    protected int getWrapContentHeight() {
        return DisplayUtils.dp2px(24, getContext());
    }

    public void setIconColor(int color) {
        iconPaint.setColor(color);
        invalidate();

    }
}
