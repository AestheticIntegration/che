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
package org.eclipse.che.security.auth0;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import org.eclipse.che.inject.DynaModule;
import org.eclipse.che.security.oauth.OAuthAuthenticator;

import org.slf4j.*;

@DynaModule
public class Auth0Module extends AbstractModule {

    private static final Logger LOG = LoggerFactory.getLogger(Auth0Module.class);

    @Override
    protected void configure() {
    	LOG.error("IMANDRA Auth0Module.configure ");
    	
        Multibinder<OAuthAuthenticator> oAuthAuthenticators = Multibinder.newSetBinder(binder(), OAuthAuthenticator.class);
    	LOG.error("IMANDRA Auth0Module oAuthAuthenticators = " + oAuthAuthenticators);
        oAuthAuthenticators.addBinding().to(Auth0OAuthAuthenticator.class);

    }
}