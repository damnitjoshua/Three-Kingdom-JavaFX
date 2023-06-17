package com.example.fxfinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FoodHarvesting {
    // 10 by 10
    private int[][] adjacencyMatrix = {
        //   1, 2, 3, 4, 5, 6, 7, 8, 9, 10
            {0, 1, 1, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 1, 0, 0, 1, 0, 0, 0},
            {0, 1, 1, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 1, 1, 0, 0, 0},
            {1, 0, 0, 0, 1, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 1, 1, 0, 1, 1, 0},
            {0, 0, 0, 0, 0, 1, 1, 0, 1, 1},
            {0, 0, 0, 0, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 1, 0}
    };

    private int numberOfNodes = adjacencyMatrix.length;
    private boolean[] visited = new boolean[numberOfNodes];
    private Stack<Integer> path = new Stack<>();
    private List<Integer> bestPath = new ArrayList<>();
    private int noFoodNode;

    public FoodHarvesting(int noFoodNode) {
        this.noFoodNode = noFoodNode;
    }

    private void findBestPath(int currentNode, int startingNode) {
        visited[currentNode - 1] = true;
        path.push(currentNode);

        // Check if all nodes have been visited (except starting node)
        boolean allVisited = true;
        for (int i = 0; i < numberOfNodes; i++) {
            if (i != startingNode - 1 && !visited[i]) {
                allVisited = false;
                break;
            }
        }

        // If all nodes have been visited, update the best path
        if (allVisited) {
            bestPath = new ArrayList<>(path);
        }

        // Explore neighboring nodes
        for (int i = 0; i < numberOfNodes; i++) {
            if (adjacencyMatrix[currentNode - 1][i] == 1 && !visited[i]) {
                if (noFoodNode != (i + 1)) {
                    findBestPath(i + 1, startingNode);
                } else {
                    findBestPath(i + 1, startingNode);
                }
            }
        }

        // Backtrack
        visited[currentNode - 1] = false;
        path.pop();
    }

    public void FindPath() {
        // Find paths
        findBestPath(1, noFoodNode);

        // Print path
        System.out.print("Paths: ");
        for (Integer path : bestPath) {
                System.out.print(path + " -> ");
        }
        System.out.print("1");
    }
}
