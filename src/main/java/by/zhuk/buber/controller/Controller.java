package by.zhuk.buber.controller;

import by.zhuk.buber.command.Command;
import by.zhuk.buber.command.GetCommandFactory;
import by.zhuk.buber.command.LangChangeCommand;
import by.zhuk.buber.command.PostCommandFactory;
import by.zhuk.buber.command.Router;
import by.zhuk.buber.command.TransitionType;
import by.zhuk.buber.constant.CommandConstant;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
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

@WebServlet(urlPatterns = {"/Controller"}, name = "Controller")
public class Controller extends HttpServlet {
    private static Logger logger = LogManager.getLogger(Controller.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter(CommandConstant.COMMAND).replaceAll("-", "_");
        Optional<Command> commandOptional = PostCommandFactory.findCommand(command);
        executeCommand(commandOptional,request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter(CommandConstant.COMMAND).replaceAll("-", "_");
        Optional<Command> commandOptional = GetCommandFactory.findCommand(command);
        executeCommand(commandOptional,request,response);
    }

    private void executeCommand(Optional<Command> commandOptional,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (commandOptional.isPresent()) {
            Router result = commandOptional.get().execute(request);
            if (result.getType() == TransitionType.FORWARD) {
                request.getRequestDispatcher(result.getTransitionResource()).forward(request, response);
            } else {
                response.sendRedirect(result.getTransitionResource());
            }
        } else {
            logger.log(Level.WARN, CommandConstant.UNKNOWN_COMMAND);
            response.sendRedirect(PagesConstant.WELCOME_PAGE);
        }
    }

}
