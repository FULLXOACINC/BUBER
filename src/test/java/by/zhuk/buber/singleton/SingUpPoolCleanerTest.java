package by.zhuk.buber.singleton;

import by.zhuk.buber.model.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class SingUpPoolCleanerTest {

    @Test
    public void userCorrectSingUpTest() {
        Thread cleaner = new Thread(new SingUpPoolCleaner());
        cleaner.setDaemon(true);
        cleaner.start();
        SignUpUserPool pool = SignUpUserPool.getInstance();
        pool.putInfo("1", new SignUpUserInfo(new User(), LocalTime.now()));

        SignUpUserInfo info=pool.find("1");
        Assert.assertNotNull(info);
        cleaner.interrupt();

    }

    @Test
    public void singUpTimeOutTest() {
        SignUpUserPool pool = SignUpUserPool.getInstance();
        pool.putInfo("1", new SignUpUserInfo(new User(), LocalTime.now()));
        Thread cleaner = new Thread(new SingUpPoolCleaner());
        cleaner.setDaemon(true);
        cleaner.start();
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            fail("Test does not throws InterruptedException");
        }
        SignUpUserInfo info=pool.find("1");
        Assert.assertNull(info);
        cleaner.interrupt();

    }
}