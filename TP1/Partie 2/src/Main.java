/**
 * Created by Anass on 2018-02-12.
 */
public class Main {
    public static void main(String[] args) {
        Fraction f = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 3);
        Fraction f3 = f2.divi(f);
        System.out.println(f3.doubleValue());

    }
}
