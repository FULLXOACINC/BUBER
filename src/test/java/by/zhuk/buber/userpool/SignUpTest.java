package by.zhuk.buber.userpool;

import by.zhuk.buber.model.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class SignUpTest {

    @Test
    public void userCorrectSingUpTest() {
        Thread cleaner = new Thread(new PoolCleaner(100));
        cleaner.setDaemon(true);
        cleaner.start();
        SignUpUserPool pool = SignUpUserPool.getInstance();
        pool.putInfo("1", new UserPoolInfo(new User(), LocalTime.now()));

        UserPoolInfo info = pool.find("1");
        Assert.assertNotNull(info);
        cleaner.interrupt();

    }

    @Test
    public void singUpTimeOutTest() {
        SignUpUserPool pool = SignUpUserPool.getInstance();
        pool.putInfo("1", new UserPoolInfo(new User(), LocalTime.now()));
        Thread cleaner = new Thread(new PoolCleaner(2));
        cleaner.setDaemon(true);
        cleaner.start();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            fail("Test does not throws InterruptedException");
        }
        UserPoolInfo info = pool.find("1");
        Assert.assertNull(info);
        cleaner.interrupt();

    }
}