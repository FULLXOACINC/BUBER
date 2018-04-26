package by.zhuk.buber.controller;

import by.zhuk.buber.command.Command;
import by.zhuk.buber.command.CommandFactory;
import by.zhuk.buber.command.Router;
import by.zhuk.buber.command.TransitionType;
import by.zhuk.buber.constant.CommandConstant;
import by.zhuk.buber.constant.PagesConstant;
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
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject json = new JSONObject();
        json.put("test","rest");
        logger.log(Level.INFO,"GOOD");
        response.getWriter().print(json);
    }
}