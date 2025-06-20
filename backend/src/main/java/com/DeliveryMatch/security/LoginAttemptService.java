package com.DeliveryMatch.security;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    private final int MAX_ATTEMPT = 5;
    private final long BLOCK_DURATION = TimeUnit.MINUTES.toMillis(15);
    
    private final ConcurrentHashMap<String, LoginAttempt> attempts = new ConcurrentHashMap<>();

    public void recordFailedAttempt(String email) {
        attempts.compute(email, (key, attempt) -> {
            if (attempt == null) {
                return new LoginAttempt(1, System.currentTimeMillis());
            }
            if (isBlocked(email)) {
                return attempt;
            }
            return new LoginAttempt(attempt.getAttempts() + 1, attempt.getLastAttemptTime());
        });
    }

    public void resetAttempts(String email) {
        attempts.remove(email);
    }

    public boolean isBlocked(String email) {
        LoginAttempt attempt = attempts.get(email);
        if (attempt == null) {
            return false;
        }
        
        if (attempt.getAttempts() >= MAX_ATTEMPT) {
            long timeSinceLastAttempt = System.currentTimeMillis() - attempt.getLastAttemptTime();
            if (timeSinceLastAttempt < BLOCK_DURATION) {
                return true;
            }
            // Reset attempts after block duration
            attempts.remove(email);
        }
        return false;
    }

    public long getRemainingBlockTime(String email) {
        LoginAttempt attempt = attempts.get(email);
        if (attempt == null || attempt.getAttempts() < MAX_ATTEMPT) {
            return 0;
        }
        long timeSinceLastAttempt = System.currentTimeMillis() - attempt.getLastAttemptTime();
        return Math.max(0, BLOCK_DURATION - timeSinceLastAttempt);
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