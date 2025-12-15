package org.museum.other;

import java.sql.Date;

public class Loan
{
    private static boolean isApproved;
    private final String name;
    private final String contactInfo;
    private final String telNum; // Must be String since numbers are too large / start with 0 or +
    private final String artefactName;
    private final java.sql.Date startDate;
    private final java.sql.Date endDate;

    public static boolean approveLoan()
    {
        isApproved = true;
        return true;
    }

    public boolean rejectLoan()
    {
        //delete loan instance

        return true;
    }

    public Loan(boolean isApproved, String name, String contactInfo, String telNum, String artefactName, java.sql.Date startDate, java.sql.Date endDate)
    {
        this.isApproved = isApproved;
        this.name = name;
        this.contactInfo = contactInfo;
        this.telNum = telNum;
        this.artefactName = artefactName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isApproved()
    {
        return isApproved;
    }

    public String getName()
    {
        return name;
    }

    public String getContactInfo()
    {
        return contactInfo;
    }

    public String getTelNum()
    {
        return telNum;
    }

    public String getArtefactName()
    {
        return artefactName;
    }

    public java.sql.Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }
}
