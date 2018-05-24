package Objects;

/**
 * Created by Anass on 2018-05-21.
 */
public class Vehicule {
    private String tag;
    public static final int CAPACITY = 200;

    public Vehicule(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "tag='" + tag + '\'' +
                '}';
    }
}
