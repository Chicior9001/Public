import org.example.client.Client;
import org.example.databasecreator.Creator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SendDataTest {
    @BeforeAll
    static void openDatabase() throws SQLException {
        Creator.main(new String[]{});
    }

    @ParameterizedTest
    @CsvFileSource(resources = "test.csv", numLinesToSkip = 1)
    void testSendData(String username, String path, Integer electrode, String image) {
        Client client = new Client();
        client.sendData(username, path);

        // Sprawdzanie danych w bazie danych
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:D:\\Studia\\Semestr II\\Programowanie obiektowe\\Projects\\eeg\\eegpliki\\usereeg.db")) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM user_eeg WHERE username='" + username + "'")) {

                    while (rs.next()) {
                        String dbUsername = rs.getString("username");
                        int electrodeNumber = rs.getInt("electrode_number");
                        String imageBase64 = rs.getString("image");

                        assertEquals(username, dbUsername);
                        assertEquals(electrode, electrodeNumber);
                        assertEquals(image, imageBase64);
                    }

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
