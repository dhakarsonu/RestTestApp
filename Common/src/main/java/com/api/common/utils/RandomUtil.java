package com.api.common.utils;

import java.security.SecureRandom;
import java.util.Random;

public final class RandomUtil {

    public enum RandomModeType {
        ALPHA, ALPHANUMERIC, NUMERIC, ALPHANUMERIC_DASH
    }

    public static String generateRandomString() {
        return generateRandomString(32, RandomModeType.ALPHANUMERIC, null);
    }

    public static String generateRandomString(int length) {
        return generateRandomString(length, RandomModeType.ALPHANUMERIC, null);
    }

    public static String generateRandomString(int length, RandomModeType type) {
        return generateRandomString(length, type, null);
    }

    public static String generateRandomString(int length, RandomModeType type, String salt) {

        StringBuilder builder = new StringBuilder(length);
        String source = getSource(type, salt);

        while (length-- != 0) {
            assert source != null;
            int c = (int) (Math.random() * source.length());
            builder.append(source.charAt(c));
        }
        return builder.toString();
    }

    private static String getSource(RandomModeType type, String salt) {

        StringBuilder source = new StringBuilder();
        switch (type) {

            case ALPHANUMERIC_DASH:
                source.append("_-");
            case ALPHANUMERIC:
                source.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
                break;
            case ALPHA:
                source.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
                break;
            case NUMERIC:
                source.append("1234567890");
                break;
            default:
                return null;
        }

        if (salt != null)
            source.append(salt);
        return source.toString();
    }

    public static String generateSecureRandomString(int length, RandomModeType type) {
        return generateSecureRandomString(length, type, null);
    }

    public static String generateSecureRandomString(int length, RandomModeType type, Random random) {

        String source = getSource(type, null);
        StringBuilder builder = new StringBuilder(length);

        if (random == null)
            random = new SecureRandom();

        while (length-- != 0) {
            builder.append(source.charAt(random.nextInt(source.length())));
        }
        return builder.toString();
    }
}
