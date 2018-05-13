package Beans;

import java.util.ArrayList;

public class Personne {
	protected String idPersonne;
	protected String mdp, nom, prenom;
	protected ArrayList<Module> modules;
	
	public Personne(String iD, String mdp, String nom, String prenom) {
		super();
		idPersonne = iD;
		this.mdp = mdp;
		this.nom = nom;
		this.prenom = prenom;
		modules = new ArrayList<>();
	}
	public ArrayList<Module> getModules() {
		return modules;
	}
	public void setModules(ArrayList<Module> modules) {
		this.modules = modules;
	}
	public String getIDPersonne() {
		return idPersonne;
	}
	public void setIDPersonne(String iD) {
		idPersonne = iD;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	
}
