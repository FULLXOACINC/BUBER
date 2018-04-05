package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.validator.AdminValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserBanCommand implements Command{

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session =request.getSession();

        if(!AdminValidator.isAdmin(session)){
            return new CommandResult(TransitionType.REDIRECT, PagesConstant.WELCOME_PAGE);
        }

        String userLogin=(String)session.getAttribute(UserConstant.TYPE);

        return null;
    }
}