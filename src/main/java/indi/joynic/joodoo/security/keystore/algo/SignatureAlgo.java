package indi.joynic.joodoo.security.keystore.algo;

import java.util.Arrays;
import java.util.Optional;

/**
 * Signature Algorithms
 *
 * @author Terrance Fung
 */
public enum SignatureAlgo {
    SHA1WITHDSA("SHA1withDSA"),
    SHA1WITHRSA("SHA1withRSA"),
    SHA256WITHRSA("SHA256withRSA");

    private String algo;
    SignatureAlgo(String algo) {
        this.algo = algo;
    }

    public String getAlgo() {
        return algo;
    }

    public static Optional<SignatureAlgo> getSignatureAlgo(String algo) {
        return Arrays.stream(values()).filter(signatureAlgo -> signatureAlgo.getAlgo().equals(algo)).findFirst();
    }

}