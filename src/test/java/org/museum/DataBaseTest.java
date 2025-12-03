package org.museum;

import org.junit.jupiter.api.*;
import org.museum.artefacts.Material;
import org.museum.artefacts.Painting;
import org.museum.artefacts.artefacts3d.Artefact3D;
import org.museum.artefacts.artefacts3d.Furniture;
import org.museum.artefacts.artefacts3d.Pottery;
import org.museum.artefacts.artefacts3d.Sculpture;
import org.museum.data.DataBase;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DataBaseTest
{
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

        Table1 = new Furniture("Medieval", "Gothic", "England", "Room A", "Jim", java.sql.Date.valueOf("2000-01-01"), 0.75, 0.5, image, 3.75, 1.3, "Dining Table", Material.Wood);
        Table2 = new Furniture("Renaissance", "Baroque", "Italy", "Room B", "Bob", java.sql.Date.valueOf("2000-05-15"), 1.0, 0.8, image, 4.0, 1.5, "Coffee Table", Material.Stone);
        Table3 = new Furniture("Modern", "Contemporary", "USA", "Room C", "Alice", java.sql.Date.valueOf("2001-08-10"), 1.2, 0.6, image, 3.5, 1.2, "Side Table", Material.Metal);
        Table4 = new Furniture("Ancient", "Classical", "Greece", "Room D", "Dave", java.sql.Date.valueOf("2002-11-05"), 1.8, 0.9, image, 4.5, 1.8, "Pedestal Table", Material.Wood);
        Table5 = new Furniture("Victorian", "Eclectic", "England", "Room E", "Jerry", java.sql.Date.valueOf("2003-01-20"), 1.4, 0.7, image, 3.0, 1.0, "Console Table", Material.Stone);

        Pot1 = new Pottery("Ancient", "Classical", "Egypt", "Room F", "Sara", java.sql.Date.valueOf("1999-03-12"), 0.3, 0.5, image, 2.0, 0.4, "Clay Pot", Material.Stone);
        Pot2 = new Pottery("Medieval", "Gothic", "France", "Room G", "Tom", java.sql.Date.valueOf("2000-06-18"), 0.4, 0.6, image, 2.5, 0.5, "Ceramic Vase", Material.Stone);
        Pot3 = new Pottery("Renaissance", "Baroque", "Italy", "Room H", "Lily", java.sql.Date.valueOf("2001-09-22"), 0.5, 0.7, image, 3.0, 0.6, "Porcelain Jar", Material.Stone);
        Pot4 = new Pottery("Modern", "Contemporary", "USA", "Room I", "Mark", java.sql.Date.valueOf("2002-12-30"), 0.6, 0.8, image, 3.5, 0.7, "Glass Bowl", Material.Metal);
        Pot5 = new Pottery("Victorian", "Eclectic", "England", "Room J", "Nina", java.sql.Date.valueOf("2003-04-14"), 0.7, 0.9, image, 4.0, 0.8, "Metal Canister", Material.Metal);

        Sculpture1 = new Sculpture("Modern", "Abstract", "USA", "Room K", "Alice", java.sql.Date.valueOf("2004-01-01"), 1.0, 1.5, image, 5.0, 2.0, "Abstract Sculpture", Material.Metal);
        Sculpture2 = new Sculpture("Renaissance", "Baroque", "Italy", "Room L", "Bob", java.sql.Date.valueOf("2004-05-15"), 1.2, 1.7, image, 6.0, 2.5, "Baroque Sculpture", Material.Stone);
        Sculpture3 = new Sculpture("Ancient", "Classical", "Greece", "Room M", "Charlie", java.sql.Date.valueOf("2004-08-10"), 1.5, 2.0, image, 7.0, 3.0, "Classical Sculpture", Material.Wood);
        Sculpture4 = new Sculpture("Medieval", "Gothic", "England", "Room N", "Diana", java.sql.Date.valueOf("2004-11-05"), 1.3, 1.8, image, 5.5, 2.5, "Gothic Sculpture", Material.Stone);
        Sculpture5 = new Sculpture("Victorian", "Eclectic", "England", "Room A", "Ethan", java.sql.Date.valueOf("2005-01-20"), 1.4, 1.9, image, 6.5, 2.8, "Eclectic Sculpture", Material.Metal);

        Painting1 = new Painting("Modern", "Abstract", "USA", "Room O", "Alice", java.sql.Date.valueOf("2005-02-01"), 1.0, 1.5, image, 5.0, "Abstract Painting");
        Painting2 = new Painting("Renaissance", "Baroque", "Italy", "Room P", "Bob", java.sql.Date.valueOf("2005-03-15"), 1.2, 1.7, image, 6.0,"Baroque Painting");
        Painting3 = new Painting("Ancient", "Classical", "Greece", "Room Q", "Charlie", java.sql.Date.valueOf("2005-04-10"), 1.5, 2.0, image, 7.0,"Classical Painting");
        Painting4 = new Painting("Medieval", "Gothic", "England", "Room R", "Diana", java.sql.Date.valueOf("2005-05-05"), 1.3, 1.8, image, 5.5, "Gothic Painting");
        Painting5 = new Painting("Victorian", "Eclectic", "England", "Room S", "Ethan", java.sql.Date.valueOf("2005-06-20"), 1.4, 1.9, image, 6.5, "Eclectic Painting");
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
    void SearchRoom() throws Exception
    {
        assertEquals("Room C", DataBase.searchArtefactRoom("Side Table"));
        assertEquals("Room F", DataBase.searchArtefactRoom("Clay Pot"));
        assertEquals("Room K", DataBase.searchArtefactRoom("Abstract Sculpture"));
        assertEquals("Room O", DataBase.searchArtefactRoom("Abstract Painting"));
    }

    @Test
    @Order(4)
    void SearchType() throws Exception
    {
        assertEquals("Dining Table, Coffee Table, Side Table, Pedestal Table, Console Table", DataBase.searchArtefactsWithType("Furniture"));
        assertEquals("Clay Pot, Ceramic Vase, Porcelain Jar, Glass Bowl, Metal Canister", DataBase.searchArtefactsWithType("Pottery"));
        assertEquals("Abstract Sculpture, Baroque Sculpture, Classical Sculpture, Gothic Sculpture, Eclectic Sculpture", DataBase.searchArtefactsWithType("Sculpture"));
        assertEquals("Abstract Painting, Baroque Painting, Classical Painting, Gothic Painting, Eclectic Painting", DataBase.searchArtefactsWithType("Painting"));
    }


    @Test
    @Order(5)
    void deleteArtefact() throws Exception
    {
        assertTrue(DataBase.deleteArtefact("Dining Table"));
        assertTrue(DataBase.deleteArtefact("Ceramic Vase"));
        assertTrue(DataBase.deleteArtefact("Baroque Sculpture"));
        assertTrue(DataBase.deleteArtefact("Baroque Painting"));
    }

    @Test
    @Order(6)
    void SearchRoomAfterDeletion() throws Exception
    {
        assertNull(DataBase.searchArtefactRoom("Dining Table"));
        assertNull(DataBase.searchArtefactRoom("Ceramic Vase"));
        assertNull(DataBase.searchArtefactRoom("Baroque Sculpture"));
        assertNull(DataBase.searchArtefactRoom("Baroque Painting"));
    }

    @Test
    @Order(7)
    void clearArtefact() throws Exception
    {
        assertTrue(DataBase.clearArtefacts());
    }


}