package indi.joynic.joodoo.security.keystore.sp.redis;

public class RedisRequestProtocol {

    public static final String EOL = "\r\n";

    private static final String SYMBOL_PARAM_COUNT = "*%d";
    private static final String SYMBOL_PARAM_BYTE_LENGTH = "$%d";

    public static String paramCount(int count) {
        return String.format(SYMBOL_PARAM_COUNT, count);
    }

    public static String byteLength(Object arg) {
        return String.format(SYMBOL_PARAM_BYTE_LENGTH, arg.toString().getBytes().length);
    }

    public static String byteLength(RedisCommand redisCommand) {
        return String.format(SYMBOL_PARAM_BYTE_LENGTH, redisCommand.getByteLength());
    }

    public static String paramValue(RedisCommand redisCommand) {
        return redisCommand.name();
    }

    public static String paramValue(Object arg) {
        return arg.toString();
    }
}