package org;

import javafx.application.Application;
import org.GUI.App;

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
            //UI.ResearcherUI();
            Application.launch(App.class, args);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}