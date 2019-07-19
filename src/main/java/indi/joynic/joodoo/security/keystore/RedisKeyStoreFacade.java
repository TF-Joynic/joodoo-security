package indi.joynic.joodoo.security.keystore;

import indi.joynic.joodoo.security.keystore.sp.redis.RedisCommand;
import indi.joynic.joodoo.security.keystore.sp.redis.RedisConnector;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class RedisKeyStoreFacade implements GeneralKeyStore {
    private static final String HOST_PORT_SEPARATOR           = ":";
    private static final String KEYSTORE_ENTRY_HOST_SEPARATOR = "@";
    private static final String KEYSTORE_HASH_NAME = "joodoo_sec_keystore";

    public static class Builder extends GeneralKeyStore.Builder {

        /**
         * specified server keyName, host & port.
         *
         * @param keyNameHostPort key1@122.221.22.1:6379
         * @return
         */
        public GeneralKeyStore.Builder serverHostPort(String keyNameHostPort) throws IllegalArgumentException {
            if (StringUtils.isEmpty(keyNameHostPort) || !keyNameHostPort.contains(HOST_PORT_SEPARATOR)) {
                throw new IllegalArgumentException();
            }

            String[] hostPortArray = keyNameHostPort.split(HOST_PORT_SEPARATOR);

            String keyEntryHost = hostPortArray[0];

            String keyName = null, host = null;
            if (StringUtils.isNotEmpty(keyEntryHost)) {
                String[] keyEntryHostArray = keyEntryHost.split(KEYSTORE_ENTRY_HOST_SEPARATOR);
                keyName = keyEntryHostArray[0];
                host = keyEntryHostArray[1];
            }

            int port = Integer.valueOf(hostPortArray[1]);
            RedisConnector redisConnector = new RedisConnector(host, port);

            InputStream inputStream = null;

            redisConnector.connect();
            if (redisConnector.isConnected()) {
                OutputStream outputStream = redisConnector.sendDataToServer(RedisCommand.HGET, KEYSTORE_HASH_NAME, keyName);
                inputStream = redisConnector.receiveDataFromServer();

                Objects.requireNonNull(outputStream, "socket outputStream null");
                Objects.requireNonNull(inputStream, "socket inputStream null");


                this.inputStream(inputStream);
            }

            return this;
        }
    }
}