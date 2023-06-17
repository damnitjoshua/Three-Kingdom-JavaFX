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
        printHierarchy(this);
    }

    private void printHierarchy(TreeNode node) {
        printGeneral(node.getGeneral());

        for (TreeNode child : node.getChildren()) {
            printHierarchy(child);
        }
    }

    private void printGeneral(Persona general) {
        System.out.println(general.toString());
    }    
    
    @Override
    public String toString() {
        return general.toString();
    }
    
}