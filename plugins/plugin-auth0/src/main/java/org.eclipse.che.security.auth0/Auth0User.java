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
package org.eclipse.che.security.oauth;

import org.eclipse.che.security.oauth.shared.User;

/***
 * See this for the complete description of the profile structure:
 *   https://auth0.com/docs/user-profile/user-profile-structure
 *
 */
public class Auth0User implements User {
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
