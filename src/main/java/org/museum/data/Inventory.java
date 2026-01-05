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

    public String SearchArtefactByName(String name, boolean testMode) throws Exception
    {
        UpdateArtefactsFromDB(testMode);

        if (this.Artifacts == null || this.Artifacts.isEmpty())
            throw new Exception("No Artefacts found. Possible Database connection issue.");

        StringBuilder result = new StringBuilder();

        for (Artefact artefact : this.Artifacts)
        {
            if (artefact != null && artefact.getName() != null && artefact.getName().toLowerCase().contains(name.toLowerCase()))
            {
                result.append("Artefact Name: ").append(artefact.getName()).append(", Artefact Type:  ").append(artefact.getType()).append(", Current Location: ").append(artefact.getCurrentRoom()).append("\n");
            }
        }

        if (!result.isEmpty())
            return result.toString().strip();

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
        List<Room> rooms = new ArrayList<>();
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

    public String SearchArtefactRoom(String roomName, boolean testMode) throws Exception
    {
        UpdateArtefactsFromDB(testMode);

        if (this.Artifacts == null || this.Artifacts.isEmpty())
            throw new Exception("No Artefacts found. Possible Database connection issue.");

        StringBuilder result = new StringBuilder();

        for (Artefact artefact : this.Artifacts)
        {
            if (artefact != null && artefact.getCurrentRoom() != null && artefact.getCurrentRoom().toLowerCase().contains(roomName.toLowerCase()))
            {
                result.append("Artefact Name: ").append(artefact.getName()).append(", Artefact Type:  ").append(artefact.getType()).append(", Current Location: ").append(artefact.getCurrentRoom()).append("\n");
            }
        }

        if (!result.isEmpty())
            return result.toString().strip();

        throw new Exception("No Artefacts found.");
    }

    public String SearchArtefactType(String type, boolean testMode) throws Exception
    {
        UpdateArtefactsFromDB(testMode);

        if (this.Artifacts == null || this.Artifacts.isEmpty())
            throw new Exception("No Artefacts found. Possible Database connection issue.");

        StringBuilder result = new StringBuilder();

        for (Artefact artefact : this.Artifacts)
        {
            if (artefact != null && artefact.getType() != null && artefact.getType().toLowerCase().contains(type.toLowerCase()))
            {
                result.append("Artefact Name: ").append(artefact.getName()).append(", Artefact Type:  ").append(artefact.getType()).append(", Current Location: ").append(artefact.getCurrentRoom()).append("\n");
            }
        }

        if (!result.isEmpty())
            return result.toString().strip();

        throw new Exception("No Artefacts found.");
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
