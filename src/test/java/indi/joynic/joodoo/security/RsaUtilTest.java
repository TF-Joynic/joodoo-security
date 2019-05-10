package indi.joynic.joodoo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RsaUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(RsaUtilTest.class);

    @Test
    public void testVerify() {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNGyBAjl6Dg64JuYhnp1Bkco7k"
                + "Ni1VwUDU9rA93qCVQgaz26SPyH08TiEeSir3eBvdt8VMt9Nlf/ersUyDjfVVHhnN"
                + "Klf2gQvu1SyvfPSteKH3ngNt6FsqJdzB+QSvh3/erK3AppekAas1tRPYSSylo1bA"
                + "vKvXRGDrua8AQouVKwIDAQAB";


        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM0bIECOXoODrgm5"
                + "iGenUGRyjuQ2LVXBQNT2sD3eoJVCBrPbpI/IfTxOIR5KKvd4G923xUy302V/96ux"
                + "TION9VUeGc0qV/aBC+7VLK989K14ofeeA23oWyol3MH5BK+Hf96srcCml6QBqzW1"
                + "E9hJLKWjVsC8q9dEYOu5rwBCi5UrAgMBAAECgYAvPEEIPQFtPkU1fiH45SJxj6Ry"
                + "0DtOlYwNv6ZFCWFoHmWWxE24w058Ne4QoLoWdfXM8QaiLW+k33y1cMaTQX0vLHUf"
                + "s764mIt9XJWBHbAbsR8jVsHqAKq5d7j+9VPe/tVYNCqirn2cqXgBbnSzekVPAcNC"
                + "yDlnM/Pdatx/oCxTWQJBAO8C8y08S8v0qxtIilEPcy1f1PfHLh5fb6rHpIOv2sDs"
                + "g/rqrc4NtV92tt59AdoLX+VFKsfnKtGjCB7G2jbMiKUCQQDbrzshJONas5mvkFxG"
                + "sNgZlLrlyStf3BwjNsoAkj5IAMoOEboIhJYx6JgrwCyH+3GNhTP+xbwmBhIrSNrY"
                + "GW2PAkAV6xVV3qOszoEqfVqB2ILddF5ZYqCuFF5vMOO7V9MdwhLQXdkWfldVq9NV"
                + "T+1ti4F9yvwDXQWmN+c0u1OjltopAkEAzzYhl8mriUoOlEKeRy9XJgnboBJ4DxsW"
                + "j0CGmLNYe6IC8/JUfuRQpxK1Nc82Ma1CFjKmMJFiPptLV/iIM30IkQJBAJ2ZYaQ1"
                + "igVgGo0Mwh5tq+kMX2GjodOwEhgDT973gOYHNmlTQgf9IhdIRp/TKOa20g2D+/nd"
                + "sq+gGjRttVe5pfY=";

        String secret = "attackOrder";

        System.out.println(RsaUtil.sign(privateKey, secret, UTF_8));

        String sign = RsaUtil.sign(privateKey, secret, UTF_8);
        logger.error("sign: {}", sign);

        boolean verifyResult = RsaUtil.verify(publicKey, secret, sign, UTF_8);
        logger.error("verifyResult: {}", verifyResult);
    }
}