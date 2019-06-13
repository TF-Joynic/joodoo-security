package indi.joynic.joodoo.security.keystore.sp.redis.response.handler;

/**
 * -ERR unknown command 'foobar'
 */
public class CommonErrorResponseHandlerImpl extends AbstractResponseHandler {
    public static final String PREFIX = "-ERR";
    public static final int PREFIX_END_INDEX = 4;

    public CommonErrorResponseHandlerImpl() {
        super(PREFIX, PREFIX_END_INDEX);
    }

    @Override
    public Object doHandle(String responseContent) {
        return responseContent.substring(PREFIX_END_INDEX + 1);
    }
}