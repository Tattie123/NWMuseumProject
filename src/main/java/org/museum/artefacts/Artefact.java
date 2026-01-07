package org.museum.artefacts;

import org.museum.artefacts.artefacts3d.Artefact3D;
import org.museum.data.DataBase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

public abstract class Artefact
{
    private final String name;
    private final String historicEra;
    private final String style;
    private final String originCountry;
    private final String currentRoom;
    private final String author;
    private final java.sql.Date dateOfCreation;
    private final double width;
    private final double height;
    private BufferedImage images;
    private double insurance;
    private double depth;
    private Material material;

    /**
     * Returns the runtime type/name of the artefact subclass.
     *
     * @return simple class name representing the artefact type
     */
    public String getType()
    {
        return this.getClass().getSimpleName();
    }

    /**
     * Human-readable representation of the artefact including key metadata.
     *
     * @return String with artefact fields
     */
    @Override
    public String toString()
    {
        return "Artefact{" +
                "insurance=" + insurance +
                ", images=" + images +
                ", height=" + height +
                ", width=" + width +
                ", dateOfCreation=" + dateOfCreation +
                ", author='" + author + '\'' +
                ", currentRoom='" + currentRoom + '\'' +
                ", originCountry='" + originCountry + '\'' +
                ", style='" + style + '\'' +
                ", historicEra='" + historicEra + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * Placeholder indicating whether the artefact can be moved between rooms.
     *
     * @return true if movable (default true until implemented)
     */
    public boolean possibleToMove()
    {
        //todo implement feat
        return true;
    }

    /**
     * Placeholder method to move the artefact to another room.
     *
     * @return true if move succeeded (default true until implemented)
     */
    public boolean moveRoom()
    {
        //todo implement feat
        return true;
    }

    /**
     * Full constructor including insurance value.
     *
     * @param historicEra historic era
     * @param style visual/style
     * @param originCountry origin country
     * @param currentRoom current room identifier
     * @param author creator/author
     * @param dateOfCreation creation date
     * @param width width in appropriate units
     * @param height height in appropriate units
     * @param insurance insurance value
     * @param name artefact name
     */
    public Artefact(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, double insurance, String name)
    {
        this.name = name;
        this.historicEra = historicEra;
        this.style = style;
        this.originCountry = originCountry;
        this.currentRoom = currentRoom;
        this.author = author;
        this.dateOfCreation = dateOfCreation;
        this.width = width;
        this.height = height;
        this.insurance = insurance;
    }

    /**
     * Constructor without insurance value.
     *
     * @param historicEra historic era
     * @param style visual/style
     * @param originCountry origin country
     * @param currentRoom current room identifier
     * @param author creator/author
     * @param dateOfCreation creation date
     * @param width width in appropriate units
     * @param height height in appropriate units
     * @param name artefact name
     */
    public Artefact(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, String name)
    {
        this.name = name;
        this.historicEra = historicEra;
        this.style = style;
        this.originCountry = originCountry;
        this.currentRoom = currentRoom;
        this.author = author;
        this.dateOfCreation = dateOfCreation;
        this.width = width;
        this.height = height;
    }

    /**
     * Protected constructor for 3D artefacts including insurance, depth and material.
     */
    protected Artefact(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, double insurance, String name, double depth, Material material)
    {
        this.name = name;
        this.historicEra = historicEra;
        this.style = style;
        this.originCountry = originCountry;
        this.currentRoom = currentRoom;
        this.author = author;
        this.dateOfCreation = dateOfCreation;
        this.width = width;
        this.height = height;
        this.insurance = insurance;
        this.depth = depth;
        this.material = material;
    }

    /**
     * Protected constructor for 3D artefacts without insurance value.
     */
    protected Artefact(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, String name, double depth, Material material)
    {
        this.name = name;
        this.historicEra = historicEra;
        this.style = style;
        this.originCountry = originCountry;
        this.currentRoom = currentRoom;
        this.author = author;
        this.dateOfCreation = dateOfCreation;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.material = material;
    }

    /**
     * @return historic era string
     */
    public String getHistoricEra()
    {
        return historicEra;
    }

    /**
     * @return style string
     */
    public String getStyle()
    {
        return style;
    }

    /**
     * @return artefact name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return origin country
     */
    public String getOriginCountry()
    {
        return originCountry;
    }

    /**
     * @return current room identifier
     */
    public String getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * @return author/creator
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     * @return date of creation as java.sql.Date
     */
    public Date getDateOfCreation()
    {
        return dateOfCreation;
    }

    /**
     * @return width
     */
    public double getWidth()
    {
        return width;
    }

    /**
     * @return height
     */
    public double getHeight()
    {
        return height;
    }

    /**
     * @return stored BufferedImage or null if none
     */
    public BufferedImage getImages()
    {
        return images;
    }

    /**
     * @return insurance value
     */
    public double getInsurance()
    {
        return insurance;
    }

    /**
     * @return depth for 3D artefacts (0 for non-3D)
     */
    public double getDepth()
    {
        if (this instanceof Artefact3D)
            return depth;
        return 0;
    }

    /**
     * @return material name or empty string if none
     */
    public String getMaterialString()
    {
        if (material != null)
            return material.toString();
        return "";
    }

    /**
     * Set the in-memory image for this artefact.
     *
     * @param images BufferedImage to store
     */
    public void setImages(BufferedImage images)
    {
        this.images = images;
    }

    /**
     * Update the insurance value for this artefact instance.
     *
     * @param insuranceValue numeric insurance value
     */
    public void setInsuranceValue(double insuranceValue)
    {
        this.insurance = insuranceValue;
    }

    /**
     * Add an image from the resources folder to this artefact and persist it via DataBase.
     *
     * @param filePath relative path under src/main/resources/Images/
     * @param testMode when true use test DB properties
     * @param fileType image file format (e.g. "png", "jpg")
     * @throws Exception if the image cannot be read or persisted
     */
    public void addImage(String filePath, boolean testMode, String fileType) throws Exception
    {
        BufferedImage image = ImageIO.read(new File("src/main/resources/Images/" + filePath));
        DataBase.addImageToArtefact(this.name, image, testMode, fileType, filePath);
    }
}
