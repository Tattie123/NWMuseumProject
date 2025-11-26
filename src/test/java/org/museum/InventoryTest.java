package org.museum;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest
{

    @org.junit.jupiter.api.Test
    void getInstance()
    {
        Inventory inventory1 = Inventory.getInstance();
        Inventory inventory2 = Inventory.getInstance();
        assertSame(inventory1, inventory2, "Both instances should be the same (singleton pattern)");
    }
}