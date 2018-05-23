package by.zhuk.buber.receiver;

import org.testng.annotations.Test;

public class SignUpReceiverTest {
    private SignUpReceiver signUpReceiver = new SignUpReceiver();

    @Test
    public void testSaveUser() throws Exception {
    }

    @Test
    public void testSendAcceptMail() throws Exception {
        signUpReceiver.sendAcceptMail("san91130324@gmail.com", "Alex", "Zhuk", "test", "1998-09-09", "+375291713227", "ru");
    }

    @Test
    public void testSaveDriver() throws Exception {
    }
}