package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LangChangeCommand implements Command {
    private static final String LANG = "lang";

    @Override
    public Router execute(HttpServletRequest request) {
        String lang = request.getParameter(LANG);
        HttpSession session = request.getSession();
        session.setAttribute(LANG, lang);
        return new Router(TransitionType.REDIRECT, PagesConstant.WELCOME_PAGE);
    }
}