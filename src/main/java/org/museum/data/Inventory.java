package org.museum.data;

import org.museum.other.Room;
import org.museum.artefacts.Artefact;

import java.util.ArrayList;
import java.util.List;

public final class Inventory
{
    private static Inventory instance;
    private List <Artefact> Artifacts;
    private List<Room> Rooms;

    private Inventory()
    {
        // initialize collections
        this.Artifacts = new ArrayList<>();
        this.Rooms = new ArrayList<>();
    }

    public static Inventory getInstance()
    {
        if (instance == null)
        {
            instance = new Inventory();
        }
        return instance;
    }

    public static Boolean addArtefact(Artefact artefact)
    {
        if (artefact == null) return false;

        Inventory inv = getInstance();

        if (inv.Artifacts == null)
        {
            inv.Artifacts = new ArrayList<>();
        }
        boolean added = inv.Artifacts.add(artefact);
        return added;
    }

    public List<Artefact> getArtifacts()
    {
        return this.Artifacts;
    }
}
