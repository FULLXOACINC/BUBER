package by.zhuk.buber.controller;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.UserReceiver;
import org.apache.logging.log4j.Level;
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

@WebFilter(urlPatterns = {"/*"}, filterName = "ban")
public class BanFilter implements Filter {
    private static Logger logger = LogManager.getLogger(BanFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (session.getAttribute(UserConstant.LOGIN) == null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserReceiver receiver = new UserReceiver();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        Optional<User> userOptional = Optional.empty();
        try {
            userOptional = receiver.findUserByLogin(login);
        } catch (ReceiverException e) {
            logger.catching(e);
            response.sendRedirect(PagesConstant.ERROR_PAGE);
        }
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.isBaned()) {
                filterChain.doFilter(request, response);
            } else {
                session.removeAttribute(UserConstant.LOGIN);
                session.removeAttribute(UserConstant.TYPE);
                request.setAttribute(ErrorConstant.BANNED_ERROR, true);
                request.getRequestDispatcher(PagesConstant.SIGN_IN_PAGE).forward(request, response);
            }
        } else {
            logger.log(Level.WARN, "User is authorization [" + UserConstant.LOGIN + "],but not found in DB");
            session.removeAttribute(UserConstant.LOGIN);
            session.removeAttribute(UserConstant.TYPE);
            response.sendRedirect(PagesConstant.SIGN_IN_PAGE);
        }

    }


    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) {

    }
}