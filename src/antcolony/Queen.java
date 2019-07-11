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
public class Queen extends Ant {
    
    // used to track/assign ID numbers of ants
    private int numOfChildren;
    // queen will be the only ant that has direct link to colony
    private Colony queensColony;
    
    Queen() {
        antType = "queen";
        lifeSpan = 20;
        antID = 0;
        hasTurn = true;
        alive = true;
        isFriendly = true;
        numOfChildren = 0;
    }
    
    Queen(Colony queensColony) {
        this();
        this.queensColony = queensColony;
    }
    
    @Override
    public void die() {
        super.die();
        this.queensColony.exists = false;
    }
    
    public void hatchAnt(String antType) {
        // happens every 10 turns (and a bunch when the colony is created)
        Ant babyAnt;
        ColonyNode currentNode = this.getCurrentLocation();
        this.numOfChildren++;
        switch (antType) {
            case "scout":
                babyAnt = new Scout(numOfChildren);
                break;
            case "forager":
                babyAnt = new Forager(numOfChildren);
                break;
            case "soldier":
                babyAnt = new Soldier(numOfChildren);
                break;
            case "random":
                // use randomizer in Simulation (not implmented yet)
                // placeholder code
                babyAnt = new Soldier(numOfChildren);
                break;
            default:
                System.out.println("Incorrect antType specified.  You get a bala.");
                babyAnt = new Bala();
                break;
        }
        // add ant to center node
        currentNode.addAnt(babyAnt);
    }
    
    public void consumeFood() {
        // queen needs to have a link to the node she is on
        ColonyNode currentNode = this.getCurrentLocation();
        // determine if there is food, and grab 1 unit
        // if no food, queen dies
        int currentFoodUnits = currentNode.getFoodUnits();
        if (currentFoodUnits > 0) {
            currentNode.setFoodUnits(currentFoodUnits - 1);
        } else {
            this.setAlive(false);
            this.queensColony.exists = false;
            System.out.println("Simulation ended");
            // need some sort of prompt to avoid abrupt exit
            // System.exit(0);
        }
    }
    
    public void takeTurn() {
        consumeFood();
        // every 10 turns hatch
        if (this.getTurn() % 10 == 0) {
            // randomly choose 
            // a. Forager - 50% b. Scout - 25% c. Soldier - 25%
            // random number between 0 and 100, 
            // 0-49: Forager
            // 50-75: Scout
            // 75-100: Soldier
            int antTypeChoice = Simulation.generateRandomNumber(100);
            if (antTypeChoice > 0 && antTypeChoice <= 50) {
                hatchAnt("forager");
            } else if (antTypeChoice > 50 && antTypeChoice <=75) {
                hatchAnt("scout");
            } else {
                hatchAnt("soldier");
            }
        }
        this.addTurn();
    }
}
