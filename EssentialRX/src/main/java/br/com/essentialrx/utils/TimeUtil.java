package br.com.essentialrx.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static long parseToMillis(String input) {
        if (input == null || input.trim().isEmpty()) return -1;

        input = input.toLowerCase().trim();
        long total = 0;

        String number = "";
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                number += c;
            } else {
                if (number.isEmpty()) return -1;
                long n = Long.parseLong(number);
                number = "";

                switch (c) {
                    case 's': total += TimeUnit.SECONDS.toMillis(n); break;
                    case 'm': total += TimeUnit.MINUTES.toMillis(n); break;
                    case 'h': total += TimeUnit.HOURS.toMillis(n); break;
                    case 'd': total += TimeUnit.DAYS.toMillis(n); break;
                    default: return -1;
                }
            }
        }

        if (!number.isEmpty()) {
            total += TimeUnit.SECONDS.toMillis(Long.parseLong(number));
        }

        return total;
    }

    public static String formatSince(long lastMillis) {
        long diff = System.currentTimeMillis() - lastMillis;
        if (diff < 0) diff = 0;

        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) return days + "d atrás";
        if (hours > 0) return hours + "h atrás";
        if (minutes > 0) return minutes + "m atrás";
        return seconds + "s atrás";
    }

    public static String formatDuration(long durationMillis) {
        long seconds = durationMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) return days + "d";
        if (hours > 0) return hours + "h";
        if (minutes > 0) return minutes + "m";
        return seconds + "s";
    }
}
