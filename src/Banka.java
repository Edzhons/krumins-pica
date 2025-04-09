public class Banka {
    private String nosaukums, vards, uzvards, parole;
    private double atlikums;

    public Banka(String nosaukums, String vards, String uzvards, Double atlikums, String parole) {
        this.nosaukums = nosaukums;
        this.vards = vards;
        this.uzvards = uzvards;
        this.parole = parole;
        this.atlikums = atlikums;
    }

    public double getAtlikums() {
        return atlikums;
    }

    public String getNosaukums() {
        return nosaukums;
    }

    public String getVards() {
        return vards;
    }

    public String getUzvards() {
        return uzvards;
    }

    public String getParole() {
        return parole;
    }

    public String toFileString() { // PAREIZS FORMATS, LAI IERAKSTITU FAILAA (Pica objekts uz String)
        return nosaukums + ";" + vards + ";" + uzvards + ";" + atlikums + ";" + parole;
    }

    public static Banka fromFileString(String data) { // PAREIZS FORMATS, LAI NOLASITU NO FAILA (String uz Pica objektu)
        String[] dalas = data.split(";");

        String nosaukums = dalas[0];
        String vards = dalas[1];
        String uzvards = dalas[2];
        Double atlikums = Double.parseDouble(dalas[3]);
        String parole = dalas[4];

        return new Banka(nosaukums, vards, uzvards, atlikums, parole);
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
