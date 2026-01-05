package org;

import org.museum.artefacts.Artefact;
import org.museum.artefacts.Material;
import org.museum.artefacts.Misc;
import org.museum.artefacts.Painting;
import org.museum.artefacts.artefacts3d.Furniture;
import org.museum.artefacts.artefacts3d.Pottery;
import org.museum.artefacts.artefacts3d.Sculpture;
import org.museum.data.Inventory;
import org.museum.other.Loan;

import java.sql.Date;
import java.util.Scanner;

public class UI
{
    static Scanner scanner = new Scanner(System.in);

    protected static void ResearcherUI() throws Exception
    {
        boolean run = true;
        do
        {
            System.out.println("""
                            
                            Welcome
                            1. Request a loan of an Artefact
                            2. List all Artefact
                            3. Search for Artefact by Name
                            4. Search for Artefact by Room
                            5. Search for Artefact by Type
                            6. View information about Artefact
                            7. View images of Artefact
                            8. Change to Staff
                            9. Exit""");
            String choice = scanner.next();
            switch (choice)
            {
                case "1" -> RequestLoan();
                case "2" -> ListArtefacts();
                case "3" -> SearchArtefactByName();
                case "4" -> SearchArtefactByRoom();
                case "5" -> SearchArtefactByType();
                case "6" -> ViewArtefactInfo();
                case "7" -> System.out.println("View images of Artefact");
                case "8" ->
                {
                    System.out.println("Enter Password");
                    String password = scanner.next();
                    if (password.equals("Password"))
                    {
                        System.out.println("Changing User");
                        StaffUI();
                    }else
                    {
                        System.out.println("Incorrect Password");
                    }
                }
                case "9" -> run = false;
                default -> System.out.println("Invalid choice, please try again.");
            }
        }while(run);
    }

    private static void StaffUI() throws Exception
    {
        boolean run = true;
        do
        {

            System.out.println("""
                                
                                Welcome
                                1. Add a new Artefact
                                2. List all Artefact
                                3. Move Artefact to another Room
                                4. Search for Artefact by Name
                                5. Search for Artefact by org.museum.other.Room
                                6. Search for Artefact by Type
                                7. View information about Artefact
                                8. View images of Artefact
                                9. Change to Managing Director
                                10. Exit to Researcher""");
            String choice = scanner.next();
            switch (choice)
            {
                case "1" -> AddArtefact();
                case "2" -> ListArtefacts();
                case "3" -> MoveArtefact();
                case "4" -> SearchArtefactByName();
                case "5" -> SearchArtefactByRoom();
                case "6" -> SearchArtefactByType();
                case "7" -> ViewArtefactInfo();
                case "8" -> ViewImagesOfArtefact();
                case "9" ->
                {
                    System.out.println("Enter Password");
                    String password = scanner.next();
                    if (password.equals("Password"))
                    {
                        System.out.println("Changing User");
                        ManagerUI();
                    }else
                    {
                        System.out.println("Incorrect Password");
                    }
                }
                case "10" -> run = false;
                default -> System.out.println("Invalid choice, please try again.");
            }
        }while(run);
    }


    private static void ManagerUI() throws Exception
    {
        boolean run = true;
        do
        {

            System.out.println("""
                                
                                Welcome
                                1. Add a new Artefact
                                2. Set Artefact Insurance Value
                                3. Search for Artefact by Name
                                4. Search for Artefact by Room
                                5. Search for Artefact by Type
                                6. View information about Artefact
                                7. View images of Artefact
                                8. Set Insurance Value of Artefact
                                9. Authorise external Loan
                                10. Exit To Staff""");
            String choice = scanner.next();
            switch (choice)
            {
                case "1" -> RequestLoan();
                case "2" -> SetInsurance();
                case "3" -> SearchArtefactByName();
                case "4" -> SearchArtefactByRoom();
                case "5" -> SearchArtefactByType();
                case "6" -> ViewArtefactInfo();
                case "7" -> ViewImagesOfArtefact();
                case "8" ->
                {
                    System.out.println("Enter Password");
                    String password = scanner.next();
                    if (password.equals("Password"))
                    {
                        System.out.println("Changing User");
                        StaffUI();
                    }else
                    {
                        System.out.println("Incorrect Password");
                    }
                }
                case "9" -> run = false;
                default -> System.out.println("Invalid choice, please try again.");
            }
        }while(run);
    }

    private static void ListArtefacts()
    {
        Inventory theInventory = Inventory.getInstance();
        try
        {
            String result = theInventory.ListAllArtefacts(false);
            System.out.println("Result: \n" + result);
        }
        catch (Exception e)
        {
            System.out.println("Error while Listing Artefacts: " + e.getMessage());
        }
    }

