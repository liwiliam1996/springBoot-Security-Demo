package com.wiliam.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: liwiliam
 */
@ConfigurationProperties(prefix = "liwiliam.security.properties")
@Data
public class SecurityProperties {
    private ValidateProperties validate = new ValidateProperties();
}
