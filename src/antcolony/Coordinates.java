/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolony;

/**
 *
 * @author brandon
 */
public class Coordinates {
    int x;
    int y;
    
    public String returnCoordinates() {
        String coordinateString;
        coordinateString = Integer.toString(x) + "," + Integer.toString(y);
        
        return coordinateString;
    }
}
