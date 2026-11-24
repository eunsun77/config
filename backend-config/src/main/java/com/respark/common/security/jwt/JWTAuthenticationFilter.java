package com.respark.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.respark.common.config.CookieUtil;
import com.respark.common.config.SecurityPathConstants;
import com.respark.common.exception.BusinessException;
import com.respark.common.exception.ErrorCode;
import com.respark.common.exception.ErrorResponseDTO;
import com.respark.common.security.CustomUserDetails;
import com.respark.common.security.auth.AuthPrincipal;
import com.respark.common.security.auth.TokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final TokenValidator tokenValidator;
    private final ObjectMapper objectMapper;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean publicPath = isAuthPublic(request) || isPublicGet(request);
        String accessToken = resolveAccessToken(request);

        if (accessToken == null || accessToken.contains("undefined")) {
            if (publicPath) {
                filterChain.doFilter(request, response);
                return;
            }
            writeError(response, ErrorCode.MISSING_JWT);
            return;
        }

        try {
            AuthPrincipal principal = tokenValidator.validate(accessToken);
            CustomUserDetails userDetails = new CustomUserDetails(principal);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (BusinessException ex) {
            writeError(response, ex.getErrorCode());
        } catch (Exception ex) {
            log.error("JWT authentication failed", ex);
            writeError(response, ErrorCode.INVALID_JWT);
        }
    }

    private boolean isAuthPublic(HttpServletRequest request) {
        String path = request.getRequestURI();
        return SecurityPathConstants.AUTH_PUBLIC_PATHS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private boolean isPublicGet(HttpServletRequest request) {
        if (!HttpMethod.GET.matches(request.getMethod())) {
            return false;
        }
        String path = request.getRequestURI();
        return SecurityPathConstants.PUBLIC_GET_PATHS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (CookieUtil.ACCESS_TOKEN_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void writeError(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getHttpStatus());
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), new ErrorResponseDTO(errorCode));
    }
}
