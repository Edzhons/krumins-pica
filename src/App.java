import java.util.Arrays;

import javax.swing.JOptionPane;

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
        }while(izvelesIndekss != 3);
    }
}
