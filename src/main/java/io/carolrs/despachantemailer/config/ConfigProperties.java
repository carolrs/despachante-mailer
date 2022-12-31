package io.carolrs.despachantemailer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "app")
@Component
public class ConfigProperties {
    String recipient;
}
