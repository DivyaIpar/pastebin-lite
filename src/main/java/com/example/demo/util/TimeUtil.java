package com.example.demo.util;

import jakarta.servlet.http.HttpServletRequest;

public class TimeUtil {
	public static long now(HttpServletRequest request) {
        if ("1".equals(System.getenv("TEST_MODE"))) {
            String header = request.getHeader("x-test-now-ms");
            if (header != null) {
                return Long.parseLong(header);
            }
        }
        return System.currentTimeMillis();
    }

}
