package org;

import org.museum.artefacts.Artefact;
import org.museum.data.DataBase;
import org.museum.data.Inventory;
import org.museum.other.Loan;

import java.sql.Date;
import java.util.Scanner;

public class UI
{
    static Scanner scanner = new Scanner(System.in);

    protected static void ResearcherUI()
    {
        boolean run = true;
        do
        {
            System.out.println("Welcome");
            System.out.println("1. Request a loan of an Artefact");
            System.out.println("2. Search for Artefact by Name");
            System.out.println("3. Search for Artefact by Room");
            System.out.println("4. Search for Artefact by Type");
            System.out.println("5. View information about Artefact");
            System.out.println("6. View images of Artefact");
            System.out.println("7. Change to Staff");
            System.out.println("8. Exit");
            String choice = scanner.next();
            switch (choice)
            {
                case "1" -> RequestLoan();
                case "2" -> SearchArtefactByName();
                case "3" -> SearchArtefactByRoom();
//                case "4" -> RequestLoan();
//                case "5" -> ViewArtefactInfo();
                case "6" -> System.out.println("View images of Artefact");
                case "7" ->
                {
                    System.out.println("Enter Password");
                    String password = scanner.next();
                    if (password.equals("Password"))
                    {
                        System.out.println("Changing User");
                        StaffUI();
                    }
                }
                case "8" -> run = false;
                default -> System.out.println("Invalid choice, please try again.");
            }
        }while(run);
    }

    private static void SearchArtefactByRoom()
    {
        System.out.println("Please enter the room of the artefact you wish to search for:");
        String roomName = scanner.next();
        Inventory theInventory = Inventory.getInstance();
        try
        {
            String result = theInventory.SearchArtefactByRoom(roomName);
            System.out.println("Result: " + result);
        }
        catch (Exception e)
        {
            System.out.println("Error while Searching" + e.getMessage());
        }
    }

    private static void SearchArtefactByName()
    {
        System.out.println("Please enter the name of the artefact you wish to search for:");
        String artefactName = scanner.next();
        Inventory theInventory = Inventory.getInstance();
        try
        {
            String result = theInventory.SearchArtefactByName(artefactName);
            System.out.println("Result: " + result);
        }
        catch (Exception e)
        {
            System.out.println("Error while Searching: " + e.getMessage());
        }

    }

    private static void RequestLoan()
    {
        System.out.println("Please enter your name:");
        String name = scanner.next();
        System.out.println("Please enter your contact information:");
        String contactInfo = scanner.next();
        System.out.println("Please enter your telephone number:");
        String telNum = scanner.next();
        System.out.println("Please enter the name of the artefact you wish to loan:");
        String artefactName = scanner.next();
        System.out.println("Please enter the start date of the loan (YYYY-MM-DD):");
        String startDate = scanner.next();
        System.out.println("Please enter the end date of the loan (YYYY-MM-DD):");
        String endDate = scanner.next();
        Loan loan = new Loan(false, name, contactInfo, telNum, artefactName, Date.valueOf(startDate), Date.valueOf(endDate));
    }


    private static void StaffUI()
    {
        boolean run = true;
        do
        {
            System.out.println("Welcome");
            System.out.println("1. View information about Artefact");
            System.out.println("2. View Image of Artefact");
            System.out.println("2. Add Artefact");
            System.out.println("3. Move Artefact");
            System.out.println("5. Change to Manager");
            System.out.println("6. Exit to Researcher");
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
                        System.out.println("Changing User");
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
        boolean run = true;
        do
        {
            System.out.println("Welcome");
            System.out.println("1. View image of Artefact");
            System.out.println("2. View information about Artefact");
            System.out.println("4. Exit to Staff");
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
