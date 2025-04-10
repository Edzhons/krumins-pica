import java.util.Arrays;
import javax.swing.JOptionPane;

public class Main {
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
                    Pica.picerijasIzvele();
                    break;
            }
        
        }while(izvelesIndekss != 2);
    }
}