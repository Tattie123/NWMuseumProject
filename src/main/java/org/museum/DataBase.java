package org.museum;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.sun.tools.javac.Main;
import org.museum.artefacts.Artefact;
import org.museum.artefacts.artefacts3d.Artefact3D;

public class DataBase
{
    static Connection connection;

    private static final String PROP_FILE = "db.properties";

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

    public static boolean addArtefact3d(Artefact3D artefact3d) throws Exception
    {
        if (connection == null)
            connection = getConnection();

        try
        {
            var ps = connection.prepareStatement(
                    "INSERT INTO artefact3ds (historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, depth) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            // Surface the SQL exception so tests and logs can show the real cause
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clearArtefact3D() throws Exception
    {
        if (connection == null)
            connection = getConnection();

        try
        {
            var ps = connection.prepareStatement("DELETE FROM artefact3ds;");
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
