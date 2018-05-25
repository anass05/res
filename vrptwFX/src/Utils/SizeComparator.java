package Utils;

import Objects.Tournee;

import java.util.Comparator;

/**
 * Created by Anass on 2018-05-25.
 */
public class SizeComparator implements Comparator<Tournee> {
    @Override
    public int compare(Tournee t1, Tournee t2) {
        if (t1.getCustomers().size() > t2.getCustomers().size())
            return 1;
        if (t1.getCustomers().size() < t2.getCustomers().size())
            return -1;
        return 0;

    }
}
