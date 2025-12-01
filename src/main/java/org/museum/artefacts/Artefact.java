package org.museum.artefacts;

import org.museum.Inventory;

import java.awt.image.BufferedImage;
import java.util.Date;

public abstract class Artefact
{
    private String name;
    private String historicEra;
    private String style;
    private String originCountry;
    private String currentRoom;
    private String author;
    private Date dateOfCreation;
    private double width;
    private double height;
    private BufferedImage images;
    private double insurance;

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

    public Artefact(String historicEra, String style, String originCountry, String currentRoom, String author, Date dateOfCreation, double width, double height, BufferedImage images, double insurance, String name)
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
        this.images = images;
        this.insurance = insurance;

        Inventory.addArtefact(this);
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

    public java.sql.Date getSQLDateOfCreation()
    {
        return new java.sql.Date(dateOfCreation.getTime());
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
}
