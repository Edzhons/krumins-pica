import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

class Pica {
    private String veids;
    private String izmers;
    private String[] piedevas;
    private String[] merces;
    private double cena;

    public Pica(String veids, String izmers, String[] piedevas, String[] merces, double cena) {
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
        System.out.print("Mērces: ");
        for (String merce : merces) {
            System.out.print(merce + " ");
        }

        System.out.println("\nCena: " + cena + " EUR\n");
    }
}

public class App {
    public static void main(String[] args) throws Exception {

        List<Pica> picasPasutijumi = new ArrayList<>();
        String izvele = "iziet";
        String[] darbibas = {"Margarita", "Salami", "Hawaii", "Peperoni (asa)", "Veģetārā"};
        int izvelesIndekss = 0;
        do{
            String veids = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies jautājumu:",
                "Darbības izvēle",
                JOptionPane.QUESTION_MESSAGE,
                null,
                darbibas,
                darbibas[0]
            );
            izvelesIndekss = Arrays.asList(darbibas).indexOf(izvele);

            if (veids == null) {
                JOptionPane.showMessageDialog(null, "Programma apturēta.");
                break;
            }

            switch(izvelesIndekss){
                case 0:
                
                break;
            }

            picasPasutijumi.add(new Pica(veids, "Liela", new String[]{"Siers", "Pepperoni"}, new String[]{"Majonēze", "Kečups"}, 14.99));
            for (Pica pica : picasPasutijumi) {
                pica.izvaditPasutijumus();
            }
        }while(izvelesIndekss != 3);
    }
}
