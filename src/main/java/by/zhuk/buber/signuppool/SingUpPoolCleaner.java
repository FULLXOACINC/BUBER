package by.zhuk.buber.signuppool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class SingUpPoolCleaner implements Runnable {
    private static Logger logger = LogManager.getLogger(SingUpPoolCleaner.class);
    private int signUpTime;

    public SingUpPoolCleaner(int signUpTime) {
        this.signUpTime = signUpTime;
    }

    @Override
    public void run() {

        while (true) {
            try {
                TimeUnit.SECONDS.sleep(signUpTime);
                ConcurrentHashMap<String, SignUpUserInfo> signUpMap = SignUpUserPool.getInstance().takeSignUpMap();
                signUpMap.values().removeIf((SignUpUserInfo info) -> {
                    int currentSecond = LocalTime.now().getSecond();
                    int signUpSecond = info.getTime().getSecond();
                    return Math.abs(currentSecond - signUpSecond) > signUpTime;
                });
                logger.log(Level.INFO, "Clear sing up pool");
            } catch (InterruptedException e) {
                logger.catching(e);
                break;
            }
        }


    }
}