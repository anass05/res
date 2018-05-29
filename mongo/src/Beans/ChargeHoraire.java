package Beans;

import java.util.ArrayList;

public class ChargeHoraire {

    private int nhC;
    private int nhTD;
    private int nhTP;
    private int nhP;
    private Module module;
    private ArrayList<Seance> seances;
    private String id;

    public ChargeHoraire(String id,int nhC, int nhTD, int nhTP,int nhP, Module module, ArrayList<Seance> seances) {
        super();
        this.nhC = nhC;
        this.nhTD = nhTD;
        this.nhP = nhP;
        this.nhTP = nhTP;
        this.module = module;
        this.seances = seances;
        this.id = id;
    }

    public int getNhC() {
        return nhC;
    }

    public void setNhC(int nhC) {
        this.nhC = nhC;
    }

    public int getNhTD() {
        return nhTD;
    }

    public void setNhTD(int nhTD) {
        this.nhTD = nhTD;
    }

    public int getNhP() {
        return nhP;
    }

    public void setNhP(int nhP) {
        this.nhP = nhP;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public ArrayList<Seance> getSeances() {
        return seances;
    }

    public void setSeances(ArrayList<Seance> seances) {
        this.seances = seances;
    }

    public int total() {
        return nhC + nhP + nhTD + nhTP;
    }

    public int getNhTP() {
        return nhTP;
    }

    public void setNhTP(int nhTP) {
        this.nhTP = nhTP;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ChargeHoraire [nhC=" + nhC + ", nhTD=" + nhTD + ", nhP=" + nhP + ", module=" + module
                + ", seances=" + seances + "]";
    }


}
