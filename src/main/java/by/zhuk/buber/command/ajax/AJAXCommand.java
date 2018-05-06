package by.zhuk.buber.command.ajax;


import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface AJAXCommand {
    String ERROR = "error";
    String ALL_CORRECT = "allCorrect";

    JSONObject execute(HttpServletRequest request);
}