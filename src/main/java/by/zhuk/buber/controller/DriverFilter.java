package by.zhuk.buber.controller;

import by.zhuk.buber.command.ajax.AJAXCommandType;
import by.zhuk.buber.constant.CommandConstant;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.model.UserType;
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

@WebFilter(urlPatterns = {"/*"}, filterName = "driver")
public class DriverFilter implements Filter {
    private static Logger logger = LogManager.getLogger(DriverFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isDriverCommand(request.getParameter(CommandConstant.COMMAND)) || isDriverPages(request.getRequestURI())) {
            HttpSession session = request.getSession();
            String userLogin = (String) session.getAttribute(UserConstant.LOGIN);
            String userType = (String) session.getAttribute(UserConstant.TYPE);
            if (UserType.DRIVER.name().equals(userType)) {
                filterChain.doFilter(request, response);
            } else {
                logger.log(Level.INFO, "User tries to perform an action without driver rights : " + userLogin);
                response.sendRedirect(PagesConstant.WELCOME_PAGE);
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private boolean isDriverPages(String requestURI) {
        return requestURI.startsWith(PagesConstant.DRIVER_PAGE) || requestURI.startsWith(PagesConstant.CHANGE_DRIVER_COORDINATE_PAGE);
    }

    private boolean isDriverCommand(String command) {
        if (command == null) {
            return false;
        }
        command = command.toUpperCase().replaceAll("-", "_");

        boolean isUpdateCurrentDriverCoordinate = command.equals(AJAXCommandType.UPDATE_CURRENT_DRIVER_COORDINATE.name());
        return isUpdateCurrentDriverCoordinate;
    }

    @Override
    public void destroy() {

    }
}