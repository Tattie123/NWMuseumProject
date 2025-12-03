package org.museum.artefacts;

import java.awt.image.BufferedImage;

public class Misc extends Artefact
{
    private String description;

    public Misc(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, BufferedImage images, double insurance, String name)
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, images, insurance, name);
    }
}
