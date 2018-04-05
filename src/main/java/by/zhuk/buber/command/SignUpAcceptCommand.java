package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.receiver.SignUpReceiver;
import by.zhuk.buber.singleton.SignUpUserInfo;
import by.zhuk.buber.singleton.SignUpUserPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignUpAcceptCommand implements Command {
    private static Logger logger = LogManager.getLogger(SignUpAcceptCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String hash = request.getParameter("hash");
        SignUpUserPool pool = SignUpUserPool.getInstance();
        SignUpUserInfo info = pool.find(hash);
        if(info == null ){
            return new CommandResult(TransitionType.REDIRECT, PagesConstant.LOGIN_PAGE);
        }
        SignUpReceiver receiver = new SignUpReceiver();
        try {
            receiver.saveUser(info.getUser());
            HttpSession session = request.getSession();
            session.setAttribute(UserConstant.LOGIN, info.getUser().getLogin());
            session.setAttribute(UserConstant.TYPE, UserType.USER.name());
            pool.removeInfo(hash);
        } catch (ReceiverException e) {
            //TODO error page
            logger.catching(e);
            return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
        }
        return new CommandResult(TransitionType.REDIRECT, PagesConstant.WELCOME_PAGE);
    }

}