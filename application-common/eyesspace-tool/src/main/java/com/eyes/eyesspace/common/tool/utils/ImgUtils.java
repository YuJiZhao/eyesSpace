package com.eyes.eyesspace.common.tool.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class ImgUtils {
    public static String getBase64Img(String str) {
        return BufferedImageToBase64(str2BufferedImage(str));
    }

    /**
     * BufferedImage转Base64
     * @param bufferedImage
     * @return
     */
    private static String BufferedImageToBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 = encoder.encodeBuffer(bytes).trim();
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");
        return "data:image/jpg;base64," + png_base64;
    }

    /**
     * Base64转BufferedImage
     * @param base64
     * @return
     */
    private static BufferedImage base64ToBufferedImage(String base64) {
        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转BufferedImage
     * @param codeString
     * @return
     */
    private static BufferedImage str2BufferedImage(String codeString) {
        int width = 150;
        int height = 34;
        Font font = new Font("Serif", Font.BOLD, 20);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = bi.createGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, width, height);

        int add = 0;
        double ro = 0.05;
        Random random = new Random();

        // 画干扰线
        for (int i = 0; i < 4; i++) {
            g2.setColor(new Color(102,102,102));
            g2.drawLine(random.nextInt(width / 2),random.nextInt(height),random.nextInt(width),random.nextInt(height));
        }

        // 写入验证码
        for (int i = 0; i < codeString.length(); i++) {
            // 设置字母颜色字体
            font.deriveFont(Font.BOLD, 30f);
            g2.setColor(new Color(102,102,102));
            g2.setFont(font);
            String let = String.valueOf(codeString.charAt(i));
            // 旋转字母
            if(random.nextBoolean()){
                ro = -ro;
            }
            g2.rotate(ro);
            // 指定写入字母位置并写入
            g2.drawString(let, random.nextInt(10) + add, 20);
            add += 40;
            g2.rotate(-ro);
        }
        g2.dispose();
        return bi;
    }
}
