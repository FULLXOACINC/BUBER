package by.zhuk.buber.connectionpool;


import by.zhuk.buber.exeption.RepositoryException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
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
            logger.log(Level.FATAL, "Tried to create connection pool with reflection api");
            throw new RuntimeException("Tried to create connection pool with reflection api");
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

    private void initPool() {
        final String DATABASE_PROPERTY = "database.properties";
        final String DATABASE_URL = "db.url";
        final String DATABASE_USER = "db.user";
        final String DATABASE_PASSWORD = "db.password";
        final String DATABASE_CHARACTER_ENCODING = "db.characterEncoding";
        final String DATABASE_USE_UNICODE = "db.useUnicode";
        final String DATABASE_POOL_SIZE = "db.poolSize";

        Properties dbProperties = new Properties();
        try {

            InputStream is = getClass().getClassLoader().getResourceAsStream(DATABASE_PROPERTY);
            dbProperties.load(is);

        } catch (IOException e) {
            logger.catching(e);
            throw new RuntimeException("Hasn't found database.properties");
        }

        Properties properties = new Properties();
        properties.put("user", dbProperties.getProperty(DATABASE_USER));
        properties.put("password", dbProperties.getProperty(DATABASE_PASSWORD));
        properties.put("characterEncoding", dbProperties.getProperty(DATABASE_CHARACTER_ENCODING));
        properties.put("useUnicode", dbProperties.getProperty(DATABASE_USE_UNICODE));

        poolSize = Integer.parseInt(dbProperties.getProperty(DATABASE_POOL_SIZE));
        connectionQueue = new LinkedBlockingDeque<>(poolSize);
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            logger.log(Level.INFO, "Register MySQL JDBC driver");
        } catch (SQLException e) {
            logger.log(Level.FATAL, "Mysql jdbc driver hasn't loaded", e);
            throw new RuntimeException("Mysql jdbc driver hasn't loaded", e);
        }
        for (int index = 0; index < poolSize; index++) {
            Connection connection;
            try {
                connection = DriverManager.getConnection(dbProperties.getProperty(DATABASE_URL), properties);
            } catch (SQLException e) {
                logger.catching(e);
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