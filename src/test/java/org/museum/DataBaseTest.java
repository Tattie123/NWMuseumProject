package org.museum;

import org.junit.jupiter.api.*;
import org.museum.artefacts.Material;
import org.museum.artefacts.Painting;
import org.museum.artefacts.artefacts3d.Artefact3D;
import org.museum.artefacts.artefacts3d.Furniture;
import org.museum.artefacts.artefacts3d.Pottery;
import org.museum.artefacts.artefacts3d.Sculpture;
import org.museum.data.DataBase;
import org.museum.data.Inventory;
import org.museum.other.Loan;
import org.museum.other.Room;

import java.awt.image.BufferedImage;
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

    Room Room1;
    Room Room2;
    Room Room3;
    Room Room4;
    Room Room5;

    @BeforeAll
    static void beforeAll() throws Exception {
        Connection conn = DataBase.getConnection();

        // Run schema (required in CI)
        InputStream in = DataBaseTest.class.getClassLoader().getResourceAsStream("schema.sql");
        if (in != null) {
            String sql = new String(in.readAllBytes());
            conn.createStatement().execute(sql);
        }
    }


    @BeforeEach
    void setUp()
    {
        Table1 = new Furniture("Medieval", "Gothic", "England", "Room A", "Jim", java.sql.Date.valueOf("2000-01-01"), 0.75, 0.5, 3.75, 1.3, "Dining Table", Material.WOOD);
        Table2 = new Furniture("Renaissance", "Baroque", "Italy", "Room B", "Bob", java.sql.Date.valueOf("2000-05-15"), 1.0, 0.8, 4.0, 1.5, "Coffee Table", Material.STONE);
        Table3 = new Furniture("Modern", "Contemporary", "USA", "Room C", "Alice", java.sql.Date.valueOf("2001-08-10"), 1.2, 0.6, 3.5, 1.2, "Side Table", Material.METAL);
        Table4 = new Furniture("Ancient", "Classical", "Greece", "Room D", "Dave", java.sql.Date.valueOf("2002-11-05"), 1.8, 0.9, 4.5, 1.8, "Pedestal Table", Material.WOOD);
        Table5 = new Furniture("Victorian", "Eclectic", "England", "Room E", "Jerry", java.sql.Date.valueOf("2003-01-20"), 1.4, 0.7, 3.0, 1.0, "Console Table", Material.STONE);

        Pot1 = new Pottery("Ancient", "Classical", "Egypt", "Room F", "Sara", java.sql.Date.valueOf("1999-03-12"), 0.3, 0.5, 2.0, 0.4, "Clay Pot", Material.STONE);
        Pot2 = new Pottery("Medieval", "Gothic", "France", "Room G", "Tom", java.sql.Date.valueOf("2000-06-18"), 0.4, 0.6, 2.5, 0.5, "Ceramic Vase", Material.STONE);
        Pot3 = new Pottery("Renaissance", "Baroque", "Italy", "Room H", "Lily", java.sql.Date.valueOf("2001-09-22"), 0.5, 0.7, 3.0, 0.6, "Porcelain Jar", Material.STONE);
        Pot4 = new Pottery("Modern", "Contemporary", "USA", "Room I", "Mark", java.sql.Date.valueOf("2002-12-30"), 0.6, 0.8, 3.5, 0.7, "Glass Bowl", Material.METAL);
        Pot5 = new Pottery("Victorian", "Eclectic", "England", "Room J", "Nina", java.sql.Date.valueOf("2003-04-14"), 0.7, 0.9, 4.0, 0.8, "Metal Canister", Material.METAL);

        Sculpture1 = new Sculpture("Modern", "Abstract", "USA", "Room K", "Alice", java.sql.Date.valueOf("2004-01-01"), 1.0, 1.5, 5.0, 2.0, "Abstract Sculpture", Material.METAL);
        Sculpture2 = new Sculpture("Renaissance", "Baroque", "Italy", "Room L", "Bob", java.sql.Date.valueOf("2004-05-15"), 1.2, 1.7, 6.0, 2.5, "Baroque Sculpture", Material.STONE);
        Sculpture3 = new Sculpture("Ancient", "Classical", "Greece", "Room M", "Charlie", java.sql.Date.valueOf("2004-08-10"), 1.5, 2.0, 7.0, 3.0, "Classical Sculpture", Material.WOOD);
        Sculpture4 = new Sculpture("Medieval", "Gothic", "England", "Room N", "Diana", java.sql.Date.valueOf("2004-11-05"), 1.3, 1.8, 5.5, 2.5, "Gothic Sculpture", Material.STONE);
        Sculpture5 = new Sculpture("Victorian", "Eclectic", "England", "Room A", "Ethan", java.sql.Date.valueOf("2005-01-20"), 1.4, 1.9, 6.5, 2.8, "Eclectic Sculpture", Material.METAL);

        Painting1 = new Painting("Modern", "Abstract", "USA", "Room O", "Alice", java.sql.Date.valueOf("2005-02-01"), 1.0, 1.5, 5.0, "Abstract Painting");
        Painting2 = new Painting("Renaissance", "Baroque", "Italy", "Room P", "Bob", java.sql.Date.valueOf("2005-03-15"), 1.2, 1.7, 6.0,"Baroque Painting");
        Painting3 = new Painting("Ancient", "Classical", "Greece", "Room Q", "Charlie", java.sql.Date.valueOf("2005-04-10"), 1.5, 2.0, 7.0,"Classical Painting");
        Painting4 = new Painting("Medieval", "Gothic", "England", "Room R", "Diana", java.sql.Date.valueOf("2005-05-05"), 1.3, 1.8, 5.5, "Gothic Painting");
        Painting5 = new Painting("Victorian", "Eclectic", "England", "Room S", "Ethan", java.sql.Date.valueOf("2005-06-20"), 1.4, 1.9, 6.5, "Eclectic Painting");

        Loan1 = new Loan(false, "John Doe", "john@example.com", "1234567890", "Abstract Sculpture", java.sql.Date.valueOf("2023-01-01"), java.sql.Date.valueOf("2023-01-15"));
        Loan2 = new Loan(false, "Jane Smith", "jane@example.com", "9876543210", "Baroque Painting", java.sql.Date.valueOf("2023-02-01"), java.sql.Date.valueOf("2023-02-15"));
        Loan3 = new Loan(false, "Alice Johnson", "alice@example.com", "5555555555", "Classical Sculpture", java.sql.Date.valueOf("2023-03-01"), java.sql.Date.valueOf("2023-03-15"));
        Loan4 = new Loan(false, "Bob Brown", "bob@example.com", "4444444444", "Gothic Painting", java.sql.Date.valueOf("2023-04-01"), java.sql.Date.valueOf("2023-04-15"));
        Loan5 = new Loan(false, "Charlie Davis", "charlie@example.com", "3333333333", "Eclectic Sculpture", java.sql.Date.valueOf("2023-05-01"), java.sql.Date.valueOf("2023-05-15"));

        Room1 = new Room("Room A", "Ancient Artifacts", 50);
        Room2 = new Room("Room B", "Medieval Wonders", 40);
        Room3 = new Room("Room C", "Renaissance Gallery", 60);
        Room4 = new Room("Room D", "Modern Art", 70);
        Room5 = new Room("Room E", "Contemporary Creations", 80);
    }

    @Test
    @Order(1)
    void getConnection() throws Exception
    {
        assertNotNull(DataBase.getConnection());
    }

    @Test
    @Order(2)
    void addArtefacts() throws Exception
    {
        assertTrue(DataBase.addArtefact(Table1));
        assertTrue(DataBase.addArtefact(Table2));
        assertTrue(DataBase.addArtefact(Table3));
        assertTrue(DataBase.addArtefact(Table4));
        assertTrue(DataBase.addArtefact(Table5));
        assertTrue(DataBase.addArtefact(Pot1));
        assertTrue(DataBase.addArtefact(Pot2));
        assertTrue(DataBase.addArtefact(Pot3));
        assertTrue(DataBase.addArtefact(Pot4));
        assertTrue(DataBase.addArtefact(Pot5));
        assertTrue(DataBase.addArtefact(Sculpture1));
        assertTrue(DataBase.addArtefact(Sculpture2));
        assertTrue(DataBase.addArtefact(Sculpture3));
        assertTrue(DataBase.addArtefact(Sculpture4));
        assertTrue(DataBase.addArtefact(Sculpture5));
        assertTrue(DataBase.addArtefact(Painting1));
        assertTrue(DataBase.addArtefact(Painting2));
        assertTrue(DataBase.addArtefact(Painting3));
        assertTrue(DataBase.addArtefact(Painting4));
        assertTrue(DataBase.addArtefact(Painting5));
    }

    @Test
    @Order(3)
    void searchArtefacts() throws Exception
    {
        assertEquals("Furniture, Room E", theInventory.SearchArtefactByName("Console Table"));
        assertEquals("Pottery, Room H", theInventory.SearchArtefactByName("Porcelain Jar"));
        assertEquals("Sculpture, Room K", theInventory.SearchArtefactByName("Abstract Sculpture"));
        assertEquals("Coffee Table", theInventory.SearchArtefactNameByRoom("Room B"));
        assertEquals("Glass Bowl", theInventory.SearchArtefactNameByRoom("Room I"));
        assertEquals("Abstract Sculpture", theInventory.SearchArtefactNameByRoom("Room K"));
        assertEquals("""
                Dining Table
                Coffee Table
                Side Table
                Pedestal Table
                Console Table""", theInventory.SearchArtefactNameByType("Furniture"));
        assertEquals("""
                Clay Pot
                Ceramic Vase
                Porcelain Jar
                Glass Bowl
                Metal Canister""", theInventory.SearchArtefactNameByType("Pottery"));
        assertEquals("""
                Abstract Sculpture
                Baroque Sculpture
                Classical Sculpture
                Gothic Sculpture
                Eclectic Sculpture""", theInventory.SearchArtefactNameByType("Sculpture"));
    }


    @Test
    @Order(4)
    void deleteArtefact() throws Exception
    {
        assertTrue(DataBase.deleteArtefact("Dining Table"));
        assertTrue(DataBase.deleteArtefact("Ceramic Vase"));
        assertTrue(DataBase.deleteArtefact("Baroque Sculpture"));
        assertTrue(DataBase.deleteArtefact("Baroque Painting"));
    }

    @Test
    @Order(5)
    void searchRoomAfterDeletion() throws Exception
    {
        assertEquals("Furniture, Room E", theInventory.SearchArtefactByName("Console Table"));
        assertEquals("Pottery, Room H", theInventory.SearchArtefactByName("Porcelain Jar"));
        assertEquals("Sculpture, Room K", theInventory.SearchArtefactByName("Abstract Sculpture"));
        assertEquals("Coffee Table", theInventory.SearchArtefactNameByRoom("Room B"));
        assertEquals("Glass Bowl", theInventory.SearchArtefactNameByRoom("Room I"));
        assertEquals("Abstract Sculpture", theInventory.SearchArtefactNameByRoom("Room K"));
        assertEquals("""
                Coffee Table
                Side Table
                Pedestal Table
                Console Table""", theInventory.SearchArtefactNameByType("Furniture"));
        assertEquals("""
                Clay Pot
                Porcelain Jar
                Glass Bowl
                Metal Canister""", theInventory.SearchArtefactNameByType("Pottery"));
        assertEquals("""
                Abstract Sculpture
                Classical Sculpture
                Gothic Sculpture
                Eclectic Sculpture""", theInventory.SearchArtefactNameByType("Sculpture"));
    }

    @Test
    @Order(6)
    void clearArtefacts() throws Exception
    {
        assertTrue(DataBase.clearArtefacts());
    }

    @Test
    @Order(7)
    void addLoans() throws Exception
    {
        assertTrue(DataBase.addLoan(Loan1));
        assertTrue(DataBase.addLoan(Loan2));
        assertTrue(DataBase.addLoan(Loan3));
        assertTrue(DataBase.addLoan(Loan4));
        assertTrue(DataBase.addLoan(Loan5));
    }

    @Test
    @Order(8)
    void searchLoans() throws Exception
    {
        assertEquals("john@example.com", DataBase.searchLoans("John Doe"));
    }

    @Test
    @Order(9)
    void clearLoans() throws Exception
    {
        assertTrue(DataBase.clearLoans());
    }

    @Test
    @Order(10)
    void addRooms() throws Exception
    {
        assertTrue(DataBase.addRoom(Room1));
        assertTrue(DataBase.addRoom(Room2));
        assertTrue(DataBase.addRoom(Room3));
        assertTrue(DataBase.addRoom(Room4));
        assertTrue(DataBase.addRoom(Room5));
    }

    @Test
    @Order(11)
    void searchRooms() throws Exception
    {
        assertEquals("Room B", DataBase.searchRoomByName("Medieval"));
    }

    @Test
    @Order(12)
    void clearRooms() throws Exception
    {
        assertTrue(DataBase.clearRooms());
    }
}