package Utils;

import Objects.Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Anass on 2018-05-21.
 */
public class FileIO {
    private ArrayList<Customer> customers;
    public static Customer depot;

    public ArrayList<Customer> readFile(String fileName) {
        String line;
        customers = new ArrayList<>();
        try {
            FileReader fileReader =
                    new FileReader("file_in/" + fileName);
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (i != 0) {
                    String[] strings = line.split("\\s+");
                    double[] ns = Arrays.stream(strings).mapToDouble(Double::parseDouble).toArray();
                    Customer c = new Customer((int) ns[0], (int) ns[1], (int) ns[2], (int) ns[3], (int) ns[4], (int) ns[5]);
                    if ((int) ns[0] == 1)
                        depot = c;
                    customers.add(c);
                }
                i++;
            }
            bufferedReader.close();
            Helper.matriceDistance(customers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

}
