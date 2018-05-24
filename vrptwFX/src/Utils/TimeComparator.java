package Utils;

import Objects.Customer;

import java.util.Comparator;

/**
 * Created by Anass on 2018-05-22.
 */
public class TimeComparator implements Comparator<Customer> {
    @Override
    public int compare(Customer o1, Customer o2) {
        if (o1.getStartTime() > o2.getStartTime())
            return 1;
        if (o1.getStartTime() < o2.getStartTime())
            return -1;
        return 0;
    }
}
