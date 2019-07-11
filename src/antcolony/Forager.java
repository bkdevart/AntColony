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
public class Forager extends Ant {
    
    // can be "forage" or "nest" or "scatter"
    String movementMode = "forage";
    // movement history should be a stack
    LinkedStack nodeHistoryStack = new LinkedStack();
    // used for detecting movement loops
    // LinkedStack previousNodeHistoryStack = new LinkedStack();
    int numberOfRepeatedLoops = 0;
    boolean carryingFood = false;
    int timeInScatter = 0;
    
    Forager() {
        // no ID set
        super.setID(-1);
    }
    
    Forager(int idNumber) {
        super(idNumber);
        this.isFriendly = true;
    }
    
    public void takeTurn() {
        System.out.println("*** Forager ***");
        switch (this.movementMode) {
            case "nest":
                System.out.println("Forager: beginning nestBehavior");
                nestBehavior();
                break;
            case "forage":
                System.out.println("Forager: beginning forageBehavior");
                forageBehavior();
                break;
            case "scatter":
                System.out.println("Forager: beginning scatterBehavior");
                scatterBehavior();
                break;
            default:
                System.out.println("Incorrect forager mode - typo");
                break;
        }
        System.out.println("***");
        this.addTurn();
    }
    
    private void scatterBehavior() {
        timeInScatter++;
        
        LinkedList availableNodes = this.viewSurroundingNodes();
        if (timeInScatter < 5) {
            int randomInt = Simulation.generateRandomNumber(availableNodes.size());
            randomInt--;
            ColonyNode nodeToMove = (ColonyNode) availableNodes.get(randomInt);
            // add current node to nodeHistoryStack
            System.out.println("Forager scattering, nodeHistoryStack size:" + nodeHistoryStack.size());
            this.nodeHistoryStack.push(this.getCurrentLocation());

            // move
            this.moveTo(nodeToMove);

            // pick up food (unless already carrying, no food, or on queen node)
            if (!this.carryingFood && nodeToMove.getFoodUnits() > 0 && !nodeToMove.hasQueen) {
                pickupFood();  
            }
        } else {
            // reset scatter count
            timeInScatter = 0;
            // this mimics the forage behavior
            this.movementMode = "forage";
            forageBehavior();
        }
    }
    
    private void forageBehavior() {
        // ColonyNode lastNodeVisited;
        // move to adjacent square with most pheromone
        ColonyNode nodeToMove = huffPheromone();
        // going to need to check this node against move history
        // history will be empty when leaving queen's node
        if (!nodeHistoryStack.isEmpty()) {
            // check to see if the new node is one just moved from
            if (nodeToMove.equals((ColonyNode) nodeHistoryStack.peek())) {
                // you will probably find a reason for the forager to trace old node
                // don't know why yet, but put the fix here when it happens
                // reroll a node to move to
                // **** this is where infinite loop occurs after food has been depleted
                // occurs if moving away from a node with higher pheromone (food depleted)
                // depleted node has more pheromone, so it is initially selected,
                // but because it is the last place chosen it is dismissed and we loop infinitely
                while(nodeToMove.equals((ColonyNode) nodeHistoryStack.peek())) {
                    numberOfRepeatedLoops++;
                    nodeToMove = huffPheromone();
                    if (numberOfRepeatedLoops > 10) {
                        // roll a random direction and go there
                        // this might not be enough - flip the forager into a "scatter" mode
                        // scatter mode will last 10 turns, then will flip back into forage mode
                        
                        this.movementMode = "scatter";
                        break;
                    }
                    System.out.println(numberOfRepeatedLoops);
                }
                numberOfRepeatedLoops = 0;   
                
                //}
            }
        }
        // add current node to nodeHistoryStack
        this.nodeHistoryStack.push(this.getCurrentLocation());
        
        // move
        this.moveTo(nodeToMove);

        // pick up food (unless already carrying, no food, or on queen node)
        if (!this.carryingFood && nodeToMove.getFoodUnits() > 0 && !nodeToMove.hasQueen) {
            pickupFood();  
        }
    }
    
    private void nestBehavior() {
        // check to see if on queen node
        // deposit 10 pheromone units in square if pheromone < 1000
        // don't do on queen's node
        if (!this.getCurrentLocation().hasQueen && this.getCurrentLocation().getPheromoneUnits() < 1000)
            depositPheromone();
        // will be relying on nodeHistoryStack for movement
        System.out.println("Forager: nodeHistoryStack size:" + nodeHistoryStack.size());
        ColonyNode nextNode = (ColonyNode) this.nodeHistoryStack.peek();
        nodeHistoryStack.pop();
        // move to next node
        moveTo(nextNode);
        
        // drop food if on queen node
        if (nextNode.hasQueen) {
            dropFood();
            // go back into forager mode
            this.movementMode = "forage";
            // reset movement history
            this.nodeHistoryStack.clear();
        }
    }
    
