package Presentation;

import Beans.Module;
import Beans.Personne;
import Beans.Professeur;
import Metier.ModulesManager;
import Persistence.ProfesseurDOA;

import javax.swing.*;

/**
 * Created by Anass on 2018-05-09.
 */
public class Controleur {

    public Controleur() {
        Module m = new ModulesManager().getModules().get(0);
//        new Dashboard(new ModulesManager().getModules(),this);
//        new AjouterPlanning(m,this);
//        ouvrirSuiviPlanning(m);
        JFrame j = new JFrame();
        j.setVisible(true);
        new Login(j,this,false).setVisible(true);
    }

    public void ouvrirSuiviPlanning(Module m) {
        new SuiviPlanning(m, this);
    }

    public void ouvrirDefinirPlanning(Module m) {
        new AjouterPlanning(m, this);
    }

    public void ouvrirDashboard(Personne p){
        new Dashboard(new ModulesManager().getModules(p),this);
    }

    public void login(String login,String password){
        Personne p = new ProfesseurDOA().login(login,password);
        if(p != null){
            ouvrirDashboard(p);
        }else{
            JOptionPane.showMessageDialog(null,
                    "Identifiant ou mot de passe incorrect!",
                    "Erreur d'authentification",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

}
