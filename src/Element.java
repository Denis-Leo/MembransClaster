import java.util.ArrayList;

public interface Element {

    static Element getLowBoundary(int flag) {
        switch (flag) {
            case 1:
                return Utils.lowBoundary;
            case 2:
                return Utils.lowBoundaryForSphere;
        }
        return Utils.lowBoundary;
    }

    static Element getUpperBoundary(int flag) {
        switch (flag) {
            case 1:
                return Utils.upperBoundary;
            case 2:
                return Utils.upperBoundaryForSphere;
        }
        return Utils.upperBoundary;
    }




    ArrayList<Element> getNeighbours();


    boolean intersectsWith(Element element);

    void addNeighbour(Element element);


    void printInfo();


}


