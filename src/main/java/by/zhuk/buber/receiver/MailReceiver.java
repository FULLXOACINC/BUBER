package by.zhuk.buber.receiver;

import by.zhuk.buber.model.Mail;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class include method to interaction with things connection with mail business logic
 */
class MailReceiver {
    private static Logger logger = LogManager.getLogger(MailReceiver.class);
    private static final String HEAD = "head";
    private static final String CONTENT = "content";
    private static final String PROPERTIES_EXTENSION = ".properties";


    /**
     * @param bundleName  bundle name
     * @param lang use language at bundle
     * @param defaultHead default mail head
     * @param defaultContent default mail content
     * @return mail created from a bundleName if it exists or from defaultHead and defaultContent if there is no bundle, use language
     */
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