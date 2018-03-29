package by.zhuk.buber.validator;

import by.zhuk.buber.constant.UserConstant;

import javax.servlet.http.HttpSession;

public class SignInValidator {
    private static final String LOGIN_REGEXP = "[\\w\\d]{3,20}@[\\w\\d]{3,20}.[\\w\\d]{2,10}";

    public static boolean isAuthorization(HttpSession session) {
        return session.getAttribute(UserConstant.LOGIN) != null && session.getAttribute(UserConstant.TYPE) != null;
    }

    public static boolean isLoginValide(String login){
        return login.matches(LOGIN_REGEXP);
    }
}