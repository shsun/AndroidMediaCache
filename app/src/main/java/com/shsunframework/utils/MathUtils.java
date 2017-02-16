package com.shsunframework.utils;

import java.util.Random;

/**
 * Created by shsun on 17/1/10.
 */

public class MathUtils {
    /**
     * @param min
     * @param max
     * @return
     */
    public static int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }
}
