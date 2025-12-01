package org.museum;

import org.junit.jupiter.api.*;
import org.museum.artefacts.artefacts3d.Artefact3D;
import org.museum.artefacts.artefacts3d.Furniture;

import java.awt.image.BufferedImage;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DataBaseTest
{
    Artefact3D Table1;
    Artefact3D Table2;
    Artefact3D Table3;
    Artefact3D Table4;
    Artefact3D Table5;

    @BeforeEach
    void setUp()
    {
        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);

        Table1 = new Furniture("Medieval", "Gothic", "England", "Room A", "Wood", new Date(1999, 2, 25), 0.75, 0.5, image, 3.75, 1.3, "Dining Table");
        Table2 = new Furniture("Renaissance", "Baroque", "Italy", "Room B", "Marble", new Date(2000, 5, 15), 1.0, 0.8, image, 4.0, 1.5, "Coffee Table");
        Table3 = new Furniture("Modern", "Contemporary", "USA", "Room C", "Metal", new Date(2001, 8, 10), 1.2, 0.6, image, 3.5, 1.2, "Side Table");
        Table4 = new Furniture("Ancient", "Classical", "Greece", "Room D", "Stone", new Date(2002, 11, 5), 1.8, 0.9, image, 4.5, 1.8, "Pedestal Table");
        Table5 = new Furniture("Victorian", "Eclectic", "England", "Room E", "Glass", new Date(2003, 1, 20), 1.4, 0.7, image, 3.0, 1.0, "Console Table");
    }

    @Test
    @Order(1)
    void getConnection() throws Exception
    {
        assertNotNull(DataBase.getConnection());
    }

    @Test
    @Order(2)
    void addArtefact3D() throws Exception
    {
        assertTrue(DataBase.addArtefact3d(Table1));
        assertTrue(DataBase.addArtefact3d(Table2));
        assertTrue(DataBase.addArtefact3d(Table3));
        assertTrue(DataBase.addArtefact3d(Table4));
        assertTrue(DataBase.addArtefact3d(Table5));
    }

    @Test
    @Order(3)
    void searchArtefact3D() throws Exception
    {
        assertNotNull(DataBase.searchArtefact3D("Dining Table"));
    }

    @Test
    @Order(4)
    void deleteArtefact3D() throws Exception
    {
        assertTrue(DataBase.deleteArtefact3D("Dining Table"));
    }

    @Test
    @Order(5)
    void clearArtefact3D() throws Exception
    {
        assertTrue(DataBase.clearArtefact3D());
    }


}