package indi.joynic.joodoo.security;

import indi.joynic.joodoo.security.keystore.algo.SignatureAlgo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Terrance Fung
 */
public class RsaUtil {
    private static final Logger logger = LoggerFactory.getLogger(RsaUtil.class);

    private static final String KEY_TYPE = "RSA";

    public static PublicKey concretePublicKey(String rawPublicKey) {
        if (StringUtils.isEmpty(rawPublicKey)) {
            return null;
        }

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rawPublicKey));
        PublicKey publicKey = null;
        try {
            publicKey = KeyFactory.getInstance(KEY_TYPE).generatePublic(x509EncodedKeySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            logger.error("concretePublicKey err", e);
        }

        return publicKey;
    }

    public static PrivateKey concretePrivateKey(String rawPrivateKey) {
        if (StringUtils.isEmpty(rawPrivateKey)) {
            return null;
        }

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rawPrivateKey));
        PrivateKey privateKey = null;

        try {
            privateKey = KeyFactory.getInstance(KEY_TYPE).generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("concretePrivateKey err", e);
        }

        return privateKey;
    }

    public static String sign(String rawPrivateKey, String content, Charset contentCharset) {
        if (StringUtils.isEmpty(content)) {
            return StringUtils.EMPTY;
        }

        try {
            Signature signature = Signature.getInstance(SignatureAlgo.SHA256WITHRSA.getAlgo());

            PrivateKey key = concretePrivateKey(rawPrivateKey);

            signature.initSign(key);
            signature.update(content.getBytes(contentCharset));

            return new String(Base64.getEncoder().encode(signature.sign()));
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            logger.error("sign err", e);
        }

        return null;
    }

    /*public static String decrypt() {

    }*/

    public static boolean verify(String rawPublicKey, String givenContent, String signStr, Charset contentCharset) {
        if (StringUtils.isEmpty(rawPublicKey) || StringUtils.isEmpty(givenContent) || null == contentCharset) {
            return false;
        }

        try {
            Signature signature = Signature.getInstance(SignatureAlgo.SHA256WITHRSA.getAlgo());

            signature.initVerify(concretePublicKey(rawPublicKey));
            signature.update(givenContent.getBytes());

            return signature.verify(Base64.getDecoder().decode(signStr));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            logger.error("verify failed", e);
        }

        return false;
    }
}