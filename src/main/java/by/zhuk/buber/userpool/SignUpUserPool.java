package by.zhuk.buber.userpool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class SignUpUserPool {
    private static Logger logger = LogManager.getLogger(SignUpUserPool.class);
    private static SignUpUserPool instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private static ConcurrentHashMap<String, UserPoolInfo> signUpMap;

    private SignUpUserPool() {
        if (instanceCreated.get()) {
            logger.log(Level.FATAL, "Tried to create signUpUser pool with reflection api");
            throw new RuntimeException("Tried to create signUpUser pool with reflection api");
        }
    }

    public static SignUpUserPool getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();

            if (!instanceCreated.get()) {
                instance = new SignUpUserPool();
                signUpMap = new ConcurrentHashMap<>();
                instanceCreated.set(true);
            }
            lock.unlock();

        }
        return instance;
    }

    public void putInfo(String hash, UserPoolInfo info) {
        signUpMap.put(hash, info);
    }

    public void removeInfo(String hash) {
        signUpMap.remove(hash);
    }

    public UserPoolInfo find(String hash) {
        return signUpMap.get(hash);
    }

    public ConcurrentHashMap<String, UserPoolInfo> takeSignUpMap() {
        return signUpMap;
    }
}