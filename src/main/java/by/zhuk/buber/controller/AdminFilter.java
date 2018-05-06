package by.zhuk.buber.controller;

import by.zhuk.buber.command.CommandType;
import by.zhuk.buber.command.ajax.AJAXCommandType;
import by.zhuk.buber.constant.CommandConstant;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.model.UserType;

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

@WebFilter(urlPatterns = {"/*"}, filterName = "admin")
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isAdminCommand(request.getParameter(CommandConstant.COMMAND)) || request.getRequestURI().startsWith(PagesConstant.ADMIN_PAGE) || request.getRequestURI().startsWith(PagesConstant.USER_VIEW_PAGE)) {
            HttpSession session = request.getSession();
            String userType = (String) session.getAttribute(UserConstant.TYPE);
            if (UserType.ROOT_ADMIN.name().equals(userType) || UserType.ADMIN.name().equals(userType)) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect(PagesConstant.WELCOME_PAGE);
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private boolean isAdminCommand(String command) {
        if (command == null) {
            return false;
        }
        command = command.toUpperCase().replaceAll("-", "_");

        boolean isSwitchBanCommand = command.equals(CommandType.SWITCH_BAN.name());
        boolean isSignUpDriverCommand = command.equals(AJAXCommandType.SIGN_UP_DRIVER.name());
        boolean isSwitchAdminStatusCommand = command.equals(CommandType.SWITCH_ADMIN_STATUS.name());
        return isSwitchBanCommand || isSwitchAdminStatusCommand || isSignUpDriverCommand;
    }

    @Override
    public void destroy() {

    }
}