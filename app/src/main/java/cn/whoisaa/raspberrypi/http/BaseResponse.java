package cn.whoisaa.raspberrypi.http;

/**
 * @Description
 * @Author AA
 * @DateTime 2017/12/26 下午4:07
 */
public class BaseResponse {

    protected int code;
    protected String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
