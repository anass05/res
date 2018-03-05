import java.util.*;

/**
 * Created by Anass on 2018-02-06.
 */
public class Main {
    public static void main(String[] args) {
        System.out.print("n = ");
        Scanner sc = new Scanner(System.in);
        double n = sc.nextDouble();
        System.out.println(harmonique(n));

        System.out.print("x = ");
        int x = sc.nextInt();
        triangle(x);

    }

    public static double harmonique(double n) {
        double res = 0;
        while (n > 0) {
            res += 1 / n;
            n--;
        }
        return res;
    }

    public static void triangle(int n) {
        int miniCount = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < miniCount; j++) {
                System.out.print("*");
            }
            miniCount++;
            System.out.println();
        }
    }
}
