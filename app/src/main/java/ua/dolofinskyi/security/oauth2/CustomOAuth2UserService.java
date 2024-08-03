package ua.dolofinskyi.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.dolofinskyi.features.user.User;
import ua.dolofinskyi.features.user.UserServiceImpl;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String accessToken = userRequest.getAccessToken().getTokenValue();
        String userInfoEndpointUri = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUri();

        Map<String, Object> userAttributes = getUserInfoFromGoogle(userInfoEndpointUri, accessToken);
        String oauth2Sub = (String) userAttributes.get("sub");

        User user = userService.getUserOAuth2Sub(oauth2Sub);

        if (user == null) {
            user = userService.createUser(userAttributes);
        }

        return new CustomOAuth2User(user, userAttributes, Collections.emptyList());
    }

    private Map<String, Object> getUserInfoFromGoogle(String userInfoEndpointUri, String accessToken) {
        String uri = userInfoEndpointUri + "?access_token=" + accessToken;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Map.class);
    }
}
