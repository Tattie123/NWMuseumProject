package org.museum.data;

import org.junit.jupiter.api.*;
import org.museum.artefacts.Material;
import org.museum.artefacts.Painting;
import org.museum.artefacts.artefacts3d.Artefact3D;
import org.museum.artefacts.artefacts3d.Furniture;
import org.museum.artefacts.artefacts3d.Pottery;
import org.museum.artefacts.artefacts3d.Sculpture;
import org.museum.other.Loan;
import org.museum.other.Room;

import java.io.InputStream;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DataBaseTest
{
    Inventory theInventory = Inventory.getInstance();

    Artefact3D Table1;
    Artefact3D Table2;
    Artefact3D Table3;
    Artefact3D Table4;
    Artefact3D Table5;

    Artefact3D Pot1;
    Artefact3D Pot2;
    Artefact3D Pot3;
    Artefact3D Pot4;
    Artefact3D Pot5;

    Sculpture Sculpture1;
    Sculpture Sculpture2;
    Sculpture Sculpture3;
    Sculpture Sculpture4;
    Sculpture Sculpture5;

    Painting Painting1;
    Painting Painting2;
    Painting Painting3;
    Painting Painting4;
    Painting Painting5;

    Loan Loan1;
    Loan Loan2;
    Loan Loan3;
    Loan Loan4;
    Loan Loan5;

    static Room Room1;
    static Room Room2;
    static Room Room3;
    static Room Room4;
    static Room Room5;

    @BeforeAll
    static void beforeAll() throws Exception {
        Connection conn = DataBase.getConnection(true);

        InputStream in = DataBaseTest.class.getClassLoader().getResourceAsStream("schema.sql");
        if (in != null) {
            String sql = new String(in.readAllBytes());
            conn.createStatement().execute(sql);
        }

        Room1 = new Room("Room a", "Ancient Artifacts", 50);
        Room2 = new Room("Room b", "Medieval Wonders", 40);
        Room3 = new Room("Room c", "Renaissance Gallery", 60);
        Room4 = new Room("Room d", "Modern Art", 70);
        Room5 = new Room("Room e", "Old Art", 30);

        DataBase.addRoom(Room1, true);
        DataBase.addRoom(Room2, true);
        DataBase.addRoom(Room3, true);
        DataBase.addRoom(Room4, true);
        DataBase.addRoom(Room5, true);

    }


    @BeforeEach
    void setUp() throws Exception
    {
        Table1 = new Furniture("Medieval", "Gothic", "England", "Room a", "Jim", java.sql.Date.valueOf("2000-01-01"), 0.75, 0.5, 3.75, 1.3, "Dining Table", Material.WOOD, true);
        Table2 = new Furniture("Renaissance", "Baroque", "Italy", "Room b", "Bob", java.sql.Date.valueOf("2000-05-15"), 1.0, 0.8, 4.0, 1.5, "Coffee Table", Material.STONE, true);
        Table3 = new Furniture("Modern", "Contemporary", "USA", "Room c", "Alice", java.sql.Date.valueOf("2001-08-10"), 1.2, 0.6, 3.5, 1.2, "Side Table", Material.METAL, true);
        Table4 = new Furniture("Ancient", "Classical", "Greece", "Room d", "Dave", java.sql.Date.valueOf("2002-11-05"), 1.8, 0.9, 4.5, 1.8, "Pedestal Table", Material.WOOD, true);
        Table5 = new Furniture("Victorian", "Eclectic", "England", "Room a", "Jerry", java.sql.Date.valueOf("2003-01-20"), 1.4, 0.7, 3.0, 1.0, "Console Table", Material.STONE, true);

        Pot1 = new Pottery("Ancient", "Classical", "Egypt", "Room b", "Sara", java.sql.Date.valueOf("1999-03-12"), 0.3, 0.5, 2.0, 0.4, "Clay Pot", Material.STONE, true);
        Pot2 = new Pottery("Medieval", "Gothic", "France", "Room c", "Tom", java.sql.Date.valueOf("2000-06-18"), 0.4, 0.6, 2.5, 0.5, "Ceramic Vase", Material.STONE, true);
        Pot3 = new Pottery("Renaissance", "Baroque", "Italy", "Room d", "Lily", java.sql.Date.valueOf("2001-09-22"), 0.5, 0.7, 3.0, 0.6, "Porcelain Jar", Material.STONE, true);
        Pot4 = new Pottery("Modern", "Contemporary", "USA", "Room a", "Mark", java.sql.Date.valueOf("2002-12-30"), 0.6, 0.8, 3.5, 0.7, "Glass Bowl", Material.METAL, true);
        Pot5 = new Pottery("Victorian", "Eclectic", "England", "Room b", "Nina", java.sql.Date.valueOf("2003-04-14"), 0.7, 0.9, 4.0, 0.8, "Metal Canister", Material.METAL, true);

        Sculpture1 = new Sculpture("Modern", "Abstract", "USA", "Room c", "Alice", java.sql.Date.valueOf("2004-01-01"), 1.0, 1.5, 5.0, 2.0, "Abstract Sculpture", Material.METAL, true);
        Sculpture2 = new Sculpture("Renaissance", "Baroque", "Italy", "Room d", "Bob", java.sql.Date.valueOf("2004-05-15"), 1.2, 1.7, 6.0, 2.5, "Baroque Sculpture", Material.STONE, true);
        Sculpture3 = new Sculpture("Ancient", "Classical", "Greece", "Room a", "Charlie", java.sql.Date.valueOf("2004-08-10"), 1.5, 2.0, 7.0, 3.0, "Classical Sculpture", Material.WOOD, true);
        Sculpture4 = new Sculpture("Medieval", "Gothic", "England", "Room b", "Diana", java.sql.Date.valueOf("2004-11-05"), 1.3, 1.8, 5.5, 2.5, "Gothic Sculpture", Material.STONE, true);
        Sculpture5 = new Sculpture("Victorian", "Eclectic", "England", "Room c", "Ethan", java.sql.Date.valueOf("2005-01-20"), 1.4, 1.9, 6.5, 2.8, "Eclectic Sculpture", Material.METAL, true);

        Painting1 = new Painting("Modern", "Abstract", "USA", "Room d", "Alice", java.sql.Date.valueOf("2005-02-01"), 1.0, 1.5, 5.0, "Abstract Painting", true);
        Painting2 = new Painting("Renaissance", "Baroque", "Italy", "Room a", "Bob", java.sql.Date.valueOf("2005-03-15"), 1.2, 1.7, 6.0,"Baroque Painting", true);
        Painting3 = new Painting("Ancient", "Classical", "Greece", "Room b", "Charlie", java.sql.Date.valueOf("2005-04-10"), 1.5, 2.0, 7.0,"Classical Painting", true);
        Painting4 = new Painting("Medieval", "Gothic", "England", "Room c", "Diana", java.sql.Date.valueOf("2005-05-05"), 1.3, 1.8, 5.5, "Gothic Painting", true);
        Painting5 = new Painting("Victorian", "Eclectic", "England", "Room d", "Ethan", java.sql.Date.valueOf("2005-06-20"), 1.4, 1.9, 6.5, "Eclectic Painting", true);

        Loan1 = new Loan(false, "John Doe", "john@example.com", "1234567890", "Abstract Sculpture", java.sql.Date.valueOf("2023-01-01"), java.sql.Date.valueOf("2023-01-15"));
        Loan2 = new Loan(false, "Jane Smith", "jane@example.com", "9876543210", "Baroque Painting", java.sql.Date.valueOf("2023-02-01"), java.sql.Date.valueOf("2023-02-15"));
        Loan3 = new Loan(false, "Alice Johnson", "alice@example.com", "5555555555", "Classical Sculpture", java.sql.Date.valueOf("2023-03-01"), java.sql.Date.valueOf("2023-03-15"));
        Loan4 = new Loan(false, "Bob Brown", "bob@example.com", "4444444444", "Gothic Painting", java.sql.Date.valueOf("2023-04-01"), java.sql.Date.valueOf("2023-04-15"));
        Loan5 = new Loan(false, "Charlie Davis", "charlie@example.com", "3333333333", "Eclectic Sculpture", java.sql.Date.valueOf("2023-05-01"), java.sql.Date.valueOf("2023-05-15"));
    }

    @Test
    @Order(1)
    void getConnection() throws Exception
    {
        assertNotNull(DataBase.getConnection(true));
    }

    @Test
    @Order(2)
    void addRooms() throws Exception
    {
        //assertTrue(DataBase.addRoom(Room1, true));
        //assertTrue(DataBase.addRoom(Room2, true));
        //assertTrue(DataBase.addRoom(Room3, true));
        //assertTrue(DataBase.addRoom(Room4, true));
        //assertTrue(DataBase.addRoom(Room5, true));
    }

    @Test
    @Order(3)
    void addArtefacts() throws Exception
    {
        assertTrue(DataBase.addArtefact(Table1, true));
        assertTrue(DataBase.addArtefact(Table2, true));
        assertTrue(DataBase.addArtefact(Table3, true));
        assertTrue(DataBase.addArtefact(Table4, true));
        assertTrue(DataBase.addArtefact(Table5, true));
        assertTrue(DataBase.addArtefact(Pot1, true));
        assertTrue(DataBase.addArtefact(Pot2, true));
        assertTrue(DataBase.addArtefact(Pot3, true));
        assertTrue(DataBase.addArtefact(Pot4, true));
        assertTrue(DataBase.addArtefact(Pot5, true));
        assertTrue(DataBase.addArtefact(Sculpture1, true));
        assertTrue(DataBase.addArtefact(Sculpture2, true));
        assertTrue(DataBase.addArtefact(Sculpture3, true));
        assertTrue(DataBase.addArtefact(Sculpture4, true));
        assertTrue(DataBase.addArtefact(Sculpture5, true));
        assertTrue(DataBase.addArtefact(Painting1, true));
        assertTrue(DataBase.addArtefact(Painting2, true));
        assertTrue(DataBase.addArtefact(Painting3, true));
        assertTrue(DataBase.addArtefact(Painting4, true));
        assertTrue(DataBase.addArtefact(Painting5, true));
    }

    @Test
    @Order(4)
    void searchArtefacts() throws Exception
    {
        assertEquals("Artefact Name: Console Table, Artefact Type:  Furniture, Current Location: Room a", theInventory.SearchArtefactByName("Console Table", true));
        assertEquals("Artefact Name: Porcelain Jar, Artefact Type:  Pottery, Current Location: Room d", theInventory.SearchArtefactByName("Porcelain Jar", true));
        assertEquals("Artefact Name: Abstract Sculpture, Artefact Type:  Sculpture, Current Location: Room c", theInventory.SearchArtefactByName("Abstract Sculpture", true));
        assertEquals("Artefact Name: Coffee Table, Artefact Type:  Furniture, Current Location: Room b\n" +
                "Artefact Name: Clay Pot, Artefact Type:  Pottery, Current Location: Room b\n" +
                "Artefact Name: Metal Canister, Artefact Type:  Pottery, Current Location: Room b\n" +
                "Artefact Name: Gothic Sculpture, Artefact Type:  Sculpture, Current Location: Room b\n" +
                "Artefact Name: Classical Painting, Artefact Type:  Painting, Current Location: Room b", theInventory.SearchArtefactRoom("Room b", true));
        assertEquals("Artefact Name: Side Table, Artefact Type:  Furniture, Current Location: Room c\n" +
                "Artefact Name: Ceramic Vase, Artefact Type:  Pottery, Current Location: Room c\n" +
                "Artefact Name: Abstract Sculpture, Artefact Type:  Sculpture, Current Location: Room c\n" +
                "Artefact Name: Eclectic Sculpture, Artefact Type:  Sculpture, Current Location: Room c\n" +
                "Artefact Name: Gothic Painting, Artefact Type:  Painting, Current Location: Room c", theInventory.SearchArtefactRoom("Room c", true));
        assertEquals("Artefact Name: Pedestal Table, Artefact Type:  Furniture, Current Location: Room d\n" +
                "Artefact Name: Porcelain Jar, Artefact Type:  Pottery, Current Location: Room d\n" +
                "Artefact Name: Baroque Sculpture, Artefact Type:  Sculpture, Current Location: Room d\n" +
                "Artefact Name: Abstract Painting, Artefact Type:  Painting, Current Location: Room d\n" +
                "Artefact Name: Eclectic Painting, Artefact Type:  Painting, Current Location: Room d", theInventory.SearchArtefactRoom("Room d", true));
        assertEquals("""
                Artefact Name: Dining Table, Artefact Type:  Furniture, Current Location: Room a
                Artefact Name: Coffee Table, Artefact Type:  Furniture, Current Location: Room b
                Artefact Name: Side Table, Artefact Type:  Furniture, Current Location: Room c
                Artefact Name: Pedestal Table, Artefact Type:  Furniture, Current Location: Room d
                Artefact Name: Console Table, Artefact Type:  Furniture, Current Location: Room a""", theInventory.SearchArtefactType("Furniture", true));
        assertEquals("""
                Artefact Name: Clay Pot, Artefact Type:  Pottery, Current Location: Room b
                Artefact Name: Ceramic Vase, Artefact Type:  Pottery, Current Location: Room c
                Artefact Name: Porcelain Jar, Artefact Type:  Pottery, Current Location: Room d
                Artefact Name: Glass Bowl, Artefact Type:  Pottery, Current Location: Room a
                Artefact Name: Metal Canister, Artefact Type:  Pottery, Current Location: Room b""", theInventory.SearchArtefactType("Pottery", true));
        assertEquals("""
                Artefact Name: Abstract Sculpture, Artefact Type:  Sculpture, Current Location: Room c
                Artefact Name: Baroque Sculpture, Artefact Type:  Sculpture, Current Location: Room d
                Artefact Name: Classical Sculpture, Artefact Type:  Sculpture, Current Location: Room a
                Artefact Name: Gothic Sculpture, Artefact Type:  Sculpture, Current Location: Room b
                Artefact Name: Eclectic Sculpture, Artefact Type:  Sculpture, Current Location: Room c""", theInventory.SearchArtefactType("Sculpture", true));
    }

    @Test
    @Order(5)
    void listArtefacts() throws Exception
    {
        assertEquals("Dining Table\nCoffee Table\nSide Table\nPedestal Table\nConsole Table\nClay Pot\nCeramic Vase\nPorcelain Jar\nGlass Bowl\nMetal Canister\nAbstract Sculpture\nBaroque Sculpture\nClassical Sculpture\nGothic Sculpture\nEclectic Sculpture\nAbstract Painting\nBaroque Painting\nClassical Painting\nGothic Painting\nEclectic Painting", theInventory.ListAllArtefacts(true));
    }

    @Test
    @Order(6)
    void deleteArtefact() throws Exception
    {
        assertTrue(DataBase.deleteArtefact("Dining Table", true));
        assertTrue(DataBase.deleteArtefact("Ceramic Vase", true));
        assertTrue(DataBase.deleteArtefact("Baroque Sculpture", true));
        assertTrue(DataBase.deleteArtefact("Baroque Painting", true));
    }

    @Test
    @Order(7)
    void listArtefactsafterDeletion() throws Exception
    {
        assertEquals("Coffee Table\nSide Table\nPedestal Table\nConsole Table\nClay Pot\nPorcelain Jar\nGlass Bowl\nMetal Canister\nAbstract Sculpture\nClassical Sculpture\nGothic Sculpture\nEclectic Sculpture\nAbstract Painting\nClassical Painting\nGothic Painting\nEclectic Painting", theInventory.ListAllArtefacts(true));
    }

    @Test
    @Order(8)
    void searchRoomAfterDeletion() throws Exception
    {
        assertEquals("Artefact Name: Console Table, Artefact Type:  Furniture, Current Location: Room a", theInventory.SearchArtefactByName("Console Table", true));
        assertEquals("Artefact Name: Porcelain Jar, Artefact Type:  Pottery, Current Location: Room d", theInventory.SearchArtefactByName("Porcelain Jar", true));
        assertEquals("Artefact Name: Abstract Sculpture, Artefact Type:  Sculpture, Current Location: Room c", theInventory.SearchArtefactByName("Abstract Sculpture", true));
        assertEquals("Artefact Name: Coffee Table, Artefact Type:  Furniture, Current Location: Room b\n" +
                "Artefact Name: Clay Pot, Artefact Type:  Pottery, Current Location: Room b\n" +
                "Artefact Name: Metal Canister, Artefact Type:  Pottery, Current Location: Room b\n" +
                "Artefact Name: Gothic Sculpture, Artefact Type:  Sculpture, Current Location: Room b\n" +
                "Artefact Name: Classical Painting, Artefact Type:  Painting, Current Location: Room b", theInventory.SearchArtefactRoom("Room B", true));
        assertEquals("Artefact Name: Side Table, Artefact Type:  Furniture, Current Location: Room c\n" +
                "Artefact Name: Abstract Sculpture, Artefact Type:  Sculpture, Current Location: Room c\n" +
                "Artefact Name: Eclectic Sculpture, Artefact Type:  Sculpture, Current Location: Room c\n" +
                "Artefact Name: Gothic Painting, Artefact Type:  Painting, Current Location: Room c", theInventory.SearchArtefactRoom("Room c", true));
        assertEquals("Artefact Name: Pedestal Table, Artefact Type:  Furniture, Current Location: Room d\n" +
                "Artefact Name: Porcelain Jar, Artefact Type:  Pottery, Current Location: Room d\n" +
                "Artefact Name: Abstract Painting, Artefact Type:  Painting, Current Location: Room d\n" +
                "Artefact Name: Eclectic Painting, Artefact Type:  Painting, Current Location: Room d", theInventory.SearchArtefactRoom("Room d", true));
        assertEquals("""
                Artefact Name: Coffee Table, Artefact Type:  Furniture, Current Location: Room b
                Artefact Name: Side Table, Artefact Type:  Furniture, Current Location: Room c
                Artefact Name: Pedestal Table, Artefact Type:  Furniture, Current Location: Room d
                Artefact Name: Console Table, Artefact Type:  Furniture, Current Location: Room a""", theInventory.SearchArtefactType("Furniture", true));
        assertEquals("""
                Artefact Name: Clay Pot, Artefact Type:  Pottery, Current Location: Room b
                Artefact Name: Porcelain Jar, Artefact Type:  Pottery, Current Location: Room d
                Artefact Name: Glass Bowl, Artefact Type:  Pottery, Current Location: Room a
                Artefact Name: Metal Canister, Artefact Type:  Pottery, Current Location: Room b""", theInventory.SearchArtefactType("Pottery", true));
        assertEquals("""
                Artefact Name: Abstract Sculpture, Artefact Type:  Sculpture, Current Location: Room c
                Artefact Name: Classical Sculpture, Artefact Type:  Sculpture, Current Location: Room a
                Artefact Name: Gothic Sculpture, Artefact Type:  Sculpture, Current Location: Room b
                Artefact Name: Eclectic Sculpture, Artefact Type:  Sculpture, Current Location: Room c""", theInventory.SearchArtefactType("Sculpture", true));
    }

    @Test
    @Order(9)
    void clearArtefacts() throws Exception
    {
        assertTrue(DataBase.clearArtefacts(true));
    }

    @Test
    @Order(10)
    void addLoans() throws Exception
    {
        assertTrue(DataBase.addLoan(Loan1, true));
        assertTrue(DataBase.addLoan(Loan2, true));
        assertTrue(DataBase.addLoan(Loan3, true));
        assertTrue(DataBase.addLoan(Loan4, true));
        assertTrue(DataBase.addLoan(Loan5, true));
    }

    @Test
    @Order(11)
    void searchLoans() throws Exception
    {
        assertEquals("john@example.com", DataBase.searchLoans("John Doe", true));
    }

    @Test
    @Order(12)
    void clearLoans() throws Exception
    {
        assertTrue(DataBase.clearLoans(true));
    }

    @Test
    @Order(13)
    void searchRooms() throws Exception
    {
        assertEquals("room b", DataBase.searchRoomByName("Medieval", true));
    }

    @Test
    @Order(14)
    void listArtefactsafterClear() throws Exception
    {
        assertEquals("", theInventory.ListAllArtefacts(true));
    }

    @Test
    @Order(15)
    void clearRooms() throws Exception
    {
        assertTrue(DataBase.clearRooms(true));
    }
}