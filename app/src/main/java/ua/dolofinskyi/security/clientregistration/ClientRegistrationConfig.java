package ua.dolofinskyi.security.clientregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Configuration
public class ClientRegistrationConfig {
    @Autowired
    private ClientRegistrationYamlParser clientRegistrationYamlParser;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }

    private ClientRegistration googleClientRegistration() {
        ClientRegistrationProvider clientRegistrationProvider =
                clientRegistrationYamlParser.parse("oauth2/google.yml");
        ClientRegistrationProvider.Configuration configuration = clientRegistrationProvider.getConfiguration();

        return ClientRegistration.withRegistrationId(configuration.getRegistrationId())
                .clientId(configuration.getClientId())
                .clientSecret(configuration.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(configuration.getRedirectUri())
                .scope(configuration.getScopes())
                .authorizationUri(configuration.getAuthorizationUri())
                .tokenUri(configuration.getTokenUri())
                .userInfoUri(configuration.getUserInfoUri())
                .userNameAttributeName(configuration.getUsernameAttributeName())
                .jwkSetUri(configuration.getJwkSetUri())
                .clientName(configuration.getClientName())
                .build();
    }
}