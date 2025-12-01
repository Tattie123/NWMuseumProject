package org.museum.artefacts.artefacts3d;

import java.awt.image.BufferedImage;
import java.util.Date;

public class Sculpture extends Artefact3D
{

    public Sculpture(String historicEra, String style, String originCountry, String currentRoom, String author, Date dateOfCreation, double width, double height, BufferedImage images, double insurance, double depth)
    {
        super(historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, images, insurance, depth);
    }
}
