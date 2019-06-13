package indi.joynic.joodoo.security.keystore.sp.redis;

public class RedisRequestProtocolTemplateImpl implements RedisRequestProtocolTemplate {
    private final RedisCommand redisCmd;
    private final Object[] args;

    public RedisRequestProtocolTemplateImpl(RedisCommand redisCmd, Object ...args) {
        this.redisCmd = redisCmd;
        this.args = args;
    }

    @Override
    public byte[] commandToByteArray() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(RedisRequestProtocol.paramCount(args.length + 1));
        stringBuilder.append(RedisRequestProtocol.EOL);
        stringBuilder.append(RedisRequestProtocol.byteLength(redisCmd));
        stringBuilder.append(RedisRequestProtocol.EOL);
        stringBuilder.append(RedisRequestProtocol.paramValue(redisCmd));
        stringBuilder.append(RedisRequestProtocol.EOL);

        for (Object arg : args) {

            stringBuilder.append(RedisRequestProtocol.byteLength(arg));
            stringBuilder.append(RedisRequestProtocol.EOL);

            stringBuilder.append(RedisRequestProtocol.paramValue(arg));
            stringBuilder.append(RedisRequestProtocol.EOL);

        }

        return stringBuilder.toString().getBytes();
    }

    @Override
    public String toString() {
        return new String(commandToByteArray());
    }
}