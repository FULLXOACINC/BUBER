package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.testng.Assert.*;

public class UserReceiverTest {

    @Test
    public void testFindUserDiscount(){
        UserReceiver userReceiver = new UserReceiver();

        try {
            Optional<Float> discountOptional= userReceiver.findUserDiscount("san91130324@gmail.com");
            Assert.assertEquals(discountOptional.get(),0.99f,0.001);
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception");
        }
    }
}