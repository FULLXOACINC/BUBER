package by.zhuk.buber.controller;

import by.zhuk.buber.command.PostCommandType;
import by.zhuk.buber.constant.CommandConstant;
import by.zhuk.buber.constant.UserConstant;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * Filter save last user action,necessary for correct translation
 */
@WebFilter(urlPatterns = {"/*"}, filterName = "lastPageFilter")
public class LastPageFilter implements Filter {
    private static final String JSP = "/jsp";
    private static final String CONTROLLER = "/Controller";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String commandValue = request.getParameter(CommandConstant.COMMAND);
        if (commandValue != null && commandValue.toUpperCase().equals(PostCommandType.LANG.name())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        HttpSession session = request.getSession();
        if (request.getServletPath().startsWith(JSP) || request.getServletPath().startsWith(CONTROLLER)) {
            if (request.getQueryString() != null) {
                session.setAttribute(UserConstant.LAST_PAGE, request.getServletPath() + "?" + request.getQueryString());
            } else {
                session.setAttribute(UserConstant.LAST_PAGE, request.getServletPath());
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }


    @Override
    public void destroy() {

    }
}