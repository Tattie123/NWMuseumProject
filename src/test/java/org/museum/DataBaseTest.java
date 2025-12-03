package org.museum;

import org.junit.jupiter.api.*;
import org.museum.artefacts.Material;
import org.museum.artefacts.artefacts3d.Artefact3D;
import org.museum.artefacts.artefacts3d.Furniture;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DataBaseTest
{
    Artefact3D Table1;
    Artefact3D Table2;
    Artefact3D Table3;
    Artefact3D Table4;
    Artefact3D Table5;

    @BeforeAll
    static void beforeAll() throws Exception {
        Connection conn = DataBase.getConnection();

        // Run schema (required in CI)
        InputStream in = DataBaseTest.class.getClassLoader().getResourceAsStream("schema.sql");
        if (in != null) {
            String sql = new String(in.readAllBytes());
            conn.createStatement().execute(sql);
        }

        DataBase.clearArtefacts();
    }


    @BeforeEach
    void setUp()
    {
        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);

        Table1 = new Furniture("Medieval", "Gothic", "England", "Room A", "Jim", new Date(1999, 2, 25), 0.75, 0.5, image, 3.75, 1.3, "Dining Table", Material.Wood);
        Table2 = new Furniture("Renaissance", "Baroque", "Italy", "Room B", "Bob", new Date(2000, 5, 15), 1.0, 0.8, image, 4.0, 1.5, "Coffee Table", Material.Stone);
        Table3 = new Furniture("Modern", "Contemporary", "USA", "Room C", "Alice", new Date(2001, 8, 10), 1.2, 0.6, image, 3.5, 1.2, "Side Table", Material.Metal);
        Table4 = new Furniture("Ancient", "Classical", "Greece", "Room D", "Dave", new Date(2002, 11, 5), 1.8, 0.9, image, 4.5, 1.8, "Pedestal Table", Material.Wood);
        Table5 = new Furniture("Victorian", "Eclectic", "England", "Room E", "Jerry", new Date(2003, 1, 20), 1.4, 0.7, image, 3.0, 1.0, "Console Table", Material.Stone);
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
    }

    @Test
    @Order(3)
    void SearchRoom() throws Exception
    {
        assertEquals("Room C", DataBase.searchArtefactRoom("Side Table"));
    }

    @Test
    @Order(4)
    void SearchType() throws Exception
    {
        assertEquals("Dining Table, Coffee Table, Side Table, Pedestal Table, Console Table", DataBase.searchArtefactsWithType("Furniture"));
    }


    @Test
    @Order(5)
    void deleteArtefact() throws Exception
    {
        assertTrue(DataBase.deleteArtefact("Dining Table"));
    }

    @Test
    @Order(6)
    void SearchRoomAfterDeletion() throws Exception
    {
        assertNull(DataBase.searchArtefactRoom("Dining Table"));
    }

    @Test
    @Order(7)
    void clearArtefact() throws Exception
    {
        assertTrue(DataBase.clearArtefacts());
    }


}