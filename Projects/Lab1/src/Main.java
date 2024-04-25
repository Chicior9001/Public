import java.util.Locale;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Point point = new Point(200, 500);
        System.out.println(point);

        Segment segment = new Segment(point, new Point(600, 400));

//        System.out.println(segment.length());
        /*System.out.println(segment.toSvg());

        Segment[] perpendicular = Segment.perpendicular(segment, point);
        System.out.println(perpendicular[0].toSvg());
        System.out.println(perpendicular[1].toSvg());*/

        Polygon polygon = new Polygon(new Point[] {
                new Point (Math.random() * 999 + 1, Math.random() * 999 + 1),
                new Point (Math.random() * 999 + 1, Math.random() * 999 + 1),
                new Point (Math.random() * 999 + 1, Math.random() * 999 + 1),
                new Point (Math.random() * 999 + 1, Math.random() * 999 + 1),
                new Point (Math.random() * 999 + 1, Math.random() * 999 + 1),
                new Point (Math.random() * 999 + 1, Math.random() * 999 + 1),
                new Point (Math.random() * 999 + 1, Math.random() * 999 + 1)
        });
        System.out.println(polygon.toSvg());

    }
}