package com.flappybird.ex.utils;

import java.time.LocalTime;

public class GameUtils {
    public static boolean isNight() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        return (hour < 6 || hour >= 18);
    }
}
