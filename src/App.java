import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) throws Exception {

        String[] darbibas = {"Banka", "Picērija", "Iziet"};
        int izvelesIndekss = 0;
        do{
            String izvele = (String) JOptionPane.showInputDialog(
                null,
                "Galvenā izvēlne",
                "Sveiks!",
                JOptionPane.QUESTION_MESSAGE,
                null,
                darbibas,
                darbibas[0]
            );
            izvelesIndekss = Arrays.asList(darbibas).indexOf(izvele);
            if (izvele == null) {
                JOptionPane.showMessageDialog(null, "Programma apturēta.");
                break;
            }

            switch(izvelesIndekss){
                case 0:
                    Banka.bankasIzvele();
                    break;
                case 1:
                    picerijasIzvele();
                    break;
            }
        
        }while(izvelesIndekss != 2);
    }

    
    
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

                // BANKAS KONTA IZVĒLE, LAI SAMAKSĀTU PAR PASŪTĪJUMU
            String[] kontiStr = Dati.bankas.stream()
            .map(banka -> banka.toString())
            .toArray(String[]::new);
            
            String konts;
            konts = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies kontu, ar kuru maksāsi par pasūtījumu: ",
                "Bankas kontu saraksts",
                JOptionPane.QUESTION_MESSAGE,
                null,
                kontiStr,
                kontiStr[0]);

            if (konts == null) {
                return;
            }

            int indekss = Arrays.asList(kontiStr).indexOf(konts);
            Banka izveletaisKonts = Dati.bankas.get(indekss);

            String paroleBanka = JOptionPane.showInputDialog(null, "Ievadi konta paroli:");
            if (paroleBanka.equals(izveletaisKonts.getParole())){
                String piegadee = "";
                if (piegade){
                    piegadee = "\nPiegāde uz adresi: "+adrese+
                                    "\nTel: "+telNr;
                }
                int atbilde = JOptionPane.showConfirmDialog(
                    null,
                    "Esi pārliecināts, ka vēlies veikt šo pasūtījumu?" +

                    "\n\nVeids: " + veids +
                    "\nIzmērs: " + izmers +
                    "\nPiedevas: " + String.join(",", Dati.piedevas) +
                    "\nMērces: " + String.join(",", Dati.merces) +
                    piegadee +
                    "\n\nCena: " + String.format("%.2f", cena) +"€"+
                    "\n[Parole: " + parole + "]",
                    "Maksājuma apstiprināšana",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (atbilde == JOptionPane.YES_OPTION) {
                    if (izveletaisKonts.getAtlikums() >= cena){
                        izveletaisKonts.setAtlikums(izveletaisKonts.getAtlikums() - cena);
                    JOptionPane.showMessageDialog(null, "Pasūtījums tika veiksmīgi apmaksāts!",
                                                "Pasūtījums veikts", JOptionPane.INFORMATION_MESSAGE);
                    Dati.picasPasutijumi.add(new Pica(veids, izmers, new ArrayList<>(Dati.piedevas), new ArrayList<>(Dati.merces), cena, piegade, adrese, telNr, parole));
                    saglabaPasutijumus(Dati.picasPasutijumi);
                    break;
                    }else{
                        JOptionPane.showMessageDialog(null, "Kontā ir nepietiekami līdzekļi!\nCena: "+cena+"€, Konta atlikums: "+izveletaisKonts.getAtlikums()+"€",
                                                "Kontā nepietiek līdzekļu", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }else{
                    return;
                }
            }else{
                JOptionPane.showMessageDialog(null, "Nepareiza parole! NELIEN SVEŠĀ KONTĀ!");
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