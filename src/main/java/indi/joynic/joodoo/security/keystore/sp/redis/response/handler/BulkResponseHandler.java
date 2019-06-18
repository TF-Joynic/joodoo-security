package indi.joynic.joodoo.security.keystore.sp.redis.response.handler;

public class BulkResponseHandler extends AbstractResponseHandler {
    private static final String PREFIX           = "$";
    private static final int    PREFIX_END_INDEX = 1;

    public BulkResponseHandler() {
        super(PREFIX, PREFIX_END_INDEX);
    }

    @Override
    public Object doHandle(String responseContent) {
        int i = 0;

        while (true) {

        }
    }
}