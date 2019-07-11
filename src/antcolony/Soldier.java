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
public class Soldier extends Ant {
    
    // can be "scout" or "attack"
    String primaryMode;
    // will be null unless bala nearby
    ColonyNode balaNode;
    
    Soldier() {
        super.setID(-1);
    }
    
    Soldier(int idNumber) {
        super(idNumber);
        primaryMode = "scout";
        this.isFriendly = true;
    }
    
    public void takeTurn() {
        // check for presence of balas
        // for debugging
        System.out.println("***");
        System.out.println("Soldier: Checking for balas");
        if (hasBala()) {
            primaryMode = "attack";
        } else
            primaryMode = "scout";
        
        // for debugging
        System.out.println("Soldier: checking primary mode");
        switch (primaryMode) {
            case "scout":
                scoutMode();
                break;
            case "attack":
                attackMode();
                break;
            default:
                System.out.println("Incorrect soldier mode - typo");
                break;
        }
        // for debugging
        System.out.println("Soldier: " + primaryMode);
        System.out.println("***");
        this.addTurn();
    }
    
    private void scoutMode() {
        // scan adjacent squares for balas
        // debugging
        System.out.println("Soldier: checking surrounding nodes");
        LinkedList nodeList = viewSurroundingNodes();
        if(scanNodesForBalas(nodeList) == true) {
            // if one found, move into that square
            moveToBala(nodeList);
        } else {
            // move randomly
            moveRandom(nodeList);
        }
    }
    
    private void attackMode() {
        // verify bala still present
        // isAnts will check for ants and attack if they exist
        if(isAnts()) {
            
        } else {
            // go into scout mode
            primaryMode = "scout";
        }
        // pick one bala and attack
    }
    
