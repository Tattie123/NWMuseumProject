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

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;

public class DataBase
{
    static Connection connection;

    private static final String PROP_FILE = "db.properties";

    /**
     * Get a connection to the database
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {

        String propertiesFile;

        // If running on GitHub CI, use CI properties
        if (System.getenv("GITHUB_ACTIONS") != null) {
            propertiesFile = "db-ci.properties";
        } else {
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
            throw new Exception("Invalid DB configuration");

        return DriverManager.getConnection(url, user, password);
    }


    /**
     * Add an artefact to the database
     * @param artefact
     * @return
     * @throws Exception
     */
    public static boolean addArtefact(Artefact artefact) throws Exception
    {
        if (connection == null)
            connection = getConnection();

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
            // Surface the SQL exception so tests and logs can show the real cause
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Clear all 3D artefacts from the database
     * @return
     * @throws Exception
     */
    public static boolean clearArtefacts() throws Exception
    {
        if (connection == null)
            connection = getConnection();

        try
        {
            var ps = connection.prepareStatement("DELETE FROM artefacts;");
            var ps2 = connection.prepareStatement("ALTER TABLE artefacts AUTO_INCREMENT = 1;");
            int rowsAffected = ps.executeUpdate();
            ps2.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete an artefact from the database by name
     * @param name
     * @return
     * @throws Exception
     */
    public static boolean deleteArtefact(String name) throws Exception
    {
        if (connection == null)
            connection = getConnection();

        try
        {
            var ps = connection.prepareStatement("DELETE FROM artefacts WHERE name = ?;");
            ps.setString(1, name);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Artefact> PullArtefacts() throws Exception
    {
        if (connection == null)
            connection = getConnection();

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
                    } catch (IllegalArgumentException e)
                    {
                        materialEnum = null;
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
                    }
                }

            }

            return artefacts;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addLoan(Loan loan) throws Exception
    {
        if (connection == null)
            connection = getConnection();

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
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clearLoans() throws Exception
    {
        if (connection == null)
            connection = getConnection();

        try
        {
            var ps = connection.prepareStatement("DELETE FROM loans;");
            var ps2 = connection.prepareStatement("ALTER TABLE loans AUTO_INCREMENT = 1;");
            int rowsAffected = ps.executeUpdate();
            ps2.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static String searchLoans(String name) throws Exception
    {
        if (connection == null)
            connection = getConnection();

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

    public static boolean addRoom(Room room) throws Exception
    {
        if (connection == null)
            connection = getConnection();

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
            e.printStackTrace();
            return false;
        }
    }

    public static String searchRoomByName(String roomName) throws Exception
    {
        if (connection == null)
            connection = getConnection();

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

    public static boolean clearRooms() throws Exception
    {
        if (connection == null)
            connection = getConnection();

        try
        {
            var ps = connection.prepareStatement("DELETE FROM rooms;");
            var ps2 = connection.prepareStatement("ALTER TABLE rooms AUTO_INCREMENT = 1;");
            int rowsAffected = ps.executeUpdate();
            ps2.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
