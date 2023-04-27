package com.eyes.eyesspace.constant;

import java.util.ArrayList;
import java.util.List;

public class MediaConstant {
    private static final List<String> image = new ArrayList<>();

    static {
        image.add(".jpg");
        image.add(".png");
        image.add(".jpeg");
    }

    public static boolean imgContain(String media) {
        return image.contains(media);
    }
}
