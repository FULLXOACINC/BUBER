package by.zhuk.buber.controller;

import by.zhuk.buber.command.CommandType;
import by.zhuk.buber.command.ajax.AJAXCommandType;
import by.zhuk.buber.constant.CommandConstant;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.validator.SignInValidator;

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

@WebFilter(urlPatterns = {"/*"}, filterName = "signIn")
public class SignInFilter implements Filter {
    private static final String JS_FILE_EXPANSION = ".js";
    private static final String CSS_FILE_EXPANSION = ".css";
    private static final String JPG_FILE_EXPANSION = ".jpg";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        if (isLoginCommand(request.getParameter(CommandConstant.COMMAND)) || request.getRequestURI().equals(PagesConstant.SING_UP_PAGE) || request.getRequestURI().equals(PagesConstant.SIGN_IN_PAGE) || requestFile(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!SignInValidator.isAuthorization(session)) {
            response.sendRedirect(PagesConstant.SIGN_IN_PAGE);
        } else {
            filterChain.doFilter(request, response);
        }


    }

    private boolean requestFile(String requestURI) {
        return requestURI.endsWith(CSS_FILE_EXPANSION) || requestURI.endsWith(JS_FILE_EXPANSION) || requestURI.endsWith(JPG_FILE_EXPANSION);
    }

    private boolean isLoginCommand(String command) {
        if (command == null) {
            return false;
        }
        command = command.toUpperCase().replaceAll("-", "_");

        boolean isLangCommand = command.equals(CommandType.LANG.name());
        boolean isSignInCommand = command.equals(AJAXCommandType.SIGN_IN.name());
        boolean isSignUpAcceptCommand = command.equals(CommandType.SIGN_UP_ACCEPT.name());
        boolean isSignUpUserCommand = command.equals(AJAXCommandType.SIGN_UP_USER.name());
        boolean isOAuthCommand = command.equals(CommandType.OAUTH.name()) || command.equals(CommandType.OAUTH_ACCEPT.name());

        return isSignInCommand || isSignUpAcceptCommand || isSignUpUserCommand || isOAuthCommand || isLangCommand;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

}