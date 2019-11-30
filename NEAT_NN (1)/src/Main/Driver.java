/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.Arrays;

/**
 *
 * @author jason
 */
public class Driver {

    public static void main(String[] args) {

        Population myPop = new Population(100, 3, 1);

        double[][] testIns = {{0, 0, 1}, {0, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        double[][] testOuts = {{0}, {0}, {1}, {0}};
        
        myPop.evaluate(testIns, testOuts);
        myPop.printBest(testIns);
        
        for(int i = 0; i < 40; i++) {
        myPop.repopulate();
        myPop.evaluate(testIns, testOuts);
//myPop.evaluateConnectionCount();
        }
        
        myPop.printBest(testIns);
    }
}
