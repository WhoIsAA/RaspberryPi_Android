package cn.whoisaa.raspberrypi.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Description https://github.com/nobukihiramine/VerticalSeekBarTest
 * @Author AA
 * @DateTime 2017/12/18 上午11:07
 */
public class VerticalSeekBar extends AppCompatSeekBar {

    public VerticalSeekBar(Context context) {
        super(context);
        setBackground(null);    // Ripple Effect(タッチした場所から波紋状に広がるエフェクト)が追従しないので、OFFにする
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackground(null);    // Ripple Effect(タッチした場所から波紋状に広がるエフェクト)が追従しないので、OFFにする
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackground(null);    // Ripple Effect(タッチした場所から波紋状に広がるエフェクト)が追従しないので、OFFにする
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        // 今、キャンバスの座標系は、原点がシークバー表示領域の左上、X軸が右向き(→)、Y軸が下向き(↓)
        c.rotate(-90);    // キャンバスの座標系を半時計周りに90度回転。
        // 今、キャンバスの座標系は、原点がシークバー表示領域の左上、X軸が上向き(↑)、Y軸が右向き(→)
        c.translate(-getHeight(), 0);    // キャンバスの座標系を下に表示領域高さ分下げる。今、上向きがX軸なので、X軸方向に-getHeight()移動させる。
        // 今、キャンバスの座標系は、原点がシークバー表示領域の左下、X軸が上向き(↑)、Y軸が右向き(→)
        // 結果、シークバーは縦向きに表示される。下端がゼロ、上端がマックスとなる。

        super.onDraw(c);
    }

    private OnSeekBarChangeListener onChangeListener;

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    private int lastProgress = 0;

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onChangeListener.onStartTrackingTouch(this);
                setProgressAndThumb(getMax() - (int) (getMax() * event.getY() / getHeight()));
                setPressed(true);
                setSelected(true);
                break;
            case MotionEvent.ACTION_MOVE:
                setProgressAndThumb(getMax() - (int) (getMax() * event.getY() / getHeight()));
                setPressed(true);
                setSelected(true);
                break;
            case MotionEvent.ACTION_UP:
                onChangeListener.onStopTrackingTouch(this);
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

    public synchronized void setProgressAndThumb(int progress) {
        if (progress < 0) {
            progress = 0;
        }
        if (progress > getMax()) {
            progress = getMax();
        }
        setProgress(progress);    // シーク位置の更新
        onSizeChanged(getWidth(), getHeight(), 0, 0);    // 描画要素の更新
        if (progress != lastProgress) {    // シーク位置が変わった場合は、onProgressChanged()をコールする。
            lastProgress = progress;
            onChangeListener.onProgressChanged(this, progress, true);
        }
    }
}