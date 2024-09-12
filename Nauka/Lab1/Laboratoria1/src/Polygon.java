import java.util.ArrayList;
import java.util.Locale;

public class Polygon {
    private ArrayList<Point> points;

    public Polygon(ArrayList<Point> points) {
        this.points = points;
    }

    public String toSvg() {
        String string = "";
        for (Point p : points) {
            string += String.format(Locale.ENGLISH, "%.2f,%.2f ", p.x, p.y);
        }
        return String.format(Locale.ENGLISH, "<polygon points=\"%s\"/>", string);
    }
    public Polygon(Polygon p) {
        this.points = p.points;
    }
}
