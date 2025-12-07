package org.museum.artefacts.artefacts3d;

import org.museum.artefacts.Artefact;
import org.museum.artefacts.Material;

import java.awt.image.BufferedImage;

public abstract class Artefact3D extends Artefact
{

    public Artefact3D(String historicEra, String style, String originCountry, String currentRoom, String author, java.sql.Date dateOfCreation, double width, double height, double insurance, double depth, String name, Material material)
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, insurance, name, depth, material);
    }


}
