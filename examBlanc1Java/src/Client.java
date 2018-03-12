import java.util.ArrayList;



/**
 * Cette classe représente la classe client d'une banque 
 * @author   HAFIDI IMAD
 */
public class Client {
	/**
	 * La liste de ces comptes
	 */
	public  ArrayList<Compte> mesComptes;
	/**
	 * Nom du client
	 */
	private String nom;
	/**
	 * Prenom du client
	 */
	private String prenom;
	/**
	 * Age du client
	 */
	private Integer age;
	/**
	 * Identifiant du client
	 */
	private Integer id;
	
	public Client() {
		super();
		this.mesComptes = new ArrayList<Compte>();
	}
	public Client(String nom, String prenom, Integer id) {
		super();
		this.mesComptes = new ArrayList<Compte>();
		this.nom = nom;
		this.prenom = prenom;
		this.id = id;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
}
