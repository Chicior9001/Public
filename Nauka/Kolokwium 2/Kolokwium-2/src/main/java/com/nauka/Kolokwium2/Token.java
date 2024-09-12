package com.nauka.Kolokwium2;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Token {
    private final String token;
    private final LocalDateTime creationTime;
    private boolean isActive = true;

    public Token(String token, LocalDateTime creationTime) {
        this.token = token;
        this.creationTime = creationTime;
    }

    public void deactivate() {
        isActive = false;
    }

    public boolean isActive() {
        if (isActive && creationTime.plusMinutes(5).isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }

    public String token() {
        return token;
    }

    public LocalDateTime creationTime() {
        return creationTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Token) obj;
        return Objects.equals(this.token, that.token) &&
                Objects.equals(this.creationTime, that.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, creationTime);
    }

    @Override
    public String toString() {
        return "Token[" +
                "token=" + token + ", " +
                "creationTime=" + creationTime + ']';
    }

}
