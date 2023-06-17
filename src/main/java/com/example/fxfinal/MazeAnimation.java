package com.example.fxfinal;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MazeAnimation extends Application {
    private static final int WALL = 1;
    private static final int START = 2;
    private static final int EXIT = 3;
    private static final int PATH = -1;

    private Rectangle[][] mazeRectangles;
    private List<Integer[]> pathCoordinates;
    private int currentPathIndex;
    private Timeline animation;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        int[][] maze = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {2, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        Pane pane = new Pane();
        mazeRectangles = createMazeRectangles(maze);
        List<Rectangle> flattenedRectangles = flattenMazeRectangles(mazeRectangles);
        pane.getChildren().addAll(flattenedRectangles);

        Scene scene = new Scene(pane, maze[0].length * 40, maze.length * 40);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Engaging Cao Cao at Hua Rong Road");
        primaryStage.show();

        // Find the path
        boolean[][] currentPath = new boolean[maze.length][maze[0].length];
        findPath(maze, 1, 0, currentPath);

        // Animate the movement along the path
        Button animateButton = new Button("Start");
        animateButton.setOnAction(event -> {
            resetMaze();
            animation.setRate(30); // Increase the rate for faster animation
            animation.play();
        });
        pane.getChildren().add(animateButton);

        // Reset button
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> {
            resetMaze();
        });
        resetButton.setLayoutX(animateButton.getLayoutX() + animateButton.getWidth() + 45);
        resetButton.setLayoutY(animateButton.getLayoutY());
        pane.getChildren().add(resetButton);
    }

    private Rectangle[][] createMazeRectangles(int[][] maze) {
        int numRows = maze.length;
        int numCols = maze[0].length;

        Rectangle[][] rectangles = new Rectangle[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Rectangle rectangle = new Rectangle(j * 40, i * 40, 40, 40);
                rectangle.setStroke(Color.BLACK);

                if (maze[i][j] == WALL) {
                    rectangle.setFill(Color.BLACK);
                } else if (maze[i][j] == START) {
                    rectangle.setFill(Color.BLUE);
                } else if (maze[i][j] == EXIT) {
                    rectangle.setFill(Color.RED);
                } else if (maze[i][j] == PATH) {
                    rectangle.setFill(Color.LIGHTBLUE);
                } else {
                    rectangle.setFill(Color.WHITE);
                }

                rectangles[i][j] = rectangle;
            }
        }

        return rectangles;
    }

    private List<Rectangle> flattenMazeRectangles(Rectangle[][] mazeRectangles) {
        List<Rectangle> flattenedRectangles = new ArrayList<>();
        for (Rectangle[] row : mazeRectangles) {
            flattenedRectangles.addAll(Arrays.asList(row));
        }
        return flattenedRectangles;
    }

    private void findPath(int[][] maze, int row, int col, boolean[][] currentPath) {
        if (row < 0 || col < 0 || row >= maze.length || col >= maze[0].length || maze[row][col] == WALL ||
                currentPath[row][col]) {
            return;
        }

        if (maze[row][col] == EXIT) {
            pathCoordinates = new ArrayList<>();
            for (int i = 0; i < currentPath.length; i++) {
                for (int j = 0; j < currentPath[0].length; j++) {
                    if (currentPath[i][j]) {
                        pathCoordinates.add(new Integer[]{i, j});
                    }
                }
            }
            createAnimation();
            return;
        }

        currentPath[row][col] = true;

        findPath(maze, row - 1, col, currentPath); // Up
        findPath(maze, row, col + 1, currentPath); // Right
        findPath(maze, row + 1, col, currentPath); // Down
        findPath(maze, row, col - 1, currentPath); // Left

        currentPath[row][col] = false;
    }

    private void resetMaze() {
        for (Rectangle[] row : mazeRectangles) {
            for (Rectangle rectangle : row) {
                if (rectangle.getFill() == Color.LIGHTBLUE || rectangle.getFill() == Color.GREEN) {
                    rectangle.setFill(Color.WHITE);
                }
            }
        }
        currentPathIndex = 0;
    }

    private void createAnimation() {
        animation = new Timeline();
        for (int i = 0; i < pathCoordinates.size(); i++) {
            Integer[] coordinates = pathCoordinates.get(i);
            int row = coordinates[0];
            int col = coordinates[1];

            Rectangle rectangle = mazeRectangles[row][col];
            KeyValue kv = new KeyValue(rectangle.fillProperty(), Color.GREEN);
            KeyFrame kf = new KeyFrame(Duration.seconds(i), kv);
            animation.getKeyFrames().add(kf);
        }
    }
}
