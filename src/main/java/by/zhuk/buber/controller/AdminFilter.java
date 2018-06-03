package by.zhuk.buber.controller;

import by.zhuk.buber.command.GetCommandType;
import by.zhuk.buber.command.PostCommandType;
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

/**
 * Web filter,When trying to access the admin resources, it will check if the user has the right to do this
 */
@WebFilter(urlPatterns = {"/*"}, filterName = "adminFilter")
public class AdminFilter implements Filter {
    private static Logger logger = LogManager.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isAdminCommand(request.getParameter(CommandConstant.COMMAND)) || isAdminPages(request.getRequestURI())) {
            HttpSession session = request.getSession();
            String userLogin = (String) session.getAttribute(UserConstant.LOGIN);
            String userType = (String) session.getAttribute(UserConstant.TYPE);
            if (UserType.ROOT_ADMIN.name().equals(userType) || UserType.ADMIN.name().equals(userType)) {
                filterChain.doFilter(request, response);
            } else {
                logger.log(Level.INFO, "User tries to perform an action without admin rights : " + userLogin);
                response.sendRedirect(PagesConstant.WELCOME_PAGE);
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private boolean isAdminPages(String requestURI) {
        return requestURI.startsWith(PagesConstant.USER_PAGE) || requestURI.startsWith(PagesConstant.SING_UP_DRIVER_PAGE) || requestURI.startsWith(PagesConstant.COMPLAINTS) || requestURI.startsWith(PagesConstant.FIND_USERS) || requestURI.startsWith(PagesConstant.USER_COMPLAINTS);
    }

    private boolean isAdminCommand(String command) {
        if (command == null) {
            return false;
        }
        command = command.toUpperCase().replaceAll("-", "_");

        boolean isChangeDiscount = command.equals(AJAXCommandType.CHANGE_DISCOUNT.name());
        boolean isSwitchBanCommand = command.equals(PostCommandType.SWITCH_BAN.name());
        boolean isSignUpDriverCommand = command.equals(AJAXCommandType.SIGN_UP_DRIVER.name());
        boolean isUpdateDriverCommand = command.equals(AJAXCommandType.UPDATE_DRIVER.name());
        boolean isFindDriverCommand = command.equals(GetCommandType.FIND_DRIVER_UPDATE.name());
        boolean isFindUser = command.equals(GetCommandType.FIND_USER.name());
        boolean isFindUsers = command.equals(AJAXCommandType.FIND_USERS.name());
        boolean isAcceptComplaint = command.equals(AJAXCommandType.ACCEPT_COMPLAINT.name());
        boolean isFindUserComplaint = command.equals(AJAXCommandType.FIND_USER_COMPLAINT.name());
        boolean isFindUnaccepted = command.equals(AJAXCommandType.FIND_UNACCEPTED_COMPLAINT.name());
        boolean isSwitchAdminStatusCommand = command.equals(PostCommandType.SWITCH_ADMIN_STATUS.name());

        return isSwitchBanCommand || isFindUser || isFindUsers || isAcceptComplaint || isFindUserComplaint || isFindUnaccepted || isSwitchAdminStatusCommand || isSignUpDriverCommand || isChangeDiscount || isUpdateDriverCommand || isFindDriverCommand;
    }

    @Override
    public void destroy() {

    }
}