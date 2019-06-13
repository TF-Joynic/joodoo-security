package indi.joynic.joodoo.security.keystore;

import indi.joynic.joodoo.security.keystore.sp.redis.RedisCommand;
import indi.joynic.joodoo.security.keystore.sp.redis.RedisConnector;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;

public class RedisKeyStoreFacade implements GeneralKeyStore {
    private static final String HOST_PORT_SEPARATOR = ":";

    public static class Builder extends GeneralKeyStore.Builder {
        /**
         * specified server host & port.
         *
         * @param hostPort
         * @return
         */
        public GeneralKeyStore.Builder serverHostPort(String hostPort) throws IllegalArgumentException {
            if (StringUtils.isEmpty(hostPort) || !hostPort.contains(HOST_PORT_SEPARATOR)) {
                throw new IllegalArgumentException();
            }

            String[] hostPortArray = hostPort.split(HOST_PORT_SEPARATOR);

            String host = hostPortArray[0];
            int port = Integer.valueOf(hostPortArray[1]);

            RedisConnector redisConnector = new RedisConnector(host, port);

            InputStream inputStream = null;

            /*try {
                redisConnector.connect();
                redisConnector.sendDataToServer(RedisCommand.HGET, );
            }*/

            return null;
        }
    }
}