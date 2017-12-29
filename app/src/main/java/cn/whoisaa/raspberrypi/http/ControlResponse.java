package cn.whoisaa.raspberrypi.http;

/**
 * @Description
 * @Author AA
 * @DateTime 2017/12/18 下午3:30
 */
public class ControlResponse extends BaseResponse {

    /**
     * data : {"vtAngle":0,"hrAngle":0}
     * code : 200
     * desc : servo control, orientation:0, angle:0 successed
     */

    private Data data;

    public Data getData() {
        return data;
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
