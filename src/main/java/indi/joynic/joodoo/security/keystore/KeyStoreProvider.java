package indi.joynic.joodoo.security.keystore;

import java.util.Arrays;
import java.util.Optional;

/**
 * KeyStoreProvider
 *
 * You can view file: JAVA_HOME/jdk/conf/security/java.security
 *
 * config like:
 * ```
 *  security.provider.1=SUN
 * security.provider.2=SunRsaSign
 * security.provider.3=SunEC
 * security.provider.4=SunJSSE
 * security.provider.5=SunJCE
 * security.provider.6=SunJGSS
 * security.provider.7=SunSASL
 * security.provider.8=XMLDSig
 * security.provider.9=SunPCSC
 * security.provider.10=JdkLDAP
 * security.provider.11=JdkSASL
 * security.provider.12=SunMSCAPI
 * security.provider.13=SunPKCS11
 * ```
 *
 * declares providers of security
 */
public enum KeyStoreProvider {
    SUN("SUN"),
    SUNRSASIGN("SunRsaSign"),
    SUNEC("SunEC"),
    SUNJSSE("SunJSSE"),
    SUNJCE("SunJCE"),
    SUNJGSS("SunJGSS"),
    SUNSASL("SunSASL"),
    XMLDSIG("XMLDSig"),
    SUNPCSC("SunPCSC"),
    JDKLDAP("JdkLDAP"),
    JDKSASL("JdkSASL"),
    SUNMSCAPI("SunMSCAPI"),
    SUNPKCS11("SunPKCS11");

    private String code;

    KeyStoreProvider(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Optional<KeyStoreProvider> getProvider(String code) {
        return Arrays.stream(values()).filter(provider -> provider.getCode().equals(code)).findFirst();
    }
}