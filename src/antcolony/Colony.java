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
public class Colony {
    // this class must store all instances of ColonyNode
    // use ListGraph
    int numberOfAnts;
    boolean exists;
    // private ListGraph antColonyGraph;
    ColonyView antColonyView;
    // create array for grid (used in updating view and model)
    public ColonyNode antColonyArray[][] = new ColonyNode[27][27];
    // this is an index of ColonyNodes used to retrieve from ListGraph
    // a better solution seems to be out there but I don't know it yet (redundant)
    // private LinkedList antColonyNodeList = new LinkedList();
    
    Colony() {
        // (view) create ColonyView
        antColonyView = new ColonyView(27,27);
        
        // (model) initialize all nodes, then set up center node
        // x/y assignments are counter-intuitive due to how for loop is structured
        int xCoord = 0;
        int yCoord = 0;
        for (ColonyNode[] y : antColonyArray) {
            for (ColonyNode x : y) {
                // create new node, along with its corresponding view
                // antColonyArray[xCoord][yCoord] = new ColonyNode(xCoord, yCoord);
                antColonyArray[yCoord][xCoord] = new ColonyNode(yCoord, xCoord, this);
                // placeFood(antColonyArray[xCoord][yCoord]);
                placeFood(antColonyArray[yCoord][xCoord]);
                antColonyView.addColonyNodeView(
                    antColonyArray[yCoord][xCoord].getColonyNodeView(), 
                    antColonyArray[yCoord][xCoord].getX(), 
                    antColonyArray[yCoord][xCoord].getY());
                // view test
                // antColonyArray[yCoord][xCoord].setOpen(true);
                yCoord++;
            }
            xCoord++;
            yCoord = 0;
        }
        
        antColonyArray[14][14].isCenter(true);
        
        // (model) add initial ant objects to ColonyNode
        Queen colonyQueen = new Queen(this);
        antColonyArray[14][14].addAnt(colonyQueen);
        // should put this somewhere else - or maybe not since we are initing
        // (total number of colony ants and current state of colony existance)
        numberOfAnts++;
        exists = true;
        
        // loop a ton to add 10 soldier ants using queen's birthing method 
        // (sorry, queen)
        for (int x = 0; x < 10; x++) {
            colonyQueen.hatchAnt("soldier");
        }
        
        // loop a ton to add 50 forager ants, then update numOfForagers
        for (int x = 0; x < 50; x++) {
            colonyQueen.hatchAnt("forager");
        }
        
        // add scout 4 scout ants, then update numOfScouts
        for (int x = 0; x < 4; x++) {
            colonyQueen.hatchAnt("scout");
        }
        
        // add 1000 units of food
        antColonyArray[14][14].setFoodUnits(1000);
        // make sure square and surrounding squares are marked as open 
        antColonyArray[13][13].setOpen(true);
        antColonyArray[13][14].setOpen(true);
        antColonyArray[13][15].setOpen(true);
        antColonyArray[14][13].setOpen(true);
        antColonyArray[14][14].setOpen(true);
        antColonyArray[14][15].setOpen(true);
        antColonyArray[15][13].setOpen(true);
        antColonyArray[15][14].setOpen(true);
        antColonyArray[15][15].setOpen(true);
        
        // (view) this updates the status of all items in the node
        // updateColonyView();
    }
    
    public void updateColonyView(int totalTurns) {
        // iterate through array to add/remove open views to ColonyView
        // for each node, updateColonyNodeView();
        int xCoord = 0;
        int yCoord = 0;
        for (ColonyNode[] y : antColonyArray) {
            for (ColonyNode x : y) {
                // might speed up if you check to see if node is open and viewable first
                if (antColonyArray[xCoord][yCoord].isOpen())
                    antColonyArray[xCoord][yCoord].updateColonyNodeView(totalTurns);
                xCoord++;
            }
            yCoord++;
            xCoord = 0;
        }
    }
    
    private void placeFood(ColonyNode currentNode) {
        // add in random amounts of food 25% chance
        // other 75% has no food
        int antTypeChoice = Simulation.generateRandomNumber(100);
        if (antTypeChoice >= 0 && antTypeChoice < 25) {
            // will have between 500 & 1000 units
            // use 500 for both values below
            int foodAmount = Simulation.generateRandomNumber(500) + 500;
            currentNode.setFoodUnits(foodAmount);
        } else {
            currentNode.setFoodUnits(0);
        }
    }
    
    public ColonyView getColonyView() {
        return this.antColonyView;
    }
    
    /*
    public LinkedList getColonyNodeList () {
        return this.antColonyNodeList;
    }
    */
    
    /*
    public ListGraph getColonyGraph () {
        return this.antColonyGraph;
    }
    */
    
    public static void doesExist() {
        
    }
    
    public int returnAntCount() {
        
        
        return this.numberOfAnts;
    }
}
