package indi.joynic.joodoo.security.keystore.sp.redis.response.handler;

/**
 * -WRONGTYPE Operation against a key holding the wrong kind of value
 */
public class WrongTypeErrorResponseHandlerImpl extends AbstractResponseHandler {
    public static final String PREFIX = "-WRONGTYPE";
    public static final int PREFIX_END_INDEX = 10;

    public WrongTypeErrorResponseHandlerImpl() {
        super(PREFIX, PREFIX_END_INDEX);
    }

    @Override
    public Object doHandle(String responseContent) {
        return responseContent.substring(PREFIX_END_INDEX + 1);
    }
}