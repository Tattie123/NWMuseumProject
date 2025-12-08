package org.museum.artefacts;

public enum Material
{
    STONE,
    METAL,
    WOOD;

    public static Material fromString(String material)
    {
        for (Material mat : Material.values())
        {
            if (mat.name().equalsIgnoreCase(material))
            {
                return mat;
            }
        }
        throw new IllegalArgumentException("No enum constant for material: " + material);
    }
}
