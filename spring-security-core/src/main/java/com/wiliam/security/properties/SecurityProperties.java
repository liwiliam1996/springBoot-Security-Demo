package com.wiliam.security.properties;

import com.wiliam.security.constant.SecurityConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: liwiliam
 */
@ConfigurationProperties(prefix = "liwiliam.security.properties")
@Data
public class SecurityProperties {
    private ValidateProperties validate = new ValidateProperties();

    private int rememberMeSeconds  = SecurityConstant.DEFAULT_SECONDS;

}
