import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Pica {
    private String veids;
    private String izmers;
    private List<String> piedevas;
    private List<String> merces;
    private double cena;
    private Boolean piegade;
    private String adrese;
    private String telNr;
    private String parole;

    public Pica(String veids, String izmers, List<String> piedevas, List<String> merces, double cena, Boolean piegade, String adrese, String telNr, String parole) {
        this.veids = veids;
        this.izmers = izmers;
        this.piedevas = piedevas;
        this.merces = merces;
        this.cena = cena;
        this.piegade = piegade;
        this.adrese = adrese;
        this.telNr = telNr;
        this.parole = parole;
    }

    public String toFileString() { // PAREIZS FORMATS, LAI IERAKSTITU FAILAA (Pica objekts uz String)
        return veids + ";" + izmers + ";" + String.join(",", piedevas) + ";" + String.join(",", merces) + ";" + cena + ";" +
        piegade + ";" + adrese + ";" + telNr + ";" + parole;
    }

    public static Pica fromFileString(String data) { // PAREIZS FORMATS, LAI NOLASITU NO FAILA (String uz Pica objektu)
        String[] dalas = data.split(";");

        String veids = dalas[0];
        String izmers = dalas[1];
        List<String> piedevas = dalas[2].isEmpty() ? new ArrayList<>() : Arrays.asList(dalas[2].split(","));
        List<String> merces = dalas[3].isEmpty() ? new ArrayList<>() : Arrays.asList(dalas[3].split(","));
        double cena = Double.parseDouble(dalas[4]);
        Boolean piegade = Boolean.parseBoolean(dalas[5]);
        String adrese = dalas[6];
        String telNr = dalas[7];
        String parole = dalas[8];

        return new Pica(veids, izmers, piedevas, merces, cena, piegade, adrese, telNr, parole);
    }

    public String getParole() {
        return parole;
    }

    public String getAtributi(){
        String piegadee = "";
        if (piegade){
            piegadee = "\nPiegāde uz adresi: "+adrese+
                            "\nTel: "+telNr;
        }

        return "Veids: " + veids +
        "\nIzmērs: " + izmers +
        "\nPiedevas: " + String.join(",", piedevas) +
        "\nMērces: " + String.join(",", merces) +
        piegadee +
        "\n\nCena: " + String.format("%.2f", cena) +"€"+
        "\n[Parole: " + parole + "]";
    }

    @Override
    public String toString() {
        if (izmers.length() >= 8){
        izmers = izmers.substring(0, izmers.length() - 8);
        }
        return veids + " (" + izmers + ") - " + String.format("%.2f", cena) + "€";
    }
}