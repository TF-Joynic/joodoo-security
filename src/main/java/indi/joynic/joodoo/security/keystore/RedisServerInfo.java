package indi.joynic.joodoo.security.keystore;

public class RedisServerInfo {
    private String host;
    private int port;
    private String hashName;
    private String keyName;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHashName() {
        return hashName;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public static RedisServerInfo valueOf(String host, int port, String hashName, String keyName) {
        RedisServerInfo redisServerInfo = new RedisServerInfo();

        redisServerInfo.setHost(host);
        redisServerInfo.setPort(port);
        redisServerInfo.setHashName(hashName);
        redisServerInfo.setKeyName(keyName);

        return redisServerInfo;
    }
}