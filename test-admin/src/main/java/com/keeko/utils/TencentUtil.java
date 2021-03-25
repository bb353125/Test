package com.keeko.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 腾讯 相关
 *
 * @author chensq
 */
public class TencentUtil {

    /**
     * key
     */
    private static final String KEY = "MGLBZ-MVN6F-IJSJV-NC4NN-LG5DE-5HFFO";

    /**
     * 获取经纬度url
     */
    private static final String ADDRESS_URL = "https://apis.map.qq.com/ws/geocoder/v1/?address=";

    /**
     * 文档: https://lbs.qq.com/webservice_v1/guide-geocoder.html
     * 根据地址获取经纬度
     *
     * @param address 厦门市杏锦路
     * @param region  厦门
     * @return { Map<String, Object> map = getLocation(address, region); lng=118.058929, lat=24.581421}
     */
    public static Map<String, Object> getLocation(String address, String region) {
        // 参数解释：lng：经度，lat：维度。KEY：腾讯地图key，get_poi：返回状态。1返回，0不返回
        String urlString = ADDRESS_URL + address + "&region=" + region + "&key=" + KEY;
        String result = HttpUtil.sendGet(urlString);
        Map<String, Object> resultMap = new HashMap<>(16);
        //解析返回的结果
        JSONObject obj = (JSONObject) JSON.parse(result);
        String status = obj.getString("status");
        if ("0".equals(status)) {
            String res = obj.getString("result");
            JSONObject resObj = (JSONObject) JSON.parse(res);
            String location = resObj.getString("location");
            JSONObject locationObj = (JSONObject) JSON.parse(location);
            String lng = locationObj.getString("lng");
            String lat = locationObj.getString("lat");
            resultMap.put("lng", lng);
            resultMap.put("lat", lat);
        }
        return resultMap;
    }

    /**
     * 获取地址url
     */
    private static final String LOCATION_URL = "https://apis.map.qq.com/ws/geocoder/v1/?location=";

    /**
     * 根据经纬度获取地址
     *
     * @param lng 经度: 118.037585
     * @param lat 纬度: 24.61897
     */
    public static Map<String, Object> getAddress(String lng, String lat) {
        String urlString = LOCATION_URL + lat + "," + lng + "&key=" + KEY;
        String result = HttpUtil.sendGet(urlString);
        Map<String, Object> resultMap = new HashMap<>(16);
        //解析返回的结果
        JSONObject obj = (JSONObject) JSON.parse(result);
        String status = obj.getString("status");
        if ("0".equals(status)) {
            String res = obj.getString("result");
            JSONObject resObj = (JSONObject) JSON.parse(res);
            //详细地址
            String address = resObj.getString("address");
            //经过腾讯地图优化过的描述方式，更具人性化特点 详细地址
            JSONObject formattedAddresses = (JSONObject) JSON.parse(resObj.getString("formatted_addresses"));
            String recommend = formattedAddresses.getString("recommend");
            //这里有 省份 城市 区 当前只取城市
            String adInfo = resObj.getString("ad_info");
            JSONObject adInfoObj = (JSONObject) JSON.parse(adInfo);
            String city = adInfoObj.getString("city");
            resultMap.put("recommend", recommend);
            resultMap.put("address", address);
            resultMap.put("city", city);
        }
        return resultMap;
    }

    /**
     * 计算距离地址url
     */
    private static final String COUNT_DISTANCE_URL = "https://apis.map.qq.com/ws/distance/v1/?parameters";

    /**
     * 根据经纬度获取地址
     *
     * @param mode 计算方式：driving（驾车）、walking（步行）默认：driving
     * @param lng  起点经度
     * @param lat  起点纬度
     * @param toLng  终点纬度
     * @param toLat  终点纬度
     */
    public static Map<String, Object> countDistance(String mode, String lng, String lat, String toLng, String toLat) {
        String url;
        if (StringUtils.isNotEmpty(mode)) {
            url = COUNT_DISTANCE_URL + "&mode=" + mode + "&to=" + lat + "," + lng+ "&from=" + toLat + "," + toLng + "&key=" + KEY;
        } else {
            url = COUNT_DISTANCE_URL + "&from=" + lat + "," + lng+ "&to=" + toLat + "," + toLng + "&key=" + KEY + "&type=" + "wgs84";
        }
        String result = HttpUtil.sendGet(url);
        Map<String, Object> resultMap = new HashMap<>(16);
        //解析返回的结果
        JSONObject obj = (JSONObject) JSON.parse(result);
        String status = obj.getString("status");
        if ("0".equals(status)) {
            String res = obj.getString("result");
            JSONObject resObj = (JSONObject) JSON.parse(res);
            JSONArray elements = resObj.getJSONArray("elements");
            for (Object element : elements) {
                JSONObject signin = (JSONObject) element;
                String distance = signin.getString("distance");
                String duration = signin.getString("duration");
                resultMap.put("distance", distance);
                resultMap.put("duration", duration);
            }
        }
        return resultMap;
    }


    /**
     * 百度地图密钥
     */
    private static String AK = "w0V9BgVwoDhFvhwezVo210QPBhPom6FO";

    /**
     * 文档: http://lbsyun.baidu.com/index.php?title=wxjsapi
     * 调用百度地图API根据地址，获取坐标
     *
     * @param address 厦门市杏锦路
     * @return { Map<String, Object> map = getLocation(address, region); lng=118.058929, lat=24.581421}
     */
    public static Map<String, Object> getCoordinate(String address) {
        String url = "http://api.map.baidu.com/geocoding/v3/?address=" + address + "&output=json&ak=" + AK;
        String result = HttpUtil.sendGet(url);
        if (!"".equals(result)) {
            JSONObject obj = (JSONObject) JSON.parse(result);
            if ("0".equals(obj.getString("status"))) {
                Map<String, Object> map = new HashMap<>(16);
                // 经度
                String lng = obj.getJSONObject("result").getJSONObject("location").getString("lng");
                // 纬度
                String lat = obj.getJSONObject("result").getJSONObject("location").getString("lat");
                map.put("lng", lng);
                map.put("lat", lat);
                return map;
            }
        }
        return null;
    }


    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度获取距离(单位：米) 直线距离
     *
     * @param lng1  起点经度
     * @param lat1  起点纬度
     * @param lat2  终点纬度
     * @param lng1  终点纬度
     * @return 距离
     */
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }


}
