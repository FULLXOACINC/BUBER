package by.zhuk.buber.userpool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class RestorePasswordUserPool {
    private static Logger logger = LogManager.getLogger(RestorePasswordUserPool.class);
    private static RestorePasswordUserPool instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private static ConcurrentHashMap<String, UserPoolInfo> restorePasswordMap;

    private RestorePasswordUserPool() {
        if (instanceCreated.get()) {
            logger.log(Level.FATAL, "Tried to create restorePasswordUserPool pool with reflection api");
            throw new RuntimeException("Tried to create restorePasswordUserPool pool with reflection api");
        }
    }

    public static RestorePasswordUserPool getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();

            if (!instanceCreated.get()) {
                instance = new RestorePasswordUserPool();
                restorePasswordMap = new ConcurrentHashMap<>();
                instanceCreated.set(true);
            }
            lock.unlock();

        }
        return instance;
    }

    public void putInfo(String hash, UserPoolInfo info) {
        restorePasswordMap.put(hash, info);
    }

    public void removeInfo(String hash) {
        restorePasswordMap.remove(hash);
    }

    public UserPoolInfo find(String hash) {
        return restorePasswordMap.get(hash);
    }

    public ConcurrentHashMap<String, UserPoolInfo> takeRestorePasswordMap() {
        return restorePasswordMap;
    }
}