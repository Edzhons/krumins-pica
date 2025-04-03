import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
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

    public Pica(String veids, String izmers, List<String> piedevas, List<String> merces, double cena) {
        this.veids = veids;
        this.izmers = izmers;
        this.piedevas = piedevas;
        this.merces = merces;
        this.cena = cena;
    }

    public void izvaditPasutijumu() {
        System.out.println("Picas veids: " + veids);
        System.out.println("Izmērs: " + izmers);
        System.out.print("Extra piedevas: ");
        for (String piedeva : piedevas) {
            System.out.print(piedeva + " ");
        }
        System.out.print("\nMērces: ");
        for (String merce : merces) {
            System.out.print(merce + " ");
        }

        System.out.println("\n\nCena: " + cena + " EUR");
    }
    @Override
    public String toString() {
        return String.format("%s (%s) - %.2f EUR\nPiedevas: %s\nMērces: %s",
            veids, izmers, cena,
            piedevas.isEmpty() ? "Nav" : String.join(", ", piedevas),
            merces.isEmpty() ? "Nav" : String.join(", ", merces)
        );
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
                    break;
                case 1:
                    apskatitPasutijumus();
                    break;
                case 2:
                    sanemtPasutijumu();
                    break;
            }
        
        }while(izvelesIndekss != 3);
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

        picasPasutijumi.add(new Pica(veids, izmers, new ArrayList<>(piedevas), new ArrayList<>(merces), 14.99));
            for (Pica pica : picasPasutijumi) {
                pica.izvaditPasutijumu();
            }

            String fNosaukums = "pasutijumuParoles";
            Pica pedejaPica = picasPasutijumi.get(picasPasutijumi.size() - 1);
            String teksts = pedejaPica + " - " + parole;
        try{
			FileWriter fw = new FileWriter(new File(fNosaukums+".txt"), true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(teksts);
			pw.close();
			JOptionPane.showMessageDialog(null, "Pasūtījums saglabāts failā "+fNosaukums);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Kļūme ierakstot failā!", "Kļūme!", JOptionPane.WARNING_MESSAGE);
		}
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
                picasPasutijumi.remove(indekss); // Remove from list
                JOptionPane.showMessageDialog(null, "Pasūtījums veiksmīgi izņemts, labu apetīti! ;]");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Pasūtījums netika izņemts!");
        }
    }
}