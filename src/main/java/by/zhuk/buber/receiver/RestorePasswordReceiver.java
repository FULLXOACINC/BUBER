package by.zhuk.buber.receiver;

import by.zhuk.buber.mail.MailProperty;
import by.zhuk.buber.mail.MailThread;
import by.zhuk.buber.model.Mail;
import by.zhuk.buber.model.User;
import by.zhuk.buber.userpool.RestorePasswordUserPool;
import by.zhuk.buber.userpool.UserPoolInfo;

import java.time.LocalTime;
import java.util.Formatter;

/**
 * Class to send restore password mail
 */
public class RestorePasswordReceiver {
    private static final String RESTORE_PASSWORD_MAIL_BUNDLE = "properties/restorePasswordMailContent";
    private static final String RESTORE_PASSWORD_DEFAULT_CONTENT = "To confirm the password recovery, please follow the link.<br/><a href=\"http://localhost:8080/controller?command=restore-password-accept&hash=%s\">Restore password</a>";
    private static final String RESTORE_PASSWORD_DEFAULT_HEAD = "BUBER restore password";

    /**
     * Method to send restore password mail
     *
     * @see MailReceiver,Mail,MailThread,RestorePasswordUserPool
     */
    public void sendRestorePasswordMail(String login, String password, String lang) {
        String hash = String.valueOf(login.concat(password).hashCode());

        MailReceiver mailReceiver = new MailReceiver();
        Mail mail = mailReceiver.createMailFromBundle(RESTORE_PASSWORD_MAIL_BUNDLE, lang, RESTORE_PASSWORD_DEFAULT_HEAD, RESTORE_PASSWORD_DEFAULT_CONTENT);
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        formatter.format(mail.getContent(), hash);


        MailThread thread = new MailThread(login, mail.getHead(), stringBuilder.toString(), MailProperty.getInstance().getProperties());
        thread.start();
        RestorePasswordUserPool pool = RestorePasswordUserPool.getInstance();
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        UserPoolInfo info = new UserPoolInfo(user, LocalTime.now());
        pool.putInfo(hash, info);
    }
}