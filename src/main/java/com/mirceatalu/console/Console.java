package com.mirceatalu.console;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.microsoft.graph.models.User;
import com.mirceatalu.graph.Graph;

public final class Console {

    private Console() {

    }
    public static void displayOptions() {
        greetUser();

        Scanner input = new Scanner(System.in);

        int choice = -1;

        while (choice != 0) {
            System.out.println("Please choose one of the following options:");
            System.out.println("0. Exit");
            System.out.println("1. Display access token");
            System.out.println("2. Get user");

            try {
                choice = input.nextInt();
            } catch (InputMismatchException ex) {
                // Skip over non-integer input
            }

            input.nextLine();

            // Process user choice
            switch(choice) {
                case 0:
                    // Exit the program
                    System.out.println("Goodbye...");
                    break;
                case 1:
                    displayAccessToken();
                    break;
                case 2:
                    getUser();
                default:
                    System.out.println("Invalid choice");
            }
        }

        input.close();
    }

    private static void greetUser() {
        System.out.println("Welcome to the Graph API com.mirceatalu.console app!");
    }

    private static void displayAccessToken() {
        try {
            final String accessToken = Graph.getUserToken();
            System.out.println("Access token: " + accessToken);
        } catch (Exception e) {
            System.out.println("Error getting access token");
            System.out.println(e.getMessage());
        }
    }

    private static void getUser() {
        try {
            final User user = Graph.getUser();
            System.out.println(user.getDisplayName());
        } catch (Exception e) {
            System.out.println("Error getting user");
            System.out.println(e.getMessage());
        }
    }

}
