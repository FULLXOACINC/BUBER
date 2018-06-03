package by.zhuk.buber.command.ajax;


import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
/**
 * Interface executing command that came from the HTTP request(AJAX)
 */
public interface AJAXCommand {
    String ALL_CORRECT = "allCorrect";

    JSONObject execute(HttpServletRequest request);
}