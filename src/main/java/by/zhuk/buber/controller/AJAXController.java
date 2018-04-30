package by.zhuk.buber.controller;

import by.zhuk.buber.command.ajax.AJAXCommand;
import by.zhuk.buber.command.ajax.AJAXCommandFactory;
import by.zhuk.buber.constant.CommandConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@WebServlet(urlPatterns = {"/AJAXController/*"}, name = "AJAXController")
public class AJAXController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(Controller.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter(CommandConstant.COMMAND).replaceAll("-", "_");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Optional<AJAXCommand> commandOptional = AJAXCommandFactory.findCommand(command);
        JSONObject result;
        if (commandOptional.isPresent()) {
            result = commandOptional.get().execute(request);
        } else {
            logger.log(Level.WARN, "Unknown command");
            result = new JSONObject();
            result.put("error", "Unknown command");

        }
        response.getWriter().print(result);
    }
}