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
public class Bala extends Ant {
    
    int newX;
    int newY;
    // for testing
    boolean canMove;
    
    Bala() {
        super(-1);
        this.isFriendly = false;
    }
    
    Bala(ColonyNode spawnNode) {
        this();
        super.setCurrentLocation(spawnNode);
        // flip to true once done testing class
        canMove = true;
    }
    
    public void takeTurn() {
        System.out.println("*** Bala ***");
        // check for ants to attack
        System.out.println("Bala: Checking for ants");
        if (isAnts()) {
            // isAnts will check for ants and attack if they exist
            // it returns false if there are no other ants besides the bala
        } else {
            if (canMove) {
                rollDirection();
                System.out.println("Bala: Direction rolled, about to move");
                moveTo(newX, newY);
                System.out.println("Bala: Has moved");
            }
        }
        System.out.println("***");
        this.addTurn();
    }
    
    private boolean isAnts() {
        // need a reference to other ants on the node
        LinkedList listOfAnts = this.getCurrentLocation().getAnts();
        Ant currentAnt;
        int randomSelect;
        // if list is not empty, randomly select an ant
        // it will always contain this one ant at minimum
        System.out.println("Bala: this many ants on current square: " + listOfAnts.size());
        if (listOfAnts.size() > 1) {
            randomSelect = Simulation.generateRandomNumber(listOfAnts.size());
            randomSelect--;
            currentAnt = (Ant) listOfAnts.get(randomSelect);
            // check to see if ant picked is bala
            System.out.println("Bala: checking current square for bala");
            if (currentAnt.isFriendly == false) {
                // check to see if square is all balas
                if (checkForAllBalas(listOfAnts) == false) {
                    // it's another bala, don't attack - reroll
                    while (currentAnt.isFriendly == false) {
                        randomSelect = Simulation.generateRandomNumber(listOfAnts.size());
                        randomSelect--;
                        currentAnt = (Ant) listOfAnts.get(randomSelect);
                    }
                }
                
            }
            
            // check to see if ant's isFriendly tag equals true
            // if true, attack
            if (currentAnt.getAlive()) {
                attackAnt(currentAnt);
                return true;
            }
        }
        // if false, move (this method will return false)
        return false;
    }
    
    private boolean checkForAllBalas(LinkedList listOfAnts) {
        System.out.println("Bala: checking for all balas");
        Ant currentAnt;
        ListIterator antIterator = listOfAnts.listIterator(0);
        while (antIterator.hasNext()) {
            currentAnt = (Ant) antIterator.getCurrent();
            if (currentAnt.isFriendly)
                return false;
            antIterator.next();
        }
        return true;
    }
    
    private void attackAnt(Ant currentAnt) {
        // 50% chance of successful attack
        boolean successfulAtack;
        if (Simulation.generateRandomNumber(2) > 1) {
            currentAnt.die();
            // set attacked ant's hasTurn to false if attack succeeds
            currentAnt.endTurn();
            // special condition for queen only
            if (currentAnt instanceof Queen) {
                currentAnt.getCurrentLocation().hasQueen = false;
            }
        } 
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
        // for debugging
        System.out.println("Bala rolling direction");
    }
}
