package org.example;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        ResearcherUI();
    }

    private static void ResearcherUI()
    {
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        do
        {
            System.out.println("Welcome");
            System.out.println("1. Request a loan of an Artefact");
            System.out.println("2. View information about Artefact");
            System.out.println("3. View images of Artefact");
            System.out.println("4. Change User");
            System.out.println("5. Exit");
            String choice = scanner.next();
            switch (choice)
            {
                case "1" -> System.out.println("You Requested a loan of an Artefact");
                case "2" -> System.out.println("View information about Artefact");
                case "3" -> System.out.println("View images of Artefact");
                case "4" ->
                {
                    System.out.println("Enter Password");
                    String password = scanner.next();
                    if (password.equals("Password"))
                    {
                        System.out.println("Access Granted. Changing User...");
                        StaffUI();
                    }
                }
                case "5" ->
                {
                    System.out.println("Exiting...");
                    run = false;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }while(run);
    }


    private static void StaffUI()
    {
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        do
        {
            System.out.println("Welcome");
            System.out.println("1. View information about Artefact");
            System.out.println("2. View Image of Artefact");
            System.out.println("2. Add Artefact");
            System.out.println("3. Move Artefact");
            System.out.println("5. Change User");
            System.out.println("6. Exit");
            String choice = scanner.next();
            switch (choice)
            {
                case "1" -> System.out.println("Viewing info about Artefact");
                case "2" -> System.out.println("Viewing Image of Artefact");
                case "3" -> System.out.println("Adding Artefact");
                case "4" -> System.out.println("Moving Artefact");
                case "5" ->
                {
                    System.out.println("Enter Password");
                    String password = scanner.next();
                    if (password.equals("BetterPassword"))
                    {
                        System.out.println("Access Granted. Changing User...");
                        ManagerUI();
                    }
                }
                case "6" ->
                {
                    System.out.println("Exiting...");
                    run = false;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }while(run);
    }


    private static void ManagerUI()
    {
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        do
        {
            System.out.println("Welcome");
            System.out.println("1. View image of Artefact");
            System.out.println("2. View information about Artefact");
            System.out.println("4. Exit");
            String choice = scanner.next();
            switch (choice)
            {
                case "1" -> System.out.println("You selected Option One");
                case "2" -> System.out.println("You selected Option Two");
                case "4" ->
                {
                    System.out.println("Exiting...");
                    run = false;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }while(run);
    }
}