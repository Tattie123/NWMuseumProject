package org.museum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.museum.artefacts.Artefact;
import org.museum.artefacts.Painting;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest
{
    Inventory inventory1 = Inventory.getInstance();
    Inventory inventory2 = Inventory.getInstance();

    @BeforeAll
    static void beforeAll()
    {

    }

    @Test
    void getInstance()
    {
        assertSame(inventory1, inventory2, "should be the same");
    }

    @Test
    void addArtefact()
    {
        Painting painting = new Painting("Historic Era", "Style", "Origin Country", "Current Room", "Author", null, 10.0, 20.0, null, 1000.0);
        assertNotNull(painting, "painting is not null");
        assertTrue(inventory1.getArtifacts().contains(painting), "Inventory should have the correct painting");
    }
}