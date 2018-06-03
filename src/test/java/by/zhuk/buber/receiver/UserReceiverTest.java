package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Optional;

public class UserReceiverTest {

    @Test
    public void testFindUserDiscount() {
        UserReceiver userReceiver = new UserReceiver();

        try {
            Optional<BigDecimal> discountOptional = userReceiver.findUserDiscount("san91130324@gmail.com");
            Assert.assertEquals(discountOptional.get(), new BigDecimal("0.00"));
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception");
        }
    }
}