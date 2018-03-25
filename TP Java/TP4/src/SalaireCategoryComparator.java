import java.util.Comparator;

/**
 * le comparateur par salaire
 *
 * @author Anass
 */
public class SalaireCategoryComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Poste p1 = (Poste) o1;
        Poste p2 = (Poste) o2;
        if (p1.getSalaireBrut() < p2.getSalaireBrut())
            return -1;
        if (p1.getSalaireBrut() > p2.getSalaireBrut())
            return 1;
        return 0;
    }
}
