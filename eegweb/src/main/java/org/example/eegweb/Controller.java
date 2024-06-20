package org.example.eegweb;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/image")
    public String image(Model model) throws IOException {
        String base64;
        BufferedReader reader = new BufferedReader(new FileReader("D:\\Studia\\Semestr II\\Programowanie obiektowe\\Projects\\eeg\\data.txt"));
        base64 = reader.readLine();
        model.addAttribute("image", base64);
        return "eegimage";
    }

    @GetMapping("/image/{username}/{electrode_number}")
    public String getData(Model model, @PathVariable String username, @PathVariable Integer electrode_number) {
        String url = "jdbc:sqlite:D:\\Studia\\Semestr II\\Programowanie obiektowe\\Projects\\eeg\\eegpliki\\usereeg.db";
        String select = "SELECT image FROM user_eeg WHERE username = ? AND electrode_number = ?";
        String image;

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(select)) {

            stmt.setString(1, username);
            stmt.setInt(2, electrode_number);

            ResultSet result = stmt.executeQuery();

            //while(result.next()) {
                image = result.getString("image");
                model.addAttribute("image", image);
            //}

            //System.out.println("Ok");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "eegimage";
    }

}
