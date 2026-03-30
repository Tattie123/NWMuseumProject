package org.museum.data;

import org.museum.other.Loan;
import org.museum.other.Room;
import org.museum.artefacts.Artefact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public final class Inventory
{
    private static Inventory instance;
    private List <Artefact> Artifacts;
    private List <Loan> Loans;
    private List <Room> Rooms;

    public static List<Loan> getLoans(boolean b)
    {
        DataBase.PullLoans(b);
        Inventory inv = getInstance();
        if (inv.Loans == null || inv.Loans.isEmpty())
        {
            inv.Loans = new ArrayList<>();
        }
        return inv.Loans;
    }

    public static boolean moveArtefactToRoom(String artefactName, String roomName, boolean testMode) throws Exception
    {
        // Ensure artefacts are loaded
        DataBase.PullArtefacts(testMode);
        Inventory inv = Inventory.getInstance();

        // roomName argument may be roomNum or roomName; try to resolve to roomNum
        String roomNum = roomName;
        try {
            // if provided a room name that isn't a number, search by name
            Room r = null;
            try {
                r = DataBase.getRoomFromName(roomName, testMode);
            } catch (Exception ignored) {
                // try fallback: assume roomName is actually a roomNum
            }
            if (r != null) roomNum = r.roomNum();
        } catch (Exception ignored) {}

        // ensure room has space
        Room target = DataBase.getRoomFromName(roomNum, testMode);
        if (target.getCurrentSpace(testMode) <= 0)
            throw new Exception("Room is at full capacity.");

        // update DB
        boolean updated = DataBase.updateArtefactRoom(artefactName, roomNum, testMode);
        if (updated) {
            // refresh in-memory artefacts
            Inventory.getInstance().UpdateArtefactsFromDB(testMode);
            return true;
        }
        return false;
    }

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
    public void UpdateArtefactsFromDB(boolean testMode) throws Exception
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
        if (images == null || images.isEmpty())
            throw new Exception("No images to display.");

        final int[] index = {0};
        final BufferedImage[] current = {images.get(0)};
        final double[] scale = {1.0};
        final double[] tx = {0.0}, ty = {0.0};
        final boolean[] fitted = {false};
        final java.awt.Point[] lastDrag = {null};

        JFrame frame = new JFrame("Image Viewer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JComponent imagePanel = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (current[0] == null) return;
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                java.awt.geom.AffineTransform at = new java.awt.geom.AffineTransform();
                at.translate(tx[0], ty[0]);
                at.scale(scale[0], scale[0]);
                g2.drawRenderedImage(current[0], at);
                g2.dispose();
            }
        };

        Runnable fitToPanel = () -> {
            if (current[0] == null) return;
            int pw = Math.max(1, imagePanel.getWidth());
            int ph = Math.max(1, imagePanel.getHeight());
            double sx = (double) pw / current[0].getWidth();
            double sy = (double) ph / current[0].getHeight();
            scale[0] = Math.max(0.01, Math.min(10.0, Math.min(sx, sy) * 0.95));
            tx[0] = (pw - current[0].getWidth() * scale[0]) / 2.0;
            ty[0] = (ph - current[0].getHeight() * scale[0]) / 2.0;
            fitted[0] = true;
            imagePanel.revalidate();
            imagePanel.repaint();
        };

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    fitToPanel.run();
                    return;
                }
                lastDrag[0] = e.getPoint();
                imagePanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lastDrag[0] = null;
                imagePanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastDrag[0] != null) {
                    int dx = e.getX() - lastDrag[0].x;
                    int dy = e.getY() - lastDrag[0].y;
                    tx[0] += dx;
                    ty[0] += dy;
                    lastDrag[0] = e.getPoint();
                    fitted[0] = false;
                    imagePanel.repaint();
                }
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (current[0] == null) return;
                double delta = -0.1 * e.getPreciseWheelRotation();
                double oldScale = scale[0];
                double newScale = Math.max(0.01, Math.min(10.0, oldScale + delta));
                if (newScale == oldScale) return;

                // zoom around mouse position
                double mx = e.getX();
                double my = e.getY();
                double imgX = (mx - tx[0]) / oldScale;
                double imgY = (my - ty[0]) / oldScale;
                tx[0] = mx - imgX * newScale;
                ty[0] = my - imgY * newScale;
                scale[0] = newScale;
                fitted[0] = false;
                imagePanel.repaint();
            }
        };

        imagePanel.addMouseListener(ma);
        imagePanel.addMouseMotionListener(ma);
        imagePanel.addMouseWheelListener(ma);

        // keep fit on resize if previously fitted
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (fitted[0]) {
                    SwingUtilities.invokeLater(fitToPanel);
                }
            }
        });

        JPanel controls = new JPanel();
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        JButton fitButton = new JButton("Fit");
        JButton resetButton = new JButton("Reset Zoom");

        prevButton.addActionListener(e -> {
            index[0] = (index[0] - 1 + images.size()) % images.size();
            current[0] = images.get(index[0]);
            fitted[0] = false;
            // fit after layout settled
            SwingUtilities.invokeLater(fitToPanel);
        });
        nextButton.addActionListener(e -> {
            index[0] = (index[0] + 1) % images.size();
            current[0] = images.get(index[0]);
            fitted[0] = false;
            SwingUtilities.invokeLater(fitToPanel);
        });
        fitButton.addActionListener(e -> fitToPanel.run());
        resetButton.addActionListener(e -> {
            scale[0] = 1.0;
            tx[0] = 0.0;
            ty[0] = 0.0;
            fitted[0] = false;
            imagePanel.repaint();
        });

        controls.add(prevButton);
        controls.add(nextButton);
        controls.add(fitButton);
        controls.add(resetButton);

        frame.add(imagePanel, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // ensure initial fit runs after visible
        SwingUtilities.invokeLater(fitToPanel);
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

    public Loan getLoanByArtefactName(String artefactName, boolean b) throws Exception
    {
        DataBase.PullLoans(b);
        for (Loan loan : this.Loans)
        {
            if (loan != null && loan.getName() != null && loan.getArtefactName().toLowerCase().contains(artefactName.toLowerCase()))
            {
                return loan;
            }
        }
        return null;
    }

    public void addLoan(Loan loan)
    {
        if (loan == null) return;

        Inventory inv = getInstance();

        if (inv.Loans == null)
        {
            inv.Loans = new ArrayList<>();
        }
        inv.Loans.add(loan);
    }

    /**
     * Clear the in-memory loans list so it can be repopulated from the database
     */
    public void clearLoans()
    {
        Inventory inv = getInstance();
        inv.Loans = new ArrayList<>();
    }

    public void addRoom(Room room)
    {
        if (room == null) return;

        Inventory inv = getInstance();

        if (inv.Rooms == null)
        {
            inv.Rooms = new ArrayList<>();
        }

        Rooms.add(room);
    }
}
