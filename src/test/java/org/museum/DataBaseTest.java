package org.museum;

import org.junit.jupiter.api.*;
import org.museum.artefacts.artefacts3d.Artefact3D;
import org.museum.artefacts.artefacts3d.Furniture;
import java.awt.image.BufferedImage;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DataBaseTest
{
    Artefact3D Table;

    @BeforeEach
    void setUp()
    {
        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Table = new Furniture("Medieval", "Gothic", "Scotland", "Room 1", "Unknown", new java.util.Date(), 2.0, 1.5, image, 5.0, 7.5);
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
        for (int i = 0; i < 10; i++)
            assertTrue(DataBase.addArtefact3d(Table));
    }

    @Test
    @Order(3)
    void clearArtefact3D() throws Exception
    {
        assertTrue(DataBase.clearArtefact3D());
    }
}