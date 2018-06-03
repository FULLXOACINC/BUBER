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
    public void testCreateRide() throws Exception {
    }

    @Test
    public void testIsRideExist() throws Exception {
    }

    @Test
    public void testSendUserMail() throws Exception {
    }

    @Test
    public void testSendDriverMail() throws Exception {
    }

    @Test
    public void testFindCurrentUserRide() throws Exception {
    }

    @Test
    public void testPassengerAcceptStart() throws Exception {
    }

    @Test
    public void testPassengerAcceptEnd() throws Exception {
    }

    @Test
    public void testFindDriverLoginByRide() throws Exception {
    }

    @Test
    public void testPassengerRefuse() throws Exception {
    }

    @Test
    public void testSendRefuseUserMail() throws Exception {
    }

    @Test
    public void testSendRefuseDriverMail() throws Exception {
    }

    @Test
    public void testFindCurrentDriverRide() throws Exception {
    }

    @Test
    public void testDriverAcceptStart() throws Exception {
    }

    @Test
    public void testDriverAcceptEnd() throws Exception {
    }

    @Test
    public void testFindPassengerLoginByRide() throws Exception {
    }

    @Test
    public void testDriverRefuse() throws Exception {
    }

    @Test
    public void testFindDriverRideHistory() throws Exception {
    }

    @Test
    public void testFindUserRideHistory(){
    }
}