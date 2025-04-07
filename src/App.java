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

class Pica {
    private String veids;
    private String izmers;
    private List<String> piedevas;
    private List<String> merces;
    private double cena;
    private String parole;

    public Pica(String veids, String izmers, List<String> piedevas, List<String> merces, double cena, String parole) {
        this.veids = veids;
        this.izmers = izmers;
        this.piedevas = piedevas;
        this.merces = merces;
        this.cena = cena;
        this.parole = parole;
    }

    public String getParole() {
        return parole;
    }    

    public String toFileString() { // PAREIZS FORMATS, LAI IERAKSTITU FAILAA (Pica objekts uz String)
        return veids + ";" + izmers + ";" + String.join(",", piedevas) + ";" + String.join(",", merces) + ";" + cena + ";" + parole;
    }

    public static Pica fromFileString(String data) { // PAREIZS FORMATS, LAI NOLASITU NO FAILA (String uz Pica objektu)
        String[] parts = data.split(";");

        String veids = parts[0];
        String izmers = parts[1];
        List<String> piedevas = parts[2].isEmpty() ? new ArrayList<>() : Arrays.asList(parts[2].split(","));
        List<String> merces = parts[3].isEmpty() ? new ArrayList<>() : Arrays.asList(parts[3].split(","));
        double cena = Double.parseDouble(parts[4]);
        String parole = parts[5];

        return new Pica(veids, izmers, piedevas, merces, cena, parole);
    }

    @Override
    public String toString() {
        return veids + " (" + izmers + ") - " + cena + " EUR";
    }
}

public class App {
        static List<Pica> picasPasutijumi = new ArrayList<>();
        static List<String> piedevas = new ArrayList<>();
        static List<String> merces = new ArrayList<>();
    public static void main(String[] args) throws Exception {

        String[] darbibas = {"Izveidot jaunu pasūtījumu", "Apskatīt esošos pasūtījumus", "Saņemt pasūtījumu", "Iziet"};
        int izvelesIndekss = 0;
        do{
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
                JOptionPane.showMessageDialog(null, "Programma apturēta.");
                break;
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
        
        }while(izvelesIndekss != 3);
    }

    public static void saglabaPasutijumus(List<Pica> picasPasutijumi) {
        String fNosaukums = "pasutijumi.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fNosaukums, false))) {
            for (Pica pica : picasPasutijumi) {
                writer.write(pica.toFileString()); // Konvertē Pica objektu uz String
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }

    public static List<Pica> nolasaPasutijumus() {
        String fNosaukums = "pasutijumi.txt";
        List<Pica> picasPasutijumi = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fNosaukums))) {
            String line;
            while ((line = reader.readLine()) != null) {
                picasPasutijumi.add(Pica.fromFileString(line)); // Konvertē String atpakaļ uz Pica objektu
            }
        } catch (IOException e) {
            System.out.println("No existing orders found, starting fresh.");
        }

        return picasPasutijumi;
    }
    
    static void izveidotPasutijumu(){
        String[] darbibas = {"Margarita", "Salami", "Hawaii", "Peperoni (asa)", "Veģetārā"};
        String veids = null;
        String izmers, parole;
        int opcija;

        piedevas.clear();
        merces.clear();

        do{
            veids = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies picas veidu:",
                "Cepam picu...",
                JOptionPane.QUESTION_MESSAGE,
                null,
                darbibas,
                darbibas[0]);
        }while(veids == null);

        do{
            darbibas = new String[]{"Liela", "Vidēja", "Maza"};
            izmers = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies picas izmēru:",
                "Cepam picu...",
                JOptionPane.QUESTION_MESSAGE,
                null,
                darbibas,
                darbibas[0]);
        }while(izmers == null);

            JCheckBox siers = new JCheckBox("Siers");
            JCheckBox pepperoni = new JCheckBox("Pepperoni");
            JCheckBox senes = new JCheckBox("Sēnes");
            JCheckBox olivas = new JCheckBox("Olīvas");

            Object[] message = {
                "Izvēlies piedevas:",
                siers,
                pepperoni,
                senes,
                olivas
            };

            opcija = JOptionPane.showConfirmDialog(null, message, "Piedevas", JOptionPane.OK_CANCEL_OPTION);
            if (opcija == JOptionPane.OK_OPTION) {
                if (siers.isSelected()) piedevas.add("Siers");
                if (pepperoni.isSelected()) piedevas.add("Pepperoni");
                if (senes.isSelected()) piedevas.add("Sēnes");
                if (olivas.isSelected()) piedevas.add("Olīvas");
            }

            JCheckBox kecups = new JCheckBox("Kečups");
            JCheckBox majoneze = new JCheckBox("Majonēze");
            JCheckBox asaMerce = new JCheckBox("Asā mērce");
            JCheckBox ipasaMerce = new JCheckBox("Pavāra īpašā mērce");

            message = new Object[] {
                "Izvēlies piedevas:",
                kecups,
                majoneze,
                asaMerce,
                ipasaMerce
            };

            opcija = JOptionPane.showConfirmDialog(null, message, "Mērces", JOptionPane.OK_CANCEL_OPTION);
            if (opcija == JOptionPane.OK_OPTION) {
                if (kecups.isSelected()) merces.add("Kečups");
                if (majoneze.isSelected()) merces.add("Majonēze");
                if (asaMerce.isSelected()) merces.add("Asā mērce");
                if (ipasaMerce.isSelected()) merces.add("Pavāra īpašā mērce");
            }
            
            do{
                parole = JOptionPane.showInputDialog(null, "Ievadi paroli, kas būs jāuzrāda, kad saņemsi savu pasūtījumu(vismaz 5 rakstzīmes): ");
            }while(parole == null || parole.length() < 5);

        picasPasutijumi.add(new Pica(veids, izmers, new ArrayList<>(piedevas), new ArrayList<>(merces), 14.99, parole));
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

        JOptionPane.showMessageDialog(null, pasutijums, "Pasūtījuma informācija", JOptionPane.INFORMATION_MESSAGE);
    }

    static void sanemtPasutijumu(){
        if (picasPasutijumi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nav pieejamu pasūtījumu.", "Kļūda", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convert picasPasutijumi to a String array for selection
        String[] pasutijumiStr = picasPasutijumi.stream()
        .map(pica -> pica.toString()) // Convert each Pica to a string
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

        opcija = JOptionPane.showConfirmDialog(null, pasutijums+"\nVai Vēlies saņemt šo pasūtījumu?", "Pasūtījuma informācija", JOptionPane.OK_CANCEL_OPTION);
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