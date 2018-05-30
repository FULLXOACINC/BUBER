package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;

public class UserReceiverTest {

    @Test
    public void testFindUserDiscount() {
        UserReceiver userReceiver = new UserReceiver();

        try {
            Optional<Float> discountOptional = userReceiver.findUserDiscount("san91130324@gmail.com");
            Assert.assertEquals(discountOptional.get(), 0.99f, 0.001);
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception");
        }
    }
}