package com.example.fxfinal;

public class Persona {
    private String name;
    private String armyType;
    private int strength;
    private int leadership;
    private int intelligence;
    private int politic;
    private int hitPoint;
    private Persona next;
    private Persona prev;

    public Persona(String name, String armyType, int strength, int leadership, int intelligence, int politic, int hitPoint) {
        this.name = name;
        this.armyType = armyType;
        this.strength = strength;
        this.leadership = leadership;
        this.intelligence = intelligence;
        this.politic = politic;
        this.hitPoint = hitPoint;
        this.prev = null;
        this.next = null;
    }

    public String getName() {
        return name;
    }

    public String getArmyType() {
        return armyType;
    }

    public int getStrength() {
        return strength;
    }

    public int getLeadership() {
        return leadership;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getPolitic() {
        return politic;
    }

    public int getHitPoint() {
        return hitPoint;
    }
    
    public Persona getPrev() {
        return prev;
    }

    public void setPrev(Persona prev) {
        this.prev = prev;
    }

    public Persona getNext() {
        return next;
    }

    public void setNext(Persona next) {
        this.next = next;
    }
    
    @Override
    public String toString() {
        return "Name: " + this.name + "\n" +
                "Army Type: " + armyType + "\n" +
                "Strength: " + this.strength + "\n" +
                "Leadership: " + leadership + "\n" +
                "Intelligence: " + intelligence + "\n" +
                "Politic: " + politic + "\n" +
                "Hit Points: " + hitPoint + "\n";
    }
    
    
}
