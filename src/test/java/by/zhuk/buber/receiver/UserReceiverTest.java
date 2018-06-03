package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserReceiverTest {

    @Test
    public void findUserDiscountPositiveTest() {
        UserReceiver userReceiver = new UserReceiver();
        try {
            Optional<BigDecimal> discountOptional = userReceiver.findUserDiscount("san91130324@gmail.com");
            Assert.assertEquals(discountOptional.get(), new BigDecimal("0.20"));
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception");
        }
    }

    @Test
    public void findUserByLoginPositiveTest() {
        UserReceiver userReceiver = new UserReceiver();
        try {
            Optional<User> userOptional = userReceiver.findUserByLogin("buberteam@gmail.com");
            if (!userOptional.isPresent()) {
                Assert.fail("Test mast not be empty");
            }
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception" + e.getMessage());
        }

    }

    @Test
    public void isPhoneNumberExistPositiveTest() {
        UserReceiver userReceiver = new UserReceiver();
        try {
            boolean result = userReceiver.isPhoneNumberExist("+375291713227");
            Assert.assertTrue(result);
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception" + e.getMessage());
        }
    }

    @Test
    public void isLoginExistPositiveTest() {
        UserReceiver userReceiver = new UserReceiver();
        try {
            boolean result = userReceiver.isLoginExist("buberteam@gmail.com");
            Assert.assertTrue(result);
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception" + e.getMessage());
        }
    }

    @Test
    public void isUserBalanceFullPositiveTest() {
        UserReceiver userReceiver = new UserReceiver();
        try {
            boolean result = userReceiver.isPhoneNumberExist("buberteam@gmail.com");
            Assert.assertFalse(result);
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception" + e.getMessage());
        }
    }

    @Test
    public void isBalanceNegativeOrEmptyPositiveTest() {
        UserReceiver userReceiver = new UserReceiver();
        try {
            boolean result = userReceiver.isBalanceNegativeOrEmpty("buberteam@gmail.com");
            Assert.assertFalse(result);
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception" + e.getMessage());
        }
    }

    @Test
    public void changePasswordPositiveTest() {
        UserReceiver userReceiver = new UserReceiver();
        try {
            userReceiver.changePassword("buberteam@gmail.com","1130324");

        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception" + e.getMessage());
        }
    }

    @Test
    public void findUsersByPattern() {
        UserReceiver userReceiver = new UserReceiver();
        try {
            List<User> users = userReceiver.findUsersByPattern("buberteam@gmail.com");
            Assert.assertEquals(users.size(),1);
        } catch (ReceiverException e) {
            Assert.fail("Test mast not throw Exception" + e.getMessage());
        }
    }
}