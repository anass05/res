/**
 * Une exception indiquant une intersection entre bateaux
 *
 * @author Anass
 */
public class CaseOccupeeException extends Exception {
    /**
     * Le constructeur
     */
    public CaseOccupeeException() {
        super("Une intersection entre les bateaux est détéctée");
    }
}
