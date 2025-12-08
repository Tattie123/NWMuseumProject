package org.museum.artefacts;

import java.awt.image.BufferedImage;

public class Misc extends Artefact
{
    private String description;

    public Misc(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, double insurance, String name)
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, name);
    }

    public Misc(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, String name)
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name);
    }
}
