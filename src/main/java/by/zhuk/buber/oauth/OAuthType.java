package by.zhuk.buber.oauth;
/**
 * Enum include type of oauth
 */
public enum OAuthType {
    YANDEX(new OAuthYandex()),
    GOOGLE(new OAuthGoogle());

    private AbstractOAuth oAuth;

    OAuthType(AbstractOAuth oAuth) {
        this.oAuth = oAuth;
    }

    public AbstractOAuth getOAuth() {
        return oAuth;
    }
}