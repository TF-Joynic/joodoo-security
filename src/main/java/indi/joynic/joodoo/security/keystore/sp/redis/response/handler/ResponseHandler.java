package indi.joynic.joodoo.security.keystore.sp.redis.response.handler;

public interface ResponseHandler {
    void setNext(AbstractResponseHandler next);

    Object handle(String responseContent);
}