package by.zhuk.buber.oauth;

public enum OAuthType {
    YANDEX(OAuthYandex.getInstance());

    private OAuth oAuth;

    OAuthType(OAuth oAuth) {
        this.oAuth = oAuth;
    }

    public OAuth getOAuth() {
        return oAuth;
    }
}