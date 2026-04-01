package org;

import org.museum.artefacts.Artefact;
import org.museum.artefacts.Material;
import org.museum.artefacts.Misc;
import org.museum.artefacts.Painting;
import org.museum.artefacts.artefacts3d.Furniture;
import org.museum.artefacts.artefacts3d.Pottery;
import org.museum.artefacts.artefacts3d.Sculpture;
import org.museum.data.DataBase;
import org.museum.data.Inventory;
import org.museum.other.Loan;
import org.museum.other.Room;

import java.sql.Date;
import java.util.Objects;
import java.util.Scanner;
//todo: move all of the methods that dont include UI to other classes
public class UI
{
    static Scanner scanner = new Scanner(System.in);

    /**
     * Entry point for the Researcher user interface.
     * Presents a menu to the researcher and dispatches to helper methods based on user input.
     *
     * @throws Exception if any called operation (e.g. database access) throws an exception
     */
    protected static void ResearcherUI() throws Exception
    {
        boolean run = true;
        do
        {
            System.out.println("""
                            
                            Welcome
                            1. Request a loan of an Artefact
                            2. View Image of Artefact
                            3. List all Artefact
                            4. Search for Artefact by Name
                            5. Search for Artefact by Room
                            6. Search for Artefact by Type
                            7. View information about Artefact
                            8. View All Images
                            9. Change to Staff
                            10. Exit""");
            String choice = scanner.next();
            switch (choice)
            {
                case "1" -> RequestLoan();
                case "2" -> ViewImagesOfArtefact();
                case "3" -> ListArtefacts();
                case "4" -> SearchArtefactByName();
                case "5" -> SearchArtefactByRoom();
                case "6" -> SearchArtefactByType();
                case "7" -> ViewArtefactInfo();
                case "8" -> ViewAllImages();
                case "9" ->
                {
                    System.out.println("Enter Password");
                    String password = scanner.next();
                    if (password.equals("StaffPassword"))
                    {
                        System.out.println("Changing User");
                        StaffUI();
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

    /**
     *
     */
    private static void ViewAllImages()
    {
        Inventory theInventory = Inventory.getInstance();
        try
        {
            theInventory.ViewImagesOfArtefact(Objects.requireNonNull(DataBase.getAllImages(false)));
        } catch (Exception e)
        {
            System.out.println("Error while Retrieving Images: " + e.getMessage());
        }
    }

    /**
     * Entry point for the Staff user interface.
     * Presents a menu to staff members and dispatches to helper methods based on user input.
     *
     * @throws Exception if any called operation (e.g. database access) throws an exception
     */
    private static void StaffUI() throws Exception
    {
        boolean run = true;
        do
        {
            //todo: should maybe either remove add artefact or add option to add room since can be done by adding artefact to a non existent room then selecting to create that room
            System.out.println("""
                                
                                Welcome
                                1. Add a new Artefact
                                2. Add Image to Artefact
                                3. List all Artefact
                                4. Move Artefact to another Room
                                5. Search for Artefact by Name
                                6. Search for Artefact by Room
                                7. Search for Artefact by Type
                                8. View information about Artefact
                                9. View images of Artefact
                                10. Change to Managing Director
                                11. Exit to Researcher""");
            String choice = scanner.next();
            switch (choice)
            {
                case "1" -> AddArtefact(false);
                case "2" -> AddImage();
                case "3" -> ListArtefacts();
                case "4" -> MoveArtefact();
                case "5" -> SearchArtefactByName();
                case "6" -> SearchArtefactByRoom();
                case "7" -> SearchArtefactByType();
                case "8" -> ViewArtefactInfo();
                case "9" -> ViewImagesOfArtefact();
                case "10" ->
                {
                    System.out.println("Enter Password");
                    String password = scanner.next();
                    if (password.equals("DirectorPassword"))
                    {
                        System.out.println("Changing User");
                        ManagerUI();
                    }else
                    {
                        System.out.println("Incorrect Password");
                    }
                }
                case "11" -> run = false;
                default -> System.out.println("Invalid choice, please try again.");
            }
        }while(run);
    }


    /**
     * Entry point for the Manager user interface.
     * Presents a manager-specific menu and dispatches operations.
     *
     * @throws Exception if any called operation (e.g. database access) throws an exception
     */
    private static void ManagerUI() throws Exception
    {
        boolean run = true;
        do
        {
            System.out.println("""
                                
                                Welcome
                                1. Add a new Artefact
                                2. Set Artefact Insurance Value
                                3. Add new Room
                                4. View information about Artefact
                                5. View All Loans
                                6. View images of Artefact
                                7. Authorise external Loan
                                8. Exit To Staff""");
            String choice = scanner.next();
            switch (choice)
            {
                case "1" -> RequestLoan();
                case "2" -> SetInsurance();
                case "3" -> AddRoom();
                case "4" -> ViewArtefactInfo();
                case "5" -> getAllLoans();
                case "6" -> ViewImagesOfArtefact();
                case "7" -> AuthorizeLoan();
                case "8" -> run = false;
                default -> System.out.println("Invalid choice, please try again.");
            }
        }while(run);
    }

    private static void AddRoom()
    {
        Inventory.getInstance();

        System.out.println("Please enter the number of the room you wish to add:");
        String roomNum = scanner.next();
        System.out.println("Please enter the name of the room you wish to add:");
        String roomName = scanner.next();
        System.out.println("Please enter the capacity of the room you wish to add:");
        String roomcapacitystr = scanner.next();

        int roomcapacity = Integer.parseInt(roomcapacitystr);

        Room room = new Room(roomNum, roomName, roomcapacity);
        Inventory.getInstance().addRoom(room);
        System.out.println("Room added successfully.");
    }

    /**
     * Retrieves and prints all loan requests from the Inventory.
     */
    private static void getAllLoans()
    {
        for (Loan loan : Inventory.getLoans(false))
        {
            System.out.println("Requester Name: " + loan.getName());
            System.out.println("Contact Info: " + loan.getContactInfo());
            System.out.println("Telephone Number: " + loan.getTelNum());
            System.out.println("Artefact Name: " + loan.getArtefactName());
            System.out.println("Loan Start Date: " + loan.getStartDate());
            System.out.println("Loan End Date: " + loan.getEndDate());
            System.out.println("Loan Approved: " + loan.isApproved() + "\n");
        }
    }

    /**
     * Prompts the user to authorize a loan request by artefact name.
     *
     * @throws Exception if any operation (e.g. database access) fails
     */
    private static void AuthorizeLoan() throws Exception
    {
        for (Loan loan : Inventory.getLoans(false))
        {
            System.out.println("Requester Name: " + loan.getName());
            System.out.println("Contact Info: " + loan.getContactInfo());
            System.out.println("Telephone Number: " + loan.getTelNum());
            System.out.println("Artefact Name: " + loan.getArtefactName());
            System.out.println("Loan Start Date: " + loan.getStartDate());
            System.out.println("Loan End Date: " + loan.getEndDate());
            System.out.println("Loan Approved: " + loan.isApproved() + "\n");
        }
        System.out.println("Please enter the name of the artefact loan you wish to authorise:");
        String artefactName = scanner.next();
        Loan loan = Inventory.getInstance().getLoanByArtefactName(artefactName, false);
        if (loan == null)
        {
            System.out.println("Loan could not be found for selected artefact");
            return;
        }
        System.out.println("Confirm Approval? (Y/n) for artefact: " + loan.getArtefactName());
        if (scanner.next().equalsIgnoreCase("Y"))
        {
            loan.authorizeLoan();
            try {
                org.museum.data.DataBase.updateLoanApproval(loan.getArtefactName(), true, false);
            } catch (Exception ignored) {}
            System.out.println("Loan authorised successfully for artefact: " + loan.getArtefactName());
        } else
        {
            System.out.println("Loan not authorised for artefact: " + loan.getArtefactName());
        }
    }

    /**
     * Prompts the user for artefact name and image file path, then adds the image to the artefact.
     * Errors during the process are caught and printed.
     */
    private static void AddImage()
    {
        System.out.println("Please enter the name of the artefact you wish to add an image to:");
        String artefactName = scanner.next();
        System.out.println("Please enter the full name of the image including file extension (place image in same Folder as the program):");
        String filePath = scanner.next();

        int lastDotIndex = filePath.lastIndexOf('.');

        String fileType = filePath.substring(lastDotIndex + 1);
        Inventory theInventory = Inventory.getInstance();

        try
        {
            Artefact artefact = theInventory.getArtefactByName(artefactName, false);
            if (artefact != null)
            {
                try
                {
                    artefact.addImage(filePath, false, fileType);
                    System.out.println("Image added successfully to artefact: " + artefact.getName());
                } catch (Exception e)
                {
                    System.out.println("Error while Adding Image to Artefact: " + e.getMessage());
                }
            } else
            {
                System.out.println("Artefact not found.");
            }
        } catch (Exception e)
        {
            System.out.println("Error while Adding Image: " + e.getMessage());
        }
    }

    /**
     * Lists all artefacts by querying the Inventory and printing the result to stdout.
     * Errors during listing are caught and printed.
     */
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

    /**
     * Prompts the user for an artefact name and insurance value and updates the artefact's
     * insurance value in the inventory if found.
     * Errors are caught and printed.
     */
    private static void SetInsurance()
    {
        System.out.println("Please enter the name of the artefact you wish to set the insurance value for:");
        String artefactName = scanner.next();
        System.out.println("Please enter the insurance value:");
        double insuranceValue = scanner.nextDouble();
        if (insuranceValue < 0)
        {
            System.out.println("Insurance value cannot be negative Please Try again.");
            SetInsurance();
            return;
        }
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

    /**
     * Move artefact
     ***/
    private static void MoveArtefact() throws Exception
    {
        System.out.println("Please enter the name of the artefact you wish to move:");
        String artefactName = scanner.next();
        System.out.println("Please enter the name of the room you wish to move the artefact to:");  //todo: add option to move to room number aswell as name
        String roomName = scanner.next();

        if (roomName.isEmpty() || artefactName.isEmpty())
        {
            System.out.println("Room name or Artefact name cannot be empty.");
            return;
        }
        if (Inventory.moveArtefactToRoom(artefactName, roomName, false))
        {
            System.out.println("Artefact moved successfully.");
        } else
        {
            System.out.println("Failed to move artefact Please try again.");
        }
    }

    /**
     * Prompts for artefact details and creates a new artefact entry using user input.
     * The created artefact is added to the Inventory.
     *
     * <p>Input is read from the shared Scanner and some parsing (dates, numbers) is performed.
     * Invalid user input may throw runtime exceptions (e.g. IllegalArgumentException from Date.valueOf).</p>
     */
    private static void AddArtefact(boolean TestMode)
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
        System.out.println("Please enter the type of the artefact: (Furniture, Pottery, Sculpture, Misc, Painting)");
        String type = scanner.next();
        if (!type.equals("Furniture") && !type.equals("Pottery") && !type.equals("Sculpture") && !type.equals("Misc") && !type.equals("Painting"))
        {
            System.out.println("Invalid artefact type. Please enter 1 to try again or any other key to exit.");
            String choice = scanner.next();
            if (choice.equals("1"))
            {
                AddArtefact(TestMode);
            }
            return;
        }
        System.out.println("Please enter the current room the artefact is in:");
        String currentRoom = scanner.next();
        if (!DataBase.getRooms().isEmpty() || !DataBase.getRooms().contains(currentRoom))
        {
            System.out.println("Invalid room name, or no room with that name could be found. Please enter 1 to try again, 2 to create the room: " + currentRoom + " or any other key to exit.");
            String choice = scanner.next();
            if (choice.equals("1"))
            {
                AddArtefact(TestMode);
            } else if (choice.equals("2"))
            {
                AddRoom();
            }
            return;
        }
        Artefact artefact = null;
        try
        {
            switch (type)
            {
                case "Furniture" -> artefact = addArtefact3D("Furniture", historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name, TestMode);
                case "Pottery" -> artefact = addArtefact3D("Pottery", historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name, TestMode);
                case "Sculpture" -> artefact = addArtefact3D("Sculpture", historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name, TestMode);
                case "Misc" -> artefact = new Misc(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name, TestMode);
                case "Painting" -> artefact = new Painting(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name, TestMode);
                default -> {
                    System.out.println("Unknown artefact type. Artefact not added.");
                    return;
                }

            }
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        if (!Inventory.addArtefact(artefact))
        {
            System.out.println("Failed to add artefact.");
            return;
        }
        System.out.println("Artefact added successfully.");
    }


    /**
     * Helper for creating 3D artefact instances (Furniture, Pottery, Sculpture).
     * Prompts for depth and material and returns a constructed Artefact subtype.
     *
     * @param type the subtype to construct ("Furniture", "Pottery", "Sculpture")
     * @param historicEra historic era string
     * @param style style string
     * @param originCountry country of origin
     * @param currentRoom current room name
     * @param author author/creator name
     * @param dateOfCreation creation date as java.sql.Date
     * @param width artefact width
     * @param height artefact height
     * @param name artefact name
     * @return constructed Artefact instance or null if the type was unknown
     */
    private static Artefact addArtefact3D(String type, String historicEra, String style, String originCountry, String currentRoom, String author, Date dateOfCreation, double width, double height, String name, boolean TestMode)
    {
        System.out.println("Please enter the depth of the artefact:");
        double depth = scanner.nextDouble();
        System.out.println("Please enter the material of the artefact (WOOD, METAL, PLASTIC, GLASS, STONE):");
        String materialStr = scanner.next();
        Material materialEnum = Material.valueOf(materialStr.toUpperCase());
        Artefact artefact = null;

        try
        {
            switch (type)
                {
                    case "Furniture" -> artefact = new Furniture(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, depth, name, materialEnum, TestMode);
                    case "Pottery" -> artefact = new Pottery(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, depth, name, materialEnum, TestMode);
                    case "Sculpture" -> artefact = new Sculpture(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, depth, name, materialEnum, TestMode);
                }
            return artefact;
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prompts for an artefact name and prints its detailed information.
     * Errors are caught and printed.
     */
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

    /**
     * Prompts for an artefact type and prints search results returned by Inventory.
     * Errors are caught and printed.
     */
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

    /**
     * Prompts for a room name and prints search results returned by Inventory.
     * Errors are caught and printed.
     */
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

    /**
     * Prompts for an artefact name and prints search results returned by Inventory.
     * Errors are caught and printed.
     */
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

    /**
     * Prompts the user for loan request information and constructs a Loan object.
     *
     * @throws Exception if validation or database operations fail while looking up artefacts
     */
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
        for (Loan loan : Inventory.getLoans(false))
        {
            System.out.println(loan.getArtefactName());
            if (loan.getArtefactName() != null && loan.getArtefactName().toLowerCase().contains(artefactName.toLowerCase()))
            {
                System.out.println("Artefact is already on loan, Please try again later or with a different artefact.");
                RequestLoan();
                return;
            }
        }
        System.out.println("Please enter the start date of the loan (YYYY-MM-DD):");
        String startDate = scanner.next();
        if (Date.valueOf(startDate).before(new Date(System.currentTimeMillis())))
        {
            System.out.println("Start date cannot be in the past, Please try again.");
            RequestLoan();
            return;
        }
        System.out.println("Please enter the end date of the loan (YYYY-MM-DD):");
        String endDate = scanner.next();
        if (Date.valueOf(endDate).before(Date.valueOf(startDate)))
        {
            System.out.println("End date cannot be before start date, Please try again.");
            RequestLoan();
            return;
        }
        Loan loan = new Loan(false, name, contactInfo, telNum, artefactName, Date.valueOf(startDate), Date.valueOf(endDate));
        Inventory.getInstance().addLoan(loan);
        System.out.println("Loan request submitted successfully.");
    }

    /**
     * Prompts for an artefact name and asks the Inventory to display its images.
     * Errors are caught and printed.
     */
    private static void ViewImagesOfArtefact()
    {
        System.out.println("Please enter the name of the artefact you wish to view images of:");
        String artefactName = scanner.next();
        Inventory theInventory = Inventory.getInstance();
        try
        {
            theInventory.ViewImagesOfArtefact(Objects.requireNonNull(DataBase.getImageFromArtefact(artefactName, false)));
        } catch (Exception e)
        {
            System.out.println("Error while Retrieving Images: " + e.getMessage());
        }
    }

}
