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

@WebFilter(urlPatterns = {"/*"}, filterName = "driverFilter")
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
        return requestURI.startsWith(PagesConstant.CHANGE_DRIVER_COORDINATE_PAGE) || requestURI.startsWith(PagesConstant.CURRENT_DRIVER_RIDE_PAGE) || requestURI.startsWith(PagesConstant.DRIVER_PROFILE_PAGE) || requestURI.startsWith(PagesConstant.DRIVER_RIDE_HISTORY_PAGE) || requestURI.startsWith(PagesConstant.WITHDRAW_EARNINGS_PAGE);
    }

    private boolean isDriverCommand(String command) {
        if (command == null) {
            return false;
        }
        command = command.toUpperCase().replaceAll("-", "_");

        boolean isUpdateCurrentDriverCoordinate = command.equals(AJAXCommandType.UPDATE_CURRENT_DRIVER_COORDINATE.name());
        boolean isAcceptStartRideDriver = command.equals(AJAXCommandType.ACCEPT_START_RIDE_DRIVER.name());
        boolean isAcceptEndRideDriver = command.equals(AJAXCommandType.ACCEPT_END_RIDE_DRIVER.name());
        boolean isFindRideInfoDriver = command.equals(AJAXCommandType.FIND_RIDE_INFO_DRIVER.name());
        boolean isComplaintPassenger = command.equals(AJAXCommandType.COMPLAINT_PASSENGER.name());
        boolean isRefuseRideDiver = command.equals(AJAXCommandType.REFUSE_RIDE_DRIVER.name());
        boolean isDriverSetNotWorkingStatus = command.equals(AJAXCommandType.DRIVER_SET_NOT_WORKING_STATUS.name());
        boolean isDriverSetWorkingStatus = command.equals(AJAXCommandType.DRIVER_SET_WORKING_STATUS.name());
        boolean isFindDriverRideHistory = command.equals(AJAXCommandType.FIND_DRIVER_RIDE_HISTORY.name());
        boolean isFindDriverEarnedMoney = command.equals(AJAXCommandType.FIND_DRIVER_EARNED_MONEY.name());

        return isUpdateCurrentDriverCoordinate || isFindDriverRideHistory || isFindDriverEarnedMoney || isAcceptStartRideDriver || isAcceptEndRideDriver || isFindRideInfoDriver || isComplaintPassenger || isRefuseRideDiver || isDriverSetNotWorkingStatus || isDriverSetWorkingStatus;
    }

    @Override
    public void destroy() {

    }
}