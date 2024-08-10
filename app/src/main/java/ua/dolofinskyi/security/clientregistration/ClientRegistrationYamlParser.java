package ua.dolofinskyi.security.clientregistration;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;

@Component
public class ClientRegistrationYamlParser {

    public ClientRegistrationProvider parse(String path) {
        try (InputStream inputStream = ClientRegistrationYamlParser.class.getClassLoader()
                .getResourceAsStream(path)) {
            Yaml yaml = new Yaml();
            return yaml.loadAs(inputStream, ClientRegistrationProvider.class);
        } catch (Exception e) {
            return null;
        }
    }
}
