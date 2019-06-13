package indi.joynic.joodoo.security.keystore.sp.redis.response.handler;

import indi.joynic.joodoo.security.keystore.sp.redis.RedisRequestProtocol;

public class StatusResponseHandlerImpl extends AbstractResponseHandler {
    public static final String PREFIX = "+";
    public static final int PREFIX_END_INDEX = 1;

    public StatusResponseHandlerImpl() {
        super(PREFIX, PREFIX_END_INDEX);
    }

    @Override
    public String doHandle(String responseContent) {
        return responseContent.substring(PREFIX_END_INDEX).replace(RedisRequestProtocol.EOL, "");
    }
}