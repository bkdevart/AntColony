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
// need to better understand implementing an interface
// responsible for setting up the initial state of the simulation, 
// starting the simulation, and controlling the turn-by-turn execution.
public class Simulation implements SimulationEventListener {
    // this will drive starting the initialization of the simulation
    // starts simulation
    // controls turn by turn execution
    // each day is 10 turns
    // AntSimGUI simulationGUI;
    String setupMode;
    int simulationTotalTurns;
    int currentDay;
    int currentYear;
    Colony antColony;
    AntSimGUI myAntSimGUI;


    Simulation(AntSimGUI myAntSimGUI) {
        
        
        
        // must have reference to one and only Colony instance
        antColony = new Colony();
        myAntSimGUI.initGUI(antColony.getColonyView());
        // will need an instance of itself to AntSimGUI by calling addSimulationEventListener
        myAntSimGUI.addSimulationEventListener(this);
        // this will be day 1
        currentDay = 1;
        
    }
    
    // implements SimulationEventListener interface
    public void simulationEventOccurred(SimulationEvent simEvent){
        // put in button-handling code here
        switch (simEvent.getEventType()) {
            // set up the simulation for normal operation
            case SimulationEvent.NORMAL_SETUP_EVENT:
                System.out.println("Normal setup clicked");
                // create new colony
                normalSetup();
                break;
            // set up simulation for testing the queen ant
            case SimulationEvent.QUEEN_TEST_EVENT:
                System.out.println("Queen test clicked");
                queenTest();
                break;
            // set up simulation for testing the scout ant
            case SimulationEvent.SCOUT_TEST_EVENT:
                System.out.println("Scout test clicked");
                scoutTest();
                break;
            // set up simulation for testing the forager ant
            case SimulationEvent.FORAGER_TEST_EVENT:
                System.out.println("Forager test clicked");
                foragerTest();
                break;
            // set up simulation for testing the soldier ant
            case SimulationEvent.SOLDIER_TEST_EVENT:
                System.out.println("Soldier test clicked");
                soldierTest();
                break;
            // run the simulation continuously
            case SimulationEvent.RUN_EVENT:
                continuousStep();
                break;
            // run the next turn of the simulation
            case SimulationEvent.STEP_EVENT:
                stepEvent();
                break;
            // invalid event occurred - probably will never happen
            default:
                break;
        }
    }
    
    public void normalSetup() {
        // flips mode to normal (all ants move) and shows the initial state
        this.setupMode = "normal";
        antColony.updateColonyView(simulationTotalTurns);
    }
    
    public void continuousStep() {
        while (this.antColony.exists) {
            processTurns(this.antColony);
        }
    }
    
    public void queenTest() {
        this.setupMode = "queen";
    }
    
    public void scoutTest() {
        this.setupMode = "scout";
    }
    
    public void soldierTest() {
        this.setupMode = "soldier";
    }
    
    public void foragerTest() {
        this.setupMode = "forager";
    }
    
    public void stepEvent() {
        // will need to check every node's ants and give them a turn (if they have one)
        processTurns(this.antColony);
    }
    
