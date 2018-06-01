package by.zhuk.buber.command;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.oauth.AbstractOAuth;
import by.zhuk.buber.oauth.OAuthFactory;
import by.zhuk.buber.receiver.OAuthReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class OAuthAcceptCommand implements Command {
    private static Logger logger = LogManager.getLogger(OAuthAcceptCommand.class);
    private static final String LOGIN_TYPE = "loginType";
    private static final String CODE_PARAM = "code";

    @Override
    public Router execute(HttpServletRequest request) {
        if (UserValidator.isAuthorization(request.getSession())) {
            return new Router(TransitionType.REDIRECT, PagesConstant.WELCOME_PAGE);
        }

        String loginType = (String) request.getSession().getAttribute(LOGIN_TYPE);
        Optional<AbstractOAuth> oAuthOptional;
        oAuthOptional = OAuthFactory.findOAuth(loginType);

        if (!oAuthOptional.isPresent()) {
            logger.log(Level.WARN, "Unknown oAuth type");
            return new Router(TransitionType.REDIRECT, PagesConstant.SIGN_IN_PAGE);
        }

        AbstractOAuth oAuth = oAuthOptional.get();

        if (oAuth.isHasError(request.getParameter(ErrorConstant.ERROR))) {
            return new Router(TransitionType.REDIRECT, PagesConstant.SIGN_IN_PAGE);
        } else {

            OAuthReceiver oAuthReceiver = new OAuthReceiver();

            try {
                String email = oAuthReceiver.findEmail(oAuth, request.getParameter(CODE_PARAM));
                UserReceiver userReceiver = new UserReceiver();
                Optional<User> optionalUser = userReceiver.findUserByLogin(email);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    request.getSession().setAttribute(UserConstant.LOGIN, email);
                    request.getSession().setAttribute(UserConstant.TYPE, user.getType().name());
                } else {
                    return new Router(TransitionType.REDIRECT, PagesConstant.SIGN_IN_PAGE);
                }

            } catch (ReceiverException e) {
                logger.catching(e);
                return new Router(TransitionType.REDIRECT, PagesConstant.ERROR_PAGE);
            }

            return new Router(TransitionType.REDIRECT, PagesConstant.WELCOME_PAGE);

        }
    }

}
