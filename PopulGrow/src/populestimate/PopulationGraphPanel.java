package populestimate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

// Classe pour dessiner le graphique de la population
class PopulationGraphPanel extends JPanel {

    private List<Double> population;
    private int startYear;
    private int visibleRangeStart;
    private int visibleRangeEnd;
    private JButton leftButton;
    private JButton rightButton;

    public PopulationGraphPanel(List<Double> population, int startYear) {
        this.population = population;
        this.startYear = startYear;
        this.visibleRangeStart = 0;
        this.visibleRangeEnd = population.size() > 10 ? 45 : population.size(); // Affiche les 10 premières années par défaut

        leftButton = new JButton("<");
        leftButton.addActionListener(e -> moveLeft());

        rightButton = new JButton(">");
        rightButton.addActionListener(e -> moveRight());

        add(leftButton);
        add(rightButton);
    }

    // Méthode pour mettre à jour la vue selon l'indice
    private void updateView() {
        repaint(); // Redessiner le panneau
    }

    private void moveLeft() {
        if (visibleRangeStart > 0) {
            visibleRangeStart--;
            visibleRangeEnd--;
            updateView();
        }
    }

    private void moveRight() {
        if (visibleRangeEnd < population.size()) {
            visibleRangeStart++;
            visibleRangeEnd++;
            updateView();
        }
    }

    DecimalFormat formatter = new DecimalFormat("0.##E0"); // Utiliser un format scientifique

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int padding = 50;
        int labelPadding = 20;
        int numYDivisions = 10;
        // Dessiner les axes
        g2d.drawLine(padding + 50, height - padding, width - padding+30, height - padding); // Axe X
        g2d.drawLine(padding + 50, padding, padding + 50, height - padding); // Axe Y

        // Dessiner les étiquettes et les marques de graduation sur l'axe Y (Population)
        g2d.setColor(Color.BLACK);
        double maxPopulation = population.stream().mapToDouble(v -> v).max().orElse(1.0);
        double populationIncrement = maxPopulation / numYDivisions;
        for (int i = 0; i <= numYDivisions; i++) {
            double populationValue = populationIncrement * i;
            String yLabel = formatPopulation(populationValue);
            int y = height - padding - (i * (height - 2 * padding)) / numYDivisions;
            g2d.drawLine(padding + 50, y, padding + 45, y); // Marque de graduation
            g2d.drawString(yLabel, padding - labelPadding, y + (g2d.getFontMetrics().getHeight() / 2) - 3);
        }

        // Dessiner les étiquettes et les marques de graduation sur l'axe X (Temps en années)
        int numXDivisions = visibleRangeEnd - visibleRangeStart;
        for (int i = 0; i <= numXDivisions; i++) {
            int x = padding + (i * (width - 2 * padding)) / numXDivisions + 50;
            int yearValue = startYear + visibleRangeStart + i;
            String xLabel = yearValue + "";
            g2d.drawLine(x, height - padding, x, height - padding + 5);
            if(i%2 == 1) g2d.drawString(xLabel, x - g2d.getFontMetrics().stringWidth(xLabel) / 2, height - padding + labelPadding);
        }

        // Dessiner la courbe de la population
        g2d.setColor(Color.RED);
        int prevX = padding + 50, prevY = height - padding;
        for (int i = visibleRangeStart; i < visibleRangeEnd; i++) {
            int x = padding + ((i - visibleRangeStart) * (width - 2 * padding)) / numXDivisions + 50;
            int y = height - padding - (int) ((population.get(i) / maxPopulation) * (height - 2 * padding));
            g2d.drawLine(prevX, prevY, x, y);
            prevX = x;
            prevY = y;
        }

        // Dessiner les étiquettes des axes
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Temps (années)", (width - padding) / 2, height - padding + 2 * labelPadding);
        g2d.drawString("Population", padding - 2 * labelPadding, padding / 2);
    }

    // Méthode pour formater la population en utilisant les suffixes K, M, etc.
    private String formatPopulation(double populationValue) {
        if (populationValue < 1e3) {
            return String.format("%.2f", populationValue);
        } else if (populationValue < 1e6) {
            return String.format("%.2fK", populationValue / 1e3);
        } else if (populationValue < 1e9) {
            return String.format("%.2fM", populationValue / 1e6);
        } else if (populationValue < 1e12) {
            return String.format("%.2fB", populationValue / 1e9);
        } else {
            return formatter.format(populationValue);
        }
    }
}
