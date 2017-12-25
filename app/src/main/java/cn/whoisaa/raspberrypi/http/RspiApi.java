package cn.whoisaa.raspberrypi.http;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

public class RspiApi {

    public static final int REQ_CONNECT_TEST = 101;
    public static final int REQ_CAR_CONTROL = 201;
    public static final int REQ_SERVO_CONTROL_HR = 301;
    public static final int REQ_SERVO_CONTROL_VT = 302;
    public static final int REQ_TAKE_PICTURE = 401;
    public static final int REQ_CONTINUOUS_SHOT = 402;
    public static final int REQ_START_RECORD = 403;
    public static final int REQ_STOP_RECORD = 404;

    /**
     * 单例对象
     */
    private volatile static RspiApi mInstance;

    /**
     * 请求队列
     */
    private RequestQueue mRequestQueue;


    private RspiApi() {
        if (mRequestQueue == null) {
            mRequestQueue = NoHttp.newRequestQueue();
        }
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static RspiApi getInstance() {
        //第一次判空
        if (mInstance == null) {
            synchronized (RspiApi.class) {//锁
                //第二次判空，多线程同时走到这的时候，需要这样优化处理
                if (mInstance == null) {
                    mInstance = new RspiApi();
                }
            }
        }
        return mInstance;
    }

    /***********************************************************************************************
     *************************************** 【API公开方法】 ***************************************
     ***********************************************************************************************/

    /**
     * 取消所有请求
     */
    public void cancelAppRequest() {
        mRequestQueue.cancelAll();
    }

    /**
     * 测试服务器连接状态
     * @param what
     * @param onResponseListener
     */
    public void connectTest(int what, OnResponseListener onResponseListener) {
        Request<ControlResponse> request = new JavaBeanRequest(getApiUrl(UrlMgr.RSPI_CONNECT_TEST), ControlResponse.class);
        mRequestQueue.add(what, request, onResponseListener);
    }

    /**
     * 小车控制
     * @param what
     * @param action
     * @param onResponseListener
     */
    public void carControl(int what, String action, OnResponseListener onResponseListener) {
        Request<ControlResponse> request = new JavaBeanRequest(getApiUrl(UrlMgr.RSPI_CAR_CONTROL), ControlResponse.class);
        request.add("action", action);
        mRequestQueue.add(what, request, onResponseListener);
    }

    /**
     * 舵机控制
     * @param what
     * @param orientation
     * @param angle
     * @param onResponseListener
     */
    public void servoControl(int what, int orientation, int angle, OnResponseListener onResponseListener) {
        Request<ControlResponse> request = new JavaBeanRequest(getApiUrl(UrlMgr.RSPI_SERVO_CONTROL), ControlResponse.class);
        request.add("orientation", orientation);
        request.add("angle", angle);
        mRequestQueue.add(what, request, onResponseListener);
    }

    /**
     * 拍一张照
     * @param what
     * @param onResponseListener
     */
    public void takePicture(int what, OnResponseListener onResponseListener) {
        Request<ControlResponse> request = new JavaBeanRequest(getApiUrl(UrlMgr.RSPI_TAKE_PICTURE), ControlResponse.class);
        mRequestQueue.add(what, request, onResponseListener);
    }

    /**
     * 连拍，默认五张
     * @param what
     * @param count
     * @param delay
     * @param onResponseListener
     */
    public void continuousShot(int what, int count, long delay, OnResponseListener onResponseListener) {
        Request<ControlResponse> request = new JavaBeanRequest(getApiUrl(UrlMgr.RSPI_CONTINUOUS_SHOT), ControlResponse.class);
        request.add("count", count);
        request.add("delay", delay);
        mRequestQueue.add(what, request, onResponseListener);
    }

    /**
     * 开始录像
     * @param what
     * @param seconds
     * @param onResponseListener
     */
    public void startRecord(int what, long seconds, OnResponseListener onResponseListener) {
        Request<ControlResponse> request = new JavaBeanRequest(getApiUrl(UrlMgr.RSPI_START_RECORD), ControlResponse.class);
        request.add("seconds", seconds);
        mRequestQueue.add(what, request, onResponseListener);
    }

    /**
     * 停止录像
     * @param what
     * @param onResponseListener
     */
    public void stopRecord(int what, OnResponseListener onResponseListener) {
        Request<ControlResponse> request = new JavaBeanRequest(getApiUrl(UrlMgr.RSPI_STOP_RECORD), ControlResponse.class);
        mRequestQueue.add(what, request, onResponseListener);
    }


    /***********************************************************************************************
     *************************************** 【API私有方法】 ***************************************
     ***********************************************************************************************/

    /**
     * 获得API请求完整地址
     *
     * @param api
     * @return
     */
    private String getApiUrl(String api) {
        return UrlMgr.getApiUrl(api);
    }
}