package cn.whoisaa.raspberrypi.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import cn.whoisaa.raspberrypi.R;
import cn.whoisaa.raspberrypi.common.glide.BlurTransformation;
import cn.whoisaa.raspberrypi.common.glide.CropCircleTransformation;
import cn.whoisaa.raspberrypi.common.glide.RoundedCornersTransformation;

/**
 * @Author: wangyb on 2016/8/27 14:38.
 * @描述 : Glide加载图片的封装，圆形、圆角，模糊等处理操作用到了jp.wasabeef:glide-transformations:2.0.1
 *         Glide默认使用httpurlconnection协议，可以配置为OkHttp
 */
public class GlideUtils {

    private static GlideUtils mInstance;
    private GlideUtils(){}
    public static GlideUtils getInstance() {
        if (mInstance == null) {
            synchronized (GlideUtils.class) {
                if (mInstance == null) {
                    mInstance = new GlideUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 常量
     */
    static class Contants {
        public static final int BLUR_VALUE = 80; //模糊
        public static final int CORNER_RADIUS = 20; //圆角
        public static final float THUMB_SIZE = 0.5f; //0-1之间  10%原图的大小
    }

    /**
     * 常规加载图片
     * @param context
     * @param imageView  图片容器
     * @param imgUrl  图片地址
     * @param isFade  是否需要动画
     */
    public void loadImage(Context context, ImageView imageView,
                          String imgUrl, boolean isFade) {
        if (isFade) {
            Glide.with(context)
                    .load(imgUrl)
                    .error(R.mipmap.default_placeholder)
                    .placeholder(R.mipmap.default_placeholder)
                    .crossFade()
                    .priority(Priority.NORMAL) //下载的优先级
                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                    //source:缓存源资源   result：缓存转换后的资源
                    .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                    .centerCrop()
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(imgUrl)
                    .error(R.mipmap.default_placeholder)
                    .placeholder(R.mipmap.default_placeholder)
                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                    //source:缓存源资源   result：缓存转换后的资源
                    .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                    .centerCrop()
                    .into(imageView);
        }
    }

    /**
     * 常规加载图片
     * @param context
     * @param imageView  图片容器
     * @param imgUrl  图片地址
     * @param errorResId  图片加载错误时
     */
    public void loadImage(Context context, ImageView imageView, String imgUrl, int errorResId) {
        Glide.with(context)
                .load(imgUrl)
                .error(errorResId)
                .placeholder(R.mipmap.default_placeholder)
                .crossFade()
                .priority(Priority.NORMAL) //下载的优先级
                //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                //source:缓存源资源   result：缓存转换后的资源
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .centerCrop()
                .into(imageView);
    }

    /**
     * 常规加载图片
     * @param context ...
     * @param imgUrl  图片地址
     * @param errorResId  图片加载错误时
     * @param target  target带监听器
     */
    public void loadImage(Context context, String imgUrl, int errorResId, GlideDrawableImageViewTarget target) {
        Glide.with(context)
                .load(imgUrl)
                .error(errorResId)
                .placeholder(R.mipmap.default_placeholder)
                .crossFade()
                .priority(Priority.NORMAL) //下载的优先级
                //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                //source:缓存源资源   result：缓存转换后的资源
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .centerCrop()
                .into(target);
    }

    /**
     * 常规加载图片（不带默认图片）
     * @param context
     * @param imageView  图片容器
     * @param imgUrl  图片地址
     * @param isFade  是否需要动画
     */
    public void loadImageNoPlaceHolder(Context context, ImageView imageView, String imgUrl, boolean isFade) {
        if (isFade) {
            Glide.with(context)
                    .load(imgUrl)
                    .error(R.mipmap.default_placeholder)
                    .crossFade()
                    .priority(Priority.NORMAL) //下载的优先级
                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                    //source:缓存源资源   result：缓存转换后的资源
                    .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                    .centerCrop()
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(imgUrl)
                    .error(R.mipmap.default_placeholder)
                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                    //source:缓存源资源   result：缓存转换后的资源
                    .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                    .centerCrop()
                    .into(imageView);
        }
    }

    /**
     * 常规加载图片（不带默认图片）
     * @param context
     * @param imageView  图片容器
     * @param imgUrl  图片地址
     * @param errorResId  图片加载错误时
     */
    public void loadImageNoPlaceHolder(Context context, ImageView imageView, String imgUrl, int errorResId) {
        Glide.with(context)
                .load(imgUrl)
                .error(errorResId)
                .crossFade()
                .priority(Priority.NORMAL) //下载的优先级
                //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                //source:缓存源资源   result：缓存转换后的资源
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .centerCrop()
                .into(imageView);
    }

    /**
     * 常规加载图片
     * @param context
     * @param imageView  图片容器
     * @param imgUrl  图片地址
     * @param isFade  是否需要动画
     */
    public void loadImageFitCenter(Context context, ImageView imageView,
                                   String imgUrl, boolean isFade) {
        if (isFade) {
            Glide.with(context)
                    .load(imgUrl)
                    .fitCenter()
                    .dontAnimate()
                    .error(R.mipmap.default_placeholder)
                    .placeholder(R.mipmap.default_placeholder)
                    .crossFade()
                    .priority(Priority.NORMAL) //下载的优先级
                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                    //source:缓存源资源   result：缓存转换后的资源
                    .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(imgUrl)
                    .fitCenter()
                    .dontAnimate()
                    .error(R.mipmap.default_placeholder)
                    .placeholder(R.mipmap.default_placeholder)
                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                    //source:缓存源资源   result：缓存转换后的资源
                    .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                    .into(imageView);
        }
    }

    /**
     * 常规加载图片
     * @param context
     * @param imageView  图片容器
     * @param imgUrl  图片地址
     * @param errorResId  图片加载错误时
     */
    public void loadImageFitCenter(Context context, ImageView imageView, String imgUrl, int errorResId) {
        Glide.with(context)
                .load(imgUrl)
                .error(errorResId)
                .placeholder(R.mipmap.default_placeholder)
                .crossFade()
                .priority(Priority.NORMAL) //下载的优先级
                //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                //source:缓存源资源   result：缓存转换后的资源
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .into(imageView);
    }

    /**
     * 常规加载背景图片
     * @param context
     * @param view  图片容器
     * @param imgUrl  图片地址
     * @param width  图片宽度
     * @param height  图片高度
     * @param errorResId  图片加载错误时
     */
    public void loadBackground(Context context, final View view, String imgUrl, int width, int height, int errorResId) {
        Glide.with(context)
                .load(imgUrl)
                .error(errorResId)
                .priority(Priority.NORMAL) //下载的优先级
                .placeholder(R.mipmap.default_placeholder)
                //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                //source:缓存源资源   result：缓存转换后的资源
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        view.setBackground(resource);
                        return false;
                    }
                })
                .centerCrop()
                .into(width, height);
    }

    /**
     * 加载缩略图
     * @param context
     * @param imageView  图片容器
     * @param imgUrl  图片地址
     */
    public void loadThumbnailImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .error(R.mipmap.default_placeholder)
                .placeholder(R.mipmap.default_placeholder)
                .crossFade()
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .thumbnail(Contants.THUMB_SIZE)
                .into(imageView);
    }

