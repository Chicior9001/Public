import java.time.LocalTime;

public abstract class ClockHand {
    public abstract void setTime(LocalTime localTime);
    public abstract String toSvg();
}
