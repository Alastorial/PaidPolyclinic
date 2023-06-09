package ru.alastorial.paidpolyclinic.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.alastorial.paidpolyclinic.service.security.JwtService;
import ru.alastorial.paidpolyclinic.service.security.PatientDetailServiceImpl;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final PatientDetailServiceImpl patientDetailService;

    /**
     * Проверяет, что запрос содержит валидный JWT-токен, и если да, то аутентифицирует пользователя.
     * иначе пропускает запрос дальше по цепочке фильтров
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (isNotBearerAuth(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = authHeader.substring(7); // убираем префикс "Bearer "
        var username = jwtService.extractUsername(jwt);

        if (canAuthenticate(jwt, username)) {
            var userDetails = patientDetailService.loadUserByUsername(username);
            authenticate(request, userDetails);
        }

        filterChain.doFilter(request, response);
    }

    private boolean canAuthenticate(String jwt, String username) {
        return username != null && getSecurityContext().getAuthentication() == null && jwtService.isNotExpired(jwt);
    }

    private SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    private void authenticate(HttpServletRequest request, UserDetails userDetails) {
        var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        getSecurityContext().setAuthentication(authToken);
    }

    private boolean isNotBearerAuth(String authHeader) {
        return authHeader == null || !authHeader.startsWith("Bearer ");
    }
}