    /**
     * 加载图片并设置为指定大小
     * @param context
     * @param imageView
     * @param imgUrl
     * @param withSize
     * @param heightSize
     */
    public void loadOverrideImage(Context context, ImageView imageView,
                                  String imgUrl, int withSize, int heightSize) {
        Glide.with(context)
                .load(imgUrl)
                .error(R.mipmap.default_placeholder)
                .placeholder(R.mipmap.default_placeholder)
                .crossFade()
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .override(withSize, heightSize)
                .into(imageView);
    }

    /**
     * 加载图片并对其进行模糊处理
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadBlurImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .error(R.mipmap.default_placeholder)
                .placeholder(R.mipmap.default_placeholder)
                .crossFade()
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .bitmapTransform(new BlurTransformation(context, Contants.BLUR_VALUE))
                .into(imageView);
    }

    /**
     * 加载圆图
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadCircleImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .error(R.mipmap.default_placeholder)
                .crossFade()
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    /**
     * 加载圆图，指定加载失败后的图片
     * @param context 上下文
     * @param imageView 图片视图
     * @param imgUrl 图片地址
     * @param errorResId 加载失败后显示的图片
     */
    public void loadCircleImage(Context context, ImageView imageView, String imgUrl, int errorResId) {
        Glide.with(context)
                .load(imgUrl)
                .error(errorResId)
                .crossFade()
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    /**
     * 加载圆图，指定加载前和加载失败后的图片
     * @param context 上下文
     * @param imageView 图片视图
     * @param imgUrl 图片地址
     * @param placeholderResId 加载前显示的图片
     * @param errorResId 加载失败后显示的图片
     */
    public void loadCircleImage(Context context, ImageView imageView, String imgUrl, int placeholderResId, int errorResId) {
        Glide.with(context)
                .load(imgUrl)
                .error(errorResId)
                .crossFade()
                .placeholder(placeholderResId)
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    /**
     * 加载模糊的圆图
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadBlurCircleImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .error(R.mipmap.default_placeholder)
                .crossFade()
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .bitmapTransform(
                        new BlurTransformation(context, Contants.BLUR_VALUE),
                        new CropCircleTransformation(context))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadCornerImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .bitmapTransform(new RoundedCornersTransformation(context, Contants.CORNER_RADIUS, 0))
                .error(R.mipmap.default_placeholder)
                .placeholder(R.mipmap.default_placeholder)
                .crossFade()
                .centerCrop()
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .into(imageView);
    }

    /**
     * 加载模糊的圆角图片
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadBlurCornerImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .error(R.mipmap.default_placeholder)
                .placeholder(R.mipmap.default_placeholder)
                .crossFade()
                .centerCrop()
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .bitmapTransform(
                        new BlurTransformation(context, Contants.BLUR_VALUE),
                        new RoundedCornersTransformation(
                                context, Contants.CORNER_RADIUS, 0))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadCornerWithMargin(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .error(R.mipmap.default_placeholder)
                .placeholder(R.mipmap.default_placeholder)
                .crossFade()
                .centerCrop()
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .bitmapTransform(
                        new RoundedCornersTransformation(
                                context, Contants.CORNER_RADIUS, Contants.CORNER_RADIUS))
                .into(imageView);
    }

    /**
     * 加载模糊的圆角图片
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadBlurCornerImageWithMargin(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .error(R.mipmap.default_placeholder)
                .placeholder(R.mipmap.default_placeholder)
                .crossFade()
                .centerCrop()
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .bitmapTransform(
                        new BlurTransformation(context, Contants.BLUR_VALUE),
                        new RoundedCornersTransformation(
                                context, Contants.CORNER_RADIUS, Contants.CORNER_RADIUS))
                .into(imageView);
    }

    /**
     * 同步加载图片
     * @param context
     * @param imgUrl
     * @param target
     */
    public void loadBitmapSync(Context context, String imgUrl, SimpleTarget<Bitmap> target) {
        Glide.with(context)
                .load(imgUrl)
                .asBitmap()
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .into(target);
    }

    /**
     * 加载gif
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadGifImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .asGif()
                .crossFade()
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .error(R.mipmap.default_placeholder)
                .into(imageView);
    }

    /**
     * 加载gif的缩略图
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadGifThumbnailImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .asGif()
                .crossFade()
                .priority(Priority.NORMAL) //下载的优先级
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略
                .error(R.mipmap.default_placeholder)
                .thumbnail(Contants.THUMB_SIZE)
                .into(imageView);
    }

    /**
     * 恢复请求，一般在停止滚动的时候
     * @param context
     */
    public void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 暂停请求 正在滚动的时候
     * @param context
     */
    public void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 清除磁盘缓存
     * @param context
     */
    public void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
            }
        }).start();
    }

    /**
     * 清除内存缓存
     * @param context
     */
    public void clearMemory(Context context) {
        Glide.get(context).clearMemory();//清理内存缓存  可以在UI主线程中进行
    }

}
