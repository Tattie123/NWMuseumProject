package org.museum.data;

import org.museum.other.Room;
import org.museum.artefacts.Artefact;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public final class Inventory
{
    private static Inventory instance;
    private List <Artefact> Artifacts;
    private final List<Room> Rooms;

    public String SearchArtefactByName(String name, boolean testMode) throws Exception
    {
        UpdateArtefactsFromDB(testMode);

        if (this.Artifacts == null || this.Artifacts.isEmpty())
            throw new Exception("No Artefacts found.");

        for (Artefact artefact : this.Artifacts)
        {
            if (artefact != null && artefact.getName() != null && artefact.getName().equalsIgnoreCase(name))
            {
                return artefact.getType() + ", " + artefact.getCurrentRoom();
            }
        }
        throw new Exception("No Artefacts found.");
    }

    private void UpdateArtefactsFromDB(boolean testMode) throws Exception
    {
        List<Artefact> artefactsFromDB = DataBase.PullArtefacts(testMode);
        if (artefactsFromDB != null)
        {
            this.Artifacts = artefactsFromDB;
            return;
        }
        if (this.Artifacts == null)
            this.Artifacts = new ArrayList<>();
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

    public String SearchArtefactNameByRoom(String roomName, boolean testMode) throws Exception
    {
        UpdateArtefactsFromDB(testMode);

        if (this.Artifacts == null || this.Artifacts.isEmpty())
            throw new Exception("No Artefacts found.");

        StringBuilder result = new StringBuilder();
        for (Artefact artefact : this.Artifacts)
        {
            if (artefact != null && artefact.getCurrentRoom() != null && artefact.getCurrentRoom().equalsIgnoreCase(roomName))
            {
                result.append(artefact.getName()).append("\n");
            }
        }
        if (result.isEmpty())
        {
            throw new Exception("No Artefacts found.");
        }
        return result.toString().strip();

    }

    public String SearchArtefactNameByType(String type, boolean testMode) throws Exception
    {
        UpdateArtefactsFromDB(testMode);

        if (this.Artifacts == null || this.Artifacts.isEmpty())
            throw new Exception("No Artefacts found.");

        StringBuilder result = new StringBuilder();
        for (Artefact artefact : this.Artifacts)
        {
            if (artefact != null && artefact.getType() != null && artefact.getType().equalsIgnoreCase(type))
            {
                result.append(artefact.getName()).append("\n");
            }
        }
        if (result.isEmpty())
        {
            throw new Exception("No Artefacts found in specified room.");
        }
        return result.toString().strip();
    }

    public Artefact getArtefactByName(String artefactName, boolean testMode) throws Exception
    {
        UpdateArtefactsFromDB(testMode);

        for (Artefact artefact : this.Artifacts)
        {
            if (artefact != null && artefact.getName() != null && artefact.getName().equalsIgnoreCase(artefactName) || artefact.getName().contains(artefactName))
            {
                return artefact;
            }
        }
        return null;
    }

    public void ViewImagesOfArtefact(String artefactName, boolean testMode) throws Exception
    {
        final char[] asciiChars = {'@', '#', 'S', '%', '?', '*', '+', ';', ':', ',', '.'};

        Artefact artefact = getArtefactByName(artefactName, testMode);
        assert artefact != null;
        BufferedImage image = artefact.getImages();

        int newWidth = 100;
        int newHeight = (image.getHeight() * newWidth) / image.getWidth();
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
        for (int y = 0; y < newHeight; y++)
        {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < newWidth; x++)
            {
                Color pixel = new Color(resizedImage.getRGB(x, y));
                double gray = (pixel.getRed() * 0.3 + pixel.getGreen() * 0.59 + pixel.getBlue() * 0.11);
                int index = (int)((gray / 255) * (asciiChars.length - 1));
                sb.append(asciiChars[index]);
            }
            System.out.println(sb);
        }
    }

    public String ListAllArtefacts(boolean b) throws Exception
    {
        StringBuilder result = new StringBuilder();
        UpdateArtefactsFromDB(b);
        for (Artefact artefact : this.Artifacts)
        {
            if (artefact.getName() != null)
            {
                result.append(artefact.getName() + "\n");
            }
        }
        return result.toString().strip();
    }
}
