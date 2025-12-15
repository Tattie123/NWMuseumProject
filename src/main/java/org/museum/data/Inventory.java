package org.museum.data;

import org.museum.other.Room;
import org.museum.artefacts.Artefact;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public final class Inventory
{
    private static Inventory instance;
    private List <Artefact> Artifacts;
    private final List<Room> Rooms;

    public void DeleteArtefactByName(String name, boolean testMode) throws Exception
    {
        UpdateArtefactsFromDB(testMode);

        if (this.Artifacts == null || this.Artifacts.isEmpty())
            throw new Exception("No Artefact found with that name.");

        for (Artefact artefact : this.Artifacts)
        {
            if (artefact != null && artefact.getName() != null && artefact.getName().equalsIgnoreCase(name))
            {
                this.Artifacts.remove(artefact);
                DataBase.deleteArtefact(name, testMode);
                return;
            }
        }
        throw new Exception("No Artefact found with that name.");
    }

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

    public static Boolean addArtefact(Artefact artefact, boolean testMode) throws Exception
    {
        if (artefact == null) return false;

        Inventory inv = getInstance();

        if (inv.Artifacts == null)
        {
            inv.Artifacts = new ArrayList<>();
        }
        try
        {
            inv.Artifacts.add(artefact);
            DataBase.addArtefact(artefact, testMode);
        } catch (Exception e)
        {
            throw new Exception("Failed to add artefact to database: " + e.getMessage());
        }
        return true;
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

    public void ViewImagesOfArtefact(List<BufferedImage> images) throws Exception
    {

        JFrame frame = new JFrame("Image Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

            for (int i = 0; i < images.size(); i++)
            {
                BufferedImage img = images.get(i);
                ImageIcon icon = new ImageIcon(img);
                JLabel label = new JLabel(icon);
                JPanel panel = new JPanel();
                panel.setBackground(Color.LIGHT_GRAY);
                panel.add(label);
                cardPanel.add(panel, "Panel" + (i + 1));
            }

            JButton nextButton = new JButton("Next");
            nextButton.addActionListener(e -> cardLayout.next(cardPanel));

            JButton prevButton = new JButton("Previous");
            prevButton.addActionListener(e -> cardLayout.previous(cardPanel));

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(prevButton);
            buttonPanel.add(nextButton);

            frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.add(cardPanel);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.toFront();
    }

    public String ListAllArtefacts(boolean b) throws Exception
    {
        StringBuilder result = new StringBuilder();
        UpdateArtefactsFromDB(b);
        for (Artefact artefact : this.Artifacts)
        {
            if (artefact.getName() != null)
            {
                result.append(artefact.getName()).append("\n");
            }
        }
        return result.toString().strip();
    }

    public String ListAllRooms(boolean testMode)
    {
        try
        {
            List<Room> rooms = DataBase.PullRooms(testMode);
            StringBuilder roomNames = new StringBuilder("Rooms:\n");
            assert rooms != null;
            for (Room room : rooms)
            {
                roomNames.append(room.getName()).append("\n");
            }
            if (roomNames.toString().equals("Rooms:\n"))
            {
                return "No rooms found.";
            }
            else
            {
                return roomNames.toString();
            }
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
