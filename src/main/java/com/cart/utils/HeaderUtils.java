package com.cart.util;

import jakarta.servlet.http.HttpServletRequest;


public class HeaderUtils {

    public static String getUserIdFromHeaders(HttpServletRequest request) {
        return request.getHeader("X-User-Id");  // "X-User-Id" 헤더에서 사용자 ID 추출
    }
}
