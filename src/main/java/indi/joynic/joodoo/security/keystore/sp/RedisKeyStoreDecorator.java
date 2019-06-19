package indi.joynic.joodoo.security.keystore.sp;

import indi.joynic.joodoo.security.keystore.sp.redis.RedisCommand;
import indi.joynic.joodoo.security.keystore.sp.redis.RedisRequestProtocol;
import indi.joynic.joodoo.security.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.provider.JavaKeyStore;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Enumeration;

/**
 * Store secret key data in Redis.
 *
 * When saving or fetching key, a connection( to redis server) operation need to be performed via Socket.
 *
 * @author Terrance Fung
 */
public class RedisKeyStoreDecorator extends KeyStoreSpi {

    private static final Logger logger = LoggerFactory.getLogger(RedisKeyStoreDecorator.class);
    private RedisKeyStore redisKeyStore;

    public RedisKeyStoreDecorator(RedisKeyStore redisKeyStore) {
        this.redisKeyStore = redisKeyStore;
    }

    @Override
    public Key engineGetKey(String alias, char[] password) throws NoSuchAlgorithmException, UnrecoverableKeyException {
        return null;
    }

    @Override
    public Certificate[] engineGetCertificateChain(String alias) {
        return new Certificate[0];
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

    /**
     * decorate to REDIS: HSET name key val
     *
     * @param stream
     * @param password
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     */
    @Override
    public void engineStore(OutputStream stream, char[] password) throws IOException, NoSuchAlgorithmException, CertificateException {

        /*DataOutputStream dataOutputStream = new DataOutputStream(stream);

        StringBuilder stringBuilder = new StringBuilder();

        RedisCommand redisCommand = RedisCommand.HSET;

        stringBuilder.append(RedisRequestProtocol.paramCount(3));
        stringBuilder.append(RedisRequestProtocol.EOL);
        stringBuilder.append(RedisRequestProtocol.byteLength(redisCommand));
        stringBuilder.append(RedisRequestProtocol.EOL);
        stringBuilder.append(RedisRequestProtocol.paramValue(redisCommand));
        stringBuilder.append(RedisRequestProtocol.EOL);

        stringBuilder.append(RedisRequestProtocol.byteLength(hashKey));
        stringBuilder.append(RedisRequestProtocol.EOL);

        *//*stringBuilder.append(RedisRequestProtocol.paramValue());
        stringBuilder.append(RedisRequestProtocol.EOL);*//*

        dataOutputStream.write(stringBuilder.toString().getBytes());

        redisKeyStore.engineStore(dataOutputStream, password);*/
    }

    @Override
    public void engineLoad(InputStream stream, char[] password) throws IOException, NoSuchAlgorithmException, CertificateException {


    }
}