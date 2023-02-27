public class Tests {
    public int mode;

    public Tests(int mode) {
        this.mode = mode;
        System.out.println("Test_point!!!!!! ");  

    
    }



    public void check_eq(Point a, Point b){
        if (  a.equals(b) == true){
            System.out.println("\n OK \n");
        } else{
            System.out.println("\n Not Ok!!!\n");
            System.out.println("Object");
            System.out.println(a);
            a.getCoord();
            System.out.println("not equale");
            System.out.println(b);
            b.getCoord();
            System.out.println("\n");


        }


    }



    public void test_point() {
        System.out.println("*****************************\n\n"); 

        System.out.println("******Test_point***");  
        
         
        Point p1 = new Point(0,0,10);
        Point p11 = new Point(0,-10,0);
        p1 = p1.rotation(90,1);
        p1.getCoord();
        System.out.println("\n");
        System.out.println(p1.equals(p11));
        this.check_eq(p1,p11);



        Point p2 = new Point(0,0,10);
        Point p21 = new Point(10,0,0);
        p2 = p2.rotation(90,2);
        p2.getCoord();
        System.out.println("\n");
        System.out.println(p2.equals(p21));
        this.check_eq(p2,p21);



        Point p3 = new Point(10.0,0,0);
        Point p31 = new Point(0,10.0,0);
        p3 = p3.rotation(90,3);
        p3.getCoord();
        System.out.println("\n");
        System.out.println(p3.equals(p31));
        this.check_eq(p3,p31);




        Point p4 = new Point(0.0,0,0);
        Point p41 = new Point(1.0,2,3);
        Vector v = new Vector(1,2,3);
        p4 = p4.move(v);
        p4.getCoord();
        System.out.println("\n");
        this.check_eq(p4,p41);


        Vector v_c = new Vector(0,0,0);
        Parallelepiped par1 = Parallelepiped.getDefineParallelepiped(1,2,3,10,10,10,v_c);
        par1.printInfo();

        Parallelepiped par2 = Parallelepiped.getDefineParallelepiped(10,20,1,30,0,0,v_c);
        par2.printInfo();
        par2 = Parallelepiped.getDefineParallelepiped(10,20,1,0,30,0,v_c);
        par2.printInfo();

        par2 = Parallelepiped.getDefineParallelepiped(10,20,1,30,30,0,v_c);
        par2.printInfo();

        v_c = new Vector(1,20,30);

        par2 = Parallelepiped.getDefineParallelepiped(10,20,1,30,30,-10,v_c);
        par2.printInfo();




        // this.check_eq(p1,p11);
        // this.check_eq(p2,p21);
        // this.check_eq(p3,p31);
        // this.check_eq(p1,p11);





        System.out.println("\n\n*****************************");   
    }

}


