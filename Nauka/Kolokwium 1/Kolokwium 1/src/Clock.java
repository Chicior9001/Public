import java.util.Date;

public abstract class Clock {
    protected int hour;
    protected int minute;
    protected int second;
    private City city;

    public Clock(City city) {
        this.city = city;
    }

    public void setCity(City city) {
        this.city = city;
        setCurrentTime();
        int integerPart = (int) city.timeZone;
        int newHour = hour + integerPart;
        int newMinute = minute + (int) city.timeZone - integerPart;
        setTime(newHour, newMinute, second);
    }

    public void setCurrentTime() {
        Date date = new Date();
        hour = date.getHours();
        minute = date.getMinutes();
        second = date.getSeconds();
    }

    public void setTime(int hour, int minute, int second) {
        if (hour > 24 || hour < 0) {
            throw new IllegalArgumentException("Godzina nie mieści się w zakresie zegara 24-godzinnego");
        } else if (minute > 59 || minute < 0) {
            throw new IllegalArgumentException("Minuta nie mieści się w zakresie zegara 24-godzinnego");
        } else if (second > 59 || second < 0) {
            throw new IllegalArgumentException("Sekunda nie mieści się w zakresie zegara 24-godzinnego");
        } else {
            this.hour = hour;
            this.minute = minute;
            this.second = second;
        }
    }

    public String toString() {
        // return hour + ":" + minute + ":" + second;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
