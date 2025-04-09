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
        static List<Pica> picasPasutijumi = new ArrayList<>();
        static List<Banka> bankas = new ArrayList<>();
        static List<String> piedevas = new ArrayList<>();
        static List<String> merces = new ArrayList<>();
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
                    bankasIzvele();
                    break;
                case 1:
                    picerijasIzvele();
                    break;
            }
        
        }while(izvelesIndekss != 2);
    }

    public static void bankasIzvele() {
        bankas = new ArrayList<>(nolasaBankasKontus());
        if (bankas.isEmpty()) {
            int atbilde = JOptionPane.showConfirmDialog(
                null,
                "Sveicināts bankā! Vai vēlies atvērt jaunu kontu?",
                "Bankas konta izveide",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
    
            if (atbilde == JOptionPane.YES_OPTION) {
                izveidotKontu();
            } else {
                JOptionPane.showMessageDialog(null, "Nu labi... bet bez konta nevarēsi pirkt picas... ;[");
            }
        }

        String[] darbibas = {"Atvērt jaunu bankas kontu", "Apskatīt atvērtos kontus", "Noguldīt kontā naudu", "Izņemt no konta naudu", "Aizvērt bankas kontu"};
        int izvelesIndekss = 0;
        String izvele = (String) JOptionPane.showInputDialog(
            null,
            "Izvēlies darbību",
            "Sveicināts bankā!",
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
                izveidotKontu();
                saglabaBankasKontus(bankas);
                break;
            case 1:
                bankas = new ArrayList<>(nolasaBankasKontus());
                apskatitBankasKontus();
                break;
            case 2:
                bankas = new ArrayList<>(nolasaBankasKontus());
                //nogulditNaudu();
                saglabaBankasKontus(bankas);
                break;
            case 3:
                bankas = new ArrayList<>(nolasaBankasKontus());
                //iznemtNaudu();
                saglabaBankasKontus(bankas);
                break;
            case 4:
                bankas = new ArrayList<>(nolasaBankasKontus());
                //dzestBankasKontu();
                saglabaBankasKontus(bankas);
                break;
        }

    }

    public static void saglabaBankasKontus(List<Banka> bankas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bankasKonti.txt", false))) {
            for (Banka b : bankas) {
                writer.write(b.toFileString()); // Konvertē Bankas objektu uz String
                writer.newLine();
                System.out.println("Saglabāju kontu...");
            }
        } catch (IOException e) {
            System.out.println("Kļūda saglabājot bankas kontus: " + e.getMessage());
        }
    }

    public static List<Banka> nolasaBankasKontus() {
        List<Banka> bankas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("bankasKonti.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bankas.add(Banka.fromFileString(line)); // Konvertē String atpakaļ uz Bankas objektu
            }
        } catch (IOException e) {
            System.out.println("Nav neviena bankas konta");
        }

        return bankas;
    }

    public static void izveidotKontu(){
        String kontaNosaukums = null, vards = null, uzvards = null, parole = null;
        Double atlikums = 0.00;
        while (true) {
            kontaNosaukums = JOptionPane.showInputDialog(null, "Ievadi konta nosaukumu:", "MansKonts1");

            if (kontaNosaukums == null) {
                return;
            }

            boolean kontsEksiste = false;
            for (Banka b : bankas) {
                if (b.getNosaukums().equalsIgnoreCase(kontaNosaukums)) {
                    kontsEksiste = true;
                    break;
                }
            }

            if (kontsEksiste) {
                JOptionPane.showMessageDialog(null, "Šāds konta nosaukums jau eksistē. Lūdzu, izvēlies citu!", "Kļūda", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }

        do{
            vards = JOptionPane.showInputDialog(null, "Ievadi savu vārdu: ");
            if (vards == null) {
                return;
            }
        }while(!vards.matches("[a-zA-ZāčēģīķļņōŗšūžĀČĒĢĪĶĻŅŌŖŠŪŽ]+"));

        do{
            uzvards = JOptionPane.showInputDialog(null, "Ievadi savu uzvārdu: ");
            if (uzvards == null) {
                return;
            }
        }while(!uzvards.matches("[a-zA-ZāčēģīķļņōŗšūžĀČĒĢĪĶĻŅŌŖŠŪŽ]+"));
        
        do{
            parole = JOptionPane.showInputDialog(null, "Ievadi paroli: \n(vismaz 5 rakstzīmes,\nsatur lielo burtu,\nsatur speciālo zīmi(!@#$%^&*()_+=-)): ");
            if (parole == null) {
                return;
            }
        }while(parole.length() < 5 ||
        !parole.matches(".*[A-Z].*") ||
        !parole.matches(".*[!@#$%^&*()_+=-].*"));

        int atbilde = JOptionPane.showConfirmDialog(
                null,
                "Konts ir izveidots! Vai vēlies jau tagad kontā iemaksāt naudu?",
                "Bankas konta izveide",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            String atlikumsStr = null;
            if (atbilde == JOptionPane.YES_OPTION) {
                atlikumsStr = JOptionPane.showInputDialog(null, "Cik vēlies iemaksāt?");
                if (atlikumsStr == null) {
                    return;
                }
                if (atlikumsStr.isEmpty()){
                    atlikums = 0.00;
                }else{
                    try {
                        atlikums = Double.parseDouble(atlikumsStr);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Ievadīts nederīgs skaitlis!", "Kļūda", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

        bankas.add(new Banka(kontaNosaukums, vards, uzvards, atlikums, parole));
        JOptionPane.showMessageDialog(null, "Konts veiksmīgi izveidots!", "Jauns bankas konts", JOptionPane.INFORMATION_MESSAGE);
    }

    static void apskatitBankasKontus(){
        if (bankas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nav neviena bankas konta!", "Kļūda", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] kontiStr = bankas.stream()
        .map(banka -> banka.toString())
        .toArray(String[]::new);
        
        String konts;
        konts = (String) JOptionPane.showInputDialog(
            null,
            "Izvēlies pasūtījumu, ko apskatīt: ",
            "Pasūtījumu saraksts",
            JOptionPane.QUESTION_MESSAGE,
            null,
            kontiStr,
            kontiStr[0]);

        if (konts == null) {
            return;
        }

        int indekss = Arrays.asList(kontiStr).indexOf(konts);

        JOptionPane.showMessageDialog(null, bankas.get(indekss).getAtributi(), "Pasūtījuma informācija", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void picerijasIzvele(){
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
                saglabaPasutijumus(picasPasutijumi);
                break;
            case 1:
                picasPasutijumi = new ArrayList<>(nolasaPasutijumus());
                apskatitPasutijumus();
                break;
            case 2:
                picasPasutijumi = new ArrayList<>(nolasaPasutijumus());
                sanemtPasutijumu();
                saglabaPasutijumus(picasPasutijumi);
                break;
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
        String[] darbibas = {"Margarita", "Salami", "Hawaii", "Peperoni (asa)", "Veģetārā"};
        String veids = null;
        String izmers, parole, adrese = null, telNr = null;
        int opcija, piegadeOpcija;
        double cena = 0.00;
        Boolean piegade = false;

        piedevas.clear();
        merces.clear();

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
                    piedevas.add("Siers");
                    cena += 0.80;
                }
                if (pepperoni.isSelected()){
                    piedevas.add("Pepperoni");
                    cena += 1.00;
                }
                if (senes.isSelected()){
                    piedevas.add("Sēnes");
                    cena += 0.70;
                }
                if (olivas.isSelected()) {
                    piedevas.add("Olīvas");
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
                    merces.add("Kečups");
                    cena += 0.30;
                }
                if (majoneze.isSelected()) {
                    merces.add("Majonēze");
                    cena += 0.30;
                }
                if (asaMerce.isSelected()) {
                    merces.add("Asā mērce");
                    cena += 0.40;
                }
                if (ipasaMerce.isSelected()) {
                    merces.add("Pavāra īpašā mērce");
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

        picasPasutijumi.add(new Pica(veids, izmers, new ArrayList<>(piedevas), new ArrayList<>(merces), cena, piegade, adrese, telNr, parole));
    }

    static void apskatitPasutijumus(){
        if (picasPasutijumi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nav pieejamu pasūtījumu.", "Kļūda", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convert picasPasutijumi to a String array for selection
        String[] pasutijumiStr = picasPasutijumi.stream()
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

        JOptionPane.showMessageDialog(null, picasPasutijumi.get(indekss).getAtributi(), "Pasūtījuma informācija", JOptionPane.INFORMATION_MESSAGE);
    }

    static void sanemtPasutijumu(){
        if (picasPasutijumi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nav pieejamu pasūtījumu.", "Kļūda", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] pasutijumiStr = picasPasutijumi.stream()
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
                if (parole != null && parole.equals(picasPasutijumi.get(indekss).getParole())) {
                    picasPasutijumi.remove(indekss);
                    JOptionPane.showMessageDialog(null, "Pasūtījums veiksmīgi izņemts, labu apetīti! ;]");
                } else {
                    JOptionPane.showMessageDialog(null, "Nepareiza parole. Pasūtījums netika izņemts.");
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Pasūtījums netika izņemts!");
        }
    }
}