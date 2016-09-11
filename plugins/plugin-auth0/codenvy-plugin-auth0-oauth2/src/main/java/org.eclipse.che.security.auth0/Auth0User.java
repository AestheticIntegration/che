/*
 *  [2012] - [2016] Codenvy, S.A.
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package org.eclipse.che.security.oauth;

import org.eclipse.che.security.oauth.shared.User;

import org.slf4j.*;

/***
 * See this for the complete description of the profile structure:
 *   https://auth0.com/docs/user-profile/user-profile-structure
 *
 */
public class Auth0User implements User {

    private static final Logger LOG = LoggerFactory.getLogger(Auth0User.class);

    private String firstName;
    private String lastName;
    private String organization;
    private String email;

    private String userid;

    @Override
    public final String getId() {
        return userid;
    }

    @Override
    public final void setId(String id) {
        userid = id;
    }

    @Override
    public String getName() {
        return firstName;
    }

    @Override
    public void setName(String name) {
        this.firstName = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public boolean email_verified () {
        return false;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Auth0User{" +
                "id='" + getId() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    }
