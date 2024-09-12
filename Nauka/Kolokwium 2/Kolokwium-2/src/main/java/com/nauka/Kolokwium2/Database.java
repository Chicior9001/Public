package com.nauka.Kolokwium2;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public static String path = "E:\\Studia\\Semestr II\\Programowanie obiektowe\\Nauka\\Kolokwium 2\\Kolokwium-2\\src\\main\\resources\\database.db";
    public static String url = "jdbc:sqlite:" + path;
    public static final String createQuery = "CREATE TABLE IF NOT EXISTS entry (token TEXT NOT NULL, x INTEGER NOT NULL, y INTEGER NOT NULL, color TEXT NOT NULL, timestamp TEXT NOT NULL);";
    public static final String insertQuery = "INSERT INTO entry (token, x, y, color, timestamp) VALUES(?, ?, ?, ?, ?);";
    public static final String deleteQuery = "DELETE FROM entry WHERE token=?;";
    public static final String selectQuery = "SELECT token, x, y, color FROM entry ORDER BY timestamp;";

    public Database() throws IOException {
        createDatabase();
    }

    public final void createDatabase() throws IOException {
        File file = new File(path);
        if(file.createNewFile()) {
            System.out.println("File created");
        } else {
            System.out.println("File already exists");
        }

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(createQuery);
            System.out.println("Ok");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertEntry(String token, int x, int y, String color, String timestamp) {
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, token);
            stmt.setInt(2, x);
            stmt.setInt(3, y);
            stmt.setString(4, color);
            stmt.setString(5, timestamp);
            stmt.executeUpdate();
            System.out.println("Ok");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Pixel> getImage() {
        try {
            List<Pixel> pixels = new ArrayList<Pixel>();
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(selectQuery);
            ResultSet rs = stmt.executeQuery();
            Pixel pixel = new Pixel();
            while(rs.next()) {
                pixel = new Pixel();
                pixel.setToken(rs.getString("token"));
                pixel.setX(rs.getInt("x"));
                pixel.setY(rs.getInt("y"));
                pixel.setColor(rs.getString("color"));
                pixels.add(pixel);
            }
            System.out.println("Ok");
            return pixels;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int deleteEntry(String token) {
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(deleteQuery);
            stmt.setString(1, token);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
