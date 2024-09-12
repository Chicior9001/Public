public class DigitalClock extends Clock {
    public enum ClockType {H12, H24}
    private ClockType clockType;

    public DigitalClock(City city, ClockType clockType) {
        super(city);
        this.clockType = clockType;
    }

    @Override
    public String toString() {
        int newHour;
        if(clockType == ClockType.H12) {
            if((newHour = hour % 12) == 0) {
                newHour = 12;
            }

            if(hour < 12) {
                // return newHour + ":" + minute + ":" + second + " AM";
                return String.format("%2d:%02d:%02d AM", newHour, minute, second);
            } else {
                // return newHour + ":" + minute + ":" + second + " PM";
                return String.format("%2d:%02d:%02d PM", newHour, minute, second);
            }
        } else {
            return super.toString();
        }
    }
}
