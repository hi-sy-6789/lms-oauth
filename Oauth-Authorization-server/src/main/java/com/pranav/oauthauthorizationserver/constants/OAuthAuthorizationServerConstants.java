package com.pranav.oauthauthorizationserver.constants;

import java.security.Key;

public class OAuthAuthorizationServerConstants {
    public static final String AUTH_URL="http://127.0.0.1:8080/login/oauth2/code/api-client-oidc";
    public static final String CALLBACK_URL="https://oauth.pstmn.io/v1/callback";
    public static final String SPRING_SECURITY_CLIENT_CLIENT_ID="api-client";
    public static final String SPRING_SECURITY_CLIENT_SECRET_KEY="secret";
    public static final String SPRING_SECURITY_CLIENT_SCOPE_TO_READ_AND_WRITE="Permission_to_Read_and_Write";
    public static final String OAUTH_AUTHORIZATION_SERVER_ISSUER_URL="http://auth-server:9000";
}
