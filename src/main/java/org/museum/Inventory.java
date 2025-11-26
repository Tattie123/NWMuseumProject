package org.museum;

import org.museum.other.Room;
import org.museum.artefacts.Artefact;

import java.util.List;

public final class Inventory
{
    private static Inventory instance;
    private List <Artefact> Artifacts;
    private List<Room> Rooms;

    private Inventory()
    {
    }

    public static Inventory getInstance()
    {
        if (instance == null)
        {
            instance = new Inventory();
        }
        return instance;
    }
}
