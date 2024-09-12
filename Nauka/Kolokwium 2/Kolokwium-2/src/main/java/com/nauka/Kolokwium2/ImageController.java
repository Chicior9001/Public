package com.nauka.Kolokwium2;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class ImageController {
    // private final TokenService tokenService;
    static BufferedImage image;
    private static int frame = 0;

    public ImageController() {
        image = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
        fillBlack();
    }

    public static void fillBlack() {
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, 512, 512);
        graphics.dispose();
    }

    @GetMapping("/image")
    public /*ResponseEntity<byte[]>*/ String createImage(Model model) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", stream);
        String base64 = Base64.getEncoder().encodeToString(stream.toByteArray());

        model.addAttribute("image", base64);
        return "image";

        /*return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"image.png\"")
                .contentType(MediaType.IMAGE_PNG)
                .body(stream.toByteArray());*/
    }

    @PostMapping("/pixel")
    public ResponseEntity<String> setPixel(@RequestBody Pixel pixel) throws IOException {
        /*String tokenc = "07194226-089d-4ee9-8a27-588ee5921e3d";*/

        // Token token = new Token(pixel.getToken(), LocalDateTime.now());
        //System.out.println(TokenService.getTokens());
        System.out.println(pixel.getToken());
        if(!TokenService.isTokenActive(pixel.getToken())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token is not active");
        }

        if (pixel.getX() < 0 || pixel.getX() >= 512 || pixel.getY() < 0 || pixel.getY() >= 512) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid coordinates");
        }
       /* if (pixel.x() < 0 || pixel.x() >= 512 || pixel.y() < 0 || pixel.y() >= 512) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid coordinates");
        }*/

        String hexColor = pixel.getColor();
        // String hexColor = pixel.color();

        try {
            Color color = Color.decode("#" + hexColor);
            image.setRGB(pixel.getX(), pixel.getY(), color.getRGB());
            Database.insertEntry(pixel.getToken(), pixel.getX(), pixel.getY(), pixel.getColor(), String.valueOf(LocalDateTime.now()));
            saveAsPNG();
            return ResponseEntity.ok("Pixel set successfully");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid color format");
        }
    }

    public static void saveAsPNG() {
        try {
            // File file = new File("E:\\Studia\\Semestr II\\Programowanie obiektowe\\Nauka\\Kolokwium 2\\Kolokwium-2\\src\\main\\resources\\Frames\\image_" + frame + ".png");
            File file = new File("C:\\Frames\\image_" + frame + ".png");
            ImageIO.write(image, "png", file);
            frame++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public static void loadImage() {
        List<Pixel> pixels = Database.getImage();
        fillBlack();
        for (Pixel pixel : pixels) {
            Color color = Color.decode("#" + pixel.getColor());
            image.setRGB(pixel.getX(), pixel.getY(), color.getRGB());
        }
    }

}
