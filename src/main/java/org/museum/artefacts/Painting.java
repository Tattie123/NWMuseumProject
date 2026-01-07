package org.museum.artefacts;

import java.awt.image.BufferedImage;

public class Painting extends Artefact
{
    /**
     * Constructor with explicit insurance value.
     */
    public Painting(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, double insurance, String name, Boolean TestMode)
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, name, TestMode);
    }

    /**
     * Constructor without insurance value.
     */
    public Painting(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, String name, Boolean TestMode) throws Exception
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name, TestMode);
    }
}
