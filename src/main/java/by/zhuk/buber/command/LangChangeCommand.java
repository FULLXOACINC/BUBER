package by.zhuk.buber.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LangChangeCommand implements Command {
    private static final String LANG = "lang";
    private static final String LAST_PAGE = "lastPage";

    @Override
    public Router execute(HttpServletRequest request) {
        String lang = request.getParameter(LANG);
        HttpSession session = request.getSession();
        session.setAttribute(LANG, lang);
        String lastPage = (String) session.getAttribute(LAST_PAGE);
        System.out.println(lastPage);
        return new Router(TransitionType.REDIRECT, lastPage);
    }
}