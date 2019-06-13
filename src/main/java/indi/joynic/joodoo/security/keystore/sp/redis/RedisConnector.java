package indi.joynic.joodoo.security.keystore.sp.redis;

import indi.joynic.joodoo.security.keystore.sp.redis.response.handler.*;
import indi.joynic.joodoo.security.net.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Redis Connector
 *
 * Encapsulation of the Socket connected to Redis Server.
 *
 * @author Terrance Fung
 */
public final class RedisConnector implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(RedisConnector.class);

    private String host;
    private int    port;
    private long connectTimeoutMillis;
    private int    soTimeoutMillis;

    private volatile boolean connected = false;

    private Socket socket;

    private static ResponseHandler responseHandler = new StatusResponseHandlerImpl();

    public RedisConnector(String host, int port) {
        this(host, port, 2500L, 1000);
    }

    public RedisConnector(String host, int port, long connectTimeoutMillis, int soTimeoutMillis) {
        this.host = host;
        this.port = port;
        this.connectTimeoutMillis = connectTimeoutMillis;
        this.soTimeoutMillis = soTimeoutMillis;
    }

    public void init() {
        WrongTypeErrorResponseHandlerImpl wrongTypeErrorResponseHandler = new WrongTypeErrorResponseHandlerImpl();

        CommonErrorResponseHandlerImpl commonErrorResponseHandler = new CommonErrorResponseHandlerImpl();
        commonErrorResponseHandler.setNext(wrongTypeErrorResponseHandler);

        IntegerResponseHandlerImpl integerResponseHandler = new IntegerResponseHandlerImpl();
        integerResponseHandler.setNext(commonErrorResponseHandler);

        responseHandler.setNext(integerResponseHandler);
    }

    public void connect() {
        try {
            Socket socket = new Socket();

            InetAddress inetAddress = Inet4Address.getByAddress(AddrUtil.inet4AddrTextToByteArray(host));
            InetSocketAddress address = new InetSocketAddress(inetAddress, port);

            socket.setSoTimeout(soTimeoutMillis);

            synchronized (InetSocketAddress.class) {
                // do connect to server!
                long connStartTime = System.currentTimeMillis();
                socket.connect(address);

                do {
                    connected = !socket.isClosed() && socket.isConnected();
                    if (connected) {
                        this.socket = socket;

                        break;
                    }
                } while ((System.currentTimeMillis() - connStartTime) <= connectTimeoutMillis);

                if (!connected) {
                    throw new RedisConnectTimedoutException();
                }
            }
        } catch (IOException e) {
            logger.error("connect to host: {}, port: {} err: {}", host, port, e.getMessage());
        }

        logger.info("connected: {}", connected);
    }

    /**
     * concrete command text string and send it to redis server via socket
     *
     * @link http://redisdoc.com/topic/protocol.html
     *
     * @param redisCmd
     * @param args
     */
    public Object executeCmd(RedisCommand redisCmd, Object ...args) {
        if (null == redisCmd || null == args || args.length == 0) {
            throw new InvalidRedisCommandException();
        }

        OutputStream outputStream = sendDataToServer(redisCmd, args);
        InputStream inputStream = receiveDataFromServer();

        Object result = responseHandler.handle(readFromInputStream(inputStream));

        closeSession(outputStream, inputStream);

        return result;
    }

    public OutputStream sendDataToServer(RedisCommand redisCmd, Object ...args) {
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            if (null != outputStream) {
                RedisRequestProtocolTemplateImpl template = new RedisRequestProtocolTemplateImpl(redisCmd, args);
                outputStream.write(template.commandToByteArray());

                outputStream.flush();
            }
        } catch (IOException e) {
            logger.error("get socket outputStream err", e);
        } finally {
            try {
                socket.shutdownOutput();
            } catch (IOException e) {
                logger.error("error occurred when shutdown output stream!", e);
            }
        }

        return outputStream;
    }

    public InputStream receiveDataFromServer() {
        try {
            return socket.getInputStream();
        } catch (IOException e) {

        }

        return null;
    }

    public void closeSession(OutputStream outputStream, InputStream inputStream) {
        try {
            socket.shutdownInput();
        } catch (IOException e) {
            logger.error("error occurred when shutdown socket input stream!", e);
        }

        if (null != outputStream) {
            try {
                outputStream.close();
            } catch (IOException x) {
                logger.error("close outputStream err", x);
            }
        }

        if (null != inputStream) {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("close inputStream err", e);
            }
        }
    }

    public String readFromInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        byte[] buffer = new byte[1024];

        try {
            if (null != inputStream) {
                // 等待服务端有数据返回
                int availableByteCount = 0;
                while (availableByteCount == 0) {
                    availableByteCount = inputStream.available();
                }

                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                int readByteCount;
                while (-1 != (readByteCount = bufferedInputStream.read(buffer))) {
                    stringBuilder.append(new String(buffer, 0, readByteCount));
                }
            }
        } catch (IOException e) {
            logger.error("receive data from Server encounter io exception", e);
        }

        return stringBuilder.toString();
    }

    public void close() {
        try {
            this.socket.close();
            logger.info("conn closed!");
        } catch (IOException e) {
            logger.error("close socket encounter an error!", e);
        }
    }
}