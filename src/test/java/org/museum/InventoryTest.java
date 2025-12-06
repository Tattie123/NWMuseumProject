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

        DataBase.clearArtefacts();
    }

    @Test
    void getInstance()
    {
        Inventory inventory1 = Inventory.getInstance();
        Inventory inventory2 = Inventory.getInstance();
        assertSame(inventory1, inventory2, "should be the same");
    }

    @Test
    void addArtefact()
    {
        Painting painting = new Painting("Historic Era", "Style", "Origin Country", "Current Room", "Author", null, 10.0, 20.0, 1000.0, "name");
        assertNotNull(painting, "painting is not null");
        assertTrue(Inventory.addArtefact(painting), "addArtefact should return true");
        assertTrue(Inventory.getInstance().getArtifacts().contains(painting), "Inventory should have the correct painting");
    }

    @Test
    void searchArtefactByName_usesDBPull() throws Exception
    {
        // Integration: add to DB, then Inventory.SearchArtefactByName should pull instances and find it
        Painting dbPainting = new Painting("Era", "Style", "Origin", "DBRoom", "Author", java.sql.Date.valueOf("2000-01-01"), 1.0, 2.0, 500.0, "DB Painting");
        assertTrue(DataBase.addArtefact(dbPainting), "should add artefact to DB");
        String result = Inventory.getInstance().SearchArtefactByName("DB Painting");
        assertNotNull(result);
        assertTrue(result.contains("DB Painting"), "Search result should contain artefact name");
    }
}