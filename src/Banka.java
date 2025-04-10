import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

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

    public String toFileString() {
        return nosaukums + ";" + vards + ";" + uzvards + ";" + atlikums + ";" + parole;
    }

    public static Banka fromFileString(String data) {
        String[] dalas = data.split(";");

        String nosaukums = dalas[0];
        String vards = dalas[1];
        String uzvards = dalas[2];
        Double atlikums = Double.parseDouble(dalas[3]);
        String parole = dalas[4];

        return new Banka(nosaukums, vards, uzvards, atlikums, parole);
    }

    public String getAtributi(){

        return "Konta nosaukums: " + nosaukums +
        "\nVārds: " + vards +
        "\nUzvārds: " + uzvards +
        "\n\nKonta atlikums: " + atlikums + "€" +
        "\n[Parole: " + parole + "]";
    }

    @Override
    public String toString() {
        return "[" + nosaukums + "] " +"(" + vards + " " +uzvards + ") " + "> " + atlikums + "€ <";
    }


    public void noguldit(double daudzums) {
        if (daudzums > 0) {
            atlikums += daudzums;
        }
    }

    public void setAtlikums(double daudzums) {
        if (daudzums >= 0) {
            atlikums = daudzums;
        }
    }

    // Galvenās metodes
    public static void bankasIzvele() {
        Dati.bankas = new ArrayList<>(nolasaBankasKontus());
            if (Dati.bankas.isEmpty()) {
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
        while (true){
            String[] darbibas = {"Atvērt jaunu bankas kontu", "Apskatīt atvērtos kontus", "Noguldīt kontā naudu", "Aizvērt bankas kontu"};
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
                    break;
                case 1:
                    apskatitBankasKontus();
                    break;
                case 2:
                    nogulditNaudu();
                    break;
                case 3:
                    dzestBankasKontu();
                    break;
            }
        }

    }

    public static void saglabaBankasKontus(List<Banka> bankas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bankasKonti.txt", false))) {
            for (Banka b : bankas) {
                writer.write(b.toFileString()); // Konvertē Bankas objektu uz String
                writer.newLine();
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
        while (true){
            String kontaNosaukums = null, vards = null, uzvards = null, parole = null;
            Double atlikums = 0.00;
            while (true) {
                do{
                    kontaNosaukums = JOptionPane.showInputDialog(null, "Ievadi konta nosaukumu:", "MansKonts1");

                    if (kontaNosaukums == null) {
                        return;
                    }
                }while(kontaNosaukums.isEmpty());

                boolean kontsEksiste = false;
                for (Banka b : Dati.bankas) {
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
                }else{
                    return;
                }

                Dati.bankas.add(new Banka(kontaNosaukums, vards, uzvards, atlikums, parole));
            JOptionPane.showMessageDialog(null, "Konts veiksmīgi izveidots!", "Jauns bankas konts", JOptionPane.INFORMATION_MESSAGE);
            saglabaBankasKontus(Dati.bankas);
            break;
        }
    }

    static void apskatitBankasKontus(){
        while(true){
            Dati.bankas = new ArrayList<>(nolasaBankasKontus());
            if (Dati.bankas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nav neviena bankas konta!", "Kļūda", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] kontiStr = Dati.bankas.stream()
            .map(banka -> banka.toString())
            .toArray(String[]::new);
            
            String konts;
            konts = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies kontu, ko apskatīt: ",
                "Bankas kontu saraksts",
                JOptionPane.QUESTION_MESSAGE,
                null,
                kontiStr,
                kontiStr[0]);

            if (konts == null) {
                return;
            }

            int indekss = Arrays.asList(kontiStr).indexOf(konts);

            JOptionPane.showMessageDialog(null, Dati.bankas.get(indekss).getAtributi(), "Konta INFO:", JOptionPane.INFORMATION_MESSAGE);
            break;
        }
    }

        static void nogulditNaudu(){
            while (true){
                Dati.bankas = new ArrayList<>(nolasaBankasKontus());
            if (Dati.bankas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nav neviena bankas konta!", "Kļūda", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] kontiStr = Dati.bankas.stream()
            .map(banka -> banka.toString())
            .toArray(String[]::new);
            
            String konts;
            konts = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies kontu, kurā noguldīt naudu: ",
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

            String parole = null;
            do{
            parole = JOptionPane.showInputDialog(null, "Ievadi konta paroli:");
            }while(parole == null || parole.isEmpty());
            
            if (parole.equals(izveletaisKonts.getParole())){
                double nauda = 0.00;
                try {
                    nauda = Double.parseDouble(JOptionPane.showInputDialog(null, "Cik daudz naudas vēlies noguldīt?"));
                    if (nauda <= 0) {
                        JOptionPane.showMessageDialog(null, "Ievadītā summa ir nederīga. Lūdzu, ievadiet pozitīvu skaitli.", "Kļūda", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Ievadīts nederīgs skaitlis!", "Kļūda", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                izveletaisKonts.noguldit(nauda);
                JOptionPane.showMessageDialog(null, "Nauda veiksmīgi noguldīta! Jaunais atlikums: " + izveletaisKonts.getAtlikums(),
                "Noguldījums veikts", JOptionPane.INFORMATION_MESSAGE);
                saglabaBankasKontus(Dati.bankas);
                break;
            }else{
                JOptionPane.showMessageDialog(null, "Nepareiza parole! NELIEN SVEŠĀ KONTĀ!");
                }
            }
        }

    static void dzestBankasKontu(){
        while (true){
            Dati.bankas = new ArrayList<>(nolasaBankasKontus());
            if (Dati.bankas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nav neviena bankas konta!", "Kļūda", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] kontiStr = Dati.bankas.stream()
            .map(banka -> banka.toString())
            .toArray(String[]::new);
            
            String konts;
            konts = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies kontu, kuru vēlies dzēst: ",
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
            
            String parole = null;
            do{
            parole = JOptionPane.showInputDialog(null, "Ievadi konta paroli:");
            }while(parole == null || parole.isEmpty());

            if (parole.equals(izveletaisKonts.getParole())){
                int atbilde = JOptionPane.showConfirmDialog(
                    null,
                    "Vai tiešām vēlies DZĒST kontu ["+ izveletaisKonts.getNosaukums() +"]?",
                    "Bankas konta dzēšana",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (atbilde == JOptionPane.YES_OPTION) {
                    Dati.bankas.remove(indekss);
                    JOptionPane.showMessageDialog(null, "Konts [" + izveletaisKonts.getNosaukums() + "] tika neatgriezeniski dzēsts!",
                                                "Konts veiksmīgi dzēsts", JOptionPane.INFORMATION_MESSAGE);
                    saglabaBankasKontus(Dati.bankas);
                    break;
                }else{
                    return;
                }
            }else{
                JOptionPane.showMessageDialog(null, "Nepareiza parole! NELIEN SVEŠĀ KONTĀ!");
            }
        }
    }
    
}
