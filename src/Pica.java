import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    public Double getCena(){
        return cena;
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

        // Galvenās metodes
        public static void picerijasIzvele(){
        while (true){
            Dati.picasPasutijumi = new ArrayList<>(nolasaPasutijumus());
            String[] darbibas = {"Izveidot jaunu pasūtījumu", "Apskatīt esošos pasūtījumus", "Saņemt pasūtījumu"};
            int izvelesIndekss = 0;
            String izvele = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies darbību",
                "Sveicināts picērijā!",
                JOptionPane.QUESTION_MESSAGE,
                null,
                darbibas,
                darbibas[0]
            );
            izvelesIndekss = Arrays.asList(darbibas).indexOf(izvele);
            if (izvele == null) {
                return;
            }

            switch(izvelesIndekss){
                case 0:
                    izveidotPasutijumu();
                    break;
                case 1:
                    apskatitPasutijumus();
                    break;
                case 2:
                    sanemtPasutijumu();
                    break;
            }
        }
    }

    public static void saglabaPasutijumus(List<Pica> picasPasutijumi) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pasutijumi.txt", false))) {
            for (Pica pica : picasPasutijumi) {
                writer.write(pica.toFileString()); // Konvertē Pica objektu uz String
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Kļūda saglabājot pasūtījumus: " + e.getMessage());
        }
    }

    public static List<Pica> nolasaPasutijumus() {
        List<Pica> picasPasutijumi = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("pasutijumi.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                picasPasutijumi.add(Pica.fromFileString(line)); // Konvertē String atpakaļ uz Pica objektu
            }
        } catch (IOException e) {
            System.out.println("Nav neviena pasūtījuma");
        }

        return picasPasutijumi;
    }
    
    static void izveidotPasutijumu(){
        while (true){
            if (Dati.bankas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nav neviena bankas konta! Kā taisies samaksāt par pasūtījumu?!?\nTiec pārvirzīts uz bankas konta izveidi!",
                                                "Kļūda", JOptionPane.ERROR_MESSAGE);
                Banka.izveidotKontu();
                return;
            }

            String[] darbibas = {"Margarita", "Salami", "Hawaii", "Peperoni (asa)", "Veģetārā"};
            String veids = null;
            String izmers, parole, adrese = null, telNr = null;
            int opcija, piegadeOpcija;
            double cena = 0.00;
            Boolean piegade = false;

            Dati.piedevas.clear();
            Dati.merces.clear();

            veids = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies picas veidu:",
                "Cepam picu...",
                JOptionPane.QUESTION_MESSAGE,
                null,
                darbibas,
                darbibas[0]);
            if (veids == null){
                return;
            }

            darbibas = new String[]{"Liela(+8.40€)", "Vidēja(+6.25€)", "Maza(+5.20€)"};
            izmers = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies picas izmēru:",
                "Cepam picu...",
                JOptionPane.QUESTION_MESSAGE,
                null,
                darbibas,
                darbibas[0]);
            if (izmers == null){
                return;
            }
            switch(izmers){
                case "Liela(+8.40€)":
                    cena += 8.40;
                    break;
                case "Vidēja(+6.25€)":
                    cena += 6.25;    
                    break;
                case "Maza(+5.20€)":
                    cena += 5.20;
                    break;
            }

                JCheckBox siers = new JCheckBox("Siers(+0.80€)");
                JCheckBox pepperoni = new JCheckBox("Pepperoni(+1.00€)");
                JCheckBox senes = new JCheckBox("Sēnes(+0.70€)");
                JCheckBox olivas = new JCheckBox("Olīvas(+0.60€)");

                Object[] message = {
                    "Izvēlies piedevas:",
                    siers,
                    pepperoni,
                    senes,
                    olivas
                };

                opcija = JOptionPane.showConfirmDialog(null, message, "Piedevas", JOptionPane.OK_CANCEL_OPTION);
                if (opcija == JOptionPane.OK_OPTION) {
                    if (siers.isSelected()){
                        Dati.piedevas.add("Siers");
                        cena += 0.80;
                    }
                    if (pepperoni.isSelected()){
                        Dati.piedevas.add("Pepperoni");
                        cena += 1.00;
                    }
                    if (senes.isSelected()){
                        Dati.piedevas.add("Sēnes");
                        cena += 0.70;
                    }
                    if (olivas.isSelected()) {
                        Dati.piedevas.add("Olīvas");
                        cena += 0.60;
                    }
                }else{
                    return;
                }
                JCheckBox kecups = new JCheckBox("Kečups(+0.30€)");
                JCheckBox majoneze = new JCheckBox("Majonēze(+0.30€)");
                JCheckBox asaMerce = new JCheckBox("Asā mērce(+0.40€)");
                JCheckBox ipasaMerce = new JCheckBox("Pavāra īpašā mērce(+0.70€)");

                message = new Object[] {
                    "Izvēlies piedevas:",
                    kecups,
                    majoneze,
                    asaMerce,
                    ipasaMerce
                };

                opcija = JOptionPane.showConfirmDialog(null, message, "Mērces", JOptionPane.OK_CANCEL_OPTION);
                if (opcija == JOptionPane.OK_OPTION) {
                    if (kecups.isSelected()) {
                        Dati.merces.add("Kečups");
                        cena += 0.30;
                    }
                    if (majoneze.isSelected()) {
                        Dati.merces.add("Majonēze");
                        cena += 0.30;
                    }
                    if (asaMerce.isSelected()) {
                        Dati.merces.add("Asā mērce");
                        cena += 0.40;
                    }
                    if (ipasaMerce.isSelected()) {
                        Dati.merces.add("Pavāra īpašā mērce");
                        cena += 0.70;
                    }
                }else{
                    return;
                }

                piegadeOpcija = JOptionPane.showConfirmDialog(
                    null,
                    "Vai vēlies piegādi? (+2.50€)",
                    "Piegādes izvēle",
                    JOptionPane.YES_NO_OPTION
                );

                if (piegadeOpcija == JOptionPane.YES_OPTION) {
                    piegade = true;
                    cena += 2.50;
                    adrese = JOptionPane.showInputDialog(null, "Ievadi piegādes adresi:");
                    telNr = JOptionPane.showInputDialog(null, "Ievadi savu telefona numuru:");
                }

                do{
                    parole = JOptionPane.showInputDialog(null, "Ievadi paroli, kas būs jāuzrāda, kad saņemsi savu pasūtījumu(vismaz 5 rakstzīmes): ");
                }while(parole == null || parole.length() < 5);
                
                Pica pasutijums = new Pica(veids, izmers, new ArrayList<>(Dati.piedevas), new ArrayList<>(Dati.merces), cena, piegade, adrese, telNr, parole);
                apmaksatPasutijumu(pasutijums);
        }
    }

    static void apmaksatPasutijumu(Pica pasutijums) {
        while (true) {
            Banka.nolasaBankasKontus();
            String[] kontiStr = Dati.bankas.stream()
                .map(b -> b.toString())
                .toArray(String[]::new);
    
            String konts = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies kontu, ar kuru maksāsi:",
                "Bankas konti",
                JOptionPane.QUESTION_MESSAGE,
                null,
                kontiStr,
                kontiStr[0]);
    
            if (konts == null) return;
    
            int indekss = Arrays.asList(kontiStr).indexOf(konts);
            Banka izveletaisKonts = Dati.bankas.get(indekss);
    
            String parole = JOptionPane.showInputDialog(null, "Ievadi konta paroli:");
            if (parole == null) return;
    
            if (!parole.equals(izveletaisKonts.getParole())) {
                JOptionPane.showMessageDialog(null, "Nepareiza parole!");
                continue;
            }
    
            if (izveletaisKonts.getAtlikums() < pasutijums.getCena()) {
                String[] opcijas = {"Papildināt kontu", "Izvēlēties citu kontu"};
                int izvele = JOptionPane.showOptionDialog(null, 
                    "Nepietiek līdzekļu!\nCena: " + pasutijums.getCena() + "€\nKontā: " + izveletaisKonts.getAtlikums() + "€",
                    "Kļūda",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcijas,
                    opcijas[0]);
    
                if (izvele == 0) {
                    Banka.nogulditNaudu(izveletaisKonts);
                } else if (izvele == 1) {
                    continue; // atpakaļ uz konta izvēli
                } else {
                    return;
                }
            } else {
                // Apmaksa
                izveletaisKonts.setAtlikums(izveletaisKonts.getAtlikums() - pasutijums.getCena());
                Dati.picasPasutijumi.add(pasutijums);
                saglabaPasutijumus(Dati.picasPasutijumi);
                Banka.saglabaBankasKontus(Dati.bankas);
                JOptionPane.showMessageDialog(null, "Pasūtījums veiksmīgi apmaksāts!");
                return;
            }
        }
    }

    static void apskatitPasutijumus(){
        while (true) {
            Dati.picasPasutijumi = new ArrayList<>(nolasaPasutijumus());
            if (Dati.picasPasutijumi.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nav pieejamu pasūtījumu.", "Kļūda", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convert picasPasutijumi to a String array for selection
            String[] pasutijumiStr = Dati.picasPasutijumi.stream()
            .map(pica -> pica.toString()) // Convert each Pica to a string
            .toArray(String[]::new);
            
            String pasutijums;
            pasutijums = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies pasūtījumu, ko apskatīt: ",
                "Pasūtījumu saraksts",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pasutijumiStr,
                pasutijumiStr[0]);

            if (pasutijums == null) {
                return;
            }

            int indekss = Arrays.asList(pasutijumiStr).indexOf(pasutijums);

            JOptionPane.showMessageDialog(null, Dati.picasPasutijumi.get(indekss).getAtributi(), "Pasūtījuma informācija", JOptionPane.INFORMATION_MESSAGE);
            break;
        }
    }

    static void sanemtPasutijumu(){
        while (true){
            Dati.picasPasutijumi = new ArrayList<>(nolasaPasutijumus());
            if (Dati.picasPasutijumi.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nav pieejamu pasūtījumu.", "Kļūda", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] pasutijumiStr = Dati.picasPasutijumi.stream()
            .map(pica -> pica.toString())
            .toArray(String[]::new);
            
            int opcija;
            String pasutijums;
            pasutijums = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies pasūtījumu, ko saņemt: ",
                "Pasūtījumu saraksts",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pasutijumiStr,
                pasutijumiStr[0]);

            if (pasutijums == null) {
                    return;
            }

            opcija = JOptionPane.showConfirmDialog(null, pasutijums+"\nVai tiešām vēlies saņemt šo pasūtījumu?", "Pasūtījuma informācija", JOptionPane.OK_CANCEL_OPTION);
            if (opcija == JOptionPane.OK_OPTION) {
                int indekss = Arrays.asList(pasutijumiStr).indexOf(pasutijums);

                if (indekss != -1) {
                    String parole = JOptionPane.showInputDialog(null, "Ievadi paroli, lai saņemtu šo sūtījumu!");
                    if (parole != null && parole.equals(Dati.picasPasutijumi.get(indekss).getParole())) {
                        Dati.picasPasutijumi.remove(indekss);
                        JOptionPane.showMessageDialog(null, "Pasūtījums veiksmīgi izņemts, labu apetīti! ;]");
                        saglabaPasutijumus(Dati.picasPasutijumi);
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Nepareiza parole. Pasūtījums netika izņemts.");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Pasūtījums netika izņemts!");
            }
        }
    }

}