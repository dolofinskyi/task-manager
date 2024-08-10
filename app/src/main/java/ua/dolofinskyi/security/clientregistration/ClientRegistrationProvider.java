package ua.dolofinskyi.security.clientregistration;

import lombok.Data;
import java.util.List;

@Data
public class ClientRegistrationProvider {
    private Configuration configuration;

    @Data
    public static class Configuration {
        private String registrationId;
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private List<String> scopes;
        private String authorizationUri;
        private String tokenUri;
        private String userInfoUri;
        private String usernameAttributeName;
        private String jwkSetUri;
        private String clientName;
    }
}
