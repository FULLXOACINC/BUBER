package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.oauth.OAuth;
import by.zhuk.buber.oauth.OAuthFactory;
import by.zhuk.buber.receiver.OAuthReceiver;
import by.zhuk.buber.receiver.SignInReceiver;
import by.zhuk.buber.validator.SignInValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class OAuthAcceptCommand implements Command {
    private static Logger logger = LogManager.getLogger(OAuthAcceptCommand.class);
    private static final String LOGIN_TYPE = "loginType";
    private static final String ERROR_PARAM = "error";
    private static final String CODE_PARAM = "code";
    private static String SIGN_IN_EXIST_ERROR = "signInExistError";

    @Override
    public Router execute(HttpServletRequest request) {
        if (SignInValidator.isAuthorization(request.getSession())) {
            return new Router(TransitionType.REDIRECT, PagesConstant.WELCOME_PAGE);
        }

        String loginType = (String) request.getSession().getAttribute(LOGIN_TYPE);
        Optional<OAuth> oAuthOptional;
        oAuthOptional = OAuthFactory.findOAuth(loginType);

        if (!oAuthOptional.isPresent()) {
            logger.log(Level.WARN, "Unknown command");
            return new Router(TransitionType.REDIRECT, PagesConstant.LOGIN_PAGE);
        }

        OAuth oAuth = oAuthOptional.get();

        if (oAuth.hasError(request.getParameter(ERROR_PARAM))) {
            return new Router(TransitionType.REDIRECT, PagesConstant.LOGIN_PAGE);
        } else {

            OAuthReceiver oAuthReceiver = new OAuthReceiver();

            try {
                String email = oAuthReceiver.findEmail(oAuth, request.getParameter(CODE_PARAM));
                SignInReceiver signInReceiver = new SignInReceiver();
                if (signInReceiver.isLoginExist(email)) {
                    request.getSession().setAttribute(UserConstant.LOGIN, email);
                    request.getSession().setAttribute(UserConstant.TYPE, UserType.USER);
                } else {
                    request.setAttribute(SIGN_IN_EXIST_ERROR, true);
                    return new Router(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
                }

            } catch (ReceiverException e) {
                //TODO error page
                logger.catching(e);
                new Router(TransitionType.REDIRECT, PagesConstant.LOGIN_PAGE);
            }

            return new Router(TransitionType.REDIRECT, PagesConstant.WELCOME_PAGE);

        }
    }

}
