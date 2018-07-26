package com.example.demo.configuration

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthorizationEntryPoint extends BasicAuthenticationEntryPoint {
    
    @Override
    void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Basic realm=$realmName")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        PrintWriter writer = response.writer
        writer.println("HTTP Status 401 - " + authException.message)
    }

    @Override
    void afterPropertiesSet() throws Exception {
        setRealmName("DeveloperStack")
        super.afterPropertiesSet()
    }
}
