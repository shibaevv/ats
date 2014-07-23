package net.apollosoft.ats.domain.dto.security;

import net.apollosoft.ats.domain.refdata.IUserType;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class UserGroupDivisionDto extends GroupDivisionDto
{

    private IUserType userType;

    /**
     * @return the userType
     */
    public IUserType getUserType()
    {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(IUserType userType)
    {
        this.userType = userType;
    }

}