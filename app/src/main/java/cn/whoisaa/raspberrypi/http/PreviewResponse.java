package cn.whoisaa.raspberrypi.http;

import java.util.List;

/**
 * @Description
 * @Author AA
 * @DateTime 2017/12/26 下午3:06
 */
public class PreviewResponse extends BaseResponse {

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        /**
         * id : 1
         * filename : u7535u8defu56fe.png
         * hash : FvP1VJoMUALd-Ns4BJsVrvhUfZks
         * fkey : rspi/u7535u8defu56fe.png
         * url : http://oz12oj1xr.bkt.clouddn.com/rspi/u7535u8defu56fe.png
         * mimeType : image/png
         * type : 1
         * thumbnail :
         * duration : 0
         * fsize : 20705
         * putTime : 15144415639115038
         */

        private int id;
        private String filename;
        private String hash;
        private String fkey;
        private String url;
        private String mimeType;
        private int type;
        private String thumbnail;
        private int duration;
        private int fsize;
        private String putTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getFkey() {
            return fkey;
        }

        public void setFkey(String fkey) {
            this.fkey = fkey;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getFsize() {
            return fsize;
        }

        public void setFsize(int fsize) {
            this.fsize = fsize;
        }

        public String getPutTime() {
            return putTime;
        }

        public void setPutTime(String putTime) {
            this.putTime = putTime;
        }

        public boolean isVideo() {
            return type == 2 && duration > 0;
        }
    }
}
