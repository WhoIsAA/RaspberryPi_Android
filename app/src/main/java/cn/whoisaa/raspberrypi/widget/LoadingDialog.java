package cn.whoisaa.raspberrypi.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import cn.whoisaa.raspberrypi.R;

/**
 * @Description
 * @Author AA
 * @DateTime 2017/12/29 上午10:02
 */
public class LoadingDialog extends ProgressDialog {

    private TextView showTextView;
    private String showText;
    private boolean canCancel;

    public LoadingDialog(Context context) {
        this(context, R.style.loadingDialog);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading_dialog);
        showTextView = findViewById(R.id.tv_loading_dialog);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    /**
     * 设置是否可以点击其他区域取消对话框
     * @param canCancel
     */
    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    @Override
    public void show() {
        super.show();

        //设置是否可以点击其他区域取消对话框
        setCancelable(canCancel);
        setCanceledOnTouchOutside(canCancel);

        if(showTextView == null) {
            showTextView = (TextView) findViewById(R.id.tv_loading_dialog);
        }

        //设置显示文字
        if(TextUtils.isEmpty(showText)) {
            showTextView.setVisibility(View.GONE);
        } else {
            showTextView.setText(showText);
            showTextView.setVisibility(View.VISIBLE);
        }
    }
}
