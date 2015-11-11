package com.shenma.yueba.config;

/**
 * Created by Administrator on 2015/11/3.
 * 用户信息配置类 存储 与用户有关的 配置信息名称  属性信息名称
 *
 */
public class PerferneceConfig {
    /***************属性 **********************/
    public static final String USER_TOKEN = "user_token";///登录用户令牌
    public static final String USER_FIRST = "user_first";///是否第一次使用应用



    /**********************经纬度****************************/
    public static final String LONGITUDE = "Longitude";//经度
    public static final String LATITUDE = "Latitude";//维度



    /********************阿里云属性*********************/
    public static final String KEY = "key";// 阿里云需要的key
    public static final String SING = "sign";//阿里云需要的sign



    /*******************城市******************/
    public static final String CURRENT_CITY_ID = "current_city_id";//当前城市ID
}
