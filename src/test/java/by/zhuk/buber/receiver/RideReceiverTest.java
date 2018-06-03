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
            rideReceiver.createRide("san91130324@gmail.com", "buberteam@gmail.com", 0.0f, 0.0f, 0.0f, 0.0f, new BigDecimal("10.00"));
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception " + e.getMessage());
        }

    }

    @Test
    public void IsRidePositiveExist() {
        RideReceiver rideReceiver = new RideReceiver();

        try {
            boolean result = rideReceiver.isRideExist("san91130324@gmail.com");
            Assert.assertFalse(result);
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception " + e.getMessage());
        }
    }

    @Test
    public void testSendUserMail() {
        RideReceiver rideReceiver = new RideReceiver();
        rideReceiver.sendUserMail("buberteam@gmail.com", "1", "1", "1", "ru","1","ru");

    }

    @Test
    public void testSendDriverMail() {
        RideReceiver rideReceiver = new RideReceiver();
        rideReceiver.sendDriverMail("buberteam@gmail.com", "1", "1", "1", "ru");

    }

}