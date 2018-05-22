package by.zhuk.buber.validator;

import by.zhuk.buber.constant.UserConstant;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInValidator {
    private static final Pattern VALID_LOGIN_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isLoginValid(String login) {
        Matcher matcher = VALID_LOGIN_REGEX.matcher(login);
        return matcher.find();
    }

    public static boolean isAuthorization(HttpSession session) {
        return session.getAttribute(UserConstant.LOGIN) != null && session.getAttribute(UserConstant.TYPE) != null;
    }


}