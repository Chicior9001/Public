package com.nauka.Kolokwium2;

import java.time.LocalDateTime;

//public record Pixel(String token, int x, int y, String color) {}

public class Pixel {
    private String token;
    private int x;
    private int y;
    private String color;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
