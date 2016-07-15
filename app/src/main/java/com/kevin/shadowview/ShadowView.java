package com.kevin.shadowview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwk on 2016/7/14 0014.
 */
public class ShadowView extends View {

    private int shadowSize;

    private String text;
    private int textColor;
    private int shadowColor;
    private int textSize;
    private Rect textBound;

    private Paint paint;

    private final int LEFT = 0;
    private final int TOP = 1;
    private final int RIGHT = 2;
    private final int BOTTOM = 4;
    private final int LEFTTOP = 8;
    private final int RIGHTTOP = 16;
    private final int RIGHTBOTTOM = 32;
    private final int LEFTBOTTOM = 64;

    private int shadowDirection = LEFTTOP;
    private List<Shadow> shadowList;

    public ShadowView(Context context) {
        this(context, null);
    }

    public ShadowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ShadowView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.ShadowView_sv_text:
                    text = a.getString(attr);
                    break;
                case R.styleable.ShadowView_sv_textColor:
                    textColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.ShadowView_sv_textSize:
                    textSize = a.getDimensionPixelSize(attr, (int) TypedValue
                            .applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                                    getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ShadowView_sv_shadowColor:
                    shadowColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.ShadowView_sv_shadowSize:
                    shadowSize = a.getDimensionPixelSize(attr, (int) TypedValue
                            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0,
                                    getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ShadowView_sv_direction:
                    shadowDirection = a.getInt(attr, RIGHTBOTTOM);
                    break;
            }

        }
        a.recycle();

        paint = new Paint();
        paint.setTextSize(textSize);
        paint.setFakeBoldText(true);
        paint.setColor(textColor);
        textBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBound);
        calcShadow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;

        // 设置宽度
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:// 明确指定
                width = getPaddingLeft() + getPaddingRight() + specSize;
                break;
            case MeasureSpec.AT_MOST:// WARP_CONTENT
                width = getPaddingLeft() + getPaddingRight() + textBound.width();
                break;
        }

        // 设置高度
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                height = getPaddingTop() + getPaddingBottom() + specSize;
                break;
            case MeasureSpec.AT_MOST:
                height = getPaddingTop() + getPaddingBottom() + textBound.height();
                break;
        }

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(shadowColor);
        for (Shadow shadow : shadowList) {
            canvas.drawText(text, getWidth() / 2 - textBound.width() / 2 + shadow.getDx(), getHeight() / 2 + textBound.height() / 2 + shadow.getDy(), paint);
        }

        paint.setColor(textColor);
        canvas.drawText(text, getWidth() / 2 - textBound.width() / 2, getHeight() / 2 + textBound.height() / 2, paint);
    }

    private void calcShadow() {
        shadowList = new ArrayList<>(shadowSize);
        for (int i = 0, len = shadowSize; i < len; i++) {
            switch (shadowDirection) {
                case LEFT:
                    shadowList.add(new Shadow(-i, 0));
                    break;
                case TOP:
                    shadowList.add(new Shadow(0, -i));
                    break;
                case RIGHT:
                    shadowList.add(new Shadow(i, 0));
                    break;
                case BOTTOM:
                    shadowList.add(new Shadow(0, i));
                    break;
                case LEFTTOP:
                    shadowList.add(new Shadow(-i, -i));
                    break;
                case RIGHTTOP:
                    shadowList.add(new Shadow(i, -i));
                    break;
                case LEFTBOTTOM:
                    shadowList.add(new Shadow(-i, i));
                    break;
                case RIGHTBOTTOM:
                    shadowList.add(new Shadow(i, i));
                    break;
            }
        }
    }

    class Shadow {

        private float dx;
        private float dy;

        public Shadow(float dx, float dy) {
            super();
            this.dx = dx;
            this.dy = dy;
        }

        public float getDx() {
            return dx;
        }

        public float getDy() {
            return dy;
        }

    }
}
