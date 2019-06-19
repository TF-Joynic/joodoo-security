package indi.joynic.joodoo.security.util;

import java.security.MessageDigest;
import java.util.Random;

public class StrUtil {

    public static final byte RANDOM_STR_MIXED = 0;
    public static final byte RANDOM_STR_CHARS = 1;
    public static final byte RANDOM_STR_DIGITS = 2;

    /**
     * 获取随机字符串
     *
     * @return 字符串
     */
    public static String randomStr(int length, byte randomStrType) {

        if (randomStrType < 0 || randomStrType > 2) { return null; }

        String alphabeticStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String alphabeticNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder randomStr = new StringBuilder();
        while (randomStr.length() < length) {
            char randChar = 0;
            Random random = new Random();
            if (RANDOM_STR_DIGITS == randomStrType) {
                randChar = String.valueOf(random.nextInt(10)).toCharArray()[0];
            } else if (RANDOM_STR_CHARS == randomStrType) {
                randChar = alphabeticStr.toCharArray()[random.nextInt(52)];
            } else if (RANDOM_STR_MIXED == randomStrType) {
                randChar = alphabeticNumericStr.toCharArray()[random.nextInt(62)];
            }

            randomStr.append(randChar);
        }

        return randomStr.toString();
    }

    public static String md5(String inStr) throws Exception {
        MessageDigest md5 = null;
        md5 = MessageDigest.getInstance("MD5");
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) { byteArray[i] = (byte) charArray[i]; }

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuilder hexValue = new StringBuilder();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) { hexValue.append("0"); }
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
}