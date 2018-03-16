package objects;

/**
 * Created by Anass on 2018-03-13.
 */
public class Sommet {
    private int indice;
    private int x, y;

    public Sommet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Sommet() {
        this(0, 0);
    }

    public double distance(Sommet s) {
        double X = Math.pow(Math.abs((double) s.x - (double) x), 2);
        double Y = Math.pow(Math.abs((double) s.y - (double) y), 2);
        return Math.sqrt(X + Y);
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    @Override
    public String toString() {
        return "s" + indice;
    }

    //    @Override
//    public String toString() {
//        return "("+x+","+y+")";
//    }

    @Override
    public boolean equals(Object obj) {
        return ((Sommet) obj).x == x && ((Sommet) obj).y == y;
    }

    public int getIndice() {
        return indice;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
