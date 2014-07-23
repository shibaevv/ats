package net.apollosoft.ats.audit.search;

import net.apollosoft.ats.search.SearchCriteria;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class UserSearchCriteria extends SearchCriteria
{

    private String userName;

    private Long groupId;

    private Long divisionId;

    /**
     * @return the userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String responsibleUserId)
    {
        this.userName = responsibleUserId;
    }

    /**
     * @return the groupId
     */
    public Long getGroupId()
    {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    /**
     * @return the divisionId
     */
    public Long getDivisionId()
    {
        return divisionId;
    }

    /**
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(Long divisionId)
    {
        this.divisionId = divisionId;
    }

}