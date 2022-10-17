package com.eyes.eyesspace.common.tool.context;

import java.util.HashMap;
import java.util.Map;

public class ConfigContext {
    private static final Map<String, String> stringMap = new HashMap<>();

    private static final Map<String, Integer> intMap = new HashMap<>();

    private static final Map<String, Double> doubleMap = new HashMap<>();

    private static final Map<String, Long> longMap = new HashMap<>();

    static {
        siteContext();
        encryptionContext();
        thirdPartyServiceContext();
        programContext();
    }

    public static String getString(String key) {
        return stringMap.get(key);
    }

    public static Integer getInt(String key) {
        return intMap.get(key);
    }

    public static Double getDouble(String key) {
        return doubleMap.get(key);
    }

    public static Long getLong(String key) {
        return longMap.get(key);
    }

    private static void siteContext() {
        // 应用名称（中文）
        stringMap.put("APP_NAME_CN", "瞳孔的个人空间");
        // 应用名称（英语）
        stringMap.put("APP_NAME_EN", "eyesSpace");
        // 作者名称（中文）
        stringMap.put("APP_AUTHOR_CN", "瞳孔");
        // 作者名称（英语）
        stringMap.put("APP_AUTHOR_EN", "eyes");

        // 图形验证码位数
        intMap.put("IMG_CODE_DIGIT", 4);
        // 邮箱验证码过期时间
        longMap.put("EMAIL_CODE_VALIDITY", 5*60L);
        // 邮箱验证码位数
        intMap.put("EMAIL_CODE_DIGIT", 6);

        // 每天最多能看视频数
        intMap.put("MAX_VIDEO_PLAY", 5);
        // 记忆用户视频播放历史
        intMap.put("VIDEO_MEMORY_NUM", 20);

        // 每天最多能播放音乐数
        intMap.put("MAX_MUSIC_PLAY", 5);
        // 记忆用户音乐播放历史
        intMap.put("MUSIC_MEMORY_NUM", 20);

        // 评论删除语
        stringMap.put("COMMENT_DELETE", "｢评论已被删除｣");
    }

    private static void encryptionContext() {
        // JWT密钥
        stringMap.put("JWT_SECRET", "");
        // JWT过期时间，单位为毫秒
        longMap.put("JWT_EXPIRE_TIME", 30 * 24 * 60 * 60 * 1000L);

        // 单向散列次数（HASH_NUM）

        // 对称加密
        stringMap.put("SYMMETRIC_ALGORITHM", "");
        stringMap.put("SYMMETRIC_SECRET_KEY", "");

        // 解密前端数据
        stringMap.put("AES_KEY", "");
        stringMap.put("AES_IV", "");
    }

    private static void thirdPartyServiceContext() {
        // 七牛云
        stringMap.put("QINIU_ACCESS_KEY", "");
        stringMap.put("QINIU_SECRET_KEY", "");
        stringMap.put("QINIU_BUCKET", "");
        stringMap.put("QINIU_FILE_BASEURL", "");
        stringMap.put("AVATAR_PATH", "avatar");
        stringMap.put("BLOG_PATH", "blog");
        stringMap.put("MUSIC_PATH", "music");
        stringMap.put("MUSIC_COVER_PATH", "musicCover");
        stringMap.put("SHUOSHUO_PATH", "shuoshuo");
        stringMap.put("VIDEO_PATH", "video");
        stringMap.put("VIDEO_COVER_PATH", "videoCover");
    }

    private static void programContext() {
        stringMap.put("FOLDER_URL", "");

        // token请求头名称
        stringMap.put("TOKEN_HEADER", "");
        stringMap.put("ID_HEADER", "");
        stringMap.put("ROLE_HEADER", "");

        // feign请求认证
        stringMap.put("FEIGN_TOKEN", "");
        stringMap.put("FEIGN_AUTHORIZATION", "");

        // 用户角色
        stringMap.put("ROLE_ADMIN", "");
        stringMap.put("ROLE_USER", "");
    }
}
