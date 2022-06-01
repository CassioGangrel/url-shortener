package br.com.cassiofiuza.tiny_url;

import java.security.SecureRandom;

public class NanoIdGenerator {
    private static final SecureRandom DEFAULT_RANDOM = new SecureRandom();

    private static final char[] DEFAULT_CHARS = "_-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            .toCharArray();

    private static final int DEFAULT_LENGTH = 6;

    private NanoIdGenerator() {
    }

    public static String generate() {
        return generate(DEFAULT_LENGTH, DEFAULT_CHARS, DEFAULT_RANDOM);
    }

    public static String generate(int length) {
        return generate(length, DEFAULT_CHARS, DEFAULT_RANDOM);
    }

    public static String generate(int length, char[] chars) {
        return generate(length, chars, DEFAULT_RANDOM);
    }

    public static String generate(int length, char[] chars, SecureRandom random) {
        if (length < 1) {
            throw new IllegalArgumentException("length < 1: " + length);
        }

        if (chars.length < 2) {
            throw new IllegalArgumentException("chars.length < 2: " + chars.length);
        }

        if (random == null) {
            throw new NullPointerException("random");
        }

        char[] nanoid = new char[length];
        for (int i = 0; i < length; i++) {
            int rnd = random.nextInt(chars.length);
            nanoid[i] = chars[rnd];
        }
        return new String(nanoid);
    }
}
