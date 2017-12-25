package cn.whoisaa.raspberrypi.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import cn.whoisaa.raspberrypi.R;

/**
 * @Description
 * @Author AA
 * @DateTime 2017/12/18 上午11:07
 */
public class VerticalSeekBar extends AppCompatSeekBar {

    private static final int HORIZONTAL_VALUE = 0;

    private boolean mIsHorizontal;
    private int mLastProgress;
    private OnSeekBarChangeListener mOnChangeListener;

    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray array = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.VerticalSeekBar, defStyle, 0
        );
        int orientationValue = array.getInt(R.styleable.VerticalSeekBar_orientation, HORIZONTAL_VALUE);
        mIsHorizontal = orientationValue == HORIZONTAL_VALUE;

        array.recycle();
    }

    @Override
    protected void onDraw(Canvas c) {
        if (!mIsHorizontal) {
            c.rotate(-90);
            c.translate(-getHeight(), 0);
        }
        super.onDraw(c);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mIsHorizontal) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(heightMeasureSpec, widthMeasureSpec);
            setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        if (mIsHorizontal) {
            super.onSizeChanged(w, h, oldW, oldH);
        } else {
            // Swap dimensions
            super.onSizeChanged(h, w, oldH, oldW);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        if (mIsHorizontal) {
            return super.onTouchEvent(event);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mOnChangeListener != null) {
                    mOnChangeListener.onStartTrackingTouch(this);
                }
                setPressed(true);
                setSelected(true);
                break;
            case MotionEvent.ACTION_MOVE:
                super.onTouchEvent(event);
                int progress = getMax() - (int) (getMax() * event.getY() / getHeight());

                // Ensure progress stays within boundaries
                if (progress < 0) {
                    progress = 0;
                }
                if (progress > getMax()) {
                    progress = getMax();
                }
                setProgress(progress); // Draw progress
                if (progress != mLastProgress) {
                    // Only enact listener if the progress has actually changed
                    mLastProgress = progress;
                    if (mOnChangeListener != null) {
                        mOnChangeListener.onProgressChanged(this, progress, true);
                    }
                }

                onSizeChanged(getWidth(), getHeight(), 0, 0);
                setPressed(true);
                setSelected(true);
                break;
            case MotionEvent.ACTION_UP:
                if (mOnChangeListener != null) {
                    mOnChangeListener.onStopTrackingTouch(this);
                }
                setPressed(false);
                setSelected(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                super.onTouchEvent(event);
                setPressed(false);
                setSelected(false);
                break;
        }
        return true;
    }

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onChangeListener) {
        mOnChangeListener = onChangeListener;
    }
}