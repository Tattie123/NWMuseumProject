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

    /**
     * Search artefacts by name (case-insensitive substring match) and return a textual result.
     *
     * @param name artefact name or substring
     * @param testMode use test DB if true
     * @return formatted search results
     * @throws Exception if no artefacts found or DB issues occur
     */
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

    /**
     * Refresh the in-memory artefact list from the database.
     *
     * @param testMode use test DB if true
     * @throws Exception on DB errors
     */
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

    /**
     * Private constructor for singleton Inventory.
     */
    private Inventory()
    {
        // initialize collections
        this.Artifacts = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
    }

    /**
     * Get the singleton Inventory instance.
     *
     * @return Inventory singleton
     */
    public static Inventory getInstance()
    {
        if (instance == null)
        {
            instance = new Inventory();
        }
        return instance;
    }

    /**
     * Add an artefact to the in-memory list (does not persist to DB).
     *
     * @param artefact artefact instance
     * @return true if added
     */
    public static Boolean addArtefact(Artefact artefact)
    {
        if (artefact == null) return false;

        Inventory inv = getInstance();

        if (inv.Artifacts == null)
        {
            inv.Artifacts = new ArrayList<>();
        }
        return inv.Artifacts.add(artefact);
    }

    /**
     * Return current in-memory artefacts list (may be empty).
     *
     * @return list of Artefact instances
     */
    public List<Artefact> getArtifacts()
    {
        return this.Artifacts;
    }

    /**
     * Search artefacts by current room (case-insensitive substring match).
     *
     * @param roomName room name or substring
     * @param testMode use test DB if true
     * @return formatted search results
     * @throws Exception if none found or DB issues occur
     */
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

    /**
     * Search artefacts by artefact type/class name (case-insensitive substring match).
     *
     * @param type artefact type name or substring
     * @param testMode use test DB if true
     * @return formatted search results
     * @throws Exception if none found or DB issues occur
     */
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

    /**
     * Find a single artefact by name (substring match) and return the first match.
     *
     * @param artefactName artefact name or substring
     * @param testMode use test DB if true
     * @return Artefact instance or null if none
     * @throws Exception on DB errors
     */
    public Artefact getArtefactByName(String artefactName, boolean testMode) throws Exception
    {
        UpdateArtefactsFromDB(testMode);

        for (Artefact artefact : this.Artifacts)
        {
            if (artefact != null && artefact.getName() != null && artefact.getName().toLowerCase().contains(artefactName.toLowerCase()))
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

    /**
     * List names of all artefacts in the inventory.
     *
     * @param b testMode flag passed to UpdateArtefactsFromDB
     * @return newline-separated artefact names
     * @throws Exception on DB errors
     */
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
}
