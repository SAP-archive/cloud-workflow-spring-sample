package com.sap.api.workflow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

@Configuration
public class SAPCloudPlatformUAA {

    @Value("${uaa.url}")
    private String url;

    @Value("${uaa.clientId}")
    private String clientId;

    @Value("${uaa.clientSecret}")
    private String clientSecret;

    @Bean
    public OAuth2ProtectedResourceDetails resourceDetails() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setAccessTokenUri(url + "/oauth/token");
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientSecret);
        resourceDetails.setGrantType("client_credentials");
        resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
        return resourceDetails;
    }

    @Bean
    public OAuth2RestOperations oauth2RestTemplate() {
        return new OAuth2RestTemplate(
                resourceDetails(),
                new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest())
        );
    }
}
