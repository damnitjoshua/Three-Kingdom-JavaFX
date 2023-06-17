package com.example.fxfinal;

import java.util.ArrayList;
import java.util.List;
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

    public List<int[]> findOptimalCoordinates(int[][] matrix) {
        List<int[]> optimalCoordinates = new ArrayList<>();
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 1 && !visited[i][j]) {
                    optimalCoordinates.add(findOptimalCoordinate(matrix, visited, i, j));
                }
            }
        }

        return optimalCoordinates;
    }

    private int[] findOptimalCoordinate(int[][] matrix, boolean[][] visited, int row, int col) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] coordinate = {row, col};
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 1 && visited[i][j]) {
                    int distance = Math.abs(row - i) + Math.abs(col - j);
                    if (distance < minDistance) {
                        minDistance = distance;
                        coordinate[0] = i;
                        coordinate[1] = j;
                    }
                }
            }
        }

        return coordinate;
    }
}
