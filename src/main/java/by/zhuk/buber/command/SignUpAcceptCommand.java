package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.receiver.SignUpReceiver;
import by.zhuk.buber.userpool.SignUpUserPool;
import by.zhuk.buber.userpool.UserPoolInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * Class include info how to react to a SignUpAccept
 */
public class SignUpAcceptCommand implements Command {
    private static Logger logger = LogManager.getLogger(SignUpAcceptCommand.class);
    private static final String HASH_PARAM = "hash";
    /**
     * Expected parameters:
     * 1)hash
     */
    @Override
    public Router execute(HttpServletRequest request) {
        String hash = request.getParameter(HASH_PARAM);
        SignUpUserPool pool = SignUpUserPool.getInstance();
        UserPoolInfo info = pool.find(hash);
        if (info == null) {
            return new Router(TransitionType.REDIRECT, PagesConstant.SIGN_IN_PAGE);
        }
        SignUpReceiver receiver = new SignUpReceiver();
        try {
            User user = info.getUser();
            if (user == null) {
                return new Router(TransitionType.REDIRECT, PagesConstant.SIGN_IN_PAGE);
            }
            receiver.saveUser(user.getLogin(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getType(), user.getBalance(), user.getBirthDay(), user.getPhoneNumber());

            HttpSession session = request.getSession();
            session.setAttribute(UserConstant.LOGIN, info.getUser().getLogin());
            session.setAttribute(UserConstant.TYPE, UserType.USER.name());
            pool.removeInfo(hash);
        } catch (ReceiverException e) {
            logger.catching(e);
            return new Router(TransitionType.REDIRECT, PagesConstant.ERROR_PAGE);
        }
        return new Router(TransitionType.REDIRECT, PagesConstant.WELCOME_PAGE);
    }

}