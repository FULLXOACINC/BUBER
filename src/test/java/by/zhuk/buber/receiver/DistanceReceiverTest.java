package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.DistanceInfo;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;


public class DistanceReceiverTest {

    @Test
    public void findDistanceInfoPositiveTest() {
        DistanceReceiver receiver = new DistanceReceiver();
        Optional<DistanceInfo> optionalDistanceInfo;
        try {
            optionalDistanceInfo = receiver.findDistanceInfo("53.9500752", "27.7153194", "53.9281787", "27.6856203");

            if (optionalDistanceInfo.isPresent()) {
                DistanceInfo info = optionalDistanceInfo.get();
                Assert.assertEquals(info.getDistance(), 5946, 500);
            }
        } catch (ReceiverException e) {
            Assert.fail("Test must not throw exception " + e.getMessage());
        }

    }

    @Test
    public void findDistanceInfoEmptyTest() {
        try {
            DistanceReceiver receiver = new DistanceReceiver();
            Optional<DistanceInfo> optionalDistanceInfo = receiver.findDistanceInfo("dasfgyhjkm", "dasfgyhjkm.71dasfgyhjkm", "dasfgyhjkm.dasfgyhjkm", "dasfgyhjkm.dasfgyhjkm");
            if (optionalDistanceInfo.isPresent()) {
                Assert.fail("optionalDistanceInfo must by empty");
            }
        } catch (ReceiverException ignored) {
            Assert.fail("Test must not throw exception");
        }
    }
}