package indi.joynic.joodoo.security.keystore;

import indi.joynic.joodoo.security.keystore.algo.SignatureAlgo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FileSystemKeyStoreTest {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemKeyStoreTest.class);

    @Test
    public void testMain() {
        GeneralKeyStore.Builder fileSystemKeyStoreBuilder = new FileSystemKeyStore.Builder()
                .filePath("D:/misc/test.keystore")
                .keyStoreType(KeyStoreType.JAVA_KEYSTORE)
                .keyStoreProvider(KeyStoreProvider.SUN)
                .keyStoreEntry("test1")
                .keyStorePassword("123456")
                .signatureAlgo(SignatureAlgo.SHA256WITHRSA)
                .certificateFilePath("D:/misc/test1.cer");

        BaseKeyStore baseKeyStore = fileSystemKeyStoreBuilder.build();
        baseKeyStore.init();
        baseKeyStore.load();

        String sit = baseKeyStore.sign("ttt");
        Assert.assertTrue(baseKeyStore.verify("ttt", sit));
    }
}