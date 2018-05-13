package Beans;

import java.util.ArrayList;

public class Vacataire extends Personne{
	private String cin;
	private Diplome diplome;
	private ArrayList<String> modules;
	ArrayList<String> mods;
	public Vacataire(int iD, String mdp, String nom, String prenom, String cin, Diplome diplome) {
		super("", mdp, nom, prenom);
		this.cin = cin;
		this.diplome = diplome;
		mods=new ArrayList();
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public Diplome getDiplome() {
		return diplome;
	}
	public void setDiplome(Diplome diplome) {
		this.diplome = diplome;
	}
	@Override
	public String toString() {
		return "Vacataire [cin=" + cin + ", diplome=" + diplome + "]";
	}


	public void remplirModuleProf(ArrayList<String> m)
	{
		for(int i=0;i<m.size();i++)
		{
			mods.add(m.get(i));
		}
	}
	public ArrayList<String> getMods() {
		return mods;
	}
	public void setMods(ArrayList<String> mods) {
		this.mods = mods;
	}
}
