package org.museum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.museum.artefacts.Painting;
import org.museum.data.DataBase;
import org.museum.data.Inventory;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest
{
    @BeforeEach
    void setup() throws Exception
    {
        Inventory inv = Inventory.getInstance();
        if (inv.getArtifacts() != null)
            inv.getArtifacts().clear();

        DataBase.clearArtefacts(true);
    }

    @Test
    void getInstance()
    {
        Inventory inventory1 = Inventory.getInstance();
        Inventory inventory2 = Inventory.getInstance();
        assertSame(inventory1, inventory2, "should be the same");
    }

    @Test
    void addArtefact() throws Exception
    {
        Painting painting = new Painting("Historic Era", "Style", "Origin Country", "Current Room", "Author", java.sql.Date.valueOf("2000-01-01"), 10.0, 20.0, 1000.0, "name");
        assertNotNull(painting, "painting is not null");
        assertTrue(Inventory.addArtefact(painting), "addArtefact should return true");
        assertTrue(Inventory.getInstance().getArtifacts().contains(painting), "Inventory should have the correct painting");
    }
}