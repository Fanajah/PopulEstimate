package populestimate;

import java.awt.Dimension;
import javax.swing.*;
import java.util.List;

// Classe principale pour exécuter le programme
public class PopulEstimate extends JFrame {

    public PopulEstimate(List<Double> population, int startYear) {
        setTitle("PopulEstimate");
        Dimension size = new Dimension(1100,650);
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new PopulationGraphPanel(population, startYear));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        double r = 0.1; // Taux de croissance
        double P = 20; // Population initiale
        double dt = 0.1; // Pas de temps
        int steps = 100; // Nombre de pas de temps
        int startYear = 2020; // Année initiale (à spécifier par l'utilisateur)

        ExponentialGrowth model = new ExponentialGrowth(r, P, dt);
        List<Double> population = model.simulate(steps);

        for (int i = 0; i < population.size(); i++) {
            System.out.println("Step " + i + ": " + population.get(i));
        }

        PopulEstimate pE = new PopulEstimate(population, startYear);
    }
}
