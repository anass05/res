package src;
import Algorithmes.Descente;
import Algorithmes.RecuitSimule;
import Algorithmes.Tabou;
import objects.Graphe;

/**
 * Created by Anass on 2018-03-13.
 */
public class Main {
    public static void main(String[] args) {
        Graphe g = new Graphe();
        g.sommets = new DataLoader().readFile("small.in");

        //new Descente().calcule(g);
        //new Tabou().calcule(g);
        new RecuitSimule().calcule(g);
    }

}
