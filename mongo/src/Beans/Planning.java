package Beans;

import java.util.ArrayList;

public class Planning {

    private boolean isValid;
    private ArrayList<Seance> seances;
    private Module module;
    private String id;

    public Planning(String id, boolean isValid) {
        super();
        this.isValid = isValid;
        this.seances = new ArrayList<>();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public ArrayList<Seance> getSeances() {
        return seances;
    }

    public void setSeances(ArrayList<Seance> seances) {
        this.seances = seances;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public void addSeance(Seance s) {
        this.seances.add(s);
    }

    public Seance getSeanceAt(int id) {
        for (Seance s : seances)
            if (s.getSemaine() == id)
                return s;
        return null;
    }

    @Override
    public String toString() {
        return "Planning [isValid=" + isValid + "]";
    }


}
