package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Driver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class DriverReceiverTest {

    @Test
    public void testFindSuitableDriver() {
        DriverReceiver driverReceiver = new DriverReceiver();
        try {
            List<Driver> drivers = driverReceiver.findSuitableDrivers(53.904537f, 27.561535f, "test");
            Assert.assertEquals(drivers.size(), 3);
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception " + e);
        }
    }
}