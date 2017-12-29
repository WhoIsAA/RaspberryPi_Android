package cn.whoisaa.raspberrypi.http;


public class UrlMgr {

    /** 服务器地址 */
    public static final String API_URL = "http://192.168.1.5:8000";
    /** 图片地址 */
    public static final String IMAGE_URL = "";



    /******************************************************************************************************************************************
     *************************************************************** API请求地址 ***************************************************************
     ******************************************************************************************************************************************/
    public static final String RSPI_CONNECT_TEST = "/carpi/connect_test";
    public static final String RSPI_CAR_CONTROL = "/carpi/car";
    public static final String RSPI_SERVO_CONTROL = "/carpi/servo";
    public static final String RSPI_CAMERA_CONTROL = "/carpi/camera";




    /**
     * 获得API请求完整地址
     * @param apiUrl
     * @return
     */
    public static String getApiUrl(String apiUrl) {
        if(apiUrl.contains(API_URL)) {
            return apiUrl;
        }
        return API_URL + apiUrl;
    }

    /**
     * 获得图片请求完整地址
     * @param imgUrl
     * @return
     */
    public static String getImgUrl(String imgUrl) {
        if(imgUrl.contains(IMAGE_URL)) {
            return imgUrl;
        }
        return IMAGE_URL + imgUrl;
    }

}
