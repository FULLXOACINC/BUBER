package by.zhuk.buber.validator;

import by.zhuk.buber.constant.OAuthConstant;

import javax.servlet.http.HttpSession;

public class AuthorizationValidator {
    public static boolean isAuthorization(HttpSession session) {
        return session.getAttribute(OAuthConstant.ACCESS_TOKEN) != null;
    }
}