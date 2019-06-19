package indi.joynic.joodoo.security.keystore.sp.redis;

public enum RedisCommand {
    SET(3),
    GET(3),
    HGET(4),
    INCR(4),
    HSET(4)
    ;

    private int byteLength;

    RedisCommand(int byteLength) {
        this.byteLength = byteLength;
    }

    public int getByteLength() {
        return byteLength;
    }
}