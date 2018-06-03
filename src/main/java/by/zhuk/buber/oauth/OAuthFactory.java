package by.zhuk.buber.oauth;

import java.util.Optional;
/**
 * Class-factory oauth object
 * Pattern: factory method
 */
public class OAuthFactory {

    public static Optional<AbstractOAuth> findOAuth(String oAuthType) {
        try {
            OAuthType type = OAuthType.valueOf(oAuthType.toUpperCase());
            return Optional.of(type.getOAuth());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

    }
}