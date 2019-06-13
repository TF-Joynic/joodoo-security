package indi.joynic.joodoo.security.keystore.sp.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RedisConnectorTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisConnectorTest.class);

    private RedisConnector redisConnector;

    public RedisConnectorTest() {
        redisConnector = new RedisConnector("127.0.0.1", 6379, 3000L, 3000);
        redisConnector.init();
    }

    @Test
    public void testConnect() {

        redisConnector.connect();

        redisConnector.executeCmd(RedisCommand.SET, "conn", "15");
        Object result = redisConnector.executeCmd(RedisCommand.INCR, "conn");

        redisConnector.close();

        logger.warn(result.toString());
        int resultInt = (int) result;
        Assert.assertEquals(resultInt, 16);
    }

    @Test
    public void testExecuteCmd() {
        redisConnector.close();
    }
}