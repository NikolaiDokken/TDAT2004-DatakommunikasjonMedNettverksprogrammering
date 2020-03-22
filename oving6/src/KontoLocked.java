import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

public class KontoLocked {
    private int version;
    private int kontonr;
    private double saldo;
    private String eier;

    public KontoLocked() {

    }

    public int getKontonr() {
        return kontonr;
    }

    public void setKontonr(int kontonr) {
        this.kontonr = kontonr;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getEier() {
        return eier;
    }

    public void setEier(String eier) {
        this.eier = eier;
    }

    public void trekk(double belop) {
        this.saldo -= belop;
    }

    @Override
    public String toString() {
        return "Konto[kontonr=" + kontonr + ", eier=" + eier + ", saldo=" + saldo + "]";
    }

}