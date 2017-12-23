package cn.whoisaa.raspberrypi.http;

/**
 * @Description
 * @Author AA
 * @DateTime 2017/12/18 下午3:30
 */
public class ControlResponse {

    /**
     * data : {"vtAngle":0,"hrAngle":0}
     * code : 200
     * desc : servo control, orientation:0, angle:0 successed
     */

    private Data data;
    private int code;
    private String desc;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class Data {
        /**
         * vtAngle : 0
         * hrAngle : 0
         */

        private int vtAngle;
        private int hrAngle;

        public int getVtAngle() {
            return vtAngle;
        }

        public void setVtAngle(int vtAngle) {
            this.vtAngle = vtAngle;
        }

        public int getHrAngle() {
            return hrAngle;
        }

        public void setHrAngle(int hrAngle) {
            this.hrAngle = hrAngle;
        }
    }
}
