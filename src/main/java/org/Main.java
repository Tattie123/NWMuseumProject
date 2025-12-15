package org;

public class Main
{
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