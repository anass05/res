package src;

import objects.Graphe;

import java.util.Comparator;

/**
 * Created by Anass on 2018-03-16.
 */
public class CoutComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Graphe g1 = (Graphe) o1;
        Graphe g2 = (Graphe) o2;
        if (g1.cout() > g2.cout())
            return 1;
        if (g1.cout() < g2.cout())
            return -1;
        return 0;
    }
}
