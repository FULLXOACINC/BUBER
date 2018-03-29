package by.zhuk.buber.filter;

import by.zhuk.buber.command.CommandType;
import by.zhuk.buber.constant.CommandConstant;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.SignInValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebFilter("/*")
public class SignInFilter implements Filter {
    private static Logger logger = LogManager.getLogger(SignInFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (isLoginRequest(request.getParameter(CommandConstant.COMMAND))) {
            filterChain.doFilter(request, response);
            return;
        }
        if (SignInValidator.isAuthorization(session)) {
            UserReceiver receiver = new UserReceiver();
            String login = (String) session.getAttribute(UserConstant.LOGIN);
            Optional<User> userOptional = Optional.empty();
            try {
                userOptional = receiver.findUserByLogin(login);
            } catch (ReceiverException e) {
                logger.catching(e);
            }
            User user = userOptional.get();
            if (!user.isBaned()) {
                filterChain.doFilter(request, response);
            } else {
                session.removeAttribute(UserConstant.LOGIN);
                session.removeAttribute(UserConstant.TYPE);
                request.setAttribute("bannedError", true);
                request.getRequestDispatcher(PagesConstant.LOGIN_PAGE).forward(request, response);
            }

        } else {
            request.getRequestDispatcher(PagesConstant.LOGIN_PAGE).forward(request, response);
        }
    }

    private boolean isLoginRequest(String command) {
        return command != null && (command.toUpperCase().equals(CommandType.SIGNIN.name()) || (command.toUpperCase().equals(CommandType.OAUTH.name())));
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

}