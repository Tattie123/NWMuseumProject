package org.museum.artefacts;

import java.sql.Date;

public class Misc extends Artefact
{
    private String description;

    /**
     * Constructor with explicit insurance value.
     */
    public Misc(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, double insurance, String name, boolean TestMode)
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, name, TestMode);
    }

    public Misc(String historicEra, String style, String originCountry, String currentRoom, String author, Date dateOfCreation, double width, double height, String name, boolean testMode) throws Exception
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, name, testMode);
    }
}
