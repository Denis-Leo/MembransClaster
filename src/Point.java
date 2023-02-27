import java.util.ArrayList;

public class Point {

    public double x;
    public double y;
    public double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void getCoord() {
        System.out.print("| " + x);
        System.out.print("| " + y);
        System.out.print("| " + z);
    }

    /* Метод для определения расстояния между текущей точкой и переданной*/
    public double getDistanceToPoint(Point point) {
        double distance = Math.sqrt(Math.pow(point.x - this.x, 2) +
                Math.pow(point.y - this.y, 2) +
                Math.pow(point.z - this.z, 2));
        return distance;
    }

    /* Метод для определения расстояния между текущей точкой и переданной плоскостью*/
    public double getDistanceToPlane(Plane plane) {
        return Math.abs(plane.A * this.x + plane.B * this.y + plane.C * this.z + plane.D) /
                Math.sqrt(Math.pow(plane.A, 2) + Math.pow(plane.B, 2) + Math.pow(plane.C, 2));

    }

    /* Метод для определения принадлежности текущей точки, переданному параллелепипеду*/
    public boolean belongsToParallelepiped(Parallelepiped parallelepiped) {
        double a = (parallelepiped.point1).getDistanceToPoint(parallelepiped.point2);
        double b = (parallelepiped.point2).getDistanceToPoint(parallelepiped.point3);
        double c = (parallelepiped.point1).getDistanceToPoint(parallelepiped.point5);
        ArrayList<Plane> facesOfParallelepiped = parallelepiped.getFaces();

        if (Utils.isEquals(this.getDistanceToPlane(facesOfParallelepiped.get(0)) +
                this.getDistanceToPlane(facesOfParallelepiped.get(2)), b)
                && Utils.isEquals(this.getDistanceToPlane(facesOfParallelepiped.get(1)) +
                this.getDistanceToPlane(facesOfParallelepiped.get(3)), a)
                && Utils.isEquals(this.getDistanceToPlane(facesOfParallelepiped.get(4)) +
                this.getDistanceToPlane(facesOfParallelepiped.get(5)), c)) {
            return true;
        }

        return false;
    }

    public boolean equals(Point point) {
        return Utils.isEquals(this.x, point.x) && Utils.isEquals(this.y, point.y) && Utils.isEquals(this.z, point.z);
    }


    public void rotation(double angle, int axis){
        double x_new = this.x;
        double y_new = this.y;
        double z_new = this.z;

        if (axis == 1){
            x_new = this.x;
            y_new = this.y*Math.cos(angle) - this.z*Math.sin(angle);
            z_new = this.y*Math.sin(angle) + this.z*Math.cos(angle);
        }
        


        this.x = x_new;
        this.y = y_new;
        this.z = z_new;
        
    }

}
