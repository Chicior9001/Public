import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, City> cities = City.parseFile("E:\\Studia\\Semestr II\\Programowanie obiektowe\\Nauka\\Kolokwium 1\\Kolokwium 1\\strefy.csv");
        DigitalClock clock = new DigitalClock(cities.get("Lublin"), DigitalClock.ClockType.H24);
        clock.setCity(cities.get("Lublin"));
        // clock.setTime(12,0,0);
        // System.out.println( cities.get("Lublin").localMeanTime(clock));
        // System.out.println(clock.toString());

        List<City> cityList = new ArrayList<>(cities.values());
        // List<City> sortedCities = City.worstTimeFit(cityList);
        /*for (City city : sortedCities) {
            System.out.println(city);
        }*/

        List<ClockHand> clockHands = new ArrayList<>();
        clockHands.add(new HourHand());
        clockHands.add(new MinuteHand());
        clockHands.add(new SecondHand());

        AnalogClock aClock = new AnalogClock(cities.get("Lublin"), clockHands);
        aClock.setCity(cities.get("Lublin"));
        City.generateAnalogClocksSvg(cityList, aClock);
        // aClock.toSvg("E:\\Studia\\Semestr II\\Programowanie obiektowe\\Nauka\\Kolokwium 1\\Kolokwium 1\\file.svg");

        // SecondHand secondHand = new SecondHand();

        // Set the current time to the second hand
        // secondHand.setTime(LocalTime.now());

        // Get the SVG representation of the second hand
        // String svg = secondHand.toSvg();

        // Print or use the SVG string
        // System.out.println(svg);



        // System.out.println(cities.get("Abu Dhabi"));
    }
}