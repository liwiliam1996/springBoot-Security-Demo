package com.wiliam.security.filter;

import com.wiliam.security.Validate.code.ImageCode;
import com.wiliam.security.exception.ValidateCodeException;
import com.wiliam.security.handle.AuthenticationFailureHandler;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: liwiliam
 */
@Data
@NoArgsConstructor
public class ValidateCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.pathEquals("/authentication/form", request.getRequestURI())) {
            try {
                validateCode(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     *  检验验证码逻辑
     *
     * @param servletWebRequest
     * @throws ServletRequestBindingException
     */
    private void validateCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        //从SESSION中获取验证码
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(servletWebRequest, "imageCode_key");
        //从ServletRequest中获取验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");
        //验证码过期
        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (codeInSession.isExpire()) {
            sessionStrategy.removeAttribute(servletWebRequest, "imageCode_key");
            throw new ValidateCodeException("验证码过期,请重新获取验证码");
        }
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new ValidateCodeException("请填写验证码");
        }
        if (!StringUtils.pathEquals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(servletWebRequest, "imageCode_key");
    }

    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler, SessionStrategy sessionStrategy) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.sessionStrategy = sessionStrategy;
    }

}
