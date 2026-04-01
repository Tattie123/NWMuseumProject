package org.museum.data;

import org.museum.artefacts.Artefact;
import org.museum.artefacts.Material;
import org.museum.artefacts.Misc;
import org.museum.artefacts.Painting;
import org.museum.artefacts.artefacts3d.Furniture;
import org.museum.artefacts.artefacts3d.Pottery;
import org.museum.artefacts.artefacts3d.Sculpture;
import org.museum.other.Loan;
import org.museum.other.Room;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;

public class DataBase
{
    static Connection connection;

    /**
     * Get a connection to the database based on environment and test mode.
     *
     * @param testmode true to use test DB properties
     * @return JDBC Connection
     * @throws Exception if properties file is missing or connection fails
     */
    public static Connection getConnection(boolean testmode) throws Exception {

        String propertiesFile;

        // If running on GitHub CI, use CI properties
        if (System.getenv("GITHUB_ACTIONS") != null) {
            propertiesFile = "db-ci.properties";
        } else if (testmode)
        {
            propertiesFile = "db-testing.properties";
        } else
        {
            //System.out.println("Using production database properties.");
            propertiesFile = "db.properties";
        }

        Properties p = new Properties();
        InputStream stream = DataBase.class.getClassLoader().getResourceAsStream(propertiesFile);

        if (stream == null) {
            throw new Exception("Properties file not found: " + propertiesFile);
        }

        p.load(stream);

        String url = p.getProperty("db.url");
        String user = p.getProperty("db.user");
        String password = p.getProperty("db.password");

        if (url == null || user == null)
            throw new SQLClientInfoException("Error loading database properties from " + propertiesFile, null);

        return DriverManager.getConnection(url, user, password);
    }


    /**
     * Add an artefact to the database.
     *
     * @param artefact artefact to persist
     * @param testMode when true use test DB properties
     * @return true if insert affected rows
     * @throws Exception on DB or input errors
     */
    public static boolean addArtefact(Artefact artefact, boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement(
                    "INSERT INTO artefacts (historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, depth, name, type, material) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, artefact.getHistoricEra());
            ps.setString(2, artefact.getStyle());
            ps.setString(3, artefact.getOriginCountry());
            ps.setString(4, artefact.getCurrentRoom());
            ps.setString(5, artefact.getAuthor());
            ps.setDate(6, (Date) artefact.getDateOfCreation());
            ps.setDouble(7, artefact.getWidth());
            ps.setDouble(8, artefact.getHeight());
            ps.setDouble(9, artefact.getInsurance());
            ps.setDouble(10, artefact.getDepth());
            ps.setString(11, artefact.getName());
            ps.setString(12, artefact.getType());
            ps.setString(13, artefact.getMaterialString());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            throw new IllegalArgumentException("Error while Adding Artefact: " + e.getMessage());
        }
    }

