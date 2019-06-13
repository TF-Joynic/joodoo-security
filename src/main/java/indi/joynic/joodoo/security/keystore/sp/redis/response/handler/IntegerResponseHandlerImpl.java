package indi.joynic.joodoo.security.keystore.sp.redis.response.handler;

import indi.joynic.joodoo.security.keystore.sp.redis.RedisRequestProtocol;

public class IntegerResponseHandlerImpl extends AbstractResponseHandler {
    public static final String PREFIX = ":";
    public static final int PREFIX_END_INDEX = 1;

    public IntegerResponseHandlerImpl() {
        super(PREFIX, PREFIX_END_INDEX);
    }

    @Override
    public Integer doHandle(String responseContent) {
        return Integer.valueOf(responseContent.substring(PREFIX_END_INDEX).replace(RedisRequestProtocol.EOL, ""));
    }
}