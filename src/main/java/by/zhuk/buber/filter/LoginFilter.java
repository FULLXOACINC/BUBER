package by.zhuk.buber.filter;

import by.zhuk.buber.constant.CommandConstant;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.validator.AuthorizationValidator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (AuthorizationValidator.isAuthorization(request.getSession()) || isLoginRequest(request.getParameter("command"))) {
            filterChain.doFilter(request, response);
        } else {
            request.getRequestDispatcher(PagesConstant.LOGIN_PAGE).forward(request, response);
        }
    }

    private boolean isLoginRequest(String command) {
        return command != null && (command.equals(CommandConstant.LOGIN) || command.equals(CommandConstant.OAUTH));
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

}