package indi.joynic.joodoo.security.keystore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;

public class KeyStoreFacade extends KeyStore {

    private static final Logger logger = LoggerFactory.getLogger(KeyStoreFacade.class);

    /**
     * Creates a KeyStore object of the given type, and encapsulates the given provider implementation (SPI object) in it.
     *
     * @param keyStoreSpi the provider implementation.
     * @param provider    the provider.
     * @param type        the keystore type.
     */
    protected KeyStoreFacade(KeyStoreSpi keyStoreSpi, Provider provider, String type) {
        super(keyStoreSpi, provider, type);
    }

    public static class RedisBuilder extends Builder {
        private final KeyStoreType type = KeyStoreType.REDIS;
        private final Provider provider;
        private final PasswordProtection passwordProtection;
        private final AccessControlContext context;

        private KeyStore keyStore;

        public RedisBuilder(Provider provider, PasswordProtection passwordProtection, AccessControlContext context) {
            this.provider = provider;
            this.passwordProtection = passwordProtection;
            this.context = context;
        }

        @Override
        public KeyStore getKeyStore() throws KeyStoreException {

            PrivilegedAction<KeyStore> action = new PrivilegedAction<KeyStore>() {
                @Override
                public KeyStore run() {
                    if (null != keyStore) {
                        return keyStore;
                    }

                    if (null == passwordProtection) {
                        throw new IllegalArgumentException();
                    }

                    char[] password = passwordProtection.getPassword();
                    try {
                        KeyStore keyStore = KeyStore.getInstance(type.getType(), provider);
                    } catch (KeyStoreException e) {
                        logger.error("init KeyStore err", e);
                        return null;
                    }


                    return null;
                }
            };

            return null;
        }

        @Override
        public ProtectionParameter getProtectionParameter(String alias) throws KeyStoreException {
            return null;
        }
    }
}