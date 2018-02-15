import java.math.BigInteger;

/**
 * Created by Anass on 2018-02-12.
 * Updated now
 */
public class Fraction {

    private BigInteger num;
    private BigInteger den;

    public Fraction(BigInteger num, BigInteger den) {
        this.num = num;
        this.den = den;
    }

    public Fraction(int num, int den) {
        this(BigInteger.valueOf(num),BigInteger.valueOf(den));

    }

    public Fraction(int num) {
        this(num,1);
    }

    public Fraction add(Fraction f){
        BigInteger top = BigInteger.valueOf(0);
        BigInteger bottom = BigInteger.valueOf(1);
        top = top.add(num.multiply(f.getDen()));
        top = top.add(den.multiply(f.getNum()));
        bottom = bottom.multiply(den);
        bottom = bottom.multiply(f.getDen());
        return new Fraction(top,bottom);
    }

    public Fraction sub(Fraction f){
        BigInteger top = BigInteger.valueOf(0);
        BigInteger bottom = BigInteger.valueOf(1);
        top = top.add(num.multiply(f.getDen()));
        top = top.subtract(den.multiply(f.getNum()));
        bottom = bottom.multiply(den);
        bottom = bottom.multiply(f.getDen());
        return new Fraction(top,bottom);
    }

    public Fraction mult(Fraction f){
        BigInteger top = BigInteger.valueOf(1);
        BigInteger bottom = BigInteger.valueOf(1);
        top = num.multiply(f.getNum());
        bottom = den.multiply(f.getDen());
        return new Fraction(top,bottom);
    }

    public Fraction divi(Fraction f){
        BigInteger top = BigInteger.valueOf(1);
        BigInteger bottom = BigInteger.valueOf(1);
        top = num.multiply(f.getDen());
        bottom = den.multiply(f.getNum());
        return new Fraction(top,bottom);
    }

    @Override
    public String toString() {
        return num+"/"+den;
    }

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
