package com.example.fxfinal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class EnemyFortress {
    public static void main(String[] args) {
        // create graph
       
        
        path graph = new path(10);
        graph.addEdge(1, 2, 10);
        graph.addEdge(1, 3, 18);
        graph.addEdge(1, 6, 20);
        graph.addEdge(1, 10, 16);
        graph.addEdge(2, 4, 10);
        graph.addEdge(3, 4, 12);
        graph.addEdge(4, 5, 12);
        graph.addEdge(5, 6, 17);
        graph.addEdge(5, 7, 10);
        graph.addEdge(6, 7, 23);
        graph.addEdge(6, 8, 35);
        graph.addEdge(7, 8, 19);
        graph.addEdge(7, 9, 17);
        graph.addEdge(8, 9, 7);
        graph.addEdge(8, 10, 12);
        graph.addEdge(9, 10, 18);
        graph.addDirectedEdge(3, 7, 28); // Directed edge

        Scanner sc = new Scanner(System.in);
        // create and initialize
        int source = 1;
        System.out.print("Enter the enemy base camp: ");
        int destination = sc.nextInt();

        
        
        ArrayList<String> General = new ArrayList<>();
        General.add("Cavalry");
        General.add("Archer");
        General.add("Infantry");
        for (String general : General) {
            


            //   to Find shortest path from source to destination
            List<List<Integer>> allPaths = graph.findPaths(source, destination);

           
            
            //   to Find shortest distance
            int distance = Integer.MAX_VALUE;
            List<Integer> shortestPath = null;
            for (List<Integer> path : allPaths) {
                int pathDistance = calculatePathDistance(graph, path);
                if (pathDistance < distance) {
                    distance = pathDistance;
                    shortestPath = path;
                }
            }

            
            
            double time = Integer.MAX_VALUE;
            List<Integer> shortestTime = null;
            for (List<Integer> path : allPaths) {
                double pathTime = calculatePathTime(graph, path, general);
                if (pathTime < time) {
                    time = pathTime;
                    shortestTime = path;
                }
            }

            // Display shortest path and distance
            System.out.println("--------------------------------------");
            System.out.println("General: " + general);
            System.out.println("Shortest path:");
            displayPath(shortestPath);
            System.out.println("Total distance: " + distance + "km");
            System.out.println("Shortest time path:");
            displayPath(shortestTime);
            System.out.printf("Total time: %.2f hour\n", time);
            System.out.println("--------------------------------------");

        }
    }

   
    
    private static int calculatePathDistance(path graph, List<Integer> path) {
        int distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int currentNode = path.get(i);
            int nextNode = path.get(i + 1);
            distance += graph.getDistance(currentNode, nextNode);
        }
        return distance;
    }

    
    
    private static double calculatePathTime(path graph, List<Integer> path, String general) {
        double time = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int currentNode = path.get(i);
            int nextNode = path.get(i + 1);
            // time = distance / speed
            time += (graph.getDistance(currentNode, nextNode) / graph.speed(currentNode, nextNode, general));
        }
        return time;
    }

   
    
    private static void displayPath(List<Integer> path) {
        int size = path.size();
        for (int i = 0; i < size; i++) {
            System.out.print(path.get(i));
            if (i < size - 1) {
                System.out.print("->");
            }
        }
        System.out.println();
    }
}

class path {

    private int V; // the number of vertices
    private List<Integer>[] adjacList; //    adjacency list representation
    private int[][] weights; // Weight matrix

    public path(int V) {
        this.V = V;
        adjacList = new ArrayList[V + 1];
        weights = new int[V + 1][V + 1];
        for (int i = 1; i <= V; i++) {
            adjacList[i] = new ArrayList<>();
        }
    }

    
    public void addDirectedEdge(int u, int v, int distance) {
        adjacList[u].add(v);
        weights[u][v] = distance;
    }

    
    public void addEdge(int u, int v, int distance) {
        addDirectedEdge(u, v, distance);
        addDirectedEdge(v, u, distance);
    }

   
    public int getDistance(int u, int v) {
        return weights[u][v];
    }

    
    