    private static void SetInsurance()
    {
        System.out.println("Please enter the name of the artefact you wish to set the insurance value for:");
        String artefactName = scanner.next();
        System.out.println("Please enter the insurance value:");
        double insuranceValue = scanner.nextDouble();
        Inventory theInventory = Inventory.getInstance();
        try
        {
            Artefact artefact = theInventory.getArtefactByName(artefactName, false);
            if (artefact != null)
            {
                artefact.setInsuranceValue(insuranceValue);
                System.out.println("Insurance value set successfully, for artefact: " + artefact.getName());
            } else
            {
                System.out.println("Artefact not found.");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error while Setting Insurance Value: " + e.getMessage());
        }
    }

    private static void MoveArtefact()
    {
    }

    private static void AddArtefact()
    {
        System.out.println("Please enter the name of the artefact:");
        String name = scanner.next();
        System.out.println("Please enter the historic era of the artefact:");
        String historicEra = scanner.next();
        System.out.println("Please enter the style of the artefact:");
        String style = scanner.next();
        System.out.println("Please enter the origin country of the artefact:");
        String originCountry = scanner.next();
        System.out.println("Please enter the author of the artefact:");
        String author = scanner.next();
        System.out.println("Please enter the date of creation of the artefact (YYYY-MM-DD):");
        String dateOfCreationStr = scanner.next();
        Date dateOfCreation = Date.valueOf(dateOfCreationStr);
        System.out.println("Please enter the width of the artefact:");
        double width = scanner.nextDouble();
        System.out.println("Please enter the height of the artefact:");
        double height = scanner.nextDouble();
        System.out.println("Please enter the type of the artefact:");
        String type = scanner.next();
        System.out.println("Please enter the current room the artefact is in:");
        String currentRoom = scanner.next();
        Artefact artefact = null;
        switch (type)
        {
            case "Furniture" -> artefact = addArtefact3D("Furniture", historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name);
            case "Pottery" -> artefact = addArtefact3D("Pottery", historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name);
            case "Sculpture" -> artefact = addArtefact3D("Sculpture", historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name);
            case "Misc" -> artefact = new Misc(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name);
            case "Painting" -> artefact = new Painting(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name);
            default -> {
            }
        }
        Inventory.addArtefact(artefact);
        System.out.println("Artefact added successfully.");

    }


    private static Artefact addArtefact3D(String type, String historicEra, String style, String originCountry, String currentRoom, String author, Date dateOfCreation, double width, double height, String name)
    {
        System.out.println("Please enter the depth of the artefact:");
        double depth = scanner.nextDouble();
        System.out.println("Please enter the material of the artefact (WOOD, METAL, PLASTIC, GLASS, STONE):");
        String materialStr = scanner.next();
        Material materialEnum = Material.valueOf(materialStr.toUpperCase());
        Artefact artefact = null;
        switch (type)
            {
                case "Furniture" -> artefact = new Furniture(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, depth, name, materialEnum);
                case "Pottery" -> artefact = new Pottery(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, depth, name, materialEnum);
                case "Sculpture" -> artefact = new Sculpture(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, depth, name, materialEnum);
            }
            return artefact;
    }

    private static void ViewArtefactInfo()
    {
        System.out.println("Please enter the name of the artefact you wish to view:");
        String artefactName = scanner.next();
        Inventory theInventory = Inventory.getInstance();
        try
        {
            Artefact artefact = theInventory.getArtefactByName(artefactName, false);
            assert artefact != null;
            System.out.println("Artefact Information: " + artefact);
        }
        catch (Exception e)
        {
            System.out.println("Error while Retrieving Artefact Information: " + e.getMessage());
        }
    }

    private static void SearchArtefactByType()
    {
        System.out.println("Please enter the Type of the artefact you wish to search for:");
        String type = scanner.next();
        Inventory theInventory = Inventory.getInstance();
        try
        {
            String result = theInventory.SearchArtefactType(type, false);
            System.out.println("Result: " + result);
        }
        catch (Exception e)
        {
            System.out.println("Error while Searching" + e.getMessage());
        }
    }

    private static void SearchArtefactByRoom()
    {
        System.out.println("Please enter the room of the artefact you wish to search for:");
        String roomName = scanner.next();
        Inventory theInventory = Inventory.getInstance();
        try
        {
            String result = theInventory.SearchArtefactRoom(roomName, false);
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
            String result = theInventory.SearchArtefactByName(artefactName, false);
            System.out.println(result);
        }
        catch (Exception e)
        {
            System.out.println("Error while Searching: " + e.getMessage());
        }

    }

    private static void RequestLoan() throws Exception
    {
        System.out.println("Please enter your name:");
        String name = scanner.next();
        System.out.println("Please enter your contact information:");
        String contactInfo = scanner.next();
        System.out.println("Please enter your telephone number:");
        String telNum = scanner.next();
        System.out.println("Please enter the name of the artefact you wish to loan:");
        String artefactName = scanner.next();
        if (!artefactName.isEmpty())
        {
            Inventory theInventory = Inventory.getInstance();
            Artefact artefact = theInventory.getArtefactByName(artefactName, false);
            if (artefact == null)
            {
                System.out.println("Artefact not found in inventory, Please try again.");
                RequestLoan();
                return;
            }
        }
        System.out.println("Please enter the start date of the loan (YYYY-MM-DD):");
        String startDate = scanner.next();
        System.out.println("Please enter the end date of the loan (YYYY-MM-DD):");
        String endDate = scanner.next();
        Loan loan = new Loan(false, name, contactInfo, telNum, artefactName, Date.valueOf(startDate), Date.valueOf(endDate));
    }

    private static void ViewImagesOfArtefact()
    {
        System.out.println("Please enter the name of the artefact you wish to view images of:");
        String artefactName = scanner.next();
        Inventory theInventory = Inventory.getInstance();
        try
        {
            theInventory.ViewImagesOfArtefact(artefactName, false);
        } catch (Exception e)
        {
            System.out.println("Error while Retrieving Images: " + e.getMessage());
        }
    }

}
