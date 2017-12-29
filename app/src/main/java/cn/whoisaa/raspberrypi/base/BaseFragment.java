package cn.whoisaa.raspberrypi.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.whoisaa.raspberrypi.R;

/**
 * @Description
 * @Author AA
 * @DateTime 2017/12/29 上午9:58
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 当前Fragment父视图
     */
    private View mView;

    /** 保存Fragmetn重建后的数据 */
    private Bundle mSavedInstanceState;

    /**
     * 权限请求码
     */
    private int mRequestCode;

    /**
     * 控件是否初始化完成
     */
    private boolean isViewCreated;

    /**
     * 数据是否已加载完毕
     */
    private boolean isLoadDataCompleted;

    /**
     * 获取当前Fragment视图ID
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化数据
     */
    protected abstract void initData();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
//        //绑定注解类
//        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getUserVisibleHint()) {
            mSavedInstanceState = savedInstanceState;
            initData();
            isLoadDataCompleted = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isViewCreated && !isLoadDataCompleted) {
            initData();
            isLoadDataCompleted = true;
        }
    }

    protected <T extends View> T findViewById(@IdRes int id) {
        return mView.findViewById(id);
    }

    /**
     * 显示加载中对话框
     * @param showText 对话框显示内容
     */
    protected void showLoading(String showText) {
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoading(showText);
        }
    }

    /**
     * 显示加载中对话框
     * @param showText 对话框显示内容
     * @param canCancel 是否可以点击其他区域取消对话框
     */
    protected void showLoading(String showText, boolean canCancel) {
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoading(showText, canCancel);
        }
    }

    /**
     * 隐藏加载中视图
     */
    protected void hideLoading() {
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoading();
        }
    }

    /**
     * 加载中动画是否显示中
     * @return
     */
    protected boolean isLoadingDialogShowing() {
        if(getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).isLoadingDialogShowing();
        }
        return false;
    }

    /**
     * 弹出Toast提示
     * @param text 提示内容
     */
    protected void toast(String text) {
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).toast(text);
        }
    }

    /**
     * 弹出Toast提示
     * @param resId 提示内容ID
     */
    protected void toast(int resId) {
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).toast(resId);
        }
    }

    /**
     * 记录错误日志
     * @param log
     */
    protected void logError(String log) {
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).logError(log);
        }
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
        showTipsDialogWithTitle(null, content, confirmText, confirmListener, null, cancelListener);
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
        showTipsDialogWithTitle(title, content, getString(R.string.confirm), confirmListener, null, cancelListener);
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
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).showTipsDialogWithTitle(title, content, confirmText, confirmListener, cancelText, cancelListener);
        }
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
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).showItemsDialogWithTitle(title, itemsId, listener);
        }
    }

    /**
     * 显示列表选择对话框
     * @param title 标题
     * @param itemsArray 列表数组
     * @param listener 列表项点击事件
     */
    protected void showItemsDialogWithTitle(String title, String[] itemsArray, DialogInterface.OnClickListener listener) {
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).showItemsDialogWithTitle(title, itemsArray, listener);
        }
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

    public void pushActivity(Intent intent) {
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).pushActivity(intent);
        }
    }

    public void pushActivity(Class<?> mClass) {
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).pushActivity(mClass);
        }
    }

    public void pushActivityForResult(Intent intent, int requestCode) {
        if(getActivity() instanceof BaseActivity) {
            this.startActivityForResult(intent, requestCode);
        }
    }

    public void pushActivityForResult(Class<?> mClass, int requestCode) {
        if(getActivity() instanceof BaseActivity) {
            this.startActivityForResult(new Intent(getActivity(), mClass), requestCode);
        }
    }
}
