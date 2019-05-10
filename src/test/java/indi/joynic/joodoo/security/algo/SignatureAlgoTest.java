package indi.joynic.joodoo.security.algo;

import indi.joynic.joodoo.security.keystore.algo.SignatureAlgo;
import org.testng.Assert;

import java.util.Optional;

public class SignatureAlgoTest {

    @org.testng.annotations.Test
    public void testGetAlgo() {
        SignatureAlgo signatureAlgo = SignatureAlgo.SHA1WITHRSA;

        Assert.assertEquals(signatureAlgo.getAlgo(), "SHA1withRSA");
    }

    @org.testng.annotations.Test
    public void testGetSignatureAlgo() {
        String algo = "SHA1withRSA";

        Optional<SignatureAlgo> find = SignatureAlgo.getSignatureAlgo(algo);
        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get(), SignatureAlgo.SHA1WITHRSA);
    }
}