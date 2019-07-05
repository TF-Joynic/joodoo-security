package indi.joynic.joodoo.security.keystore.sp;

import indi.joynic.joodoo.security.keystore.sp.redis.RedisCommand;
import indi.joynic.joodoo.security.keystore.sp.redis.RedisRequestProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Enumeration;

public class RedisKeyStore extends KeyStoreSpi {

    private static final Logger logger = LoggerFactory.getLogger(RedisKeyStore.class);

    private String hashName;
    private String key;

    public RedisKeyStore(String hashName, String key) {
        this.hashName = hashName;
        this.key = key;
    }

    private final int HSET_CMD_PRAM_COUNT = 4;

    /**
     * jooDoo-sECurity rEDis => DECED
     */
    private static final int MAGIC = 0xDECED;
    private static final int VERSION_01 = 0x01;

    @Override
    public Key engineGetKey(String alias, char[] password) throws NoSuchAlgorithmException, UnrecoverableKeyException {
        return null;
    }

    @Override
    public Certificate[] engineGetCertificateChain(String alias) {
        return null;
    }

    @Override
    public Certificate engineGetCertificate(String alias) {
        return null;
    }

    @Override
    public Date engineGetCreationDate(String alias) {
        return null;
    }

    @Override
    public void engineSetKeyEntry(String alias, Key key, char[] password, Certificate[] chain) throws KeyStoreException {
    }

    @Override
    public void engineSetKeyEntry(String alias, byte[] key, Certificate[] chain) throws KeyStoreException {
    }

    @Override
    public void engineSetCertificateEntry(String alias, Certificate cert) throws KeyStoreException {
    }

    @Override
    public void engineDeleteEntry(String alias) throws KeyStoreException {
    }

    @Override
    public Enumeration<String> engineAliases() {
        return null;
    }

    @Override
    public boolean engineContainsAlias(String alias) {
        return false;
    }

    @Override
    public int engineSize() {
        return 0;
    }

    @Override
    public boolean engineIsKeyEntry(String alias) {
        return false;
    }

    @Override
    public boolean engineIsCertificateEntry(String alias) {
        return false;
    }

    @Override
    public String engineGetCertificateAlias(Certificate cert) {
        return null;
    }

    @Override
    public void engineStore(OutputStream stream, char[] password) throws IOException, NoSuchAlgorithmException, CertificateException {

        RedisCommand redisCmd = RedisCommand.HSET;
        // hset name key val
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(RedisRequestProtocol.paramCount(HSET_CMD_PRAM_COUNT));
        stringBuilder.append(RedisRequestProtocol.EOL);
        stringBuilder.append(RedisRequestProtocol.byteLength(redisCmd));
        stringBuilder.append(RedisRequestProtocol.EOL);
        stringBuilder.append(RedisRequestProtocol.paramValue(redisCmd));
        stringBuilder.append(RedisRequestProtocol.EOL);

        Object[] args = new Object[]{hashName, key};
        for (Object arg : args) {

            stringBuilder.append(RedisRequestProtocol.byteLength(arg));
            stringBuilder.append(RedisRequestProtocol.EOL);

            stringBuilder.append(RedisRequestProtocol.paramValue(arg));
            stringBuilder.append(RedisRequestProtocol.EOL);
        }

        stream.write(stringBuilder.toString().getBytes());

        OutputStream valOutputStream = new ByteArrayOutputStream();

        //jks.engineStore(valOutputStream, password);

        storeVal(valOutputStream, "1222".toCharArray());

        int valSize = ((ByteArrayOutputStream) valOutputStream).size();

        stream.write(String.format(RedisRequestProtocol.SYMBOL_PARAM_BYTE_LENGTH, valSize).getBytes());
        stream.write(RedisRequestProtocol.EOL.getBytes());

        stream.write(((ByteArrayOutputStream) valOutputStream).toByteArray());
        stream.write(RedisRequestProtocol.EOL.getBytes());

        stream.flush();
    }

    @Override
    public void engineLoad(InputStream stream, char[] password) throws IOException, NoSuchAlgorithmException, CertificateException {

    }

    private OutputStream storeVal(OutputStream stream, char[] password) {
        try {
            stream.write(new String(password).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }
}