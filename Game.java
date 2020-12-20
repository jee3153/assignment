package assignment.org;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Game {
    static Player winner;
    static Player currentPlayer;
    static ArrayList<Integer> chosenPlaces = new ArrayList<>();

    public static String[] decidePlayer() {
        Scanner sc = new Scanner(System.in);
        String[] players = new String[2];
        System.out.println("Welcome to noughts and crosses.");
        System.out.println("How it works: the player who make three marks in a horizontal, vertical, or diagonal row wins the game.");
        System.out.println("Let's choose players.");
        System.out.println("Player 1: type your name");
        String player1 = sc.nextLine();
        System.out.println(player1 + ", your mark is 'O'.");
        System.out.println("Player 2: type your name");
        String player2 = sc.nextLine();
        System.out.println(player2 + ", your mark is 'X'.");
        players[0] = player1;
        players[1] = player2;

        return players;
    }

    public static void printGrid(String[] grid) {
        for (int i = 1; i < grid.length; i++) {
            System.out.print(" ");
            if (i % 3 == 0) {
                System.out.println(grid[i]);
            } else {
                System.out.print(grid[i]);
            }
        }
    }

    public static void makeMark(Player currentPlayer, String[] grid) {
        Scanner sc = new Scanner(System.in);
        System.out.println(currentPlayer.playerName + ", choose the number to place your mark.");

        try {
            int position = sc.nextInt();

            while (chosenPlaces.contains(position)) {
                System.out.println("The place already occupied.");
                System.out.println("Type other position.");
                position = sc.nextInt();

            }

            System.out.println(currentPlayer.playerName + " have chosen position " + position);
            grid[position] = currentPlayer.playerMark;
            chosenPlaces.add(position);

        } catch(Exception e) {
            System.out.println("The place invalid.");
            System.out.println("Try again.");
            makeMark(currentPlayer, grid);
        }
    }

    public static Optional<Player> detectCompleteLine(String[] grid, Player currentPlayer) {
        int n = 3;
        // detect rows
        for (int i = 1; i <= n; i += 3) {
            if(grid[i].equals(grid[i + 1]) && grid[i + 1].equals(grid[i + 2])) {
                winner = currentPlayer;
                System.out.println(winner.playerName + " won!");
                return Optional.ofNullable(winner);
            }
        }

        // detect columns
        for (int i = 1; i <= n; i++) {
            if(grid[i].equals(grid[i + 3]) && grid[i + 3].equals(grid[i + 6])) {
                winner = currentPlayer;
                System.out.println(winner.playerName + " won!");
                return Optional.ofNullable(winner);
            }
        }

        // detect diagonal
        for (int i = 1; i <= n; i += 2) {
            if (i == 1 && grid[i].equals(grid[i+4]) && grid[i+4].equals(grid[i+8])) {
                winner = currentPlayer;
                System.out.println(winner.playerName + " won!");
                return Optional.ofNullable(winner);
            }
            if (i == 3 && grid[i].equals(grid[i+2]) && grid[i+2].equals(grid[i+4])) {
                winner = currentPlayer;
                System.out.println(winner.playerName + " won!");
                return Optional.ofNullable(winner);
            }
        }
        return Optional.ofNullable(winner);
    }

    public static void replay() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you wish to replay? (y/n)");
        String answer = sc.nextLine();


        if (answer.equals("y")) {
            System.out.println("Glad I'll see you again :), Replaying the game.");
            decidePlayer();
        }
        else if (answer.equals("n")) {
            System.out.println("How sad, exit the game. Bye :(");
        } else {
            System.out.println("That's invalid answer :(");
        }
    }

    public static void main(String[] args)
    {
        String[] players = decidePlayer();
        Player player1 = new Player("O", players[0]);
        Player player2 = new Player("X", players[1]);
        String[] grid = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        currentPlayer = player1;

        while (winner == null) {
            printGrid(grid);
            makeMark(currentPlayer, grid);
            // detect a complete line
            detectCompleteLine(grid, currentPlayer);
            if (currentPlayer == player1) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }
        }
        System.out.println("------- result -------");
        printGrid(grid);
        replay();
    }
}

