package cn.whoisaa.raspberrypi.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;

import cn.whoisaa.raspberrypi.R;
import cn.whoisaa.raspberrypi.utils.LogUtils;
import cn.whoisaa.raspberrypi.widget.LoadingDialog;

/**
 * @Description Activity基类
 * @Author AA
 * @DateTime 2017/11/27 下午2:37
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 当前Activity上下文
     */
    private Context mContext;

    /**
     * 加载中动画
     */
    private LoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 获得上下文
     * @return
     */
    protected Context getContext() {
        return mContext;
    }

    /**
     * 弹出Toast提示
     * @param text 提示内容
     */
    protected void toast(String text) {
        if(this.isFinishing()) {
            return;
        }

        if(!TextUtils.isEmpty(text)) {
            Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 弹出Toast提示
     * @param resId 提示内容ID
     */
    protected void toast(int resId) {
        String text = getString(resId);
        toast(text);
    }

    /**
     * 记录错误日志
     * @param log
     */
    protected void logError(String log) {
        LogUtils.e(log);
    }

    /**
     * 显示加载中对话框
     * @param showText 对话框显示内容
     */
    protected void showLoading(String showText) {
        showLoading(showText, true);
    }

    /**
     * 显示加载中对话框
     * @param showText 对话框显示内容
     * @param canCancel 是否可以点击其他区域取消对话框
     */
    protected void showLoading(String showText, boolean canCancel) {
        if(this.isFinishing()) {
            return;
        }

        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.setShowText(showText);
        mLoadingDialog.setCanCancel(canCancel);
        if(!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    /**
     * 隐藏加载中视图
     */
    protected void hideLoading() {
        if(this.isFinishing()) {
            return;
        }

        if(mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    /**
     * 加载中动画是否显示中
     * @return
     */
    protected boolean isLoadingDialogShowing() {
        return mLoadingDialog != null && mLoadingDialog.isShowing();
    }

    /**
     * 显示提示对话框
     * @param content 内容
     * @param confirmListener 确定按钮点击事件
     */
    protected void showTipsDialog(String content, DialogInterface.OnClickListener confirmListener) {
        showTipsDialogWithTitle(null, content, getString(R.string.confirm), confirmListener, null, null);
    }

    /**
     * 显示提示对话框
     * @param content 内容
     * @param confirmText 确定按钮文字
     * @param confirmListener 确定按钮点击事件
     */
    protected void showTipsDialog(String content, String confirmText, DialogInterface.OnClickListener confirmListener) {
        showTipsDialogWithTitle(null, content, confirmText, confirmListener, null, null);
    }

    /**
     * 显示提示对话框
     * @param content 内容
     * @param confirmText 确定按钮文字
     * @param confirmListener 确定按钮点击事件
     * @param cancelListener 取消按钮点击事件
     */
    protected void showTipsDialog(String content, String confirmText, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        showTipsDialogWithTitle(null, content, confirmText, confirmListener, getString(R.string.cancel), cancelListener);
    }

    /**
     * 显示提示对话框
     * @param content 内容
     * @param confirmText 确定按钮文字
     * @param confirmListener 确定按钮点击事件
     * @param cancelText 取消按钮文字
     * @param cancelListener 取消按钮点击事件
     */
    protected void showTipsDialog(String content, String confirmText, DialogInterface.OnClickListener confirmListener, String cancelText, DialogInterface.OnClickListener cancelListener) {
        showTipsDialogWithTitle("", content, confirmText, confirmListener, cancelText, cancelListener);
    }

    /**
     * 显示提示对话框（带标题）
     * @param title 标题
     * @param content 内容
     * @param confirmListener 确定按钮点击事件
     */
    protected void showTipsDialogWithTitle(String title, String content, DialogInterface.OnClickListener confirmListener) {
        showTipsDialogWithTitle(title, content, getString(R.string.confirm), confirmListener, null, null);
    }

    /**
     * 显示提示对话框（带标题）
     * @param title 标题
     * @param content 内容
     * @param confirmListener 确定按钮点击事件
     * @param cancelListener 取消按钮点击事件
     */
    protected void showTipsDialogWithTitle(String title, String content, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        showTipsDialogWithTitle(title, content, getString(R.string.confirm), confirmListener, getString(R.string.cancel), cancelListener);
    }

    /**
     * 显示提示对话框（带标题）
     * @param title 标题
     * @param content 内容
     * @param confirmText 确定按钮文字
     * @param confirmListener 确定按钮点击事件
     */
    protected void showTipsDialogWithTitle(String title, String content, String confirmText, DialogInterface.OnClickListener confirmListener) {
        showTipsDialogWithTitle(title, content, confirmText, confirmListener, null, null);
    }

    /**
     * 显示提示对话框（带标题）
     * @param title 标题
     * @param content 内容
     * @param confirmText 确定按钮文字
     * @param confirmListener 确定按钮点击事件
     * @param cancelText 取消按钮文字
     * @param cancelListener 取消按钮点击事件
     */
    protected void showTipsDialogWithTitle(String title, String content, String confirmText, DialogInterface.OnClickListener confirmListener, String cancelText, DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(isNotEmptyString(title)) {
            builder.setTitle(title);
        }
        builder.setMessage(content);
        builder.setPositiveButton(confirmText, confirmListener);
        if(isNotEmptyString(cancelText)) {
            builder.setNegativeButton(cancelText, cancelListener);
        }
        builder.create().show();
    }

    /**
     * 显示列表选择对话框
     * @param itemsId 列表数组资源ID
     * @param listener 列表项点击事件
     */
    protected void showItemsDialog(int itemsId, DialogInterface.OnClickListener listener) {
        showItemsDialogWithTitle(null, itemsId, listener);
    }

    /**
     * 显示列表选择对话框
     * @param itemsArray 列表数组
     * @param listener 列表项点击事件
     */
    protected void showItemsDialog(String[] itemsArray, DialogInterface.OnClickListener listener) {
        showItemsDialogWithTitle(null, itemsArray, listener);
    }

    /**
     * 显示列表选择对话框
     * @param title 标题
     * @param itemsId 列表数组资源ID
     * @param listener 列表项点击事件
     */
    protected void showItemsDialogWithTitle(String title, int itemsId, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(isNotEmptyString(title)) {
            builder.setTitle(title);
        }
        builder.setItems(itemsId, listener).create().show();
    }

    /**
     * 显示列表选择对话框
     * @param title 标题
     * @param itemsArray 列表数组
     * @param listener 列表项点击事件
     */
    protected void showItemsDialogWithTitle(String title, String[] itemsArray, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(isNotEmptyString(title)) {
            builder.setTitle(title);
        }
        builder.setItems(itemsArray, listener).create().show();
    }

    protected void pushActivity(Intent intent) {
        startActivity(intent);
    }

    protected void pushActivity(Class<?> mClass) {
        startActivity(new Intent(mContext, mClass));
    }

    protected void pushActivity(Intent intent, boolean finishSelf) {
        pushActivity(intent);

        if (finishSelf)
            finish();
    }

    protected void pushActivity(Class<?> mClass, boolean finishSelf) {
        pushActivity(mClass);

        if (finishSelf)
            finish();
    }

    protected void pushActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    protected void pushActivityForResult(Class<?> mClass, int requestCode) {
        startActivityForResult(new Intent(mContext, mClass), requestCode);
    }

    /**
     * 判断字符串是否不为空
     * @param text
     * @return
     */
    protected boolean isNotEmptyString(String text) {
        return !TextUtils.isEmpty(text) && !text.equals("null");
    }

    /**
     * 判断字符串是否为空
     * @param text
     * @return
     */
    protected boolean isEmptyString(String text) {
        return TextUtils.isEmpty(text) || text.equals("null");
    }

    /**
     * 判断列表是否为空
     * @param list
     * @return
     */
    protected boolean isEmptyList(List<?> list) {
        return list == null || list.size() <= 0;
    }
}
