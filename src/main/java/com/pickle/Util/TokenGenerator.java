package com.pickle.Util;

import java.util.Random;

/**
 * Created by Syukri Mullia Adil P on 4/25/2016.
 */
public class TokenGenerator {

    private static final String stringPool = "abcdefghijklmnopqrstuvwxyz1234567890";
    private static final Random r = new Random();

    // generate token with format %phoneNumber%
    public static String generateApiToken(String phoneNumber){
        String token = "";
        int mid = r.nextInt(10);
        for (int i = 0; i < 10; i++) {
            token += stringPool.charAt(r.nextInt(stringPool.length()));
            if (i == mid) token += phoneNumber;
        }
        return token;
    }
}
