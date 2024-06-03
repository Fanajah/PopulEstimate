/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package populestimate;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */

/**
 * Le modèle de croissance exponentielle est l'un des modèles les plus simples
 * et décrit une croissance sans contraintes. 
 * * dP/dt = r*P   (dy/dt = f(t,y))
 */
class ExponentialGrowth implements SousFonctionEuler {

    private double r; // Taux de croissance
    private double P; // Population initiale (y anaty lesona)
    private double dt; // Pas de temps (h anaty lesona)

    public ExponentialGrowth(double r, double P, double dt) {
        this.r = r;
        this.P = P;
        this.dt = dt;
    }

    // Méthode d'Euler pour calculer la population au fil du temps selon la croissance exponentielle
    public List<Double> simulate(int steps) {
        List<Double> population = new ArrayList<>();
        double currentP = P;

        // w = w + h*f(t,w)
        for (int t = 0; t < steps; t++) {
            population.add(currentP);
            double dP = calculateDP(r, currentP);
            currentP += dP * dt;
        }

        return population;
    }

    @Override
    public double calculateDP(double r, double P) {
        return r * P;
    }
}
