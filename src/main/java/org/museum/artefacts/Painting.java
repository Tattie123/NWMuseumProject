package org.museum.artefacts;

import java.awt.image.BufferedImage;

public class Painting extends Artefact
{
    public Painting(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, double insurance, String name)
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, name);
    }

    public Painting(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, String name)
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name);
    }
}
