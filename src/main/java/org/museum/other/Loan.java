package org.museum.other;

import java.sql.Date;

public class Loan
{
    private boolean isApproved;
    private String name;
    private String contactInfo;
    private String telNum; // Must be String since numbers are too large / start with 0 or +
    private String artefactName;
    private java.sql.Date startDate;
    private java.sql.Date endDate;

    public boolean createLoanRequest()
    {
        //todo implement feat
        return true;
    }

    public boolean approveLoan()
    {
        //todo implement feat
        return true;
    }

    public boolean rejectLoan()
    {
        //todo implement feat
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
