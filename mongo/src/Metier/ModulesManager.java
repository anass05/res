package Metier;

import Beans.Module;
import Beans.Personne;
import Beans.Professeur;
import Persistence.ModulesDAO;

import java.util.ArrayList;

/**
 * Created by Anass on 2018-05-08.
 */
public class ModulesManager {
    private ModulesDAO modulesDAO;

    public ModulesManager(){
        modulesDAO = new ModulesDAO();
    }

    public ArrayList<Module> getModules(){
        return modulesDAO.getModules();
    }

    public ArrayList<Module> getModules(Personne p){
        return modulesDAO.getModules(p);
    }
}
