import java.util.Locale;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Segment {
    private Point start;
    private Point end;

    public Segment(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public double length() {
        return (double) sqrt(pow(end.x, start.x) + pow(end.y, start.y));
    }

    public String toSvg() {
        return String.format(Locale.ENGLISH, "<line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" style=\"stroke:red;stroke-width:2\"/>", start.x,start.y, end.x,end.y);
    }

    public static Segment[] perpendicular(Segment s, Point p) {
        double dx = s.end.x - s.start.x;
        double dy = s.end.y - s.start.y;

        double x1 = p.x - dy;
        double y1 = p.y + dx;

        double x2 = p.x + dy;
        double y2 = p.y - dx;

        return new Segment[] {
                new Segment(p, new Point(x1, y1)),
                new Segment(p, new Point(x2, y2)),
        };
    }
}
