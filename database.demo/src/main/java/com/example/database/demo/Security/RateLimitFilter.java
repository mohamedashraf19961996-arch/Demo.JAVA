package com.example.database.demo.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int LIMIT = 20;
    private static final long WINDOW_SECONDS = 60;

    private final Map<String, ClientRequestInfo> clients = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String ip = getClientIp(request);
        long now = Instant.now().getEpochSecond();

        ClientRequestInfo info = clients.computeIfAbsent(ip, key -> new ClientRequestInfo(0, now));

        synchronized (info) {
            if (now - info.windowStart >= WINDOW_SECONDS) {
                info.count = 0;
                info.windowStart = now;
            }

            if (info.count >= LIMIT) {
                response.setStatus(429);
                response.setContentType("application/json");
                response.getWriter().write("{\"message\":\"Too many requests. Try again later.\"}");
                return;
            }

            info.count++;
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");

        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }

    private static class ClientRequestInfo {
        int count;
        long windowStart;

        ClientRequestInfo(int count, long windowStart) {
            this.count = count;
            this.windowStart = windowStart;
        }
    }
}