package cn.whoisaa.raspberrypi.http;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

import cn.whoisaa.raspberrypi.utils.LogUtils;

public class JavaBeanRequest<T> extends RestRequest<T> {

    /**
     * 要解析的JavaBean的class
     */
    private Class<T> clazz;

    /**
     * 是否需要对所有参数进行签名
     */
    private boolean needToSign;

    /**
     * 自动解析字符串的请求类
     * @param url 请求链接
     * @param requestMethod 请求方式
     * @param clazz 待解析的实体类
     * @param needToSign 是否需要对所有参数进行签名
     */
    public JavaBeanRequest(String url, RequestMethod requestMethod, Class<T> clazz, boolean needToSign) {
        super(url, requestMethod);
        this.clazz = clazz;
        this.needToSign = needToSign;
    }

    public JavaBeanRequest(String url, Class<T> clazz) {
        this(url, RequestMethod.POST, clazz);
    }

    public JavaBeanRequest(String url, RequestMethod requestMethod, Class<T> clazz) {
        this(url, requestMethod, clazz, false);
    }

//    /**
//     * 为参数签名和数据加密
//     * 参考：http://blog.csdn.net/xuyonghong1122/article/details/53350968
//     */
//    @Override
//    public void onPreExecute() {
//        if(!needToSign) {
//            return;
//        }
//
//        //第一步:获取所有请求参数
//        MultiValueMap<String, Object> multiValueMap = getParamKeyValues();
//        //第二步,定义List用于存储所有请求参数的key
//        List<String> keyList = new ArrayList<>();
//        //第三步:定义Map用于存储所有请求参数的value
//        Map<String, String> keyValueMap = new HashMap<>();
//        //第四步:拿到所有具体请求参数
//        for (Map.Entry<String, List<Object>> paramsEntry : multiValueMap.entrySet()) {
//            String key = paramsEntry.getKey();
//            List<Object> values = paramsEntry.getValue();
//            for (Object value : values) {
//                if (value instanceof String) {
//
//                    //第五步:将请求参数的key添加到list中用于排序
//                    keyList.add(key);
//
//                    //第六步:将请求参数的value添加到Map中
//                    keyValueMap.put(key, (String) value);
//                }
//            }
//        }
//        //第七步:对请求参数key进行排序
//        Collections.sort(keyList);
//        //第八步:依次取出排序之后的key-value,并拼接
//        StringBuilder paramsBuilder = new StringBuilder();
//        int listSize = keyList.size();
//        for (int i = 0; i < listSize; i++) {
//            String key = keyList.get(i);
//            String value = keyValueMap.get(key);
//            if (i == listSize - 1) {
//                paramsBuilder.append(key).append("=").append(value);
//            } else {
//                paramsBuilder.append(key).append("=").append(value).append("&");
//            }
//        }
//        paramsBuilder.append(UrlMgr.MIYAO);
//        //第九步:对拼接好的参数进行MD5加密
//        String sign =  Md5Utils.md5(paramsBuilder.toString());
//        //最后，加入签名参数
//        add("_sign", sign);
//    }

    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) {
        String response = StringRequest.parseResponseString(responseHeaders, responseBody);
        LogUtils.e("返回结果：\n" + response);
        // 这里如果数据格式错误，或者解析失败，会在失败的回调方法中返回 ParseError 异常。
        T object = null;
        try {
            object = new Gson().fromJson(response, clazz);
        } catch (Exception e) {
            LogUtils.e("*** Gson解析数据出错：" + response);
        }
        return object;
    }
}
