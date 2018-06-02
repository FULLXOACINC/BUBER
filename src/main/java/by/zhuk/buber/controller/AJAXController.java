package by.zhuk.buber.controller;

import by.zhuk.buber.command.ajax.AJAXCommand;
import by.zhuk.buber.command.ajax.AJAXCommandFactory;
import by.zhuk.buber.constant.CommandConstant;
import by.zhuk.buber.constant.ErrorConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@WebServlet(urlPatterns = {"/AJAXController/*"}, name = "AJAXController")
public class AJAXController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(Controller.class);
    private static String CHARACTER_ENCODING = "UTF-8";
    private static String CONTENT_TYPE = "application/json";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter(CommandConstant.COMMAND).replaceAll("-", "_");
        response.setCharacterEncoding(CHARACTER_ENCODING);
        response.setContentType(CONTENT_TYPE);

        Optional<AJAXCommand> commandOptional = AJAXCommandFactory.findCommand(command);
        JSONObject result;
        if (commandOptional.isPresent()) {
            result = commandOptional.get().execute(request);
        } else {
            logger.log(Level.WARN, CommandConstant.UNKNOWN_COMMAND);
            result = new JSONObject();
            result.put(ErrorConstant.ERROR, CommandConstant.UNKNOWN_COMMAND);
        }
        response.getWriter().print(result);
    }


}