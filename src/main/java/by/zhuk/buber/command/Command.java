package by.zhuk.buber.command;


import javax.servlet.http.HttpServletRequest;

/**
 * Interface executing command that came from the HTTP request
 */
public interface Command {
    Router execute(HttpServletRequest request);
}