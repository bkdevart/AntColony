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
public class Scout extends Ant {
    
    private int newX;
    private int newY;
    
    Scout() {
        // no ID specified, -1 is assigned to indicate error    
        super.setID(-1);
    }
            
    Scout(int idNumber) {
        super(idNumber);
        this.isFriendly = true;
        // super.setID(idNumber);
    }
    
    public void takeTurn() {
        // will randomly choose 1 of 8 directions
        // must exclude directions at edge of map (negative numbers and > 26)
        rollDirection();
        // will move in chosen direction regardless if open or closed
        moveTo(newX, newY);
        // reset newX and newY for next turn
        // newX = -1;
        // newY = -1;
        this.addTurn();
    }
    
    private void moveTo(int x, int y) {
        // remove ant from old location
        this.getCurrentLocation().removeAnt(this);
        // must get reference to new location
        ColonyNode newLocation;
        // pull reference to new location
        newLocation = this.getCurrentLocation().getColony().antColonyArray[x][y];
        // set ant's location
        this.setCurrentLocation(newLocation);
        // let new node know scout has moved there
        newLocation.addAnt(this);
        // if node is closed, scout will open it
        newLocation.setOpen(true);     
    }
    
    /** 
     * this does something
     */
    private void rollDirection() {
        // get current location
        
        ColonyNode currentNode = this.getCurrentLocation();
        int x = currentNode.getX();
        int y = currentNode.getY();
        int chosenDirection = Simulation.generateRandomNumber(8);
        switch (chosenDirection) {
            case 1:
                // North
                this.newX = x;
                this.newY = y - 1;
                break;
            case 2:
                // Northeast
                this.newX = x + 1;
                this.newY = y - 1;
                break;
            case 3:
                // East
                this.newX = x + 1;
                this.newY = y;
                break;
            case 4:
                // Southeast
                this.newX = x + 1;
                this.newY = y + 1;
                break;
            case 5:
                // South
                this.newX = x;
                this.newY = y + 1;
                break;
            case 6:
                // Southwest
                this.newX = x - 1;
                this.newY = y + 1;
                break;
            case 7:
                // West
                this.newX = x - 1;
                this.newY = y;
                break;
            case 8:
                // Northwest
                this.newX = x - 1;
                this.newY = y - 1;
                break;
            default:
                System.out.println("Something went wrong with choosing scout direction");
                break;
                
                
                
        }
        // can't go out of range
        // watch this - don't want an infinite recursion call
        if (newX > 26 || newY > 26 || newX < 0 || newY < 0)
            rollDirection();
    }
}
