package com.example.fxfinal;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatrixVisualizationAdvance extends Application {
    private static final int CELL_SIZE = 50; // Adjust this value to change the cell size

    private int[][] matrix;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Prompt the user for matrix dimensions and values
        matrix = getUserMatrix();

        GridPane gridPane = createGridPane();

        // Add rectangles to grid pane based on matrix values
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                Rectangle rectangle = createRectangle(matrix[row][col]);
                gridPane.add(rectangle, col, row);
            }
        }

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Matrix Visualization");
        primaryStage.show();

        // Example usage of ClusterAnalyzer class
        ClusterAnalyzerTwo clusterAnalyzer = new ClusterAnalyzerTwo();
        int numClusters = clusterAnalyzer.countClusters(matrix);
        System.out.println("Number of clusters: " + numClusters);

        List<int[]> optimalCoordinates = ClusterAnalyzerTwo.findOptimalCoordinates(matrix);
        System.out.println("Optimal coordinates for each cluster:");
        for (int[] coordinate : optimalCoordinates) {
            System.out.println("(" + coordinate[0] + ", " + coordinate[1] + ")");
            burnCluster(gridPane, coordinate[0], coordinate[1], matrix);
        }
    }

    private void burnCluster(GridPane gridPane, int row, int col, int[][] matrix) {
        Rectangle rectangle = (Rectangle) gridPane.getChildren().get(row * matrix[0].length + col);
        final Duration burnDuration = Duration.seconds(2);

        Color initialColor = (Color) rectangle.getFill();
        Color targetColor = Color.YELLOW;

        KeyValue keyValue = new KeyValue(rectangle.fillProperty(), targetColor);
        KeyFrame keyFrame = new KeyFrame(burnDuration, keyValue);

        Timeline timeline = new Timeline(keyFrame);
        timeline.setOnFinished(event -> {
            // Burn adjacent cells recursively
            burnAdjacentCells(gridPane, row, col, matrix);
        });

        rectangle.setFill(initialColor); // Reset to initial color before starting the animation
        timeline.play();
    }

    private void burnAdjacentCells(GridPane gridPane, int row, int col, int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Check all 8 directions for adjacent cells
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, /*Current Cell*/ {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && matrix[newRow][newCol] == 1) {
                Rectangle adjacentRectangle = (Rectangle) gridPane.getChildren().get(newRow * cols + newCol);
                if (adjacentRectangle.getFill() != Color.YELLOW) {
                    burnCluster(gridPane, newRow, newCol, matrix);
                }
            }
        }
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        return gridPane;
    }

    private Rectangle createRectangle(int value) {
        Rectangle rectangle = new Rectangle(CELL_SIZE, CELL_SIZE);
        rectangle.setStroke(Color.BLACK);
        if (value == 1) {
            rectangle.setFill(Color.BROWN);
        } else {
            rectangle.setFill(Color.WHITE);
        }
        return rectangle;
    }

    private int[][] getUserMatrix() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of rows: ");
        int rows = scanner.nextInt();

        System.out.print("Enter the number of columns: ");
        int columns = scanner.nextInt();

        int[][] matrix = new int[rows][columns];

        System.out.println("Enter the matrix values (0 or 1):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        return matrix;
    }
}

class ClusterAnalyzerTwo {
    public ClusterAnalyzerTwo() {}

    public int countClusters(int[][] matrix) {
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
