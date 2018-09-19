package com.example.cuiweicong.download;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DownloadProgressView extends View {
    private int progress;
    private int color = 0xff1da1d5;
    private Rect rect;
    private Paint paint;
    private int defaultHeight = dip2px(5);

    public DownloadProgressView(Context context) {
        this(context, null);
    }

    public DownloadProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownloadProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DownloadProgressView);
        color = typedArray.getColor(R.styleable.DownloadProgressView_progress_color, color);
        typedArray.recycle();
        rect = new Rect();
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(defaultHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (progress >= 0 && progress <= 100) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            rect.left = 0;
            rect.top = 0;
            rect.right = width * progress / 100;
            rect.bottom = height;
            canvas.drawRect(rect, paint);
        }
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void downloadComplete(){
        setProgress(100);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
