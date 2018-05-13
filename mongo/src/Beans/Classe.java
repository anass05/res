package Beans;

import java.util.ArrayList;

public class Classe {

	private String nom;
	private String promo;
	private int niveau;
	private String filiere;
	private int effectif;
	
	private ArrayList<Module> modules;
	
	

	public Classe(String nom, String promo, int niveau, String filiere, int effectif, ArrayList<Module> modules) {
		super();
		this.nom = nom;
		this.promo = promo;
		this.niveau = niveau;
		this.filiere = filiere;
		this.effectif = effectif;
		this.modules = new ArrayList<>();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPromo() {
		return promo;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}

	public int getNiveau() {
		return niveau;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}

	public int getEffectif() {
		return effectif;
	}

	public void setEffectif(int effectif) {
		this.effectif = effectif;
	}

	public ArrayList<Module> getModules() {
		return modules;
	}

	public void setModules(ArrayList<Module> modules) {
		this.modules = modules;
	}

	@Override
	public String toString() {
		return "Classe [nom=" + nom + ", promo=" + promo + ", niveau=" + niveau + ", filiere=" + filiere + ", effectif="
				+ effectif + ", modules=" + modules + "]";
	}
	
	
}
