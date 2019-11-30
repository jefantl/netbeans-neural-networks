/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author jason
 */
public class Population {

    Genome[] genomes;
    Counter innovationCounter;
    ArrayList<ArrayList<Genome>> species = new ArrayList<>();

    private final float MUTATION_RATE = 0.1f;
    private final float ADD_CONNECTION_RATE = 0.1f;
    private final float ADD_NODE_RATE = 0.1f;

    public Population(int popSize, int ins, int outs) {
        innovationCounter = new Counter();
        genomes = new Genome[popSize];
        genomes[0] = new Genome(ins, outs, innovationCounter);

        for (int i = 1; i < popSize; i++) {
            genomes[i] = genomes[0].copy();
            while (genomes[i].connections.size() < 1) {
                genomes[i].mutate(MUTATION_RATE, ADD_CONNECTION_RATE, ADD_NODE_RATE);
            }
        }
    }

    public void evaluate(double[][] inData, double[][] outData) {
        if (outData[0].length != genomes[0].getOutputCount()) {
            System.err.println("out data doesnt match pops out data size");
            return;
        }
        if (inData[0].length != genomes[0].getInputCount()) {
            System.err.println("in data doesnt match pops in data size");
            return;
        }

        for (int genomeIndex = 0; genomeIndex < genomes.length; genomeIndex++) {
            double dis = 0;
            double[][] genomeGuess = new double[outData.length][outData[0].length];

            for (int outIndex = 0; outIndex < outData.length; outIndex++) {
                genomeGuess[outIndex] = genomes[genomeIndex].feedForward(inData[outIndex]);

                for (int outValue = 0; outValue < outData[0].length; outValue++) {
                    dis += Math.pow(genomeGuess[outIndex][outValue] - outData[outIndex][outValue], 2);
                }
            }
            dis = Math.sqrt(dis);

            genomes[genomeIndex].fitness = 1/dis;
        }
    }

    public void evaluateConnectionCount() {
        for (int genomeIndex = 0; genomeIndex < genomes.length; genomeIndex++) {
            genomes[genomeIndex].fitness = genomes[genomeIndex].connections.size();
        }
    }

    public void repopulate() {
        ArrayList<ArrayList<Genome>> newSpecies = new ArrayList<>();
        int replaceIndex = 0;

        //repops genome array
        for (ArrayList<Genome> specie : species) {
            for (int genomeIndex = 0; genomeIndex < specie.size(); genomeIndex++) {
                //tournament parent choosing
                Genome parent1 = specie.get(genomeIndex);
                Genome parent2 = specie.get((int) (Math.random() * specie.size()));
                for (int i = 0; i < specie.size() / 10; i++) { //pick a random 10% of population
                    Genome possibleBest = specie.get((int) (Math.random() * specie.size()));
                    if (possibleBest.fitness > parent1.fitness) {
                        parent1 = possibleBest.copy();
                    }
                    possibleBest = specie.get((int) (Math.random() * specie.size()));
                    if (possibleBest.fitness > parent2.fitness) {
                        parent2 = possibleBest.copy();
                    }
                }

                Genome child = GenomeUtl.crossOver(parent1, parent2);
                child.mutate(MUTATION_RATE, ADD_CONNECTION_RATE, ADD_NODE_RATE);
                genomes[replaceIndex] = child;
                replaceIndex++;
            }
        }

        //repops species
        species.clear();
        species.add(new ArrayList<>());
        species.get(0).add(genomes[0]);

        double maxGDistance = 0.7;
        for (int i = 1; i < genomes.length; i++) {
            boolean placed = false;
            for (ArrayList<Genome> specie : species) {
                if (GenomeUtl.geneticDistance(genomes[i], specie.get(0)) < maxGDistance) {
                    specie.add(genomes[i]);
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                species.add(new ArrayList<>());
                species.get(species.size() - 1).add(genomes[0]);
            }
        }
    }

    public void printBest(double[][] ins) {
        Genome bestG = genomes[0];
        for (Genome g : genomes) {
            if (g.fitness > bestG.fitness) {
                bestG = g;
            }
        }
        GenomeUtl.print(bestG);
        for (double[] d : ins) {
            System.out.println(Arrays.toString(bestG.feedForward(d)));
        }
        System.out.println(species.size());
    }
}
