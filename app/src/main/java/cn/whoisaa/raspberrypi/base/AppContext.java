package cn.whoisaa.raspberrypi.base;

import android.app.Application;

import com.yanzhenjie.nohttp.NoHttp;

/**
 * @Description
 * @Author AA
 * @DateTime 2017/11/27 下午2:26
 */
public class AppContext extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
    }
}
