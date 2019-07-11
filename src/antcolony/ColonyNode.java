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
// see if this needs to extend anything for node traversal
public class ColonyNode {
     // this will be the model implementation of the ColonyNodeView class
    // Colony datatype should be a graph, 
    // this should be able to hold a series of nodes of ants
    
    private int xCoord;
    private int yCoord;
    private int foodUnits;
    private int pheromoneUnits;
    private boolean isOpen;
    private boolean isEntrance;
    // ant stats
    boolean hasQueen;
    private int numOfScouts;
    private int numOfSoldiers;
    private int numOfForagers;
    private int numOfBalas;
    // use LinkedList initially until you come up with better solution
    // will pop the list into a queue for taking turns
    private LinkedList ants = new LinkedList();
    // need a reference to view
    private ColonyNodeView currentNodeView = new ColonyNodeView();
    // need a reference to Colony
    private Colony currentColony;

    // default constructor
    // ColonyNode() {
        // uninitialized values
        // this(-1,-1);
        // setX(-1);
        // setY(-1);
    // }
    
    
    ColonyNode(int x, int y, Colony currentColony) {
        setX(x);
        setY(y);
        this.isOpen = false;
        this.currentColony = currentColony;
    }
    
    public void updateColonyNodeView(int totalTurns) {
        this.currentNodeView.setID(this.getCoord());
        // run a check to see if queen is there, then set
        if (this.hasQueen) {
            this.currentNodeView.setQueen(true);
            this.currentNodeView.showQueenIcon();
        } else {
            this.currentNodeView.setQueen(false);
            this.currentNodeView.hideQueenIcon();
        }
        
        this.currentNodeView.setForagerCount(this.getNumOfForagers());
        if (this.getNumOfForagers() > 0) {
            this.currentNodeView.showForagerIcon();
        } else
            this.currentNodeView.hideForagerIcon();
        
        this.currentNodeView.setSoldierCount(this.getNumOfSoldiers());
        if (this.getNumOfSoldiers() > 0) {
            this.currentNodeView.showSoldierIcon();
        } else
            this.currentNodeView.hideSoldierIcon();
        
        this.currentNodeView.setScoutCount(this.getNumOfScouts());
        if (this.getNumOfScouts() > 0) {
            this.currentNodeView.showScoutIcon();
        } else
            this.currentNodeView.hideScoutIcon();
        
        this.currentNodeView.setBalaCount(this.getNumOfBalas());
        if (this.getNumOfBalas() > 0) {
            this.currentNodeView.showBalaIcon();
        } else
            this.currentNodeView.hideBalaIcon();
        
        this.currentNodeView.setFoodAmount(this.getFoodUnits());
        // check if node has been discovered
        if (this.isOpen)
            this.currentNodeView.showNode();
        else
            this.currentNodeView.hideNode();
        // update pheromone levels
        if (totalTurns % 10 == 0)
            this.pheromoneUnits = this.pheromoneUnits / 2;
        this.currentNodeView.setPheromoneLevel(this.getPheromoneUnits());
    }
    
    public void addAnt(Ant newAnt) {
        // add object to linked list
        this.ants.add(newAnt);
        // give ant location info
        newAnt.setCurrentLocation(this);
        // update node stats
        if (newAnt instanceof Queen) {
            hasQueen = true;
        } else if (newAnt instanceof Scout) {
            setNumOfScouts(getNumOfScouts() + 1);
        } else if (newAnt instanceof Soldier) {
            setNumOfSoldiers(getNumOfSoldiers() + 1);
        } else if (newAnt instanceof Forager) {
            setNumOfForagers(getNumOfForagers() + 1);
        } else if (newAnt instanceof Bala) {
            setNumOfBalas(getNumOfBalas() + 1);
        }
    }
    
    public LinkedList returnAntList() {
        return this.ants;
    }
    
    // returns a count of specified ant type
    /*
    public int getAntTypeCount(String antType) {
        // see if you can use iterator to trace - not sure if HashMap is the best type
        if (this.ants.containsValue(Queen)) {
            return 1;
        } else
            return 0;
    }
    */
    
    public void setFoodUnits(int foodAmount) {
        this.foodUnits = foodAmount;
    }
    
    
    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    
    public boolean isOpen() {
        return this.isOpen;
    }
    
    private void setEntrance(boolean setEntrance) {
        this.isEntrance = setEntrance;
    }
    
    public int getX() {
        return xCoord;
    }
    
    public int getY() {
        return yCoord;
    }
    
    public String getCoord() {
        return Integer.toString(this.getX())+","+Integer.toString(this.getY());
    }
    
    private void setX(int x) {
        this.xCoord = x;
    }
    
    private void setY(int y) {
        this.yCoord = y;
    }
    
    
    public int getFoodUnits() {
        return this.foodUnits;
    }
    
    public void isCenter(boolean isEntrance) {
        this.isEntrance = isEntrance;
    }
    
    public ColonyNodeView getColonyNodeView() {
        return this.currentNodeView;
    }
    
    // only used initially (and for testing)
    public void setNumOfScouts(int numOfScouts) {
        this.numOfScouts = numOfScouts;
    }
    
    public void setNumOfSoldiers(int numOfSoldiers) {
        this.numOfSoldiers = numOfSoldiers;
    }
    
    public void setNumOfForagers(int numOfForagers) {
        this.numOfForagers = numOfForagers;
    }
    
    public void setNumOfBalas(int numOfBalas) {
        this.numOfBalas = numOfBalas;
    }
    
    public int getNumOfScouts() {
        return this.numOfScouts;
    }
    
    public int getNumOfSoldiers() {
        return this.numOfSoldiers;
    }
    
    public int getNumOfForagers() {
        return this.numOfForagers;
    }
    
    public int getNumOfBalas() {
        return this.numOfBalas;
    }
    
    public boolean hasAnts() {
        return !this.ants.isEmpty();
    }
    
    public LinkedList getAnts() {
        return this.ants;
    }
    
    public Colony getColony() {
        return this.currentColony;
    }
    
    public void removeAnt(Ant antToRemove) {
        // you might have created more than one retrieval method for this - clean up if time
        LinkedList antList = this.getAnts();
        if (antList.remove(antToRemove)) {
            // update view
            if (antToRemove instanceof Scout)
                this.numOfScouts--;
            else if (antToRemove instanceof Soldier)
                this.numOfSoldiers--;
            else if (antToRemove instanceof Forager)
                this.numOfForagers--;
            else if (antToRemove instanceof Bala)
                this.numOfBalas--;
            // update model
            this.getAnts().remove(antToRemove);
        }
        else
            System.out.println("ant not found");
    }
    
    public int getPheromoneUnits() {
        return this.pheromoneUnits;
    }
    
    public void setPheromoneUnits(int pheromoneUnits) {
        this.pheromoneUnits = this.pheromoneUnits + pheromoneUnits;
    }
    
    /*
    private void setAnts(LinkedList newAntList) {
        this.ants = newAntList;
    }*/
}
