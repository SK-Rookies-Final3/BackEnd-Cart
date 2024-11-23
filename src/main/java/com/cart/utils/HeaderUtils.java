package com.cart.util;

import org.springframework.web.server.ServerWebExchange;

public class HeaderUtils {

    public static String getUserIdFromHeaders(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().getFirst("X-User-Id");
    }
}
