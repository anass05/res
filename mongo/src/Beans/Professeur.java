package Beans;

import java.util.ArrayList;

public class Professeur extends Personne{

	private int ID;
	private String nsomme;
	private Grade grade;
	private Profile profile;
	ArrayList<String> profs;

	public ArrayList<String> getProfs() {
		return profs;
	}
	public void setProfs(ArrayList<String> profs) {
		this.profs = profs;
	}
	public Professeur(int iD,int idpersonne, String mdp, String nom, String prenom, String nsomme, Grade grade, Profile profile) {
		super("", mdp, nom, prenom);
		this.nsomme = nsomme;
		this.grade = grade;
		this.profile = profile;
		this.ID = iD;
		profs = new ArrayList<>();
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getNsomme() {
		return nsomme;
	}
	public void setNsomme(String nsomme) {
		this.nsomme = nsomme;
	}
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}


	public ArrayList<String> remplirModuleProf(ArrayList<String> m)
	{
		for(int i=0;i<m.size();i++)
		{
			profs.add(m.get(i));
		}
		return profs;
	}
	
}
