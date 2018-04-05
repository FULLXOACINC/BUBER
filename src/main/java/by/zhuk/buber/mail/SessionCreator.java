package by.zhuk.buber.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class SessionCreator {
    private String username;
    private String userPassword;
    private Properties sessionProperties;

    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String MAIL_USER_NAME = "mail.user.name";
    private static final String MAIL_USER_PASSWORD = "mail.user.password";

    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String SMTP = "smtp";
    private static final String MAIL_HOST = "mail.host";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
    private static final String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";
    private static final String MAIL_SMTP_SOCKET_FACTORY_FALLBACK = "mail.smtp.socketFactory.fallback";
    private static final String MAIL_SMTP_QUITWAIT = "mail.smtp.socketFactory.fallback";

    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String SSL_SOCKET_FACTORY_CLASS = "javax.net.ssl.SSLSocketFactory";

    SessionCreator(Properties configProperties) {
        String smtpHost = configProperties.getProperty(MAIL_SMTP_HOST);
        String smtpPort = configProperties.getProperty(MAIL_SMTP_PORT);
        username = configProperties.getProperty(MAIL_USER_NAME);
        userPassword = configProperties.getProperty(MAIL_USER_PASSWORD);

        sessionProperties = new Properties();
        sessionProperties.setProperty(MAIL_TRANSPORT_PROTOCOL, SMTP);
        sessionProperties.setProperty(MAIL_HOST, smtpHost);
        sessionProperties.put(MAIL_SMTP_AUTH, TRUE);
        sessionProperties.put(MAIL_SMTP_PORT, smtpPort);
        sessionProperties.put(MAIL_SMTP_SOCKET_FACTORY_PORT, smtpPort);
        sessionProperties.put(MAIL_SMTP_SOCKET_FACTORY_CLASS, SSL_SOCKET_FACTORY_CLASS);
        sessionProperties.put(MAIL_SMTP_SOCKET_FACTORY_FALLBACK, FALSE);
        sessionProperties.setProperty(MAIL_SMTP_QUITWAIT, FALSE);
    }

    public Session createSession() {
        return Session.getDefaultInstance(sessionProperties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, userPassword);
            }
        });
    }
}
