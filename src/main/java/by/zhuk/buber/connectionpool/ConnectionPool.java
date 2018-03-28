package by.zhuk.buber.connectionpool;


import by.zhuk.buber.exeption.RepositoryException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private static Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private static BlockingQueue<ProxyConnection> connectionQueue;
    private static int poolSize;

    private ConnectionPool() {
        if (instanceCreated.get()) {
            logger.log(Level.FATAL, "Tried to clone connection pool with reflection api");
            throw new RuntimeException("Tried to clone connection pool with reflection api");
        }
    }

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();

            if (!instanceCreated.get()) {
                instance = new ConnectionPool();
                instance.initPool();
                instanceCreated.set(true);
            }
            lock.unlock();

        }
        return instance;
    }

    public ProxyConnection takeConnection() {
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = connectionQueue.take();
            logger.log(Level.INFO, "Take connection from connection pool");
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
        }
        return proxyConnection;
    }

    public void destroyConnections() {
        for (int index = 0; index < poolSize; index++) {
            ProxyConnection proxyConnection = null;
            try {
                proxyConnection = connectionQueue.take();
            } catch (InterruptedException e) {
                logger.catching(e);
            } finally {
                if (proxyConnection != null) {
                    try {
                        proxyConnection.closeConnection();
                    } catch (RepositoryException e) {
                        logger.catching(e);
                    }
                }
            }
        }

        logger.log(Level.INFO, "Close connections");

        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
            logger.log(Level.INFO, "Deregister drivers");
        } catch (SQLException e) {
            logger.log(Level.WARN, e);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        logger.log(Level.ERROR, "Tried to clone connection pool");
        throw new CloneNotSupportedException("Tried to clone connection pool");
    }

    void releaseConnection(ProxyConnection proxyConnection) {
        try {
            connectionQueue.put(proxyConnection);
            logger.log(Level.INFO, "Put connection to connection pool");
        } catch (InterruptedException e) {
            logger.log(Level.WARN, e);
        }
    }

    private static void initPool() {
        final String DATABASE_PROPERTY = "database";
        final String DATABASE_URL = "db.url";
        final String DATABASE_USER = "db.user";
        final String DATABASE_PASSWORD = "db.password";
        final String DATABASE_CHARACTER_ENCODING = "db.characterEncoding";
        final String DATABASE_USE_UNICODE = "db.useUnicode";
        final String DATABASE_POOL_SIZE = "db.poolSize";

        ResourceBundle resourceBundle;
        final String URL;
        final String USER;
        final String PASSWORD;
        final String CHARACTER_ENCODING;
        final String USE_UNICODE;
        final String POOL_SIZE;
        try {
            resourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTY);
            URL = resourceBundle.getString(DATABASE_URL);
            USER = resourceBundle.getString(DATABASE_USER);
            PASSWORD = resourceBundle.getString(DATABASE_PASSWORD);
            CHARACTER_ENCODING = resourceBundle.getString(DATABASE_CHARACTER_ENCODING);
            USE_UNICODE = resourceBundle.getString(DATABASE_USE_UNICODE);
            POOL_SIZE = resourceBundle.getString(DATABASE_POOL_SIZE);
        } catch (MissingResourceException e) {
            logger.log(Level.FATAL, "Hasn't found bundle for database");
            throw new RuntimeException("Hasn't found bundle for database");
        }

        Properties properties = new Properties();
        properties.put("user", USER);
        properties.put("password", PASSWORD);
        properties.put("characterEncoding", CHARACTER_ENCODING);
        properties.put("useUnicode", USE_UNICODE);

        poolSize = Integer.parseInt(POOL_SIZE);
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            logger.log(Level.INFO, "Register MySQL JDBC driver");
        } catch (SQLException e) {
            logger.log(Level.FATAL, "Mysql jdbc driver hasn't loaded", e);
            throw new RuntimeException("Mysql jdbc driver hasn't loaded", e);
        }
        for (int index = 0; index < poolSize; index++) {
            Connection connection;
            try {
                connection = DriverManager.getConnection(URL, properties);
            } catch (SQLException e) {
                logger.log(Level.FATAL, "Hasn't found connection with database");
                throw new RuntimeException("Hasn't found connection with database");
            }

            ProxyConnection proxyConnection = new ProxyConnection(connection);
            try {
                connectionQueue.put(proxyConnection);
            } catch (InterruptedException e) {
                logger.log(Level.WARN, e);
            }
        }

        logger.log(Level.INFO, "Init connection pool");
    }
}