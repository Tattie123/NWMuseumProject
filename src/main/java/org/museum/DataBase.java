package org.museum;

import com.sun.tools.javac.Main;
import org.museum.artefacts.artefacts3d.Artefact3D;

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
    public static Connection getConnection() throws Exception
    {

        String url, user, password;

        //load from a local file
        try (InputStream fis = Main.class.getClassLoader().getResourceAsStream(PROP_FILE))
        {
            Properties p = new Properties();
            p.load(fis);
            url = p.getProperty("db.url");
            user = p.getProperty("db.user");
            if (url.isBlank() || user.isBlank())
                throw new Exception("Missing database login information");
            password = p.getProperty("db.password");
        } catch (Exception e)
        {
            throw new Exception("No properties file found...");
        }
        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    /**
     * Add a 3D artefact to the database
     * @param artefact3d
     * @return
     * @throws Exception
     */
    public static boolean addArtefact3d(Artefact3D artefact3d) throws Exception
    {
        if (connection == null)
            connection = getConnection();

        try
        {
            var ps = connection.prepareStatement(
                    "INSERT INTO artefact3ds (historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, depth, name, type) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, artefact3d.getHistoricEra());
            ps.setString(2, artefact3d.getStyle());
            ps.setString(3, artefact3d.getOriginCountry());
            ps.setString(4, artefact3d.getCurrentRoom());
            ps.setString(5, artefact3d.getAuthor());
            ps.setDate(6, artefact3d.getSQLDateOfCreation());
            ps.setDouble(7, artefact3d.getWidth());
            ps.setDouble(8, artefact3d.getHeight());
            ps.setDouble(9, artefact3d.getInsurance());
            ps.setDouble(10, artefact3d.getDepth());
            ps.setString(11, artefact3d.getName());
            ps.setString(12, artefact3d.getType());
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
    public static boolean clearArtefact3D() throws Exception
    {
        if (connection == null)
            connection = getConnection();

        try
        {
            var ps = connection.prepareStatement("DELETE FROM artefact3ds;");
            var ps2 = connection.prepareStatement("ALTER TABLE artefact3ds AUTO_INCREMENT = 1;");
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
     * Delete a 3D artefact from the database by name
     * @param name
     * @return
     * @throws Exception
     */
    public static boolean deleteArtefact3D(String name) throws Exception
    {
        if (connection == null)
            connection = getConnection();

        try
        {
            var ps = connection.prepareStatement("DELETE FROM artefact3ds WHERE name = ?;");
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
     * Search for the current room of a 3D artefact by name
     * @param name
     * @return
     * @throws Exception
     */
    public static String searchArtefact3DRoom(String name) throws Exception
    {
        if (connection == null)
            connection = getConnection();

        try
        {
            var ps = connection.prepareStatement("SELECT currentRoom FROM artefact3ds WHERE name = ?;");
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
}