    /**
     * Clear all artefacts from the database (used in tests).
     *
     * @param testMode when true use test DB properties
     * @return true if rows were deleted
     * @throws Exception on DB errors
     */
    public static boolean clearArtefacts(boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement("DELETE FROM artefacts;");
            var ps2 = connection.prepareStatement("ALTER TABLE artefacts AUTO_INCREMENT = 1;");
            int rowsAffected = ps.executeUpdate();
            ps2.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            throw new IllegalArgumentException("Error clearing artefacts: " + e);
        }
    }

    /**
     * Delete an artefact from the database by name.
     *
     * @param name artefact name
     * @param testMode use test DB if true
     * @return true if deletion affected rows
     * @throws Exception on DB errors
     */
    public static boolean deleteArtefact(String name, boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement("DELETE FROM artefacts WHERE name = ?;");
            ps.setString(1, name);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            throw new IllegalArgumentException("Error deleting artefact: " + e.getMessage());
        }
    }

    /**
     * Pull all artefacts from the database and construct domain objects.
     *
     * @param testMode test DB flag
     * @return list of Artefact instances or null on failure
     * @throws Exception on DB errors
     */
    public static List<Artefact> PullArtefacts(boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement("SELECT * FROM artefacts;");
            var rs = ps.executeQuery();

            List<Artefact> artefacts = new ArrayList<>();

            while (rs.next())
            {
                String historicEra = rs.getString("historicEra");
                String style = rs.getString("style");
                String originCountry = rs.getString("originCountry");
                String currentRoom = rs.getString("currentRoom");
                String author = rs.getString("author");
                Date dateOfCreation = rs.getDate("dateOfCreation");
                double width = rs.getDouble("width");
                double height = rs.getDouble("height");
                double insurance = rs.getDouble("insurance");
                double depth = rs.getDouble("depth");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String material = rs.getString("material");

                Material materialEnum = null;
                if (material != null && !material.isBlank())
                {
                    try
                    {
                        materialEnum = Material. fromString(material);
                    } catch (Exception e)
                    {
                        throw new IllegalArgumentException("Unknown material type: " + e);
                    }
                }

                switch (type)
                {
                    case "Furniture" -> artefacts.add(new Furniture(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, depth, name, materialEnum, false));
                    case "Pottery" -> artefacts.add(new Pottery(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, depth, name, materialEnum, false));
                    case "Sculpture" -> artefacts.add(new Sculpture(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, depth, name, materialEnum, false));
                    case "Misc" -> artefacts.add(new Misc(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, name, false));
                    case "Painting" -> artefacts.add(new Painting(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, name, false));
                    default -> {
                        throw new IllegalArgumentException("Unknown artefact type: " + type);
                    }
                }

            }
            return artefacts;
        } catch (SQLException e)
        {
            return null;
        }
    }

    /**
     * Persist a loan request in the database.
     *
     * @param loan loan request
     * @param testMode use test DB if true
     * @return true if insert affected rows
     * @throws Exception on DB errors
     */
    public static boolean addLoan(Loan loan, boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement(
                    "INSERT INTO loans (isApproved, name, contactInfo, telNum, artefactName, startDate, endDate) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setBoolean(1, loan.isApproved());
            ps.setString(2, loan.getName());
            ps.setString(3, loan.getContactInfo());
            ps.setString(4, loan.getTelNum());
            ps.setString(5, loan.getArtefactName());
            ps.setDate(6, loan.getStartDate());
            ps.setDate(7, loan.getEndDate());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            throw new IllegalArgumentException("Error Adding Loan: " + e.getMessage());
        }
    }

    /**
     * Clear loans table (test helper).
     *
     * @param testMode use test DB if true
     * @return true if rows deleted
     * @throws Exception on DB errors
     */
    public static boolean clearLoans(boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement("DELETE FROM loans;");
            var ps2 = connection.prepareStatement("ALTER TABLE loans AUTO_INCREMENT = 1;");
            int rowsAffected = ps.executeUpdate();
            ps2.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            return false;
        }
    }

    /**
     * Search loans by requester name and return contact info if found.
     *
     * @param name requester name
     * @param testMode use test DB if true
     * @return contactInfo string or null
     * @throws Exception on DB errors
     */
    public static String searchLoans(String name, boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement("SELECT contactInfo FROM loans WHERE name = ?;");
            ps.setString(1, name);
            var rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getString("contactInfo");
            }
            return null;
        } catch (SQLException e)
        {
            return null;
        }
    }

    /**
     * Add a room to the rooms table.
     *
     * @param room Room instance
     * @param testMode test DB flag
     * @return true if insert affected rows
     * @throws Exception on DB errors
     */
    public static boolean addRoom(Room room, boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement(
                    "INSERT INTO rooms (roomNum, roomName, capacity) " +
                            "VALUES (?, ?, ?)");
            ps.setString(1, room.roomNum());
            ps.setString(2, room.roomName());
            ps.setInt(3, room.capacity());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            return false;
        }
    }

    /**
     * Search room by name and return its room number if found.
     *
     * @param roomName partial or full room name
     * @param testMode test DB flag
     * @return room number or null
     * @throws Exception on DB errors
     */
    public static String searchRoomByName(String roomName, boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement("SELECT roomNum FROM rooms WHERE roomName LIKE ?;");
            ps.setString(1, "%" + roomName + "%");
            var rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getString("roomNum").toLowerCase();
            }
            return null;
        } catch (SQLException e)
        {
            return null;
        }
    }

    /**
     * Clear rooms table (test helper).
     *
     * @param testMode use test DB if true
     * @return true if rows deleted
     * @throws Exception on DB errors
     */
    public static boolean clearRooms(boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement("DELETE FROM rooms;");
            var ps2 = connection.prepareStatement("ALTER TABLE rooms AUTO_INCREMENT = 1;");
            int rowsAffected = ps.executeUpdate();
            ps2.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            return false;
        }
    }

    /**
     * Set an artefact's insurance value by name.
     *
     * @param name artefact name
     * @param insuranceValue numeric insurance value
     */
    public static void setInsuranceValue(String name, double insuranceValue)
    {
        try
        {
            if (connection == null)
                connection = getConnection(false);

            var ps = connection.prepareStatement("UPDATE artefacts SET insurance = ? WHERE name = ?;");
            ps.setDouble(1, insuranceValue);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve all rooms from the database.
     *
     * @return list of Room instances (may be empty)
     */
    public static List<Room> getRooms()
    {
        List<Room> rooms = new ArrayList<>();
        try
        {
            if (connection == null)
                connection = getConnection(false);

            var ps = connection.prepareStatement("SELECT * FROM rooms;");
            var rs = ps.executeQuery();

            while (rs.next())
            {
                String roomNum = rs.getString("roomNum");
                String roomName = rs.getString("roomName");
                int capacity = rs.getInt("capacity");

                rooms.add(new Room(roomNum, roomName, capacity));
            }
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    /**
     * Pull rooms helper used by Inventory to refresh cache.
     *
     * @param testMode test DB flag
     * @return list of Room instances or null on failure
     * @throws Exception on DB errors
     */
    public static List<Room> PullRooms(boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement("SELECT * FROM rooms;");
            var rs = ps.executeQuery();

            List<Room> rooms = new ArrayList<>();

            while (rs.next())
            {
                Room room = new Room(rs.getString("roomNum"), rs.getString("roomName"), rs.getInt("capacity"));
                rooms.add(room);
            }
            return rooms;
        } catch (SQLException e)
        {
            return null;
        }
    }

    /**
     * Persist an image for an artefact into the images table.
     *
     * @param name artefact name
     * @param image BufferedImage to persist
     * @param testMode test DB flag
     * @param fileType image file type (e.g. png)
     * @param filePath original file path or identifier
     * @throws Exception on I/O or DB errors
     */
    public static void addImageToArtefact(String name, BufferedImage image, boolean testMode, String fileType, String filePath) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement("INSERT INTO images(name, data) VALUES (?, ?)");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(image, fileType, baos);
            byte[] imageBytes = baos.toByteArray();

            ps.setString(1, name + " " + filePath);
            ps.setBytes(2, imageBytes);
            ps.executeUpdate();

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve all images stored in the images table.
     *
     * @param testMode test DB flag
     * @return list of BufferedImage instances or null on failure
     * @throws Exception on I/O or DB errors
     */
    public static List<BufferedImage> getImageFromArtefact(String artefactName, boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement("SELECT data FROM images WHERE name LIKE ?;");
            ps.setString(1, artefactName + "%");
            var rs = ps.executeQuery();

            List<BufferedImage> images = new ArrayList<>();
            while (rs.next())
            {
                byte[] imageBytes = rs.getBytes("data");
                InputStream in = new java.io.ByteArrayInputStream(imageBytes);
                BufferedImage image = ImageIO.read(in);
                images.add(image);
            }
            return images;
        } catch (SQLException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static List<BufferedImage> getAllImages(boolean b) throws Exception
    {
        if (connection == null)
            connection = getConnection(b);

        try
        {
            var ps = connection.prepareStatement("SELECT data FROM images;");
            var rs = ps.executeQuery();

            List<BufferedImage> images = new ArrayList<>();
            while (rs.next())
            {
                byte[] imageBytes = rs.getBytes("data");
                InputStream in = new java.io.ByteArrayInputStream(imageBytes);
                BufferedImage image = ImageIO.read(in);
                images.add(image);
            }
            return images;
        } catch (SQLException e)
        {
            throw new SQLException(e);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getAllRooms(boolean b) throws Exception
    {
        if (connection == null)
            connection = getConnection(b);

        try
        {
            var ps = connection.prepareStatement("SELECT roomNum FROM rooms;");
            var rs = ps.executeQuery();

            List<String> rooms = new ArrayList<>();
            while (rs.next())
            {
                String room = rs.getString("roomNum");
                rooms.add(room);
            }
            return rooms;
        } catch (SQLException e)
        {
            throw new SQLException(e);
        }
    }

    public static void PullLoans(boolean b)
    {
        if (connection == null)
            try
            {
                connection = getConnection(b);
            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }

        // clear existing in-memory loans so we don't duplicate when pulling
        Inventory.getInstance().clearLoans();

        try
        {
            var ps = connection.prepareStatement("SELECT * FROM loans;");
            var rs = ps.executeQuery();

            while (rs.next())
            {
                boolean isApproved = rs.getBoolean("isApproved");
                String name = rs.getString("name");
                String contactInfo = rs.getString("contactInfo");
                String telNum = rs.getString("telNum");
                String artefactName = rs.getString("artefactName");
                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");
                Loan loan = new Loan(isApproved, name, contactInfo, telNum, artefactName, startDate, endDate);
                Inventory.getInstance().addLoan(loan);
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the approval state of a loan in the database by artefact name.
     */
    public static boolean updateLoanApproval(String artefactName, boolean approved, boolean testMode)
    {
        if (connection == null)
            try
            {
                connection = getConnection(testMode);
            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }

        try
        {
            var ps = connection.prepareStatement("UPDATE loans SET isApproved = ? WHERE artefactName = ?;");
            ps.setBoolean(1, approved);
            ps.setString(2, artefactName);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e)
        {
            return false;
        }
    }

    public static Room getRoomFromName(String roomName, boolean testMode)
    {
        if (connection == null)
            try
            {
                connection = getConnection(testMode);
            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }

        try
        {
            var ps = connection.prepareStatement("SELECT * FROM rooms WHERE roomNum LIKE ?;");
            ps.setString(1, roomName);
            var rs = ps.executeQuery();
            rs.next();
            Room room = new Room(rs.getString("roomNum"), rs.getString("roomName"), rs.getInt("capacity"));
            Inventory.getInstance().addRoom(room);
            return room;
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update an artefact's current room value in the database.
     *
     * @param artefactName artefact name to update
     * @param roomNum room number to assign
     * @param testMode when true use test DB properties
     * @return true if update affected rows
     * @throws Exception on DB errors
     */
    public static boolean updateArtefactRoom(String artefactName, String roomNum, boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement("UPDATE artefacts SET currentRoom = ? WHERE name = ?;");
            ps.setString(1, roomNum);
            ps.setString(2, artefactName);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
