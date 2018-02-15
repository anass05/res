import java.math.BigInteger;

/**
 * Classe Fraction qui represente une fraction
 * @author Anass
 */
public class Fraction {
    /**
     *Une fraction est définie par:
     * un numérateur: num de type BigInteger
     * un dénominateur: den de type BigInteger
     */
    private BigInteger num;
    private BigInteger den;

    /**
     * Le constructeur
     * @param num qui est le numérateur de type BigInteger
     * @param den qui est le dénominateur de type BigInteger
     * */
    public Fraction(BigInteger num, BigInteger den) {
        this.num = num;
        this.den = den;
    }

    /**
     *Le deuxiéme constructeur qui fait appelle au premier constructeur
     * avec une conversion des parametre en BigInt
     * @param num qui est le numérateur de type int
     * @param den qui est le dénominateur de type int
     * */

    public Fraction(int num, int den) {
        this(BigInteger.valueOf(num),BigInteger.valueOf(den));

    }
    /**
     *Le troisiere constructeur qui fait appelle au deuxiéme constructeur
     * avec une conversion du parametre num en BigInt et le den est initialisé à 1
     * @param num qui est le numérateur de type int
     * */
    public Fraction(int num) {
        this(num,1);
    }

    /**
     * La fonction d'ajout qui sert a ajouter deux fractions
     * @param f la fraction à ajouter à la fraction appelant la méthode
     * */
    public Fraction add(Fraction f){
        BigInteger top = BigInteger.valueOf(0);
        BigInteger bottom = BigInteger.valueOf(1);
        top = top.add(num.multiply(f.getDen()));
        top = top.add(den.multiply(f.getNum()));
        bottom = bottom.multiply(den);
        bottom = bottom.multiply(f.getDen());
        return new Fraction(top,bottom);
    }

    /**
     * La fonction de soustraction qui sert a soustraire deux fractions
     * @param f la fraction à soustraire de la fraction appelant la méthode
     * */
    public Fraction sub(Fraction f){
        BigInteger top = BigInteger.valueOf(0);
        BigInteger bottom = BigInteger.valueOf(1);
        top = top.add(num.multiply(f.getDen()));
        top = top.subtract(den.multiply(f.getNum()));
        bottom = bottom.multiply(den);
        bottom = bottom.multiply(f.getDen());
        return new Fraction(top,bottom);
    }

    /**
     * La fonction de multiplication qui sert a multiplier deux fractions
     * @param f la fraction à multiplier avec la fraction appelant la méthode
     * */
    public Fraction mult(Fraction f){
        BigInteger top = BigInteger.valueOf(1);
        BigInteger bottom = BigInteger.valueOf(1);
        top = num.multiply(f.getNum());
        bottom = den.multiply(f.getDen());
        return new Fraction(top,bottom);
    }

    /**
     * La fonction de division qui sert a diviser deux fractions
     * @param f la fraction à diviser de la fraction appelant la méthode
     * */
    public Fraction divi(Fraction f){
        BigInteger top = BigInteger.valueOf(1);
        BigInteger bottom = BigInteger.valueOf(1);
        top = num.multiply(f.getDen());
        bottom = den.multiply(f.getNum());
        return new Fraction(top,bottom);
    }

    /**
     * La fonction qui retourne un String affichant l'objet
     * */
    @Override
    public String toString() {
        return num+"/"+den;
    }

    /**
     * Le getters et le setters
     * */
    public BigInteger getNum() {
        return num;
    }

    public void setNum(BigInteger num) {
        this.num = num;
    }

    public BigInteger getDen() {
        return den;
    }

    public void setDen(BigInteger den) {
        this.den = den;
    }

    public double doubleValue(){
        return num.doubleValue()/den.doubleValue();
    }
}