    public double speed(int u, int v, String g) {
        if (g.equalsIgnoreCase("Cavalry")) {
            return 2 * geo(u, v, g);
        } else if (g.equalsIgnoreCase("Archer")) {
            return 1 * geo(u, v, g);
        } else {
            return 1 * geo(u, v, g);
        }
    }

    
    
    public double geo(int u, int v, String g) {
        List<Integer>[] flatRoad, forest, swamp, plankRoad;
        flatRoad = new ArrayList[11];
        forest = new ArrayList[11];
        swamp = new ArrayList[11];
        plankRoad = new ArrayList[11];
        for (int i = 1; i <= 10; i++) {
            flatRoad[i] = new ArrayList<>();
        }
        flatRoad[1].add(6);
        flatRoad[6].add(1);
        flatRoad[1].add(3);
        flatRoad[3].add(1);
        flatRoad[1].add(3);
        flatRoad[5].add(6);
        flatRoad[6].add(5);
        flatRoad[7].add(8);
        flatRoad[8].add(7);
        flatRoad[7].add(9);
        flatRoad[9].add(7);
        flatRoad[1].add(10);
        flatRoad[10].add(1);
        flatRoad[9].add(10);
        flatRoad[10].add(9);

       
        for (int i = 1; i <= 10; i++) {
            forest[i] = new ArrayList<>();
        }
        forest[1].add(2);
        forest[2].add(1);
        forest[5].add(7);
        forest[7].add(5);
        forest[6].add(7);
        forest[7].add(6);
        forest[8].add(10);
        forest[10].add(8);

        
        for (int i = 1; i <= 10; i++) {
            swamp[i] = new ArrayList<>();
        }
        swamp[2].add(4);
        swamp[4].add(2);
        swamp[3].add(4);
        swamp[4].add(3);
        swamp[4].add(5);
        swamp[5].add(4);
        swamp[8].add(9);
        swamp[9].add(8);

        
        for (int i = 1; i <= 10; i++) {
            plankRoad[i] = new ArrayList<>();
        }
        plankRoad[3].add(7);
        plankRoad[6].add(8);
        plankRoad[8].add(6);

        
        if (flatRoad[u].contains(v)) {
            if (g.equalsIgnoreCase("Cavalry")) {
                return 3;
            } else if (g.equalsIgnoreCase("Archer")) {
                return 2;
            } else {
                return 2;
            }
        } else if (forest[u].contains(v)) {
            if (g.equalsIgnoreCase("Cavalry")) {
                return 0.8;
            } else if (g.equalsIgnoreCase("Archer")) {
                return 1;
            } else {
                return 2.5;
            }
        } else if (swamp[u].contains(v)) {
            if (g.equalsIgnoreCase("Cavalry")) {
                return 0.3;
            } else if (g.equalsIgnoreCase("Archer")) {
                return 2.5;
            } else {
                return 1;
            }
        } else {
            return 0.5;
        }

    }

    
    
    public List<List<Integer>> findPaths(int source, int destination) {
        List<List<Integer>> allPaths = new ArrayList<>();

        // Queue for BFS traversal
        Queue<List<Integer>> queue = new LinkedList<>();
        List<Integer> path = new ArrayList<>();
        path.add(source);
        queue.add(path);

        while (!queue.isEmpty()) {
            List<Integer> currentPath = queue.poll();
            int lastNode = currentPath.get(currentPath.size() - 1);

            // If destination node is reached, add the path to the result
            if (lastNode == destination) {
                allPaths.add(currentPath);
            }

            // Enqueue all adjacent nodes
            for (int adjacentNode : adjacList[lastNode]) {
                // Avoid revisiting nodes already in the current path
                if (!currentPath.contains(adjacentNode)) {
                    List<Integer> newPath = new ArrayList<>(currentPath);
                    newPath.add(adjacentNode);
                    queue.add(newPath);
                }
            }
        }

        return allPaths;
    }
    
}
