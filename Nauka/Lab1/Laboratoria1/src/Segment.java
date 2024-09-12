import java.text.Format;
import java.util.Locale;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Segment {
    private Point point1;
    private Point point2;

    public Segment(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public double length() {
        return sqrt(pow((point2.x - point1.x), 2) + pow((point2.y - point1.y), 2));
    }

    public String toSvg() {
        return String.format(Locale.ENGLISH, "<line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" style=\"stroke:red;stroke-width:2\" />", point1.x, point1.y, point2.x, point2.y);
    }

    public static Segment[] perpendicularTo(Segment s, Point p) {
        double dx = s.point2.x - s.point1.x;
        double dy = s.point2.y - s.point1.y;

        return new Segment[] {
                new Segment(p, new Point(
                        p.x - dy, p.y + dx
                )),
                new Segment(p, new Point(
                        p.x + dy, p.y - dx
                )),
        };
    }
}
