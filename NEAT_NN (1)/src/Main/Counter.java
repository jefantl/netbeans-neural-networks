/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

public class Counter {

    int count;

    public Counter() {
        count = 0;
    }

    public int getNext() {
        count++;
        return count;
    }
}