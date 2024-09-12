import java.io.*;
import java.util.*;

public class City {
    private String capital;
    protected double timeZone;
    private String latitude;
    private Double longitude;

    public City(String capital, double timeZone, String latitude, Double longitude) {
        this.capital = capital;
        this.timeZone = timeZone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private static City parseLine(String line) {
        String[] parts = line.split(",");

        String[] longitudeParts = parts[3].trim().split(" ");
        Double longitude = Double.parseDouble(longitudeParts[0]);
        if(longitudeParts[1].equals("E")) {
            longitude = Math.abs(longitude);
        } else if(longitudeParts[1].equals("W")) {
            longitude = -Math.abs(longitude);
        }

        return new City(parts[0], Double.parseDouble(parts[1].trim()), parts[2], longitude);
    }

    public static Map<String, City> parseFile(String path) throws IOException {
        Map<String, City> cities = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        br.readLine();
        while((line = br.readLine()) != null) {
            City city = parseLine(line);
            cities.put(city.capital, city);
        }
        return cities;
    }

    public String localMeanTime(Clock clock) {
        int offset = (int) (longitude * 240);

        int time = clock.hour * 3600 + clock.minute * 60 + clock.second;
        time += offset;

        int hour = time / 3600;
        int minute = time % 3600 / 60;
        int second = time % 60;

        return hour + ":" + minute + ":" + second;
    }

    public static List<City> worstTimeFit(List<City> cities) {
        cities.sort((city1, city2) -> {
            double offset1 = city1.longitude * 240;
            double difference1 = Math.abs(city1.timeZone * 3600 - offset1);

            double offset2 = city2.longitude * 240;
            double difference2 = Math.abs(city2.timeZone * 3600 - offset2);

            return Double.compare(difference2, difference1);
        });
        return cities;
    }

    public static void generateAnalogClocksSvg(List<City> cities, AnalogClock analogClock) throws IOException {
        String catalogName = "";
        catalogName = "time";
        // catalogName = analogClock.toString();
        File catalog = new File("E:\\Studia\\Semestr II\\Programowanie obiektowe\\Nauka\\Kolokwium 1\\Kolokwium 1\\test\\" + catalogName);

        if(catalog.mkdirs()) {
            System.out.println("Folder created: " + catalog.getAbsolutePath());
        } else {
            System.out.println("Folder creation failed or folder already exists.");
        }

        for(City city : cities) {
            String path = catalog.getAbsolutePath() + "\\" + city.capital + ".svg";
            analogClock.setCity(city);
            analogClock.toSvg(path);
        }
    }

    @Override
    public String toString() {
        return "City{" +
                "capital='" + capital + '\'' +
                ", timeZone=" + timeZone +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
