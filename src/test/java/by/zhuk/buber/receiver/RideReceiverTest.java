package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class RideReceiverTest {

    @Test
    public void createRidePositiveTest() {
        RideReceiver rideReceiver = new RideReceiver();

        try {
            rideReceiver.createRide("san91130324@gmail.com", "root@gmail.com", 0.0f, 0.0f, 0.0f, 0.0f, new BigDecimal("10.00"));
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception");
        }

    }
}