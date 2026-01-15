package org.museum.artefacts;

/**
 * Materials that can be used for 3D artefacts.
 */
public enum Material
{
    STONE,
    METAL,
    WOOD;

    /**
     * Parse a material name (case-insensitive) into the enum.
     *
     * @param material material name
     * @return corresponding Material enum value
     * @throws IllegalArgumentException if the material string is unknown
     */
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
