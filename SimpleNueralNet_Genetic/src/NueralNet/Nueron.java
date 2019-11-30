/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NueralNet;

import java.io.Serializable;


public class Nueron implements Serializable{
public double value, bias;
    public Nueron() {
        value= 0;
        bias = Math.random();
    }
}