    private void dropFood() {
        this.getCurrentLocation().setFoodUnits(this.getCurrentLocation().getFoodUnits() + 1);
        this.carryingFood = false;
    }
    
    private void depositPheromone() {
        this.getCurrentLocation().setPheromoneUnits(10);
    }
    
    @Override public void die() {
        super.die();
        dropFood();
    }
    
    private void pickupFood() {
        // subtract 1 from ColonyNode's food units
        this.getCurrentLocation().setFoodUnits(
            this.getCurrentLocation().getFoodUnits()-1);
        this.carryingFood = true;
        // enter nest mode if food picked up (turn ends)
        this.movementMode = "nest";
        // check to see if we're repeating moves
        // if (!previousNodeHistoryStack.isEmpty()) {
            // check to see if both stacks are equal lengths
            // if they are equal lengths, compare item by item
            // if a match, increment numberOfRepeatedLoops
            // will have to decide how many repeats we will tolerate
        // }
        // store nodeHistoryStack for comparison to avoid endless loops
        // this.previousNodeHistoryStack = nodeHistoryStack;
        
    }
    
    private void moveTo(ColonyNode newLocation) {
        // remove ant from old location
        this.getCurrentLocation().removeAnt(this);
        // set ant's location
        this.setCurrentLocation(newLocation);
        // let new node know scout has moved there
        newLocation.addAnt(this);
        // if node is closed, scout will open it
        newLocation.setOpen(true);     
    }
    
    private ColonyNode huffPheromone() {
        ColonyNode chosenNode;
        // check 8 surrounding nodes, move to one with most pheromone
        LinkedList nodeChoices = viewSurroundingNodes();
        // go through stack of nodes, only viewing open nodes, and find node with max pheromone
        chosenNode = findMaxPheromone(nodeChoices);
        
        return chosenNode;
    }
    
    private ColonyNode findMaxPheromone(LinkedList nodeChoices) {
        ColonyNode maxPheromoneNode;
        ColonyNode currentPheromoneNode;
        int maxPheromoneUnits;
        int currentPheromoneUnits;
        ListIterator nodeIterator = nodeChoices.listIterator(0);
        // setting up initial max to be first item in list
        maxPheromoneNode = (ColonyNode) nodeIterator.getCurrent();
        maxPheromoneUnits = maxPheromoneNode.getPheromoneUnits();
        nodeIterator.next();
        
        // go through remaining items for comparison
        while (nodeIterator.hasNext()) {
            
            currentPheromoneNode = (ColonyNode) nodeIterator.getCurrent();
            currentPheromoneUnits = currentPheromoneNode.getPheromoneUnits();
            // for every other node, compare to maxPheromone  
            // if greater, replace value, assign as chosenNode
            if (currentPheromoneUnits > maxPheromoneUnits) {
                maxPheromoneUnits = currentPheromoneUnits;
                maxPheromoneNode = currentPheromoneNode;
            }
            // remove node from stack
            // nodeChoices.pop();
            nodeIterator.next();
        }
        // this will comb through the list again to see if multiple nodes exist with same pheromone level
        maxPheromoneNode = checkForDuplicates(nodeChoices, maxPheromoneNode);
        
        return maxPheromoneNode;
    }
    
    private ColonyNode checkForDuplicates(LinkedList nodeChoices, ColonyNode maxPheromoneNode) {
        ColonyNode randomMaxPheromoneNode;
        ColonyNode currentPheromoneNode;
        int maxPheromoneUnits;
        int currentPheromoneUnits;
        LinkedList pickRandomList = new LinkedList();
        // check nodeChoices for duplicates of maxPheromoneNode's pheromone unit numbers
        ListIterator nodeChoiceIterator = nodeChoices.listIterator(0);
        while (nodeChoiceIterator.hasNext()) {
            currentPheromoneNode = (ColonyNode) nodeChoiceIterator.getCurrent();
            // build a new linkedList of duplicates if they are found
            if (currentPheromoneNode.getPheromoneUnits() == maxPheromoneNode.getPheromoneUnits()) {
                // check to make sure it's not the same node as the maxPheromoneNode
                if (!currentPheromoneNode.getCoord().equals(maxPheromoneNode.getCoord()))
                    pickRandomList.add(currentPheromoneNode);
            }
            nodeChoiceIterator.next();
        }
        // if no duplicates, return the original maxPheromoneNode
        if (pickRandomList.isEmpty())
            return maxPheromoneNode;
        else {
            // randomly select one of these duplicates and return it (-1 because of 0 index)
            int randomNumber = Simulation.generateRandomNumber(pickRandomList.size());
            randomNumber--;
            randomMaxPheromoneNode = (ColonyNode) pickRandomList.get(randomNumber);
            return randomMaxPheromoneNode;
        }
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
