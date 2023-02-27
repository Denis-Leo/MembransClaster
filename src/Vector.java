

public class Vector {
    public double a;
    public double b;
    public double c;
    public final double MIN_VALUE = -100.0;
    public final double MAX_VALUE = 100.0;

    public Vector(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Vector(Point point1, Point point2) {
        this.a = point2.x - point1.x;
        this.b = point2.y - point1.y;
        this.c = point2.z - point1.z;
    }

    /* Метод для получения длины вектора*/
    public double getVectorLength() {
        double length = Math.sqrt(Math.pow(this.a, 2) +
                Math.pow(this.b, 2) +
                Math.pow(this.c, 2));
        return length;
    }

    /* Метод для получения отнормированного текущего вектора*/
    public Vector getNormalVector() {
        double length = this.getVectorLength();
        return new Vector(this.a / length, this.b / length, this.c / length);
    }

    /* Метод для получения случайного вектора, перпендикулярного текущему*/
    public Vector getRandomPerpendicularNormalVector() {
        Vector vector = null;
        double a = 0;
        double b = 0;
        double c = 0;
        if (!Utils.isEquals(this.a, 0.0)) {
            b = Utils.getRandomNumberInRange(MAX_VALUE, MIN_VALUE);
            c = Utils.getRandomNumberInRange(MAX_VALUE, MIN_VALUE);
            a = -(this.b * b + this.c * c) / this.a;
            vector = new Vector(a, b, c);
        } else {
            if (!Utils.isEquals(this.b, 0.0)) {
                a = Utils.getRandomNumberInRange(MAX_VALUE, MIN_VALUE);
                c = Utils.getRandomNumberInRange(MAX_VALUE, MIN_VALUE);
                b = -(this.a * a + this.c * c) / this.b;
                vector = new Vector(a, b, c);
            } else {
                a = Utils.getRandomNumberInRange(MAX_VALUE, MIN_VALUE);
                b = Utils.getRandomNumberInRange(MAX_VALUE, MIN_VALUE);
                c = -(this.a * a + this.b * b) / this.c;
                vector = new Vector(a, b, c);
            }
        }

        return vector.getNormalVector();
    }

    /* Метод для получения вектора, перпендикулярного текущему и переданному*/
    public Vector getNormalVectorPerpendicularThisAndPassed(Vector passedVector) {
        double a = this.b * passedVector.c - this.c * passedVector.b;
        double b = this.c * passedVector.a - this.a * passedVector.c;
        double c = this.a * passedVector.b - this.b * passedVector.a;
        Vector vector = new Vector(a, b, c);
        return vector.getNormalVector();
    }

    public Vector getVectorsSum(Vector vector){
        return new Vector(this.a + vector.a, this.b + vector.b, this.c + vector.c );
    }



    public double getPhi(){
        double phi;
        if (this.b != 0 ) {
            phi = Math.acos( this.a/Math.sqrt( Math.pow(this.a,2) + Math.pow(this.b,2) ) ) * this.b/Math.abs(b);
        }else {
            phi = 0;
        }

        return phi ;
    }

    public double getTheta(){
        double theta;

        theta = Math.acos( this.c/Math.sqrt( Math.pow(this.a,2) + Math.pow(this.b,2) +Math.pow(this.c,2)  ) );

        return theta ;
    }





    public Vector getNormalVectorTo(double normalization){
        double length = this.getVectorLength();
        return new Vector(this.a * normalization / length , this.b * normalization / length, this.c * normalization / length);
    }
}
