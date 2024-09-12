import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

public class AnalogClock extends Clock {
    private final List<ClockHand> hands;
    private LocalTime currentTime = LocalTime.of(hour, minute, second);
    public AnalogClock(City city, List<ClockHand> hands) {
        super(city);
        this.hands = hands;
    }

    public void toSvg(String path) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        hands.get(0).setTime(currentTime);
        hands.get(1).setTime(currentTime);
        hands.get(2).setTime(currentTime);
        bw.write(String.format(Locale.ENGLISH, "<svg width=\"200\" height=\"200\" viewBox=\"-100 -100 200 200\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "  <!-- Tarcza zegara -->\n" +
                "  <circle cx=\"0\" cy=\"0\" r=\"90\" fill=\"none\" stroke=\"black\" stroke-width=\"2\" />\n" +
                "  <g text-anchor=\"middle\">\n" +
                "    <text x=\"0\" y=\"-80\" dy=\"6\">12</text>\n" +
                "    <text x=\"80\" y=\"0\" dy=\"4\">3</text>\n" +
                "    <text x=\"0\" y=\"80\" dy=\"6\">6</text>\n" +
                "    <text x=\"-80\" y=\"0\" dy=\"4\">9</text>\n" +
                "  </g>\n" +
                hands.get(0).toSvg() + hands.get(1).toSvg() + hands.get(2).toSvg() + "\n" +
                "</svg>"));
        bw.flush();
    }
}
