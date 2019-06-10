package indi.joynic.joodoo.security.keystore;

import indi.joynic.joodoo.security.keystore.algo.SignatureAlgo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

public class FileSystemKeyStoreTest {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemKeyStoreTest.class);

    @Test
    public void testMain() {
        GeneralKeyStore.Builder fileSystemKeyStoreBuilder = new FileSystemKeyStore.Builder()
                .filePath("D:/misc/joodoo-sec.keystore")
                .keyStoreType(KeyStoreType.JAVA_KEYSTORE)
                .keyStoreProvider(KeyStoreProvider.SUN)
                .keyStoreEntry("joodoo-sec-demo")
                .keyStorePassword("123456")
                .signatureAlgo(SignatureAlgo.SHA256WITHRSA)
                .certificateFilePath("D:/misc/joodoo-sec.cer");

        BaseKeyStore baseKeyStore = fileSystemKeyStoreBuilder.build();
        try {
            baseKeyStore.init();
            baseKeyStore.load();
        } catch (KeyStoreException | NoSuchProviderException | CertificateException
                | NoSuchAlgorithmException | IOException e) {

            e.printStackTrace();
        }

        String content = "He can tune.";
        String sit = baseKeyStore.sign(content);

        logger.info("signed: {}", sit);
        Assert.assertTrue(baseKeyStore.verify(content, sit));
    }
}