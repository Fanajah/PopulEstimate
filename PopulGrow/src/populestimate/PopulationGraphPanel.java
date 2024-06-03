/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package populestimate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
// Classe pour dessiner le graphique de la population
class PopulationGraphPanel extends JPanel {
    private List<Double> population;

    public PopulationGraphPanel(List<Double> population) {
        this.population = population;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int padding = 50;

        // Dessiner les axes
        g2d.drawLine(padding, height - padding, width - padding, height - padding); // Axe X
        g2d.drawLine(padding, padding, padding, height - padding); // Axe Y

        // Dessiner la courbe de la population
        g2d.setColor(Color.RED);
        double maxPopulation = population.stream().mapToDouble(v -> v).max().orElse(1.0);
        int prevX = padding, prevY = height - padding;
        for (int i = 0; i < population.size(); i++) {
            int x = padding + (i * (width - 2 * padding)) / population.size();
            int y = height - padding - (int) ((population.get(i) / maxPopulation) * (height - 2 * padding));
            g2d.drawLine(prevX, prevY, x, y);
            prevX = x;
            prevY = y;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
}