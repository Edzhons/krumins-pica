import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) throws Exception {
        Dati.bankas = new ArrayList<>(Banka.nolasaBankasKontus());
        Dati.picasPasutijumi = new ArrayList<>(Pica.nolasaPasutijumus());
        String[] darbibas = {"Banka", "Picērija", "Noteikumi / Instrukcija", "Iziet"};
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
                case 2:
                    noteikumi();
                    break;
            }
        
        }while(izvelesIndekss != 3);
    }

    static void noteikumi() {
        String noteikumi = "Sveiks! Šī ir programma, kas ļauj taisīt / rediģēt bankas kontus un veikt picērijas pasūtījumus.\n" +
                "Izvēlies kādu no pieejamajām opcijām un seko norādēm.\n" +
                "Ja vēlies iziet no programmas, izvēlies 'Iziet'.";
        JOptionPane.showMessageDialog(null, noteikumi, "Noteikumi", JOptionPane.INFORMATION_MESSAGE);
    }
}