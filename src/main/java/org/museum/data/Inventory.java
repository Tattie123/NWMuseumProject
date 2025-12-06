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

    public String SearchArtefactByName(String name) throws Exception
    {
        UpdateArtefactsFromDB();

        if (this.Artifacts == null || this.Artifacts.isEmpty())
            return "Artefact not found.";

        for (Artefact artefact : this.Artifacts)
        {
            if (artefact != null && artefact.getName() != null && artefact.getName().equalsIgnoreCase(name))
            {
                return artefact.toString();
            }
        }
        return "Artefact not found.";
    }

    private boolean UpdateArtefactsFromDB() throws Exception
    {
        List<Artefact> artefactsFromDB = DataBase.PullArtefacts();
        if (artefactsFromDB != null)
        {
            this.Artifacts = artefactsFromDB;
            return true;
        }
        if (this.Artifacts == null)
            this.Artifacts = new ArrayList<>();
        return false;
    }

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

    public String SearchArtefactByRoom(String roomName) throws Exception
    {

    }
}
