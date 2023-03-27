import java.util.*;

public class Utils {
    public static final double ACCURACY = 0.0000000001;
    public static  double LIMIT_X = 10.0;
    public static  double LIMIT_Y = 10.0;
    public static  double LIMIT_Z = 10.0;


    public static final double DELTA = 30;

    public static final Parallelepiped lowBoundary = new Parallelepiped(

            new Point(0.0, 0.0, 0.0),
            new Point(Utils.LIMIT_X, 0.0, 0.0),
            new Point(Utils.LIMIT_X, Utils.LIMIT_Y, 0.0),
            new Point(0.0, Utils.LIMIT_Y, 0.0),
            new Point(0.0, 0.0, -Utils.DELTA),
            new Point(Utils.LIMIT_X, 0.0, -Utils.DELTA),
            new Point(Utils.LIMIT_X, Utils.LIMIT_Y, -Utils.DELTA),
            new Point(0.0, Utils.LIMIT_Y, -Utils.DELTA)
    );

    public static final Parallelepiped upperBoundary = new Parallelepiped(
            new Point(0.0, 0.0, Utils.LIMIT_Z),
            new Point(Utils.LIMIT_X, 0.0, Utils.LIMIT_Z),
            new Point(Utils.LIMIT_X, Utils.LIMIT_Y, Utils.LIMIT_Z),
            new Point(0.0, Utils.LIMIT_Y, Utils.LIMIT_Z),
            new Point(0.0, 0.0, Utils.LIMIT_Z + Utils.DELTA),
            new Point(Utils.LIMIT_X, 0.0, Utils.LIMIT_Z + Utils.DELTA),
            new Point(Utils.LIMIT_X, Utils.LIMIT_Y, Utils.LIMIT_Z + Utils.DELTA),
            new Point(0.0, Utils.LIMIT_Y, Utils.LIMIT_Z + Utils.DELTA)
    );

    public static final Sphere lowBoundaryForSphere = new Sphere(new Point(Utils.LIMIT_X / 2, Utils.LIMIT_Y / 2, -100.0), 100);

    public static final Sphere upperBoundaryForSphere = new Sphere(new Point(Utils.LIMIT_X / 2, Utils.LIMIT_Y / 2, LIMIT_Z + 100.0), 100);

    public static double getRandomNumberInRange(double max, double min) {
        return (Math.random() * (max - min)) + min;
    }

    public static boolean isEquals(double a, double b) {
        return Math.abs(a - b) < ACCURACY;
    }

    //Перегрузка для параллелепипедов
    public static Map<String, Double> getDataForPermeableParallelepipeds(int numberOfIterations, int numberOfElements, double a, double b, double c) {
        int flag = 1;
        double numberOfClasters = 0.0;
        double summaryClusterСapacity = 0.0;
        ArrayList<Double> clusterCapacities = new ArrayList<>();
        for (int i = 0; i < numberOfIterations; ++i) {
            ArrayList<Element> parallelepipeds = Parallelepiped.getParallelepipeds(numberOfElements, a, b, c);
            if (Utils.hasClaster(parallelepipeds, flag)) {
                numberOfClasters += 1.0;
                summaryClusterСapacity += Utils.getClusterCapacity(parallelepipeds, flag);
            }

        }

        Map<String, Double> data = new HashMap<>();
        double probabilityOfClastersCreation = (Utils.isEquals(numberOfIterations, 0.0)) ? 0.0 : numberOfClasters / numberOfIterations;
        double averageClasterCapacity = (Utils.isEquals(numberOfClasters, 0.0)) ? 0.0 : summaryClusterСapacity / numberOfClasters;
        data.put("ProbabilityOfClastersCreation", probabilityOfClastersCreation);
        data.put("AverageClasterCapacity", averageClasterCapacity);
        return data;
    }

    //Перегрузка для параллелепипедов
    public static Map<String, Object> getDataForParallelepipeds(int numberOfIterations, int numberOfElements, double a, double b, double c, double t) {
        int flag = 1;
        double numberOfClasters = 0.0;
        double summaryClusterСapacity = 0.0;
        ArrayList<Double> clusterCapacities = new ArrayList<>();
        for (int i = 0; i < numberOfIterations; ++i) {
            ArrayList<Element> parallelepipeds = Parallelepiped.getParallelepipeds(numberOfElements, a, b, c, t);



            if (Utils.hasClaster(parallelepipeds, flag)) {
                numberOfClasters += 1.0;
                double clusterСapacity = Utils.getClusterCapacity(parallelepipeds, flag);
                clusterCapacities.add(clusterСapacity / numberOfElements);
                summaryClusterСapacity += clusterСapacity / numberOfElements;
            }
        }

        Map<String, Object> data = new HashMap<>();
        double probabilityOfClastersCreation = (Utils.isEquals(numberOfIterations, 0.0)) ? 0.0 : numberOfClasters / numberOfIterations;
        data.put("ProbabilityOfClustersCreation", probabilityOfClastersCreation);
        data.put("ClusterCapacities", clusterCapacities);
        return data;
    }


