import java.util.Locale;

public class Polygon {
    private Point[] points;

    public Polygon(Point[] points) {
        this.points = points;
    }

    public String toSvg() {
        String corner = "";
        for (int i = 0; i < points.length; i++) {
            corner += String.format(Locale.ENGLISH, "%f,%f ", points[i].x, points[i].y);
        }
        return String.format(Locale.ENGLISH, "<polygon points=\"%s\" style=\"fill:lime;stroke:purple;stroke-width:3\"/>", corner);
    }

    public Polygon(Polygon polygon) {
        this.points = new Point[polygon.points.length];
        for (int i = 0; i < polygon.points.length; i++) {
            this.points[i] = new Point(polygon.points[i].x, polygon.points[i].y);
        }
    }
}
