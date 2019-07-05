package indi.joynic.joodoo.security.keystore;

import indi.joynic.joodoo.security.keystore.algo.SignatureAlgo;
import indi.joynic.joodoo.security.keystore.callback.Callback;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * GeneralKeyStore
 */
public interface GeneralKeyStore {

    public static class Builder {
        private KeyStoreType     keyStoreType;
        private KeyStoreProvider keyStoreProvider;
        private InputStream      inputStream;
        private OutputStream outputStream;
        private String           keyStoreEntry;
        private String           keyStorePassword;
        private SignatureAlgo    signatureAlgo;
        private String           certificateFilePath;

        Builder keyStoreType(KeyStoreType keyStoreType) {
            this.keyStoreType = keyStoreType;
            return this;
        }

        Builder keyStoreProvider(KeyStoreProvider keyStoreProvider) {
            this.keyStoreProvider = keyStoreProvider;
            return this;
        }

        Builder inputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        Builder outputStream(OutputStream outputStream) {
            this.outputStream = outputStream;
            return this;
        }

        Builder keyStoreEntry(String keyStoreEntry) {
            this.keyStoreEntry = keyStoreEntry;
            return this;
        }

        Builder keyStorePassword(String keyStorePassword) {
            this.keyStorePassword = keyStorePassword;
            return this;
        }

        public Builder signatureAlgo(SignatureAlgo signatureAlgo) {
            this.signatureAlgo = signatureAlgo;
            return this;
        }

        Builder certificateFilePath(String certificateFilePath) {
            this.certificateFilePath = certificateFilePath;
            return this;
        }

        public BaseKeyStore build() {
            return new BaseKeyStore(this);
        }

        KeyStoreType getKeyStoreType() {
            return keyStoreType;
        }

        KeyStoreProvider getKeyStoreProvider() {
            return keyStoreProvider;
        }

        InputStream getInputStream() {
            return inputStream;
        }

        OutputStream getOutputStream() {
            return outputStream;
        }

        String getKeyStoreEntry() {
            return keyStoreEntry;
        }

        String getKeyStorePassword() {
            return keyStorePassword;
        }

        SignatureAlgo getSignatureAlgo() {
            return signatureAlgo;
        }

        String getCertificateFilePath() {
            return certificateFilePath;
        }

    }
}