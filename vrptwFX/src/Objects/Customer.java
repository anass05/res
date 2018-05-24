package Objects;

/**
 * Created by Anass on 2018-05-21.
 */
public class Customer {
    private int id;
    private int x;
    private int y;
    private int demandes;
    private int startTime;
    private int dueTime;
    private boolean visited;

    public Customer(int id, int x, int y, int demandes, int startTime, int dueTime) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.demandes = demandes;
        this.startTime = startTime;
        this.dueTime = dueTime;
        this.visited = false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getDemandes() {
        return demandes;
    }

    public void setDemandes(int demandes) {
        this.demandes = demandes;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getDueTime() {
        return dueTime;
    }

    public void setDueTime(int dueTime) {
        this.dueTime = dueTime;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
               /* ", x=" + x +
                ", y=" + y +
                ", demandes=" + demandes +
                ", startTime=" + startTime +
                ", dueTime=" + dueTime +*/
                '}';
    }

    public double distance(Customer s) {
        double X = Math.pow(Math.abs((double) s.x - (double) x), 2);
        double Y = Math.pow(Math.abs((double) s.y - (double) y), 2);
        return Math.sqrt(X + Y);
    }
}
