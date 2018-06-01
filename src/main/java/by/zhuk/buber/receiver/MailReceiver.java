package by.zhuk.buber.receiver;

import by.zhuk.buber.model.Mail;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

class MailReceiver {
    private static Logger logger = LogManager.getLogger(MailReceiver.class);
    private static final String HEAD = "head";
    private static final String CONTENT = "content";
    private static final String PROPERTIES_EXTENSION = ".properties";

    public Mail createMailFromBundle(String bundleName, String lang, String defaultHead, String defaultContent) {
        Locale locale = new Locale(lang);

        String propsPath = this.getClass().getClassLoader().getResource(".").getPath();
        File file = new File(propsPath, bundleName + PROPERTIES_EXTENSION);
        String head;
        String content;
        if (file.exists()) {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
            head = bundle.getString(HEAD);
            content = bundle.getString(CONTENT);
        } else {
            head = defaultHead;
            content = defaultContent;
            logger.log(Level.WARN, "bundle not found + " + bundleName);
        }
        return new Mail(head, content);
    }
}