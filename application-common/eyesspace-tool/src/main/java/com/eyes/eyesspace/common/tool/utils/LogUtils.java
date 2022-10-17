package com.eyes.eyesspace.common.tool.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LogUtils {
    public static void info(Map<String, Object> map) {
        log.info(map2String(map));
    }

    public static void warn(Map<String, Object> map) {
        log.warn(map2String(map));
    }

    public static void error(Map<String, Object> map) {
        log.error(map2String(map));
    }

    public static void info(HttpServletRequest request, Map<String, Object> map) {
        log.info(baseLog(request) + map2String(map));
    }

    public static void warn(HttpServletRequest request, Map<String, Object> map) {
        log.warn(baseLog(request) + map2String(map));
    }

    public static void error(HttpServletRequest request, Map<String, Object> map) {
        log.error(baseLog(request) + map2String(map));
    }

    public static void info(HttpServletRequest request, String msg) {
        log.info(baseLog(request) + " logMsg: " + msg);
    }

    public static void warn(HttpServletRequest request, String msg) {
        log.warn(baseLog(request) + " logMsg: " + msg);
    }

    public static void error(HttpServletRequest request, String msg) {
        log.error(baseLog(request) + " logMsg: " + msg);
    }

    public static Map<String, Object> logMap(Object... info) {
        if(info.length % 2 != 0) return null;
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < info.length - 1; i+=2) {
            map.put(info[i].toString(), info[i + 1]);
        }
        return map;
    }

    private static String baseLog(HttpServletRequest request) {
        return  "url: " + request.getRequestURL() +
                " Ip: " + IpUtils.getIpAddr(request) +
                " OS: " + OSUtils.osName(request) +
                " hostName: " + IpUtils.getHostName() +
                " browserName: " + BrowserUtils.browserName(request) +
                "\n";
    }

    private static String map2String(Map<String, Object> map) {
        StringBuilder content = new StringBuilder();
        map.forEach((key, value) -> {
            content.append(" ").append(key).append(":").append(value);
        });
        return content.toString();
    }
}
