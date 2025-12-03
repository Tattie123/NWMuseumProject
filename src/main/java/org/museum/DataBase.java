package org.museum;

import org.museum.artefacts.Artefact;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
            ps.setDate(6, artefact.getSQLDateOfCreation());
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

    /**
     * Search for the current room of an artefact by name
     * @param name
     * @return
     * @throws Exception
     */
    public static String searchArtefactRoom(String name) throws Exception
    {
        if (connection == null)
            connection = getConnection();

        try
        {
            var ps = connection.prepareStatement("SELECT currentRoom FROM artefacts WHERE name = ?;");
            ps.setString(1, name);
            var rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getString("currentRoom");
            }
            return null;
        } catch (SQLException e)
        {
            return null;
        }
    }

    /**
     * Search for all artefacts of one type with a given type
     * @param type
     * @return
     * @throws Exception
     */
    public static String searchArtefactsWithType(String type) throws Exception
    {
        if (connection == null)
            connection = getConnection();

        try
        {
            var ps = connection.prepareStatement("SELECT name FROM artefacts WHERE type = ?;");
            ps.setString(1, type);
            var rs = ps.executeQuery();
            String names = "";
            while (rs.next())
            {
                names += rs.getString("name") + ", ";
            }
            if (names.isEmpty())
                return null;
            names = names.substring(0, names.length() - 2);
            return names;
        } catch (SQLException e)
        {
            return null;
        }
    }
}
