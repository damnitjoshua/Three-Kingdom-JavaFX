package com.example.fxfinal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BattleshipClusters {
    public BattleshipClusters() {}
    public int countBattleshipClusters(int[][] matrix) {
        int numClusters = 0;
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 1 && !visited[i][j]) {
                    numClusters++;
                    dfs(matrix, visited, i, j);
                }
            }
        }

        return numClusters;
    }

    private void dfs(int[][] matrix, boolean[][] visited, int row, int col) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        if (row < 0 || row >= rows || col < 0 || col >= cols || matrix[row][col] == 0 || visited[row][col]) {
            return;
        }

        visited[row][col] = true;

        // Recursively visit the adjacent cells (up, down, left, right, and diagonals)
        dfs(matrix, visited, row - 1, col); // up
        dfs(matrix, visited, row + 1, col); // down
        dfs(matrix, visited, row, col - 1); // left
        dfs(matrix, visited, row, col + 1); // right
        dfs(matrix, visited, row - 1, col - 1); // diagonal up-left
        dfs(matrix, visited, row - 1, col + 1); // diagonal up-right
        dfs(matrix, visited, row + 1, col - 1); // diagonal down-left
        dfs(matrix, visited, row + 1, col + 1); // diagonal down-right
    }

    public static List<int[]> findOptimalCoordinates(int[][] matrix) {
        List<int[]> optimalCoordinates = new ArrayList<>();
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 1 && !visited[i][j]) {
                    int[] optimalCoordinate = findOptimalCoordinate(matrix, visited, i, j);
                    optimalCoordinates.add(optimalCoordinate);
                }
            }
        }

        return optimalCoordinates;
    }

    private static int[] findOptimalCoordinate(int[][] matrix, boolean[][] visited, int row, int col) {
        List<int[]> clusterCells = new ArrayList<>();
        dfs(matrix, visited, row, col, clusterCells);
        int[] centroid = findCentroid(clusterCells);

        int[] optimalCoordinate = null;
        double minDistance = Double.MAX_VALUE;

        for (int[] cell : clusterCells) {
            double distance = Math.sqrt(Math.pow(cell[0] - centroid[0], 2) + Math.pow(cell[1] - centroid[1], 2));
            if (distance < minDistance) {
                minDistance = distance;
                optimalCoordinate = cell;
            }
        }

        return optimalCoordinate;
    }


    private static void dfs(int[][] matrix, boolean[][] visited, int row, int col, List<int[]> clusterCells) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        if (row < 0 || row >= rows || col < 0 || col >= cols || matrix[row][col] == 0 || visited[row][col]) {
            return;
        }

        visited[row][col] = true;
        clusterCells.add(new int[]{row, col});

        // Recursively visit the adjacent cells (up, down, left, right)
        dfs(matrix, visited, row - 1, col, clusterCells); // up
        dfs(matrix, visited, row + 1, col, clusterCells); // down
        dfs(matrix, visited, row, col - 1, clusterCells); // left
        dfs(matrix, visited, row, col + 1, clusterCells); // right
        dfs(matrix, visited, row - 1, col - 1, clusterCells); // diagonal up-left
        dfs(matrix, visited, row - 1, col + 1, clusterCells); // diagonal up-right
        dfs(matrix, visited, row + 1, col - 1, clusterCells); // diagonal down-left
        dfs(matrix, visited, row + 1, col + 1, clusterCells); // diagonal down-right
    }

    private static int[] findCentroid(List<int[]> clusterCells) {
        int xSum = 0;
        int ySum = 0;

        for (int[] cell : clusterCells) {
            xSum += cell[0];
            ySum += cell[1];
        }

        int xCentroid = (int) Math.round((double) xSum / clusterCells.size());
        int yCentroid = (int) Math.round((double) ySum / clusterCells.size());

        return new int[]{xCentroid, yCentroid};
    }


}
