package com.avirantEnterprises.information_collector.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPT = 3;
    private static final long BLOCK_TIME = 24 * 60 * 60; // 24 hours in seconds

    private final ConcurrentHashMap<String, LoginAttempt> attemptsCache = new ConcurrentHashMap<>();

    public void loginFailed(String username) {
        attemptsCache.merge(username, new LoginAttempt(1, Instant.now().getEpochSecond()), (oldValue, newValue) -> {
            int newAttempts = oldValue.getAttempts() + 1;
            return new LoginAttempt(newAttempts, Instant.now().getEpochSecond());
        });
    }

    public void loginSucceeded(String username) {
        attemptsCache.remove(username);
    }

    public boolean isBlocked(String username) {
        LoginAttempt attempt = attemptsCache.get(username);
        if (attempt == null) {
            return false;
        }

        if (attempt.getAttempts() >= MAX_ATTEMPT) {
            long currentTime = Instant.now().getEpochSecond();
            long elapsedTime = currentTime - attempt.getLastAttemptTime();
            if (elapsedTime >= BLOCK_TIME) {
                // Reset the block after 24 hours
                attemptsCache.remove(username);
                return false;
            }
            return true; // Still blocked
        }

        return false;
    }

    private static class LoginAttempt {
        private final int attempts;
        private final long lastAttemptTime;

        public LoginAttempt(int attempts, long lastAttemptTime) {
            this.attempts = attempts;
            this.lastAttemptTime = lastAttemptTime;
        }

        public int getAttempts() {
            return attempts;
        }

        public long getLastAttemptTime() {
            return lastAttemptTime;
        }
    }
}
