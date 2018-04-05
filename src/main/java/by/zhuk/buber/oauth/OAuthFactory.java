package by.zhuk.buber.oauth;

import by.zhuk.buber.exeption.UnknownOAuthException;
import by.zhuk.buber.oauth.OAuth;
import by.zhuk.buber.oauth.OAuthYandex;

public class OAuthFactory {
    public static OAuth create(String loginType) throws UnknownOAuthException {
        OAuth oAuth;
        switch (loginType) {
            case "yandex": {
                oAuth = OAuthYandex.getInstance();
                break;
            }
            default: {
                throw new UnknownOAuthException(loginType);
            }


        }
        return oAuth;
    }
}