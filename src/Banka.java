public class Banka {
    private String nosaukums;
    private double atlikums;

    public Banka(String nosaukums) {
        this.nosaukums = nosaukums;
        this.atlikums = 0.0;
    }

    public double getAtlikums() {
        return atlikums;
    }

    public String getNosaukums() {
        return nosaukums;
    }

    public void noguldit(double daudzums) {
        if (daudzums > 0) {
            atlikums += daudzums;
        }
    }

    public boolean iznemt(double daudzums) {
        if (daudzums > 0 && daudzums <= atlikums) {
            atlikums -= daudzums;
            return true;
        }
        return false;
    }
}
