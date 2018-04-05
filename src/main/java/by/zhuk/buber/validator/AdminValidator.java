package by.zhuk.buber.validator;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.model.UserType;

import javax.servlet.http.HttpSession;

public class AdminValidator {
    public static boolean isAdmin(HttpSession session) {
        String userType = (String) session.getAttribute(UserConstant.TYPE);
        return userType.equals(UserType.ADMIN.name());
    }
}