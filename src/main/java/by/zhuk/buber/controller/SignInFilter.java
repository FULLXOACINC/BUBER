package by.zhuk.buber.controller;

import by.zhuk.buber.command.GetCommandType;
import by.zhuk.buber.command.PostCommandType;
import by.zhuk.buber.command.ajax.AJAXCommandType;
import by.zhuk.buber.constant.CommandConstant;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.validator.UserValidator;

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
/**
 * Web filter,if the user is not authorized and accesses the page inaccessible to unauthorized users then redirect to sign in page
 */
@WebFilter(urlPatterns = {"/*"}, filterName = "signInFilter")
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

        if (isLoginCommand(request.getParameter(CommandConstant.COMMAND)) || request.getRequestURI().equals(PagesConstant.SING_UP_PAGE) || request.getRequestURI().equals(PagesConstant.RESTORE_PASSWORD) || request.getRequestURI().equals(PagesConstant.SIGN_IN_PAGE) || requestFile(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!UserValidator.isAuthorization(session)) {
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

        boolean isLangCommand = command.equals(PostCommandType.LANG.name());
        boolean isSignInCommand = command.equals(AJAXCommandType.SIGN_IN.name());
        boolean isSignUpAcceptCommand = command.equals(GetCommandType.SIGN_UP_ACCEPT.name());
        boolean isRestorePasswordCommand = command.equals(AJAXCommandType.RESTORE_PASSWORD.name());
        boolean isRestorePasswordAcceptCommand = command.equals(GetCommandType.RESTORE_PASSWORD_ACCEPT.name());

        boolean isSignUpUserCommand = command.equals(AJAXCommandType.SIGN_UP_USER.name());
        boolean isOAuthCommand = command.equals(GetCommandType.OAUTH.name()) || command.equals(GetCommandType.OAUTH_ACCEPT.name());

        return isSignInCommand || isSignUpAcceptCommand || isSignUpUserCommand || isOAuthCommand || isLangCommand || isRestorePasswordAcceptCommand || isRestorePasswordCommand;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

}