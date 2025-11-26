package org.museum.artefacts;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;

public abstract class Artefact
{
    private String historicEra;
    private String style;
    private String originCountry;
    private String currentRoom;
    private String author;
    private Date dateOfCreation;
    private double width;
    private double height;
    private List images;
    private double insurance;

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
}
