import java.util.ArrayList;
import java.util.Date;

public class Sphere implements Element {

    public double R;

    public Point center;

    public ArrayList<Element> neighbours = new ArrayList<>();

    public ArrayList<Element> getNeighbours() {
        return this.neighbours;
    }

    public void addNeighbour(Element sphere) {
        this.neighbours.add(sphere);
    }

    public void printInfo(){
        System.out.println("Info: " + this.R + "   " );

    }


    public static Sphere getRandomElement(double R) {
        double x = Utils.getRandomNumberInRange(Utils.LIMIT_X, 0.0);
        double y = Utils.getRandomNumberInRange(Utils.LIMIT_Y, 0.0);
        double z = Utils.getRandomNumberInRange(Utils.LIMIT_Z, 0.0);
        Point point = new Point(x, y, z);
        return new Sphere(point, R);
    }

    public boolean intersectsWith(Element element) {
        Sphere sphere = (Sphere) element;
        if (sphere.center.equals(Utils.lowBoundaryForSphere.center)) {
            return this.center.z < this.R;
        }

        if (sphere.center.equals(Utils.upperBoundaryForSphere.center)) {
            return Utils.LIMIT_Z - this.center.z < this.R;
        }

        if (this.center.equals(Utils.lowBoundaryForSphere.center)) {
            return sphere.center.z < sphere.R;
        }

        if (this.center.equals(Utils.upperBoundaryForSphere.center)) {
            return Utils.LIMIT_Z - sphere.center.z < sphere.R;
        }

        return this.center.getDistanceToPoint(sphere.center) < 2 * R;
    }

    public static ArrayList<Element> getSpheres(int numberOfElements, double R) {

        ArrayList<Element> spheres = new ArrayList<>();
        //System.out.println("Заполняем систему");
        long start = new Date().getTime();
        for (int i = 0; i < numberOfElements; ++i) {
            Element newSphere = Sphere.getRandomElement(R);
            for (Element sphere : spheres) {
                if (sphere.intersectsWith(newSphere)) {
                    sphere.addNeighbour(newSphere);
                    newSphere.addNeighbour(sphere);
                }
            }

            if (Utils.lowBoundaryForSphere.intersectsWith(newSphere)) {
                newSphere.addNeighbour(Utils.lowBoundaryForSphere);
            }

            if (Utils.upperBoundaryForSphere.intersectsWith(newSphere)) {
                newSphere.addNeighbour(Utils.upperBoundaryForSphere);
            }

            spheres.add(i, newSphere);
            /*Sphere sr = (Sphere) newSphere;
            System.out.println("//////////");
            System.out.println(sr.center.x);
            System.out.println(sr.center.y);
            System.out.println(sr.center.z);
            System.out.println(sr.R);*/
        }
        /*long end = new Date().getTime();
        double time = (end - start) / 1000.0 / 60.0;
        System.out.println("Система заполнялась " + time + " минут");*/
        return spheres;
    }


    public Sphere(Point point, double R) {
        this.center = point;
        this.R = R;
    }

}
