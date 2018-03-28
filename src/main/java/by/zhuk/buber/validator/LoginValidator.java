package by.zhuk.buber.validator;

import by.zhuk.buber.constant.OAuthConstant;

import javax.servlet.http.HttpSession;

public class LoginValidator {
    private static final String LOGIN_REGEXP = "[\\w\\d]{3,20}@[\\w\\d]{3,20}.[\\w\\d]{2,10}";

    public static boolean isAuthorization(HttpSession session) {
        return session.getAttribute(OAuthConstant.ACCESS_TOKEN) != null;
    }

    public static boolean isLoginValide(String login){
        return login.matches(LOGIN_REGEXP);
    }
}