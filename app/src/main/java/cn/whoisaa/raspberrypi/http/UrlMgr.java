package cn.whoisaa.raspberrypi.http;


public class UrlMgr {

    /** 服务器地址 */
    public static final String API_URL = "http://192.168.1.124:8000";
    /** 图片地址 */
    public static final String IMAGE_URL = "";



    /******************************************************************************************************************************************
     *************************************************************** API请求地址 ***************************************************************
     ******************************************************************************************************************************************/
    public static final String RSPI_CONNECT_TEST = "/carpi/connect_test";
    public static final String RSPI_CAR_CONTROL = "/carpi/car_control";
    public static final String RSPI_SERVO_CONTROL = "/carpi/servo_control";
    public static final String RSPI_TAKE_PICTURE = "/carpi/take_picture";
    public static final String RSPI_CONTINUOUS_SHOT = "/carpi/continuous_shot";
    public static final String RSPI_START_RECORD = "/carpi/start_record";
    public static final String RSPI_STOP_RECORD = "/carpi/stop_record";




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
