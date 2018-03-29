package by.zhuk.buber.controller;

import by.zhuk.buber.command.Command;
import by.zhuk.buber.command.CommandFactory;
import by.zhuk.buber.command.CommandResult;
import by.zhuk.buber.command.TransitionType;
import by.zhuk.buber.constant.PagesConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

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

        Optional<Command> commandOptional = CommandFactory.findCommand(request.getParameter(COMMAND));
        if (commandOptional.isPresent()) {
            CommandResult result = commandOptional.get().execute(request);


            if (result.getType() == TransitionType.FORWARD) {
                request.getRequestDispatcher(result.getTransitionResource()).forward(request, response);
            } else {
                response.sendRedirect(result.getTransitionResource());
            }
        }
        else {
            logger.log(Level.WARN,"Unknown command");
            response.sendRedirect(PagesConstant.WELCOME_PAGE);
        }

    }


}
