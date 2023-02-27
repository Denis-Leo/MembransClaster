import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import java.io.*;


public class Parallelepiped implements Element {

    Point point1;
    Point point2;
    Point point3;
    Point point4;
    Point point5;
    Point point6;
    Point point7;
    Point point8;
    public ArrayList<Element> neighbours = new ArrayList<>();

    public Point center;

    public double radius;

    ArrayList<Line> edgesOfParallelepiped = null;
    ArrayList<Plane> facesOfParallelepiped = null;

    public ArrayList<Element> getNeighbours() {
        return this.neighbours;
    }

    public Parallelepiped(Point point1, Point point2, Point point3, Point point4,
                          Point point5, Point point6, Point point7, Point point8) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
        this.point5 = point5;
        this.point6 = point6;
        this.point7 = point7;
        this.point8 = point8;
        Vector vector = new Vector(point1, point7);
        Vector normalizedVector = vector.getNormalVectorTo(vector.getVectorLength() / 2);
        this.center = new Point(normalizedVector.a + point1.x, normalizedVector.b + point1.y, normalizedVector.c + point1.z);
        this.radius = vector.getVectorLength() / 2;

    }


    public void addTextToExportFile(String text, String name, boolean rewrite){

        try(FileWriter writer = new FileWriter(name, rewrite))
        {
            // запись всей строки
            writer.write(text);
            // запись по символам
            writer.append('\n');
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }



    }

    public void printInfo(){
        String text ;
        String SPACE = "  " ;

        text =  this.center.x + SPACE+ this.center.y + SPACE+ this.center.z + SPACE ;
        text += (new Vector(this.point1, this.point5).getPhi()/Math.PI*180 +
                SPACE + new Vector(this.point1, this.point5).getTheta()/Math.PI*180 + SPACE + 
                new Vector(this.point1, this.point4).getPhi()/Math.PI*180 + SPACE ) ;

    //    System.out.println(text);


       ExportFile.addTextToExportFile(text, false);




        //p = this.point2;
        //System.out.println(p.x,p.y,p.z);


    }



    
    /* Метод для поворота параллелепипеда относительно какой то из координатных  
    осей x,y,z (1,2,3)*/
    public Parallelepiped rotation(double angle, int axis){

        Point p1 = this.point1.rotation(angle,axis);
        Point p2 = this.point2.rotation(angle,axis);
        Point p3 = this.point3.rotation(angle,axis);
        Point p4 = this.point4.rotation(angle,axis);
        Point p5 = this.point5.rotation(angle,axis);
        Point p6 = this.point6.rotation(angle,axis);
        Point p7 = this.point7.rotation(angle,axis);
        Point p8 = this.point8.rotation(angle,axis);
        return new Parallelepiped(p1,p2,p3,p4,p5,p6,p7,p8);
    }

    
    public Parallelepiped move(Vector vector){

        Point p1 = this.point1.move(vector);
        Point p2 = this.point2.move(vector);
        Point p3 = this.point3.move(vector);
        Point p4 = this.point4.move(vector);
        Point p5 = this.point5.move(vector);
        Point p6 = this.point6.move(vector);
        Point p7 = this.point7.move(vector);
        Point p8 = this.point8.move(vector);
        return new Parallelepiped(p1,p2,p3,p4,p5,p6,p7,p8);
    }



    /* Метод для получения вершин параллелепипеда*/
    public ArrayList<Point> getVerticles() {
        ArrayList<Point> verticles = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                String fieldName = field.getName();
                if (!field.isAccessible() && !fieldName.equals("neighbours") &&
                        !fieldName.equals("center") && !fieldName.equals("radius") &&
                        !fieldName.equals("edgesOfParallelepiped") && !fieldName.equals("facesOfParallelepiped")) {
                    field.setAccessible(true);
                }
                if (!fieldName.equals("neighbours") && !fieldName.equals("center") && !fieldName.equals("radius") &&
                    !fieldName.equals("edgesOfParallelepiped")&& !fieldName.equals("facesOfParallelepiped")) {
                    Point value = (Point) field.get(this);
                    verticles.add(value);
                }
            }
        } catch (Exception e) {
            System.out.println("method = getVerticles");
        }

        return verticles;
    }

    /* Метод для получения ребер параллелепипеда*/
    public ArrayList<Line> getEdges() {

        if (this.edgesOfParallelepiped != null) {
            return this.edgesOfParallelepiped;
        }
        ArrayList<Point> verticlesOfParallelepiped = this.getVerticles();
        ArrayList<Line> edgesOfParallelepiped = new ArrayList<>();

        for (int i = 0; i < verticlesOfParallelepiped.size(); ++i) {
            if (i != 0) {
                if (i == 3 || i == 7) {
                    edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(i), verticlesOfParallelepiped.get(i - 3)));
                } else {
                    edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(i), verticlesOfParallelepiped.get(i - 1)));
                }
            }

            if (i < 4) {
                edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(i), verticlesOfParallelepiped.get(i + 4)));
            }
        }
        /*edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(0), verticlesOfParallelepiped.get(1)));
        edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(1), verticlesOfParallelepiped.get(2)));
        edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(2), verticlesOfParallelepiped.get(3)));
        edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(3), verticlesOfParallelepiped.get(0)));
        edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(0), verticlesOfParallelepiped.get(4)));
        edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(1), verticlesOfParallelepiped.get(5)));
        edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(2), verticlesOfParallelepiped.get(6)));
        edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(3), verticlesOfParallelepiped.get(7)));
        edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(4), verticlesOfParallelepiped.get(5)));
        edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(5), verticlesOfParallelepiped.get(6)));
        edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(6), verticlesOfParallelepiped.get(7)));
        edgesOfParallelepiped.add(new Line(verticlesOfParallelepiped.get(7), verticlesOfParallelepiped.get(4)));*/
        this.edgesOfParallelepiped = edgesOfParallelepiped;
        return edgesOfParallelepiped;
    }

    /* Метод для получения граней параллелепипеда*/
    public ArrayList<Plane> getFaces() {
        if(this.facesOfParallelepiped != null){
            return this.facesOfParallelepiped;
        }
        ArrayList<Point> verticlesOfParallelepiped = this.getVerticles();
        ArrayList<Plane> facesOfParallelepiped = new ArrayList<>();
        /*Параллельные плоскости под индексами 0, 2; 1, 3; 4, 5*/
        for (int i = 0; i < 4; ++i) {
            facesOfParallelepiped.add(new Plane(verticlesOfParallelepiped.get(i),
                    verticlesOfParallelepiped.get(i + 1),
                    verticlesOfParallelepiped.get(i + 4)));

        }

        facesOfParallelepiped.add(new Plane(verticlesOfParallelepiped.get(0),
                verticlesOfParallelepiped.get(1),
                verticlesOfParallelepiped.get(2)));
        facesOfParallelepiped.add(new Plane(verticlesOfParallelepiped.get(4),
                verticlesOfParallelepiped.get(5),
                verticlesOfParallelepiped.get(6)));
        this.facesOfParallelepiped = facesOfParallelepiped;
        return facesOfParallelepiped;
    }

    /* Метод для определения, пересекается ли текущий параллелепипед с переданным*/
    public boolean intersectsWith(Element parallelepiped) {
        boolean result = false;
        Parallelepiped parallelepiped2 = (Parallelepiped) parallelepiped;
        Point intersectionPoint = null;
        Parallelepiped parallelepiped1 = this;
        if (parallelepiped1.center.getDistanceToPoint(parallelepiped2.center) > parallelepiped2.radius + parallelepiped1.radius) {
            return false;
        }

        ArrayList<Line> edgesOfParallelepiped1 = parallelepiped1.getEdges();
        ArrayList<Plane> facesOfParallelepiped2 = parallelepiped2.getFaces();

        for (Line edge : edgesOfParallelepiped1) {
            for (Plane face : facesOfParallelepiped2) {
                intersectionPoint = edge.getIntersectionPointWithPlane(face);
                if (intersectionPoint != null && intersectionPoint.belongsToParallelepiped(parallelepiped1)
                        && intersectionPoint.belongsToParallelepiped(parallelepiped2)) {
                    return true;
                }
            }
        }

        edgesOfParallelepiped1 = parallelepiped2.getEdges();
        facesOfParallelepiped2 = parallelepiped1.getFaces();
        for (Line edge : edgesOfParallelepiped1) {
            for (Plane face : facesOfParallelepiped2) {
                intersectionPoint = edge.getIntersectionPointWithPlane(face);
                if (intersectionPoint != null && intersectionPoint.belongsToParallelepiped(parallelepiped1)
                        && intersectionPoint.belongsToParallelepiped(parallelepiped2)) {
                    return true;
                }
            }
        }

        return result;
    }

    public boolean intersectsWithTheUpperBoundary() {
        boolean result = false;
        ArrayList<Point> verticles = this.getVerticles();
        for (Point point : verticles) {
            if (point.z > Utils.LIMIT_Z || Utils.isEquals(point.z, Utils.LIMIT_Z)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean intersectsWithTheLowerBoundary() {
        boolean result = false;
        ArrayList<Point> verticles = this.getVerticles();
        for (Point point : verticles) {
            if (point.z < 0.0 || Utils.isEquals(point.z, 0.0)) {
                result = true;
                break;
            }
        }
        return result;
    }


    public Parallelepiped getExternalParallelepiped(double t) {
        ArrayList<Point> verticles = this.getVerticles();
        /*Не стал писать цикл,чтобы обойти все вершины и найти все точки, т.к. получилась бы
          слишком сложная логика, поэтому решил сделать в лоб и обойти все точки. Конечно, это
          ужасный код,но зато быстро и просто
         */
        Point point1 = this.getExternalPoint(t, verticles.get(0), verticles.get(1), verticles.get(4), verticles.get(3));
        Point point2 = this.getExternalPoint(t, verticles.get(1), verticles.get(2), verticles.get(0), verticles.get(5));
        Point point3 = this.getExternalPoint(t, verticles.get(2), verticles.get(1), verticles.get(6), verticles.get(3));
        Point point4 = this.getExternalPoint(t, verticles.get(3), verticles.get(2), verticles.get(0), verticles.get(7));
        Point point5 = this.getExternalPoint(t, verticles.get(4), verticles.get(5), verticles.get(0), verticles.get(7));
        Point point6 = this.getExternalPoint(t, verticles.get(5), verticles.get(1), verticles.get(6), verticles.get(4));
        Point point7 = this.getExternalPoint(t, verticles.get(6), verticles.get(5), verticles.get(2), verticles.get(7));
        Point point8 = this.getExternalPoint(t, verticles.get(7), verticles.get(4), verticles.get(3), verticles.get(6));
        return new Parallelepiped(point1, point2, point3, point4, point5, point6, point7, point8);

    }

    private Point getExternalPoint(double t, Point startPoint, Point point1, Point point2, Point point3) {
        Vector vector1 = new Vector(point1, startPoint);
        Vector vector2 = new Vector(point2, startPoint);
        Vector vector3 = new Vector(point3, startPoint);
        vector1 = vector1.getNormalVectorTo(t);
        vector2 = vector2.getNormalVectorTo(t);
        vector3 = vector3.getNormalVectorTo(t);
        Vector sumVector = vector1.getVectorsSum(vector2).getVectorsSum(vector3);
        return new Point(sumVector.a + startPoint.x, sumVector.b + startPoint.y, sumVector.c + startPoint.z);
    }

    public void addNeighbour(Element parallelepiped) {
        this.neighbours.add(parallelepiped);
    }



    public boolean equals(Parallelepiped parallelepiped) {
        ArrayList<Point> thisVerticles = this.getVerticles();
        ArrayList<Point> parallelepipedVerticles = parallelepiped.getVerticles();
        for (int i = 0; i < 8; ++i) {
            if (!thisVerticles.get(i).equals(parallelepipedVerticles.get(i))) {
                return false;
            }
        }

        return true;
    }


/* Create Parallelepiped  on zero point with aplpha angle  */
 public static Parallelepiped getDefineParallelepiped(double a, double b, double c,
    double alpha_x, double alpha_y, double alpha_z, Vector vect_cent ) {


            Point point1 = new Point(0, 0, 0);
           
            Point point2 = new Point(0, b, 0);
            
            Point point3 = new Point(a, b, 0);
           
            Point point4 = new Point(a, 0, 0);

            Point point5 = new Point(0, 0, c);
           
            Point point6 = new Point(0, b, c);
            
            Point point7 = new Point(a, b, c);
           
            Point point8 = new Point(a, 0, c);


            Parallelepiped par1 =  new Parallelepiped(point1, point2, point3, point4, point5, point6, point7, point8);
           
     
           Vector v = new Vector(-par1.center.x,-par1.center.y,-par1.center.z);

            par1 = par1.move(v);

            par1 = par1.rotation(alpha_z,3);
            par1 = par1.rotation(alpha_x,1);
            par1 = par1.rotation(alpha_y,2);
           


            par1 = par1.move(vect_cent);




        return par1;
    }





    public static Parallelepiped getRandomElement(double a, double b, double c) {
        // int flag_initial_distribution = 1;


            // /* Old from Barishev */
            // /* Создаем первую точку*/
            // double x = Utils.getRandomNumberInRange(Utils.LIMIT_X, 0.0);
            // double y = Utils.getRandomNumberInRange(Utils.LIMIT_Y, 0.0);
            // double z = Utils.getRandomNumberInRange(Utils.LIMIT_Z, 0.0);
            // Point point1 = new Point(x, y, z);
            // /*Создаем случайную 2-ю точку*/
            // x = Utils.getRandomNumberInRange(Utils.LIMIT_X, 0.0);
            // y = Utils.getRandomNumberInRange(Utils.LIMIT_Y, 0.0);
            // z = Utils.getRandomNumberInRange(Utils.LIMIT_Z, 0.0);
            // Point point2 = new Point(x, y, z);
            // /* Получаем точку в направлении вектора проведенного от точки 1 к точке 2 на расстоянии a*/
            // double length = point1.getDistanceToPoint(point2);
            // x = (point2.x - point1.x) * a / length + point1.x;
            // y = (point2.y - point1.y) * a / length + point1.y;
            // z = (point2.z - point1.z) * a / length + point1.z;
            // point2 = new Point(x, y, z);
            // /* Получаем случайный вектор, перпендикулярный 1-му вектору*/
            // Vector vector1 = new Vector(point2.x - point1.x, point2.y - point1.y, point2.z - point1.z);
            // Vector vector2 = vector1.getRandomPerpendicularNormalVector();
            // /* Находим точку вдоль 2-го вектора на расстоянии b от точки 1*/
            // x = vector2.a * b + point1.x;
            // y = vector2.b * b + point1.y;
            // z = vector2.c * b + point1.z;
            // Point point3 = new Point(x, y, z);
            // /* Получаем 3-й вектор перпендикулярный двум первым и строим 4 точку на расстоянии c от точки 1*/
            // Vector vector3 = vector1.getNormalVectorPerpendicularThisAndPassed(vector2);
            // x = vector3.a * c + point1.x;
            // y = vector3.b * c + point1.y;
            // z = vector3.c * c + point1.z;
            // Point point4 = new Point(x, y, z);
            // /*Находим все остальные точки*/
            // x = point2.x + vector3.a * c;
            // y = point2.y + vector3.b * c;
            // z = point2.z + vector3.c * c;
            // Point point5 = new Point(x, y, z);
            // x = point3.x + vector3.a * c;
            // y = point3.y + vector3.b * c;
            // z = point3.z + vector3.c * c;
            // Point point6 = new Point(x, y, z);
            // x = point2.x + vector2.a * b;
            // y = point2.y + vector2.b * b;
            // z = point2.z + vector2.c * b;
            // Point point7 = new Point(x, y, z);
            // x = point7.x + vector3.a * c;
            // y = point7.y + vector3.b * c;
            // z = point7.z + vector3.c * c;
            // Point point8 = new Point(x, y, z);
                   
                   
       

            /* New  My*/


            /* Создаем первую точку*/
            // double alpha_x = Utils.getRandomNumberInRange(-180.0*0-0.1, 180.0*0+0.1);
            // double alpha_x = Utils.getRandomNumberInRange(-180.0, 180.0);
            // double alpha_y = Utils.getRandomNumberInRange(-180.0, 180.0);

            double alpha_x = 0;
            double alpha_y = 0;
            double alpha_z = Utils.getRandomNumberInRange(-180.0, 180.0);


            double theta = Utils.getRandomNumberInRange(-Math.PI/2, Math.PI/2);
            double phi = Utils.getRandomNumberInRange(-Math.PI, Math.PI);

        

            alpha_x = Math.asin(-Math.sin(theta)*Math.sin(phi));

            if (Math.cos(alpha_x) != 0){
                alpha_y = Math.asin(Math.sin(theta)*Math.cos(phi)/Math.cos(alpha_x));
            }else{
                // Любое подойдет т.к. вектор лежит на оси y
                alpha_y = 0.0;
            }

            alpha_x = alpha_x/Math.PI*180.0;
            alpha_y = alpha_y/Math.PI*180.0;





            Vector vector_centr = new Vector(
                Utils.getRandomNumberInRange(Utils.LIMIT_X, 0.0),
                Utils.getRandomNumberInRange(Utils.LIMIT_Y, 0.0),
                Utils.getRandomNumberInRange(Utils.LIMIT_Z, 0.0));


            Parallelepiped par =  Parallelepiped.getDefineParallelepiped(a, b, c, alpha_x, alpha_y, alpha_z, vector_centr );


        return par;
    }

    public static ArrayList<Element> getParallelepipeds(int numberOfElements, double a, double b, double c, double t) {

        ArrayList<Element> parallelepipeds = new ArrayList<>();
        ArrayList<Parallelepiped> internalParallelepipeds = new ArrayList<>();
        Parallelepiped internalParallelepiped = null;
        //        System.out.println("Заполняем систему");

        ExportFile.addTextToExportFile("#____________", false);

        boolean withWall = false;


        long start = new Date().getTime();
        for (int i = 0; i < numberOfElements; ++i) {
            boolean isIntersects = true;
            while (isIntersects) {
                internalParallelepiped = Parallelepiped.getRandomElement(a, b, c);

                // // Проверка с пересечением с верхней и нижней стенкой мембраны
                if (withWall == true){
                isIntersects = ((Utils.lowBoundary.intersectsWith(internalParallelepiped)) ||
                        Utils.upperBoundary.intersectsWith(internalParallelepiped)   ) ;
                isIntersects =(isIntersects ||
                                Parallelepiped.intersectsWithParallelepipeds(
                                    internalParallelepipeds, internalParallelepiped) );
                }
                else {
                isIntersects =(Parallelepiped.intersectsWithParallelepipeds(
                                    internalParallelepipeds, internalParallelepiped) );
                }




                if (!isIntersects) {
                    internalParallelepipeds.add(i, internalParallelepiped);
                }
            }
            Element newParallelepiped = internalParallelepiped.getExternalParallelepiped(t);

            for (Element parallelepiped : parallelepipeds) {
                if (parallelepiped.intersectsWith(newParallelepiped)) {
                    parallelepiped.addNeighbour(newParallelepiped);
                    newParallelepiped.addNeighbour(parallelepiped);
                }
            }





            if (Utils.lowBoundary.intersectsWith(newParallelepiped)) {
                newParallelepiped.addNeighbour(Utils.lowBoundary);
            }



            if (Utils.upperBoundary.intersectsWith(newParallelepiped)) {
                newParallelepiped.addNeighbour(Utils.upperBoundary);
            }




            //Добавление элемента в систему   **********************************  export
            // newParallelepiped.printInfo();

            internalParallelepiped.printInfo(); 
 
            parallelepipeds.add(i, newParallelepiped);
        }

        long end = new Date().getTime();
//        double time = (end - start) / 1000.0 / 60.0;
        ExportFile.addTextToExportFile("#_____________", false);
//        System.out.println("Система заполнялась " + time + " минут");
        return parallelepipeds;
    }

    public static ArrayList<Element> getParallelepipeds(int numberOfElements, double a, double b, double c) {

        ArrayList<Element> parallelepipeds = new ArrayList<>();
        ArrayList<Parallelepiped> internalParallelepipeds = new ArrayList<>();
        Parallelepiped internalParallelepiped = null;
        //System.out.println("Заполняем систему");
        long start = new Date().getTime();
        for (int i = 0; i < numberOfElements; ++i) {
            Element newParallelepiped = Parallelepiped.getRandomElement(a, b, c);
            for (Element parallelepiped : parallelepipeds) {
                if (parallelepiped.intersectsWith(newParallelepiped)) {
                    parallelepiped.addNeighbour(newParallelepiped);
                    newParallelepiped.addNeighbour(parallelepiped);
                }
            }

            if (Utils.lowBoundary.intersectsWith(newParallelepiped)) {
                newParallelepiped.addNeighbour(Utils.lowBoundary);
            }

            if (Utils.upperBoundary.intersectsWith(newParallelepiped)) {
                newParallelepiped.addNeighbour(Utils.upperBoundary);
            }


            parallelepipeds.add(i, newParallelepiped);
        }
        long end = new Date().getTime();
        double time = (end - start) / 1000.0 / 60.0;
        System.out.println("Система заполнялась " + time + " минут");
        return parallelepipeds;
    }

    /* Метод определяет, пересекается ли новый параллелепипед с уже существующими в системе*/
    public static boolean intersectsWithParallelepipeds(ArrayList<Parallelepiped> parallelepipeds, Parallelepiped newParallelepiped) {

        if (parallelepipeds.isEmpty()) {
            return false;
        }
        boolean result = false;

        for (Parallelepiped parallelepiped : parallelepipeds) {
            if (parallelepiped.intersectsWith(newParallelepiped)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
