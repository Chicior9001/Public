package org.example.webserver;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


@RestController
public class WebController {
    private List<JSONObject> tokens = new ArrayList<>();
    BufferedImage image = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);


    public void createImage() {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.BLACK);
    }

    private String encodeBase64(BufferedImage image) throws IOException {
        this.createImage();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", outputStream);
        String base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        return base64Image;
    }

    @PostMapping("/register")
    public JSONObject register() {
        String token = UUID.randomUUID().toString();
        DateFormat df = new SimpleDateFormat("hh:mm aa");
        String timeNow = df.format(new Date());
        JSONObject json = new JSONObject();
        json.put("token", token);
        json.put("time", timeNow);
        json.put("active", "true");
        tokens.add(json);
        return json;
    }

    @GetMapping("/tokens")
    public List<JSONObject> tokens() {
        return tokens;
    }

    /*@GetMapping("/image")
    public String image(Model model) throws IOException {
        String base64;
        base64 = encodeBase64(image);
        model.addAttribute("image", base64); //
        return "image";
    }*/

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public byte[] getImage() throws IOException {
        String base64 = encodeBase64(image);
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return decodedBytes;
    }

    @PostMapping("/pixel")
    public String pixel(@RequestBody JSONPObject token, @RequestBody Integer x, @RequestBody Integer y, @RequestBody Integer color) {
        image.setRGB(x, y, color);
        return "200 OK";
    }

    @PostMapping("/createDB")
    public void createDataBase() {
        File file = new File("/database.db");
        String url = "/database.db";

        String createTableSQL = "CREATE TABLE IF NOT EXISTS entry (token TEXT NOT NULL, x INTEGER NOT NULL, y INTEGER NOT NULL, color TEXT NOT NULL, timestamp TEXT NOT NULL);\n" +
                "INSERT INTO entry (token, x, y, color, timestamp) VALUES(?, ?, ?, ?, ?);\n" +
                "DELETE FROM entry WHERE token=?;\n" +
                "SELECT token, x, y, color FROM entry ORDER BY timestamp;\n";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Ok");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
