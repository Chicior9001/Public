import java.time.LocalTime;
import java.util.Locale;

public class MinuteHand extends ClockHand{
    private int angle;
    @Override
    public void setTime(LocalTime localTime) {
        angle = localTime.getSecond() * 6;
    }

    @Override
    public String toSvg() {
        return String.format(Locale.ENGLISH, "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-80\" stroke=\"blue\" stroke-width=\"3\" transform=\"rotate(%d)\"/>", angle);
    }
}
