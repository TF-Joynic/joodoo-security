package indi.joynic.joodoo.security.net;

import java.util.Objects;
import java.util.regex.Pattern;

public class AddrUtil {

    /**
     *
     * @param host like: 10.12.122.1
     * @return
     * @throws IllegalArgumentException
     */
    public static byte[] inet4AddrTextToByteArray(String host) throws IllegalArgumentException {
        Objects.requireNonNull(host, "host can't be null!");

        String[] segments = host.split(Pattern.quote("."));
        if (segments.length != 4) {
            throw new IllegalArgumentException("not a valid host");
        }

        byte[] addrArray = new byte[4];
        for (int i = 0; i < segments.length; i ++) {
            addrArray[i] = (byte) (Integer.parseInt(segments[i]) & 0xFF);
        }

        return addrArray;
    }

    public static String inet4AddrByteArrayToText(byte[] src) throws IllegalArgumentException {
        if (null == src || src.length != 4) {
            throw new IllegalArgumentException("src byte is not valid");
        }

        return (src[0] & 0xff) + "." + (src[1] & 0xff) + "." + (src[2] & 0xff) + "." + (src[3] & 0xff);
    }
}