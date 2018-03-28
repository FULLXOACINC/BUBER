package by.zhuk.buber.filter;

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

@WebFilter("/*")
public class SaveLastPageUrlFilter implements Filter {
    private static final String LAST_PAGE_URL = "lastPageUrl";
    private static final String CHANGE_LANG = "changeLang";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String commandValue = request.getParameter(CommandConstant.COMMAND);
        if (commandValue != null && commandValue.equals(CHANGE_LANG)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String lastPageUrl = request.getServletPath() + "?" + request.getQueryString();
        HttpSession session = request.getSession();
        session.setAttribute(LAST_PAGE_URL, lastPageUrl);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}