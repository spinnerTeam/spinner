package com.spinner.www.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spinner.www.util.ResponseVOUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 로그아웃 성공 핸들러
 * [note] 요청값 맞추기 위해서 강제로 값 넣었음.
 */
@Component
public class CustomerLogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("code", 20000);
        jsonResponse.put("message", "요청 성공");
        jsonResponse.put("results", ResponseVOUtils.getSuccessResponse().getResults());

        String jsonOutput = objectMapper.writeValueAsString(jsonResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonOutput);
        response.getWriter().flush();

    }
}
