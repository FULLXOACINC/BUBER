package by.zhuk.buber.controller;

import by.zhuk.buber.command.Command;
import by.zhuk.buber.command.CommandFactory;
import by.zhuk.buber.command.CommandResult;
import by.zhuk.buber.command.TransitionType;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.exeption.UnknownCommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/controller"}, name = "controller")
public class Controller extends HttpServlet {
    private static Logger logger = LogManager.getLogger(Controller.class);
    private static String COMMAND = "command";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command;
        try {
            command = CommandFactory.createCommand(request.getParameter(COMMAND));
            CommandResult result = command.execute(request);
            if (result.getType() == TransitionType.FORWARD) {
                request.getRequestDispatcher(result.getTransitionResource()).forward(request, response);
            } else {
                response.sendRedirect(result.getTransitionResource());
            }

        } catch (UnknownCommandException e) {
            request.getRequestDispatcher(PagesConstant.WELCOM_PAGE).forward(request, response);
            logger.catching(e);
        }
    }


}
