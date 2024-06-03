/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package populestimate;

/**
 *
 * @author User
 */
import javax.swing.*;
import java.util.List;

// Classe principale pour exécuter le programme
public class PopulEstimate extends JFrame {

    public PopulEstimate(List<Double> population) {
        setTitle("Population Growth Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new PopulationGraphPanel(population));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        double r = 0.1; // Taux de croissance
        double P = 100; // Population initiale
        double dt = 0.1; // Pas de temps
        int steps = 100; // Nombre de pas de temps

        ExponentialGrowth model = new ExponentialGrowth(r, P, dt);
        List<Double> population = model.simulate(steps);

        for (int i = 0; i < population.size(); i++) {
            System.out.println("Step " + i + ": " + population.get(i));
        }
    }
}
