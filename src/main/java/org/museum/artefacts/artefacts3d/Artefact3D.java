package org.museum.artefacts.artefacts3d;

import org.museum.artefacts.Artefact;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Date;

public abstract class Artefact3D extends Artefact
{
    private double depth;

    public Artefact3D(String historicEra, String style, String originCountry, String currentRoom, String author, Date dateOfCreation, double width, double height, BufferedImage images, double insurance, double depth, String name)
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, images, insurance, name);
        this.depth = depth;
    }

    public double getDepth()
    {
        return depth;
    }

}
