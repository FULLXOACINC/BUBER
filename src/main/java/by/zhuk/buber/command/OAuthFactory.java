package by.zhuk.buber.command;

import by.zhuk.buber.exeption.UnknownOAuthException;
import by.zhuk.buber.oauth.OAuth;

import by.zhuk.buber.oauth.OAuthYandex;

class OAuthFactory {
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