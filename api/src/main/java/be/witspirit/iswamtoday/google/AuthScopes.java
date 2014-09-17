package be.witspirit.iswamtoday.google;

/**
 * Container for Google's OAuth2 Authentication Scopes
 */
public interface AuthScopes {
    String PLUS_PROFILE = "https://www.googleapis.com/auth/plus.login";
    String PLUS_ID = "https://www.googleapis.com/auth/plus.me";

    String EMAIL = "https://www.googleapis.com/auth/userinfo.email";
    String PROFILE = "https://www.googleapis.com/auth/userinfo.profile";

}
