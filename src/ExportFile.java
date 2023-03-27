
import java.io.*;

public class ExportFile {
//
//    String nameTextFile = "Report.txt";
//
//    public static void addTitleTextToExportFile() {
//
//        String title_text = ("# Case    \n" +
//                "#a=" + Main.a + "\n" +
//                "#  x , y , z,   phi , theta ");
//
//
//        try (FileWriter writer = new FileWriter(nameTextFile, false)) {
//            // запись всей строки
//            writer.write(title_text);
//            // запись по символам
//            writer.append('\n');
//            writer.flush();
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
//
//
//    }


    // Defolt rewrite om Main prog 
    public static String nameTextFile = "Report.txt";


    public static  String   NAME_EXPORT_CLUSTER = "Cluster.txt";


    public static String nameExportDir = "../Export/";



    public static void addTextToExportFile(String text, boolean rewrite){
//        name = "Report.txt";


        try(FileWriter writer = new FileWriter(ExportFile.nameExportDir+ExportFile.nameTextFile, ! rewrite))
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


    public static void addTextToExportClusterFile(String text, boolean rewrite){
//        name = "Report.txt";

//        System.out.println("Cluster");
        try(FileWriter writer = new FileWriter(ExportFile.nameExportDir+ExportFile.NAME_EXPORT_CLUSTER, ! rewrite))
        {
            // запись всей строки
//            System.out.println("Cluster");
//            writer.write(text);
            // запись по символам
            writer.append('\n');
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }



    }


}