    public void processTurns(Colony antColony) {
        
        // iterate through array of nodes, stop on ones that have ants
        // add these nodes to a linked list (will be used again later to initalize ant turns)
        LinkedList nodesWithAnts = findNodesWithAnts();
        // when done, add all nodes in linked list to a queue of nodes
        LinkedQueue nodesWithAntsQueue = loadNodeQueue(nodesWithAnts);
        // when done, begin stepping through the queue of nodes
        // create another queue for ants in node
        LinkedQueue antTurnQueue = loadAntQueue(nodesWithAntsQueue);     
        // begin stepping through this queue
        // execute a turn for each ant, and set it's hasTurn false after doing so
        Ant currentAnt;
        while (!antTurnQueue.isEmpty()) {
            currentAnt = (Ant) antTurnQueue.getFront();
            // tweak this to implement individual ant test buttons
            if (currentAnt instanceof Queen) {
                if (this.setupMode.equals("normal") || this.setupMode.equals("queen") && currentAnt.hasTurn == true)
                    ((Queen) currentAnt).takeTurn();
            } else if (currentAnt instanceof Scout) {
                if (this.setupMode.equals("normal") || this.setupMode.equals("scout") && currentAnt.hasTurn == true)
                    ((Scout) currentAnt).takeTurn();
            } else if (currentAnt instanceof Soldier) {
                if (this.setupMode.equals("normal") || this.setupMode.equals("soldier") && currentAnt.hasTurn == true)
                    ((Soldier) currentAnt).takeTurn();
            } else if (currentAnt instanceof Forager) {
                if (this.setupMode.equals("normal") || this.setupMode.equals("forager") && currentAnt.hasTurn == true)
                    ((Forager) currentAnt).takeTurn();
            } else if (currentAnt instanceof Bala) {
                // no test mode
                ((Bala) currentAnt).takeTurn();
            }
            antTurnQueue.dequeue();
        }
        
        // possibly spawn a bala at 0,0 - 3% chance
        // if time randomize this
        int randomBalaChance = generateRandomNumber(100);
        if (randomBalaChance < 4) {
            // bala location in two places, change if time available
            this.antColony.antColonyArray[0][0].addAnt(generateBala());
        }
        // check on queen
        if (!antColony.antColonyArray[14][14].hasQueen) {
            System.out.println("Simulation ended.  Queen has died.");
            System.out.println("It took " + simulationTotalTurns + " turns, or " 
                    + currentDay + " days, or " + currentYear + " years.");
            
            // antColony.antColonyArray[14][14].getColonyNodeView().hideQueenIcon();
        }
        
        // update time metrics
        simulationTotalTurns++;
        currentDay = simulationTotalTurns / 10;
        currentYear = currentDay / 365;
        
        // update display
        antColony.updateColonyView(simulationTotalTurns);
    }
    
    private Ant generateBala() {
        // bala location in two places, change if time available
        Bala newBalaAnt = new Bala(this.antColony.antColonyArray[0][0]);
        
        return newBalaAnt;
    }
    
    public LinkedQueue loadAntQueue(LinkedQueue nodesWithAntsQueue) {
        LinkedQueue antQueue = new LinkedQueue();
        ColonyNode currentNode;
        LinkedList antList;
        Iterator antListIterator;
        while (!nodesWithAntsQueue.isEmpty()) {
            // pull up node
            currentNode = (ColonyNode) nodesWithAntsQueue.get();
            // grab node's list of ants
            antList = currentNode.getAnts();
            // iterate through list and add queue each ant
            antListIterator = antList.iterator();
            while (antListIterator.hasNext()) {
                antQueue.enqueue(antListIterator.getCurrent());
                antListIterator.next();
            }
            // remove node from queue
            nodesWithAntsQueue.dequeue();
        }
        return antQueue;
    }
    
    public LinkedQueue loadNodeQueue(LinkedList nodesWithAnts) {
        LinkedQueue nodeQueue = new LinkedQueue();
        // iterate through nodesWithAnts and add to nodeQueue
        Iterator nodeIterator = nodesWithAnts.iterator();
        while (nodeIterator.hasNext()) {
            nodeQueue.enqueue(nodeIterator.getCurrent());
            nodeIterator.next();
        }
        
        return nodeQueue;
    }
    
    public LinkedList findNodesWithAnts() {
        LinkedList antNodes = new LinkedList();
        // iterate through all nodes
        int xCoord = 0;
        int yCoord = 0;
        for (ColonyNode[] y : antColony.antColonyArray) {
            for (ColonyNode x : y) {
                // check to see if node has ants, add to linked list if so
                if (antColony.antColonyArray[xCoord][yCoord].hasAnts())
                    antNodes.add(antColony.antColonyArray[xCoord][yCoord]);
                xCoord++;
            }
            yCoord++;
            xCoord = 0;
        }
        return antNodes;
    }
    
    // random number generator - find number between 1 and randomRange
    public static int generateRandomNumber(int randomRange) {
        int randomNumber = (int)(Math.random() * randomRange + 1);       
        return randomNumber;
    }
    
}
