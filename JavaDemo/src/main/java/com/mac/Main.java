package com.mac;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by yaoh on 2018/12/26.
 */

public class Main {

    private static final String KEY_HmacSHA256 = "qoz2D3gLPzAY+Sv+1hwciMY9kkF4P9W/Upom3vKMl6c=";

    public static void main(String[] args) {
        String testData = "12345678000A012311544583497000";
        String key = decodeBase64(KEY_HmacSHA256);
        System.out.println(" key = " + key);

        String result = sha256_HMAC(testData);
        System.out.println(" result--->" + result);
    }

    private static String sha256_HMAC(String message) {
        String hash = "";
        String secret = decodeBase64(KEY_HmacSHA256);
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
            System.out.println(" before encodeBase64 ===========> " + hash);

            hash = encodeBase64(hash);
            return hash;
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    private static String encodeBase64(String data) {
        String asB64 = null;
        try {
            asB64 = Base64.getEncoder().encodeToString(data.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return asB64;
    }

    private static String decodeBase64(String data) {
        byte[] asBytes = Base64.getDecoder().decode(data);
        return byteArrayToHexString(asBytes);
//        try {
////            return new String(asBytes, "utf-8"); // 输出为: some string
//            return byteArrayToHexString(asBytes);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

}
