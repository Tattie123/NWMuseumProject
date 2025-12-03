package org.museum.artefacts.artefacts3d;

import org.museum.artefacts.Artefact;
import org.museum.artefacts.Material;

import java.awt.image.BufferedImage;
import java.util.Date;

public abstract class Artefact3D extends Artefact
{

    public Artefact3D(String historicEra, String style, String originCountry, String currentRoom, String author, Date dateOfCreation, double width, double height, BufferedImage images, double insurance, double depth, String name, Material material)
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, images, insurance, name, depth, material);
    }


}
