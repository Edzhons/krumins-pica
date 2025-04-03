import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CheckBoxExample {
    public static void main(String[] args) {
        JCheckBox cheese = new JCheckBox("Siers");
        JCheckBox pepperoni = new JCheckBox("Pepperoni");
        JCheckBox mushrooms = new JCheckBox("Sēnes");
        JCheckBox olives = new JCheckBox("Olīvas");

        Object[] message = {
            "Izvēlies piedevas:",
            cheese,
            pepperoni,
            mushrooms,
            olives
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Piedevas", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            List<String> selectedToppings = new ArrayList<>();

            if (cheese.isSelected()) selectedToppings.add("Siers");
            if (pepperoni.isSelected()) selectedToppings.add("Pepperoni");
            if (mushrooms.isSelected()) selectedToppings.add("Sēnes");
            if (olives.isSelected()) selectedToppings.add("Olīvas");

            System.out.println("Izvēlētās piedevas: " + String.join(", ", selectedToppings));
        } else {
            System.out.println("Lietotājs atcēla izvēli.");
        }
    }
}
