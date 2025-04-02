import java.util.Arrays;

import javax.swing.JOptionPane;

class Pica {
    private String izmers;
    private String[] piedevas;
    private String[] merces;
    private double cena;

    public Pica(String size, String[] toppings, double price) {
        this.izmers = izmers;
        this.piedevas = piedevas;
        this.merces = merces;
        this.cena = cena;
    }
}

public class App {
    public static void main(String[] args) throws Exception {

        String izvele;
        String[] darbibas = {"Margarita", "Salami", "Hawaii", "Peperoni (asa)", "Veģetārā"};
        int izvelesIndekss = 0;
        do{
            izvele = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlies jautājumu:",
                "Darbības izvēle",
                JOptionPane.QUESTION_MESSAGE,
                null,
                darbibas,
                darbibas[0]
            );
            izvelesIndekss = Arrays.asList(darbibas).indexOf(izvele);

            if (izvele == null || izvele.equals("Iziet")) {
                JOptionPane.showMessageDialog(null, "Programma apturēta.");
                break;
            }

            switch(izvelesIndekss){
                case 0:
                
                break;
            }
        }while(izvelesIndekss != 3);
    }
}
