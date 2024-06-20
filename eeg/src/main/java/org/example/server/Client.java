package org.example.server;

import org.example.databasecreator.Creator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Base64;

public class Client implements Runnable{
    private List<List<Float>> data = new ArrayList<>(); // lista list floatów, lista składa się z linii które są podzielone potem na floaty
    private BufferedReader reader;
    private String username;
    private Integer electrode_number;
    private String encoded_image;

    String url = "jdbc:sqlite:D:\\Studia\\Semestr II\\Programowanie obiektowe\\Projects\\eeg\\eegpliki\\usereeg.db"; // ścieżka do podłączenia się do bazy


    public Client(Socket socket) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // nawiązuje połączenie z serwerem
    }

    private void parseMessage(String message)
    {
        //System.out.println(message);
        List<Float> lineData = Arrays.stream(message.split(",")).map(Float::parseFloat).toList(); // /tworzy listę floatów, które zostały podzielone w otrzymanej linii
        data.add(lineData); // dodaje tą listę do listy list floatów (wypełnia listę list, listami zawierającymi floaty)

    }

    public void generate(int index) throws IOException {
        List<Float> dataLine = data.get(index); // tworzy kolejną listę floatów do którą od razu uzupełnia floatami z listy zapisanej w liście list
        BufferedImage image = new BufferedImage(dataLine.size(), 140, BufferedImage.TYPE_INT_ARGB); // tworzy szablon na obrazek o szerokości ilości floatów i wysokości 140, ustawia na typ RGB z przeźroczystością (png)
        for(int i = 0; i < dataLine.size(); i++) { // leci po każdym floacie
            int y0 = image.getHeight() / 2; // ustawia y0 na połowę wysokości szablonu
            int y = (int) (-dataLine.get(i) + y0); // ustawia spółrzędną y na sumę zanegowanego floata i polowy wysokości szablonu (czyli ustawia wysokość na powyżej lub poniżej połowy wysokości szablonu zależnie czy float jest ujemny czy dodatni)
            image.setRGB(i, y, 0xffff0000); // ustawia pixel o współrzędnych  i oraz y na kolor czerwony (0xffff0000 -> A=255(kanał peźroczystości) R=255 G=0 B=0) (stawia czerwoną krope)
        }
        encoded_image = encodeBase64(image); // zakodowuje obrazek jako string znaków
        //System.out.println(encoded_image);
        //ImageIO.write(image, "png", new File("/tmp/image.png"));

        File file = new File("D:\\Studia\\Semestr II\\Programowanie obiektowe\\Projects\\eeg\\data.txt"); // otwiera plik data.txt
        PrintWriter writer = new PrintWriter(new FileWriter(file)); // tworzy PrintWriter do zapisu do pliku
        writer.println(encoded_image); // zapisuje w pliku zakodowany obrazek
        writer.close(); // zamyka PrintWitera

        //System.out.println(encoded_image);
        electrode_number = index;
        this.insert(url); // wstawia do bazy danych wiersz (username, electrode_number, encoded_image)

        System.out.println("line");
    }

    private static String encodeBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // tworzy sobie jakieś dziwne coś do trzymania obrazka
        ImageIO.write(image, "png", outputStream); // zapisuje obrazek w formacie png do tego dziwnego cosia
        String base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray()); // koduje to dziwne coś na string postaci base64 i zapisuje do Stringa base64Image
        return base64Image; // zwraca zakodowany string obrazka
    }

    public void insert(String url){
        String insertData = "INSERT INTO user_eeg(username, electrode_number, image) VALUES("
                + username + ", "
                + electrode_number + ", "
                + encoded_image
                + ");";

        String insertData2 = "INSERT INTO user_eeg(username, electrode_number, image) VALUES(?, ?, ?)"; // polecenie sql które wsadza dane do tabeli user_eeg do kolumn username, electrode_number oraz image

        try (Connection conn = DriverManager.getConnection(url); // podłącza się do bazy
             PreparedStatement stmt = conn.prepareStatement(insertData2)) { // tworzy PreparedStatement z naszego polecenia sql
            stmt.setString(1, username); // do pierwszego ? daje username
            stmt.setInt(2, electrode_number); // do drugiego
            stmt.setString(3, (encoded_image)); // itd.
            stmt.executeUpdate(); // wykonuje polecenie
            //stmt.execute(insertData);
            // gdzieś tu chyba powinno byc jeszcze polecenie do zamknięcia połączenia ale chyba jako że jest w try/catch to nie musimy?
            System.out.println("Ok");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        String message;
        try {
            username = reader.readLine(); // odbierz pierwszą linię, którą w tym przypadku jest username
            while ((message = reader.readLine())!= null) { // dal każdej następnej przyjetej linii, dopóki nie jest ona pusta
                parseMessage(message); // podziel linię na floaty, wrzuć do listy i tą listę do listy list
                generate(data.size() - 1); // wygeneruj obrazek dla każdej nowo dodanje listy i wyślij do bazy
            }
            //this.leave();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}