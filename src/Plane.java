public class Plane {

    public double A;
    public double B;
    public double C;
    public double D;

    public Plane(Point m0, Point m1, Point m2) {
        this.A = (m1.y - m0.y) * (m2.z - m0.z) - (m1.z - m0.z) * (m2.y - m0.y);
        this.B = (m0.x - m1.x) * (m2.z - m0.z) + (m2.x - m0.x) * (m1.z - m0.z);
        this.C = (m1.x - m0.x) * (m2.y - m0.y) + (m0.x - m2.x) * (m1.y - m0.y);
        this.D = -m0.x * A - m0.y * B - m0.z * C;
    }

    public Plane(double A, double B, double C, double D) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
    }

}
