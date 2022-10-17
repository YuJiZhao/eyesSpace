package com.eyes.eyesspace.common.tool.context;

import java.util.ArrayList;
import java.util.List;

public class MediaContext {
    public static final List<String> image = new ArrayList<>();

    static {
        imageSet();
    }

    private static void imageSet() {
        image.add(".jpg");
        image.add(".png");
        image.add(".jpeg");
    }
}
