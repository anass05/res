package Presentation;

import Beans.Module;
import Beans.Personne;
import Metier.ModulesManager;
import Persistence.ModulesDAO2;
import Persistence.ProfesseurDOA;

import javax.swing.*;

/**
 * Created by Anass on 2018-05-09.
 */
public class Controleur {

    private JFrame loginFrame;
    Module m;

    public Controleur() {
//        m = new ModulesManager().getModules().get(0);
        loginFrame = new JFrame();
        new Login(loginFrame, this, false).setVisible(true);
//        ouvrirDefinirPlanning(m);
    }

    public void ouvrirSuiviPlanning(Module m) {
        new SuiviPlanning(m, this);
    }

    public void ouvrirDefinirPlanning(Module m) {
        new DefinirPlanning(m, this);
    }

    public void ouvrirDashboard(Personne p) {
        new Dashboard(new ModulesManager().getModules(p), this,p);
    }

    public void login(String login, String password, JDialog dialog) {
        Personne p = new ProfesseurDOA().login(login, password);
        if (p != null) {
            dialog.dispose();
            loginFrame.dispose();
            ouvrirDashboard(p);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Identifiant ou mot de passe incorrect!",
                    "Erreur d'authentification",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void insertPlanning(Module m){
        new ModulesDAO2().definePlanning(m);
    }

    public void suiviPlanning(Module m){
        new ModulesDAO2().suiviPlanning(m);
    }

    public void edtierChargeHoraire(Module m){
        new EditerChargeHoraire(m,this);
    }

    public void saveModuleChargeHoraire(Module m){
        new ModulesDAO2().saveModuleChargeHoraire(m);
    }

}
