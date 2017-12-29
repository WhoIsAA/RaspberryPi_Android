package cn.whoisaa.raspberrypi.utils;

/**
 * @Description 时间工具类
 * @Author AA
 * @DateTime 2017/12/29 下午4:20
 */
public class TimeUtils {


    /**
     * 将秒转换为mm:ss格式
     * @param second
     * @return
     */
    public static String getTimeFromInt(int second) {

        if (second <= 0) {
            return "0:00";
        }
        int minute = second / 60;
        second = second % 60;
        String second_s = second >= 10 ? String.valueOf(second) : "0"+ String.valueOf(second);
        return minute + ":" + second_s;
    }





}
