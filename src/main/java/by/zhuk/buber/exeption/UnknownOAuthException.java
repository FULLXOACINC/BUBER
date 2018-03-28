package by.zhuk.buber.exeption;

public class UnknownOAuthException extends Exception {
    public UnknownOAuthException(String loginType) {
        super(loginType);
    }
}