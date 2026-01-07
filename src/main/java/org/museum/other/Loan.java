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

    /**
     * Approve the current loan (static shared flag in this implementation).
     *
     * @return true when approved
     */
    public static boolean approveLoan()
    {
        isApproved = true;
        return true;
    }

    /**
     * Reject a loan. Current implementation is a placeholder.
     *
     * @return true when rejection performed
     */
    public boolean rejectLoan()
    {
        //delete loan instance

        return true;
    }

    /**
     * Construct a new loan request.
     *
     * @param isApproved initial approval state
     * @param name requester name
     * @param contactInfo contact details
     * @param telNum telephone number
     * @param artefactName requested artefact
     * @param startDate loan start
     * @param endDate loan end
     */
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

    /**
     * @return whether the last loan was approved (static flag)
     */
    public boolean isApproved()
    {
        return isApproved;
    }

    /**
     * @return requester name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return contact info
     */
    public String getContactInfo()
    {
        return contactInfo;
    }

    /**
     * @return telephone number
     */
    public String getTelNum()
    {
        return telNum;
    }

    /**
     * @return requested artefact name
     */
    public String getArtefactName()
    {
        return artefactName;
    }

    /**
     * @return loan start date
     */
    public java.sql.Date getStartDate()
    {
        return startDate;
    }

    /**
     * @return loan end date
     */
    public Date getEndDate()
    {
        return endDate;
    }
}
