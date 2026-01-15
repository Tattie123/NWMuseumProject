package org.museum.artefacts.artefacts3d;

import org.museum.artefacts.Material;

import java.awt.image.BufferedImage;

public class Sculpture extends Artefact3D
{

    /**
     * Constructor with insurance value for sculpture.
     */
    public Sculpture(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, double insurance, double depth, String name, Material material, Boolean TestMode) throws Exception
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, depth, name, material, TestMode);
    }

    /**
     * Constructor without insurance value for sculpture.
     */
    public Sculpture(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, double depth, String name, Material material, Boolean TestMode) throws Exception
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, depth, name, material, TestMode);
    }
}