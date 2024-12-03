package com.cart.utils;

import jakarta.servlet.http.HttpServletRequest;


public class HeaderUtils {

    private static final String USER_ID_HEADER = "X-User-Id";

    public static String getUserId(HttpServletRequest request) {
        String userId = request.getHeader(USER_ID_HEADER);
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID header is missing or empty.");
        }
        return userId;
    }
}