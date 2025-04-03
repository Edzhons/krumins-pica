import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

class Pica {
    private String veids;
    private String izmers;
    private List<String> piedevas;
    private String[] merces;
    private double cena;

    public Pica(String veids, String izmers, List<String> piedevas, String[] merces, double cena) {
        this.veids = veids;
        this.izmers = izmers;
        this.piedevas = piedevas;
        this.merces = merces;
        this.cena = cena;
    }

    public void izvaditPasutijumus() {
        System.out.println("Picas veids: " + veids);
        System.out.println("Izmērs: " + izmers);
        System.out.print("Extra piedevas: ");
        for (String piedeva : piedevas) {
            System.out.print(piedeva + " ");
        }
        System.out.println("Mērces: ");
        for (String merce : merces) {
            System.out.print(merce + " ");
        }

        System.out.println("\nCena: " + cena + " EUR\n");
    }
}

public class App {
    public static void main(String[] args) throws Exception {

        List<Pica> picasPasutijumi = new ArrayList<>();
        String[] darbibas = {"Margarita", "Salami", "Hawaii", "Peperoni (asa)", "Veģetārā"};
        List<String> piedevas = new ArrayList<>();
        int izvelesIndekss = 0;
        do{
            String veids = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies picas veidu:",
                "Cepam picu...",
                JOptionPane.QUESTION_MESSAGE,
                null,
                darbibas,
                darbibas[0]
            );
            izvelesIndekss = Arrays.asList(darbibas).indexOf(veids);
            if (veids == null) {
                JOptionPane.showMessageDialog(null, "Programma apturēta.");
                break;
            }
            
            darbibas = new String[]{"Liela", "Vidēja", "Maza"};
            String izmers = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies picas izmēru:",
                "Cepam picu...",
                JOptionPane.QUESTION_MESSAGE,
                null,
                darbibas,
                darbibas[0]
            );
            izvelesIndekss = Arrays.asList(darbibas).indexOf(izmers);
            if (izmers == null) {
                JOptionPane.showMessageDialog(null, "Programma apturēta.");
                break;
            }

            darbibas = new String[]{"Siers", "Pepperoni"};
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

            int izvele = JOptionPane.showConfirmDialog(null, message, "Piedevas", JOptionPane.OK_CANCEL_OPTION);
            if (izvele == JOptionPane.OK_OPTION) {
                if (siers.isSelected()) piedevas.add("Siers");
                if (pepperoni.isSelected()) piedevas.add("Pepperoni");
                if (senes.isSelected()) piedevas.add("Sēnes");
                if (olivas.isSelected()) piedevas.add("Olīvas");
            }else{
                
            }

            picasPasutijumi.add(new Pica(veids, izmers, piedevas, new String[]{"Majonēze", "Kečups"}, 14.99));
            for (Pica pica : picasPasutijumi) {
                pica.izvaditPasutijumus();
            }
        
        }while(izvelesIndekss != 3);
    }
}