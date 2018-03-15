package src;

import objects.Sommet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Anass on 2018-03-14.
 */
public class DataLoader {

    public ArrayList<Sommet> readFile(String fileName) {
        ArrayList<Sommet> sommets = new ArrayList<>();
        String line;
        int index = 0;
        try {
            FileReader fileReader = new FileReader("src/raw/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                String[] numbers = line.split("\t");
                Sommet s = new Sommet(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
                s.setIndice(index);
                index++;
                sommets.add(s);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sommets;
    }
}
