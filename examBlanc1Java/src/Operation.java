import java.util.Date;

/**
 * Cette classe représente une opération élémentaire dans un compte
 * @author   HAFIDI IMAD
 */
public class Operation {
	/**
	 * La date de l'opération sous la forme jj/mm/aa (05/12/14)
	 */
	private String Date;
	/**
	 * montant de l'opération 
	 */
	private Float montant;
	/**
	 * Le type d'opération 
	 */
	private TypeOperation type;
	
	
	public Operation() {
		super();
	}
	/**
	 * constructeur de la classe opération prend une date et un montant et initialise tous les attributs
	 * @param date
	 * @param montant
	 */
	public Operation(String date, Float montant) {
		super();
		this.Date = date;
		this.montant = montant;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public Float getMontant() {
		return montant;
	}
	public void setMontant(Float montant) {
		this.montant = montant;
	}

	public TypeOperation getType() {
		return type;
	}

	/**
	 * une méthode qui return l'année de la date 
	 * @return String
	 */
	public String getAnnee(){
		String[] sp = Date.split("/");
		return sp[2];
	}
	/**
	 * une méthode qui return le mois de la date 
	 * @return String
	 */
	public String getMois(){
		String[] sp = Date.split("/");
		return sp[1];
	}
	/**
	 * une méthode qui return le jour de la date 
	 * @return String
	 */
	public String getJour(){
		String[] sp = Date.split("/");
		return sp[0];
	}
		
}
