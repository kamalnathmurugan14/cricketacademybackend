package com.cricketacademy.api.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * Utility class for extracting real client IP addresses from HTTP requests.
 * Handles proxy/load balancer scenarios by checking common headers.
 */
@Component
public class IpAddressUtil {

    /**
     * Extracts the real client IP address from the request.
     * Checks common proxy headers like X-Forwarded-For, X-Real-IP, etc.
     * Falls back to request.getRemoteAddr() if no proxy headers are present.
     *
     * @param request The HTTP servlet request
     * @return The real client IP address
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = null;

        // Check X-Forwarded-For header (common in load balancers and proxies)
        ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress != null && !ipAddress.isEmpty()) {
            // X-Forwarded-For can contain multiple IPs separated by comma
            // The first IP is the original client
            ipAddress = ipAddress.split(",")[0].trim();
            return sanitizeIpAddress(ipAddress);
        }

        // Check X-Real-IP header (used by some proxies)
        ipAddress = request.getHeader("X-Real-IP");
        if (ipAddress != null && !ipAddress.isEmpty()) {
            return sanitizeIpAddress(ipAddress);
        }

        // Check X-Client-IP header
        ipAddress = request.getHeader("X-Client-IP");
        if (ipAddress != null && !ipAddress.isEmpty()) {
            return sanitizeIpAddress(ipAddress);
        }

        // Check CF-Connecting-IP (Cloudflare)
        ipAddress = request.getHeader("CF-Connecting-IP");
        if (ipAddress != null && !ipAddress.isEmpty()) {
            return sanitizeIpAddress(ipAddress);
        }

        // Check X-Cluster-Client-IP (AWS ELB)
        ipAddress = request.getHeader("X-Cluster-Client-IP");
        if (ipAddress != null && !ipAddress.isEmpty()) {
            return sanitizeIpAddress(ipAddress);
        }

        // Fallback to remote address
        ipAddress = request.getRemoteAddr();
        return sanitizeIpAddress(ipAddress);
    }

    /**
     * Sanitizes and validates the IP address string.
     * Removes any whitespace and validates basic IP format.
     *
     * @param ipAddress The IP address string to sanitize
     * @return The sanitized IP address
     */
    private static String sanitizeIpAddress(String ipAddress) {
        if (ipAddress == null) {
            return null;
        }

        ipAddress = ipAddress.trim();

        // Basic validation for IPv4 and IPv6
        if (ipAddress.isEmpty()) {
            return null;
        }

        // Remove brackets for IPv6 addresses if present
        if (ipAddress.startsWith("[") && ipAddress.endsWith("]")) {
            ipAddress = ipAddress.substring(1, ipAddress.length() - 1);
        }

        return ipAddress;
    }

    /**
     * Validates if the given string is a valid IP address (IPv4 or IPv6).
     *
     * @param ipAddress The IP address to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidIpAddress(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            return false;
        }

        // Basic regex for IPv4
        if (ipAddress.matches(
                "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) {
            return true;
        }

        // Basic regex for IPv6
        if (ipAddress.matches("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$") ||
                ipAddress.matches("^(?:[0-9a-fA-F]{1,4}:){1,7}:$") ||
                ipAddress.matches("^:(:[0-9a-fA-F]{1,4}){1,7}$") ||
                ipAddress.matches("^(?:[0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}$")) {
            return true;
        }

        return false;
    }
}
