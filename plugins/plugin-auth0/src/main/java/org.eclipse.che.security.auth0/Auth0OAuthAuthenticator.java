/**
 *
 * Copyright (c) 2016 Aesthetic Integration Limited
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 */
package org.eclipse.che.security.auth0;

import com.google.api.client.util.store.MemoryDataStoreFactory;

import org.eclipse.che.api.auth.shared.dto.OAuthToken;
import org.eclipse.che.commons.annotation.Nullable;
import org.eclipse.che.commons.json.JsonHelper;
import org.eclipse.che.commons.json.JsonParseException;
import org.eclipse.che.security.oauth.Auth0User;
import org.eclipse.che.security.oauth.OAuthAuthenticationException;
import org.eclipse.che.security.oauth.OAuthAuthenticator;
import org.eclipse.che.security.oauth.shared.User;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 *
 * OAuth authentication for Auth0 accounts.
 *
 * */

@Singleton
public class Auth0OAuthAuthenticator extends OAuthAuthenticator {

    private String auth0Domain; // This is specific to each application

    @Inject
    public Auth0OAuthAuthenticator( @Nullable @Named("oauth.auth0.clientid") String clientId,
                                    @Nullable @Named("oauth.auth0.clientsecret") String clientSecret,
                                    @Nullable @Named("oauth.auth0.redirecturis") String[] redirectUris,
                                    @Nullable @Named("oauth.auth0.authuri") String authUri,
                                    @Nullable @Named("oauth.auth0.tokenuri") String tokenUri,
                                    @Nullable @Named("oauth.auth0.domain") String domain) throws IOException {
        if (!isNullOrEmpty(clientId)
                && !isNullOrEmpty(clientSecret)
                && !isNullOrEmpty(authUri)
                && !isNullOrEmpty(tokenUri)
                && !isNullOrEmpty(domain)
                && redirectUris != null && redirectUris.length != 0) {

            auth0Domain = domain;

            configure(clientId, clientSecret, redirectUris, authUri, tokenUri, new MemoryDataStoreFactory());
        }
    }

    @Override
    public User getUser(OAuthToken accessToken) throws OAuthAuthenticationException {
        Auth0User user = getJson("https://" + this.getDomain() + "/userinfo/?access_token=" + accessToken.getToken(), Auth0User.class);

        if (user.verified_email() == false) {
            throw new OAuthAuthenticationException(
                    "Sorry, we failed to find any verified emails associated with your Auth0 account." +
                    "Please, verify at least one email in your Auth0 account and try to connect with Auth0 again.");

        }

        return user;
    }

    @Override
    public final String getOAuthProvider() {
        return "auth0";
    }

    @Override
    public OAuthToken getToken(String userId) throws IOException {
        final OAuthToken token = super.getToken(userId);

        if (!(token == null || token.getToken() == null || token.getToken().isEmpty())) {

            // Need to check if token which stored is valid for requests, then if valid - we returns it to caller
            String tokenVerifyUrl = "https://" + this.getDomain() + "/userinfo/?access_token=" + token.getToken();
            HttpURLConnection http = null;
            try {
                http = (HttpURLConnection)new URL(tokenVerifyUrl).openConnection();
                http.setInstanceFollowRedirects(false);
                http.setRequestMethod("GET");
                http.setRequestProperty("Accept", "application/json");

                if (http.getResponseCode() == 401) {
                    return null;
                }
            } finally {
                if (http != null) {
                    http.disconnect();
                }
            }

            return token;
        }
        return null;
    }


    private String getDomain () {
        return auth0Domain;
    }

}
