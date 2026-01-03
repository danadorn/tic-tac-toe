package array;

import java.util.Scanner;

public class TicTacToe {

    private static final char EMPTY = '-';
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';

    private static char[][] board = new char[3][3];
    private static char currentPlayer = PLAYER_X;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Initialize board
        initBoard();

        // Welcome animation
        String welcome = "Welcome to Tic Tac Toe Game version 2.0";
        for (byte ch : welcome.getBytes()) {
            Thread.sleep(50);
            System.out.print((char) ch);
        }
        System.out.println("\n");

        // Game loop
        while (true) {
            printBoard();

            System.out.println("\nPlayer " + currentPlayer + " turn");
            System.out.print("Insert row (1-3): ");
            int r = scanner.nextInt();
            System.out.print("Insert column (1-3): ");
            int c = scanner.nextInt();

            // Validate move
            if (!isValidMove(r, c)) {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            board[r - 1][c - 1] = currentPlayer;

            // Check game status
            if (hasWinner()) {
                printBoard();
                System.out.println("\n Player " + currentPlayer + " wins!");
                break;
            }

            if (isFull()) {
                printBoard();
                System.out.println("\n It's a tie!");
                break;
            }

            // Switch player
            currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
        }

        scanner.close();
    }

    // Initialize board with '-'
    private static void initBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    // Print board
    private static void printBoard() throws Exception {
        System.out.println("Current Board:");
        for (char[] row : board) {
            for (char col : row) {
                Thread.sleep(80);
                System.out.print(col + " | ");
            }
            System.out.println();
        }
    }

    // Validate move
    private static boolean isValidMove(int r, int c) {
        if (r < 1 || r > 3 || c < 1 || c > 3) {
            return false;
        }
        return board[r - 1][c - 1] == EMPTY;
    }

    // Check winner
    private static boolean hasWinner() {
        // Rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != EMPTY &&
                    board[i][0] == board[i][1] &&
                    board[i][1] == board[i][2]) {
                return true;
            }
        }

        // Columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != EMPTY &&
                    board[0][i] == board[1][i] &&
                    board[1][i] == board[2][i]) {
                return true;
            }
        }

        // Diagonals
        if (board[0][0] != EMPTY &&
                board[0][0] == board[1][1] &&
                board[1][1] == board[2][2]) {
            return true;
        }

        if (board[0][2] != EMPTY &&
                board[0][2] == board[1][1] &&
                board[1][1] == board[2][0]) {
            return true;
        }

        return false;
    }

    // Check tie
    private static boolean isFull() {
        for (char[] row : board) {
            for (char col : row) {
                if (col == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
