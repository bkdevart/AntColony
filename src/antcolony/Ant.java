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
public abstract class Ant {
    public int lifeSpan;
    public int antID;
    public boolean alive;
    // this is used for interrupting turn after death
    public boolean hasTurn;
    private int turnsTaken;
    public boolean isFriendly;
    private ColonyNode currentLocation;
    public double age;
    public String antType; //  {SCOUT, SOLDIER, FORAGER, BALA, QUEEN};
    
    protected Ant() {
        // will only be used for bala, all have ID of -1
        this.antID = -1;
    }
    
    protected Ant(int antID) {
        // IDs will be generated sequentially, with queen starting at 0
        // would this be a master list in the Colony or Simulation class?
        // for now, will specify ID here
        this.antID = antID;
        // this.currentLocation = currentLocation;
        this.alive = true;
        this.age = 0;
        this.hasTurn = true;
        // queen will override this
        this.lifeSpan = 1;
        this.turnsTaken = 0;
        
    }
    
    public static void moveToRevealedTerrain() {
        // would need to check eight (?) surrounding nodes
        // throw randomizer on check so behavior isn't static?
        // when an empty one is found, move
    }
    
    public void detectPheremone() {
        
    }
    
    public void growOlder() {
        
    }
    
    public void updateLocation(Location NewLocation) {
        
    }
    
    public void die() {
        this.alive = false;
        this.getCurrentLocation().removeAnt(this);
    }
    
    public void setID(int newID) {
        this.antID = newID;
    }
    
    public int getID() {
        return this.antID;
    }
    
    public void setAntType(String antType) {
        this.antType = antType;
    }
    
    public String getAntType() {
        return this.antType;
    }
    
    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }
    
    public int getLifeSpan() {
        return this.lifeSpan;
    }
    
    public void setAlive(boolean isAlive) {
        this.alive = isAlive;
    }
    
    public boolean getAlive() {
        return this.alive;
    }
    
    public void setIsFriendly(boolean isFriendly) {
        this.isFriendly = true;
    }
    
    public boolean getIsFriendly() {
        return this.isFriendly;
    }
    
    public void setCurrentLocation(ColonyNode currentLocation) {
        this.currentLocation = currentLocation;
    }
    
    public ColonyNode getCurrentLocation() {
        return this.currentLocation;
    }
    
    public void setAge(double age) {
        this.age = age;
    }
    
    public double getAge() {
        return this.age;
    }
    
    public void addTurn() {
        this.turnsTaken++;
        // all ants besides queen will live 1 year (10x365 = 3,650 turns)
        // queen 20 years (10x365x20 = 73,000 turns)
        if (this instanceof Queen) {
            if (this.turnsTaken % 73000 == 0)
                this.die();
        } else {
            if (this.turnsTaken % 3650 == 0) {
                this.die();
            }
        }
            
    }
    
    public int getTurn() {
        return this.turnsTaken;
    }
    
    public void endTurn() {
        this.hasTurn = false;
    }
    
}
