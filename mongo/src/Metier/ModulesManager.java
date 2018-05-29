package Metier;

import Beans.ChargeHoraire;
import Beans.Module;
import Beans.Personne;
import Persistence.ModulesDAO2;

import java.util.ArrayList;

/**
 * Created by Anass on 2018-05-08.
 */
public class ModulesManager {
    private ModulesDAO2 modulesDAO;

    public ModulesManager() {
        modulesDAO = new ModulesDAO2();
    }

    public ArrayList<Module> getModules(Personne p) {
        return modulesDAO.getModules(p);
    }

}
