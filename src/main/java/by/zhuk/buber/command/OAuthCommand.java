package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.oauth.AbstractOAuth;
import by.zhuk.buber.oauth.OAuthFactory;
import by.zhuk.buber.validator.SignInValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class OAuthCommand implements Command {
    private static Logger logger = LogManager.getLogger(OAuthCommand.class);
    private static final String LOGIN_TYPE = "loginType";

    @Override
    public Router execute(HttpServletRequest request) {
        if (SignInValidator.isAuthorization(request.getSession())) {
            return new Router(TransitionType.REDIRECT, PagesConstant.WELCOME_PAGE);
        }

        String loginType = request.getParameter(LOGIN_TYPE);
        if (loginType == null) {
            loginType = (String) request.getSession().getAttribute(LOGIN_TYPE);
        }
        Optional<AbstractOAuth> oAuthOptional;


        oAuthOptional = OAuthFactory.findOAuth(loginType);

        if (!oAuthOptional.isPresent()) {
            logger.log(Level.WARN, "Unknown command");
            return new Router(TransitionType.REDIRECT, PagesConstant.LOGIN_PAGE);
        }

        AbstractOAuth oAuth = oAuthOptional.get();

        request.getSession().setAttribute(LOGIN_TYPE, loginType);
        return new Router(TransitionType.REDIRECT, oAuth.getAuthUrl());

    }
}