    private boolean isAnts() {
        // need a reference to other ants on the node
        LinkedList listOfAnts = this.getCurrentLocation().getAnts();
        Ant currentAnt;
        int randomSelect;
        // if list is not empty, randomly select an ant
        // it will always contain this one ant at minimum
        System.out.println("Soldier: this many ants on current square: " + listOfAnts.size());
        if (listOfAnts.size() > 1) {
            randomSelect = Simulation.generateRandomNumber(listOfAnts.size());
            randomSelect--;
            currentAnt = (Ant) listOfAnts.get(randomSelect);
            // check to see if ant picked is friendly
            System.out.println("Soldier: checking current square for friendly");
            if (currentAnt.isFriendly == true) {
                // check to see if square is all friendly
                if (checkForAllFriendly(listOfAnts) == false) {
                    // it's another friendly, don't attack - reroll
                    while (currentAnt.isFriendly == true) {
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
    
    private boolean checkForAllFriendly(LinkedList listOfAnts) {
        System.out.println("Soldier: checking for all friendly");
        Ant currentAnt;
        ListIterator antIterator = listOfAnts.listIterator(0);
        while (antIterator.hasNext()) {
            currentAnt = (Ant) antIterator.getCurrent();
            if (currentAnt.isFriendly)
                return true;
            antIterator.next();
        }
        return false;
    }
    
    private void attackAnt(Ant currentAnt) {
        // 50% chance of successful attack
        boolean successfulAtack;
        if (Simulation.generateRandomNumber(2) > 1) {
            currentAnt.die();
            currentAnt.endTurn();
        }
    }
    
    private boolean scanNodesForBalas(LinkedList nodeList) {
        // pull up each node
        ListIterator nodeListIterator = nodeList.listIterator(0);
        ListIterator antListIterator;
        LinkedList antList;
        Ant currentAnt;
        // scan antList for non friendly
        ColonyNode currentNode = (ColonyNode) nodeListIterator.getCurrent();
        while(nodeListIterator.hasNext()) {
            antList = currentNode.getAnts();
            // check to see if there are ants in this node
            if (antList.isEmpty() == false) {
                // loop through list
                antListIterator = antList.listIterator(0);
                while (antListIterator.hasNext()) {
                    currentAnt = (Ant) antListIterator.getCurrent();
                    // check to see if they're friendly
                    // if non friendly found, return true
                    if (currentAnt.isFriendly == false) {
                        return true;
                    }
                    antListIterator.next();
                }
            }
            nodeListIterator.next();
        }
        // no unfriendly ants
        return false;
    }
    
    private void moveToBala(LinkedList nodeList) {
        // pull up each node
        ListIterator nodeListIterator = nodeList.listIterator(0);
        ListIterator antListIterator;
        LinkedList antList;
        Ant currentAnt;
        // scan antList for non friendly
        ColonyNode currentNode = (ColonyNode) nodeListIterator.getCurrent();
        while(nodeListIterator.hasNext()) {
            antList = currentNode.getAnts();
            // check to see if there are ants in this node
            if (antList.isEmpty() == false) {
                // loop through list
                antListIterator = antList.listIterator(0);
                while (antListIterator.hasNext()) {
                    currentAnt = (Ant) antListIterator.getCurrent();
                    // check to see if they're friendly
                    // if non friendly found, return true
                    if (currentAnt.isFriendly == false) {
                        // move to node
                        moveTo(currentNode);
                        break;
                    }
                    antListIterator.next();
                }
            }
            nodeListIterator.next();
        }
    }
    
    private void moveRandom(LinkedList locationOptions) {
        // pick randomly based off of list length
        // for debugging
        System.out.println("Soldier: Hasn't chosen direction yet");
        
        int chosenDirection = Simulation.generateRandomNumber(locationOptions.size());
        chosenDirection--;
        
        ColonyNode chosenNode = (ColonyNode) locationOptions.get(chosenDirection);
        
        // for debugging
        System.out.println("Soldier: Size of list of locations: " + locationOptions.size());
        System.out.println("Soldier: Chosen direction (# in list): " + chosenDirection);
        System.out.println("Soldier: Coordinates of chosen node: " + chosenNode.getCoord());
        // remove ant from old location
        this.getCurrentLocation().removeAnt(this);
        // set ant's location
        this.setCurrentLocation(chosenNode);
        // let new node know soldier has moved there
        chosenNode.addAnt(this);
        
    }
    
    private void moveTo(ColonyNode newLocation) {
        // remove ant from old location
        this.getCurrentLocation().removeAnt(this);
        // set ant's location
        this.setCurrentLocation(newLocation);
        // let new node know soldier has moved there
        newLocation.addAnt(this);
    }
    
    private boolean hasBala() {
        // checks if current location contains bala
        LinkedList antList = this.getCurrentLocation().getAnts();
        // iterate through antList and see if there are any non-friendly ants
        // this SHOULD always have at least one ant (the soldier) present
        ListIterator antListIterator = antList.listIterator(0);
        Ant currentAnt = (Ant) antListIterator.getCurrent();
        // for debugging
        System.out.println("Soldier: Before while loop");
        while (antListIterator.hasNext()) {
            if (currentAnt.isFriendly == false) {
                // if one found, return true
                return true;
            }
            antListIterator.next();
        }
        System.out.println("Soldier: After while loop");
        // no bala found
        return false;
    }
    
    private LinkedList viewSurroundingNodes() {
        int currentX = this.getCurrentLocation().getX();
        int currentY = this.getCurrentLocation().getY();
        boolean xAxisRightMovement = true;
        boolean xAxisLeftMovement = true;
        boolean yAxisUpMovement = true;
        boolean yAxisDownMovement = true;
        LinkedList nodeChoices = new LinkedList();
        // must get reference to new location
        ColonyNode newLocation;
        // add up to 8 surrounding OPEN nodes to a stack
        // combinations of (x+1, x-1, y+1, x+1)
        // push reference to nodeChoices
        
        // don't move right on X axis
        if ((currentX + 1) > 26)
            xAxisRightMovement = false;
        
        if ((currentX - 1) < 0)
            xAxisLeftMovement = false;
        
        // don't move on Y axis
        if ((currentY + 1) > 26)
            yAxisDownMovement = false;
        
        if ((currentY - 1) < 0)
            yAxisUpMovement = false;
        
        if (xAxisRightMovement) {
            newLocation = this.getCurrentLocation().getColony().antColonyArray[currentX+1][currentY];
            if (newLocation.isOpen())
                nodeChoices.add(newLocation);
        }
        
        if (xAxisLeftMovement) {
            newLocation = this.getCurrentLocation().getColony().antColonyArray[currentX-1][currentY];
            if (newLocation.isOpen())
                nodeChoices.add(newLocation);
        }
        
        if (yAxisDownMovement) {
            newLocation = this.getCurrentLocation().getColony().antColonyArray[currentX][currentY+1];
            if (newLocation.isOpen())
                nodeChoices.add(newLocation);
        }
        
        if (yAxisUpMovement) {
            newLocation = this.getCurrentLocation().getColony().antColonyArray[currentX][currentY-1];
            if (newLocation.isOpen())
                nodeChoices.add(newLocation);
        }
        
        if (xAxisRightMovement && yAxisDownMovement) {
            newLocation = this.getCurrentLocation().getColony().antColonyArray[currentX+1][currentY+1];
            if (newLocation.isOpen())
                nodeChoices.add(newLocation);
        }
        
        if (xAxisLeftMovement && yAxisUpMovement) {
            newLocation = this.getCurrentLocation().getColony().antColonyArray[currentX-1][currentY-1];
            if (newLocation.isOpen())
                nodeChoices.add(newLocation);
        }
        
        if (xAxisRightMovement && yAxisUpMovement) {
            newLocation = this.getCurrentLocation().getColony().antColonyArray[currentX+1][currentY-1];
            if (newLocation.isOpen())
                nodeChoices.add(newLocation);
        }
        
        if (xAxisLeftMovement && yAxisDownMovement) {
            newLocation = this.getCurrentLocation().getColony().antColonyArray[currentX-1][currentY+1];
            if (newLocation.isOpen())
                nodeChoices.add(newLocation);
        }
        
        return nodeChoices;
    }
    
}
