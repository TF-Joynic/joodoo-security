package indi.joynic.joodoo.security.keystore.sp.redis.response.handler;

public abstract class AbstractResponseHandler implements ResponseHandler {
    private final String prefix;
    private final int prefixEndIndex;

    private AbstractResponseHandler next;

    public AbstractResponseHandler(String prefix, int prefixEndIndex) {
        this.prefix = prefix;
        this.prefixEndIndex = prefixEndIndex;
    }

    public void setNext(AbstractResponseHandler next) {
        this.next = next;
    }

    public final Object handle(String responseContent) {
        if (responseContent.substring(0, prefixEndIndex).equals(prefix)) {
            return doHandle(responseContent);
        } else {
            return next.handle(responseContent);
        }
    }

    public abstract Object doHandle(String responseContent);
}