    /*Метод для получения мощности кластера, на вход подаются параллелепипеды,*/
    public static double getClusterCapacity(ArrayList<Element> graph, int flag) {

        Map<Element, Boolean> visitedVerticles = new HashMap<>();
        visitedVerticles.put(Element.getLowBoundary(flag), true);
        Stack<Element> stack = new Stack<>();
        for (Element element : graph) {
            if (Element.getLowBoundary(flag).intersectsWith(element)) {
                stack.add(element);
            }
        }

        while (!(stack.empty())) {
            Element element = stack.pop();
            if (!visitedVerticles.containsKey(element)) {
                visitedVerticles.put(element, true);
                for (Element neighbour : element.getNeighbours()) {
                    stack.add(neighbour);
                }
            }
        }

        int countOfNodes = 0;
        for (Map.Entry<Element, Boolean> verticles : visitedVerticles.entrySet()) {
            if (Utils.isConnectedWithBoundaries(graph, verticles.getKey(), flag)) {
                // !! verticles.getKey() - is element with Claster !!
                verticles.getKey().printInfo("Cluster");

                ++countOfNodes;
            }
        }

        return countOfNodes;
    }

    ////////ПЕРЕГРУЗКА
    public static boolean isConnectedWithBoundaries(ArrayList<Element> graph, Element node, int flag) {
        return Utils.isConnectedWithUpperBoundary(graph, node, flag) && Utils.isConnectedWithLowerBoundary(graph, node, flag);
    }

    ////////ПЕРЕГРУЗКА
    public static boolean isConnectedWithLowerBoundary(ArrayList<Element> graph, Element node, int flag) {

        boolean result = false;

        Map<Element, Boolean> visitedVerticles = new HashMap<>();
        visitedVerticles.put(node, true);
        Stack<Element> stack = new Stack<>();
        for (Element element : node.getNeighbours()) {
            stack.add(element);
        }

        while (!(stack.empty() || visitedVerticles.containsKey(Element.getLowBoundary(flag)))) {
            Element element = stack.pop();
            if (!visitedVerticles.containsKey(element) && !element.equals(Element.getUpperBoundary(flag))) {
                visitedVerticles.put(element, true);
                for (Element neighbour : element.getNeighbours()) {
                    stack.add(neighbour);
                }
            }
        }

        return visitedVerticles.containsKey(Element.getLowBoundary(flag));
    }

    ////////ПЕРЕГРУЗКА
    public static boolean isConnectedWithUpperBoundary(ArrayList<Element> graph, Element node, int flag) {

        Map<Element, Boolean> visitedVerticles = new HashMap<>();
        visitedVerticles.put(node, true);
        Stack<Element> stack = new Stack<>();
        for (Element element : node.getNeighbours()) {
            stack.add(element);
        }

        while (!(stack.empty() || visitedVerticles.containsKey(Element.getUpperBoundary(flag)))) {
            Element element = stack.pop();
            if (!visitedVerticles.containsKey(element) && !element.equals(Element.getLowBoundary(flag))) {
                visitedVerticles.put(element, true);
                for (Element neighbour : element.getNeighbours()) {
                    stack.add(neighbour);
                }
            }
        }

        return visitedVerticles.containsKey(Element.getUpperBoundary(flag));
    }


    /*Метод для определения, образовался ли кластер из параллелепипедов*/
    public static boolean hasClaster(ArrayList<Element> graph, int flag) {
        /*Поиск в глубину использует две структуры – стек для запоминания еще не обработанных вершин и список для запоминания уже обработанных.
        Поиск выполняется следующим образом:
        1.задать стартовую вершину (аналог корневой вершины при обходе дерева)
        2.обработать стартовую вершину и включить ее во вспомогательный список обработанных вершин
        3.включить в стек все вершины, смежные со стартовой
        4.организовать цикл по условию опустошения стека и внутри цикла выполнить:
            1.извлечь из стека очередную вершину
            2.проверить по вспомогательному списку обработанность этой вершины
            3.если вершина уже обработана, то извлечь из стека следующую вершину
            4.если вершина еще не обработана, то обработать ее и поместить в список обработанных вершин
            5.просмотреть весь список смежных с нею вершин и поместить в стек все еще не обработанные вершины*/
        Map<Element, Boolean> visitedVerticles = new HashMap<>();
        visitedVerticles.put(Element.getLowBoundary(flag), true);
        Stack<Element> stack = new Stack<>();
        for (Element element : graph) {
            if (Element.getLowBoundary(flag).intersectsWith(element)) {
                stack.add(element);
            }
        }

        while (!(stack.empty() || visitedVerticles.containsKey(Element.getUpperBoundary(flag)))) {
            Element element = stack.pop();
            if (!visitedVerticles.containsKey(element)) {
                visitedVerticles.put(element, true);
                for (Element neighbour : element.getNeighbours()) {
                    stack.add(neighbour);

                }
            }
        }

        return visitedVerticles.containsKey(Element.getUpperBoundary(flag));
    }

    public static double getAverage(ArrayList<Double> measurements){
        double average = 0.0;
        for (Double measurement : measurements) {
            average += measurement;
        }
        return average / measurements.size();
    }


}
