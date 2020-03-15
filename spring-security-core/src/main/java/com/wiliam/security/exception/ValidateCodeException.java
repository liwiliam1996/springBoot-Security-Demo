package com.wiliam.security.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @author: liwiliam
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String explanation) {
        super(explanation);
    }
}
