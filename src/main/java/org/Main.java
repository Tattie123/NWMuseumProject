package org;

public class Main
{
    /**
     * Main application entry point.
     * Starts the console UI for the researcher role.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args)
    {
        try
        {
            UI.ResearcherUI();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}