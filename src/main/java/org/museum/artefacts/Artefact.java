package org.museum.artefacts;

import org.museum.artefacts.artefacts3d.Artefact3D;

import java.awt.image.BufferedImage;
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

    public String getType()
    {
        return this.getClass().getSimpleName();
    }

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

    public boolean possibleToMove()
    {
        //todo implement feat
        return true;
    }

    public boolean moveRoom()
    {
        //todo implement feat
        return true;
    }

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

    public String getHistoricEra()
    {
        return historicEra;
    }

    public String getStyle()
    {
        return style;
    }

    public String getName()
    {
        return name;
    }

    public String getOriginCountry()
    {
        return originCountry;
    }

    public String getCurrentRoom()
    {
        return currentRoom;
    }

    public String getAuthor()
    {
        return author;
    }

    public Date getDateOfCreation()
    {
        return dateOfCreation;
    }

    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }

    public BufferedImage getImages()
    {
        return images;
    }

    public double getInsurance()
    {
        return insurance;
    }

    public double getDepth()
    {
        if (this instanceof Artefact3D)
            return depth;
        return 0;
    }

    public String getMaterialString()
    {
        if (material != null)
            return material.toString();
        return "";
    }

    public void setImages(BufferedImage images)
    {
        this.images = images;
    }

    public void setInsuranceValue(double insuranceValue)
    {
        this.insurance = insuranceValue;
    }
}
