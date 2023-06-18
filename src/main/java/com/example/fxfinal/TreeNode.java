package com.example.fxfinal;

import java.util.ArrayList;
import java.util.List;

class TreeNode {
    private Persona general;
    private List<TreeNode> children;

    public TreeNode(Persona general) {
        this.general = general;
        this.children = new ArrayList<>();
    }

    public Persona getGeneral() {
        return general;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }
    
    public void printList() {
        printHierarchy(this, 0);
    }

    private void printHierarchy(TreeNode node, int indentLevel) {
        printGeneral(node.getGeneral(), indentLevel);

        for (TreeNode child : node.getChildren()) {
            printHierarchy(child, indentLevel + 1);
        }
    }

    private void printGeneral(Persona general, int indentLevel) {
        printIndent(indentLevel);
        System.out.println("Name: " + general.getName());
        printIndent(indentLevel);
        System.out.println("Army Type: " + general.getArmyType());
        printIndent(indentLevel);
        System.out.println("Strength: " + general.getStrength());
        printIndent(indentLevel);
        System.out.println("Leadership: " + general.getLeadership());
        printIndent(indentLevel);
        System.out.println("Intelligence: " + general.getIntelligence());
        printIndent(indentLevel);
        System.out.println("Politic: " + general.getPolitic());
        printIndent(indentLevel);
        System.out.println("Hit Points: " + general.getHitPoint());
        System.out.println();
    }

    private void printIndent(int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            System.out.print("\t"); // Use "\t" for indentation
        }
    }     
    
    @Override
    public String toString() {
        return general.toString();
    }
    
}
