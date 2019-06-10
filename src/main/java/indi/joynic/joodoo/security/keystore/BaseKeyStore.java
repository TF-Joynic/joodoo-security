package indi.joynic.joodoo.security.keystore;

import indi.joynic.joodoo.security.keystore.algo.SignatureAlgo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * keyStore
 */
public class BaseKeyStore implements GeneralKeyStore {
    private static final Logger logger = LoggerFactory.getLogger(BaseKeyStore.class);

    private KeyStoreType     keyStoreType;
    private KeyStoreProvider keyStoreProvider;
    private InputStream      inputStream;
    private String           keyStoreEntry;
    private String           keyStorePassword;
    private SignatureAlgo    signatureAlgo;
    private String certificateFilePath;

    private KeyStore keyStore;

    BaseKeyStore(GeneralKeyStore.Builder builder) {
        this.keyStoreType = builder.getKeyStoreType();
        this.keyStoreProvider = builder.getKeyStoreProvider();
        this.inputStream = builder.getInputStream();
        this.keyStoreEntry = builder.getKeyStoreEntry();
        this.keyStorePassword = builder.getKeyStorePassword();
        this.signatureAlgo = builder.getSignatureAlgo();
        this.certificateFilePath = builder.getCertificateFilePath();
    }

    void init() throws KeyStoreException, NoSuchProviderException {
        this.keyStore = KeyStore.getInstance(keyStoreType.getType(), keyStoreProvider.getCode());
    }

    void load() throws IOException, NoSuchAlgorithmException, CertificateException {
        keyStore.load(inputStream, keyStorePassword.toCharArray());
    }

    String sign(String content) {
        String signStr = null;

        try {
            KeyStore.PasswordProtection parameter = new KeyStore.PasswordProtection(keyStorePassword.toCharArray());
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(keyStoreEntry, parameter);

            Signature signature = Signature.getInstance(signatureAlgo.getAlgo());

            signature.initSign(privateKeyEntry.getPrivateKey());

            if (null != content) {
                signature.update(content.getBytes(UTF_8));
            }

            signStr = new String(Base64.getEncoder().encode(signature.sign()));

        } catch (KeyStoreException | NoSuchAlgorithmException | SignatureException
                | InvalidKeyException | UnrecoverableEntryException e) {

            logger.error("keyStore sign failed", e);
        }

        return signStr;
    }

    public boolean verify(String content, String signStr) {
        if (StringUtils.isEmpty(signStr)) {
            return false;
        }

        boolean verifiedSuccess = false;

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            File publicKeyFile = new File(certificateFilePath);

            FileInputStream fileInputStream = new FileInputStream(publicKeyFile);

            Certificate certificate = cf.generateCertificate(fileInputStream);

            Signature signatureServerSide = Signature.getInstance(signatureAlgo.getAlgo());

            PublicKey publicKey = certificate.getPublicKey();
            signatureServerSide.initVerify(publicKey);

            if (null != content) {
                signatureServerSide.update(content.getBytes(UTF_8));
            }

            verifiedSuccess = signatureServerSide.verify(Base64.getDecoder().decode(signStr));

        } catch (NoSuchAlgorithmException
                | InvalidKeyException | SignatureException | CertificateException | IOException e) {

            logger.error("keyStore verify failed", e);

        }

        return verifiedSuccess;
    }



}