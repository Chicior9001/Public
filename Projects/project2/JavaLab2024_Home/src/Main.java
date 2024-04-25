public class Main {
    public static void main(String[] args) {
        Point point;
       /* point = new Point(24, 55);
        System.out.println(point);

        Segment seg = new Segment(point, new Point(67, 85));

//        System.out.println(seg.length());
        System.out.println(seg.toSvg());

        Segment[] p_seg = Segment.perpendicularTo(seg, point);
        System.out.println(p_seg[0].toSvg());
        System.out.println(p_seg[1].toSvg());*/

        Polygon poly = new Polygon(new Point[] {
                new Point (30, 70),
                new Point (60, 80),
                new Point (50, 40)
        });
        System.out.println(poly.toSvg());

        Polygon poly2 = new Polygon(poly);
        System.out.println(poly2.toSvg());


    }
}