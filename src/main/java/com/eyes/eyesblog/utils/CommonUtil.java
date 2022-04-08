package com.eyes.eyesblog.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
    /**
     * 字数统计
     * @param sContent 正文内容
     * @return
     */
    public static int wordCount(String sContent) {
        int byteCount = 0;
        //中文字符的处理
        String cn_words = sContent.replaceAll("[^(\\u4e00-\\u9fa5\\x3130-\\x318F\\u0800-\\u4e00，。《》？；’‘：“”【】、）（……￥！·)]", "");
        int cn_word_count = cn_words.length();
        //英文字符的处理
        String en_words = sContent.replaceAll("[^(a-zA-Z0-9`\\-=\\';.,/~!@#$%^&*()_+|}{\\\":><?\\[\\]\" \")]", "");
        int en_words_count = 0;
        String[] en = en_words.split(" ");
        for (String s : en){
            if (s.trim().length() != 0){
                en_words_count++;
            }
        }
        //合计处理
        byteCount = cn_word_count + en_words_count;
        return byteCount;
    }
}
