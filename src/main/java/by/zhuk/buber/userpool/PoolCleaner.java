package by.zhuk.buber.userpool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
/**
 * Class that clean SignUpUserPool and RestorePasswordUserPool if element in it timeout
 * @see RestorePasswordUserPool,Runnable,SignUpUserPool
 */
public class PoolCleaner implements Runnable {
    private static Logger logger = LogManager.getLogger(PoolCleaner.class);
    private int timeOut;

    public PoolCleaner(int timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public void run() {

        while (true) {
            try {
                TimeUnit.SECONDS.sleep(timeOut);
                ConcurrentHashMap<String, UserPoolInfo> signUpMap = SignUpUserPool.getInstance().takeSignUpMap();
                signUpMap.values().removeIf((UserPoolInfo info) -> {
                    int currentSecond = LocalTime.now().getSecond();
                    int signUpSecond = info.getTime().getSecond();
                    return Math.abs(currentSecond - signUpSecond) > timeOut;
                });
                logger.log(Level.INFO, "Clear sing up pool");
                ConcurrentHashMap<String, UserPoolInfo> restorePasswordMap = RestorePasswordUserPool.getInstance().takeRestorePasswordMap();
                restorePasswordMap.values().removeIf((UserPoolInfo info) -> {
                    int currentSecond = LocalTime.now().getSecond();
                    int signUpSecond = info.getTime().getSecond();
                    return Math.abs(currentSecond - signUpSecond) > timeOut;
                });
                logger.log(Level.INFO, "Clear restorePassword pool");
            } catch (InterruptedException e) {
                logger.catching(e);
                break;
            }
        }


    }
}