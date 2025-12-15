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
import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;

public class DataBase
{
    static Connection connection;

    /**
     * Get a connection to the database
     * @return
     * @throws Exception
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
     * Add an artefact to the database
     * @param artefact
     * @return
     * @throws Exception
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
     * Clear all 3D artefacts from the database
     * @return
     * @throws Exception
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
     * Delete an artefact from the database by name
     * @param name
     * @return
     * @throws Exception
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
                        materialEnum = Material.fromString(material);
                    } catch (Exception e)
                    {
                        throw new IllegalArgumentException("Unknown material type: " + e);
                    }
                }

                switch (type)
                {
                    case "Furniture" -> artefacts.add(new Furniture(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, depth, name, materialEnum));
                    case "Pottery" -> artefacts.add(new Pottery(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, depth, name, materialEnum));
                    case "Sculpture" -> artefacts.add(new Sculpture(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, depth, name, materialEnum));
                    case "Misc" -> artefacts.add(new Misc(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, name));
                    case "Painting" -> artefacts.add(new Painting(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, name));
                    default -> {
                        throw new IllegalArgumentException("Unknown artefact type: " + type);
                    }
                }

            }
            return artefacts;
        } catch (SQLException e)
        {
            System.out.println("Error Pulling Artefacts: " + e.getMessage());
            return null;
        }
    }

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
            System.out.println("Error Clearing Loans: " + e.getMessage());
            return false;
        }
    }

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

    public static boolean addRoom(Room room, boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement(
                    "INSERT INTO rooms (roomNum, roomName, capacity) " +
                            "VALUES (?, ?, ?)");
            ps.setString(1, room.getRoomNum());
            ps.setString(2, room.getRoomName());
            ps.setInt(3, room.getCapacity());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            System.out.println("Error Adding Room: " + e.getMessage());
            return false;
        }
    }

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
                return rs.getString("roomNum");
            }
            return null;
        } catch (SQLException e)
        {
            return null;
        }
    }

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
            System.out.println("Error clearing Rooms: " + e.getMessage());
            return false;
        }
    }

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
            System.out.println("Error Setting Insurance: " + e.getMessage());
        }
    }

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
            System.out.println("Error retrieving rooms: " + e.getMessage());
        }
        return rooms;
    }

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
            System.out.println("Error Pulling Rooms: " + e.getMessage());
            return null;
        }
    }

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
            System.out.println("Error Adding Image to Artefact: " + e.getMessage());
        }
    }

    public static List<BufferedImage> getImageFromArtefact(boolean testMode) throws Exception
    {
        if (connection == null)
            connection = getConnection(testMode);

        try
        {
            var ps = connection.prepareStatement("SELECT data FROM images");
            var rs = ps.executeQuery();

            List<BufferedImage> images = new ArrayList<>();
            while (rs.next())
            {
                byte[] imageBytes = rs.getBytes("data");
                System.out.println(imageBytes.length);
                System.out.println(Arrays.toString(imageBytes));
                InputStream in = new java.io.ByteArrayInputStream(imageBytes);
                BufferedImage image = ImageIO.read(in);
                images.add(image);
            }
            return images;
        } catch (SQLException e)
        {
            System.out.println("Error getting Images: " + e.getMessage());
        }
        return null;
    }
}
