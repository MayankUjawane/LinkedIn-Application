package com.example.linkedin.posts_service.auth;

public class UserContextHolder {

    // ThreadLocal gives us the ability to store data individually for the current thread
    // and simply wrap it within a special type of object.
    private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    public static Long getCurrentUserId() {
        return currentUserId.get();
    }

    static void setCurrentUserId(Long userId) {
        currentUserId.set(userId);
    }

    static void clear() {
        currentUserId.remove();
    }
}
