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
public class AntColony {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // create 2D array for nodes in map
        // hide all ColonyNodeView objects to initialize all but queen's square (hideNode())
        // use setQueen()
        // set scout, forager, soldier, bala, food, pheremone level
        // assuming (for now) that array should be made up of ColonyNodeView instances
        // (though this might be redundant with ColonyView having similar dimensions)
        
        // create an instance of AntSimGUI
        AntSimGUI myAntSimGUI = new AntSimGUI();
        // create instance of Simulation, add reference to GUI
        Simulation antSimulation = new Simulation(myAntSimGUI);
    }
    
}
