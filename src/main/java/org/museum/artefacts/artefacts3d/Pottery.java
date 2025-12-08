package org.museum.artefacts.artefacts3d;

import org.museum.artefacts.Material;

import java.awt.image.BufferedImage;

public class Pottery extends Artefact3D
{
    public Pottery(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, double insurance, double depth, String name, Material material)
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, depth, name, material);
    }

    public Pottery(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, double depth, String name, Material material)
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, depth, name, material);
    }
}
