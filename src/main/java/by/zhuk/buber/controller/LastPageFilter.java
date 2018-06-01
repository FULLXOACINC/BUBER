package by.zhuk.buber.controller;

import by.zhuk.buber.command.CommandType;
import by.zhuk.buber.constant.CommandConstant;

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

@WebFilter(urlPatterns = {"/*"}, filterName = "lastPageFilter")
public class LastPageFilter implements Filter {
    private static final String LAST_PAGE = "lastPage";
    private static final String JSP = "/jsp";
    private static final String CONTROLLER = "/controller";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String commandValue = request.getParameter(CommandConstant.COMMAND);
        if (commandValue != null && commandValue.toUpperCase().equals(CommandType.LANG.name())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        HttpSession session = request.getSession();
        if (request.getServletPath().startsWith(JSP) || request.getServletPath().startsWith(CONTROLLER)) {
            if (request.getQueryString() != null) {
                session.setAttribute(LAST_PAGE, request.getServletPath() + "?" + request.getQueryString());
            } else {
                session.setAttribute(LAST_PAGE, request.getServletPath());
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }


    @Override
    public void destroy() {

    }
}