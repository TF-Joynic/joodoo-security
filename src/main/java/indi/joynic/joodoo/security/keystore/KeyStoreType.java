package indi.joynic.joodoo.security.keystore;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Terrance Fung
 */
public enum KeyStoreType {
    JKS("JKS"),

    REDIS("REDIS")
    ;

    private String type;

    KeyStoreType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Optional<KeyStoreType> getKeyStoreType(String type) {
        return Arrays.stream(values()).filter(keyStoreType1 -> keyStoreType1.getType().equals(type)).findFirst();
    }
}