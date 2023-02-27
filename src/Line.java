public class Line {

    public double x0;
    public double y0;
    public double z0;
    public double p1;
    public double p2;
    public double p3;

    public Line(Point m1, Point m2) {
        this.x0 = m1.x;
        this.y0 = m1.y;
        this.z0 = m1.z;
        this.p1 = m2.x - m1.x;
        this.p2 = m2.y - m1.y;
        this.p3 = m2.z - m1.z;
    }

    public Line(double x0, double y0,double z0,double p1,double p2,double p3) {
        this.x0 = x0;
        this.y0 = y0;
        this.z0 = z0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    /* Метод для определения точки пересечения текущей прямой и переданной плоскости*/
    public Point getIntersectionPointWithPlane(Plane plane) {

        Point point = null;
        double detA = p2 * (p3 * plane.C + p2 * plane.B) +
                p1 * p2 * plane.A;

        if (!Utils.isEquals(detA, 0.0)) {
            double detA1 = (p2 * x0 - p1 * y0) * (p3 * plane.C + p2 * plane.B) +
                    p1 * (plane.C * (p3 * y0 - p2 * z0) - p2 * plane.D);
            double detA2 = p2 * (plane.C * (p3 * y0 - p2 * z0) - p2 * plane.D) -
                    (p2 * x0 - p1 * y0) * plane.A * p2;
            double detA3 = p2 * (-p3 * plane.D - plane.B * (p3 * y0 - p2 * z0)) -
                    p1 * plane.A * (p3 * y0 - p2 * z0) - (p2 * x0 - p1 * y0) * plane.A * p3;

            double x = detA1 / detA;
            double y = detA2 / detA;
            double z = detA3 / detA;
            point = new Point(x, y, z);
        }
        return point;
    }
}
