import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Main {


    public static void main(String[] args) {

        System.out.println("New prog !!!");
        System.out.println("Argument count"+ args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println("Argument" + i+":" + args[i]);
        }

        if (args.length != 6) {
            System.out.println("Pleace input 7 args: X Y Z  a b c  /path_exportDir/ ");
        }



        double t = 0.1;

        double a = 5;
        double b = 5;
        double c = 0.01;
        double R = 0.1;
        /*Сколько расчетов проводить, для вычисления погрешности*/
        int maxK = 1;


        Utils.LIMIT_X = Double.parseDouble(args[0]);
        Utils.LIMIT_Y = Double.parseDouble(args[1]);
        Utils.LIMIT_Z = Double.parseDouble(args[2]);

        a = Double.parseDouble(args[3]);
        b = Double.parseDouble(args[4]);
        c = Double.parseDouble(args[5]);

        ExportFile.nameExportDir = args[6];


        int number_of_points = 3;

        int number_of_itaration = 20;
        
        number_of_itaration = Integer.parseInt(args[7]);




        Tests test = new  Tests(2);
        test.test_point();




        System.out.println("Helloy");

        String name_export_file = ("D_"+Utils.LIMIT_X+"x"+Utils.LIMIT_Y+"x"+Utils.LIMIT_Z +
                "_"+ a+"x"+ b +"x"+c+"_.txt" );
        ExportFile.nameTextFile =name_export_file;

        String title_text;
        title_text = ("# Case    \n" +
                "#a = " + a + "\n"+
                "#b = " + b + "\n"+
                "#c = " + c + "\n"+
                "#t = " + t + "\n"+
                "#LIMIT_X = " + Utils.LIMIT_X +"\n"+
                "#LIMIT_Y = " + Utils.LIMIT_Y +"\n"+
                "#LIMIT_Z = " + Utils.LIMIT_Z +"\n"+
                "#  x , y , z,   phi , theta , gamma ");


        ExportFile.addTextToExportFile(title_text, true);

//        ExportFile.addTitleTextToExportFile();


        for (int numberOfElements = number_of_points; numberOfElements <= number_of_points+ 0; numberOfElements += 1) {
            int k = 0;
            ArrayList<Double> probabilities = new ArrayList<>();
            ArrayList<Double> clasterCapacities = new ArrayList<>();
            while (k < maxK) {
                Map<String, Object> data = Utils.getDataForParallelepipeds(number_of_itaration, numberOfElements, a, b, c, t);
                Double probability = (Double) data.get("ProbabilityOfClustersCreation");
                ArrayList<Double> intermediateClasterCapacities = (ArrayList<Double>) data.get("ClusterCapacities");
                probabilities.add(probability);
                clasterCapacities.addAll(intermediateClasterCapacities);
                ++k;
            }

            ExportFile.addTextToExportFile("# end solve case  \n\n\n", false);
            System.out.println("////////////");
            System.out.println("Результаты");
            System.out.println("Число элементов = " + numberOfElements);
            System.out.println("Вероятность = " + Utils.getAverage(probabilities));
            System.out.println("Мощность кластера = " + Utils.getAverage(clasterCapacities));
            System.out.println("Объемная концентрация = " + numberOfElements * a * b * c / Utils.LIMIT_X / Utils.LIMIT_Y / Utils.LIMIT_Z);

        }

    }

}
