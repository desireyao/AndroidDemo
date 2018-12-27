package com.mac;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by yaoh on 2018/12/26.
 */

public class Main2 {

    private static final String KEY_HmacSHA256 = "YJSVqW+5EKFL3ROumnFY5ofKqH1JJtOaf9J/tHr88Bs=";

    public static void main(String[] args) {
        String testData = "123456000A400101545876310390";
        String result = sha256_HMAC(testData);
        System.out.println(" result ========> " + result);

    }

    public static String sha256_HMAC(String message) {
        String hash = "";
//        String secret = decodeBase64(KEY_HmacSHA256);
        byte[] key = decodeBase64(KEY_HmacSHA256);
        System.out.println(" key decodeBase64 ===========> " + byteArrayToHexString(key));

        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("utf-8"), "HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key, "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes("utf-8"));

            System.out.println(" before encodeBase64 ===========> " + byteArrayToHexString(bytes));
            hash = encodeBase64(bytes);
            return hash;
        } catch (Exception e) {

        }
        return hash;
    }

    private static String encodeBase64(byte[] data) {
        String asB64 = null;
        try {
            asB64 = Base64.getEncoder().encodeToString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return asB64;
    }

//    private static String decodeBase64(String data) {
//        byte[] asBytes = Base64.getDecoder().decode(data);
//        String result = null;
//        try {
//            result = new String(asBytes, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    private static byte[] decodeBase64(String data) {
        byte[] asBytes = Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8));
        return asBytes;
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


}
