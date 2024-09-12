import java.util.ArrayList;

import static java.lang.Math.pow;

public class Main {
    public static void main(String[] args) {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(100, 10));
        points.add(new Point(150, 190));
        points.add(new Point(50, 190));
        Polygon polygon = new Polygon(points);
        System.out.println(polygon.toSvg());
    }
}