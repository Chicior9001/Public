import java.time.LocalTime;
import java.util.Locale;

public class SecondHand extends ClockHand {
    private int angle;
    @Override
    public void setTime(LocalTime localTime) {
        angle = localTime.getSecond() * 6;
    }

    @Override
    public String toSvg() {
        return String.format(Locale.ENGLISH, "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-80\" stroke=\"red\" stroke-width=\"1\" transform=\"rotate(%d)\"/>", angle);
    }
}
