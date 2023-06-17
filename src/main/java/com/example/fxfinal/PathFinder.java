package com.example.fxfinal;

public class PathFinder {
    private int wall, start, exit, path;

    public PathFinder() {
        wall = 1;
        start = 1;
        exit = 3;
        path = -2;
    }

    public void findPath(int[][] maze, boolean[][] currentPath) {
        int startRow = -1;
        int startCol = -1;

        // Find the starting point
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if (maze[row][col] == start) {
                    startRow = row;
                    startCol = col;
                    break;
                }
            }
        }

        // Call recursive function to find the path
        findPathRecursive(maze, startRow, startCol, false, currentPath);
    }

    public boolean findPathRecursive(int[][] maze, int row, int col, boolean foundPath, boolean[][] currentPath) {
        // Base cases
        if (row < 0 || row >= maze.length || col < 0 || col >= maze[row].length || maze[row][col] == wall) {
            return false;
        }

        if (maze[row][col] == exit) {
            maze[row][col] = path;
            return true;
        }

        if (maze[row][col] != 0) {
            return false;
        }

        // Mark the current cell as part of the path
        currentPath[row][col] = true;

        // Recursive calls in all four directions
        if (!foundPath &&
                (findPathRecursive(maze, row - 1, col, foundPath, currentPath) ||
                        findPathRecursive(maze, row + 1, col, foundPath, currentPath) ||
                        findPathRecursive(maze, row, col - 1, foundPath, currentPath) ||
                        findPathRecursive(maze, row, col + 1, foundPath, currentPath))) {
            foundPath = true;
        }

        // Unmark the current cell if no path is found
        if (!foundPath) {
            maze[row][col] = 0;
            currentPath[row][col] = false;
        }
        return foundPath;
    }

    public void displayMaze(int[][] maze, boolean[][] currentPath) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == wall) {
                    System.out.print("# ");
                } else if (currentPath[i][j]) {
                    System.out.print("X ");
                } else {
                    System.out.print(maze[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

}
